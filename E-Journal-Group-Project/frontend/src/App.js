// App.jsx
import React from 'react';
import {BrowserRouter as Router, Link, Route, Routes} from 'react-router-dom';
import SchoolList from './components/SchoolList';
import SchoolDetails from './components/SchoolDetails';
import TeacherList from './components/TeacherList';
import SchoolClassesList from "./components/SchoolClassList";
import Headmaster from "./components/Headmaster";
import AddSchool from "./components/AddSchool";
import TeacherQualifications from "./components/TeacherQualifications";
import SchoolClassDetails from "./components/SchoolClassDetails";
import Caregivers from "./components/Caregivers";
import StudentView from "./components/StudentView";
import CaregiverView from "./components/CaregiverView";
import AddDiscipline from "./components/AddDiscipline";
import CurriculumDetails from "./components/CurriculumDetails";
import HomePage from "./components/HomePage";
import StudentsGrades from "./components/StudentsGrades";

function App() {
    return (
        <Router>
            <div className="App">
                <header className="App-header">
                    <h1>School Management System</h1>
                </header>
                <main>
                    <Routes>
                        <Route path="/" element={<HomePage/>} />
                        <Route path="/admin" element={<SchoolList />} />
                        <Route path="/admin/add-school" element={<AddSchool />} />
                        <Route path="/admin/add-discipline" element={<AddDiscipline />} />
                        <Route path="/admin/school/:schoolId" element={<SchoolDetails />} />

                        <Route path="/admin/school/:schoolId/teachers" element={<TeacherList />} />
                        <Route path="/admin/school/:schoolId/teacher/:id/qualifications" element={<TeacherQualifications/>} />

                        <Route path="/admin/school/:schoolId/classes" element={<SchoolClassesList />} />
                        <Route path="/admin/school/:schoolId/class/:classId" element={<SchoolClassDetails />} />
                        <Route path="/admin/school/:schoolId/class/:classId/curriculum" element={<CurriculumDetails />} />

                        <Route path="/admin/school-class/:classId/students-grades/teacher/:teacherId/discipline/:disciplineId/grades" element={<StudentsGrades />} />


                        <Route path="/admin/school/:schoolId/class/:classId/student/:studentId/caregivers" element={<Caregivers />} />

                        <Route path="/admin/school/:schoolId/headmaster" element={<Headmaster />} />

                        <Route path="/student/:studentId" element={<StudentView />} />
                        <Route path="/caregiver/:id" element={<CaregiverView />} />



                    </Routes>
                </main>
            </div>
        </Router>
    );
}

export default App;
