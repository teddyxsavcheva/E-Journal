import React, { useState, useEffect } from 'react';
import axios from '../axiosInstance';
import { useParams } from 'react-router-dom';

const StudentGrades = () => {
    const { classId, teacherId, disciplineId } = useParams();
    const [students, setStudents] = useState([]);
    const [grades, setGrades] = useState({});
    const [gradeTypes, setGradeTypes] = useState({});
    const [error, setError] = useState(null);

    const fetchGrades = async (studentId) => {
        try {
            const response = await axios.get(`/students/${studentId}/discipline/${disciplineId}/grades`);
            setGrades(prevGrades => ({ ...prevGrades, [studentId]: response.data }));
        } catch (error) {
            setError(error);
        }
    };

    const fetchGradeTypes = async () => {
        try {
            const response = await axios.get('/gradeTypes/');
            const gradeTypesMap = response.data.reduce((acc, gradeType) => {
                acc[gradeType.id] = gradeType.name;
                return acc;
            }, {});
            setGradeTypes(gradeTypesMap);
        } catch (error) {
            setError(error);
        }
    };

    useEffect(() => {
        const fetchStudents = async () => {
            try {
                const response = await axios.get(`/students/school-class/${classId}`);
                setStudents(response.data);
            } catch (error) {
                setError(error);
            }
        };

        fetchStudents();
        fetchGradeTypes();
    }, [classId, disciplineId]);

    useEffect(() => {
        if (students.length > 0) {
            students.forEach(student => {
                fetchGrades(student.id);
            });
        }
    }, [students]);

    return (
        <div className="container mt-4">
            <h2 className="mb-4">Student Grades</h2>
            {error && <div className="alert alert-danger">Error: {error.message}</div>}
            <ul className="list-group">
                {students.length > 0 ? (
                    students.map(student => (
                        <li key={student.id} className="list-group-item">
                            <h5>{student.name}</h5>
                            <ul>
                                {grades[student.id] && grades[student.id].length > 0 ? (
                                    grades[student.id].map(grade => (
                                        <li key={grade.id}>
                                            Date: {grade.dateOfIssue}, Grade Type: {gradeTypes[grade.gradeTypeId] || grade.gradeTypeId}
                                        </li>
                                    ))
                                ) : (
                                    <li>No grades available</li>
                                )}
                            </ul>
                        </li>
                    ))
                ) : (
                    <li className="list-group-item">No students available</li>
                )}
            </ul>
        </div>
    );
};

export default StudentGrades;
