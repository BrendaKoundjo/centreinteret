package com.centreinteret.centreinteret.repository;

import com.centreinteret.centreinteret.entities.Categorie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategorieRepository extends JpaRepository<Categorie,Long> {
}
