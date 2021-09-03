package com.chun.springboot.entity;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "roles")
public class RoleEntity extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Column(nullable = false)
    private String name;

    @ManyToMany(mappedBy = "roles")
    private Set<UserEntity> users;

    public RoleEntity() {
    }

    public RoleEntity(String string) {
        this.name = string;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Set<UserEntity> getUsers() {
        return users;
    }
    public void setUsers(Set<UserEntity> users) {
        this.users = users;
    }
    
}
