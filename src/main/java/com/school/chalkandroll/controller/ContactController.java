package com.school.chalkandroll.controller;

import com.school.chalkandroll.model.Contact;
import com.school.chalkandroll.service.ContactService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.logging.Logger;

@Slf4j
@Controller
public class ContactController {
    private final Logger logger=Logger.getLogger(ContactController.class.getName());
    private final ContactService contactService;



    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }


    @RequestMapping("/contact")
    public String displayContact(Model model){
        model.addAttribute("contact",new Contact());
        return "contact.html";
    }



    @RequestMapping(value = "/saveMsg",method = RequestMethod.POST)
    public String takeContactDetails(@Valid @ModelAttribute("contact") Contact contact, Errors errors){
        if(errors.hasErrors()){
            logger.info("Errors"+errors.toString());
            return "contact.html";
        }
        contactService.saveMessageDetails(contact);
        return "redirect:/contact";
    }
    @RequestMapping("/displayMessages/page/{pageNum}")
    public ModelAndView displayMessages(Model model,
                                        @PathVariable(name = "pageNum") int pageNum,@RequestParam("sortField") String sortField,
                                        @RequestParam("sortDir") String sortDir) {
        Page<Contact> msgPage = contactService.findMsgsWithOpenStatus(pageNum,sortField,sortDir);
        List<Contact> contactMsgs = msgPage.getContent();
        ModelAndView modelAndView = new ModelAndView("messages.html");
        model.addAttribute("currentPage", pageNum);
        model.addAttribute("totalPages", msgPage.getTotalPages());
        model.addAttribute("totalMsgs", msgPage.getTotalElements());
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
        modelAndView.addObject("contactMsgs",contactMsgs);
        return modelAndView;
    }

    @RequestMapping(value = "/closeMsg",method = RequestMethod.GET)
    public String closeMsg(@RequestParam int id) {
        contactService.updateMsgStatus(id);
        return "redirect:/displayMessages/page/1?sortField=name&sortDir=desc";
    }


}
