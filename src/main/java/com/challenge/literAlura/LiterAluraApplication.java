package com.challenge.literAlura;

import com.challenge.literAlura.model.DatosAutor;
import com.challenge.literAlura.model.DatosLibro;
import com.challenge.literAlura.principal.Principal;
import com.challenge.literAlura.repository.AutorRepository;
import com.challenge.literAlura.repository.LibroRepository;
import com.challenge.literAlura.service.ConsumoAPI;
import com.challenge.literAlura.service.ConvierteDatos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LiterAluraApplication implements CommandLineRunner {

@Autowired
	private LibroRepository repositoryLibro;

@Autowired
	private AutorRepository repositoryAutor;

	public static void main(String[] args) {
		SpringApplication.run(LiterAluraApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal(repositoryLibro, repositoryAutor);
		principal.muestraMenu();
	}
}
