package com.challenge.literAlura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.persistence.*;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import java.util.List;

@Entity
@Table(name = "autores")
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String nombre;
    private String fechaDeNacimiento;
    private String fechaMuerte;

    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Libro> listLibros;

    public Autor(){}

    public Autor(DatosAutor datosAutor) {
        this.nombre = datosAutor.nombre();
        this.fechaDeNacimiento = datosAutor.fechaDeNacimiento();
        this.fechaMuerte = datosAutor.fechaMuerte();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFechaDeNacimiento() {
        return fechaDeNacimiento;
    }

    public void setFechaDeNacimiento(String fechaDeNacimiento) {
        this.fechaDeNacimiento = fechaDeNacimiento;
    }

    public String getFechaMuerte() {
        return fechaMuerte;
    }

    public void setFechaMuerte(String fechaMuerte) {
        this.fechaMuerte = fechaMuerte;
    }

    public List<Libro> getListLibros() {
        return listLibros;
    }

    public void setListLibros(List<Libro> listLibros) {
        this.listLibros = listLibros;
    }

    @Override
    public String toString() {
        StringBuilder libros = new StringBuilder();
        libros.append("Libros escritos: ");
        for (int i = 0; i < listLibros.size(); i++) {
            libros.append(listLibros.get(i).getTitulo());
        }
        return String.format("\t~~~~~~ AUTOR ~~~~~~%n" +
                                    "NOMBRE: %s%n" +
                                    "FECHA DE NACIMIENTO: %s%n" +
                                    "FECHA DE MUERTE: %s%n" +
                                    "%s%n" +
                              "\t~~~~~~~~~~~~~~~~~~~~%n", nombre, fechaDeNacimiento, fechaMuerte, libros.toString());
    }

}
