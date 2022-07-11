package com.cap.project.demo.repository;

import com.cap.project.demo.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {


    @Query(value = "select u from User u where u.loginId = :loginId")
    Optional<User> findByLoginId(@Param("loginId") String loginId);

    @Query(value = "select u from User u where u.name = :name and u.email = :email")
    User findByNameAndEmail(@Param("name") String name, @Param("email") String email);

    @Query(value = "select u from User u where u.name = :name and u.loginId = :loginId and u.email = :email")
    User findByNameAndLoginIdAndEmail(@Param("name") String name,@Param("loginId") String id,@Param("email") String email);
}
