// SchoolDetails.jsx
import React, { useState, useEffect } from 'react';
import axios from '../axiosInstance';
import { useParams, useNavigate } from 'react-router-dom';

const SchoolDetails = () => {
    const { schoolId } = useParams();
    const [school, setSchool] = useState(null);
    const [error, setError] = useState(null);
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

    if (error) {
        return <div className="alert alert-danger">Error: {error.message}</div>;
    }

    if (!school) {
        return <div>Loading...</div>; // Optional loading indicator
    }

    const handleTeachersClick = () => {
        navigate(`/school/${schoolId}/teachers`);
    };

    return (
        <div className="container mt-4">
            <h2>School Details</h2>
            <div className="card">
                <div className="card-body">
                    <h5 className="card-title">{school.name}</h5>
                    <p className="card-text">Address: {school.address}</p>
                    <button className="btn btn-primary" onClick={handleTeachersClick}>Teachers</button>
                </div>
            </div>
        </div>
    );
};

export default SchoolDetails;
