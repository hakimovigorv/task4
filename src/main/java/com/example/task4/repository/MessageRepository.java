package com.example.task4.repository;
import com.example.task4.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long>{

    @Query(value = "SELECT * FROM messages WHERE recipient = ?1", nativeQuery = true)
    List<Message> findAllByRecipient(String recipient);
}
