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
    const [editingGrade, setEditingGrade] = useState(null);
    const [visibleGradeIds, setVisibleGradeIds] = useState({}); // Use an object to track visibility by gradeId
    const [editingGrades, setEditingGrades] = useState({}); // State for editing grades
    const [addingGrades, setAddingGrades] = useState({}); // State for adding grades

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
    const fetchGrades = async (studentId) => {
        try {
            const response = await axios.get(`/students/${studentId}/discipline/${disciplineId}/grades`);
            setGrades(prevGrades => ({
                ...prevGrades,
                [studentId]: response.data
            }));
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
        fetchGradeTypes();
    }, [classId, teacherId, disciplineId]);

    // Effect to fetch grades whenever students or disciplineId change
    useEffect(() => {
        students.forEach(student => {
            fetchGrades(student.id);
        });
    }, [students, disciplineId]);

    // Find grade type by ID
    const findGradeTypeById = (id) => {
        const gradeType = gradeTypes.find(gradeType => gradeType.id === id);
        return gradeType ? gradeType.gradeTypeEnum : 'Unknown';
    };

    // Add grade to a student
    const handleAddGrade = async (event, studentId, disciplineId) => {
        event.preventDefault();
        try {
            const gradeTypeId = addingGrades[studentId];
            await axios.post(`/grades/`, { gradeTypeId, studentId, disciplineId });
            setSuccessMessage('Grade added successfully!');
            setTimeout(() => setSuccessMessage(''), 3000);
            setAddingGrades(prevState => ({ ...prevState, [studentId]: '' }));
            fetchGrades(studentId); // Refresh the grades list for the specific student
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

            // Update local state to remove the deleted grade
            const updatedGrades = { ...grades };
            Object.keys(updatedGrades).forEach(studentId => {
                updatedGrades[studentId] = updatedGrades[studentId].filter(grade => grade.id !== gradeId);
            });
            setGrades(updatedGrades);

            // Optionally, you can also update visibleGradeIds if needed
            setVisibleGradeIds(prevState => {
                const updatedVisibleGradeIds = { ...prevState };
                delete updatedVisibleGradeIds[gradeId];
                return updatedVisibleGradeIds;
            });
        } catch (error) {
            setError('Error removing grade');
            console.error('Error removing grade:', error);
        }
    };

    // Start editing a grade
    const handleEditGrade = (grade) => {
        setEditingGrade(grade);
        setEditingGrades(prevState => ({ ...prevState, [grade.studentId]: grade.gradeTypeId }));
        setVisibleGradeIds(prevState => ({ ...prevState, [grade.id]: true }));
    };

    // Save edited grade
    const handleSaveGrade = async (event) => {
        event.preventDefault();
        try {
            const response = await axios.get(`/grades/${editingGrade.id}`);
            const existingGrade = response.data;

            const updatedGrade = {
                ...existingGrade,
                gradeTypeId: parseInt(editingGrades[editingGrade.studentId], 10)
            };

            await axios.put(`/grades/${editingGrade.id}`, updatedGrade);

            setSuccessMessage('Grade updated successfully!');
            setTimeout(() => setSuccessMessage(''), 3000);

            // Clear state and update visibility
            setEditingGrades(prevState => ({ ...prevState, [editingGrade.studentId]: '' }));
            setEditingGrade(null);
            setVisibleGradeIds(prevState => ({ ...prevState, [editingGrade.id]: false }));

            fetchGrades(editingGrade.studentId);
        } catch (error) {
            setError('Error updating grade');
            console.error('Error updating grade:', error);
        }
    };


    // Handle grade selection change for editing
    const handleEditGradeChange = (studentId, value) => {
        setEditingGrades(prevState => ({ ...prevState, [studentId]: value }));
    };

    // Handle grade selection change for adding
    const handleAddGradeChange = (studentId, value) => {
        setAddingGrades(prevState => ({ ...prevState, [studentId]: value }));
    };

    // Toggle visibility of grade details
    const toggleGradeVisibility = (gradeId) => {
        setVisibleGradeIds(prevState => ({ ...prevState, [gradeId]: !prevState[gradeId] }));
    };

    const getBadgeClass = (gradeType) => {
        switch (gradeType) {
            case 'A': return 'badge-success'; // Green
            case 'B': return 'badge-primary'; // Blue
            case 'C': return 'badge-warning'; // Yellow
            case 'D': return 'badge-orange'; // Orange
            case 'F': return 'badge-danger'; // Red
            default: return 'badge-secondary'; // Default color
        }
    };

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
                    <th>Edit</th>
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
                                            className={`badge p-2 ${getBadgeClass(findGradeTypeById(grade.gradeTypeId))}`}
                                            onClick={() => toggleGradeVisibility(grade.id)}
                                        >
                                                {findGradeTypeById(grade.gradeTypeId)}
                                            {visibleGradeIds[grade.id] && (
                                                <>
                                                    <button className="btn btn-sm btn-light ml-2" onClick={() => handleEditGrade(grade)}>Edit</button>
                                                    <button className="btn btn-sm btn-light ml-2" onClick={() => handleRemoveGrade(grade.id)}>Delete</button>
                                                </>
                                            )}
                                            </span>
                                    ))
                                ) : (
                                    <span>No grades</span>
                                )}
                            </td>
                            <td className="edit">
                                {editingGrade && editingGrade.studentId === student.id && (
                                    <form onSubmit={handleSaveGrade} className="form-group">
                                        <select
                                            className="form-select"
                                            value={editingGrades[editingGrade.studentId] || ''}
                                            onChange={(e) => handleEditGradeChange(editingGrade.studentId, e.target.value)}
                                            required
                                        >
                                            <option value="">Select Grade</option>
                                            {gradeTypes.map((grade) => (
                                                <option key={grade.id} value={grade.id}>{grade.gradeTypeEnum}</option>
                                            ))}
                                        </select>
                                        <button type="submit" className="btn btn-primary mx-2">Save</button>
                                        <button type="button" className="btn btn-secondary" onClick={() => setEditingGrade(null)}>Cancel</button>
                                    </form>
                                )}
                            </td>
                            <td className="add">
                                <form onSubmit={(event) => handleAddGrade(event, student.id, disciplineId)} className="add-grade-form">
                                    <div className="form-group">
                                        <select
                                            className="form-select"
                                            value={addingGrades[student.id] || ''}
                                            onChange={(e) => handleAddGradeChange(student.id, e.target.value)}
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
                        <td colSpan="4">No students available</td>
                    </tr>
                )}
                </tbody>
            </table>
        </div>
    );
};

export default StudentsGrades;
