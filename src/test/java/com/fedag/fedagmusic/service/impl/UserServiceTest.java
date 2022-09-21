package com.fedag.fedagmusic.service.impl;

import com.fedag.fedagmusic.entities.User;
import com.fedag.fedagmusic.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

import static com.fedag.fedagmusic.entities.UserRole.ROLE_USER;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


@ExtendWith(SpringExtension.class)
public class UserServiceTest {

   @InjectMocks
   private UserServiceImpl userServiceImpl;

    @Mock
    private UserRepository userRepository;

    private User users;

    @BeforeEach
    public void init() {
        users = User.builder()
                         .id(1L).created(LocalDateTime.now()).email("test@test.ru")
                         .firstName("Alex").lastName("Dear").password("123")
                         .role(ROLE_USER).build();
    }

    @Test
    public void getUserByIdTest(){
        Mono<User> user = Mono.just(users);
        when(userRepository.findById(1L)).thenReturn(user);
        Mono<User> userTwo = userServiceImpl.getUserById(1L);
        Assertions.assertEquals(user, userTwo);
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    public void deleteUserByIdTest(){
        Mockito.when(userRepository.deleteById(1L)).thenReturn(Mono.empty());
        Mono<Void> voidMono = userServiceImpl.deleteUserById(users.getId());
         assertEquals(Mono.empty(), voidMono);
        verify(userRepository, times(1)).deleteById(users.getId());
    }

    @Test
    public void createUserTest(){
        Mockito.when(userRepository.save(users)).thenReturn(Mono.just(users));
        userServiceImpl.addUser(users);
        Mockito.verify(userRepository, times(1)).save(users);
    }

    @Test
    public void updateUserTest(){
        User newUser = new User(1L, "333@qwer.ru", "3333", "Bob","Chack", ROLE_USER, LocalDateTime.now());
        Mockito.when(userRepository.findById(1L)).thenReturn(Mono.just(users));
        userServiceImpl.updateUser(newUser, 1L);
        Mockito.verify(userRepository, times(1)).findById(users.getId());
    }
}

