package musobek.shodmonov.developer_test.service;

import musobek.shodmonov.developer_test.entity.Role;
import musobek.shodmonov.developer_test.entity.User;
import musobek.shodmonov.developer_test.entity.enums.RoleEnumeration;
import musobek.shodmonov.developer_test.model.RegisterRequest;
import musobek.shodmonov.developer_test.repositories.RoleRepository;
import musobek.shodmonov.developer_test.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mail.MailSender;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class AuthServiceTest {
    @Mock
    UserRepository userRepository;
    @Mock
    RoleRepository roleRepository;
    @Mock
    MailSender mailSender;

    AuthService authService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        authService=new AuthService(userRepository,roleRepository,mailSender);
    }

    @Test
    void loadUserByUsername() {

        List<Role>roles=new LinkedList<>();
        roles.add(new Role(RoleEnumeration.ROLE_USER));
        when(roleRepository.findAll()).thenReturn(roles);
        User user = new User("some user","myemail@.com","my full name","my password",roleRepository.findAll(), UUID.randomUUID());
        userRepository.save(user);
        when(userRepository.findUsersByUsername(user.getUsername())).thenReturn(Optional.of(user));
        assertEquals(authService.loadUserByUsername(user.getUsername()),user);
    }


    @Test
    void registerTemporary() {
        List<Role>roles=new LinkedList<>();
        Role role = new Role(RoleEnumeration.ROLE_USER);
        roles.add(role);
        when(roleRepository.findByName(RoleEnumeration.ROLE_USER)).thenReturn(Optional.of(role));

        RegisterRequest registerRequest=new RegisterRequest("some user","some@mail.ru","my full name","my passowrd","my passowrd");
        User user = new User(registerRequest.getUsername(), registerRequest.getEmail(), registerRequest.getFullName(), registerRequest.getPassword(), roles, UUID.randomUUID());
        when(userRepository.findByUsernameOrEmail(user.getUsername(),user.getEmail())).thenReturn(Optional.of(user));


    }

    @Test
    void activateAccount() {
    }
}