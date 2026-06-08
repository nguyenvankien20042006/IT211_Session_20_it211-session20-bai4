package com.example.bai4.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class EnrollmentSummary {
    private Integer totalCompleted;
    private List<String> coursesNameCompleted;
}
