// App.jsx
import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import SchoolList from './components/SchoolList';
import SchoolDetails from './components/SchoolDetails';
import TeacherList from './components/TeacherList';

function App() {
    return (
        <Router>
            <div className="App">
                <header className="App-header">
                    <h1>School Management System</h1>
                </header>
                <main>
                    <Routes>
                        <Route path="/" element={<SchoolList />} />
                        <Route path="/school/:schoolId" element={<SchoolDetails />} />
                        <Route path="/school/:schoolId/teachers" element={<TeacherList />} />
                    </Routes>
                </main>
            </div>
        </Router>
    );
}

export default App;
