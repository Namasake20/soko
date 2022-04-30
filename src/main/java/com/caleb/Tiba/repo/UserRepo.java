package com.caleb.Tiba.repo;

import com.caleb.Tiba.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<AppUser,Long> {
    AppUser findByEmail(String email);
}
