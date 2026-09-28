package Java;

import java.io.File;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Uso: java Java.Main <fichero_o_directorio>");
            return;
        }

        File ruta = new File(args[0]);
        if (!ruta.exists()) {
            System.out.println("El fichero o directorio no existe: " + ruta.getPath());
            return;
        }

        try (Scanner scanner = new Scanner(System.in)) {
            if (ruta.isDirectory()) {
                System.out.println("La ruta indicada es un directorio.");
                new MenuDirectorio(ruta, scanner).mostrar();
            } else if (ruta.isFile()) {
                System.out.println("La ruta indicada es un fichero.");
                new MenuArchivo(ruta, scanner).mostrar();
            } else {
                System.out.println("La ruta existe, pero no es un fichero ni un directorio.");
            }
        }
    }
}
