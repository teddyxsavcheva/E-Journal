import React, { useState, useEffect, useCallback } from 'react';
import axios from '../../axiosInstance';
import {Link, useParams} from 'react-router-dom';

const useSchoolClasses = (schoolId) => {
    const [schoolClasses, setSchoolClasses] = useState([]);
    const [error, setError] = useState(null);

    const fetchSchoolClasses = useCallback(async () => {
        try {
            const response = await axios.get(`/school-class/school-id/${schoolId}`);
            setSchoolClasses(response.data);
        } catch (error) {
            setError(error);
            console.error('There was an error fetching the school Classes!', error);
        }
    }, [schoolId]);

    useEffect(() => {
        fetchSchoolClasses();
    }, [fetchSchoolClasses]);

    return { schoolClasses, error, fetchSchoolClasses };
};

const SchoolClasses = () => {
    const { schoolId, headmasterId } = useParams();
    const { schoolClasses, error, fetchSchoolClasses } = useSchoolClasses(schoolId);

    return (
        <div className="container mt-4">
            <div className="mb-4">
                <h2>School Classes for School ID: {schoolId}</h2>
                {error && <div className="alert alert-danger">Error: {error.message}</div>}
            </div>

            <ul className="list-group">
                {schoolClasses.length > 0 ? (
                    schoolClasses.map((schoolClass) => (
                        <li key={schoolClass.id} className="list-group-item mb-3">
                            <div className="d-flex align-items-center justify-content-between">
                                <div className="schoolClass-info">
                                            <span className="d-block mb-1">{schoolClass.name}</span>
                                            <span>{schoolClass.year}</span>
                                </div>
                                <div className="actions">
                                    <Link to={`/headmaster/${headmasterId}/school/${schoolId}/class/${schoolClass.id}`} className="btn btn-sm btn-primary me-1">Details</Link>
                                </div>
                            </div>
                        </li>
                    ))
                ) : (
                    <li className="list-group-item">No school Classes available</li>
                )}
            </ul>
        </div>
    );
};

export default SchoolClasses;
