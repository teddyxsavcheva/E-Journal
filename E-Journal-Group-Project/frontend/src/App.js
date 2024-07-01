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
import School from "./headmasterView/School";
import Teachers from "./headmasterView/Teachers";
import ViewTeacherQualifications from "./headmasterView/ViewTeacherQualifications";
import SchoolClasses from "./headmasterView/SchoolClasses";
import SchoolClass from "./headmasterView/SchoolClass";
import SeeCaregivers from "./headmasterView/SeeCaregivers";
import Curriculum from "./headmasterView/Curriculum";
import Grades from "./headmasterView/Grades";
import Absences from "./headmasterView/Absences";
import EditSchool from "./adminView/EditSchool";
import TSchoolClasses from "./teacherView/TSchoolClasses";
import TCurriculum from "./teacherView/TCurriculum";
import TAbsences from "./teacherView/TAbsences";
import TGrades from "./teacherView/TGrades";
import StatisticsTeacher from "./headmasterView/StatisticsTeacher";
import Statistics from "./adminView/Statistics";


function App() {
    return (
        <Router>
            <div className="App">
                <main>
                    <Routes>
                        <Route path="/" element={<HomePage/>} />

                        {/*ADMIN*/}
                        <Route path="/admin" element={<SchoolList />} />
                        <Route path="/admin/statistics" element={<Statistics />} />
                        <Route path="/admin/add-school" element={<AddSchool />} />
                        <Route path="/admin/edit-school/:schoolId" element={<EditSchool />} />
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

                        {/*STUDENT*/}
                        <Route path="/student/:studentId" element={<StudentView />} />

                        {/*CAREGIVER*/}
                        <Route path="/caregiver/:id" element={<CaregiverView />} />

                        {/*HEADMASTER*/}
                        <Route path="/headmaster/:headmasterId" element={<School />} />
                        <Route path="/headmaster/:headmasterId/statistics" element={<StatisticsTeacher />} />
                        <Route path="/headmaster/:headmasterId/school/:schoolId/teachers" element={<Teachers />} />
                        <Route path="/headmaster/:headmasterId/school/:schoolId/teacher/:teacherId/qualifications" element={<ViewTeacherQualifications />} />
                        <Route path="/headmaster/:headmasterId/school/:schoolId/classes" element={<SchoolClasses />} />
                        <Route path="/headmaster/:headmasterId/school/:schoolId/class/:classId" element={<SchoolClass />} />
                        <Route path="/headmaster/:headmasterId/school/:schoolId/class/:classId/curriculum" element={<Curriculum />} />
                        <Route path="/headmaster/:headmasterId/school/:schoolId/class/:classId/student/:studentId/caregivers" element={<SeeCaregivers />} />

                        <Route path="/headmaster/:headmasterId/school/:schoolId/school-class/:classId/students-grades/teacher/:teacherId/discipline/:disciplineId/grades" element={<Grades />} />
                        <Route path="/headmaster/:headmasterId/school/:schoolId/school-class/:classId/students-grades/teacher/:teacherId/discipline/:disciplineId/absences" element={<Absences />} />

                        {/*TEACHER*/}
                        <Route path="/teacher/:teacherId" element={<TSchoolClasses/>} />
                        <Route path="/teacher/:teacherId/class/:classId" element={<TCurriculum/>} />
                        <Route path="/teacher/:teacherId/class/:classId/discipline/:disciplineId/absences" element={<TAbsences/>} />
                        <Route path="/teacher/:teacherId/class/:classId/discipline/:disciplineId/grades" element={<TGrades/>} />
                    </Routes>
                </main>
            </div>
        </Router>
    );
}

export default App;
