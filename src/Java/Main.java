package Java;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static Scanner teclado = new Scanner(System.in);

    static void main() throws IOException {
        System.out.print("Introduce la ruta del fichero o directorio: ");
        String nombre = teclado.nextLine();
        File fichero = new File(nombre);

        if (!fichero.exists()) {
            System.out.println("El fichero o directorio no existe.");
        } else if (fichero.isDirectory()) {
            System.out.println("La ruta indicada es un directorio.");
            MenuDirectorio.mostrarMenu(fichero);
        } else if (fichero.isFile()) {
            System.out.println("La ruta indicada es un fichero.");
            MenuArchivo.mostrarMenu(fichero);
        }
    }
}
