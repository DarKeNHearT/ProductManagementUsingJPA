package com.project.product_management.configuration.dto;

import com.project.product_management.model.User;
import com.project.product_management.repository.RepositoryUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    RepositoryUser repositoryUser;

    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Optional<User> user = repositoryUser.findByUserName(userName);
        user.orElseThrow(() -> new UsernameNotFoundException("Not Found " + userName));
        return user.map(MyUserDetails::new).get();
    }

    @Transactional
    public void save(User user) {
        System.out.println("hello");
        System.out.println(user.getPassword());
        System.out.println(bCryptPasswordEncoder.encode(user.getPassword()));
       user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        System.out.println("hello");
        System.out.println(user.getRoles());
        System.out.println(user.getActive());
        try {
            repositoryUser.save(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("hello");
    }

}
