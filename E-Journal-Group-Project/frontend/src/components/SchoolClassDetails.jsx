import React, { useState, useEffect, useCallback } from 'react';
import axios from '../axiosInstance';
import { useParams, Link } from 'react-router-dom';

const useStudents = (classId) => {
    const [students, setStudents] = useState([]);
    const [error, setError] = useState(null);

    const fetchStudents = useCallback(async () => {
        try {
            const response = await axios.get(`students/school-class/${classId}`);
            setStudents(response.data);
        } catch (error) {
            setError(error);
            console.error('There was an error fetching the students!', error);
        }
    }, [classId]);

    useEffect(() => {
        fetchStudents();
    }, [fetchStudents]);

    return { students, error, fetchStudents };
};

const SchoolClassDetails = () => {
    const { schoolId, classId } = useParams();
    const { students, error , fetchStudents} = useStudents(classId);
    const [editStudent, setEditStudent] = useState(null);
    const [newStudent, setNewStudent] = useState({ name: '', numberInClass: ''});

    const handleEditStudent = (student) => setEditStudent({...student});

    const handleChange = (e, setter) => {
        const {name, value} = e.target;
        setter((prev) => ({...prev, [name]:value}));
    }

    const handleSaveStudent = async() => {
        try{
            await axios.put(`/students/${editStudent.id}`,{
                ...editStudent,
                schoolClassId : Number(classId)
            });
            setEditStudent(null);
            fetchStudents();
        } catch(error){
            console.error('Error saving student:', error);
        }
    };

    const handleDeleteStudent = async(studentId) => {
        if(window.confirm('Are you sure you want to delete this student?')) {
            try{
                await axios.delete(`/students/${studentId}`);
                fetchStudents();
            } catch(error) {
                console.error('Error deleting student: ', error);
            }
        }
    };

    const handleAddStudent = async() => {
        try{
            await axios.post(`/students/`, {
                ...newStudent,
                schoolClassId: Number(classId)
            });
            setNewStudent({name: '', numberInClass: ''});
            fetchStudents()
        } catch(error) {
            console.error("Error adding student: ", error);
        }
    };

    return (
        <div className="container mt-4">
            <div className="mb-4 d-flex justify-content-between align-items-center">
                <h2 className="mb-0">School Class Details for School ID: {schoolId}</h2>
                <Link to={`/admin/school/${schoolId}/class/${classId}/curriculum`}
                      className="btn btn-primary">
                    Curriculum
                </Link>
            </div>
            {error &&
                <div className="alert alert-danger text-center">
                    Error: {error.message}
                </div>
            }



            <div className="mb-4">
                <div className="card">
                    <h3 className="card-header">Add New Student</h3>
                    <div className="card-body">
                        <div className="form-group">
                            <input
                                type="text"
                                className="form-control mb-3"
                                placeholder="Student's Name"
                                name="name"
                                value={newStudent.name}
                                onChange={(e) => handleChange(e, setNewStudent)}
                            />
                        </div>
                        <div className="form-group">
                            <input
                                type="number"
                                className="form-control mb-3"
                                placeholder="Student's Number in Class"
                                name="numberInClass"
                                value={newStudent.numberInClass}
                                onChange={(e) => handleChange(e, setNewStudent)}
                            />
                        </div>
                        <button className="btn btn-success me-1" onClick={handleAddStudent}>
                            Add Student
                        </button>
                    </div>
                </div>
            </div>

            <ul className="list-group">
                {students.length > 0 ? (
                    students.map((student) => (
                        <li key={student.id} className="list-group-item mb-3">
                            <div className="d-flex align-items-center justify-content-between">
                                <div className="student-info">
                                    {editStudent && editStudent.id === student.id ? (
                                        <>
                                            <div className="mb-2">
                                                <input
                                                    type="text"
                                                    className="form-control"
                                                    name="name"
                                                    value={editStudent.name}
                                                    onChange={(e) => handleChange(e, setEditStudent)}
                                                />
                                            </div>
                                            <div className="mb-2">
                                                <input
                                                    type="number"
                                                    className="form-control"
                                                    name="numberInClass"
                                                    value={editStudent.numberInClass}
                                                    onChange={(e) => handleChange(e, setEditStudent)}
                                                />
                                            </div>
                                        </>
                                    ) : (
                                        <>
                                            <span className="d-block mb-1">{student.name}</span>
                                            <span>{student.numberInClass}</span>
                                        </>
                                    )}
                                </div>
                                <div className="actions">
                                    {editStudent && editStudent.id === student.id ? (
                                        <button className="btn btn-sm btn-success me-1" onClick={handleSaveStudent}>
                                            Save
                                        </button>
                                    ) : (
                                        <button className="btn btn-sm btn-success me-1" onClick={() => handleEditStudent(student)}>
                                            Edit
                                        </button>
                                    )}
                                    <Link to={`/admin/school/${schoolId}/class/${classId}/student/${student.id}/caregivers`}
                                          className="btn btn-primary btn-sm me-1">
                                        Caregivers
                                    </Link>
                                    <button className="btn btn-sm btn-danger me-1" onClick={() => handleDeleteStudent(student.id)}>
                                        Delete
                                    </button>
                                </div>
                            </div>
                        </li>
                    ))
                ) : (
                    <li className="list-group-item">No students available</li>
                )}
            </ul>
        </div>
    );
};

export default SchoolClassDetails;
