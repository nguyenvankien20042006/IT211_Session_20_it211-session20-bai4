package com.example.bai4.repository;

import com.example.bai4.model.entity.Course;
import com.example.bai4.model.entity.Enrollment;
import com.example.bai4.model.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
    List<Enrollment> findByStudentAndLessonCompleted(Student student, Double lessonCompleted);

    Boolean existsByStudentAndCourseAndLessonCompleted(Student student, Course course, Double lessonCompleted);
}
