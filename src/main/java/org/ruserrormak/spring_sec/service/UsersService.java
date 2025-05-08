package org.ruserrormak.spring_sec.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.ruserrormak.spring_sec.model.Roles;
import org.ruserrormak.spring_sec.model.Users;
import org.ruserrormak.spring_sec.repository.RolesRepository;
import org.ruserrormak.spring_sec.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class UsersService implements UserDetailsService {

    @PersistenceContext
    private EntityManager em;


    private final UsersRepository usersRepository;

    private final RolesRepository rolesRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UsersService(UsersRepository usersRepository,
                        RolesRepository rolesRepository,
                        BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.usersRepository = usersRepository;
        this.rolesRepository = rolesRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }



    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = usersRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return user;
    }

    public Users findByUsername(String username) {
        return usersRepository.findByUsername(username);
    }

    public List<Users> allUsers() {
        return usersRepository.findAll();
    }

    public void saveUser(Users user, String role) {

        Set<Roles> roles = new HashSet<>();
        roles.add(rolesRepository.findByName("ROLE_USER"));
        if (role.equals("ROLE_ADMIN")) {
            roles.add(rolesRepository.findByName("ROLE_ADMIN"));
        }
        user.setRoles(roles);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        usersRepository.save(user);

    }

    public void deleteUser(Long Id) {
        if (usersRepository.findById(Id).isPresent()) {
            usersRepository.deleteById(Id);
        }
    }

    public Users getById(Long id) {
        return em.find(Users.class, id);
    }

    public void update(Users user) {
        em.merge(user);
    }

}
