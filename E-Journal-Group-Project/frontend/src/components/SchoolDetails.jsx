import React, { useState, useEffect } from 'react';
import axios from '../axiosInstance';
import { useParams, useNavigate } from 'react-router-dom';

const SchoolDetails = () => {
    const { schoolId } = useParams();
    const [school, setSchool] = useState(null);
    const [error, setError] = useState(null);
    const [editSchool, setEditSchool] = useState(null);
    const navigate = useNavigate();

    useEffect(() => {
        const fetchSchoolDetails = async () => {
            try {
                const response = await axios.get(`/school/${schoolId}`);
                setSchool(response.data);
            } catch (error) {
                setError(error);
                console.error('There was an error fetching the school details!', error);
            }
        };

        fetchSchoolDetails();
    }, [schoolId]);

    const handleTeachersClick = () => {
        navigate(`/school/${schoolId}/teachers`);
    };

    const handleSchoolClassesClick = () => {
        navigate(`/school/${schoolId}/classes`);
    };

    const handleHeadmasterClick = () => {
        navigate(`/school/${schoolId}/headmaster`);
    };

    const handleChange = (e, setter) => {
        const { name, value } = e.target;
        setter((prev) => ({ ...prev, [name]: value }));
    };

    const handleEditClick = () => {
        setEditSchool(school);
    };

    const handleSaveClick = async () => {
        try {
            await axios.put(`/school/${schoolId}`, editSchool);
            setSchool(editSchool);
            setEditSchool(null);
        } catch (error) {
            setError(error);
            console.error('There was an error updating the school!', error);
        }
    };

    const handleDeleteClick = async () => {
        if (window.confirm('Are you sure you want to delete this school?')) {
            try {
                await axios.delete(`/school/${schoolId}`);
                navigate('/schools'); // Assuming you have a list of schools to navigate to after deletion
            } catch (error) {
                setError(error);
                console.error('There was an error deleting the school!', error);
            }
        }
    };

    return (
        <div className="container mt-4">
            <h2>School Details</h2>
            {error && <div className="alert alert-danger">Error: {error.message}</div>}
            {school ? (
                <div className="card">
                    <div className="card-body">
                        {editSchool ? (
                            <>
                                <div className="mb-2">
                                    <input
                                        type="text"
                                        className="form-control"
                                        name="name"
                                        value={editSchool.name}
                                        onChange={(e) => handleChange(e, setEditSchool)}
                                    />
                                </div>
                                <div className="mb-2">
                                    <input
                                        type="text"
                                        className="form-control"
                                        name="address"
                                        value={editSchool.address}
                                        onChange={(e) => handleChange(e, setEditSchool)}
                                    />
                                </div>
                                <button className="btn btn-success me-1" onClick={handleSaveClick}>Save</button>
                                <button className="btn btn-secondary me-1" onClick={() => setEditSchool(null)}>Cancel</button>
                            </>
                        ) : (
                            <>
                                <h5 className="card-title">{school.name}</h5>
                                <p className="card-text">Address: {school.address}</p>
                                <button className="btn btn-primary me-1" onClick={handleTeachersClick}>Teachers</button>
                                <button className="btn btn-primary me-1" onClick={handleSchoolClassesClick}>School Classes</button>
                                <button className="btn btn-primary me-1" onClick={handleHeadmasterClick}>Headmaster</button>
                                <button className="btn btn-secondary me-1" onClick={handleEditClick}>Edit</button>
                                <button className="btn btn-danger me-1" onClick={handleDeleteClick}>Delete</button>
                            </>
                        )}
                    </div>
                </div>
            ) : (
                <div className="card">
                    {/* Placeholder for loading or no data */}
                </div>
            )}
        </div>
    );
};

export default SchoolDetails;
