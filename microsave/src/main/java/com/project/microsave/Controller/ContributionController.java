package com.project.microsave.Controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.microsave.Entity.Contribution;
import com.project.microsave.Service.ContributionService;

@RestController
@RequestMapping("/api/contributions")
public class ContributionController {

    private final ContributionService contributionService;

    public ContributionController(
            ContributionService contributionService) {

        this.contributionService = contributionService;
    }

    @PostMapping("/add")
    public Contribution addContribution(
            @RequestBody Contribution contribution) {

        return contributionService.addContribution(contribution);
    }

    @PutMapping("/update/{id}")
    public Contribution updateContribution(
            @PathVariable Integer id,
            @RequestBody Contribution contribution) {

        return contributionService.updateContribution(
                id, contribution);
    }

    @GetMapping("/all")
    public List<Contribution> getAllContributions() {

        return contributionService.getAllContributions();
    }

    @GetMapping("/get/{id}")
    public Contribution getContributionById(
            @PathVariable Integer id) {

        return contributionService.getContributionById(id);
    }

    @GetMapping("/member/{memberId}")
    public List<Contribution> getContributionsByMember(
            @PathVariable Integer memberId) {

        return contributionService.getContributionsByMember(
                memberId);
    }

    @GetMapping("/group/{groupId}")
    public List<Contribution> getContributionsByGroup(
            @PathVariable Integer groupId) {

        return contributionService.getContributionsByGroup(
                groupId);
    }

    @GetMapping("/group/{groupId}/total")
    public Double getTotalContributionByGroup(
            @PathVariable Integer groupId) {

        return contributionService.getTotalContributionByGroup(
                groupId);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteContribution(
            @PathVariable Integer id) {

        contributionService.deleteContribution(id);

        return "Contribution deleted successfully";
    }
}