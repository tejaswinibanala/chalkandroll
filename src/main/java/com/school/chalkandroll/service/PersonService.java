package com.school.chalkandroll.service;

import com.school.chalkandroll.constants.chalkandrollConstants;
import com.school.chalkandroll.model.Person;
import com.school.chalkandroll.model.Roles;
import com.school.chalkandroll.repository.PersonRepository;
import com.school.chalkandroll.repository.RolesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PersonService {

    @Autowired
    PersonRepository personRepository;

    @Autowired
    RolesRepository rolesRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public boolean createNewPerson(Person person) {
        boolean isSaved=false;
        Roles roles=rolesRepository.findByRoleName(chalkandrollConstants.STUDENT_ROLE);
        person.setRoles(roles);
        person.setPwd(passwordEncoder.encode(person.getPwd()));

        person=personRepository.save(person);
        if(person!=null && person.getPersonId()>0){
            return !isSaved;
        }
        return isSaved;
    }
}