import React, { useState, useEffect } from 'react';
import {Link, useParams} from 'react-router-dom';
import { fetchAllTeachersFromSchool, fetchAllDisciplines } from '../functions/fetchFunctions';
import useCurriculum from '../functions/useCurriculum';
import useTeachersAndDisciplines from '../functions/useTeachersAndDisciplines';

const Curriculum = () => {
    const { schoolId, classId, headmasterId } = useParams();
    const { curriculum, error, fetchCurriculum, setCurriculum } = useCurriculum(classId);
    const { teachersAndDisciplines, error: teachersError, fetchTeachersAndDisciplines } = useTeachersAndDisciplines(curriculum?.id);
    const [teachers, setTeachers] = useState([]);
    const [disciplines, setDisciplines] = useState([]);
    const [duplicateError, setDuplicateError] = useState('');

    useEffect(() => {
        const fetchData = async () => {
            try {
                const fetchedTeachers = await fetchAllTeachersFromSchool(schoolId);
                const fetchedDisciplines = await fetchAllDisciplines();
                setTeachers(fetchedTeachers);
                setDisciplines(fetchedDisciplines);
            } catch (error) {
                console.error('Error fetching teachers and disciplines:', error);
            }
        };

        fetchData();
    }, [schoolId]);

    const findById = (id, array) => array.find(item => item.id === id);

    return (
        <div className="container mt-4">
            {teachersAndDisciplines.length > 0 && (
                <div className="card mt-4">
                    <h3 className="card-header">Teachers and Disciplines</h3>
                    <div className="card-body">
                        <ul className="list-group">
                            {teachersAndDisciplines.map((item) => {
                                const teacher = findById(item.teacherId, teachers);
                                const discipline = findById(item.disciplineId, disciplines);

                                if (!teacher || !discipline) {
                                    return null;
                                }

                                return (
                                    <li key={item.id} className="list-group-item d-flex justify-content-between align-items-center">
                                        <span>{discipline.name} - {teacher.name}</span>
                                        <div>
                                            <Link
                                                to={`/headmaster/${headmasterId}/school/${schoolId}/school-class/${classId}/students-grades/teacher/${teacher.id}/discipline/${item.disciplineId}/absences`}
                                                className="btn btn-info text-white btn-sm me-2"
                                            >
                                                Absence
                                            </Link>
                                            <Link
                                                to={`/headmaster/${headmasterId}/school/${schoolId}/school-class/${classId}/students-grades/teacher/${teacher.id}/discipline/${item.disciplineId}/grades`}
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

export default Curriculum;
