package com.nbu.ejournalgroupproject.model.user;

import com.nbu.ejournalgroupproject.model.Caregiver;
import com.nbu.ejournalgroupproject.model.Headmaster;
import com.nbu.ejournalgroupproject.model.Student;
import com.nbu.ejournalgroupproject.model.Teacher;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name="user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String password;

    @ManyToMany(mappedBy = "users")
    private Set<Role> authorities;

    @NotNull
    private boolean accountNonExpired = true;

    @NotNull
    private boolean accountNonLocked = true;

    @NotNull
    private boolean credentialsNonExpired = true;

    @NotNull
    private boolean enabled = true;

    @OneToMany(mappedBy = "user")
    private Set<Teacher> teachers = new HashSet<>();

    @OneToMany(mappedBy = "user")
    private Set<Student> students = new HashSet<>();

    @OneToMany(mappedBy = "user")
    private Set<Caregiver> caregivers = new HashSet<>();

    @OneToMany(mappedBy = "user")
    private Set<Headmaster> headmasters = new HashSet<>();

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", authorities=" + authorities +
                ", accountNonExpired=" + accountNonExpired +
                ", accountNonBlocked=" + accountNonLocked +
                ", credentialsNonExpired=" + credentialsNonExpired +
                ", enabled=" + enabled +
                '}';
    }
}

