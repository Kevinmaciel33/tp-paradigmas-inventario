package org.example;

import java.util.Scanner;

public class Menu {

	public static void ejecutarMenu() {
		Scanner scanner = new Scanner(System.in);
		int opcion;

		do {
			mostrarMenu();
			System.out.print("Elegí una opción: ");

			while (!scanner.hasNextInt()) {
				System.out.print("Entrada inválida. Ingresá un número: ");
				scanner.next();
			}

			opcion = scanner.nextInt();
			ejecutarOpcion(opcion);

		} while (opcion != 0);

		scanner.close();
	}

	public static void mostrarMenu() {
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

	public static void ejecutarOpcion(int opcion) {
		limpiarConsola();
		switch (opcion) {
		case 1:
			queNecesitoParaCraftear();
			break;
		case 2:
			queNecesitoDesdeCero();
			break;
		case 3:
			queMeFaltaParaCraftear();
			break;
		case 4:
			queMeFaltaDesdeCero();
			break;
		case 5:
			cuantosPuedoCraftear();
			break;
		case 6:
			realizarCrafteo();
			break;
		case 7:
			mostrarHistorial();
			break;
		case 8:
			mostrarRecetaArbol();
			break;
		case 9:
			mostrarInventario();
			break;
		case 0:
			System.out.println("Volviendo al menú anterior / Saliendo...");
			break;
		default:
			System.out.println("Opción no válida. Intentá de nuevo.");
		}
	}

	public static void queNecesitoParaCraftear() {
		System.out.println("[1] Mostrando lo necesario para craftear el objeto...");
		limpiarConsola();
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

	public static void queNecesitoDesdeCero() {
		System.out.println("[2] Mostrando lo necesario para craftear el objeto desde cero...");
	}

	public static void queMeFaltaParaCraftear() {
		System.out.println("[3] Mostrando lo que te falta para craftear el objeto...");
	}

	public static void queMeFaltaDesdeCero() {
		System.out.println("[4] Mostrando lo que te falta para craftear desde cero...");
	}

	public static void cuantosPuedoCraftear() {
		System.out.println("[5] Calculando cuántos objetos se pueden craftear...");
	}

	public static void realizarCrafteo() {
		System.out.println("[6] Realizando el crafteo...");
	}

	public static void mostrarHistorial() {
		System.out.println("[7] Mostrando historial de crafteos...");
	}

	public static void mostrarRecetaArbol() {
		System.out.println("[8] Mostrando receta en forma de árbol...");
	}

	public static void mostrarInventario() {
		System.out.println("[9] Mostrando inventario actual...");
	}

	public static void limpiarConsola() {
		for (int i = 0; i < 50; ++i) System.out.println();
	}
}
