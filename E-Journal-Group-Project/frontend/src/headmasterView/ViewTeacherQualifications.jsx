import React, { useState, useEffect } from 'react';
import axios from '../axiosInstance';
import { useParams, Link } from 'react-router-dom';

const ViewTeacherQualifications = () => {
    const { schoolId, teacherId } = useParams();
    const [qualifications, setQualifications] = useState([]);
    const [allQualifications, setAllQualifications] = useState([]);
    const [error, setError] = useState(null);
    const [successMessage, setSuccessMessage] = useState('');
    const fetchQualifications = async () => {
        try {
            const response = await axios.get(`/teacher/${teacherId}/qualifications`);
            setQualifications(response.data);
        } catch (error) {
            setError('Error fetching qualifications');
            console.error('Error fetching qualifications:', error);
        }
    };

    const fetchAllQualifications = async () => {
        try {
            const response = await axios.get('/teacher-qualifications/');
            setAllQualifications(response.data);
        } catch (error) {
            setError('Error fetching all qualifications');
            console.error('Error fetching all qualifications:', error);
        }
    };

    useEffect(() => {
        fetchQualifications();
        fetchAllQualifications();
    }, [teacherId]);

    return (
        <div className="container mt-4">
            {error && <div className="alert alert-danger">Error: {error}</div>}
            {successMessage && <div className="alert alert-success">{successMessage}</div>}

            <ul className="list-group mt-4">
                {qualifications.length > 0 ? (
                    qualifications.map((qualification) => (
                        <li key={qualification.id} className="list-group-item d-flex justify-content-between align-items-center">
                            {qualification.qualificationEnum}
                        </li>
                    ))
                ) : (
                    <li className="list-group-item">No qualifications available</li>
                )}
            </ul>
        </div>
    );
};

export default ViewTeacherQualifications;
