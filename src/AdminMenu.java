package biblioteca.Model;

import java.util.Scanner;

public class AdminMenu {

    private final ListaLibros listaLibros;

    public AdminMenu(ListaLibros listaLibros) {
        this.listaLibros = listaLibros;
    }

    public void mostrarMenu() {
        Scanner scanner = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("\n--- Menu de Administrador ---");
            System.out.println("1. Agregar Libro");
            System.out.println("2. Eliminar Libro");
            System.out.println("3. Consultar Biblioteca");
            System.out.println("4. Salir");
            System.out.print("Seleccione una opcion: ");

            opcion = scanner.nextInt();
            scanner.nextLine(); 

            switch (opcion) {
                case 1 -> agregarLibro();
                case 2 -> eliminarLibro();
                case 3 -> consultarBiblioteca();
                case 4 -> System.out.println("Saliendo del Menu de Administrador.");
                default -> System.out.println("Opcion no valida. Intente de nuevo.");
            }

        } while (opcion != 4);
    }

    private void agregarLibro() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\n--- Agregar Libro ---");
        System.out.print("Ingrese el titulo del libro: ");
        String titulo = scanner.nextLine();

        System.out.print("Ingrese el autor del libro: ");
        String autor = scanner.nextLine();

        System.out.print("Ingrese el genero del libro: ");
        String genero = scanner.nextLine();

        Libro nuevoLibro = new Libro(titulo, autor, genero);
        listaLibros.agregarLibro(nuevoLibro);

        System.out.println("Libro agregado exitosamente.");
    }

    private void eliminarLibro() {
    Scanner scanner = new Scanner(System.in);

    System.out.println("\n--- Eliminar Libro ---");
    System.out.print("Ingrese el titulo del libro que desea eliminar: ");
    String titulo = scanner.nextLine();

    Libro libroEliminar = listaLibros.buscarPorTitulo(titulo);

    if (libroEliminar != null) {
        listaLibros.eliminarLibro(libroEliminar);
        System.out.println("Libro eliminado exitosamente.");
    } else {
        System.out.println("El libro no se encontro en la lista.");
    }
}


    private void consultarBiblioteca() {
        System.out.println("\n--- Consultar Biblioteca ---");
        listaLibros.mostrarContenido();
    }
}
