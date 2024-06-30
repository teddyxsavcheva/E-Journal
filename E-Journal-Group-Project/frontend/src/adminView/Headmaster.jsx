import React, { useState, useEffect } from 'react';
import axios from '../axiosInstance';
import { useParams, useNavigate } from 'react-router-dom';

const Headmaster = () => {
    const { schoolId } = useParams();
    const [headmaster, setHeadmaster] = useState(null);
    const [error, setError] = useState(null);
    const [editHeadmaster, setEditHeadmaster] = useState(null);
    const [newHeadmaster, setNewHeadmaster] = useState({ name: '', email: '' });
    const navigate = useNavigate();

    const fetchHeadmaster = async () => {
        try {
            const response = await axios.get(`/headmaster/school-id/${schoolId}`);
            if (response.data) {
                setHeadmaster(response.data);
            } else {
                setHeadmaster(null);
            }
        } catch (error) {
            if (error.response && error.response.status === 500) {
                setHeadmaster(null); // Handle 500 error gracefully by setting headmaster to null
            } else {
                setError(error);
                console.error('There was an error fetching the headmaster!', error);
            }
        }
    };

    useEffect(() => {
        fetchHeadmaster();
    }, [schoolId]);

    const handleChange = (e, setter) => {
        const { name, value } = e.target;
        setter((prev) => ({ ...prev, [name]: value }));
    };

    const handleSaveHeadmaster = async () => {
        try {
            await axios.put(`/headmaster/${editHeadmaster.id}`, {
                ...editHeadmaster,
                schoolId: Number(schoolId)
            });
            setEditHeadmaster(null);
            await fetchHeadmaster();
        } catch (error) {
            console.error('Error saving headmaster:', error);
        }
    };

    const handleDeleteHeadmaster = async (headmasterId) => {
        if (window.confirm('Are you sure you want to delete this headmaster?')) {
            try {
                await axios.delete(`/headmaster/${headmasterId}`);
                setHeadmaster(null);
                await fetchHeadmaster();
            } catch (error) {
                console.error('Error deleting headmaster:', error);
            }
        }
    };

    const handleAddHeadmaster = async () => {
        try {
            await axios.post('/headmaster/', {
                ...newHeadmaster,
                schoolId: Number(schoolId)
            });
            setNewHeadmaster({ name: '', email: '' });
            fetchHeadmaster();
        } catch (error) {
            console.error('Error adding headmaster:', error);
        }
    };

    return (
        <div className="container mt-4">
            <h2>Headmaster for School ID: {schoolId}</h2>
            {error && <div className="alert alert-danger">Error: {error.message}</div>}

            <div className="mb-4">
                <div className="card">
                    <h3 className="card-header">Headmaster</h3>
                    <div className="card-body">
                        {headmaster ? (
                            <div className="mb-2">
                                <div className="d-flex align-items-center justify-content-between">
                                    <div className="teacher-info">
                                        {editHeadmaster ? (
                                            <>
                                                <div className="mb-2">
                                                    <input
                                                        type="text"
                                                        className="form-control"
                                                        name="name"
                                                        value={editHeadmaster.name}
                                                        onChange={(e) => handleChange(e, setEditHeadmaster)}
                                                    />
                                                </div>
                                                <div className="mb-2">
                                                    <input
                                                        type="email"
                                                        className="form-control"
                                                        name="email"
                                                        value={editHeadmaster.email}
                                                        onChange={(e) => handleChange(e, setEditHeadmaster)}
                                                    />
                                                </div>
                                            </>
                                        ) : (
                                            <>
                                                <span className="d-block mb-1">{headmaster.name}</span>
                                                <span>{headmaster.email}</span>
                                            </>
                                        )}
                                    </div>
                                    <div className="actions">
                                        {editHeadmaster ? (
                                            <button className="btn btn-sm btn-success me-1" onClick={handleSaveHeadmaster}>
                                                Save
                                            </button>
                                        ) : (
                                            <button className="btn btn-sm btn-primary me-1" onClick={() => setEditHeadmaster(headmaster)}>
                                                Edit
                                            </button>
                                        )}
                                        <button className="btn btn-sm btn-danger me-1" onClick={() => handleDeleteHeadmaster(headmaster.id)}>
                                            Delete
                                        </button>
                                    </div>
                                </div>
                            </div>
                        ) : (
                            <div>
                                <h5>There is no headmaster. Please add a new one.</h5>
                                <div className="form-group">
                                    <input
                                        type="text"
                                        className="form-control mb-3"
                                        placeholder="Headmaster's Name"
                                        name="name"
                                        value={newHeadmaster.name}
                                        onChange={(e) => handleChange(e, setNewHeadmaster)}
                                    />
                                </div>
                                <div className="form-group">
                                    <input
                                        type="email"
                                        className="form-control mb-3"
                                        placeholder="Headmaster's Email"
                                        name="email"
                                        value={newHeadmaster.email}
                                        onChange={(e) => handleChange(e, setNewHeadmaster)}
                                    />
                                </div>
                                <button className="btn btn-success me-1" onClick={handleAddHeadmaster}>
                                    Add Headmaster
                                </button>
                            </div>
                        )}
                    </div>
                </div>
            </div>
            <button className="btn btn-secondary" onClick={() => navigate(-1)}>Back</button>
        </div>
    );
};

export default Headmaster;
