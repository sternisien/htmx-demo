package com.training.htmx.htmxDemo.repository;

import com.training.htmx.htmxDemo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
