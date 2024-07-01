import React, { useState, useEffect } from 'react';
import axios from '../axiosInstance';
import { useParams } from "react-router-dom";

const StatisticsTeacher = () => {
    const { headmasterId } = useParams();
    const [disciplineGrades, setDisciplineGrades] = useState([]);
    const [teacherDisciplineGrades, setTeacherDisciplineGrades] = useState([]);
    const [error, setError] = useState(null);
    const [sortConfig, setSortConfig] = useState({ key: '', direction: 'ascending' });

    useEffect(() => {
        const getStatistics = async () => {
            try {
                const responseDiscipline = await axios.get(`/headmaster/${headmasterId}/avg-grade-for-discipline`);
                const responseTeacherDiscipline = await axios.get(`/headmaster/${headmasterId}/avg-grade-for-teacher-by-discipline`);

                if (responseDiscipline.data && responseDiscipline.data.length > 0) {
                    setDisciplineGrades(responseDiscipline.data.map(item => {
                        const [discipline, grade] = item.split(',');
                        return { discipline, grade };
                    }));
                }

                if (responseTeacherDiscipline.data && responseTeacherDiscipline.data.length > 0) {
                    setTeacherDisciplineGrades(responseTeacherDiscipline.data.map(item => {
                        const [teacher, discipline, grade] = item.split(',');
                        return { teacher, discipline, grade };
                    }));
                }
            } catch (error) {
                setError(error);
                console.error('There was an error getting the statistics!', error);
            }
        };

        getStatistics();
    }, [headmasterId]);

    const getGradeClass = (grade) => {
        if (grade >= 'A') return 'table-success';
        if (grade >= 'B') return 'table-info';
        if (grade >= 'C') return 'table-warning';
        return 'table-danger';
    };

    const sortArray = (array, key) => {
        return array.sort((a, b) => {
            if (a[key] < b[key]) {
                return sortConfig.direction === 'ascending' ? -1 : 1;
            }
            if (a[key] > b[key]) {
                return sortConfig.direction === 'ascending' ? 1 : -1;
            }
            return 0;
        });
    };

    const requestSort = (key) => {
        let direction = 'ascending';
        if (sortConfig.key === key && sortConfig.direction === 'ascending') {
            direction = 'descending';
        }
        setSortConfig({ key, direction });
    };

    const sortedDisciplineGrades = sortArray([...disciplineGrades], sortConfig.key);
    const sortedTeacherDisciplineGrades = sortArray([...teacherDisciplineGrades], sortConfig.key);

    return (
        <div className="container mt-5">
            <h1 className="text-center mb-4">Statistics</h1>
            {error && <p className="text-danger">Error: {error.message}</p>}
            <h2 className="mb-3">Discipline Grades</h2>
            {disciplineGrades.length > 0 ? (
                <table className="table table-striped table-bordered mb-4">
                    <thead>
                    <tr>
                        <th onClick={() => requestSort('discipline')}>
                            Discipline
                        </th>
                        <th onClick={() => requestSort('grade')}>
                            Average Grade
                        </th>
                    </tr>
                    </thead>
                    <tbody>
                    {sortedDisciplineGrades.map((item, index) => (
                        <tr key={index} className={getGradeClass(item.grade)}>
                            <td>{item.discipline}</td>
                            <td>{item.grade}</td>
                        </tr>
                    ))}
                    </tbody>
                </table>
            ) : (
                <p>Loading...</p>
            )}
            <h2 className="mb-3">Teacher Discipline Grades</h2>
            {teacherDisciplineGrades.length > 0 ? (
                <table className="table table-striped table-bordered">
                    <thead>
                    <tr>
                        <th onClick={() => requestSort('teacher')}>
                            Teacher
                        </th>
                        <th onClick={() => requestSort('discipline')}>
                            Discipline
                        </th>
                        <th onClick={() => requestSort('grade')}>
                            Average Grade
                        </th>
                    </tr>
                    </thead>
                    <tbody>
                    {sortedTeacherDisciplineGrades.map((item, index) => (
                        <tr key={index} className={getGradeClass(item.grade)}>
                            <td>{item.teacher}</td>
                            <td>{item.discipline}</td>
                            <td>{item.grade}</td>
                        </tr>
                    ))}
                    </tbody>
                </table>
            ) : (
                <p>Loading...</p>
            )}
        </div>
    );
};

export default StatisticsTeacher;
