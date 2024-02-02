package biblioteca.Model;

import java.util.HashMap;
import java.util.Map;

public class Inventario {

    private static Inventario instanciaUnica = null;
    private Map<Libro, Integer> existenciaLibros;

    private Inventario() {
        existenciaLibros = new HashMap<>();
    }

    public static Inventario obtenerInstancia() {
        if (instanciaUnica == null) {
            instanciaUnica = new Inventario();
        }
        return instanciaUnica;
    }

    public void agregarLibro(Libro libro, int cantidad) {
        existenciaLibros.put(libro, cantidad);
    }

    public void mostrarInventario() {
        System.out.println("Inventario de libros:");
        for (Map.Entry<Libro, Integer> entry : existenciaLibros.entrySet()) {
            Libro libro = entry.getKey();
            int cantidad = entry.getValue();
            System.out.println(libro.getTitulo() + " - Cantidad: " + cantidad);
        }
    }

    // Otros métodos relacionados con el inventario

}
