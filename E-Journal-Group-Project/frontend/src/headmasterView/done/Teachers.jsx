import React, { useState, useEffect, useCallback } from 'react';
import axios from '../../axiosInstance';
import {Link, useParams} from 'react-router-dom';

const useTeachers = (schoolId) => {
    const [teachers, setTeachers] = useState([]);
    const [error, setError] = useState(null);

    const fetchTeachers = useCallback(async () => {
        try {
            const response = await axios.get(`/teacher/school-id/${schoolId}`);
            setTeachers(response.data);
        } catch (error) {
            setError(error);
            console.error('There was an error fetching the teachers!', error);
        }
    }, [schoolId]);

    useEffect(() => {
        fetchTeachers();
    }, [fetchTeachers]);

    return { teachers, error, fetchTeachers };
};

const Teachers = () => {
    const { headmasterId, schoolId } = useParams();
    const { teachers, error, fetchTeachers } = useTeachers(schoolId);
    const [editTeacher, setEditTeacher] = useState(null);
    const [newTeacher, setNewTeacher] = useState({ name: '', email: '' });

    return (
        <div className="container mt-4">
            <div className="mb-4">
                <h2>Teachers for School ID: {schoolId}</h2>
                {error && <div className="alert alert-danger">Error: {error.message}</div>}
            </div>

            <ul className="list-group">
                {teachers.length > 0 ? (
                    teachers.map((teacher) => (
                        <li key={teacher.id} className="list-group-item mb-3">
                            <div className="d-flex align-items-center justify-content-between">
                                <div className="teacher-info">
                                        <>
                                            <span className="d-block mb-1">{teacher.name}</span>
                                            <span>{teacher.email}</span>
                                        </>
                                </div>
                                    <Link to={`/headmaster/${headmasterId}/school/${schoolId}/teacher/${teacher.id}/qualifications`} className="btn btn-primary btn-sm me-1">Qualifications</Link>
                            </div>
                        </li>
                    ))
                ) : (
                    <li className="list-group-item">No teachers available</li>
                )}
            </ul>
        </div>
    );
};

export default Teachers;
