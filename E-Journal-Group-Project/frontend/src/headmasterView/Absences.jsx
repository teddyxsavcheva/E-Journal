import React, { useState, useEffect } from 'react';
import axios from '../axiosInstance';
import { useParams, Link } from 'react-router-dom';
import './done/Absences.css'; // Import the CSS file

const Absences = () => {
    const { classId, teacherId, disciplineId } = useParams();
    const [students, setStudents] = useState([]);
    const [absences, setAbsences] = useState({});
    const [absenceTypes, setAbsenceTypes] = useState([]);
    const [absenceStatuses, setAbsenceStatuses] = useState([]);
    const [error, setError] = useState(null);
    const [successMessage, setSuccessMessage] = useState('');
    const [editingAbsence, setEditingAbsence] = useState(null);
    const [visibleAbsenceIds, setVisibleAbsenceIds] = useState({});
    const [editingAbsences, setEditingAbsences] = useState({});
    const [addingAbsences, setAddingAbsences] = useState({});
    const [addingAbsencesStatus, setAddingAbsencesStatus] = useState({});
    const [excusedAbsencesCount, setExcusedAbsencesCount] = useState({});
    const [notExcusedAbsencesCount, setNotExcusedAbsencesCount] = useState({});

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

    // Fetch absences for each student
    const fetchAbsences = async (studentId) => {
        try {
            const response = await axios.get(`/students/${studentId}/discipline/${disciplineId}/absences`);
            const studentAbsences = response.data;

            // Update absences and counts for the specific student
            setAbsences(prevAbsences => ({
                ...prevAbsences,
                [studentId]: studentAbsences
            }));

            const counts = calculateAbsenceCounts(studentAbsences);

            setExcusedAbsencesCount(prevCounts => ({
                ...prevCounts,
                [studentId]: counts.excusedCount
            }));

            setNotExcusedAbsencesCount(prevCounts => ({
                ...prevCounts,
                [studentId]: counts.notExcusedCount
            }));
        } catch (error) {
            setError('Error fetching absences');
            console.error('Error fetching absences:', error);
        }
    };

    // Calculate absence counts based on status
    const calculateAbsenceCounts = (absences) => {
        const excusedCount = absences.filter(absence => absence.absenceStatusId === 1).length;
        const notExcusedCount = absences.filter(absence => absence.absenceStatusId === 2).length;
        return { excusedCount, notExcusedCount };
    };

    // Fetch all absence types
    const fetchAbsenceTypes = async () => {
        try {
            const response = await axios.get('/absenceTypes/');
            setAbsenceTypes(response.data);
        } catch (error) {
            setError('Error fetching absence types');
            console.error('Error fetching absence types:', error);
        }
    };

    // Fetch all absence statuses
    const fetchAbsenceStatuses = async () => {
        try {
            const response = await axios.get('/absenceStatuses/');
            setAbsenceStatuses(response.data);
        } catch (error) {
            setError('Error fetching absence statuses');
            console.error('Error fetching absence statuses:', error);
        }
    };

    // Fetch initial data on component mount
    useEffect(() => {
        fetchStudents();
        fetchAbsenceTypes();
        fetchAbsenceStatuses();
    }, [classId, teacherId, disciplineId]);

    // Effect to fetch absences whenever students or disciplineId change
    useEffect(() => {
        students.forEach(student => {
            fetchAbsences(student.id);
        });
    }, [students, disciplineId]);

    // Add absence to a student
    const handleAddAbsence = async (event, studentId) => {
        event.preventDefault();
        try {
            const absenceTypeId = addingAbsences[studentId];
            const absenceStatusId = addingAbsencesStatus[studentId];
            await axios.post(`/absences/`, { absenceTypeId, absenceStatusId, studentId, disciplineId });

            setSuccessMessage('Absence added successfully!');
            setTimeout(() => setSuccessMessage(''), 3000);

            // Clear the form state
            setAddingAbsences(prevState => ({ ...prevState, [studentId]: '' }));
            setAddingAbsencesStatus(prevState => ({ ...prevState, [studentId]: '' }));

            // Fetch updated absences and recalculate counts
            fetchAbsences(studentId);
        } catch (error) {
            setError('Error adding absence');
            console.error('Error adding absence:', error);
        }
    };

    // Remove absence from a student
    const handleRemoveAbsence = async (absenceId) => {
        try {
            await axios.delete(`/absences/${absenceId}`);

            setSuccessMessage('Absence removed successfully!');
            setTimeout(() => setSuccessMessage(''), 3000);

            // Update local state to remove the deleted absence
            const updatedAbsences = { ...absences };
            Object.keys(updatedAbsences).forEach(studentId => {
                updatedAbsences[studentId] = updatedAbsences[studentId].filter(absence => absence.id !== absenceId);
            });
            setAbsences(updatedAbsences);

            // Recalculate counts after removal
            students.forEach(student => {
                const counts = calculateAbsenceCounts(updatedAbsences[student.id] || []);
                setExcusedAbsencesCount(prevCounts => ({
                    ...prevCounts,
                    [student.id]: counts.excusedCount
                }));
                setNotExcusedAbsencesCount(prevCounts => ({
                    ...prevCounts,
                    [student.id]: counts.notExcusedCount
                }));
            });

            // Optionally, update visibleAbsenceIds if needed
            setVisibleAbsenceIds(prevState => {
                const updatedVisibleAbsenceIds = { ...prevState };
                delete updatedVisibleAbsenceIds[absenceId];
                return updatedVisibleAbsenceIds;
            });
        } catch (error) {
            setError('Error removing absence');
            console.error('Error removing absence:', error);
        }
    };

    // Start editing an absence
    const handleEditAbsence = (absence) => {
        setEditingAbsence(absence);
        setEditingAbsences(prevState => ({ ...prevState, [absence.studentId]: absence.absenceTypeId }));
        setVisibleAbsenceIds(prevState => ({ ...prevState, [absence.id]: true }));
    };

    // Save edited absence
    const handleSaveAbsence = async (event) => {
        event.preventDefault();
        try {
            const response = await axios.get(`/absences/${editingAbsence.id}`);
            const existingAbsence = response.data;

            const updatedAbsence = {
                ...existingAbsence,
                absenceTypeId: parseInt(editingAbsences[editingAbsence.studentId], 10)
            };

            await axios.put(`/absences/${editingAbsence.id}`, updatedAbsence);

            setSuccessMessage('Absence updated successfully!');
            setTimeout(() => setSuccessMessage(''), 3000);

            // Clear state and update visibility
            setEditingAbsences(prevState => ({ ...prevState, [editingAbsence.studentId]: '' }));
            setEditingAbsence(null);
            setVisibleAbsenceIds(prevState => ({ ...prevState, [editingAbsence.id]: false }));

            // Fetch updated absences and recalculate counts
            fetchAbsences(editingAbsence.studentId);
        } catch (error) {
            setError('Error updating absence');
            console.error('Error updating absence:', error);
        }
    };

    // Handle absence selection change for editing
    const handleEditAbsenceChange = (studentId, value) => {
        setEditingAbsences(prevState => ({ ...prevState, [studentId]: value }));
    };

    // Handle absence selection change for adding
    const handleAddAbsenceChange = (studentId, value) => {
        setAddingAbsences(prevState => ({ ...prevState, [studentId]: value }));
    };

    // Toggle visibility of absence details
    const toggleAbsenceVisibility = (absenceId) => {
        setVisibleAbsenceIds(prevState => ({ ...prevState, [absenceId]: !prevState[absenceId] }));
    };

    return (
        <div className="container mt-4">
            <h2>Absences for Class ID: {classId}, Teacher ID: {teacherId}, Discipline ID: {disciplineId}</h2>
            {error && <div className="alert alert-danger">Error: {error}</div>}
            {successMessage && <div className="alert alert-success">{successMessage}</div>}

            <Link to={`/admin/school/${classId}/classes`} className="btn btn-secondary mb-4">Back to Class List</Link>

            <table className="table table-striped">
                <thead>
                <tr>
                    <th>Student</th>
                    <th>Excused</th>
                    <th>Not Excused</th>
                    <th>Current Absence</th>
                </tr>
                </thead>
                <tbody>
                {students.length > 0 ? (
                    students.map((student) => (
                        <tr key={student.id}>
                            <td className="name">{student.name}</td>
                            <td>{excusedAbsencesCount[student.id] || 0}</td>
                            <td>{notExcusedAbsencesCount[student.id] || 0}</td>
                            <td className="add">
                                <form onSubmit={(event) => handleAddAbsence(event, student.id)} className="add-grade-form">
                                    <div className="form-group">
                                        <select
                                            className="form-select"
                                            value={addingAbsences[student.id] || ''}
                                            onChange={(e) => handleAddAbsenceChange(student.id, e.target.value)}
                                            required
                                        >
                                            <option value="">Select Absence Type</option>
                                            {absenceTypes.map((absence) => (
                                                <option key={absence.id} value={absence.id}>{absence.absenceTypeEnum}</option>
                                            ))}
                                        </select>

                                        <select
                                            className="form-select"
                                            value={addingAbsencesStatus[student.id] || ''}
                                            onChange={(e) => setAddingAbsencesStatus(prevState => ({ ...prevState, [student.id]: e.target.value }))}
                                            required
                                        >
                                            <option value="">Select Absence Status</option>
                                            {absenceStatuses.map((status) => (
                                                <option key={status.id} value={status.id}>{status.absenceStatusEnum}</option>
                                            ))}
                                        </select>

                                        <button type="submit" className="btn btn-primary add-absence-btn">Add</button>
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

export default Absences;
