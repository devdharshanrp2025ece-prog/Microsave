package com.project.microsave.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.microsave.Entity.Group;

public interface GroupRepo extends JpaRepository<Group, Integer> {
}