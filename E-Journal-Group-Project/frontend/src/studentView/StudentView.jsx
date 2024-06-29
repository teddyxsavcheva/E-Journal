import React, { useState, useEffect, useCallback } from 'react';
import axios from '../axiosInstance';
import { useParams } from 'react-router-dom';

const useStudentData = (studentId) => {
    const [disciplines, setDisciplines] = useState([]);
    const [error, setError] = useState(null);

    const fetchData = useCallback(async () => {
        try {
            const disciplinesResponse = await axios.get(`students/${studentId}/disciplines`);
            const disciplinesData = await Promise.all(disciplinesResponse.data.map(async (discipline) => {
                const gradesResponse = await axios.get(`students/${studentId}/disciplines/${discipline.id}/grades`);
                const absencesResponse = await axios.get(`students/${studentId}/disciplines/${discipline.id}/absences`);
                return {
                    ...discipline,
                    grades: gradesResponse.data,
                    absences: absencesResponse.data
                };
            }));
            setDisciplines(disciplinesData);
        } catch (error) {
            setError(error);
            console.error('There was an error fetching the data!', error);
        }
    }, [studentId]);

    useEffect(() => {
        fetchData();
    }, [fetchData]);

    return { disciplines, error };
};

const StudentView = () => {
    const { studentId } = useParams();
    const { disciplines, error } = useStudentData(studentId);

    // Define inline styles
    const styles = {
        container: {
            marginTop: '20px',
        },
        listItem: {
            marginBottom: '20px',
            padding: '15px',
            border: '1px solid #ccc',
            borderRadius: '8px',
            display: 'flex',
            flexDirection: 'column',
        },
        heading: {
            fontWeight: 'bold',
            fontSize: '20px',
        },
        details: {
            marginTop: '10px',
            display: 'flex',
            justifyContent: 'space-between',
            width: '100%',
        },
        detailBox: {
            flex: '1',
            marginRight: '10px',
        },
        lastDetailBox: {
            marginRight: '0',
        },
        detailItem: {
            color: 'white',
            background: '#f8f9fa',
            padding: '5px 10px',
            borderRadius: '5px',
            margin: '5px 0px 0px 5px',
        },
        excused: {
            background: '#71ce00',
            color: 'white',
        },
        notExcused: {
            background: '#f44336',
            color: 'white',
        }
    };

    const getGradeStyle = (grade) => {
        let backgroundColor = '';
        switch (grade) {
            case 'A':
                backgroundColor = '#71ce00';
                break;
            case 'B':
                backgroundColor = '#2993cc';
                break;
            case 'C':
                backgroundColor = '#f4d536';
                break;
            case 'D':
                backgroundColor = '#f47036';
                break;
            case 'F':
                backgroundColor = '#f44336';
                break;
            default:
                backgroundColor = '#f8f9fa';
        }
        return { ...styles.detailItem, backgroundColor };
    };

    return (
        <div className="container" style={styles.container}>
            <h2>Disciplines and Results for Student ID: {studentId}</h2>
            {error && <div className="alert alert-danger">Error: {error.message}</div>}
            <ul className="list-group">
                {disciplines.length > 0 ? (
                    disciplines.map((discipline) => (
                        <li key={discipline.id} style={styles.listItem}>
                            <h4 style={styles.heading}>{discipline.name}</h4>
                            <div style={styles.details}>
                                <div style={{ ...styles.detailBox, marginRight: '20px' }}>
                                    <strong>Grades:</strong>
                                    {discipline.grades.map((grade, index) => (
                                        <span key={index} style={getGradeStyle(grade)}>{grade}</span>
                                    ))}
                                </div>
                                <div style={{ ...styles.detailBox, marginRight: '0' }}>
                                    <strong>Absences:</strong>
                                    {discipline.absences.map((absence, index) => (
                                        <span key={index} style={{...styles.detailItem, ...(index === 0 ? styles.excused : styles.notExcused)}}>
                                            {index === 0 ? 'Excused' : 'Not Excused'} ({absence})
                                        </span>
                                    ))}
                                </div>
                            </div>
                        </li>
                    ))
                ) : (
                    <li className="list-group-item">No disciplines available</li>
                )}
            </ul>
        </div>
    );
};

export default StudentView;