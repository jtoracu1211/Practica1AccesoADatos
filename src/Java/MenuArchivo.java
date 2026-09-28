package Java;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Scanner;

public class MenuArchivo {
    private final File archivo;
    private final Scanner scanner;

    public MenuArchivo(File archivo, Scanner scanner) {
        this.archivo = archivo;
        this.scanner = scanner;
    }

    public void mostrar() {
        int opcion;

        do {
            System.out.println("\n--- MENU FICHERO ---");
            System.out.println("1. Listar fichero");
            System.out.println("2. Listar fichero numerado");
            System.out.println("3. Encontrar texto");
            System.out.println("4. Anexar fichero");
            System.out.println("0. Salir");
            opcion = leerOpcion();

            switch (opcion) {
                case 1:
                    listarArchivo(false);
                    break;
                case 2:
                    listarArchivo(true);
                    break;
                case 3:
                    encontrarTexto();
                    break;
                case 4:
                    anexarArchivo();
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

        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private void listarArchivo(boolean numerarLineas) {
        try (BufferedReader lector = new BufferedReader(new FileReader(archivo))) {
            String linea;
            int numeroLinea = 1;

            while ((linea = lector.readLine()) != null) {
                if (numerarLineas) {
                    System.out.println(numeroLinea + "- " + linea);
                } else {
                    System.out.println(linea);
                }
                numeroLinea++;
            }
        } catch (IOException e) {
            System.out.println("No se ha podido leer el fichero: " + e.getMessage());
        }
    }

    private void encontrarTexto() {
        System.out.print("Texto a buscar: ");
        if (!scanner.hasNextLine()) {
            return;
        }

        String texto = scanner.nextLine();
        if (texto.isEmpty()) {
            System.out.println("El texto a buscar no puede estar vacio.");
            return;
        }

        boolean encontrado = false;
        try (BufferedReader lector = new BufferedReader(new FileReader(archivo))) {
            String linea;
            int numeroLinea = 1;

            while ((linea = lector.readLine()) != null) {
                int posicion = linea.indexOf(texto);
                while (posicion != -1) {
                    System.out.println(linea + " - linea " + numeroLinea + " posicion " + posicion);
                    encontrado = true;
                    posicion = linea.indexOf(texto, posicion + 1);
                }
                numeroLinea++;
            }
        } catch (IOException e) {
            System.out.println("No se ha podido leer el fichero: " + e.getMessage());
            return;
        }

        if (!encontrado) {
            System.out.println("No se ha encontrado el texto indicado.");
        }
    }

    private void anexarArchivo() {
        System.out.print("Fichero que se desea anexar: ");
        if (!scanner.hasNextLine()) {
            return;
        }

        String rutaAnexar = scanner.nextLine().trim();
        if (rutaAnexar.isEmpty()) {
            System.out.println("Debes indicar un fichero.");
            return;
        }

        File archivoAnexar = new File(rutaAnexar);
        if (!archivoAnexar.exists()) {
            System.out.println("El fichero a anexar no existe.");
            return;
        }
        if (!archivoAnexar.isFile()) {
            System.out.println("La ruta indicada para anexar es un directorio.");
            return;
        }

        try {
            if (archivo.getCanonicalFile().equals(archivoAnexar.getCanonicalFile())) {
                System.out.println("No se puede anexar un fichero a si mismo.");
                return;
            }
        } catch (IOException e) {
            System.out.println("No se ha podido comprobar el fichero a anexar: " + e.getMessage());
            return;
        }

        try (InputStream entrada = new FileInputStream(archivoAnexar);
             OutputStream salida = new FileOutputStream(archivo, true)) {
            byte[] buffer = new byte[8192];
            int bytesLeidos;

            while ((bytesLeidos = entrada.read(buffer)) != -1) {
                salida.write(buffer, 0, bytesLeidos);
            }
            System.out.println("Fichero anexado correctamente.");
        } catch (IOException e) {
            System.out.println("No se ha podido anexar el fichero: " + e.getMessage());
        }
    }
}
