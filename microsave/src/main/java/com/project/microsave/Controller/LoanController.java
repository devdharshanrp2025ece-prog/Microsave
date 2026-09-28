package com.project.microsave.Controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.microsave.Entity.Loan;
import com.project.microsave.Service.LoanService;

@RestController
@RequestMapping("/api/loans")
public class LoanController {

    private final LoanService loanService;

    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    @PostMapping("/add")
    public Loan addLoan(@RequestBody Loan loan) {
        return loanService.addLoan(loan);
    }

    @GetMapping("/all")
    public List<Loan> getAllLoans() {
        return loanService.getAllLoans();
    }

    @GetMapping("/get/{id}")
    public Loan getLoanById(@PathVariable Integer id) {
        return loanService.getLoanById(id);
    }

    @GetMapping("/member/{memberId}")
    public List<Loan> getLoansByMember(
            @PathVariable Integer memberId) {

        return loanService.getLoansByMember(memberId);
    }

    @GetMapping("/active")
    public List<Loan> getActiveLoans() {
        return loanService.getActiveLoans();
    }

    @DeleteMapping("/delete/{id}")
    public String deleteLoan(@PathVariable Integer id) {

        loanService.deleteLoan(id);

        return "Loan deleted successfully";
    }
}