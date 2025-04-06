package com.centreinteret.centreinteret.serviceImplement;

import com.centreinteret.centreinteret.dto.CategorieDTO;
import com.centreinteret.centreinteret.entities.Categorie;
import com.centreinteret.centreinteret.repository.CategorieRepository;
import com.centreinteret.centreinteret.services.CategorieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategorieServiceImplement implements CategorieService {
    @Autowired
    private CategorieRepository categorieRepository;

    // Méthode pour lister toutes les catégories
    @Override
    public List<CategorieDTO> listeCategories() {
        return categorieRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // Méthode pour créer une nouvelle catégorie
    @Override
    public CategorieDTO createCategorie(CategorieDTO categorieDTO) {
        Categorie categorie = mapToEntity(categorieDTO);
        Categorie savedCategorie = categorieRepository.save(categorie);
        return mapToDTO(savedCategorie);
    }

    // Mapper l'entité Categorie vers CategorieDTO
    private CategorieDTO mapToDTO(Categorie categorie) {
        CategorieDTO dto = new CategorieDTO();
        dto.setId(categorie.getId());
        dto.setNom(categorie.getNom());
        return dto;
    }

    // Mapper CategorieDTO vers l'entité Categorie
    private Categorie mapToEntity(CategorieDTO dto) {
        Categorie categorie = new Categorie();
        categorie.setNom(dto.getNom());
        return categorie;
    }

    // Méthode pour trouver une catégorie par ID
    @Override
    public CategorieDTO findCategorieById(Long id) {
        return categorieRepository.findById(id)
                .map(this::mapToDTO)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Catégorie non trouvée"));
    }
}
