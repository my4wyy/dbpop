package com.maisapires.todosimple.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.maisapires.todosimple.models.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
