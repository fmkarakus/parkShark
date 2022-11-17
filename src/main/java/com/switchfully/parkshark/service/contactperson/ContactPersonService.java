package com.switchfully.parkshark.service.contactperson;

import com.switchfully.parkshark.domain.contactperson.ContactPerson;
import com.switchfully.parkshark.domain.contactperson.ContactPersonRepository;
import org.springframework.stereotype.Service;

@Service
public class ContactPersonService {
    private final ContactPersonRepository contactPersonRepository;

    public ContactPersonService(ContactPersonRepository contactPersonRepository) {
        this.contactPersonRepository = contactPersonRepository;
    }

    public ContactPerson findContactPersonByID(Long contactPersonId) {
        return contactPersonRepository.findById(contactPersonId).orElseThrow(() -> new IllegalArgumentException("This Id does not exist"));
    }
}
