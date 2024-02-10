package Modelo;

import java.util.List;
import java.util.Scanner;

public class Admin extends User {

    private static Admin instance;

    public Admin() {
        super("", "", "", "", "");
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

            System.out.println("Ingrese la dirección del administrador:");
            String direccion = scanner.nextLine();
            instance.setDireccion(direccion);

            System.out.println("Ingrese el teléfono del administrador:");
            String telf = scanner.nextLine();
            instance.setTelf(telf);

            System.out.println("Ingrese el correo electrónico del administrador:");
            String email = scanner.nextLine();
            instance.setEmail(email);

            System.out.println("Ingrese la contraseña del administrador:");
            String password = scanner.nextLine();
            // Hashear la contraseña antes de guardarla en producción
            instance.setPassword(password);

            System.out.println("Datos del administrador inicializados con éxito.\n");
        } else {
            System.out.println("Ya se han inicializado los datos del administrador.\n");
        }
    }

    public static synchronized void printAdminInfo() {
        System.out.println("Información del Administrador:");
        System.out.println("Nombre: " + instance.getNombre());
        System.out.println("Dirección: " + instance.getDireccion());
        System.out.println("Teléfono: " + instance.getTelf());
        System.out.println("Correo electrónico: " + instance.getEmail());
    }

    public boolean autenticar(String correo, String contrasena) {
        return this.getEmail().equals(correo) && this.getPassword().equals(contrasena);
    }

    public void agregarLibro(List<Libro> listaLibros, String titulo, String autor, String genero) {
        if (this.autenticar()) {
            Libro nuevoLibro = LibroFactory.crearLibro(titulo, autor, genero);
            listaLibros.add(nuevoLibro);
            System.out.println("Libro agregado: " + nuevoLibro);
        } else {
            System.out.println("Acceso no autorizado para agregar libros.");
        }
    }

    public void actualizarLibro(List<Libro> listaLibros, String titulo, String nuevoTitulo, String nuevoAutor, String nuevoGenero) {
        if (this.autenticar()) {
            Libro libroActualizado = buscarLibroPorTitulo(listaLibros, titulo);
            if (libroActualizado != null) {
                libroActualizado.setTitulo(nuevoTitulo);
                libroActualizado.setAutor(nuevoAutor);
                libroActualizado.setGenero(nuevoGenero);
                System.out.println("Libro actualizado: " + libroActualizado);
            } else {
                System.out.println("Libro no encontrado: " + titulo);
            }
        } else {
            System.out.println("Acceso no autorizado para actualizar libros.");
        }
    }

    public void eliminarLibro(List<Libro> listaLibros, String titulo) {
        if (this.autenticar()) {
            Libro libroEliminado = buscarLibroPorTitulo(listaLibros, titulo);
            if (libroEliminado != null) {
                listaLibros.remove(libroEliminado);
                System.out.println("Libro eliminado: " + libroEliminado);
            } else {
                System.out.println("Libro no encontrado: " + titulo);
            }
        } else {
            System.out.println("Acceso no autorizado para eliminar libros.");
        }
    }

    private Libro buscarLibroPorTitulo(List<Libro> listaLibros, String titulo) {
        for (Libro libro : listaLibros) {
            if (libro.getTitulo().equals(titulo)) {
                return libro;
            }
        }
        return null;
    }

    public boolean autenticar() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Ingrese su correo electronico:");
        String correo = scanner.nextLine();
        System.out.println("Ingrese su contrasenia:");
        String contrasenia = scanner.nextLine();

        // Verifica si el correo y la contraseña coinciden con las del administrador
        return this.getEmail().equals(correo) && this.getPassword().equals(contrasenia);
    }
}
