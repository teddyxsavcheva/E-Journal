// App.jsx
import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import SchoolList from './components/SchoolList';
import SchoolDetails from './components/SchoolDetails';
import TeacherList from './components/TeacherList';
import SchoolClassesList from "./components/SchoolClassList";
import Headmaster from "./components/Headmaster";
import AddSchool from "./components/AddSchool";
import TeacherQualifications from "./components/TeacherQualifications";
import SchoolClassDetails from "./components/SchoolClassDetails";

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
                        <Route path="/admin/add-school" element={<AddSchool />} />
                        <Route path="/admin/school/:schoolId" element={<SchoolDetails />} />

                        <Route path="/admin/school/:schoolId/teachers" element={<TeacherList />} />
                        <Route path="/admin/school/:schoolId/teacher/:id/qualifications" element={<TeacherQualifications/>} />

                        <Route path="/admin/school/:schoolId/classes" element={<SchoolClassesList />} />
                        <Route path="/admin/school/:schoolId/class/:classId" element={<SchoolClassDetails />} />
                        <Route path="`/admin/school/:schoolId/class/:classId/student/:studentId/caregivers`" element={<SchoolClassDetails />} />


                        <Route path="/admin/school/:schoolId/headmaster" element={<Headmaster />} />
                    </Routes>
                </main>
            </div>
        </Router>
    );
}

export default App;
