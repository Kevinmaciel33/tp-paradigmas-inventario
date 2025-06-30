package org.unlam.paradigmas.zeta;

import java.util.Scanner;

import org.unlam.paradigmas.zeta.loaders.InventorySaver;
import org.unlam.paradigmas.zeta.enums.Classification;
import org.unlam.paradigmas.zeta.enums.QueryEnum;
import org.unlam.paradigmas.zeta.models.*;

import java.util.*;

import static org.unlam.paradigmas.zeta.Constants.FILE_INVENTORY_OUT;

public class Menu {
    private final Worker worker;
    private final Guide guide;

    public Menu(Worker w, Guide guide) {
        this.worker = w;
        this.guide = guide;
    }

    public void run() {
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
            executeOption(option);

        } while (option != 9);

        scanner.close();
    }

    private static void showMenu() {
        System.out.println("\n=== MENÚ PRINCIPAL ===");
        System.out.println("0. Guia de Elementos");
        System.out.println("1. ¿Qué necesito para craftear un objeto?");
        System.out.println("2. ¿Qué necesito para craftear un objeto desde cero?");
        System.out.println("3. ¿Qué me falta para craftear un objeto?");
        System.out.println("4. ¿Qué me falta para craftear un objeto desde cero?");
        System.out.println("5. ¿Cuántos puedo craftear?");
        System.out.println("6. Realizar un crafteo");
        System.out.println("7. Mostrar historial de crafteos");
        System.out.println("8. Mostrar inventario actual");
        System.out.println("9. Salir");
    }

    private void executeOption(int option) {

        switch (option) {
            case 0:
                this.showGuide();
                break;
            case 1:
                queryProcessInput(QueryEnum.ELEMENTS);
                break;
            case 2:
                queryProcessInput(QueryEnum.ELEMENTS_FROM_ZERO);
                break;
            case 3:
                queryProcessInput(QueryEnum.MISSING_ELEMENTS);
                break;
            case 4:
                queryProcessInput(QueryEnum.MISSING_ELEMENTS_FROM_ZERO);
                break;
            case 5:
                queryProcessInput(QueryEnum.HOW_MANY_ELEMENTS);
                break;
            case 6:
                performCraft();
                break;
            case 7:
                showCraftHistory();
                break;
            case 8:
                showCurrentInventory();
                break;
            case 9:
                System.out.println("Guardando inventario...");
                InventorySaver.saveToFile(worker.getInventory(), FILE_INVENTORY_OUT);
                System.out.println("Inventario guardado en " +FILE_INVENTORY_OUT+ ". Saliendo...");
                break;
            default:
                System.out.println("Opción no válida. Intentá de nuevo.");
        }
    }

    private Element buildElementFromInput() {

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

    private void queryProcessInput(QueryEnum query) {
        Scanner scanner = new Scanner(System.in);
        Element e = buildElementFromInput();
        try {
            Queryable res = worker.query(query, e);
            System.out.println(res.show());
            scanner.nextLine();
        } catch (IllegalArgumentException ex) {
            System.out.println("No encontramos receta para ese elemento");
            scanner.nextLine();
        }
    }

    private void performCraft() {
        Scanner scanner = new Scanner(System.in);
        Element e = this.buildElementFromInput();
        try {

            MultiRecipe multiRecipe = (MultiRecipe) worker.query(QueryEnum.ELEMENTS, e);
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

            worker.create(e, decitionMap.get(o-1));
            System.out.println("Elemento creado");
            scanner.nextLine();
        } catch (IllegalArgumentException ex) {
            System.out.println("No pudimos completar la peticion intente de nuevo");
        } catch (MissingResourceException ex) {
            System.out.println("No tiene todos los ingredientes necestarios. Falta: " + ex.getKey());
        }
    }

    protected void showCraftHistory() {
        for ( Log l : this.worker.getHistoric() ) {
            System.out.println("["+l.time+"] se creo "+l.recipe.give()+" con "+l.recipe.ingredients());
        }
    }

    protected void showGuide() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Los elementos bases son: ");
        for (Guide.Page p : this.guide.getBase()) {
            System.out.println(p.name()+": "+p.description());
        }
        System.out.println("Los elementos que se pueden crear: ");
        for (Guide.Page p : this.guide.getCreate()) {
            System.out.println(p.name()+": "+p.description());
        }
    }

    protected void showCurrentInventory() {
        Inventory inventory = worker.getInventory();
        System.out.println("Inventario: ");
        for (Map.Entry<Element, Integer> entry : inventory.getStock().entrySet()) {
            if ( entry.getValue() == 0 ) {
                continue;
            }

            System.out.println(entry.getKey() + " - " + entry.getValue());
        }
    }
}
