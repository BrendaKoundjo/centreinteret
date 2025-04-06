package com.centreinteret.centreinteret.repository;

import com.centreinteret.centreinteret.entities.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactRepository extends JpaRepository<Contact,Long> {
    List<Contact> findByCentreinteretId(Long centreId);

}
