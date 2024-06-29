import React, { useState, useEffect } from 'react';
import axios from '../axiosInstance';
import { useParams, Link } from 'react-router-dom';
import './StudentsGrades.css'; // Import the CSS file

const StudentsGrades = () => {
    const { classId, teacherId, disciplineId } = useParams();
    const [students, setStudents] = useState([]);
    const [grades, setGrades] = useState({});
    const [gradeTypes, setGradeTypes] = useState([]);
    const [error, setError] = useState(null);
    const [successMessage, setSuccessMessage] = useState('');
    const [selectedGrades, setSelectedGrades] = useState({});
    const [editingGrade, setEditingGrade] = useState(null);
    const [visibleGradeId, setVisibleGradeId] = useState(null);

    // Fetch students in the class
    const fetchStudents = async () => {
        try {
            const response = await axios.get(`/students/school-class/${classId}`);
            setStudents(response.data);
        } catch (error) {
            setError('Error fetching students');
            console.error('Error fetching students:', error);
        }
    };

    // Fetch grades for each student
    const fetchGrades = async () => {
        try {
            const response = await axios.get(`/students/${teacherId}/discipline/${disciplineId}/grades`);
            const gradesData = response.data.reduce((acc, grade) => {
                acc[grade.studentId] = acc[grade.studentId] || [];
                acc[grade.studentId].push(grade);
                return acc;
            }, {});
            setGrades(gradesData);
        } catch (error) {
            setError('Error fetching grades');
            console.error('Error fetching grades:', error);
        }
    };

    // Fetch all grade types
    const fetchGradeTypes = async () => {
        try {
            const response = await axios.get('/gradeTypes/');
            setGradeTypes(response.data);
        } catch (error) {
            setError('Error fetching grade types');
            console.error('Error fetching grade types:', error);
        }
    };

    // Fetch initial data on component mount
    useEffect(() => {
        fetchStudents();
        fetchGrades();
        fetchGradeTypes();
    }, [classId, teacherId, disciplineId]);

    // Find grade type by ID
    const findGradeTypeById = (id) => {
        const gradeType = gradeTypes.find(gradeType => gradeType.id === id);
        return gradeType ? gradeType.gradeTypeEnum : 'Unknown';
    };

    // Add grade to a student
    const handleAddGrade = async (event, studentId, disciplineId) => {
        event.preventDefault();
        try {
            const selectedGrade = selectedGrades[studentId];
            await axios.post(`/grades/`, { gradeTypeId: selectedGrade.gradeTypeId, studentId, disciplineId });
            setSuccessMessage('Grade added successfully!');
            setTimeout(() => setSuccessMessage(''), 3000);
            setSelectedGrades(prevState => ({ ...prevState, [studentId]: '' }));
            fetchGrades(); // Refresh the grades list
        } catch (error) {
            setError('Error adding grade');
            console.error('Error adding grade:', error);
        }
    };

    // Remove grade from a student
    const handleRemoveGrade = async (gradeId) => {
        try {
            await axios.delete(`/grades/${gradeId}`);
            setSuccessMessage('Grade removed successfully!');
            setTimeout(() => setSuccessMessage(''), 3000);
            fetchGrades(); // Refresh the grades list
        } catch (error) {
            setError('Error removing grade');
            console.error('Error removing grade:', error);
        }
    };

    // Start editing a grade
    const handleEditGrade = (grade) => {
        setEditingGrade(grade);
        setSelectedGrades(prevState => ({ ...prevState, [grade.studentId]: grade.gradeTypeId }));
        setVisibleGradeId(grade.id);
    };

    // Save edited grade
    const handleSaveGrade = async (event) => {
        event.preventDefault();
        try {
            const selectedGrade = selectedGrades[editingGrade.studentId];
            await axios.put(`/grades/${editingGrade.id}`, { gradeTypeId: selectedGrade });
            setSuccessMessage('Grade updated successfully!');
            setTimeout(() => setSuccessMessage(''), 3000);
            setSelectedGrades(prevState => ({ ...prevState, [editingGrade.studentId]: '' }));
            setEditingGrade(null);
            setVisibleGradeId(null);
            fetchGrades(); // Refresh the grades list
        } catch (error) {
            setError('Error updating grade');
            console.error('Error updating grade:', error);
        }
    };

    // Handle grade selection change
    const handleGradeChange = (studentId, value) => {
        setSelectedGrades(prevState => ({ ...prevState, [studentId]: value }));
    };

    // Render component
    return (
        <div className="container mt-4">
            <h2>Grades for Class ID: {classId}, Teacher ID: {teacherId}, Discipline ID: {disciplineId}</h2>
            {error && <div className="alert alert-danger">Error: {error}</div>}
            {successMessage && <div className="alert alert-success">{successMessage}</div>}

            <Link to={`/admin/school/${classId}`} className="btn btn-secondary mb-4">Back to Class List</Link>

            <table className="table table-striped">
                <thead>
                <tr>
                    <th>Student</th>
                    <th>Grades</th>
                    <th>Current Grade</th>
                </tr>
                </thead>
                <tbody>
                {students.length > 0 ? (
                    students.map((student) => (
                        <tr key={student.id}>
                            <td className="name">{student.name}</td>
                            <td className="grades">
                                {grades[student.id] && grades[student.id].length > 0 ? (
                                    grades[student.id].map((grade) => (
                                        <span
                                            key={grade.id}
                                            className={`badge badge-${findGradeTypeById(grade.gradeTypeId).toLowerCase()}`}
                                            onClick={() => setVisibleGradeId(grade.id === visibleGradeId ? null : grade.id)}
                                        >
                                            {findGradeTypeById(grade.gradeTypeId)}
                                            {visibleGradeId === grade.id && (
                                                <>
                                                    <button className="btn btn-sm btn-info ml-2" onClick={() => handleEditGrade(grade)}>Edit</button>
                                                    <button className="btn btn-sm btn-danger ml-2" onClick={() => handleRemoveGrade(grade.id)}>Delete</button>
                                                </>
                                            )}
                                        </span>
                                    ))
                                ) : (
                                    <span>No grades</span>
                                )}
                            </td>
                            <td>
                                <form onSubmit={(event) => handleAddGrade(event, student.id, disciplineId)} className="add-grade-form">
                                    <div className="form-group">
                                        <select
                                            className="form-select"
                                            value={selectedGrades[student.id] || ''}
                                            onChange={(e) => handleGradeChange(student.id, e.target.value)}
                                            required
                                        >
                                            <option value="">Select Grade</option>
                                            {gradeTypes.map((grade) => (
                                                <option key={grade.id} value={grade.id}>{grade.gradeTypeEnum}</option>
                                            ))}
                                        </select>
                                        <button type="submit" className="btn btn-primary add-grade-btn">Add</button>
                                    </div>
                                </form>
                            </td>
                        </tr>
                    ))
                ) : (
                    <tr>
                        <td colSpan="5">No students available</td>
                    </tr>
                )}
                </tbody>
            </table>

            {editingGrade && (
                <form onSubmit={handleSaveGrade} className="edit-grade-form mt-4">
                    <h3>Edit Grade</h3>
                    <div className="mb-3">
                        <label htmlFor="gradeSelectEdit" className="form-label">Edit Grade:</label>
                        <select
                            id="gradeSelectEdit"
                            className="form-select"
                            value={selectedGrades[editingGrade.studentId] || ''}
                            onChange={(e) => handleGradeChange(editingGrade.studentId, e.target.value)}
                            required
                        >
                            <option value="">Select Grade</option>
                            {gradeTypes.map((grade) => (
                                <option key={grade.id} value={grade.id}>{grade.gradeTypeEnum}</option>
                            ))}
                        </select>
                    </div>
                    <button type="submit" className="btn btn-primary me-2">Save Grade</button>
                    <button type="button" className="btn btn-secondary" onClick={() => setEditingGrade(null)}>Cancel</button>
                </form>
            )}
        </div>
    );
};

export default StudentsGrades;
