package com.project.microsave.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.microsave.Entity.Member;

public interface MemberRepo extends JpaRepository<Member, Integer> {
}