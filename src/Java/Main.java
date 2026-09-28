package Java;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] parametros) throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Introduce la ruta del fichero o directorio: ");
        File ruta = new File(scanner.nextLine());

        if (!ruta.exists()) {
            System.out.println("El fichero o directorio no existe: " + ruta.getPath());
        } else if (ruta.isDirectory()) {
            System.out.println("La ruta indicada es un directorio.");
            new MenuDirectorio(ruta, scanner).mostrar();
        } else if (ruta.isFile()) {
            System.out.println("La ruta indicada es un fichero.");
            new MenuArchivo(ruta, scanner).mostrar();
        } else {
            System.out.println("La ruta existe, pero no es un fichero ni un directorio.");
        }

        scanner.close();
    }
}
