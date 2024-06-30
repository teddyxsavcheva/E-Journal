import { useState, useEffect, useCallback } from 'react';
import axios from '../axiosInstance';

const useCurriculum = (classId) => {
    const [curriculum, setCurriculum] = useState(null);
    const [error, setError] = useState(null);

    const fetchCurriculum = useCallback(async () => {
        try {
            const response = await axios.get(`student-curriculum/school-class/${classId}`);
            setCurriculum(response.data);
        } catch (error) {
            setError(error);
            console.error('There was an error fetching the curriculum!', error);
        }
    }, [classId]);

    useEffect(() => {
        fetchCurriculum();
    }, [fetchCurriculum]);

    return { curriculum, error, fetchCurriculum, setCurriculum };
};

export default useCurriculum;
