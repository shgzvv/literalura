package com.example.literalura.principal;

import com.example.literalura.service.ConsumoAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class Menu {

    private static Scanner sc = new Scanner(System.in);
    private static ConsumoAPI consumoAPI;

    @Autowired
    public Menu(ConsumoAPI consumoAPI) {
        this.consumoAPI = consumoAPI;
    }

    public static void display_menu() {

        System.out.println("********************************");
        String menu = "Elija la opción a través de su número:\n" +
                "1. Buscar libro por título (registrar)\n" +
                "2. Consultar libros registrados\n" +
                "3. Consultar autores registrados\n" +
                "4. Consultar autores vivos registrados en un determinado año\n" +
                "5. Consultar libros registrados por idioma\n" +
                "0. Salir\n";

        var option = "";
        do {
            System.out.println(menu);
            System.out.println("********************************\n");
            System.out.print("Opción: ");
            option = sc.nextLine();

            switch (option) {
                case "1":
                    System.out.print("Ingrese el título del libro: ");
                    String title = sc.nextLine();
                    consumoAPI.findBookByTitle(title);
                    break;
                case "2":
                    consumoAPI.findAllRegisteredBooks();
                    break;
                case "3":
                    consumoAPI.findAllRegisteredAuthors();
                    break;
                case "4":
                    System.out.print("Ingrese el año: ");
                    int year = Integer.parseInt(sc.nextLine());
                    consumoAPI.findAliveAuthors(year);
                    break;
                case "5":
                    System.out.print("Ingrese el idioma: ");
                    String language = sc.nextLine();
                    consumoAPI.findBooksByLanguage(language);
                    break;
                case "0":
                    System.out.println("Saliendo...");
                    break;
                default:
                    System.out.println("Opción no válida");
                    break;
            }
            System.out.println("\n");
        } while (!option.equals("0"));



    }
}
