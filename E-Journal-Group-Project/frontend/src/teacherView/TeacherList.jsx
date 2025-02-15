import React, { useState, useEffect, useCallback } from 'react';
import axios from '../axiosInstance';
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

const TeacherList = () => {
    const { schoolId } = useParams();
    const { teachers, error, fetchTeachers } = useTeachers(schoolId);
    const [editTeacher, setEditTeacher] = useState(null);
    const [newTeacher, setNewTeacher] = useState({ name: '', email: '' });

    const handleEditTeacher = (teacher) => {
        setEditTeacher({ ...teacher });
    };

    const handleChange = (e, setter) => {
        const { name, value } = e.target;
        setter((prev) => ({ ...prev, [name]: value }));
    };

    const handleSaveTeacher = async () => {
        try {
            await axios.put(`/teacher/${editTeacher.id}`, {
                ...editTeacher,
                schoolId: Number(schoolId)
            });
            setEditTeacher(null);
            fetchTeachers();
        } catch (error) {
            console.error('Error saving teacher:', error);
        }
    };

    const handleDeleteTeacher = async (teacherId) => {
        if (window.confirm('Are you sure you want to delete this teacher?')) {
            try {
                await axios.delete(`/teacher/${teacherId}`);
                fetchTeachers();
            } catch (error) {
                console.error('Error deleting teacher:', error);
            }
        }
    };

    const handleAddTeacher = async () => {
        try {
            await axios.post('/teacher/', {
                ...newTeacher,
                schoolId: Number(schoolId)
            });
            setNewTeacher({ name: '', email: '' });
            fetchTeachers();
        } catch (error) {
            console.error('Error adding teacher:', error);
        }
    };

    return (
        <div className="container mt-4">
            <div className="mb-4">
                <h2>Teachers for School ID: {schoolId}</h2>
                {error && <div className="alert alert-danger">Error: {error.message}</div>}
            </div>

            <div className="mb-4">
                <div className="card">
                    <h3 className="card-header">Add New Teacher</h3>
                    <div className="card-body">
                        <div className="form-group">
                            <input
                                type="text"
                                className="form-control mb-3"
                                placeholder="Teacher's Name"
                                name="name"
                                value={newTeacher.name}
                                onChange={(e) => handleChange(e, setNewTeacher)}
                            />
                        </div>
                        <div className="form-group">
                            <input
                                type="email"
                                className="form-control mb-3"
                                placeholder="Teacher's Email"
                                name="email"
                                value={newTeacher.email}
                                onChange={(e) => handleChange(e, setNewTeacher)}
                            />
                        </div>
                        <button className="btn btn-success me-1" onClick={handleAddTeacher}>
                            Add Teacher
                        </button>
                    </div>
                </div>
            </div>

            <ul className="list-group">
                {teachers.length > 0 ? (
                    teachers.map((teacher) => (
                        <li key={teacher.id} className="list-group-item mb-3">
                            <div className="d-flex align-items-center justify-content-between">
                                <div className="teacher-info">
                                    {editTeacher && editTeacher.id === teacher.id ? (
                                        <>
                                            <div className="mb-2">
                                                <input
                                                    type="text"
                                                    className="form-control"
                                                    name="name"
                                                    value={editTeacher.name}
                                                    onChange={(e) => handleChange(e, setEditTeacher)}
                                                />
                                            </div>
                                            <div className="mb-2">
                                                <input
                                                    type="email"
                                                    className="form-control"
                                                    name="email"
                                                    value={editTeacher.email}
                                                    onChange={(e) => handleChange(e, setEditTeacher)}
                                                />
                                            </div>
                                        </>
                                    ) : (
                                        <>
                                            <span className="d-block mb-1">{teacher.name}</span>
                                            <span>{teacher.email}</span>
                                        </>
                                    )}
                                </div>
                                <div className="actions">
                                    {editTeacher && editTeacher.id === teacher.id ? (
                                        <button className="btn btn-sm btn-success me-1" onClick={handleSaveTeacher}>
                                            Save
                                        </button>
                                    ) : (
                                        <button className="btn btn-sm btn-success me-1" onClick={() => handleEditTeacher(teacher)}>
                                            Edit
                                        </button>
                                    )}
                                    <Link to={`/admin/school/${schoolId}/teacher/${teacher.id}/qualifications`} className="btn btn-primary btn-sm me-1">Qualifications</Link>
                                    <button className="btn btn-sm btn-danger me-1" onClick={() => handleDeleteTeacher(teacher.id)}>
                                        Delete
                                    </button>
                                </div>
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

export default TeacherList;
