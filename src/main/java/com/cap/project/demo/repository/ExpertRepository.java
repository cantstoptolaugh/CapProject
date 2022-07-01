package com.cap.project.demo.repository;

import com.cap.project.demo.domain.Expert;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpertRepository extends JpaRepository<Expert, Long> {

}
