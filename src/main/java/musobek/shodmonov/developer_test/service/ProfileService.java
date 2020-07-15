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
import org.springframework.core.io.ClassPathResource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.mail.Multipart;
import javax.transaction.Transactional;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Optional;

@Service
public class ProfileService {

    private  AttachmentRepository attachmentRepository;
    private  AttachmentContentRepository attachmentContentRepository;
    private final UserRepository userRepository;


    @Autowired
    public ProfileService(AttachmentRepository attachmentRepository, AttachmentContentRepository attachmentContentRepository, UserRepository userRepository) {
        this.attachmentRepository = attachmentRepository;
        this.attachmentContentRepository = attachmentContentRepository;
        this.userRepository = userRepository;
    }
    @Autowired
    private PasswordEncoder passwordEncoder;


    public TemplateMessage
    updateProfile(User currentUser, ProfileRequest profileRequest)
    {
        TemplateMessage message=new TemplateMessage();
        try {
            if (profileRequest.getPhoto() != null){
            MultipartFile photo=profileRequest.getPhoto();
            Attachment attachment = attachmentRepository.save(new Attachment(photo.getName(), photo.getContentType(), photo.getSize()));
            attachmentContentRepository.save(new AttachmentContent(attachment,photo.getBytes()));}
            Optional<User> byId = userRepository.findById(currentUser.getId());

            if (byId.isPresent())
            {

                User user = byId.get();
                Optional<Attachment> optionalAttachment = attachmentRepository.findByCreatedBy(user);
                if (optionalAttachment.isPresent() && user.getAttachment() == null)
                {
                    user.setAttachment(currentUser.getAttachment());
                }
                user.setEmail(currentUser.getEmail());
                user.setFullName(currentUser.getFullName());
                user.setPassword(passwordEncoder.encode(currentUser.getPassword()));
                user.setRoles(currentUser.getRoles());
                user.setUsername(currentUser.getUsername());

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
    @Transactional
    public MultipartFile convertAttachmentToMultipartFile(User user)

    {
        Optional<AttachmentContent> optionalContent = attachmentContentRepository.findByCreatedBy(user);
        Optional<Attachment> optionalAttachment = attachmentRepository.findByCreatedBy(user);
        if (optionalAttachment.isPresent() && optionalContent.isPresent()) {
            Attachment attachment = optionalAttachment.get();
            AttachmentContent content = optionalContent.get();

            String type=attachment.getContentType().substring(6);
            MultipartFile multipartFile = new MockMultipartFile(attachment.getName(),attachment.getName(),type,content.getContent());


            return multipartFile;
        }
        else return null;
    }

    @Transactional
    public File convertAttachmentToImage(User user) throws IOException {
        Optional<AttachmentContent> optionalContent = attachmentContentRepository.findByCreatedBy(user);
        Optional<Attachment> optionalAttachment = attachmentRepository.findByCreatedBy(user);
        if (optionalAttachment.isPresent() && optionalContent.isPresent()) {
            Attachment attachment = optionalAttachment.get();
            AttachmentContent content = optionalContent.get();

            String type = attachment.getContentType().substring(6);
            ClassPathResource classPathResource = new ClassPathResource("/src/main/resources/static/assets/img/");
                File image = new File(classPathResource.getPath() + "/" + attachment.getId().toString() + "." + type);

                if (image.createNewFile()) {

                }
            FileOutputStream fileOutputStream = new FileOutputStream(image);
            fileOutputStream.write(content.getContent());
            ImageIO.createImageOutputStream(fileOutputStream);
            fileOutputStream.close();
                return image;
            }
           return  null;
        }

        @Transactional
        public String[] getImageBase64AndType(User user)
         {
        Optional<AttachmentContent> optionalContent = attachmentContentRepository.findByCreatedBy(user);
        Optional<Attachment> optionalAttachment = attachmentRepository.findByCreatedBy(user);
        if (optionalAttachment.isPresent() && optionalContent.isPresent()) {
            Attachment attachment = optionalAttachment.get();
            AttachmentContent content = optionalContent.get();

            String type = attachment.getContentType().substring(6);
            String toString = Base64.getEncoder().encodeToString(content.getContent());

            return new String[]{type,toString};
        }
        return null;
         }
         public String addVideo(String title, Multipart multipart)
         {
            return null;//can not implement in mvc model will be done in web flux
         }
}
