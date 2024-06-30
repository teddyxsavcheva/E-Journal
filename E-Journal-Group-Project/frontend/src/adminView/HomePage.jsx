import { Link } from "react-router-dom";
import React, { useState } from "react";

const HomePage = () => {
    const [id, setId] = useState('');
    const [role, setRole] = useState('');

    const handleIdChange = (e) => {
        setId(e.target.value);
    };

    const handleRoleChange = (e) => {
        setRole(e.target.value);
    };

    const generateUrl = () => {
        if (!id || !role) {
            return "#";
        }
        return `/${role}/${id}`;
    };

    return (
        <div className="container mt-5">
            <h1 className="text-center mb-4">Navigation Form</h1>
            <div className="card p-4">
            <Link to="/admin" className="btn btn-info text-white">Go to Admin Page</Link>
                <span className="text-center mt-3">OR</span>
                <div className="mb-3">
                    <label htmlFor="idInput" className="form-label">Enter ID:</label>
                    <input
                        id="idInput"
                        type="text"
                        value={id}
                        onChange={handleIdChange}
                        placeholder="Enter ID"
                        className="form-control"
                    />
                </div>
                <div className="mb-3">
                    <label htmlFor="roleSelect" className="form-label">Select Role:</label>
                    <select id="roleSelect" value={role} onChange={handleRoleChange} className="form-select">
                        <option value="">Choose role</option>
                        <option value="headmaster">Headmaster</option>
                        <option value="student">Student</option>
                        <option value="teacher">Teacher</option>
                        <option value="caregiver">Caregiver</option>
                    </select>
                </div>
                <div className="d-grid gap-2">
                    <Link to={generateUrl()} className="btn btn-primary">Go to {role} Page</Link>
                </div>
            </div>
        </div>
    );
};

export default HomePage;
