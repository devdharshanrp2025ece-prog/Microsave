package com.project.microsave.Service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.project.microsave.Entity.Member;
import com.project.microsave.Repository.GroupRepo;
import com.project.microsave.Repository.MemberRepo;

@Service
public class MemberService {

    private final MemberRepo memberRepository;
    private final GroupRepo groupRepository;

    public MemberService(MemberRepo memberRepository,
                         GroupRepo groupRepository) {

        this.memberRepository = memberRepository;
        this.groupRepository = groupRepository;
    }

    public Member addMember(Member member) {

        if (member.getName() == null || member.getName().isBlank()) {
            throw new RuntimeException("Member name is required");
        }

        if (member.getGroupId() == null) {
            throw new RuntimeException("Group ID is required");
        }

        groupRepository.findById(member.getGroupId())
                .orElseThrow(() -> new RuntimeException("Group not found"));

        member.setId(getNextId());

        return memberRepository.save(member);
    }

    public Member updateMember(Integer id, Member updatedMember) {

        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Member not found"));

        if (updatedMember.getGroupId() != null) {

            groupRepository.findById(updatedMember.getGroupId())
                    .orElseThrow(() -> new RuntimeException("Group not found"));

            member.setGroupId(updatedMember.getGroupId());
        }

        member.setName(updatedMember.getName());
        member.setPhone(updatedMember.getPhone());
        member.setJoinDate(updatedMember.getJoinDate());
        member.setStatus(updatedMember.getStatus());

        return memberRepository.save(member);
    }

    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }

    public Member getMemberById(Integer id) {

        return memberRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Member not found"));
    }

    public List<Member> getMembersByGroup(Integer groupId) {

        groupRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Group not found"));

        List<Member> members = memberRepository.findAll();

        List<Member> result = new ArrayList<>();

        for (Member member : members) {

            if (member.getGroupId().equals(groupId)) {
                result.add(member);
            }
        }

        return result;
    }

    public void deleteMember(Integer id) {

        if (!memberRepository.existsById(id)) {
            throw new RuntimeException("Member not found");
        }

        memberRepository.deleteById(id);
    }

    private Integer getNextId() {

        List<Member> members = memberRepository.findAll();

        Integer maxId = 0;

        for (Member member : members) {

            if (member.getId() > maxId) {
                maxId = member.getId();
            }
        }

        return maxId + 1;
    }
}