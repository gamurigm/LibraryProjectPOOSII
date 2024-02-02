package biblioteca.Model;

import java.util.Scanner;

public class Admin extends User {

    private static Admin instance; 

    private Admin() {
        super("Default Admin", "Default Address", "Default Phone", "admin@example.com", "adminPassword");
    }

    public static synchronized Admin getInstance() {
        if (instance == null) {
            instance = new Admin();
        }
        return instance;
    }

    public static synchronized void inicializarDatos() {
        if (instance == null) {
            instance = new Admin();
            Scanner scanner = new Scanner(System.in);

            System.out.println("Ingrese el nombre del administrador:");
            String nombre = scanner.nextLine();
            instance.setNombre(nombre);

            System.out.println("Ingrese la direccion del administrador:");
            String direccion = scanner.nextLine();
            instance.setDireccion(direccion);

            System.out.println("Ingrese el telefono del administrador:");
            String telf = scanner.nextLine();
            instance.setTelf(telf);

            System.out.println("Ingrese el correo electronico del administrador:");
            String email = scanner.nextLine();
            instance.setEmail(email);

            System.out.println("Ingrese la contrasenia del administrador:");
            String password = scanner.nextLine();
            instance.setPassword(password);

            System.out.println("Datos del administrador inicializados con exito.\n");
        } else {
            System.out.println("Ya se han inicializado los datos del administrador.\n");
        }
    }

    public static synchronized void printAdminInfo() {
        System.out.println("Informacion del Administrador:");
        System.out.println("Nombre: " + instance.getNombre());
        System.out.println("Direccion: " + instance.getDireccion());
        System.out.println("Telefono: " + instance.getTelf());
        System.out.println("Correo electronico: " + instance.getEmail());
    }
}
