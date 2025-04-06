package com.centreinteret.centreinteret.services;

import com.centreinteret.centreinteret.dto.ContactDTO;

import java.util.List;

public interface ContactService {
    List<ContactDTO> listeContacts();
    ContactDTO createContact(ContactDTO contactDTO);
    ContactDTO findContactById(Long id);
    List<ContactDTO> getContactsByCentreId(Long centreId);

}
