package com.employeemansys.Empmansys_backend.Model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "Employees")
@AllArgsConstructor
@NoArgsConstructor
public class Empmodel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; // Auto-generated

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String gender;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private Double expectedSalary;

    @Column(nullable = false)
    private Boolean knowsEnglish;

    @Column(nullable = false)
    private Float hscPercentage;

    @OneToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private Userauth user;

}
