package org.ruserrormak.spring_sec.service;

import org.ruserrormak.spring_sec.model.Roles;
import org.ruserrormak.spring_sec.model.Users;
import org.ruserrormak.spring_sec.repository.RolesRepository;
import org.ruserrormak.spring_sec.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


@Service
public class UsersServiceImpl implements UsersService {

    private final UsersRepository usersRepository;

    private final RolesRepository rolesRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UsersServiceImpl(UsersRepository usersRepository,
                            RolesRepository rolesRepository,
                            BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.usersRepository = usersRepository;
        this.rolesRepository = rolesRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = usersRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return user;
    }

    @Transactional
    @Override
    public Users findByUsername(String username) {
        return usersRepository.findByUsername(username);
    }

    @Transactional
    @Override
    public List<Users> allUsers() {
        return usersRepository.findAll();
    }

    @Transactional
    @Override
    public void saveUser(Users user, String[] roles) {

        Set<Roles> userRoles = new HashSet<>();
        if (roles != null) {
            for (String roleName : roles) {
                Roles role = rolesRepository.findByName(roleName);
                if (role != null) {
                    userRoles.add(role);
                }
            }
        }
        user.setRoles(userRoles);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        usersRepository.save(user);

    }

    @Transactional
    @Override
    public void deleteUser(Long Id) {
        if (usersRepository.findById(Id).isPresent()) {
            usersRepository.deleteById(Id);
        }
    }

    @Transactional
    @Override
    public Users getById(Long id) {
        return usersRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));    }

    @Transactional
    @Override
    public void update(Users user, String[] roles) {
        Users existingUser =  usersRepository.findById(user.getId())
                .orElseThrow(() -> new RuntimeException("User not found with id: " + user.getId()));

        existingUser.setUsername(user.getUsername());
        existingUser.setEmail(user.getEmail());

        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            existingUser.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        }

        Set<Roles> userRoles = new HashSet<>();
        if (roles != null) {
            for (String roleName : roles) {
                Roles role = rolesRepository.findByName(roleName);
                if (role != null) {
                    userRoles.add(role);
                }
            }
        }
        existingUser.setRoles(userRoles);

        usersRepository.save(existingUser);
    }

}
