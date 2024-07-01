import React, { useState, useEffect } from 'react';
import axios from '../axiosInstance';
import { useParams, Link } from 'react-router-dom';
import './Absences.css'; // Import the CSS file

const Absences = () => {
    const { classId, teacherId, disciplineId } = useParams();
    const [students, setStudents] = useState([]);
    const [absences, setAbsences] = useState({});
    const [absenceTypes, setAbsenceTypes] = useState([]);
    const [absenceStatuses, setAbsenceStatuses] = useState([]);
    const [error, setError] = useState(null);
    const [successMessage, setSuccessMessage] = useState('');
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

            <Link to={`/admin/school/${classId}/classes`} className="btn btn-secondary mb-4">Back to Class List</Link>

            <table className="table table-striped">
                <thead>
                <tr>
                    <th>Student</th>
                    <th>Excused</th>
                    <th>Not Excused</th>
                    {/*<th>Current Absence</th>*/}
                </tr>
                </thead>
                <tbody>
                {students.length > 0 ? (
                    students.map((student) => (
                        <React.Fragment key={student.id}>
                            <tr>
                                <td className="student m-3">{student.numberInClass}. {student.name}</td>
                                <td className="name" onClick={() => handleAbsenceClick(student.id, 1)} style={{ cursor: 'pointer', color: 'blue', textDecoration: 'underline' }}>
                                    {excusedAbsencesCount[student.id] || 0}
                                </td>
                                <td className="name" onClick={() => handleAbsenceClick(student.id, 2)} style={{ cursor: 'pointer', color: 'red', textDecoration: 'underline' }}>
                                    {notExcusedAbsencesCount[student.id] || 0}
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

export default Absences;
