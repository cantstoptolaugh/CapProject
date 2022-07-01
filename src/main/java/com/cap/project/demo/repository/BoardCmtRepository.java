package com.cap.project.demo.repository;

import com.cap.project.demo.domain.BoardCmt;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardCmtRepository extends JpaRepository<BoardCmt, Long> {

}
