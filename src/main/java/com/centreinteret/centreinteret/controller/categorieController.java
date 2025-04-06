package com.centreinteret.centreinteret.controller;

import java.util.List;

import com.centreinteret.centreinteret.services.CategorieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.centreinteret.centreinteret.dto.CategorieDTO;

@RestController
@RequestMapping("/api/categories")
@CrossOrigin(origins = "http://localhost:8100",
        methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE},
        allowedHeaders = "*")
public class categorieController {
 
     @Autowired
    private CategorieService categorieService;

    // Endpoint pour lister toutes les catégories
    @GetMapping
    public ResponseEntity<List<CategorieDTO>> getAllCategories() {
        List<CategorieDTO> categories = categorieService.listeCategories();
        return ResponseEntity.ok(categories);
    }

    // Endpoint pour créer une nouvelle catégorie (si nécessaire)
    @PostMapping
    public ResponseEntity<CategorieDTO> createCategorie(@RequestBody CategorieDTO categorieDTO) {
        CategorieDTO createdCategorie = categorieService.createCategorie(categorieDTO);
        return ResponseEntity.status(201).body(createdCategorie);
    }

    // Endpoint pour récupérer une catégorie par ID
    @GetMapping("/{id}")
    public ResponseEntity<CategorieDTO> getCategorieById(@PathVariable Long id) {
        CategorieDTO categorie = categorieService.findCategorieById(id);
        return ResponseEntity.ok(categorie);
    }
    @PutMapping
    public ResponseEntity<CategorieDTO> updateCategorie(@PathVariable long id,@RequestBody CategorieDTO categorieDTO){
        categorieDTO.setId(id);
        CategorieDTO updateCategorie=categorieService.createCategorie(categorieDTO);
        return new ResponseEntity<>(updateCategorie, HttpStatus.OK);
    }
}
