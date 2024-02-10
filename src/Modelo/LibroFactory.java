package Modelo;



public class LibroFactory {
    public static Libro crearLibro(String titulo, String autor, String genero) {
        return new Libro(titulo, autor, genero);
    }
}
