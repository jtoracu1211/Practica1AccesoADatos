package Java;

import java.io.*;

import static Java.Main.teclado;

public class MenuArchivo {
    public static void mostrarMenu(File archivo) throws IOException {
        int opcion;

        do {
            System.out.println("\n- - - Menu Fichero - - -");
            System.out.println("0. Salir");
            System.out.println("1. Listar fichero");
            System.out.println("2. Listar fichero numerado");
            System.out.println("3. Encontrar texto");
            System.out.println("4. Anexar fichero");
            System.out.print("Opcion: ");

            while (!teclado.hasNextInt()) {
                System.out.print("Error, introduce un numero: ");
                teclado.nextLine();
            }
            opcion = teclado.nextInt();
            teclado.nextLine();

            switch (opcion) {
                case 0 -> System.out.println("Fin del programa.");
                case 1 -> listarArchivo(archivo);
                case 2 -> listarArchivoNumerado(archivo);
                case 3 -> encontrarTexto(archivo);
                case 4 -> anexarFichero(archivo);
                default -> System.out.println("Opcion no valida.");
            }
        } while (opcion != 0);
    }

    public static void listarArchivo(File archivo) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(archivo));
        String linea;

        while ((linea = br.readLine()) != null) {
            System.out.println(linea);
        }

        br.close();
    }

    public static void listarArchivoNumerado(File archivo) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(archivo));
        String linea;
        int numeroLinea = 1;

        while ((linea = br.readLine()) != null) {
            System.out.println(numeroLinea + "- " + linea);
            numeroLinea++;
        }

        br.close();
    }

    public static void encontrarTexto(File archivo) throws IOException {
        System.out.print("Texto a buscar: ");
        String texto = teclado.nextLine();

        if (texto.equals("")) {
            System.out.println("No puedes buscar un texto vacio.");
            return;
        }

        BufferedReader br = new BufferedReader(new FileReader(archivo));
        String linea;
        int numeroLinea = 1;
        boolean encontrado = false;

        while ((linea = br.readLine()) != null) {
            int posicion = linea.indexOf(texto);

            while (posicion != -1) {
                System.out.println(linea + " - linea " + numeroLinea + " posicion " + posicion);
                encontrado = true;
                posicion = linea.indexOf(texto, posicion + 1);
            }
            numeroLinea++;
        }

        br.close();

        if (!encontrado) {
            System.out.println("No se ha encontrado el texto.");
        }
    }

    public static void anexarFichero(File archivo) throws IOException {
        System.out.print("Introduce el fichero que quieres anexar: ");
        String nombre = teclado.nextLine();
        File ficheroAnexar = new File(nombre);

        if (!ficheroAnexar.exists()) {
            System.out.println("El fichero a anexar no existe.");
        } else if (!ficheroAnexar.isFile()) {
            System.out.println("La ruta indicada es un directorio.");
        } else {
            BufferedReader br = new BufferedReader(new FileReader(ficheroAnexar));
            BufferedWriter bw = new BufferedWriter(new FileWriter(archivo, true));
            String linea;

            if (archivo.length() > 0) {
                bw.newLine();
            }

            while ((linea = br.readLine()) != null) {
                bw.write(linea);
                bw.newLine();
            }

            br.close();
            bw.close();
            System.out.println("Fichero anexado correctamente.");
        }
    }
}
