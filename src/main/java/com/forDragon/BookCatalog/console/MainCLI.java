package com.forDragon.BookCatalog.console;

import com.forDragon.BookCatalog.model.*;
import com.forDragon.BookCatalog.repository.AuthorRepository;
import com.forDragon.BookCatalog.repository.BookRepository;
import com.forDragon.BookCatalog.service.DataConverter;
import com.forDragon.BookCatalog.service.GutendexApi;

import java.util.*;

public class MainCLI {

    private Scanner keyBoardInput = new Scanner(System.in);

    private final String URL_BASE = "https://gutendex.com/books/?search=";
    private GutendexApi callApi = new GutendexApi();
    private DataConverter converter = new DataConverter();

    private List<BookData> bookDataList = new ArrayList<>();
    private List<Book> bookList;
    private List<Author> authorList;

    private BookRepository repository;
    private AuthorRepository authorRepository;

    //@Autowired
    //private BookService bookService;

    public MainCLI(BookRepository repository, AuthorRepository authorRepository) {
        this.repository = repository;
        this.authorRepository = authorRepository;
    }

    public void showMenu() {
        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                    1 - Buscar libro por Titulo
                    2 - Mostrar libros buscados
                    3 - Buscar Autor por Nombre
                    4 - Mostrar Lista de Autores
                    5 - Buscar libros en un determinado idioma
                    6 - Buscar Autores vivos en determinado año
                    7 - Top 10 libros más descargados
                    8 - Mostrar estadisticas Libros
                                  
                    0 - Salir
                    """;
            System.out.println(menu);
            opcion = keyBoardInput.nextInt();
            keyBoardInput.nextLine();

            switch (opcion) {
                case 1:
                    searchBook();
                    break;
                case 2:
                    showLocalBooks();
                    break;
                case 3:
                    getAuthorByName();
                case 4:
                    showAuthors();
                    break;
                case 5:
                    getBooksByLanguage();
                    break;
                case 6:
                    getAliveAuthorsByYear();
                    break;
                case 7:
                    getTop10Books();
                    break;
                case 8:
                    booksStatistics();
                    break;
                case 0:
                    System.out.println("Cerrando la aplicación...");
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        }
    }

    //Case 1
    private void searchBook(){
        ApiResponse response = getBookFromApi();
        if (!response.results().isEmpty()) {
            System.out.println("ApiResponse" + response);
            BookData firstBook = response.results().get(0);
            System.out.println("firstBook: " + firstBook + " \n");
            saveBook(firstBook);
        }
        else{
            System.out.println("Error on SearchBooks ");
        }
    }

    private void saveBook(BookData book) {
            try {
                // Save the book and its associated author using the BookRepository
                Book newBook = new Book(book);
                repository.save(newBook);
                System.out.println("Book and Author saved successfully!");

            } catch (Exception e) {
                System.out.println("Error saving book: " + e.getMessage());
            }
    }

    private ApiResponse getBookFromApi() {
        System.out.println("Escribe el nombre del Libro que deseas buscar");
        var searchInput = keyBoardInput.nextLine();

        var jsonResponse = callApi.fetchBooks(URL_BASE + searchInput.replace(" ", "+") ); //could use "%20" instead of "+"
        System.out.println("json: "+ jsonResponse);

        ApiResponse data = converter.convertData(jsonResponse, ApiResponse.class);
        return data;
    }

    //Case 2
    private void showLocalBooks() {
        bookList = repository.findAll();

        bookList.stream()
                .sorted(Comparator.comparing(Book::getTitle))
                .forEach(System.out::println);
    }

    //Case 3
    private void getAuthorByName() {
        System.out.println("Escribe el nombre del Autor que deseas buscar");
        var authorName = keyBoardInput.nextLine();
        List<Author> authorFound = repository.getAuthorByName(authorName);
        authorFound.forEach(a ->
                System.out.printf("Author: Nombre %s Nacimiento %s Muerte %s\n",
                        a.getName(), a.getBirthYear(), a.getDeathYear()));
    }

    //Case 4
    private void showAuthors() {
        authorList = authorRepository.findAll();

        authorList.stream()
                .sorted(Comparator.comparing(Author::getName))
                .forEach(System.out::println);
    }

    //Case 5
    private void getBooksByLanguage(){
        System.out.println("Escribe el idioma que deseas buscar (en, es, fr)");
        var bookLanguage = keyBoardInput.nextLine();
        List<Book> booksFound = repository.findByLanguage(bookLanguage);
        booksFound.forEach(b ->
                System.out.printf("Book: Idioma - %s, Titulo - %s, Descargas -  %s.\n",
                        b.getLanguage(), b.getTitle(), b.getDownloadCount()));

        long bookCount = booksFound.stream().count();
        System.out.println("Total elementos: " + bookCount);
    }

    //Case 6
    private void getAliveAuthorsByYear(){
        System.out.println("Escribe el año que deseas buscar");
        int givenYear = Integer.parseInt(keyBoardInput.nextLine());
        List<Author> aliveAuthors = authorRepository.authorAliveByGivenYear(givenYear);
        aliveAuthors.forEach(a ->
                System.out.printf("Author: Nombre %s Nacimiento %s Muerte %s\n",
                        a.getName(), a.getBirthYear(), a.getDeathYear()));
    }

    //Case 7
    private void getTop10Books(){
        List<Book> booksFound = repository.findTop10ByOrderByDownloadCountDesc();
        booksFound.forEach(b ->
                System.out.printf("Book: Descargas - %s, Titulo - %s, Idioma - %s, Autor %s.\n",
                        b.getDownloadCount(), b.getTitle(), b.getLanguage(), b.getAuthor()));
    }

    //Case 8
    private void booksStatistics() {
        bookList = repository.findAll();
        IntSummaryStatistics statistics = bookList.stream()
                .mapToInt(Book::getDownloadCount)
                .summaryStatistics();

        System.out.println("Total de Descargas: " + statistics.getSum() + ", Total elementos: " + statistics.getCount());
        System.out.println("Descargas promedio: " + statistics.getAverage());
        System.out.println("Descargas mínima: " + statistics.getMin());
        System.out.println("Descargas máxima: " + statistics.getMax());
    }
}
