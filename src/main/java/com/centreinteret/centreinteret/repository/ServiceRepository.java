package com.centreinteret.centreinteret.repository;

import com.centreinteret.centreinteret.entities.ServiceI;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceRepository extends JpaRepository<ServiceI,Long> {
    List<ServiceI> findByCentreInteretId(Long centreInteretId);
}
