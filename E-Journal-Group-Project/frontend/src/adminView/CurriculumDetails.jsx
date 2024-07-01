import React, { useState, useEffect } from 'react';
import axios from '../axiosInstance';
import { Link, useParams } from 'react-router-dom';
import EditTeacherDiscipline from './EditTeacherDiscipline';
import { fetchAllTeachersFromSchool, fetchAllDisciplines } from '../functions/fetchFunctions';
import useCurriculum from '../functions/useCurriculum';
import useTeachersAndDisciplines from '../functions/useTeachersAndDisciplines';

const CurriculumDetails = () => {
    const { schoolId, classId } = useParams();
    const { curriculum, error, fetchCurriculum, setCurriculum } = useCurriculum(classId);
    const { teachersAndDisciplines, error: teachersError, fetchTeachersAndDisciplines } = useTeachersAndDisciplines(curriculum?.id);
    const [editCurriculum, setEditCurriculum] = useState(null);
    const [newCurriculum, setNewCurriculum] = useState({ semester: '', year: '' });
    const [teachers, setTeachers] = useState([]);
    const [disciplines, setDisciplines] = useState([]);
    const [editTeacherDiscipline, setEditTeacherDiscipline] = useState(null);
    const [selectedTeachers, setSelectedTeachers] = useState([]);
    const [selectedDisciplines, setSelectedDisciplines] = useState([]);
    const [newTeacherDiscipline, setNewTeacherDiscipline] = useState({ teacherId: '', disciplineId: '' });
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

    const handleEditCurriculum = (curriculum) => setEditCurriculum({ ...curriculum });

    const handleChange = (e, setter) => {
        const { name, value } = e.target;
        setter((prev) => ({ ...prev, [name]: value }));
    };

    const handleSaveCurriculum = async () => {
        try {
            await axios.put(`/student-curriculum/${editCurriculum.id}`, {
                ...editCurriculum,
                schoolClassId: Number(classId)
            });
            setEditCurriculum(null);
            fetchCurriculum();
        } catch (error) {
            console.error('Error saving curriculum:', error);
        }
    };

    const handleDeleteCurriculum = async (id) => {
        if (window.confirm('Are you sure you want to delete this curriculum?')) {
            try {
                await axios.delete(`/student-curriculum/${id}`);
                setCurriculum(null); // Set the curriculum to null to immediately reflect the deletion
                fetchCurriculum();
            } catch (error) {
                console.error('Error deleting curriculum: ', error);
            }
        }
    };

    const handleAddCurriculum = async () => {
        try {
            if (curriculum !== null) {
                console.log("A curriculum already exists for this class.");
                return;
            }

            const response = await axios.post(`/student-curriculum/`, {
                ...newCurriculum,
                schoolClassId: Number(classId),
                teachers: selectedTeachers,
                disciplines: selectedDisciplines
            });

            setNewCurriculum({ semester: '', year: '' });
            setSelectedTeachers([]);
            setSelectedDisciplines([]);
            fetchCurriculum();
            fetchTeachersAndDisciplines(); // Optionally refresh the teachers and disciplines list
        } catch (error) {
            console.error("Error adding curriculum: ", error);
        }
    };

    const handleEditTeacherDiscipline = (item) => setEditTeacherDiscipline({ ...item });

    const handleSaveTeacherDiscipline = async (updatedItem) => {
        try {
            await axios.put(`/curriculums-teachers-disciplines/${updatedItem.id}`, updatedItem);
            setEditTeacherDiscipline(null);
            fetchTeachersAndDisciplines();
        } catch (error) {
            console.error('Error saving teacher and discipline:', error);
        }
    };

    const handleAddTeacherDiscipline = async () => {
        try {
            const newEntry = {
                teacherId: newTeacherDiscipline.teacherId,
                disciplineId: newTeacherDiscipline.disciplineId,
                curriculumId: curriculum.id
            };

            // Check if the entry already exists in teachersAndDisciplines
            const exists = teachersAndDisciplines.some(item =>
                item.teacherId == newEntry.teacherId && item.disciplineId == newEntry.disciplineId
            );

            if (exists) {
                setDuplicateError("This teacher and discipline combination already exists.");
                return;
            }

            // Add the new entry to teachersAndDisciplines array
            const response = await axios.post('/curriculums-teachers-disciplines/', newEntry);

            setNewTeacherDiscipline({ teacherId: '', disciplineId: '' });
            fetchTeachersAndDisciplines();
        } catch (error) {
            console.error('Error adding teacher and discipline:', error);
        }
    };

    const handleDeleteTeacherDiscipline = async (id) => {
        if (window.confirm('Are you sure you want to delete this teacher and discipline?')) {
            try {
                await axios.delete(`/curriculums-teachers-disciplines/${id}`);
                fetchTeachersAndDisciplines();
            } catch (error) {
                console.error('Error deleting teacher and discipline: ', error);
            }
        }
    };

    // Helper function to find teacher or discipline by ID
    const findById = (id, array) => array.find(item => item.id === id);

    // Function to filter out disciplines already assigned to teachers
    const filterAssignedDisciplines = () => {
        const assignedDisciplineIds = teachersAndDisciplines.map(item => item.disciplineId);
        return disciplines.filter(discipline => !assignedDisciplineIds.includes(discipline.id));
    };

    return (
        <div className="container mt-4">
            {curriculum && !editCurriculum ? (
                <div className="card">
                    <div className="card-body">
                        <h3 className="card-title">Curriculum Information</h3>
                        <div className="list-group-item d-flex justify-content-between align-items-center">
                            <div className="list-group-item d-flex align-items-center">
                                <span className="me-3">Semester: {curriculum.semester}</span>
                                <span>Year: {curriculum.year}</span>
                            </div>
                            <div>
                                <button className="btn btn-primary me-2" onClick={() => handleEditCurriculum(curriculum)}>
                                    Edit Curriculum
                                </button>
                                {/*<button className="btn btn-danger me-2" onClick={() => handleDeleteCurriculum(curriculum.id)}>*/}
                                {/*    Delete Curriculum*/}
                                {/*</button>*/}
                            </div>
                        </div>
                    </div>
                </div>
            ) : !curriculum && !editCurriculum ? (
                <div className="card mt-4">
                    <h3 className="card-header">Add New Curriculum</h3>
                    <div className="card-body">
                        <div className="form-group">
                            <label htmlFor="semester">Semester:</label>
                            <input
                                type="text"
                                id="semester"
                                name="semester"
                                className="form-control"
                                value={newCurriculum.semester}
                                onChange={(e) => handleChange(e, setNewCurriculum)}
                            />
                        </div>
                        <div className="form-group">
                            <label htmlFor="year">Year:</label>
                            <input
                                type="text"
                                id="year"
                                name="year"
                                className="form-control"
                                value={newCurriculum.year}
                                onChange={(e) => handleChange(e, setNewCurriculum)}
                            />
                        </div>
                        <div className="form-group">
                            <label htmlFor="teacherSelect">Select Teachers:</label>
                            <select
                                id="teacherSelect"
                                className="form-control mb-3"
                                multiple
                                value={selectedTeachers}
                                onChange={(e) => setSelectedTeachers([...e.target.selectedOptions].map(option => option.value))}
                            >
                                <option value="">Select teachers</option>
                                {teachers.map(teacher => (
                                    <option key={teacher.id} value={teacher.id}>
                                        {teacher.name}
                                    </option>
                                ))}
                            </select>
                        </div>
                        <div className="form-group">
                            <label htmlFor="disciplineSelect">Select Disciplines:</label>
                            <select
                                id="disciplineSelect"
                                className="form-control mb-3"
                                multiple
                                value={selectedDisciplines}
                                onChange={(e) => setSelectedDisciplines([...e.target.selectedOptions].map(option => option.value))}
                            >
                                <option value="">Select disciplines</option>
                                {filterAssignedDisciplines().map(discipline => (
                                    <option key={discipline.id} value={discipline.id}>
                                        {discipline.name}
                                    </option>
                                ))}
                            </select>
                        </div>
                        <button className="btn btn-success" onClick={handleAddCurriculum}>
                            Add Curriculum
                        </button>
                    </div>
                </div>
            ) : (
                editCurriculum && (
                    <div className="card mt-4">
                        <h3 className="card-header">Edit Curriculum</h3>
                        <div className="card-body">
                            <div className="form-group">
                                <label htmlFor="semester">Semester:</label>
                                <input
                                    type="text"
                                    id="semester"
                                    name="semester"
                                    className="form-control"
                                    value={editCurriculum.semester}
                                    onChange={(e) => handleChange(e, setEditCurriculum)}
                                />
                            </div>
                            <div className="form-group">
                                <label htmlFor="year">Year:</label>
                                <input
                                    type="text"
                                    id="year"
                                    name="year"
                                    className="form-control"
                                    value={editCurriculum.year}
                                    onChange={(e) => handleChange(e, setEditCurriculum)}
                                />
                            </div>
                            <button className="btn btn-success btn-sm me-1" onClick={handleSaveCurriculum}>
                                Save Curriculum
                            </button>
                            <button className="btn btn-secondary btn-sm me-1" onClick={() => setEditCurriculum(null)}>
                                Cancel
                            </button>
                        </div>
                    </div>
                )
            )}

            <div className="card mt-4">
                {duplicateError && (
                    <div className="alert alert-danger" role="alert">
                        {duplicateError}
                    </div>
                )}
                <h3 className="card-header">Add Teacher and Discipline</h3>
                <div className="card-body">
                    <div className="form-group">
                        <label htmlFor="teacherSelect">Teacher:</label>
                        <select
                            id="teacherSelect"
                            className="form-control m-3 w-50"
                            value={newTeacherDiscipline.teacherId}
                            onChange={(e) => setNewTeacherDiscipline({ ...newTeacherDiscipline, teacherId: e.target.value })}
                        >
                            <option value="">Select a teacher</option>
                            {teachers.map(teacher => (
                                <option key={teacher.id} value={teacher.id}>
                                    {teacher.name}
                                </option>
                            ))}
                        </select>
                    </div>
                    <div className="form-group">
                        <label htmlFor="disciplineSelect">Discipline:</label>
                        <select
                            id="disciplineSelect"
                            className="form-control m-3 w-50"
                            value={newTeacherDiscipline.disciplineId}
                            onChange={(e) => setNewTeacherDiscipline({ ...newTeacherDiscipline, disciplineId: e.target.value })}
                        >
                            <option value="">Select a discipline</option>
                            {filterAssignedDisciplines().map(discipline => (
                                <option key={discipline.id} value={discipline.id}>
                                    {discipline.name}
                                </option>
                            ))}
                        </select>
                    </div>
                    <button className="btn btn-success" onClick={handleAddTeacherDiscipline}>
                        Add Teacher and Discipline
                    </button>
                </div>
            </div>

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
                                            <button className="btn btn-primary btn-sm me-2" onClick={() => handleEditTeacherDiscipline(item)}>
                                                Edit
                                            </button>
                                            <button className="btn btn-danger btn-sm me-2" onClick={() => handleDeleteTeacherDiscipline(item.id)}>
                                                Delete
                                            </button>
                                            <Link
                                                to={`/admin/school-class/${classId}/students-grades/teacher/${teacher.id}/discipline/${item.disciplineId}/absences`}
                                                className="btn btn-info text-white btn-sm me-2"
                                            >
                                                Absence
                                            </Link>
                                            <Link
                                                to={`/admin/school-class/${classId}/students-grades/teacher/${teacher.id}/discipline/${item.disciplineId}/grades`}
                                                className="btn btn-info text-white btn-sm me-2"
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

            {editTeacherDiscipline && (
                <EditTeacherDiscipline
                    editTeacherDiscipline={editTeacherDiscipline}
                    teachers={teachers}
                    disciplines={disciplines}
                    onSave={handleSaveTeacherDiscipline}
                    onCancel={() => setEditTeacherDiscipline(null)}
                />
            )}
        </div>
    );
};

export default CurriculumDetails;
