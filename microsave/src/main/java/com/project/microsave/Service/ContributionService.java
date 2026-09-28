package com.project.microsave.Service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.project.microsave.Entity.Contribution;
import com.project.microsave.Repository.ContributionRepo;
import com.project.microsave.Repository.GroupRepo;
import com.project.microsave.Repository.MemberRepo;

@Service
public class ContributionService {

    private final ContributionRepo contributionRepository;
    private final MemberRepo memberRepository;
    private final GroupRepo groupRepository;

    public ContributionService(ContributionRepo contributionRepository,
                               MemberRepo memberRepository,
                               GroupRepo groupRepository) {

        this.contributionRepository = contributionRepository;
        this.memberRepository = memberRepository;
        this.groupRepository = groupRepository;
    }

    public Contribution addContribution(Contribution contribution) {

        if (contribution.getAmount() == null
                || contribution.getAmount() <= 0) {

            throw new RuntimeException(
                    "Contribution amount must be greater than zero");
        }

        if (contribution.getMemberId() == null) {
            throw new RuntimeException("Member ID is required");
        }

        if (contribution.getGroupId() == null) {
            throw new RuntimeException("Group ID is required");
        }

        memberRepository.findById(contribution.getMemberId())
                .orElseThrow(() -> new RuntimeException("Member not found"));

        groupRepository.findById(contribution.getGroupId())
                .orElseThrow(() -> new RuntimeException("Group not found"));

        contribution.setId(getNextId());

        return contributionRepository.save(contribution);
    }

    public Contribution updateContribution(Integer id,
                                           Contribution updatedContribution) {

        Contribution contribution = contributionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        "Contribution not found"));

        if (updatedContribution.getAmount() == null
                || updatedContribution.getAmount() <= 0) {

            throw new RuntimeException(
                    "Contribution amount must be greater than zero");
        }

        if (updatedContribution.getMemberId() != null) {

            memberRepository.findById(updatedContribution.getMemberId())
                    .orElseThrow(() -> new RuntimeException(
                            "Member not found"));

            contribution.setMemberId(updatedContribution.getMemberId());
        }

        if (updatedContribution.getGroupId() != null) {

            groupRepository.findById(updatedContribution.getGroupId())
                    .orElseThrow(() -> new RuntimeException(
                            "Group not found"));

            contribution.setGroupId(updatedContribution.getGroupId());
        }

        contribution.setAmount(updatedContribution.getAmount());
        contribution.setContributionDate(
                updatedContribution.getContributionDate());
        contribution.setDescription(
                updatedContribution.getDescription());

        return contributionRepository.save(contribution);
    }

    public List<Contribution> getAllContributions() {
        return contributionRepository.findAll();
    }

    public Contribution getContributionById(Integer id) {

        return contributionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        "Contribution not found"));
    }

    public List<Contribution> getContributionsByMember(Integer memberId) {

        memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException(
                        "Member not found"));

        List<Contribution> contributions =
                contributionRepository.findAll();

        List<Contribution> result = new ArrayList<>();

        for (Contribution contribution : contributions) {

            if (contribution.getMemberId().equals(memberId)) {
                result.add(contribution);
            }
        }

        return result;
    }

    public List<Contribution> getContributionsByGroup(Integer groupId) {

        groupRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException(
                        "Group not found"));

        List<Contribution> contributions =
                contributionRepository.findAll();

        List<Contribution> result = new ArrayList<>();

        for (Contribution contribution : contributions) {

            if (contribution.getGroupId().equals(groupId)) {
                result.add(contribution);
            }
        }

        return result;
    }

    public Double getTotalContributionByGroup(Integer groupId) {

        groupRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException(
                        "Group not found"));

        List<Contribution> contributions =
                contributionRepository.findAll();

        double total = 0.0;

        for (Contribution contribution : contributions) {

            if (contribution.getGroupId().equals(groupId)) {
                total += contribution.getAmount();
            }
        }

        return total;
    }

    public void deleteContribution(Integer id) {

        if (!contributionRepository.existsById(id)) {
            throw new RuntimeException("Contribution not found");
        }

        contributionRepository.deleteById(id);
    }

    private Integer getNextId() {

        List<Contribution> contributions =
                contributionRepository.findAll();

        Integer maxId = 0;

        for (Contribution contribution : contributions) {

            if (contribution.getId() > maxId) {
                maxId = contribution.getId();
            }
        }

        return maxId + 1;
    }
}