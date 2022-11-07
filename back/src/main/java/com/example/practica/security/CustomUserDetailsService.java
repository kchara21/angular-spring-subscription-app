package com.example.practica.security;


import com.example.practica.repository.UserRepository;
import com.example.practica.utils.enums.role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import com.example.practica.entities.User;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);

        if(user == null){
            throw new UsernameNotFoundException("User not founded");
        }


        // No se esta usando "ROLES" como entidad, sino como una propiedad. porque asi lo amerita este ejercicio
        // Por esta razon agrego solo la propiedad a una lista, asi uso el metodo.
        Set<role> roles = new HashSet<>();
        roles.add(user.getRole());

        return new org.springframework.security.core.userdetails.User
                (user.getEmail(),user.getPassword(),mapearRoles(roles));


    }

    private Collection<? extends GrantedAuthority> mapearRoles(Set<role> roles){
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.toString())).collect(Collectors.toList());
    }


}
