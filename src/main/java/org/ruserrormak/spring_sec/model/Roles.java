package org.ruserrormak.spring_sec.model;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;

@Entity
@Table
public class Roles implements GrantedAuthority {

    @Id
    private Long id;
    private String name;

    public Roles() {}

    public Roles(Long id) { this.id = id; }

    public Roles(String name, Long id) {
        this.name = name;
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getAuthority() {
        return getName();
    }
}
