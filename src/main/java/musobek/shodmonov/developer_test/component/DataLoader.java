package musobek.shodmonov.developer_test.component;

import musobek.shodmonov.developer_test.entity.Contact;
import musobek.shodmonov.developer_test.entity.Role;
import musobek.shodmonov.developer_test.entity.User;
import musobek.shodmonov.developer_test.entity.enums.RoleEnumeration;
import musobek.shodmonov.developer_test.repositories.ContactRepository;
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
    private final ContactRepository contactRepository;
    @Value("${spring.datasource.initialization-mode}")
    private String mode;


    public DataLoader(UserRepository userRepository, RoleRepository roleRepository, ContactRepository contactRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.contactRepository = contactRepository;
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

            User userAdmin = new User("MShodmonov", "musobek.shodmonov@mail.ru", "Shodmonov Musobek",passwordEncoder.encode("12345qwer"), roleRepository.findAll(),true);
            Contact contact=new Contact("Jurabek","423","dfjds@mail.ru");
            Contact contact1=new Contact("Jurabek1","42312","df23e23jds@mail.ru");
            Contact contact2=new Contact("Jurabek1","423121","dffdvfsjds@mail.ru");

            Contact save = contactRepository.save(contact);
            Contact save1 = contactRepository.save(contact1);
            Contact save2 = contactRepository.save(contact2);


            userRepository.save(userAdmin);

        }
    }
}
