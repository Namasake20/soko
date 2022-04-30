package com.caleb.Tiba.service;

import com.caleb.Tiba.model.Role;
import com.caleb.Tiba.model.AppUser;
import com.caleb.Tiba.repo.RoleRepo;
import com.caleb.Tiba.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service @RequiredArgsConstructor @Slf4j @Transactional
public class DataAccessService implements UserDetailsService {
    private final UserRepo userRepo;
    private final RoleRepo roleRepo;
    private final PasswordEncoder passwordEncoder;

    public AppUser saveUser(AppUser user){
        log.info("saving {} to database",user.getName());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepo.save(user);
    }
    public Role saveRole(Role role){
        log.info("saving role to database");
        return roleRepo.save(role);
    }
    public List<AppUser> getUsers(){
        log.info("Fetching all users from database");
        return userRepo.findAll();
    }

    public void addRoleToUser(String email,String roleName){
        log.info("Adding role {} to {}",roleName,email);
        AppUser user = userRepo.findByEmail(email);
        Role role = roleRepo.findByName(roleName);
        user.getRoles().add(role);
    }
    public AppUser getUserByEmail(String email){
        log.info("Fetching {} ",email);
        return userRepo.findByEmail(email);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser user = userRepo.findByEmail(username);
        if (user == null){
            log.error("user not found in the database");
            throw new UsernameNotFoundException("user not found in the database");
        }else {
            log.info("user {} found in the database",username);
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(role -> {authorities.add(new SimpleGrantedAuthority(role.getName()));
        });
        return new org.springframework.security.core.userdetails.User(user.getEmail(),user.getPassword(),authorities);
    }
}
