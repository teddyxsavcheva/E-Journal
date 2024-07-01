import React, { useState, useEffect } from 'react';
import axios from '../../axiosInstance';
import { useParams, Link } from 'react-router-dom';
import './Grades.css'; // Import the CSS file

const Grades = () => {
    const { classId, schoolId, teacherId, disciplineId, headmasterId } = useParams();
    const [students, setStudents] = useState([]);
    const [grades, setGrades] = useState({});
    const [gradeTypes, setGradeTypes] = useState([]);
    const [error, setError] = useState(null);
    const [successMessage, setSuccessMessage] = useState('');

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

            <Link to={`/headmaster/${headmasterId}/school/${schoolId}/classes`} className="btn btn-secondary mb-4">Back to Class List</Link>

            <table className="table table-striped">
                <thead>
                <tr>
                    <th>Student</th>
                    <th>Grades</th>
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
                                        <span className={`badge p-2 ${getBadgeClass(findGradeTypeById(grade.gradeTypeId))}`}>
                                            {findGradeTypeById(grade.gradeTypeId)}
                                        </span>
                                    ))
                                ) : (
                                    <span>No grades</span>
                                )}
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

export default Grades;
