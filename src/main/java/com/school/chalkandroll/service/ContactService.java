package com.school.chalkandroll.service;

import com.school.chalkandroll.model.Contact;
import com.school.chalkandroll.constants.chalkandrollConstants;
import com.school.chalkandroll.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service

public class ContactService {

    @Autowired
    private ContactRepository contactRepository;


    public boolean saveMessageDetails(Contact contact){
        boolean isSaved = false;
        contact.setStatus(chalkandrollConstants.OPEN);
        Contact savedContact = contactRepository.save(contact);
        if(null != savedContact && savedContact.getContactId()>0) {
            isSaved = true;
        }
        return isSaved;
    }

    // public List<Contact> findMsgsWithOpenStatus(){
    //     List<Contact> contactMsgs = contactRepository.findByStatus(EazySchoolConstants.OPEN);
    //     return contactMsgs;
    // }

    public boolean updateMsgStatus(int contactId){
        boolean isUpdated = false;
        int rows = contactRepository.updateMsgStatusNative(chalkandrollConstants.CLOSE, contactId);
        if(rows>0) {
            isUpdated = true;
        }
        return isUpdated;
    }

    public Page<Contact> findMsgsWithOpenStatus(int pageNum,String sortField, String sortDir){
        int pageSize = 5;
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize,
                sortDir.equals("asc") ? Sort.by(sortField).ascending()
                        : Sort.by(sortField).descending());
        Page<Contact> msgPage = contactRepository.findOpenMsgsNative(
                chalkandrollConstants.OPEN,pageable);
        return msgPage;
    }

}
