package org.unlam.paradigmas.zeta;

import org.unlam.paradigmas.zeta.enums.QueryEnum;
import org.unlam.paradigmas.zeta.models.Element;
import org.unlam.paradigmas.zeta.models.Queryable;

import java.util.Locale;
import java.util.Scanner;

public class Menu {

    public static void run(Worker w) {
        Scanner scanner = new Scanner(System.in);
        int option;

        do {
            showMenu();
            System.out.print("Elegí una opción: ");

            while (!scanner.hasNextInt()) {
                System.out.print("Entrada inválida. Ingresá un número: ");
                scanner.next();
            }

            option = scanner.nextInt();
            executeOption(option, w);

        } while (option != 10);

        scanner.close();
    }

    public static void showMenu() {
        System.out.println("\n=== MENÚ PRINCIPAL ===");
        System.out.println("0. Guia de Elementos");
        System.out.println("1. ¿Qué necesito para craftear un objeto?");
        System.out.println("2. ¿Qué necesito para craftear un objeto desde cero?");
        System.out.println("3. ¿Qué me falta para craftear un objeto?");
        System.out.println("4. ¿Qué me falta para craftear un objeto desde cero?");
        System.out.println("5. ¿Cuántos puedo craftear?");
        System.out.println("6. Realizar un crafteo");
        System.out.println("7. Mostrar historial de crafteos");
        System.out.println("8. Mostrar receta de crafteo(Arbol)");
        System.out.println("9. Mostrar inventario actual");
        System.out.println("10. Salir");
    }

    public static void executeOption(int option, Worker w) {

        switch (option) {
            case 1:
                queryProcessInput(w, QueryEnum.ELEMENTS);
                break;
            case 2:
                queryProcessInput(w, QueryEnum.ELEMENTS_FROM_ZERO);
                break;
            case 3:
                queryProcessInput(w, QueryEnum.MISSING_ELEMENTS);
                break;
            case 4:
                queryProcessInput(w, QueryEnum.MISSING_ELEMENTS_FROM_ZERO);
                break;
            case 5:
                queryProcessInput(w, QueryEnum.HOW_MANY_ELEMENTS);
                break;
            case 6:
                Scanner scanner = new Scanner(System.in);
                Element e = buildElementFromInput();
                try {
                    w.create(e);
                    scanner.nextLine();
                } catch (IllegalArgumentException ex) {
                    System.out.println("No pudimos completar la peticion intente de nuevo");
                }
                showCraftRecipeTree();
                break;
            case 9:
                showCurrentInventory();
                break;
            case 0:
                //TODO: show list of all elements
                break;
            default:
                System.out.println("Opción no válida. Intentá de nuevo.");
        }
    }

    private static Element buildElementFromInput() {

        Scanner scanner = new Scanner(System.in);

        System.out.println("Ingrese el elemento y el tipo (opcional)");
        String line = scanner.nextLine();
        //TODO: leer el type y que cree una consulta por type
        return new Element(line.toUpperCase(Locale.ROOT));
    }

    private static void queryProcessInput(Worker w, QueryEnum query) {
        Scanner scanner = new Scanner(System.in);
        Element e = buildElementFromInput();
        try {
            Queryable res = w.query(query, e);
            System.out.println(res.show());
            scanner.nextLine();
        } catch (IllegalArgumentException ex) {
            System.out.println("No encontramos receta para ese elemento");
            scanner.nextLine();
        }
    }

    public static void performCraft() {
        System.out.println("[6] Realizando el crafteo...");
    }

    public static void showCraftHistory() {
        System.out.println("[7] Mostrando historial de crafteos...");
    }

    public static void showCraftRecipeTree() {
        System.out.println("[8] Mostrando receta en forma de árbol...");
    }

    public static void showCurrentInventory() {
        System.out.println("[9] Mostrando inventario actual...");
    }
}
