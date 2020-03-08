package com.server.core.service;

import com.server.core.model.Role;
import com.server.core.model.User;
import com.server.core.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.Mockito.when;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class testUserDetailsService {

    @Autowired
    private UserDetailsService service;

    @MockBean
    private UserRepository repository;

    @Test
    public void loadUserByUsernameTest(){
        User user1 = new User((long) 1,"first", "second", "test", "password", "email@gmail.com", Role.ADMIN, "null");

        when(repository.findByUsername("test")).thenReturn(user1);
        assertEquals("test" , service.loadUserByUsername("test").getUsername());

    }
}
