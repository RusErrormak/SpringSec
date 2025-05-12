package org.ruserrormak.spring_sec.service;

import org.ruserrormak.spring_sec.model.Users;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

public interface UsersService extends UserDetailsService {

    @Override
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

     Users findByUsername(String username);

     List<Users> allUsers();

     void saveUser(Users user, String[] roles);

     void deleteUser(Long Id);

     Users getById(Long id);

     void update(Users user, String[] roles);



}
