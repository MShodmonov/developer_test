package musobek.shodmonov.developer_test.service;

import musobek.shodmonov.developer_test.entity.Role;
import musobek.shodmonov.developer_test.entity.User;
import musobek.shodmonov.developer_test.entity.enums.RoleEnumeration;
import musobek.shodmonov.developer_test.model.RegisterRequest;
import musobek.shodmonov.developer_test.model.TemplateMessage;
import musobek.shodmonov.developer_test.repositories.RoleRepository;
import musobek.shodmonov.developer_test.repositories.UserRepository;
import musobek.shodmonov.developer_test.security.MyAuthentication;
import musobek.shodmonov.developer_test.security.MySecurityContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AuthService implements UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final MailSender mailSender;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthService(UserRepository userRepository, RoleRepository roleRepository, MailSender mailSender, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.mailSender = mailSender;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findUsersByUsername(username).orElseThrow(() -> new UsernameNotFoundException("user not found" + username));
    }

    public boolean isAuthenticated()
    {
        return !(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken);
    }
    public TemplateMessage registerTemporary(RegisterRequest registerRequest) {
        TemplateMessage templateMessage = new TemplateMessage();
        try {
        if (userRepository.findByUsernameOrEmail(registerRequest.getUsername(), registerRequest.getEmail()).isPresent()) {
            templateMessage.setActionSuccess(false);
            templateMessage.setMessage("the username or eamil exists");
            return templateMessage;
        }
        Optional<Role> byName = roleRepository.findByName(RoleEnumeration.ROLE_USER);
        List<Role> userRole = new LinkedList<>();
        userRole.add(byName.get());
        User user = userRepository.save(new User(registerRequest.getUsername(), registerRequest.getEmail(), registerRequest.getFullName(),
                passwordEncoder.encode(registerRequest.getPassword()), userRole,UUID.randomUUID()));
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(user.getEmail());
        message.setSubject("Registration confirmation");
        message.setText("this is a message from the developer-test.com site to activate your registration. please click or press the link to activate your account " +
                "http://localhost:8888/activate?activationCode=" + user.getActivationCode());
        mailSender.send(message);
        templateMessage.setActionSuccess(true);
        templateMessage.setMessage("Your account has been registered");


    }
        catch (Exception e){
            templateMessage.setActionSuccess(false);
            templateMessage.setMessage("Activation failed");
        }
        return templateMessage;
    }
    public TemplateMessage activateAccount(String code )
    {
        TemplateMessage templateMessage=new TemplateMessage();
        try {
            Optional<User> optionalUser = userRepository.findByActivationCode(UUID.fromString(code));
            if (optionalUser.isPresent())
            {
                User user = optionalUser.get();
                user.setEnabled(true);
                user.setRoles(roleRepository.findAllByName(RoleEnumeration.ROLE_USER));
                user.setActivationCode(null);
                userRepository.save(user);

                if (!setSecurity(user))
                    throw new Exception();

                templateMessage.setActionSuccess(true);
                templateMessage.setMessage("Successfully");
            }
            else throw new Exception("Not found");
        }
        catch (Exception e)
        {
            templateMessage.setMessage("Link Expired");
            templateMessage.setActionSuccess(false);
        }
        return templateMessage;
    }
private boolean setSecurity(User user)
{
    try {


        SecurityContext context = new MySecurityContext();
        MyAuthentication myAuthentication = new MyAuthentication(user.getAuthorities());
        myAuthentication.setDetails(user);
        myAuthentication.setAuthenticated(true);
        context.setAuthentication(myAuthentication);
        SecurityContextHolder.setContext(context);
        return true;
    }
    catch (Exception e)
    {
        return false;
    }
}
}
