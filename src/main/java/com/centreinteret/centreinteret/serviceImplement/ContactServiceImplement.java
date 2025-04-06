package com.centreinteret.centreinteret.serviceImplement;

import com.centreinteret.centreinteret.dto.ContactDTO;
import com.centreinteret.centreinteret.entities.Centreinteret;
import com.centreinteret.centreinteret.entities.Contact;
import com.centreinteret.centreinteret.repository.CentreInteretRepository;
import com.centreinteret.centreinteret.repository.ContactRepository;
import com.centreinteret.centreinteret.services.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ContactServiceImplement implements ContactService {

    @Autowired
    private ContactRepository contactRepository;
    @Autowired
    private CentreInteretRepository centreInteretRepository;

    @Override
    public List<ContactDTO> listeContacts() {
        return contactRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ContactDTO createContact(ContactDTO contactDTO) {
        Contact contact = mapToEntity(contactDTO);
        Contact savedContact = contactRepository.save(contact);
        return mapToDTO(savedContact);
    }

    private ContactDTO mapToDTO(Contact contact) {
        ContactDTO dto = new ContactDTO();
        dto.setId(contact.getId());
        dto.setNom(contact.getNom());
        dto.setEmail(contact.getEmail());
        dto.setNumeroWhatsapp(contact.getNumeroWhatsapp());
        if (contact.getCentreinteret() != null) {
            dto.setCentreInteretId(contact.getCentreinteret().getId());
        } else {
            dto.setCentreInteretId(null);  // Ou une valeur par défaut si nécessaire
        }

        return dto;
    }
    @Override
    public List<ContactDTO> getContactsByCentreId(Long centreId) {
        List<Contact> contacts = contactRepository.findByCentreinteretId(centreId);
        return contacts.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }
    private Contact mapToEntity(ContactDTO dto) {
        Contact contact = new Contact();
        contact.setNom(dto.getNom());
        contact.setEmail(dto.getEmail());
        contact.setNumeroWhatsapp(dto.getNumeroWhatsapp());

        if (dto.getCentreInteretId() != null) {
            Centreinteret centreInteret = centreInteretRepository.findById(dto.getCentreInteretId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Centre d'intérêt non trouvé"));
            contact.setCentreinteret(centreInteret);
        } else {
            contact.setCentreinteret(null);
        }

        return contact;
    }

    @Override
    public ContactDTO findContactById(Long id) {
        return contactRepository.findById(id)
                .map(this::mapToDTO)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Contact non trouvé"));
    }
}
