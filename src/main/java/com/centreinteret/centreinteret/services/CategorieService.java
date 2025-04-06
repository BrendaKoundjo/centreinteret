package com.centreinteret.centreinteret.services;

import com.centreinteret.centreinteret.dto.CategorieDTO;

import java.util.List;

public interface CategorieService {
    List<CategorieDTO> listeCategories();
    CategorieDTO createCategorie(CategorieDTO categorieDTO);
    CategorieDTO findCategorieById(Long id);
}
