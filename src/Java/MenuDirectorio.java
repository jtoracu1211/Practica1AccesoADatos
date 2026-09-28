package Java;

import java.io.File;

import static Java.Main.teclado;

public class MenuDirectorio {
    public static void mostrarMenu(File directorio) {
        int opcion;

        do {
            System.out.println("\n- - - Menu Directorio - - -");
            System.out.println("0. Salir");
            System.out.println("1. Mostrar directorio");
            System.out.println("2. Mostrar directorio recursivo");
            System.out.print("Opcion: ");

            while (!teclado.hasNextInt()) {
                System.out.print("Error, introduce un numero: ");
                teclado.nextLine();
            }
            opcion = teclado.nextInt();
            teclado.nextLine();

            switch (opcion) {
                case 0 -> System.out.println("Fin del programa.");
                case 1 -> mostrarDirectorio(directorio);
                case 2 -> mostrarDirectorioRecursivo(directorio, "");
                default -> System.out.println("Opcion no valida.");
            }
        } while (opcion != 0);
    }

    public static void mostrarDirectorio(File directorio) {
        File[] lista = directorio.listFiles();

        if (lista == null) {
            System.out.println("No se puede mostrar el directorio.");
        } else if (lista.length == 0) {
            System.out.println("El directorio esta vacio.");
        } else {
            for (int i = 0; i < lista.length; i++) {
                if (lista[i].isDirectory()) {
                    System.out.println("[D] " + lista[i].getName());
                } else {
                    System.out.println("[F] " + lista[i].getName());
                }
            }
        }
    }

    public static void mostrarDirectorioRecursivo(File directorio, String sangria) {
        File[] lista = directorio.listFiles();

        if (lista == null) {
            System.out.println("No se puede mostrar el directorio.");
        } else {
            for (int i = 0; i < lista.length; i++) {
                if (lista[i].isDirectory()) {
                    System.out.println(sangria + "[D] " + lista[i].getName());
                    mostrarDirectorioRecursivo(lista[i], sangria + "  ");
                } else {
                    System.out.println(sangria + "[F] " + lista[i].getName());
                }
            }
        }
    }
}
