package com.challenge.literAlura.repository;

import com.challenge.literAlura.model.Idioma;
import com.challenge.literAlura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface LibroRepository extends JpaRepository<Libro, Long> {

    List<Libro> findByIdiomas(Idioma idioma);
    Optional<Libro> findByTitulo(String titulo);


}
