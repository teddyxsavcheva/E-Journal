// App.jsx
import React from 'react';
import {BrowserRouter as Router, Link, Route, Routes} from 'react-router-dom';
import SchoolList from './adminView/SchoolList';
import SchoolDetails from './adminView/SchoolDetails';
import TeacherList from './teacherView/TeacherList';
import SchoolClassesList from "./adminView/SchoolClassList";
import Headmaster from "./adminView/Headmaster";
import AddSchool from "./adminView/AddSchool";
import TeacherQualifications from "./adminView/TeacherQualifications";
import SchoolClassDetails from "./adminView/SchoolClassDetails";
import Caregivers from "./adminView/Caregivers";
import StudentView from "./studentView/StudentView";
import CaregiverView from "./adminView/CaregiverView";
import AddDiscipline from "./adminView/AddDiscipline";
import CurriculumDetails from "./adminView/CurriculumDetails";
import HomePage from "./adminView/HomePage";
import StudentsGrades from "./adminView/StudentsGrades";
import StudentsAbsences from "./adminView/StudentsAbsences";

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
                        <Route path="/admin/school-class/:classId/students-grades/teacher/:teacherId/discipline/:disciplineId/absences" element={<StudentsAbsences />} />


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
