import React, { useState, useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import axios from '../axiosInstance';
import { fetchAllTeachersFromSchool, fetchAllDisciplines } from '../functions/fetchFunctions';
import useCurriculum from '../functions/useCurriculum';
import useTeachersAndDisciplines from '../functions/useTeachersAndDisciplines';

const TCurriculum = () => {
    const { teacherId, classId } = useParams();
    const { curriculum, error, fetchCurriculum, setCurriculum } = useCurriculum(classId);
    const { teachersAndDisciplines, error: teachersError, fetchTeachersAndDisciplines } = useTeachersAndDisciplines(curriculum?.id);
    const [teachers, setTeachers] = useState([]);
    const [teacher, setTeacher] = useState(null);
    const [disciplines, setDisciplines] = useState([]);
    const [filteredDisciplines, setFilteredDisciplines] = useState([]);
    const [duplicateError, setDuplicateError] = useState('');

    useEffect(() => {
        const fetchTeacher = async () => {
            try {
                const response = await axios.get(`/teacher/${teacherId}`);
                if (response.data) {
                    setTeacher(response.data);
                    console.log(response.data);
                } else {
                    setTeacher(null);
                }
            } catch (error) {
                console.error('There was an error fetching the teacher!', error);
            }
        };
        fetchTeacher();
    }, [teacherId]);

    useEffect(() => {
        const fetchData = async () => {
            if (teacher && teacher.schoolId) {
                try {
                    const fetchedTeachers = await fetchAllTeachersFromSchool(teacher.schoolId);
                    const fetchedDisciplines = await fetchAllDisciplines();
                    setTeachers(fetchedTeachers);
                    setDisciplines(fetchedDisciplines);

                    const filtered = teachersAndDisciplines.filter(item => item.teacherId === parseInt(teacherId));
                    setFilteredDisciplines(filtered);
                } catch (error) {
                    console.error('Error fetching teachers and disciplines:', error);
                }
            }
        };

        fetchData();
    }, [teacher, teachersAndDisciplines]);

    const findById = (id, array) => array.find(item => item.id === id);

    return (
        <div className="container mt-4">
            {filteredDisciplines.length > 0 && (
                <div className="card mt-4">
                    <h3 className="card-header">Disciplines for Teacher {teacher ? teacher.name : ''}</h3>
                    <div className="card-body">
                        <ul className="list-group">
                            {filteredDisciplines.map((item) => {
                                const discipline = findById(item.disciplineId, disciplines);

                                if (!discipline) {
                                    return null;
                                }

                                return (
                                    <li key={item.id} className="list-group-item d-flex justify-content-between align-items-center">
                                        <span>{discipline.name}</span>
                                        <div>
                                            <Link
                                                to={`/teacher/${teacher.id}/class/${classId}/discipline/${item.disciplineId}/absences`}
                                                className="btn btn-info text-white btn-sm me-2"
                                            >
                                                Absence
                                            </Link>
                                            <Link
                                                to={`/teacher/${teacher.id}/class/${classId}/discipline/${item.disciplineId}/grades`}
                                                className="btn btn-success btn-sm me-2"
                                            >
                                                Grade
                                            </Link>
                                        </div>
                                    </li>
                                );
                            })}
                        </ul>
                    </div>
                </div>
            )}

            <div className="card mt-4">
                {duplicateError && (
                    <div className="alert alert-danger" role="alert">
                        {duplicateError}
                    </div>
                )}
            </div>
        </div>
    );
};

export default TCurriculum;
