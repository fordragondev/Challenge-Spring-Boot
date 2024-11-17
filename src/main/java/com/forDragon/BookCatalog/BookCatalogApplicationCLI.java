package com.forDragon.BookCatalog;

import com.forDragon.BookCatalog.console.MainCLI;
import com.forDragon.BookCatalog.repository.AuthorRepository;
import com.forDragon.BookCatalog.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BookCatalogApplicationCLI implements CommandLineRunner {

	@Autowired
	private BookRepository repository;

	@Autowired
	private AuthorRepository authorRepository;

	public static void main(String[] args) {
		SpringApplication.run(BookCatalogApplicationCLI.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		MainCLI myMainCLI = new MainCLI(repository, authorRepository);
		myMainCLI.showMenu();
	}


}
