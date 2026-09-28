package com.project.microsave.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.microsave.Entity.Repayment;

public interface RepaymentRepo extends JpaRepository<Repayment, Integer> {
}