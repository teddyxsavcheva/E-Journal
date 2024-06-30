import axios from '../axiosInstance';

export const fetchAllTeachersFromSchool = async (schoolId) => {
    try {
        const response = await axios.get(`/teacher/school-id/${schoolId}`);
        // Assuming the response data is an array of objects with id and name properties
        return response.data.map(teacher => ({ id: teacher.id, name: teacher.name }));
    } catch (error) {
        console.error('Error fetching teachers:', error);
        throw error;
    }
};

export const fetchAllDisciplines = async () => {
    try {
        const response = await axios.get('/disciplines/');
        // Assuming the response data is an array of objects with id and name properties
        return response.data.map(discipline => ({ id: discipline.id, name: discipline.name }));
    } catch (error) {
        console.error('Error fetching disciplines:', error);
        throw error;
    }
};
