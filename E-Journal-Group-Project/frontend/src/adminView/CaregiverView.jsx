import React, { useState, useEffect } from 'react';
import axios from '../axiosInstance';
import { Link, useParams } from 'react-router-dom';

const CaregiverView = () => {
    const { id } = useParams();
    const [caregiver, setCaregiver] = useState(null);
    const [students, setStudents] = useState([]);
    const [error, setError] = useState(null);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        const fetchCaregiver = async () => {
            try {
                const response = await axios.get(`/caregivers/${id}`);
                console.log('API response:', response.data);
                setCaregiver(response.data);

                // Fetch student details
                const studentIds = response.data.studentIds;
                const studentDetails = await Promise.all(
                    studentIds.map(async (studentId) => {
                        const studentResponse = await axios.get(`/students/${studentId}`);
                        return studentResponse.data;
                    })
                );
                setStudents(studentDetails);
                console.log('Students details:', studentDetails);
            } catch (error) {
                setError(error);
                console.error('API error:', error);
            } finally {
                setLoading(false);
            }
        };

        fetchCaregiver();
    }, [id]);

    if (loading) return <div>Loading...</div>;
    if (error) return <div>Error: {error.message}</div>;


    return (
        <div className="container mt-4">
            <h2 className="mb-4">Caregiver Details</h2>
            {caregiver ? (
                <div>
                    <h3>{caregiver.name}</h3>
                    <ul className="list-group">
                        {students.length > 0 ? (
                            students.map(student => (
                                <li key={student.id} className="list-group-item d-flex justify-content-between align-items-center">
                                    {student.name}
                                    <div>
                                        <Link to={`/student/${student.id}`} className="btn btn-primary btn-sm">Details</Link>
                                    </div>
                                </li>
                            ))
                        ) : (
                            <li className="list-group-item">No students available</li>
                        )}
                    </ul>
                </div>
            ) : (
                <div>Loading...</div>
            )}
        </div>
    );
};

export default CaregiverView;
