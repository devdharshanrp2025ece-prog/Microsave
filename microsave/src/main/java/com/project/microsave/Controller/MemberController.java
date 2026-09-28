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

import com.project.microsave.Entity.Member;
import com.project.microsave.Service.MemberService;

@RestController
@RequestMapping("/api/members")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/add")
    public Member addMember(@RequestBody Member member) {
        return memberService.addMember(member);
    }

    @PutMapping("/update/{id}")
    public Member updateMember(@PathVariable Integer id,
                               @RequestBody Member member) {

        return memberService.updateMember(id, member);
    }

    @GetMapping("/all")
    public List<Member> getAllMembers() {
        return memberService.getAllMembers();
    }

    @GetMapping("/get/{id}")
    public Member getMemberById(@PathVariable Integer id) {
        return memberService.getMemberById(id);
    }

    @GetMapping("/group/{groupId}")
    public List<Member> getMembersByGroup(
            @PathVariable Integer groupId) {

        return memberService.getMembersByGroup(groupId);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteMember(@PathVariable Integer id) {

        memberService.deleteMember(id);

        return "Member deleted successfully";
    }
}