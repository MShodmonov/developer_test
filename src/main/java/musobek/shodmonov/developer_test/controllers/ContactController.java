package musobek.shodmonov.developer_test.controllers;

import musobek.shodmonov.developer_test.entity.Contact;
import musobek.shodmonov.developer_test.entity.User;
import musobek.shodmonov.developer_test.model.ContactRequest;
import musobek.shodmonov.developer_test.security.CurrentUser;
import musobek.shodmonov.developer_test.service.ContactService;
import musobek.shodmonov.developer_test.util.AppConstants;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/cabinet/contact")
public class ContactController {


    private final ContactService contactService;

    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @GetMapping({"","/","index","index.html"})
    public String getContactPage(Model model, @CurrentUser User user,
                                 @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
                                 @RequestParam(value = "size",defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size)
    {
        if (page < 0) page=0;
        if (size > AppConstants.MAX_PAGE_SIZE) size=AppConstants.MAX_PAGE_SIZE;
        Page<Contact> contactList = contactService.getContactList(user, page, size);
        model.addAttribute("contactPage",contactList);
        int totalPages = contactList.getTotalPages();
        if (totalPages>0)
        {
            List<Integer> pageNumbers = IntStream.rangeClosed(0, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers",pageNumbers);

        }
        return "cabinet/contact/index";
    }
    @GetMapping("/add")
    public String addContact(Model model)
    {
        model.addAttribute("contactRequest", new ContactRequest());
        model.addAttribute("savePath","cabinet/contact/add");
        return "/cabinet/contact/form";
    }
    @PostMapping("/add")
    public String addContactPost(@Valid  ContactRequest contactRequest,
                                 BindingResult bindingResult,
                                 Model model)
    {
        if (bindingResult.hasErrors()) {
            model.addAttribute("contactRequest", contactRequest);
            return "cabinet/contact/form";
        }
        else {
            contactService.insertContact(contactRequest);
            return "redirect:cabinet/contact/";
        }
    }

}
