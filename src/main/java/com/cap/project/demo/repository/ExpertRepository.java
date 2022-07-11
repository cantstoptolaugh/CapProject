package com.cap.project.demo.repository;

import com.cap.project.demo.domain.Expert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ExpertRepository extends JpaRepository<Expert, Long> {

    @Query("SELECT e FROM Expert e WHERE e.loginId = :loginId")
    Optional<Expert> findByLoginId(@Param("loginId") String username);

    @Query(value = "SELECT e FROM Expert e WHERE e.name = :name AND e.certificate_number = :certificateNumber")
    Expert findByNameAndCertificateNumber(@Param("name") String name, @Param("certificateNumber") String certificationNumber);

    @Query(value = "SELECT e FROM Expert e WHERE e.name = :name AND e.loginId = :loginId AND e.certificate_number = :certificateNumber")
    Expert findByNameAndLoginIdAndCertificateNumber(@Param("name") String name, @Param("loginId") String loginId, @Param("certificateNumber") String certificationNumber);
}
