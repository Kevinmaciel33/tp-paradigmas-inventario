package org.unlam.paradigmas.zeta;

import java.util.Scanner;
import org.unlam.paradigmas.zeta.loaders.InventorySaver;
import org.unlam.paradigmas.zeta.loaders.InventoryLoader;

public class Menu {
	
	public static final String FINAL_INVENTORY_PATH = "src/main/resources/inventory.json";
    
	public static void ejecutarMenu() {
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
            executeOption(option);

        } while (option != 0);

        scanner.close();
    }

    public static void ShowMenu() {
        System.out.println("\n=== MENÚ PRINCIPAL ===");
        System.out.println("1. ¿Qué necesito para craftear un objeto?");
        System.out.println("2. ¿Qué necesito para craftear un objeto desde cero?");
        System.out.println("3. ¿Qué me falta para craftear un objeto?");
        System.out.println("4. ¿Qué me falta para craftear un objeto desde cero?");
        System.out.println("5. ¿Cuántos puedo craftear?");
        System.out.println("6. Realizar un crafteo");
        System.out.println("7. Mostrar historial de crafteos");
        System.out.println("8. Mostrar receta de crafteo(Arbol)");
        System.out.println("9. Mostrar inventario actual");
        System.out.println("0. Salir");
    }

    public static void executeOption(int option) {
        clearConsole();
        switch (option) {
        case 1:
            whatDoINeedToCraft();
            break;
        case 2:
            whatDoINeedToCraftFromScratch();
            break;
        case 3:
            whatAmIMissingToCraft();
            break;
        case 4:
            whatAmIMissingToCraftFromScratch();
            break;
        case 5:
            howManyCanICraft();
            break;
        case 6:
            performCraft();
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
        	System.out.println("Guardando inventario...");
        	InventorySaver.saveToFile(InventoryLoader.getData(), FINAL_INVENTORY_PATH);
        	System.out.println("Inventario guardado en " + FINAL_INVENTORY_PATH + ". Saliendo...");
        	break;
        default:
            System.out.println("Opción no válida. Intentá de nuevo.");
        }
    }

    public static void whatDoINeedToCraft() {
        System.out.println("[1] Mostrando lo necesario para craftear el objeto...");
        clearConsole();
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

    public static void clearConsole() {
        for (int i = 0; i < 50; ++i)
            System.out.println();
    }
}
