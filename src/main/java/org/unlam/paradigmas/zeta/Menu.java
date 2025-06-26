package org.unlam.paradigmas.zeta;

import org.unlam.paradigmas.zeta.enums.QueryEnum;
import org.unlam.paradigmas.zeta.models.Element;
import org.unlam.paradigmas.zeta.models.Queryable;

import java.util.Locale;
import java.util.Scanner;

public class Menu {

    public static void ejecutarMenu(Worker w) {
        Scanner scanner = new Scanner(System.in);
        int option;

        do {
            ShowMenu();
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

    public static void ShowMenu() {
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
        Scanner scanner = new Scanner(System.in);
        Element e = null;

        switch (option) {
        case 1:
            e = buildElementFromInput();
            try {
                Queryable res = w.query(QueryEnum.ELEMENTS, e);
                System.out.println(res.toString());
                scanner.nextLine();
            } catch (IllegalArgumentException ex) {
                System.out.println("No encontramos receta para ese elemento");
                scanner.nextLine();
            }
            break;
        case 2:
            e = buildElementFromInput();
            try {
                w.query(QueryEnum.ELEMENTS_FROM_ZERO, e);
                scanner.nextLine();
            } catch (IllegalArgumentException ex) {
                System.out.println("No pudimos completar la peticion intente de nuevo");
            }
            break;
        case 3:
            e = buildElementFromInput();
            w.query(QueryEnum.MISSING_ELEMENTS, e);
            scanner.nextLine();
            break;
        case 4:
            e = buildElementFromInput();
            w.query(QueryEnum.MISSING_ELEMENTS_FROM_ZERO, e);
            scanner.nextLine();
            break;
        case 5:
            e = buildElementFromInput();
            w.query(QueryEnum.HOW_MANY_ELEMENTS, e);
            scanner.nextLine();
            break;
        case 6:
            e = buildElementFromInput();
            try {
                w.create(e);
                scanner.nextLine();
            } catch (IllegalArgumentException ex) {
                System.out.println("No pudimos completar la peticion intente de nuevo");
            }
            break;
        case 7:
            showCraftHistory();
            break;
        case 8:
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

    public static void whatDoINeedToCraft() {
        System.out.println("[1] Mostrando lo necesario para craftear el objeto...");
        Scanner scanner = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("=== ¿Qué me falta para craftear un objeto? ===");
            System.out.println("--- Aca se mostraria el inventario");

            System.out.println("0. Volver al menú principal");
            System.out.print("Elegí una opción: ");

            while (!scanner.hasNextInt()) {
                System.out.print("Entrada inválida. Ingresá un número: ");
                scanner.next();
            }

            opcion = scanner.nextInt();

            switch (opcion) {
            case 0:
                System.out.println("Volviendo al menú principal...");
                break;
            default:
                System.out.println("Opción no válida.");
            }
        } while (opcion != 0);
    }

    public static void whatDoINeedToCraftFromScratch() {
        System.out.println("[2] Mostrando lo necesario para craftear el objeto desde cero...");
    }

    public static void whatAmIMissingToCraft() {
        System.out.println("[3] Mostrando lo que te falta para craftear el objeto...");
    }

    public static void whatAmIMissingToCraftFromScratch() {
        System.out.println("[4] Mostrando lo que te falta para craftear desde cero...");
    }

    public static void howManyCanICraft() {
        System.out.println("[5] Calculando cuántos objetos se pueden craftear...");
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
