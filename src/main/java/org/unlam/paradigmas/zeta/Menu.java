package org.unlam.paradigmas.zeta;

import java.util.Scanner;

import org.unlam.paradigmas.zeta.loaders.InventoryJson;
import org.unlam.paradigmas.zeta.loaders.InventorySaver;
import org.unlam.paradigmas.zeta.enums.Classification;
import org.unlam.paradigmas.zeta.enums.QueryEnum;
import org.unlam.paradigmas.zeta.models.Element;
import org.unlam.paradigmas.zeta.models.MultiRecipe;
import org.unlam.paradigmas.zeta.models.Queryable;
import org.unlam.paradigmas.zeta.models.Recipe;

import java.util.*;

import static org.unlam.paradigmas.zeta.Constants.FILE_INVENTORY_OUT;

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
                performCraft(w);
                break;
            case 9:
                showCurrentInventory(w);
                break;
            case 0:
                //TODO: show list of all elements and the classififcations
                break;
            case 10:
                System.out.println("Guardando inventario...");
                InventorySaver.saveToFile(w.getInventory(), FILE_INVENTORY_OUT);
                System.out.println("Inventario guardado en " +FILE_INVENTORY_OUT+ ". Saliendo...");
                break;
            default:
                System.out.println("Opción no válida. Intentá de nuevo.");
        }
    }

    private static Element buildElementFromInput() {

        Scanner scanner = new Scanner(System.in);

        System.out.println("Ingrese el elemento:");
        String name = scanner.nextLine().trim();
        System.out.println("Ingrese el tipo del elemento ( default ALL ):");
        String type = scanner.nextLine().trim();
        //TODO: leer el type y que cree una consulta por type
        if ( type.isEmpty() ) {
            return new Element(name.toUpperCase(Locale.ROOT));
        }

        return new Element(name.toUpperCase(Locale.ROOT), Classification.valueOf(type.toUpperCase(Locale.ROOT)));
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

    public static void performCraft(Worker w) {
        Scanner scanner = new Scanner(System.in);
        Element e = buildElementFromInput();
        try {

            MultiRecipe multiRecipe = (MultiRecipe) w.query(QueryEnum.ELEMENTS, e);
            System.out.println(multiRecipe.show());

            if ( multiRecipe.libraries.isEmpty()) {
                return;
            }

            Map<Integer, Recipe> decitionMap = new HashMap<>();
            for (int i=0;i<multiRecipe.libraries.size();++i) {
                Map.Entry<String, Recipe> l = multiRecipe.libraries.get(i);
                Recipe r = l.getValue();
                decitionMap.put(i, r);
                r.show();
            }
            System.out.println("Ingrese el numero de receta que desea usar: ");
            int o = scanner.nextInt();

            if ( o > decitionMap.size() || o < 0 ) o = 1;

            w.create(e, decitionMap.get(o-1));
            scanner.nextLine();
        } catch (IllegalArgumentException ex) {
            System.out.println("No pudimos completar la peticion intente de nuevo");
        } catch (MissingResourceException ex) {
            System.out.println("No tiene todos los ingredientes necestarios. Falta: " + ex.getKey());
        }
    }

    public static void showCraftHistory() {
        System.out.println("[7] Mostrando historial de crafteos...");
    }

    public static void showCurrentInventory(Worker w) {
        Inventory inventory = w.getInventory();
        System.out.println("Inventario: ");
        for (Map.Entry<Element, Integer> entry : inventory.getStock().entrySet()) {
            if ( entry.getValue() == 0 ) {
                continue;
            }

            System.out.println(entry.getKey() + " - " + entry.getValue());
        }

    }
}
