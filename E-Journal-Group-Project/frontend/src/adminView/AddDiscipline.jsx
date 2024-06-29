import React, { useState, useEffect, useCallback } from 'react';
import axios from '../axiosInstance';
import { Link } from 'react-router-dom';

const useDisciplines = () => {
    const [disciplines, setDisciplines] = useState([]);
    const [error, setError] = useState(null);

    const fetchDisciplines = useCallback(async () => {
        try {
            const response = await axios.get('/disciplines/');
            setDisciplines(response.data);
        } catch (error) {
            setError(error);
            console.error('There was an error fetching the disciplines!', error);
        }
    }, []);

    useEffect(() => {
        fetchDisciplines();
    }, [fetchDisciplines]);

    return { disciplines, error, fetchDisciplines };
};

const useDisciplineTypes = () => {
    const [disciplineTypes, setDisciplineTypes] = useState([]);
    const [error, setError] = useState(null);

    const fetchDisciplineTypes = useCallback(async () => {
        try {
            const response = await axios.get('/discipline-types/');
            setDisciplineTypes(response.data);
        } catch (error) {
            setError(error);
            console.error('There was an error fetching the discipline types!', error);
        }
    }, []);

    useEffect(() => {
        fetchDisciplineTypes();
    }, [fetchDisciplineTypes]);

    return { disciplineTypes, error, fetchDisciplineTypes };
};

const DisciplineManagement = () => {
    const { disciplines, error, fetchDisciplines } = useDisciplines();
    const { disciplineTypes, fetchDisciplineTypes } = useDisciplineTypes();
    const [editDiscipline, setEditDiscipline] = useState(null);
    const [newDiscipline, setNewDiscipline] = useState({ name: '', disciplineTypeId: '' });

    const handleChange = (e, setter) => {
        const { name, value } = e.target;
        setter((prev) => ({ ...prev, [name]: value }));
    };

    const handleAddDiscipline = async () => {
        try {
            const response = await axios.post('/disciplines/', newDiscipline);
            setNewDiscipline({ name: '', disciplineTypeId: '' });
            fetchDisciplines();
        } catch (error) {
            console.error('Error adding discipline:', error);
        }
    };

    const handleEditDiscipline = (discipline) => setEditDiscipline({ ...discipline });

    const handleSaveDiscipline = async () => {
        try {
            await axios.put(`/disciplines/${editDiscipline.id}`, editDiscipline);
            setEditDiscipline(null);
            fetchDisciplines();
        } catch (error) {
            console.error('Error saving discipline:', error);
        }
    };

    const handleDeleteDiscipline = async (disciplineId) => {
        if (window.confirm('Are you sure you want to delete this discipline?')) {
            try {
                await axios.delete(`/disciplines/${disciplineId}`);
                fetchDisciplines();
            } catch (error) {
                console.error('Error deleting discipline:', error);
            }
        }
    };

    return (
        <div className="container mt-4">
            <div className="mb-4">
                <h2>Discipline Management</h2>
                {error && <div className="alert alert-danger">Error: {error.message}</div>}
            </div>

            <div className="mb-4">
                <div className="card">
                    <h3 className="card-header">Add New Discipline</h3>
                    <div className="card-body">
                        <div className="form-group mb-3">
                            <input
                                type="text"
                                className="form-control"
                                placeholder="Discipline Name"
                                name="name"
                                value={newDiscipline.name}
                                onChange={(e) => handleChange(e, setNewDiscipline)}
                            />
                        </div>
                        <div className="form-group mb-3">
                            <select
                                className="form-select"
                                name="disciplineTypeId"
                                value={newDiscipline.disciplineTypeId}
                                onChange={(e) => handleChange(e, setNewDiscipline)}
                            >
                                <option value="">Select a type</option>
                                {disciplineTypes.map((type) => (
                                    <option key={type.id} value={type.id}>
                                        {type.disciplineType}
                                    </option>
                                ))}
                            </select>
                        </div>
                        <button className="btn btn-primary" onClick={handleAddDiscipline}>
                            Add Discipline
                        </button>
                    </div>
                </div>
            </div>

            <ul className="list-group">
                {disciplines.length > 0 ? (
                    disciplines.map((discipline) => (
                        <li key={discipline.id} className="list-group-item mb-3">
                            <div className="d-flex align-items-center justify-content-between">
                                <div>
                                    {editDiscipline && editDiscipline.id === discipline.id ? (
                                        <>
                                            <div className="form-group mb-2">
                                                <input
                                                    type="text"
                                                    className="form-control"
                                                    name="name"
                                                    value={editDiscipline.name}
                                                    onChange={(e) => handleChange(e, setEditDiscipline)}
                                                />
                                            </div>
                                            <div className="form-group mb-2">
                                                <select
                                                    className="form-select"
                                                    name="disciplineTypeId"
                                                    value={editDiscipline.disciplineTypeId}
                                                    onChange={(e) => handleChange(e, setEditDiscipline)}
                                                >
                                                    <option value="">Select a type</option>
                                                    {disciplineTypes.map((type) => (
                                                        <option key={type.id} value={type.id}>
                                                            {type.disciplineType}
                                                        </option>
                                                    ))}
                                                </select>
                                            </div>
                                        </>
                                    ) : (
                                        <>
                                            <span className="d-block mb-1">{discipline.name}</span>
                                            <span>
                                                {disciplineTypes.find((type) => type.id === discipline.disciplineTypeId)?.disciplineType}
                                            </span>
                                        </>
                                    )}
                                </div>
                                <div>
                                    {editDiscipline && editDiscipline.id === discipline.id ? (
                                        <button className="btn btn-sm btn-success me-1" onClick={handleSaveDiscipline}>
                                            Save
                                        </button>
                                    ) : (
                                        <button className="btn btn-sm btn-success me-1" onClick={() => handleEditDiscipline(discipline)}>
                                            Edit
                                        </button>
                                    )}
                                    <button className="btn btn-sm btn-danger" onClick={() => handleDeleteDiscipline(discipline.id)}>
                                        Delete
                                    </button>
                                </div>
                            </div>
                        </li>
                    ))
                ) : (
                    <li className="list-group-item">No disciplines available</li>
                )}
            </ul>
        </div>
    );
};

export default DisciplineManagement;
