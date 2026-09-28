package com.project.microsave.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.microsave.Entity.Loan;

public interface LoanRepo extends JpaRepository<Loan, Integer> {
}