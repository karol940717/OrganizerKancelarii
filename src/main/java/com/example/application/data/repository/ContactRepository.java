package com.example.application.data.repository;

import com.example.application.data.entity.Contact;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ContactRepository extends JpaRepository<Contact, Integer> {
    @Query("SELECT c FROM Contact c WHERE " +
            "LOWER(c.date) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(c.claimant) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(c.defendant) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(c.signature) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(c.personWhichGo) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(c.comments) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(c.victim) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(c.room) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(c.time) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(c.court) LIKE LOWER(CONCAT('%', :searchTerm, '%')) ")
    List<Contact> search(@Param("searchTerm") String searchTerm);
}
