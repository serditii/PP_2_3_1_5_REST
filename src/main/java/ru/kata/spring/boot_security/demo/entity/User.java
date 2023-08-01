package ru.kata.spring.boot_security.demo.entity;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.Set;


@Entity
@Data
@Table(name = "users")
@DynamicUpdate
public class User implements UserDetails {

    @ManyToMany(fetch = FetchType.LAZY)
    private Set<Role> roles;

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private long id;

    @Size(min = 2, max = 30, message = "Name must be between 2 and 30 characters")
    private String username;

    @Size(min = 2, max = 30, message = "Name must be between 2 and 30 characters")
    private String name;

    @Size(min = 2, max = 30, message = "Lastname must be between 2 and 30 characters")
    private String lastname;

    @NotEmpty(message = "The field must not be empty")
    @Email(message = "Email must be valid")
    private String email;

    @Min(value = 0, message = "Age must be between 0 and 100 years old")
    private byte age;

    private String password;

    @Transient
    public String role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

