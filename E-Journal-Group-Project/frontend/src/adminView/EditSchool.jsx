import React, { useState, useEffect } from 'react';
import axios from '../axiosInstance';
import { Link, useParams } from 'react-router-dom';

const EditSchool = () => {
    const { schoolId } = useParams();
    const [name, setName] = useState('');
    const [address, setAddress] = useState('');
    const [schoolTypeId, setSchoolTypeId] = useState('');
    const [schoolTypes, setSchoolTypes] = useState([]);
    const [error, setError] = useState(null);
    const [successMessage, setSuccessMessage] = useState('');

    useEffect(() => {
        const fetchSchool = async () => {
            try {
                const response = await axios.get(`/school/${schoolId}`);
                const { name, address, schoolTypeId } = response.data;
                setName(name);
                setAddress(address);
                setSchoolTypeId(schoolTypeId);
            } catch (error) {
                console.error('Error fetching school:', error);
                setError('Error fetching school');
            }
        };

        const fetchSchoolTypes = async () => {
            try {
                const response = await axios.get('/school-type/');
                setSchoolTypes(response.data);
            } catch (error) {
                console.error('Error fetching school types:', error);
                setError('Error fetching school types');
            }
        };

        fetchSchool();
        fetchSchoolTypes();
    }, [schoolId]);

    const handleSubmit = async (event) => {
        event.preventDefault();
        try {
            await axios.put(`/school/${schoolId}`, {
                name,
                address,
                schoolTypeId
            });
            setSuccessMessage('School updated successfully!');
            // Optionally, you can redirect or set a timer to clear success message
            setTimeout(() => {
                setSuccessMessage('');
            }, 3000); // Clear success message after 3 seconds
        } catch (error) {
            setError('Error updating school');
            console.error('Error updating school:', error);
        }
    };

    return (
        <div className="container mt-4">
            <h2 className="mb-4">Edit School</h2>
            {error && <div className="alert alert-danger">Error: {error}</div>}
            {successMessage && <div className="alert alert-success">{successMessage}</div>}
            <form onSubmit={handleSubmit}>
                <div className="mb-3">
                    <label htmlFor="nameInput" className="form-label">Name:</label>
                    <input type="text" className="form-control" id="nameInput" value={name} onChange={(e) => setName(e.target.value)} required />
                </div>
                <div className="mb-3">
                    <label htmlFor="addressInput" className="form-label">Address:</label>
                    <input type="text" className="form-control" id="addressInput" value={address} onChange={(e) => setAddress(e.target.value)} required />
                </div>
                <div className="mb-3">
                    <label htmlFor="schoolTypeSelect" className="form-label">School Type:</label>
                    <select id="schoolTypeSelect" className="form-select" value={schoolTypeId} onChange={(e) => setSchoolTypeId(e.target.value)} required>
                        <option value="">Select School Type</option>
                        {schoolTypes.map(type => (
                            <option key={type.id} value={type.id}>{type.schoolType}</option>
                        ))}
                    </select>
                </div>
                <div>
                    <button type="submit" className="btn btn-primary me-2">Update School</button>
                    <Link to="/admin" className="btn btn-secondary">Back to Schools</Link>
                </div>
            </form>
        </div>
    );
};

export default EditSchool;
