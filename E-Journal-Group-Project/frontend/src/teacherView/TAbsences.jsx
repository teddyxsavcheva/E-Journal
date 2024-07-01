import React, { useState, useEffect } from 'react';
import axios from '../axiosInstance';
import { useParams, Link } from 'react-router-dom';
import './Absences.css'; // Import the CSS file

const TAbsences = () => {
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
    const [editingAbsenceStatus, setEditingAbsenceStatus] = useState({});
    const [addingAbsences, setAddingAbsences] = useState({});
    const [addingAbsencesStatus, setAddingAbsencesStatus] = useState({});
    const [excusedAbsencesCount, setExcusedAbsencesCount] = useState({});
    const [notExcusedAbsencesCount, setNotExcusedAbsencesCount] = useState({});
    const [selectedAbsences, setSelectedAbsences] = useState({});

    useEffect(() => {
        fetchStudents();
        fetchAbsenceTypes();
        fetchAbsenceStatuses();
    }, [classId, teacherId, disciplineId]);

    useEffect(() => {
        if (students.length > 0) {
            students.forEach(student => {
                fetchAbsences(student.id);
            });
        }
    }, [students, disciplineId]);

    const fetchStudents = async () => {
        try {
            const response = await axios.get(`/students/school-class/${classId}`);
            setStudents(response.data);
        } catch (error) {
            setError('Error fetching students');
            console.error('Error fetching students:', error);
        }
    };

    const fetchAbsences = async (studentId) => {
        try {
            const response = await axios.get(`/students/${studentId}/discipline/${disciplineId}/absences`);
            const studentAbsences = response.data;

            setAbsences(prevAbsences => {
                const updatedAbsences = { ...prevAbsences, [studentId]: studentAbsences };
                fetchAbsenceCount(studentId);

                return updatedAbsences;
            });
        } catch (error) {
            setError('Error fetching absences');
            console.error('Error fetching absences:', error);
        }
    };

    const fetchAbsenceCount = async (studentId) => {
        try {
            const response = await axios.get(`/students/${studentId}/disciplines/${disciplineId}/absences`);
            const [excusedCount, notExcusedCount] = response.data;

            setExcusedAbsencesCount(prevCounts => ({
                ...prevCounts,
                [studentId]: excusedCount
            }));

            setNotExcusedAbsencesCount(prevCounts => ({
                ...prevCounts,
                [studentId]: notExcusedCount
            }));
        } catch (error) {
            setError('Error fetching absence counts');
            console.error('Error fetching absence counts:', error);
        }
    };

    const fetchAbsenceTypes = async () => {
        try {
            const response = await axios.get('/absenceTypes/');
            setAbsenceTypes(response.data);
        } catch (error) {
            setError('Error fetching absence types');
            console.error('Error fetching absence types:', error);
        }
    };

    const fetchAbsenceStatuses = async () => {
        try {
            const response = await axios.get('/absenceStatuses/');
            setAbsenceStatuses(response.data);
        } catch (error) {
            setError('Error fetching absence statuses');
            console.error('Error fetching absence statuses:', error);
        }
    };

    const handleAddAbsence = async (event, studentId) => {
        event.preventDefault();
        try {
            const absenceTypeId = addingAbsences[studentId];
            const absenceStatusId = addingAbsencesStatus[studentId];
            await axios.post(`/absences/`, { absenceTypeId, absenceStatusId, studentId, disciplineId });

            setSuccessMessage('Absence added successfully!');
            setTimeout(() => setSuccessMessage(''), 8000);

            setAddingAbsences(prevState => ({ ...prevState, [studentId]: '' }));
            setAddingAbsencesStatus(prevState => ({ ...prevState, [studentId]: '' }));

            fetchAbsences(studentId);
            fetchAbsenceCount(studentId);
        } catch (error) {
            setError('Error adding absence');
            console.error('Error adding absence:', error);
        }
    };

    const handleRemoveAbsence = async (absenceId, studentId) => {
        try {
            await axios.delete(`/absences/${absenceId}`);

            setSuccessMessage('Absence removed successfully!');
            setTimeout(() => setSuccessMessage(''), 8000);

            fetchAbsences(studentId);
            fetchAbsenceCount(studentId);

            setVisibleAbsenceIds(prevState => {
                const updatedVisibleAbsenceIds = { ...prevState };
                delete updatedVisibleAbsenceIds[absenceId];
                return updatedVisibleAbsenceIds;
            });

            setSelectedAbsences({});
        } catch (error) {
            setError('Error removing absence');
            console.error('Error removing absence:', error);
        }
    };

    const handleEditAbsence = (absence) => {
        setEditingAbsence(absence);
        setEditingAbsences(prevState => ({ ...prevState, [absence.studentId]: absence.absenceTypeId }));
        setEditingAbsenceStatus(prevState => ({ ...prevState, [absence.studentId]: absence.absenceStatusId }));
        setVisibleAbsenceIds(prevState => ({ ...prevState, [absence.id]: true }));
    };

    const handleSaveAbsence = async (event) => {
        event.preventDefault();
        try {
            const updatedAbsence = {
                ...editingAbsence,
                absenceTypeId: parseInt(editingAbsences[editingAbsence.studentId], 10),
                absenceStatusId: parseInt(editingAbsenceStatus[editingAbsence.studentId], 10)
            };

            await axios.put(`/absences/${editingAbsence.id}`, updatedAbsence);

            setSuccessMessage('Absence updated successfully!');
            setTimeout(() => setSuccessMessage(''), 8000);

            setEditingAbsences(prevState => ({ ...prevState, [editingAbsence.studentId]: '' }));
            setEditingAbsenceStatus(prevState => ({ ...prevState, [editingAbsence.studentId]: '' }));
            setEditingAbsence(null);
            setVisibleAbsenceIds(prevState => ({ ...prevState, [editingAbsence.id]: false }));

            fetchAbsences(editingAbsence.studentId);
            setSelectedAbsences({});
        } catch (error) {
            setError('Error updating absence');
            console.error('Error updating absence:', error);
        }
    };

    const handleEditAbsenceChange = (studentId, value) => {
        setEditingAbsences(prevState => ({ ...prevState, [studentId]: value }));
    };

    const handleEditAbsenceStatusChange = (studentId, value) => {
        setEditingAbsenceStatus(prevState => ({ ...prevState, [studentId]: value }));
    };

    const handleAddAbsenceChange = (studentId, value) => {
        setAddingAbsences(prevState => ({ ...prevState, [studentId]: value }));
    };

    const toggleAbsenceVisibility = (absenceId) => {
        setVisibleAbsenceIds(prevState => ({ ...prevState, [absenceId]: !prevState[absenceId] }));
    };

    const handleAbsenceClick = (studentId, absenceStatusId) => {
        const filteredAbsences = absences[studentId].filter(absence => absence.absenceStatusId === absenceStatusId);
        setSelectedAbsences(prevState => ({
            ...prevState,
            [studentId]: prevState[studentId]?.[absenceStatusId] ? null : { [absenceStatusId]: filteredAbsences }
        }));
    };

    const getAbsenceTypeName = (absenceTypeId) => {
        const absenceType = absenceTypes.find(type => type.id == absenceTypeId);
        return absenceType ? absenceType.absenceTypeEnum : 'Unknown Type';
    };

    const getAbsenceStatusName = (absenceStatusId) => {
        const absenceStatus = absenceStatuses.find(status => status.id == absenceStatusId);
        return absenceStatus ? absenceStatus.absenceStatusEnum : 'Unknown Status';
    };

    return (
        <div className="container mt-4">
            {error && <div className="alert alert-danger">Error: {error}</div>}
            {successMessage && <div className="alert alert-success">{successMessage}</div>}
            <h1>Absences</h1>
            <table className="table table-striped">
                <thead>
                <tr>
                    <th>Student</th>
                    <th>Excused</th>
                    <th>Not Excused</th>
                    <th>Add Absence</th>
                </tr>
                </thead>
                <tbody>
                {students.length > 0 ? (
                    students.map((student) => (
                        <React.Fragment key={student.id}>
                            <tr>
                                <td className="student m-3">{student.name}</td>
                                <td className="name" onClick={() => handleAbsenceClick(student.id, 1)} style={{ cursor: 'pointer', color: 'blue', textDecoration: 'underline' }}>
                                    {excusedAbsencesCount[student.id] || 0}
                                </td>
                                <td className="name" onClick={() => handleAbsenceClick(student.id, 2)} style={{ cursor: 'pointer', color: 'red', textDecoration: 'underline' }}>
                                    {notExcusedAbsencesCount[student.id] || 0}
                                </td>
                                <td className="add">
                                    <form onSubmit={(event) => handleAddAbsence(event, student.id)} className="add-grade-form">
                                        <div className="form-group">
                                            <select
                                                className="form-select m-1"
                                                value={addingAbsences[student.id] || ''}
                                                onChange={(e) => handleAddAbsenceChange(student.id, e.target.value)}
                                                required
                                            >
                                                <option value="">Select Type</option>
                                                {absenceTypes.map((absence) => (
                                                    <option key={absence.id} value={absence.id}>{absence.absenceTypeEnum}</option>
                                                ))}
                                            </select>

                                            <select
                                                className="form-select m-1"
                                                value={addingAbsencesStatus[student.id] || ''}
                                                onChange={(e) => setAddingAbsencesStatus(prevState => ({ ...prevState, [student.id]: e.target.value }))}
                                                required
                                            >
                                                <option value="">Select Status</option>
                                                {absenceStatuses.map((status) => (
                                                    <option key={status.id} value={status.id}>{status.absenceStatusEnum}</option>
                                                ))}
                                            </select>

                                            <button type="submit" className="btn btn-success add-absence-btn">Add</button>
                                        </div>
                                    </form>
                                </td>
                            </tr>
                            {selectedAbsences[student.id] && selectedAbsences[student.id][1] && (
                                <tr className="absence-details-row">
                                    <td colSpan="4">
                                        <ul className="absence-list">
                                            {selectedAbsences[student.id][1].map(absence => (
                                                <li className="absence-item" key={absence.id}>
                                                    <div className="absence-info">
                                                        {getAbsenceTypeName(absence.absenceTypeId)} : {absence.dateOfIssue}
                                                    </div>
                                                    {/*<div className="absence-actions">*/}
                                                    {/*    {!visibleAbsenceIds[absence.id] && (*/}
                                                    {/*        <>*/}
                                                    {/*            <button onClick={() => handleEditAbsence(absence)} className="btn btn-primary btn-sm ms-2">Edit</button>*/}
                                                    {/*            /!*<button onClick={() => handleRemoveAbsence(absence.id, student.id)} className="btn btn-danger btn-sm ms-2">Remove</button>*!/*/}
                                                    {/*        </>*/}
                                                    {/*    )}*/}
                                                    {/*</div>*/}
                                                    {visibleAbsenceIds[absence.id] && (
                                                        <form onSubmit={handleSaveAbsence} className="edit-absence-form mt-2">
                                                            <select
                                                                className="form-select"
                                                                value={editingAbsences[absence.studentId] || ''}
                                                                onChange={(e) => handleEditAbsenceChange(absence.studentId, e.target.value)}
                                                                required
                                                            >
                                                                <option value="">Select Absence Type</option>
                                                                {absenceTypes.map((absenceType) => (
                                                                    <option key={absenceType.id} value={absenceType.id}>{absenceType.absenceTypeEnum}</option>
                                                                ))}
                                                            </select>

                                                            <select
                                                                className="form-select"
                                                                value={editingAbsenceStatus[absence.studentId] || ''}
                                                                onChange={(e) => handleEditAbsenceStatusChange(absence.studentId, e.target.value)}
                                                                required
                                                            >
                                                                <option value="">Select Absence Status</option>
                                                                {absenceStatuses.map((status) => (
                                                                    <option key={status.id} value={status.id}>{status.absenceStatusEnum}</option>
                                                                ))}
                                                            </select>

                                                            <button type="submit" className="btn btn-success btn-sm">Save</button>
                                                        </form>
                                                    )}
                                                </li>
                                            ))}
                                        </ul>
                                    </td>
                                </tr>
                            )}

                            {selectedAbsences[student.id] && selectedAbsences[student.id][2] && (
                                <tr className="absence-details-row">
                                    <td colSpan="4">
                                        <ul className="absence-list">
                                            {selectedAbsences[student.id][2].map(absence => (
                                                <li className="absence-item" key={absence.id}>
                                                    <div className="absence-info">
                                                        {getAbsenceTypeName(absence.absenceTypeId)} : {absence.dateOfIssue}
                                                    </div>
                                                    <div className="absence-actions">
                                                        {!visibleAbsenceIds[absence.id] && (
                                                            <button onClick={() => handleEditAbsence(absence)} className="btn btn-primary btn-sm ms-2">Excuse</button>
                                                        )}
                                                    </div>
                                                    {visibleAbsenceIds[absence.id] && (
                                                        <form onSubmit={handleSaveAbsence} className="edit-absence-form mt-2">

                                                            <select
                                                                className="form-select"
                                                                value={editingAbsenceStatus[absence.studentId] || ''}
                                                                onChange={(e) => handleEditAbsenceStatusChange(absence.studentId, e.target.value)}
                                                                required
                                                            >
                                                                <option value="">Select Absence Status</option>
                                                                {absenceStatuses.map((status) => (
                                                                    <option key={status.id} value={status.id}>{status.absenceStatusEnum}</option>
                                                                ))}
                                                            </select>

                                                            <button type="submit" className="btn btn-success btn-sm">Save</button>
                                                        </form>
                                                    )}
                                                </li>
                                            ))}
                                        </ul>
                                    </td>
                                </tr>
                            )}

                        </React.Fragment>
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

export default TAbsences;
