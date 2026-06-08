package com.example.bai4.service;

import com.example.bai4.controller.CertificateClient;
import com.example.bai4.exception.CourseNotCompletedException;
import com.example.bai4.exception.NotFoundException;
import com.example.bai4.model.dto.request.GetCertificateRequest;
import com.example.bai4.model.dto.response.EnrollmentSummary;
import com.example.bai4.model.entity.Course;
import com.example.bai4.model.entity.Enrollment;
import com.example.bai4.model.entity.Student;
import com.example.bai4.repository.CourseRepository;
import com.example.bai4.repository.EnrollmentRepository;
import com.example.bai4.security.principal.StudentPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class EnrollmentService {
    private final EnrollmentRepository enrollmentRepository;
    private final CourseRepository courseRepository;
    private final CertificateClient certificateClient;

    public EnrollmentSummary enrollmentSummary() {
        // Lấy người dùng hiện trại
        StudentPrincipal studentPrincipal = (StudentPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Student student = studentPrincipal.getStudent();
        // Lấy danh sách khoá học đã hoàn thành
        List<Enrollment> enrollments = enrollmentRepository.findByStudentAndLessonCompleted(student, 100D);
        List<String> courseNames = enrollments.stream().map(e -> e.getCourse().getTitle()).toList();
        return new EnrollmentSummary(
                enrollments.size(),
                courseNames
        );
    }

    public String getCertificate(Long courseId) {
        // Kiểm tra khoá học
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new NotFoundException("Course not found"));
        // Lấy người dùng hiện tại
        StudentPrincipal studentPrincipal = (StudentPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Student student = studentPrincipal.getStudent();
        // Kiểm tra khoá học đã hoàn thành chưa
        if (!enrollmentRepository.existsByStudentAndCourseAndLessonCompleted(student, course, 100D)) {
            throw new CourseNotCompletedException("Course not completed");
        }
        return certificateClient.generateCertificate(new GetCertificateRequest(
                student.getFullName(),
                course.getTitle()
        ));
    }
}
