import React, { useState, useEffect, useCallback } from 'react';
import axios from '../axiosInstance';
import {Link, useParams} from 'react-router-dom';

const useSchoolClasses = (schoolId) => {
    const [schoolClasses, setSchoolClasses] = useState([]);
    const [error, setError] = useState(null);

    const fetchSchoolClasses = useCallback(async () => {
        try {
            const response = await axios.get(`/school-class/school-id/${schoolId}`);
            setSchoolClasses(response.data);
        } catch (error) {
            setError(error);
            console.error('There was an error fetching the school Classes!', error);
        }
    }, [schoolId]);

    useEffect(() => {
        fetchSchoolClasses();
    }, [fetchSchoolClasses]);

    return { schoolClasses, error, fetchSchoolClasses };
};

const SchoolClassList = () => {
    const { schoolId } = useParams();
    const { schoolClasses, error, fetchSchoolClasses } = useSchoolClasses(schoolId);
    const [editSchoolClass, setEditSchoolClass] = useState(null);
    const [newSchoolClass, setNewSchoolClass] = useState({ name: '', year: '' });

    const handleEditSchoolClass = (schoolClass) => {
        setEditSchoolClass({ ...schoolClass });
    };

    const handleChange = (e, setter) => {
        const { name, value } = e.target;
        setter((prev) => ({ ...prev, [name]: value }));
    };

    const handleSaveSchoolClass = async () => {
        try {
            await axios.put(`/school-class/${editSchoolClass.id}`, {
                ...editSchoolClass,
                schoolId: Number(schoolId)
            });
            setEditSchoolClass(null);
            fetchSchoolClasses();
        } catch (error) {
            console.error('Error saving school class:', error);
        }
    };

    const handleDeleteSchoolClass = async (schoolClassId) => {
        if (window.confirm('Are you sure you want to delete this school class?')) {
            try {
                await axios.delete(`/school-class/${schoolClassId}`);
                fetchSchoolClasses();
            } catch (error) {
                console.error('Error deleting school class:', error);
            }
        }
    };

    const handleAddSchoolClass = async () => {
        try {
            await axios.post('/school-class/', {
                ...newSchoolClass,
                schoolId: Number(schoolId)
            });
            setNewSchoolClass({ name: '', year: '' });
            fetchSchoolClasses();
        } catch (error) {
            console.error('Error adding schoolClass:', error);
        }
    };

    return (
        <div className="container mt-4">
            <div className="mb-4">
                <h2>School Classes for School ID: {schoolId}</h2>
                {error && <div className="alert alert-danger">Error: {error.message}</div>}
            </div>

            <div className="mb-4">
                <div className="card">
                    <h3 className="card-header">Add New School Class</h3>
                    <div className="card-body">
                        <div className="form-group">
                            <input
                                type="text"
                                className="form-control mb-3"
                                placeholder="Name"
                                name="name"
                                value={newSchoolClass.name}
                                onChange={(e) => handleChange(e, setNewSchoolClass)}
                            />
                        </div>
                        <div className="form-group">
                            <input
                                type="text"
                                className="form-control mb-3"
                                placeholder="Year"
                                name="year"
                                value={newSchoolClass.year}
                                onChange={(e) => handleChange(e, setNewSchoolClass)}
                            />
                        </div>
                        <button className="btn btn-success" onClick={handleAddSchoolClass}>
                            Add School Class
                        </button>
                    </div>
                </div>
            </div>

            <ul className="list-group">
                {schoolClasses.length > 0 ? (
                    schoolClasses.map((schoolClass) => (
                        <li key={schoolClass.id} className="list-group-item mb-3">
                            <div className="d-flex align-items-center justify-content-between">
                                <div className="schoolClass-info">
                                    {editSchoolClass && editSchoolClass.id === schoolClass.id ? (
                                        <>
                                            <div className="mb-2">
                                                <input
                                                    type="text"
                                                    className="form-control"
                                                    name="name"
                                                    value={editSchoolClass.name}
                                                    onChange={(e) => handleChange(e, setEditSchoolClass)}
                                                />
                                            </div>
                                            <div className="mb-2">
                                                <input
                                                    type="number" //TODO
                                                    className="form-control"
                                                    name="year"
                                                    value={editSchoolClass.year}
                                                    onChange={(e) => handleChange(e, setEditSchoolClass)}
                                                />
                                            </div>
                                        </>
                                    ) : (
                                        <>
                                            <span className="d-block mb-1">{schoolClass.name}</span>
                                            <span>{schoolClass.year}</span>
                                        </>
                                    )}
                                </div>
                                <div className="actions">
                                    {editSchoolClass && editSchoolClass.id === schoolClass.id ? (
                                        <button className="btn btn-sm btn-success me-1" onClick={handleSaveSchoolClass}>
                                            Save
                                        </button>
                                    ) : (
                                        <button className="btn btn-sm btn-success me-1" onClick={() => handleEditSchoolClass(schoolClass)}>
                                            Edit
                                        </button>
                                    )}
                                    <Link to={`/admin/school/${schoolId}/class/${schoolClass.id}`} className="btn btn-sm btn-primary me-1">Details</Link>
                                    <button className="btn btn-sm btn-danger me-1" onClick={() => handleDeleteSchoolClass(schoolClass.id)}>
                                        Delete
                                    </button>
                                </div>
                            </div>
                        </li>
                    ))
                ) : (
                    <li className="list-group-item">No school Classes available</li>
                )}
            </ul>
        </div>
    );
};

export default SchoolClassList;
