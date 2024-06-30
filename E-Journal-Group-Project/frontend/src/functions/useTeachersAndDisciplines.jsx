import { useState, useEffect, useCallback } from 'react';
import axios from '../axiosInstance';

const useTeachersAndDisciplines = (curriculumId) => {
    const [teachersAndDisciplines, setTeachersAndDisciplines] = useState([]);
    const [error, setError] = useState(null);

    const fetchTeachersAndDisciplines = useCallback(async () => {
        try {
            const response = await axios.get(`curriculums-teachers-disciplines/student-curriculum/${curriculumId}`);
            setTeachersAndDisciplines(response.data);
        } catch (error) {
            setError(error);
            console.error('There was an error fetching the teachers and disciplines!', error);
        }
    }, [curriculumId]);

    useEffect(() => {
        if (curriculumId) {
            fetchTeachersAndDisciplines();
        }
    }, [curriculumId, fetchTeachersAndDisciplines]);

    return { teachersAndDisciplines, error, fetchTeachersAndDisciplines };
};

export default useTeachersAndDisciplines;
