import React, { useState, useEffect } from 'react';
import axios from '../axiosInstance';
import { Link } from 'react-router-dom';

const SchoolList = () => {
    const [schools, setSchools] = useState([]);
    const [error, setError] = useState(null);

    useEffect(() => {
        const fetchSchools = async () => {
            try {
                const response = await axios.get('/school/');
                setSchools(response.data);
            } catch (error) {
                setError(error);
            }
        };

        fetchSchools();
    }, []);

    return (
        <div className="container mt-4">
            <h2 className="mb-4">Schools</h2>
            {error && <div className="alert alert-danger">Error: {error.message}</div>}
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
        </div>
    );
};

export default SchoolList;
