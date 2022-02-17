package com.example.task4.service;

import com.example.task4.model.Message;
import com.example.task4.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Service
public class MessageService {
    @Autowired
    MessageRepository messageRepository;

    /*public List<Message> findMessagesBySender(String sender){
        return messageRepository.findAllBySender(sender);
    }*/
    public List<Message> findMessagesByRecipient(String recipient){
        return messageRepository.findAllByRecipient(recipient);
    }
    public void saveMessage(Message message) {
        messageRepository.save(message);
    }
}
