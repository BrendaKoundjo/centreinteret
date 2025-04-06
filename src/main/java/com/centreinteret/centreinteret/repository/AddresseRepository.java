package com.centreinteret.centreinteret.repository;

import com.centreinteret.centreinteret.entities.Addresse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AddresseRepository extends JpaRepository<Addresse, Long> {
//    List<Addresse> findByCentreinteretId(Long centreInteretId);
    List<Addresse> findByQuartierContainingIgnoreCase(String quartier);
    List<Addresse> findByVilleContainingIgnoreCase(String ville);
    List<Addresse>findByPaysContainingIgnoreCase(String pays);
    List<Addresse>findByPointDeRepareContainingIgnoreCase(String pointderepere);

}