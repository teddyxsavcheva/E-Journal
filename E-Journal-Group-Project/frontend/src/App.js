// App.jsx
import React from 'react';
import {BrowserRouter as Router, Link, Route, Routes} from 'react-router-dom';
import SchoolList from './adminView/SchoolList';
import SchoolDetails from './adminView/SchoolDetails';
import TeacherList from './adminView/TeacherList';
import SchoolClassesList from "./adminView/SchoolClassList";
import Headmaster from "./adminView/Headmaster";
import AddSchool from "./adminView/AddSchool";
import TeacherQualifications from "./adminView/TeacherQualifications";
import SchoolClassDetails from "./adminView/SchoolClassDetails";
import Caregivers from "./adminView/Caregivers";
import StudentView from "./studentView/StudentView";
import CaregiverView from "./caregiverView/CaregiverView";
import AddDiscipline from "./adminView/AddDiscipline";
import CurriculumDetails from "./adminView/CurriculumDetails";
import HomePage from "./adminView/HomePage";
import StudentsGrades from "./adminView/StudentsGrades";
import StudentsAbsences from "./adminView/StudentsAbsences";
import School from "./headmasterView/done/School";
import Teachers from "./headmasterView/done/Teachers";
import ViewTeacherQualifications from "./headmasterView/done/ViewTeacherQualifications";
import SchoolClasses from "./headmasterView/done/SchoolClasses";
import SchoolClass from "./headmasterView/done/SchoolClass";
import SeeCaregivers from "./headmasterView/done/SeeCaregivers";
import Curriculum from "./headmasterView/done/Curriculum";
import Grades from "./headmasterView/done/Grades";
import Absences from "./headmasterView/Absences";

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


                        <Route path="/headmaster/:headmasterId" element={<School />} />
                        <Route path="/headmaster/:headmasterId/school/:schoolId/teachers" element={<Teachers />} />
                        <Route path="/headmaster/:headmasterId/school/:schoolId/teacher/:teacherId/qualifications" element={<ViewTeacherQualifications />} />
                        <Route path="/headmaster/:headmasterId/school/:schoolId/classes" element={<SchoolClasses />} />
                        <Route path="/headmaster/:headmasterId/school/:schoolId/class/:classId" element={<SchoolClass />} />
                        <Route path="/headmaster/:headmasterId/school/:schoolId/class/:classId/curriculum" element={<Curriculum />} />
                        <Route path="/headmaster/:headmasterId/school/:schoolId/class/:classId/student/:studentId/caregivers" element={<SeeCaregivers />} />

                        <Route path="/headmaster/:headmasterId/school/:schoolId/school-class/:classId/students-grades/teacher/:teacherId/discipline/:disciplineId/grades" element={<Grades />} />
                        <Route path="/headmaster/:headmasterId/school/:schoolId/school-class/:classId/students-grades/teacher/:teacherId/discipline/:disciplineId/absences" element={<Absences />} />
                http://localhost:3000/headmaster/1            /school/1            /school-class/1/students-grades/teacher/2/discipline/3/grades

                    </Routes>
                </main>
            </div>
        </Router>
    );
}

export default App;
