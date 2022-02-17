package com.example.task4.controller;


import com.example.task4.model.Message;
import com.example.task4.service.MessageService;
import com.example.task4.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


@Controller
public class MainController {

    @Autowired
    private UserService userService;
    @Autowired
    private MessageService messageService;
    @Autowired private SimpMessagingTemplate simpMessagingTemplate;

    @GetMapping({"/"})
    public String home( Model model) {
        return "redirect:/messages";
    }

    @GetMapping("/messages")
    public String showMessages(HttpServletRequest request, Model model) {
        model.addAttribute("users", userService.allUsers());
        List<Message> messages = messageService.findMessagesByRecipient(request.getRemoteUser());
        Collections.sort(messages, Comparator.comparing(Message::getSendDate).reversed());
        model.addAttribute("messages", messages);
        return "/messages";
    }

    @MessageMapping("/messages")
    public void sendMessage(final Message message, Principal principal) throws InterruptedException {
        message.setSendDate(new Timestamp(System.currentTimeMillis()));
        message.setSender(principal.getName());
        messageService.saveMessage(message);
        simpMessagingTemplate.convertAndSendToUser(message.getRecipient(), "/topic/messages", message);
    }

}

