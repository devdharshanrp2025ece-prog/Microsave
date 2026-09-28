package com.project.microsave.Service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.project.microsave.Entity.Loan;
import com.project.microsave.Entity.Repayment;
import com.project.microsave.Repository.LoanRepo;
import com.project.microsave.Repository.RepaymentRepo;

@Service
public class RepaymentService {

    private final RepaymentRepo repaymentRepository;
    private final LoanRepo loanRepository;

    public RepaymentService(RepaymentRepo repaymentRepository,
                            LoanRepo loanRepository) {

        this.repaymentRepository = repaymentRepository;
        this.loanRepository = loanRepository;
    }

    public Repayment addRepayment(Repayment repayment) {

        if (repayment.getAmount() == null
                || repayment.getAmount() <= 0) {

            throw new RuntimeException(
                    "Repayment amount must be greater than zero");
        }

        if (repayment.getLoanId() == null) {

            throw new RuntimeException(
                    "Loan ID is required");
        }

        Loan loan = loanRepository.findById(repayment.getLoanId())
                .orElseThrow(() -> new RuntimeException(
                        "Loan not found"));

        if (!"ACTIVE".equalsIgnoreCase(loan.getStatus())) {

            throw new RuntimeException(
                    "Loan is not active");
        }

        double totalRepaid = 0.0;

        List<Repayment> repayments =
                repaymentRepository.findAll();

        for (Repayment existingRepayment : repayments) {

            if (existingRepayment.getLoanId()
                    .equals(repayment.getLoanId())) {

                totalRepaid += existingRepayment.getAmount();
            }
        }

        double outstandingAmount =
                loan.getAmount() - totalRepaid;

        if (repayment.getAmount() > outstandingAmount) {

            throw new RuntimeException(
                    "Repayment amount exceeds outstanding loan amount. "
                            + "Outstanding amount: " + outstandingAmount);
        }

        repayment.setId(getNextId());

        Repayment savedRepayment =
                repaymentRepository.save(repayment);

        double newTotalRepaid =
                totalRepaid + repayment.getAmount();

        if (newTotalRepaid >= loan.getAmount()) {

            loan.setStatus("COMPLETED");

            loanRepository.save(loan);
        }

        return savedRepayment;
    }

    public List<Repayment> getAllRepayments() {
        return repaymentRepository.findAll();
    }

    public Repayment getRepaymentById(Integer id) {

        return repaymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        "Repayment not found"));
    }

    public List<Repayment> getRepaymentsByLoan(Integer loanId) {

        loanRepository.findById(loanId)
                .orElseThrow(() -> new RuntimeException(
                        "Loan not found"));

        List<Repayment> repayments =
                repaymentRepository.findAll();

        List<Repayment> result = new ArrayList<>();

        for (Repayment repayment : repayments) {

            if (repayment.getLoanId().equals(loanId)) {
                result.add(repayment);
            }
        }

        return result;
    }

    public void deleteRepayment(Integer id) {

        if (!repaymentRepository.existsById(id)) {

            throw new RuntimeException(
                    "Repayment not found");
        }

        repaymentRepository.deleteById(id);
    }

    private Integer getNextId() {

        List<Repayment> repayments =
                repaymentRepository.findAll();

        Integer maxId = 0;

        for (Repayment repayment : repayments) {

            if (repayment.getId() > maxId) {
                maxId = repayment.getId();
            }
        }

        return maxId + 1;
    }
}