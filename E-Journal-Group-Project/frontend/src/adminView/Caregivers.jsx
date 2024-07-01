import React, { useState, useEffect, useCallback } from 'react';
import axios from '../axiosInstance';
import { useParams, Link } from 'react-router-dom';

const useCaregivers = (studentId) => {
    const [caregivers, setCaregivers] = useState([]);
    const [error, setError] = useState(null);

    const fetchCaregivers = useCallback(async () => {
        try {
            const response = await axios.get(`caregivers/student/${studentId}`);
            setCaregivers(response.data);
        } catch (error) {
            setError(error);
            console.error('There was an error fetching the caregivers!', error);
        }
    }, [studentId]);

    useEffect(() => {
        fetchCaregivers();
    }, [fetchCaregivers]);

    return { caregivers, error, fetchCaregivers };
};

const SchoolClassDetails = () => {
    const { schoolId, classId, studentId } = useParams();
    const { caregivers, error, fetchCaregivers } = useCaregivers(studentId);
    const [editCaregiver, setEditCaregiver] = useState(null);
    const [newCaregiver, setNewCaregiver] = useState({ name: '', email: '' });

    const handleEditCaregiver = (caregiver) => {
        setEditCaregiver({ ...caregiver });
    };

    const handleChange = (e, setter) => {
        const { name, value } = e.target;
        setter((prev) => ({ ...prev, [name]: value }));
    };

    const handleSaveCaregiver = async () => {
        try {
            await axios.put(`/caregivers/${editCaregiver.id}`, {
                ...editCaregiver,
            });
            setEditCaregiver(null);
            fetchCaregivers();
        } catch (error) {
            console.error('Error saving caregiver:', error);
        }
    };

    const handleDeleteCaregiver = async (caregiverId) => {
        if (window.confirm('Are you sure you want to delete this caregiver?')) {
            try {
                await axios.delete(`/caregivers/${caregiverId}/students/${studentId}`);
                fetchCaregivers();
            } catch (error) {
                console.error('Error deleting caregiver: ', error);
            }
        }
    };

    const handleAddCaregiver = async () => {
        try {
            // Fetch all caregivers
            const response = await axios.get('/caregivers/');
            const existingCaregiver = response.data.find(caregiver => caregiver.email === newCaregiver.email);

            if (existingCaregiver) {
                console.log("exist");
                // If caregiver with the same email already exists, associate them with the student
                await axios.post(`/students/${studentId}/caregivers/${existingCaregiver.id}`);
            } else {
                console.log("not exist")
                // If caregiver doesn't exist, create a new one
                const newCaregiverResponse = await axios.post(`/caregivers/`, {
                    ...newCaregiver,
                });
                const newCaregiverId = newCaregiverResponse.data.id;
                // Associate the new caregiver with the student
                await axios.post(`/students/${studentId}/caregivers/${newCaregiverId}`);
            }

            // Clear the input fields after successful addition
            setNewCaregiver({ name: '', email: '' });
            // Fetch caregivers again to update the list
            fetchCaregivers();
        } catch (error) {
            console.error("Error adding caregiver: ", error);
        }
    };

    return (
        <div className="container mt-4">
            <div className="mb-4">
                {error && <div className="alert alert-danger">Error: {error.message}</div>}
            </div>

            <div className="mb-4">
                <div className="card">
                    <h3 className="card-header">Add New Caregiver</h3>
                    <div className="card-body">
                        <div className="form-group">
                            <input
                                type="text"
                                className="form-control mb-3"
                                placeholder="Caregiver's Name"
                                name="name"
                                value={newCaregiver.name}
                                onChange={(e) => handleChange(e, setNewCaregiver)}
                            />
                        </div>
                        <div className="form-group">
                            <input
                                type="email"
                                className="form-control mb-3"
                                placeholder="Caregiver's Email"
                                name="email"
                                value={newCaregiver.email}
                                onChange={(e) => handleChange(e, setNewCaregiver)}
                            />
                        </div>
                        <button
                            className="btn btn-success me-1"
                            onClick={handleAddCaregiver}
                            disabled={caregivers.length >= 2}
                        >
                            Add Caregiver
                        </button>
                        {caregivers.length >= 2 && (
                            <div className="alert alert-warning mt-3">
                                You cannot add more than 2 caregivers.
                            </div>
                        )}
                    </div>
                </div>
            </div>

            <ul className="list-group">
                {caregivers.length > 0 ? (
                    caregivers.map((caregiver) => (
                        <li key={caregiver.id} className="list-group-item mb-3">
                            <div className="d-flex align-items-center justify-content-between">
                                <div className="caregiver-info">
                                    {editCaregiver && editCaregiver.id === caregiver.id ? (
                                        <>
                                            <div className="mb-2">
                                                <input
                                                    type="text"
                                                    className="form-control"
                                                    name="name"
                                                    value={editCaregiver.name}
                                                    onChange={(e) => handleChange(e, setEditCaregiver)}
                                                />
                                            </div>
                                            <div className="mb-2">
                                                <input
                                                    type="email"
                                                    className="form-control"
                                                    name="email"
                                                    value={editCaregiver.email}
                                                    onChange={(e) => handleChange(e, setEditCaregiver)}
                                                />
                                            </div>
                                        </>
                                    ) : (
                                        <>
                                            <span className="d-block mb-1">{caregiver.name}</span>
                                            <span>{caregiver.email}</span>
                                        </>
                                    )}
                                </div>
                                <div className="actions d-flex">
                                    {editCaregiver && editCaregiver.id === caregiver.id ? (
                                        <div>
                                            <button className="btn btn-sm btn-success me-1" onClick={handleSaveCaregiver}>
                                                Save
                                            </button>
                                            <button className="btn btn-sm btn-secondary me-1" onClick={() => { setEditCaregiver(null); }}>Cancel</button>
                                        </div>
                                    ) : (
                                        <button className="btn btn-sm btn-success me-1" onClick={() => handleEditCaregiver(caregiver)}>
                                            Edit
                                        </button>
                                    )}
                                    <button className="btn btn-sm btn-danger me-1" onClick={() => handleDeleteCaregiver(caregiver.id)}>
                                        Delete
                                    </button>
                                </div>
                            </div>
                        </li>
                    ))
                ) : (
                    <li className="list-group-item">No caregivers available</li>
                )}
            </ul>
        </div>
    );
};

export default SchoolClassDetails;
