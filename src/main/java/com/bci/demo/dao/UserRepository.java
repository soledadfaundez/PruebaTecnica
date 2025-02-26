package com.bci.demo.dao;

import com.bci.demo.model.User;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    // SFC: Buscar un usuario por su email
    User findByEmail(String email);
}
