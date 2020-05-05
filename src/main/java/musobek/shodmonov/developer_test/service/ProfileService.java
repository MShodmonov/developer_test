package musobek.shodmonov.developer_test.service;

import musobek.shodmonov.developer_test.entity.Attachment;
import musobek.shodmonov.developer_test.entity.AttachmentContent;
import musobek.shodmonov.developer_test.entity.User;
import musobek.shodmonov.developer_test.model.ProfileRequest;
import musobek.shodmonov.developer_test.model.TemplateMessage;
import musobek.shodmonov.developer_test.repositories.AttachmentContentRepository;
import musobek.shodmonov.developer_test.repositories.AttachmentRepository;
import musobek.shodmonov.developer_test.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Service
public class ProfileService {

    private final AttachmentRepository attachmentRepository;
    private final AttachmentContentRepository attachmentContentRepository;
    private final UserRepository userRepository;

    public ProfileService(AttachmentRepository attachmentRepository, AttachmentContentRepository attachmentContentRepository, UserRepository userRepository) {
        this.attachmentRepository = attachmentRepository;
        this.attachmentContentRepository = attachmentContentRepository;
        this.userRepository = userRepository;
    }
    @Autowired
    private PasswordEncoder passwordEncoder;


    public TemplateMessage updateProfile(User currentUser, ProfileRequest profileRequest)
    {
        TemplateMessage message=new TemplateMessage();
        try {
            MultipartFile photo=profileRequest.getPhoto();
            Attachment attachment = attachmentRepository.save(new Attachment(photo.getName(), photo.getContentType(), photo.getSize()));
            attachmentContentRepository.save(new AttachmentContent(attachment,photo.getBytes()));
            Optional<User> byId = userRepository.findById(currentUser.getId());
            if (byId.isPresent())
            {
                User user = byId.get();
                user.setEmail(currentUser.getEmail());
                user.setFullName(currentUser.getFullName());
                user.setPassword(passwordEncoder.encode(currentUser.getPassword()));
                user.setRoles(currentUser.getRoles());
                user.setUsername(currentUser.getUsername());
                user.setAttachment(currentUser.getAttachment());
                userRepository.save(user);
                message.setMessage("Successful");
                message.setActionSuccess(true);
            }
            else
            {
                message.setActionSuccess(false);
                message.setMessage("Failed");
            }

        }
        catch (Exception e)
        {
            message.setActionSuccess(false);
            message.setMessage("Exception occurred");
        }
        return message;
    }
}
