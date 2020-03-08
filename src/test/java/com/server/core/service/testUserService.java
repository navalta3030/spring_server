package com.server.core.service;


import com.server.core.model.Role;
import com.server.core.model.User;
import com.server.core.repository.UserRepository;
import com.server.core.service.Interface.UserService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class testUserService {

    @Autowired
    private UserService service;

    @MockBean
    private UserRepository repository;

    @Test
    public void findAllUsersTest() {
        User user1 = new User((long) 1,"first", "second", "butter", "password", "email@gmail.com", Role.ADMIN, "null");
        User user2 = new User((long) 2,"first1", "second1", "ted", "password1", "email1@gmail.com", Role.USER, "null");

        // test get all users
        when(repository.findAll()).thenReturn(Stream.of(user1, user2).collect(Collectors.toList()));
        assertEquals(2, repository.findAll().size());
        // test empty objects
        when(repository.findAll()).thenReturn(Collections.emptyList());
        assertEquals(0, repository.findAll().size());
    }

    @Test
    public void findByUsernameTest() {
        User user1 = new User((long) 1,"first", "second", "butter", "password", "email@gmail.com", Role.ADMIN, "null");
        User user2 = new User((long) 2,"first1", "second1", "ted", "password1", "email1@gmail.com", Role.USER, "null");

        // test get one user | user1
        when(repository.findByUsername("butter"))
                .thenReturn(user1);
        assertEquals("email@gmail.com", service.findByUsername("butter").getEmail());

        // test got one user expect different email | user2
        when(repository.findByUsername("ted"))
                .thenReturn(user2);
        assertNotEquals("email@gmail.com", service.findByUsername("ted").getEmail());

        // test get one user but doesn't exist.
        when(repository.findByUsername("greg"))
                .thenReturn(null);

        assertNull(service.findByUsername("greg"));
    }

    @Test
    public void saveUserTest() {
        User user1 = new User((long) 1,"first", "second", "butter", "password", "email@gmail.com", Role.ADMIN, "null");
        User user2 = new User((long) 2,"first", "second", "ted", "password1", "email@gmail.com", Role.USER, "null");
        User invalidUser = new User((long) 2,null, null, null, "password1", "email.com", Role.USER, "null");

        // test null | bcrypt can't encode null
        when(repository.save(null)).thenThrow(new NullPointerException("...") { });
        assertThrows(NullPointerException.class, () ->  service.saveUser(null));

        // test user save | user1
        when(repository.save(user1)).thenReturn(user1);
        assertEquals("email@gmail.com", service.saveUser(user1).getEmail());

        // test user save but duplicated | This is post validation | user1 and user 2
        ArrayList<User> users = new ArrayList<>() ;
        users.add(user1);
        users.add(user2);
        when(repository.saveAll(users)).thenThrow(new DataIntegrityViolationException("..."){});
        assertThrows(DataIntegrityViolationException.class, () -> service.saveAll(users));


        // test user save but duplicated | This is post validation | user1
        when(repository.save(invalidUser)).thenThrow(new DataIntegrityViolationException("..."){});
        assertThrows(DataIntegrityViolationException.class, () -> {
            service.saveUser(invalidUser);
        });
    }

    @Test
    public void updateUser(){

        User user1 = new User((long) 1,"first", "second", "butter", "password", "email@gmail.com", Role.ADMIN, "null");
        User invalidUser = new User((long) 2,null, null, null, "password1", "email.com", Role.USER, "null");

        // test null | bcrypt can't encode null
        when(repository.save(null)).thenThrow(new NullPointerException("...") { });
        assertThrows(NullPointerException.class, () ->  service.updateUser(null));

        // test user save | user1
        when(repository.save(user1)).thenReturn(user1);
        assertEquals("email@gmail.com", service.updateUser(user1).getEmail());

        // test user save but duplicated | This is post validation | user1
        when(repository.save(invalidUser)).thenThrow(new DataIntegrityViolationException("..."){});
        assertThrows(DataIntegrityViolationException.class, () -> {
            service.saveUser(invalidUser);
        });
    }

    @Test
    public void deleteUserTest(){
        assertEquals(null, service.deleteUser((long) 1));
    }

    @Test
    public void userCountTest(){

        when(repository.count()).thenReturn((long) 2);
        assertEquals(2, service.numberOfUsers());

    }
}
