package Modelo;

import com.mongodb.client.MongoCollection;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class LibroDAO {

    private final MongoCollection<Document> coleccionLibros;

    public LibroDAO(MongoCollection<Document> coleccionLibros) {
        this.coleccionLibros = coleccionLibros;
    }

    public void agregarLibro(Libro libro) {
        Document libroDoc = convertirLibroADocumento(libro);
        coleccionLibros.insertOne(libroDoc);
    }

    public void actualizarLibro(Libro libro) {
        Document filtro = new Document("titulo", libro.getTitulo());
        Document nuevoDato = new Document("$set", convertirLibroADocumento(libro));
        coleccionLibros.updateOne(filtro, nuevoDato);
    }

    public void eliminarLibro(String titulo) {
        Document filtro = new Document("titulo", titulo);
        coleccionLibros.deleteOne(filtro);
    }

    public List<Libro> obtenerTodosLosLibros() {
        List<Libro> libros = new ArrayList<>();
        for (Document documento : coleccionLibros.find()) {
            libros.add(documentoToLibro(documento));
        }
        return libros;
    }

    private Document convertirLibroADocumento(Libro libro) {
        return new Document("titulo", libro.getTitulo())
                .append("autor", libro.getAutor())
                .append("genero", libro.getGenero())
                .append("disponible", libro.estaDisponible())
                .append("stock", libro.getStock());
    }

    private Libro documentoToLibro(Document documento) {
        String titulo = documento.getString("titulo");
        String autor = documento.getString("autor");
        String genero = documento.getString("genero");
        boolean disponible = documento.getBoolean("disponible");

        Libro libro = new Libro(titulo, autor, genero);
        libro.setDisponible(disponible);
        libro.setStock(documento.getInteger("stock", 0));

        return libro;
    }
}
