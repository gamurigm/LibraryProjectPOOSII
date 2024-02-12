package Modelo;

//import com.google.gson.Gson;

public class Libro {

    private static int nextId = 1;

    private final int id;
    private String titulo;
    private String autor;
    private String genero;
    private boolean disponible;
    private int stock;

    public Libro(String titulo, String autor, String genero) {
        this.id = nextId++;
        this.titulo = titulo;
        this.autor = autor;
        this.genero = genero;
        this.disponible = true;
    }

    public int getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getAutor() {
        return autor;
    }

    public String getGenero() {
        return genero;
    }

    public boolean estaDisponible() {
        return disponible;
    }

    public int getStock() {
        return stock;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

//    public String toJson() {
//        Gson gson = new Gson();
//        return gson.toJson(this);
//    }

    public void saveToMongoDB() {
        LibroDAO libroDAO = new LibroDAO(new Conexion().getColeccion("Libros"));
        libroDAO.agregarLibro(this);
    }

    public static Libro register(String titulo, String autor, String genero) {
        Libro nuevoLibro = new Libro(titulo, autor, genero);
        nuevoLibro.saveToMongoDB();
        return nuevoLibro;
    }

    @Override
    public String toString() {
        return """
               Libro {
               \tid: """ + id +
                "\n\ttitulo: " + titulo +
                "\n\tautor: " + autor +
                "\n\tgenero: " + genero +
                "\n\tdisponible: " + disponible +
                "\n\tstock: " + stock +
                "\n}\n";
    }
}
