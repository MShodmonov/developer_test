package musobek.shodmonov.developer_test.component;

import musobek.shodmonov.developer_test.entity.Role;
import musobek.shodmonov.developer_test.entity.User;
import musobek.shodmonov.developer_test.entity.enums.RoleEnumeration;
import musobek.shodmonov.developer_test.repositories.RoleRepository;
import musobek.shodmonov.developer_test.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    @Value("${spring.datasource.initialization-mode}")
    private String mode;


    public DataLoader(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (true) {
            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            Role admin = new Role(RoleEnumeration.ROLE_ADMIN);
            Role user = new Role(RoleEnumeration.ROLE_USER);
            Role moderator = new Role(RoleEnumeration.ROLE_MODERATOR);
            roleRepository.save(admin);
            roleRepository.save(user);
            roleRepository.save(moderator);
            User userAdmin = new User("MShodmonov", "musobek.shodmonov@mail.ru", "Shodmonov Musobek",passwordEncoder.encode("12345qwer"), roleRepository.findAll());
            userRepository.save(userAdmin);
        }
    }
}
