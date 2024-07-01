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

    return (
        <div className="container mt-4">
            <h2 className="mb-4">Schools</h2>
            {error && <div className="alert alert-danger">Error: {error.message}</div>}
            <div className="mb-3">
                <Link to="/admin/add-school" className="btn btn-success me-2">Add School</Link>
                <Link to="/admin/add-discipline" className="btn btn-success me-2">Add Discipline</Link>
                <Link to={`/admin/statistics`} className="btn btn-success me-2">Statistics</Link>
            </div>
            <ul className="list-group">
                {schools.length > 0 ? (
                    schools.map(school => (
                        <li key={school.id} className="list-group-item d-flex justify-content-between align-items-center">
                            {school.name}
                            <div className="d-flex">
                            <div>
                                <Link to={`/admin/edit-school/${school.id}`} className="btn btn-primary text-white btn-sm me-2">Edit</Link>
                            </div>
                            <div>
                                <Link to={`/admin/school/${school.id}`} className="btn btn-info text-white btn-sm me-2">Details</Link>
                            </div>
                            </div>
                        </li>
                    ))
                ) : (
                    <li className="list-group-item">No schools available</li>
                )}
            </ul>
        </div>
    );
};

export default SchoolList;
