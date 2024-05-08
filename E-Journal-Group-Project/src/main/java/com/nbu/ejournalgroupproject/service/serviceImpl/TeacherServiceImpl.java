package com.nbu.ejournalgroupproject.service.serviceImpl;

import com.nbu.ejournalgroupproject.dto.TeacherDTO;
import com.nbu.ejournalgroupproject.mappers.TeacherMapper;
import com.nbu.ejournalgroupproject.model.Teacher;
import com.nbu.ejournalgroupproject.repository.TeacherRepository;
import com.nbu.ejournalgroupproject.service.TeacherService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class TeacherServiceImpl implements TeacherService {

    @Autowired
    private final TeacherRepository teacherRepository;

    @Autowired
    private final TeacherMapper teacherMapper;

    @Override
    public void createTeacher(TeacherDTO teacherDTO) {
        Teacher teacher = teacherMapper.TeacherDtoToEntity(teacherDTO);
        teacherRepository.save(teacher);
    }

    @Override
    public TeacherDTO getTeacher(Long teacherId) {
       Teacher teacher = teacherRepository.findById(teacherId).orElseThrow(EntityNotFoundException::new);
       TeacherDTO teacherDTO = teacherMapper.TeacherEntityToDto(teacher);
       return teacherDTO;
    }

    @Override
    public List<TeacherDTO> getTeachersFromSchool(Long schoolId) {
        List<Teacher> teachers = teacherRepository.findAllBySchoolId(schoolId);
        return teachers
                .stream()
                .map(teacherMapper::TeacherEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<TeacherDTO> getTeachers() {
        List<Teacher> teacherEntities = teacherRepository.findAll();
        return teacherEntities
                .stream()
                .map(teacherMapper::TeacherEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public void updateTeacher(Long teacherId, TeacherDTO teacherDTO) {

    }



    @Override
    public boolean deleteTeacher(Long teacherId) {
        try{
            getTeacher(teacherId);
        } catch (Exception e){
            return false;
        }
        teacherRepository.deleteById(teacherId);
        return true;
    }
}
