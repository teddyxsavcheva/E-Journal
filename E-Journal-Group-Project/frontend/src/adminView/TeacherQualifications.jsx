import React, { useState, useEffect } from 'react';
import axios from '../axiosInstance';
import { useParams, Link } from 'react-router-dom';

const TeacherQualifications = () => {
    const { schoolId, id } = useParams(); // Assuming schoolId and teacher id are fetched from URL params
    const [qualifications, setQualifications] = useState([]);
    const [allQualifications, setAllQualifications] = useState([]);
    const [error, setError] = useState(null);
    const [successMessage, setSuccessMessage] = useState('');
    const [selectedQualification, setSelectedQualification] = useState('');

    // Fetch qualifications assigned to the teacher
    const fetchQualifications = async () => {
        try {
            const response = await axios.get(`/teacher/${id}/qualifications`);
            setQualifications(response.data);
        } catch (error) {
            setError('Error fetching qualifications');
            console.error('Error fetching qualifications:', error);
        }
    };

    // Fetch all qualifications
    const fetchAllQualifications = async () => {
        try {
            const response = await axios.get('/teacher-qualifications/');
            setAllQualifications(response.data);
        } catch (error) {
            setError('Error fetching all qualifications');
            console.error('Error fetching all qualifications:', error);
        }
    };

    // Fetch initial data on component mount
    useEffect(() => {
        fetchQualifications();
        fetchAllQualifications();
    }, [id]); // Fetch data again when teacher id changes

    // Filter out qualifications that are already assigned to the teacher
    const getAvailableQualifications = () => {
        return allQualifications.filter(qual => (
            !qualifications.some(q => q.id === qual.id)
        ));
    };

    // Add qualification to the teacher
    const handleAddQualification = async (event) => {
        event.preventDefault();
        try {
            await axios.post(`/teacher-qualifications/${selectedQualification}/teachers/${id}`);
            setSuccessMessage('Qualification added successfully!');
            setTimeout(() => setSuccessMessage(''), 3000);
            setSelectedQualification('');
            fetchQualifications(); // Refresh the qualifications list
            fetchAllQualifications(); // Refresh all qualifications (in case of updates)
        } catch (error) {
            setError('Error adding qualification');
            console.error('Error adding qualification:', error);
        }
    };

    // Remove qualification from the teacher
    const handleRemoveQualification = async (qualificationId) => {
        try {
            await axios.delete(`/teacher-qualifications/${qualificationId}/teachers/${id}`);
            setSuccessMessage('Qualification removed successfully!');
            setTimeout(() => setSuccessMessage(''), 3000);
            fetchQualifications(); // Refresh the qualifications list
            fetchAllQualifications(); // Refresh all qualifications (in case of updates)
        } catch (error) {
            setError('Error removing qualification');
            console.error('Error removing qualification:', error);
        }
    };

    // Render component
    return (
        <div className="container mt-4">
            <h2>Qualifications for Teacher ID: {id}</h2>
            {error && <div className="alert alert-danger">Error: {error}</div>}
            {successMessage && <div className="alert alert-success">{successMessage}</div>}

            <form onSubmit={handleAddQualification}>
                <div className="mb-3">
                    <label htmlFor="qualificationSelect" className="form-label">Add Qualification:</label>
                    <select
                        id="qualificationSelect"
                        className="form-select"
                        value={selectedQualification}
                        onChange={(e) => setSelectedQualification(e.target.value)}
                        required
                    >
                        <option value="">Select Qualification</option>
                        {getAvailableQualifications().map((qual) => (
                            <option key={qual.id} value={qual.id}>{qual.qualificationEnum}</option>
                        ))}
                    </select>
                </div>
                <button type="submit" className="btn btn-primary me-2">Add Qualification</button>
                <Link to={`/admin/school/${schoolId}`} className="btn btn-secondary">Back to Teacher List</Link>
            </form>

            <ul className="list-group mt-4">
                {qualifications.length > 0 ? (
                    qualifications.map((qualification) => (
                        <li key={qualification.id} className="list-group-item d-flex justify-content-between align-items-center">
                            {qualification.qualificationEnum}
                            <button
                                className="btn btn-sm btn-danger"
                                onClick={() => handleRemoveQualification(qualification.id)}
                            >
                                Remove
                            </button>
                        </li>
                    ))
                ) : (
                    <li className="list-group-item">No qualifications available</li>
                )}
            </ul>
        </div>
    );
};

export default TeacherQualifications;
