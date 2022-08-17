package com.mysite.SBBPractice2.repository;

import com.mysite.SBBPractice2.domain.SiteUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<SiteUser, Integer> {
}
