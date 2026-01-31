package com.employeemansys.Empmansys_backend.Model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Table(name="Users")
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Userauth {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, nullable = false)
    private Long phoneNumber;

    @Column(nullable = false)
    private String password;

    // One User has one Employee Profile
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonManagedReference
    private Empmodel employee;
}
