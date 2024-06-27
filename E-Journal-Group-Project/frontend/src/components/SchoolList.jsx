import React, { useState, useEffect } from 'react';
import axios from '../axiosInstance';
import { Link } from 'react-router-dom';

const SchoolList = () => {
    const [schools, setSchools] = useState([]);
    const [error, setError] = useState(null);
    const [schoolTypes, setSchoolTypes] = useState([]);
    const [selectedSchoolTypeId, setSelectedSchoolTypeId] = useState('');

    useEffect(() => {
        const fetchSchools = async () => {
            try {
                const response = await axios.get('/school/');
                setSchools(response.data);
            } catch (error) {
                setError(error);
            }
        };

        const fetchSchoolTypes = async () => {
            try {
                const response = await axios.get('/school-type/');
                setSchoolTypes(response.data);
            } catch (error) {
                console.error('Error fetching school types:', error);
            }
        };

        fetchSchools();
        fetchSchoolTypes();
    }, []);

    const handleSchoolTypeChange = (event) => {
        setSelectedSchoolTypeId(event.target.value);
        // You can implement filtering of schools based on selected school type here
        // Example: fetchFilteredSchools(event.target.value);
    };

    return (
        <div className="container mt-4">
            <h2 className="mb-4">Schools</h2>
            {error && <div className="alert alert-danger">Error: {error.message}</div>}
            <div className="mb-3">
                <Link to="/add-school" className="btn btn-primary">Add School</Link>
            </div>
            <ul className="list-group">
                {schools.length > 0 ? (
                    schools.map(school => (
                        <li key={school.id} className="list-group-item d-flex justify-content-between align-items-center">
                            {school.name}
                            <div>
                                <Link to={`/school/${school.id}`} className="btn btn-primary btn-sm mr-2">Details</Link>
                            </div>
                        </li>
                    ))
                ) : (
                    <li className="list-group-item">No schools available</li>
                )}
            </ul>
            <div className="mt-4">
                <label htmlFor="schoolTypeSelect" className="form-label">Filter by School Type:</label>
                <select id="schoolTypeSelect" className="form-select" onChange={handleSchoolTypeChange}>
                    <option value="">All</option>
                    {schoolTypes.map(type => (
                        <option key={type.id} value={type.id}>{type.schoolType}</option>
                    ))}
                </select>
            </div>
        </div>
    );
};

export default SchoolList;
