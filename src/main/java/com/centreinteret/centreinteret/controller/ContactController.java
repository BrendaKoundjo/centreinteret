package com.centreinteret.centreinteret.controller;

import com.centreinteret.centreinteret.dto.ContactDTO;
import com.centreinteret.centreinteret.services.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contact")
@CrossOrigin(origins = "http://localhost:8100",
        methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE},
        allowedHeaders = "*")
public class ContactController {
        @Autowired
        private ContactService contactService;

        /**
         * Récupérer la liste de tous les contacts.
         * @return Liste de ContactDTO.
         */
        @GetMapping
        public List<ContactDTO> listeContacts() {
            return contactService.listeContacts();
        }

        /**
         * Créer un nouveau contact.
         * @param contactDTO Le DTO du contact à créer.
         * @return Le ContactDTO créé.
         */
        @PostMapping
        public ResponseEntity<ContactDTO> createContact(@RequestBody ContactDTO contactDTO) {
            ContactDTO createdContact = contactService.createContact(contactDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdContact);
        }

        /**
         * Récupérer un contact par son ID.
         * @param id L'ID du contact à récupérer.
         * @return Le ContactDTO correspondant.
         */
        @GetMapping("/{id}")
        public ResponseEntity<ContactDTO> findContactById(@PathVariable Long id) {
            ContactDTO contactDTO = contactService.findContactById(id);
            return ResponseEntity.ok(contactDTO);
        }
        @PutMapping
        public ResponseEntity<ContactDTO> updateContact(@PathVariable Long id,@RequestBody ContactDTO contactDTO){
            contactDTO.setId(id);
            ContactDTO updateContact=contactService.createContact(contactDTO);
            return new ResponseEntity<>(updateContact,HttpStatus.OK);
        }
    @GetMapping("/contacts/centre/{centreId}")
    public List<ContactDTO> getContactsByCentre(@PathVariable Long centreId) {
        return contactService.getContactsByCentreId(centreId);
    }

}
