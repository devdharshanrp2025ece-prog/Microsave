package com.project.microsave.Controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.microsave.Entity.Repayment;
import com.project.microsave.Service.RepaymentService;

@RestController
@RequestMapping("/api/repayments")
public class RepaymentController {

    private final RepaymentService repaymentService;

    public RepaymentController(RepaymentService repaymentService) {
        this.repaymentService = repaymentService;
    }

    @PostMapping("/add")
    public Repayment addRepayment(
            @RequestBody Repayment repayment) {

        return repaymentService.addRepayment(repayment);
    }

    @GetMapping("/all")
    public List<Repayment> getAllRepayments() {
        return repaymentService.getAllRepayments();
    }

    @GetMapping("/get/{id}")
    public Repayment getRepaymentById(
            @PathVariable Integer id) {

        return repaymentService.getRepaymentById(id);
    }

    @GetMapping("/loan/{loanId}")
    public List<Repayment> getRepaymentsByLoan(
            @PathVariable Integer loanId) {

        return repaymentService.getRepaymentsByLoan(loanId);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteRepayment(
            @PathVariable Integer id) {

        repaymentService.deleteRepayment(id);

        return "Repayment deleted successfully";
    }
}