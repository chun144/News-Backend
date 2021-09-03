package com.chun.springboot.repository;

import java.util.List;

import com.chun.springboot.entity.NewEntity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewRepository extends JpaRepository<NewEntity, Long> {
    NewEntity findOneById(long id);
    List<NewEntity> findAllByCategoryCode(String category);
    List<NewEntity> findAllByCategoryCode(String category, Pageable pageable);
    List<NewEntity> findAllByDescriptionIgnoreCaseContaining(String description);
    Page<NewEntity> getAllBy(Pageable pageable);
    Page<NewEntity> getAllByDescriptionIgnoreCaseContaining(String description, Pageable pageable);
    List<NewEntity> findAllByDescriptionIgnoreCaseContaining(String description, Pageable pageable);
    
}
