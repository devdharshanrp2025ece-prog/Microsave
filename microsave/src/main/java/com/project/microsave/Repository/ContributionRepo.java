package com.project.microsave.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.microsave.Entity.Contribution;

public interface ContributionRepo extends JpaRepository<Contribution, Integer> {
}