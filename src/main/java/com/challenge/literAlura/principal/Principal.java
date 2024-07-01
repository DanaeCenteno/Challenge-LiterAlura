package com.challenge.literAlura.principal;

import com.challenge.literAlura.model.*;
import com.challenge.literAlura.repository.AutorRepository;
import com.challenge.literAlura.repository.LibroRepository;
import com.challenge.literAlura.service.ConsumoAPI;
import com.challenge.literAlura.service.ConvierteDatos;

import java.util.*;

public class Principal {

    Scanner teclado = new Scanner(System.in);
    ConsumoAPI consumoAPI = new ConsumoAPI();

    private final String URL_BASE = "https://gutendex.com/books/?search=";
    private ConvierteDatos conversor = new ConvierteDatos();
    private LibroRepository libroRepository;
    private AutorRepository autorRepository;
    private List<Libro> listDatosLibro;
    private List<Autor> listDatosAutor;

    public Principal(LibroRepository repositoryLibro, AutorRepository repositoryAutor) {
        this.libroRepository = repositoryLibro;
        this.autorRepository = repositoryAutor;
    }

    public void muestraMenu(){
        var opcion = -1;
        while (opcion != 0) {

            System.out.println("\n\t~~~~~~~ LIBRERÍA LITER-ALURA ~~~~~~");
            var menu = """
                    1 - Buscar libros por título
                    2 - Listar libros registrados
                    3 - Listar autores registrados
                    4 - Listar autores vivos en un determinado año
                    5 - Buscar libros por idioma
 
                    0 - Salir
                    \t~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
                    """;

            System.out.println(menu);
            System.out.println("Opcion: ");
            opcion = teclado.nextInt();
            teclado.nextLine();

            switch (opcion) {
                case 1:
                    buscarLibro();
                    break;
                case 2:
                    mostrarLibrosRegistrados();
                    break;
                case 3:
                  mostrarAutoresRegistrados();
                    break;
                case 4:
                    listaAutoresVivos();
                    break;
                case 5:
                    buscarLibroPorIdioma();
                    break;
          
                case 0:
                    System.out.println("Saliendo de la Libreria LiterAlura... ");
                    break;
                default:
                    System.out.printf("Opción inválida\n");

            }
        }
    }




    private Datos getBusqueda() {
        System.out.println("Escribe el nombre del libro: ");
        var nombreLibro = teclado.nextLine();
        var json = consumoAPI.obtenerDatos(URL_BASE + nombreLibro.replace(" ", "%20"));
        Datos datos = conversor.obtenerDatos(json, Datos.class);
        return datos;

    }

    //BUSCAR LIBRO POR NOMBRE/TITULO
    private void buscarLibro() {
    Datos datos = getBusqueda();

        if(datos != null && !datos.listLibros().isEmpty()){
            DatosLibro libroEncontrado = datos.listLibros().get(0);

            System.out.println("Libro Encontrado");
            System.out.println(libroEncontrado);

            Libro libro = new Libro(libroEncontrado);


            Optional<Libro> book = libroRepository.findByTitulo(libro.getTitulo());

           if (book.isPresent()){
               System.out.println("\nLibro registrado con Exito!!");
           }else {
               if(!libroEncontrado.autor().isEmpty()){
                   DatosAutor autor = libroEncontrado.autor().get(0);
                   Autor buscaAutor = new Autor(autor);
                   Optional<Autor> obtenerAutor = autorRepository.findByNombre(buscaAutor.getNombre());

                   if (obtenerAutor.isPresent()) {
                       Autor autorExiste = obtenerAutor.get();
                       libro.setAutor(autorExiste);
                       libroRepository.save(libro);
                   } else {
                       Autor autorNuevo = autorRepository.save(buscaAutor);
                       libro.setAutor(autorNuevo);
                       libroRepository.save(libro);
                   }

               }
           }

            System.out.println(libro);
        }else{
            System.out.println("Libro no Encontrado");
        }


    }

    //LISTADO DE LIBROS REGISTRADOS DE ACUERDO A LA BUSQUEDA DE LIBROS
    private void mostrarLibrosRegistrados() {
        listDatosLibro = libroRepository.findAll();
        listDatosLibro.stream()
                .sorted(Comparator.comparing(Libro::getTitulo))
                .forEach(System.out::println);
    }

    //LISTADO DE AUTORES DE ACUERDO A LOS LIBROS BUSCADOS
    private void mostrarAutoresRegistrados() {
        listDatosAutor = autorRepository.findAll();
        listDatosAutor.stream()
                .sorted(Comparator.comparing(Autor::getNombre))
                .forEach(System.out::println);
    }

    //BUSQUEDA DE AUTORES DE ACUERDO AL AÑO INSERTADO
    private void listaAutoresVivos() {
        System.out.println("Inserta el año para buscar al autor: ");
        var autorVivo = teclado.nextLine();

        listDatosAutor =autorRepository.buscarAutorVivo(autorVivo);

        listDatosAutor.stream()
                .forEach(System.out::println);
        
    }


    private List<Libro> busquedaLibroPorIdioma(String idiomas){
        var idiomaBuscado = Idioma.fromString(idiomas);
        List<Libro> lenguaje = libroRepository.findByIdiomas(idiomaBuscado);
        return lenguaje;
    }

    //BUSQUEDA DE LIBROS DE ACUERDO AL IDIOMA
    private void buscarLibroPorIdioma() {

        String opcion = "";
        while (!opcion.equals(0)){
            System.out.println("~~~~~~ IDIOMAS ~~~~~~");
            var idioma = """
                    es - Español
                    en - Inglés
                    fr - Francés
                    pt - Portugués

                    0. Regresar al menu principal
                    """;
            System.out.println("Elige un idioma: \n" + idioma);
            opcion = teclado.nextLine();
            switch (opcion){
                case "es":
                    List<Libro> idiomaEspanol = busquedaLibroPorIdioma("[es]");
                    idiomaEspanol.forEach(System.out::println);
                    break;

                case "en":
                    List<Libro> idiomaIngles = busquedaLibroPorIdioma("[en]");
                    idiomaIngles.forEach(System.out::println);
                 break;

                case "fr":
                    List<Libro> idiomafrances = busquedaLibroPorIdioma("[fr]");
                    idiomafrances.forEach(System.out::println);
                    break;

                case "pt":
                    List<Libro> idiomaPortugues = busquedaLibroPorIdioma("[pt]");
                    idiomaPortugues.forEach(System.out::println);
                    break;

                default:
                    System.out.println("No se encontraron libros con este idioma");

                case "0":
                    return;
            }
        }
    }
}//cierra clase

