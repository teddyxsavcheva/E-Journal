import React, { useState, useEffect } from 'react';
import axios from '../axiosInstance';
import { useParams, useNavigate } from 'react-router-dom';

const SchoolDetails = () => {
    const { schoolId } = useParams();
    const [school, setSchool] = useState(null);
    const [schoolType, setSchoolType] = useState(null);
    const [schoolTypes, setSchoolTypes] = useState([]);
    const [isEditing, setIsEditing] = useState(false);
    const [formData, setFormData] = useState({
        name: '',
        address: '',
        schoolTypeId: '',
    });
    const [error, setError] = useState(null);
    const navigate = useNavigate();

    useEffect(() => {
        const fetchSchoolDetails = async () => {
            try {
                const schoolResponse = await axios.get(`/school/${schoolId}`);
                setSchool(schoolResponse.data);
                setFormData({
                    name: schoolResponse.data.name,
                    address: schoolResponse.data.address,
                    schoolTypeId: schoolResponse.data.schoolTypeId,
                });

                const schoolTypeResponse = await axios.get(`/school-type/${schoolResponse.data.schoolTypeId}`);
                setSchoolType(schoolTypeResponse.data.schoolType);

                const allSchoolTypesResponse = await axios.get('/school-type/');
                setSchoolTypes(allSchoolTypesResponse.data);
            } catch (error) {
                setError(error);
                console.error('There was an error fetching the school details!', error);
            }
        };

        fetchSchoolDetails();
    }, [schoolId]);

    const handleEditClick = () => {
        setIsEditing(true);
    };

    const handleCancelClick = () => {
        setIsEditing(false);
    };

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData((prevData) => ({ ...prevData, [name]: value }));
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            await axios.put(`/school/${schoolId}`, formData);
            setIsEditing(false);
            setSchool({ ...school, ...formData });
            const updatedSchoolTypeResponse = await axios.get(`/school-type/${formData.schoolTypeId}`);
            setSchoolType(updatedSchoolTypeResponse.data.schoolType);
        } catch (error) {
            setError(error);
            console.error('There was an error updating the school details!', error);
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
                        {isEditing ? (
                            <form onSubmit={handleSubmit}>
                                <div className="mb-3">
                                    <label htmlFor="name" className="form-label">Name</label>
                                    <input
                                        type="text"
                                        className="form-control"
                                        id="name"
                                        name="name"
                                        value={formData.name}
                                        onChange={handleChange}
                                    />
                                </div>
                                <div className="mb-3">
                                    <label htmlFor="address" className="form-label">Address</label>
                                    <input
                                        type="text"
                                        className="form-control"
                                        id="address"
                                        name="address"
                                        value={formData.address}
                                        onChange={handleChange}
                                    />
                                </div>
                                <div className="mb-3">
                                    <label htmlFor="schoolTypeId" className="form-label">School Type</label>
                                    <select
                                        className="form-control"
                                        id="schoolTypeId"
                                        name="schoolTypeId"
                                        value={formData.schoolTypeId}
                                        onChange={handleChange}
                                    >
                                        {schoolTypes.map((type) => (
                                            <option key={type.id} value={type.id}>
                                                {type.schoolType.replace('_', ' ').toLowerCase().replace(/\b\w/g, c => c.toUpperCase())}
                                            </option>
                                        ))}
                                    </select>
                                </div>
                                <button type="submit" className="btn btn-primary me-1">Save</button>
                                <button type="button" className="btn btn-secondary" onClick={handleCancelClick}>Cancel</button>
                            </form>
                        ) : (
                            <>
                                <h5 className="card-title">{school.name}</h5>
                                <p className="card-text">Address: {school.address}</p>
                                <p className="card-text">School Type: {schoolType ? schoolType.replace('_', ' ').toLowerCase().replace(/\b\w/g, c => c.toUpperCase()) : 'N/A'}</p>
                                <button className="btn btn-primary me-1" onClick={() => navigate(`/school/${schoolId}/teachers`)}>Teachers</button>
                                <button className="btn btn-primary me-1" onClick={() => navigate(`/school/${schoolId}/classes`)}>School Classes</button>
                                <button className="btn btn-primary me-1" onClick={() => navigate(`/school/${schoolId}/headmaster`)}>Headmaster</button>
                                <button className="btn btn-secondary me-1" onClick={handleEditClick}>Edit</button>
                                <button className="btn btn-danger me-1" onClick={handleDeleteClick}>Delete</button>
                            </>
                        )}
                    </div>
                </div>
            ) : (
                <div className="card">
                    <div className="card-body">
                        Loading...
                    </div>
                </div>
            )}
        </div>
    );
};

export default SchoolDetails;
