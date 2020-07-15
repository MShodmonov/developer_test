package musobek.shodmonov.developer_test.service;

import musobek.shodmonov.developer_test.entity.Contact;
import musobek.shodmonov.developer_test.entity.User;
import musobek.shodmonov.developer_test.model.ContactRequest;
import musobek.shodmonov.developer_test.repositories.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class ContactService {
    private  ContactRepository contactRepository;

    @Autowired
    public ContactService(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }
    public boolean insertContact(ContactRequest request)
    {
        Contact save = contactRepository.saveAndFlush(new Contact(request.getName(), request.getPhoneNumber(), request.getEmail()));
            save.getCreatedBy();
            save.getUpdatedBy();
        return true;
    }
    public Page<Contact> getContactList(User user,int page, int size){
        PageRequest pageRequest = PageRequest.of(page, size);
       return contactRepository.findAllByCreatedBy(user,pageRequest);
    }


}
