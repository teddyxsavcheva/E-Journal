import React, { useState, useEffect, useCallback } from 'react';
import axios from '../axiosInstance';
import { useParams, Link } from 'react-router-dom';

const useStudents = (classId) => {
    const [students, setStudents] = useState([]);
    const [error, setError] = useState(null);

    const fetchStudents = useCallback(async () => {
        try {
            const response = await axios.get(`students/school-class/${classId}`);
            setStudents(response.data);
        } catch (error) {
            setError(error);
            console.error('There was an error fetching the students!', error);
        }
    }, [classId]);

    useEffect(() => {
        fetchStudents();
    }, [fetchStudents]);

    return { students, error, fetchStudents };
};

const SchoolClass = () => {
    const { schoolId, classId, headmasterId } = useParams();
    const { students, error , fetchStudents} = useStudents(classId);

    return (
        <div className="container mt-4">
                <h1>Students</h1>
            <div className="mb-4 d-flex justify-content-between align-items-center">
                <Link to={`/headmaster/${headmasterId}/school/${schoolId}/class/${classId}/curriculum`}
                      className="btn btn-primary w-100">
                    Curriculum
                </Link>
            </div>
            {error &&
                <div className="alert alert-danger text-center">
                    Error: {error.message}
                </div>
            }

            <ul className="list-group">
                {students.length > 0 ? (
                    students.map((student) => (
                        <li key={student.id} className="list-group-item mb-3">
                            <div className="d-flex align-items-center justify-content-between">
                                <div className="student-info">
                                    <span className="d-block mb-1">{student.numberInClass}. {student.name}</span>
                                </div>
                                <div className="actions">
                                    <Link to={`/headmaster/${headmasterId}/school/${schoolId}/class/${classId}/student/${student.id}/caregivers`}
                                          className="btn btn-primary btn-sm me-1">
                                        Caregivers
                                    </Link>
                                </div>
                            </div>
                        </li>
                    ))
                ) : (
                    <li className="list-group-item">No students available</li>
                )}
            </ul>
        </div>
    );
};

export default SchoolClass;
