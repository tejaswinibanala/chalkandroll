package com.school.chalkandroll.controller;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import com.school.chalkandroll.model.Contact;
import com.school.chalkandroll.model.Response;
import com.school.chalkandroll.repository.ContactRepository;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;



import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/content")
public class ContactRestController {
    private final Logger logger=Logger.getLogger(ContactRestController.class.getName());

    private final ContactRepository contactRepository;

    private boolean isPresent;

    public ContactRestController(ContactRepository contactRepository){
        this.contactRepository=contactRepository;
    }

    @GetMapping("/display")
    public List<Contact> getContacts(@RequestParam(name = "status")String status){
        return contactRepository.findByStatus(status);
    }

    @GetMapping("/getContact")
    public List<Contact> getContactByStatus(@RequestBody Contact contact){

        if(contact != null && contact.getStatus() != null){
            return contactRepository.findByStatus(contact.getStatus());
        }
        return List.of();

    }
    @PostMapping("/saveContact")
    public ResponseEntity<Response> saveContact(@Valid @RequestBody Contact contact){
        if(contact != null){
            contactRepository.save(contact);
        }
        Response response=new Response("200","Created");
        return ResponseEntity.status(HttpStatus.CREATED).header("isSaved","true").body(response);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Response> deleteContact(@PathVariable("id") int id){
        Contact contact=contactRepository.findById(id).orElse(null);
        System.out.println(contact.getContactId());
        Response response=new Response();
        if(contact != null && contact.getContactId()==id){
            contactRepository.deleteById(id);
            response.setResponseCode("200");
            response.setResponseMessage("Deleted");
            return ResponseEntity.status(HttpStatus.OK).header("isDeleted","true").body(response);

        }
        response.setResponseCode("400");
        response.setResponseMessage("Error in deletion");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).header("isDeleted","false").body(response);

    }
    @PutMapping("/updateContact")
    public ResponseEntity<Response> updateContact(RequestEntity<Contact> requestEntity){
        Response response=new Response();

        HttpHeaders headers=requestEntity.getHeaders();
        headers.forEach(
                (key,value) -> {
                    logger.info(String.format(key,value.stream().collect(Collectors.joining("|"))));
                }
        );

        Contact contact=requestEntity.getBody();
        contactRepository.findById(contact.getContactId()).ifPresentOrElse(exx -> {this.isPresent=true;contactRepository.save(contact);}, () -> {
            // Handle the case where the Contact does not exist
            this.isPresent=false;
            System.out.println("Contact not found");
        });
        if(isPresent){
            response.setResponseCode("201");
            response.setResponseMessage("updated");
            return ResponseEntity.status(HttpStatus.CREATED).header("isUpdated","true").body(response);
        }
        response.setResponseCode("401");
        response.setResponseMessage("BAD_REQUEST");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).header("isUpdated","false").body(response);

    }
}
