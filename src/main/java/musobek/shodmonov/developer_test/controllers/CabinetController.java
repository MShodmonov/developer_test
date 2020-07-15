package musobek.shodmonov.developer_test.controllers;

import musobek.shodmonov.developer_test.entity.User;
import musobek.shodmonov.developer_test.model.ProfileRequest;
import musobek.shodmonov.developer_test.model.TemplateMessage;
import musobek.shodmonov.developer_test.repositories.AttachmentRepository;
import musobek.shodmonov.developer_test.repositories.UserRepository;
import musobek.shodmonov.developer_test.security.CurrentUser;
import musobek.shodmonov.developer_test.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;

@Controller
public class CabinetController {
    private final ProfileService profileService;
    private UserRepository userRepository;
    private AttachmentRepository attachmentRepository;

    @Autowired
    public CabinetController(ProfileService profileService, UserRepository userRepository, AttachmentRepository attachmentRepository) {
        this.profileService = profileService;
        this.userRepository = userRepository;
        this.attachmentRepository = attachmentRepository;
    }

    @GetMapping("/cabinet")
    public String getCabinet()
    {
        return "cabinet";

    }
    @GetMapping("/cabinet/profile")
    public String getProfile(@CurrentUser User user, Model model)
    {
        if (attachmentRepository.findByCreatedBy(user).isPresent())
        {
            try {
                File image = profileService.convertAttachmentToImage(user);
                String  imageName= image.getName();
                String[] strings = profileService.getImageBase64AndType(user);
                model.addAttribute("image",strings[1]);
                model.addAttribute("type",strings[0]);


                model.addAttribute("imageName",imageName);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        model.addAttribute("profileRequest", new ProfileRequest(user));

        return "/cabinet/profile";
    }
    @PostMapping("cabinet/profile")
    public String getPostProfile(@CurrentUser User user,
                                 @Valid ProfileRequest profileRequest,
                                 BindingResult result,
                                 Model model)
    {
        if (!(result.hasErrors()))
        {
            TemplateMessage templateMessage = profileService.updateProfile(user, profileRequest);
            if (templateMessage.getActionSuccess())
            {
                return "redirect:/cabinet";
            }
            else return "/cabinet/profile";
        }
        else return "/cabinet/profile";
    }

    @GetMapping("/cabinet/dashboard")
    public String getDashboard()
    {
        return "/cabinet/dashboard";
    }
    @GetMapping("/cabinet/developer")
    public String getDeveloperPage()
    {
        return "/cabinet/developer";
    }
    @GetMapping("/cabinet/wishes")
    public String getWishesPage()
    {
        return "cabinet/wishes";
    }
}
