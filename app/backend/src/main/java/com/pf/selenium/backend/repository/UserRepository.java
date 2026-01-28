package com.pf.selenium.backend.repository;

import com.pf.selenium.backend.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
