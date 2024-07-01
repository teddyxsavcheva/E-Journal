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

    return (
        <div className="container mt-4">
            <div className="mb-4">
                {error && <div className="alert alert-danger">Error: {error.message}</div>}
            </div>

            <ul className="list-group">
                {caregivers.length > 0 ? (
                    caregivers.map((caregiver) => (
                        <li key={caregiver.id} className="list-group-item mb-3">
                            <div className="d-flex align-items-center justify-content-between">
                                <span className="d-block mb-1">{caregiver.name}</span>
                                <span>{caregiver.email}</span>
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
