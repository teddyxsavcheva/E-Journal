package com.nbu.ejournalgroupproject.model;

import com.nbu.ejournalgroupproject.model.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Table(name = "headmaster")
@Entity
public class Headmaster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @NotBlank(message = "Name cannot be null")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    @Column(name = "name")
    private String name;

    @NotBlank(message = "Email cannot be null")
    @Email(message = "Email should be valid")
    @Column(name = "email")
    private String email;

    @NotNull(message = "School ID cannot be null")
    @OneToOne
    @JoinColumn(name = "school_id")
    private School school;
}


