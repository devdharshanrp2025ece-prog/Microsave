package com.project.microsave.Service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.project.microsave.Entity.Contribution;
import com.project.microsave.Entity.Loan;
import com.project.microsave.Entity.Member;
import com.project.microsave.Entity.Repayment;
import com.project.microsave.Repository.ContributionRepo;
import com.project.microsave.Repository.LoanRepo;
import com.project.microsave.Repository.MemberRepo;
import com.project.microsave.Repository.RepaymentRepo;

@Service
public class LoanService {

    private final LoanRepo loanRepository;
    private final MemberRepo memberRepository;
    private final ContributionRepo contributionRepository;
    private final RepaymentRepo repaymentRepository;

    public LoanService(LoanRepo loanRepository,
                       MemberRepo memberRepository,
                       ContributionRepo contributionRepository,
                       RepaymentRepo repaymentRepository) {

        this.loanRepository = loanRepository;
        this.memberRepository = memberRepository;
        this.contributionRepository = contributionRepository;
        this.repaymentRepository = repaymentRepository;
    }

    public Loan addLoan(Loan loan) {

        if (loan.getAmount() == null || loan.getAmount() <= 0) {

            throw new RuntimeException(
                    "Loan amount must be greater than zero");
        }

        if (loan.getMemberId() == null) {
            throw new RuntimeException("Member ID is required");
        }

        Member member = memberRepository.findById(loan.getMemberId())
                .orElseThrow(() -> new RuntimeException(
                        "Member not found"));

        List<Loan> loans = loanRepository.findAll();

        for (Loan existingLoan : loans) {

            if (existingLoan.getMemberId().equals(loan.getMemberId())
                    && "ACTIVE".equalsIgnoreCase(
                    existingLoan.getStatus())) {

                throw new RuntimeException(
                        "Member already has an active loan");
            }
        }

        Integer groupId = member.getGroupId();

        double totalContributions = 0.0;

        List<Contribution> contributions =
                contributionRepository.findAll();

        for (Contribution contribution : contributions) {

            if (contribution.getGroupId().equals(groupId)) {

                totalContributions += contribution.getAmount();
            }
        }

        double outstandingLoans = 0.0;

        for (Loan existingLoan : loans) {

            if (existingLoan.getMemberId() != null
                    && "ACTIVE".equalsIgnoreCase(
                    existingLoan.getStatus())) {

                Member existingMember = memberRepository
                        .findById(existingLoan.getMemberId())
                        .orElse(null);

                if (existingMember != null
                        && existingMember.getGroupId().equals(groupId)) {

                    outstandingLoans +=
                            getOutstandingAmount(existingLoan);
                }
            }
        }

        double availableAmount =
                totalContributions - outstandingLoans;

        if (loan.getAmount() > availableAmount) {

            throw new RuntimeException(
                    "Loan amount exceeds available contribution pool. "
                            + "Available amount: " + availableAmount);
        }

        loan.setId(getNextId());
        loan.setStatus("ACTIVE");

        return loanRepository.save(loan);
    }

    public List<Loan> getAllLoans() {
        return loanRepository.findAll();
    }

    public Loan getLoanById(Integer id) {

        return loanRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        "Loan not found"));
    }

    public List<Loan> getLoansByMember(Integer memberId) {

        memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException(
                        "Member not found"));

        List<Loan> loans = loanRepository.findAll();

        List<Loan> result = new ArrayList<>();

        for (Loan loan : loans) {

            if (loan.getMemberId().equals(memberId)) {
                result.add(loan);
            }
        }

        return result;
    }

    public List<Loan> getActiveLoans() {

        List<Loan> loans = loanRepository.findAll();

        List<Loan> result = new ArrayList<>();

        for (Loan loan : loans) {

            if ("ACTIVE".equalsIgnoreCase(loan.getStatus())) {
                result.add(loan);
            }
        }

        return result;
    }

    public void deleteLoan(Integer id) {

        if (!loanRepository.existsById(id)) {
            throw new RuntimeException("Loan not found");
        }

        loanRepository.deleteById(id);
    }

    private double getOutstandingAmount(Loan loan) {

        double repaidAmount = 0.0;

        List<Repayment> repayments =
                repaymentRepository.findAll();

        for (Repayment repayment : repayments) {

            if (repayment.getLoanId().equals(loan.getId())) {

                repaidAmount += repayment.getAmount();
            }
        }

        return loan.getAmount() - repaidAmount;
    }

    private Integer getNextId() {

        List<Loan> loans = loanRepository.findAll();

        Integer maxId = 0;

        for (Loan loan : loans) {

            if (loan.getId() > maxId) {
                maxId = loan.getId();
            }
        }

        return maxId + 1;
    }
}