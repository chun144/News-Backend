package com.chun.springboot.repository;

import com.chun.springboot.entity.RoleEntity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
    RoleEntity findRoleByName(String name);
}
