import React, { useState, useEffect } from 'react';

const EditTeacherDiscipline = ({ editTeacherDiscipline, teachers, disciplines, onSave, onCancel }) => {
    const [teacherDiscipline, setTeacherDiscipline] = useState(editTeacherDiscipline);

    useEffect(() => {
        setTeacherDiscipline(editTeacherDiscipline);
    }, [editTeacherDiscipline]);

    const handleChange = (e) => {
        const { name, value } = e.target;
        setTeacherDiscipline(prev => ({ ...prev, [name]: value }));
    };

    const handleSubmit = (e) => {
        e.preventDefault();
        onSave(teacherDiscipline);
    };

    return (
        <div className="card mt-4">
            <h3 className="card-header">Edit Teacher and Discipline</h3>
            <div className="card-body">
                <form onSubmit={handleSubmit}>
                    <div className="form-group">
                        <label className="me-4" htmlFor="teacherSelect">Select Teacher:</label>
                        <select
                            id="teacherSelect"
                            name="teacherId"
                            className="form-control mb-3 w-50"
                            value={teacherDiscipline.teacherId}
                            onChange={handleChange}
                        >
                            <option value="">Select a teacher</option>
                            {teachers.map(teacher => (
                                <option key={teacher.id} value={teacher.id}>{teacher.name}</option>
                            ))}
                        </select>
                    </div>
                    <div className="form-group">
                        <label className="me-3" htmlFor="disciplineSelect">Select Discipline:</label>
                        <select
                            id="disciplineSelect"
                            name="disciplineId"
                            className="form-control mb-3 w-50"
                            value={teacherDiscipline.disciplineId}
                            onChange={handleChange}
                        >
                            <option value="">Select a discipline</option>
                            {disciplines.map(discipline => (
                                <option key={discipline.id} value={discipline.id}>{discipline.name}</option>
                            ))}
                        </select>
                    </div>
                    <button type="submit" className="btn btn-success m-2">Save</button>
                    <button type="button" className="btn btn-secondary m-2" onClick={onCancel}>Cancel</button>
                </form>
            </div>
        </div>
    );
};

export default EditTeacherDiscipline;
