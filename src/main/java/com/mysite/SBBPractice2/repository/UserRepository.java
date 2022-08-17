package com.mysite.SBBPractice2.repository;

import com.mysite.SBBPractice2.domain.SiteUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<SiteUser, Integer> {
    Optional<SiteUser> findByusername(String username);
}
