package com.cap.project.demo.domain;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "departments")
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "department_id")
    private Long id;

    @Column(name = "department_type" , columnDefinition = "varchar(255) NOT NULL UNIQUE ")
    private Long type;

    // department know list of experts
    @OneToMany(mappedBy = "department")
    private List<Expert> experts = new ArrayList<>();

    public Department() {
    }

    @Builder
    public Department(Long type) {
        this.type = type;
    }
}
