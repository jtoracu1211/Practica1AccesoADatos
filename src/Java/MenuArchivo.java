package Java;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class MenuArchivo {
    private final File archivo;
    private final Scanner scanner;

    public MenuArchivo(File archivo, Scanner scanner) {
        this.archivo = archivo;
        this.scanner = scanner;
    }

    public void mostrar() throws IOException {
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

        String entrada = scanner.nextLine().trim();
        if (entrada.equals("1")) {
            return 1;
        }
        if (entrada.equals("2")) {
            return 2;
        }
        if (entrada.equals("3")) {
            return 3;
        }
        if (entrada.equals("4")) {
            return 4;
        }
        if (entrada.equals("0")) {
            return 0;
        }
        return -1;
    }

    private void listarArchivo(boolean numerarLineas) throws IOException {
        BufferedReader lector = new BufferedReader(new FileReader(archivo));
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

        lector.close();
    }

    private void encontrarTexto() throws IOException {
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
        BufferedReader lector = new BufferedReader(new FileReader(archivo));
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

        lector.close();

        if (!encontrado) {
            System.out.println("No se ha encontrado el texto indicado.");
        }
    }

    private void anexarArchivo() throws IOException {
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

        if (archivo.getCanonicalFile().equals(archivoAnexar.getCanonicalFile())) {
            System.out.println("No se puede anexar un fichero a si mismo.");
            return;
        }

        FileInputStream entrada = new FileInputStream(archivoAnexar);
        FileOutputStream salida = new FileOutputStream(archivo, true);
        byte[] buffer = new byte[8192];
        int bytesLeidos;

        while ((bytesLeidos = entrada.read(buffer)) != -1) {
            salida.write(buffer, 0, bytesLeidos);
        }
        entrada.close();
        salida.close();
        System.out.println("Fichero anexado correctamente.");
    }
}
