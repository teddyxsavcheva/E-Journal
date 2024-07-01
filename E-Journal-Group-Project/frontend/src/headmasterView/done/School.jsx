import React, { useState, useEffect } from 'react';
import axios from '../../axiosInstance';
import { useParams, useNavigate } from 'react-router-dom';

const School = () => {
    const { headmasterId } = useParams();
    const [school, setSchool] = useState(null);
    const [headmaster, setHeadmaster] = useState(null);
    const [schoolType, setSchoolType] = useState(null);
    const [schoolTypes, setSchoolTypes] = useState([]);
    const [formData, setFormData] = useState({
        name: '',
        address: '',
        schoolTypeId: '',
    });
    const [error, setError] = useState(null);
    const navigate = useNavigate();

    useEffect(() => {
        const fetchHeadmaster = async () => {
            try {
                const response = await axios.get(`/headmaster/${headmasterId}`);
                if (response.data) {
                    setHeadmaster(response.data);
                } else {
                    setHeadmaster(null);
                }
            } catch (error) {
                setError(error);
                console.error('There was an error fetching the headmaster!', error);
            }
        };

        fetchHeadmaster();
    }, [headmasterId]);

    useEffect(() => {
        const fetchSchoolDetails = async () => {
            if (headmaster && headmaster.schoolId) {
                try {
                    const schoolResponse = await axios.get(`/school/${headmaster.schoolId}`);
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
            }
        };

        fetchSchoolDetails();
    }, [headmaster]);

    return (
        <div className="container mt-4">
            <h2>School Details</h2>
            {error && <div className="alert alert-danger">Error: {error.message}</div>}
            {school ? (
                <div className="card">
                    <div className="card-body">
                        <>
                                <h5 className="card-title">{school.name}</h5>
                                <p className="card-text">Address: {school.address}</p>
                                <p className="card-text">School Type: {schoolType ? schoolType.replace('_', ' ').toLowerCase().replace(/\b\w/g, c => c.toUpperCase()) : 'N/A'}</p>
                                <button className="btn btn-primary me-1" onClick={() => navigate(`/headmaster/${headmasterId}/school/${school.id}/teachers`)}>Teachers</button>
                                <button className="btn btn-primary me-1" onClick={() => navigate(`/headmaster/${headmasterId}/school/${school.id}/classes`)}>School Classes</button>
                                {/*<button className="btn btn-secondary me-1" onClick={handleEditClick}>Edit</button>*/}
                            </>
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

export default School;
