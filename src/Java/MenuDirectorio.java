package Java;

import java.io.File;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

public class MenuDirectorio {
    private final File directorio;
    private final Scanner scanner;

    public MenuDirectorio(File directorio, Scanner scanner) {
        this.directorio = directorio;
        this.scanner = scanner;
    }

    public void mostrar() {
        int opcion;

        do {
            System.out.println("\n--- MENU DIRECTORIO ---");
            System.out.println("1. Mostrar directorio");
            System.out.println("2. Mostrar directorio recursivo");
            System.out.println("0. Salir");
            opcion = leerOpcion();

            switch (opcion) {
                case 1:
                    mostrarDirectorio();
                    break;
                case 2:
                    mostrarDirectorioRecursivo(directorio, "");
                    break;
                case 0:
                    System.out.println("Fin del programa.");
                    break;
                default:
                    System.out.println("Opcion no valida.");
            }
        } while (opcion != 0);
    }

    private int leerOpcion() {
        System.out.print("Selecciona una opcion: ");
        if (!scanner.hasNextLine()) {
            return 0;
        }

        String entrada = scanner.nextLine().trim();
        try {
            return Integer.parseInt(entrada);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private void mostrarDirectorio() {
        File[] elementos = obtenerElementos(directorio);
        if (elementos == null) {
            return;
        }

        if (elementos.length == 0) {
            System.out.println("El directorio esta vacio.");
            return;
        }

        for (File elemento : elementos) {
            System.out.println(tipoElemento(elemento) + " " + elemento.getName());
        }
    }

    private void mostrarDirectorioRecursivo(File directorioActual, String sangria) {
        if (sangria.isEmpty()) {
            System.out.println("[D] " + directorioActual.getPath());
        }

        File[] elementos = obtenerElementos(directorioActual);
        if (elementos == null) {
            return;
        }

        for (File elemento : elementos) {
            System.out.println(sangria + tipoElemento(elemento) + " " + elemento.getName());
            if (elemento.isDirectory()) {
                mostrarDirectorioRecursivo(elemento, sangria + "  ");
            }
        }
    }

    private File[] obtenerElementos(File directorioActual) {
        File[] elementos = directorioActual.listFiles();
        if (elementos == null) {
            System.out.println("No se puede acceder al directorio: " + directorioActual.getPath());
            return null;
        }

        Arrays.sort(elementos, Comparator.comparing(File::getName, String.CASE_INSENSITIVE_ORDER));
        return elementos;
    }

    private String tipoElemento(File elemento) {
        return elemento.isDirectory() ? "[D]" : "[F]";
    }
}
