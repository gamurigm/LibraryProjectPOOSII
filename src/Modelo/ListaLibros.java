package Modelo;

import com.mongodb.client.MongoCollection;
import org.bson.Document;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class ListaLibros {

    private final MongoCollection<Document> coleccionLibros;
    private final List<Libro> listaLibros;

    // Constructor que acepta la colección y la lista de libros
    public ListaLibros(MongoCollection<Document> coleccionLibros, List<Libro> listaLibros) {
        this.coleccionLibros = coleccionLibros;
        this.listaLibros = listaLibros;
    }

    public void agregarLibro(Libro libro) {
        Document libroDoc = convertirLibroADocumento(libro);
        coleccionLibros.insertOne(libroDoc);
    }

    public void eliminarLibro(String titulo) {
        coleccionLibros.deleteOne(new Document("titulo", titulo));
        System.out.println("Libro eliminado: " + titulo);
    }

    public Libro buscarPorTitulo(String titulo) {
        Document libroDoc = coleccionLibros.find(new Document("titulo", titulo)).first();
        if (libroDoc != null) {
            return documentoToLibro(libroDoc);
        }
        return null;
    }

    public ListaLibros buscarPorGenero(String genero) {
        List<Libro> resultados = new ArrayList<>();
        for (Document document : coleccionLibros.find(new Document("genero", genero))) {
            Libro libro = documentoToLibro(document);
            resultados.add(libro);
        }
        return new ListaLibros(coleccionLibros, resultados);
    }

    public ListaLibros buscarPorAutor(String autor) {
        List<Libro> resultados = new ArrayList<>();
        for (Document document : coleccionLibros.find(new Document("autor", autor))) {
            Libro libro = documentoToLibro(document);
            resultados.add(libro);
        }
        return new ListaLibros(coleccionLibros, resultados);
    }

    public void guardarLibrosEnArchivo() {
        String archivoPath = "src/librosprueba1.txt";
        try (PrintWriter writer = new PrintWriter(new FileWriter(archivoPath))) {
            for (Document libroDoc : coleccionLibros.find()) {
                writer.println(libroDoc.toJson());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void cargarLibrosDesdeArchivo() {
        String archivoPath = "src/librosprueba1.txt";
        List<Document> libros = leerLibrosDesdeArchivo(archivoPath);
        coleccionLibros.insertMany(libros);
    }

  private List<Document> leerLibrosDesdeArchivo(String archivoPath) {
    List<Document> libros = new ArrayList<>();

    try (BufferedReader reader = new BufferedReader(new FileReader(archivoPath))) {
        String jsonLine;
        while ((jsonLine = reader.readLine()) != null) {
            System.out.println("Línea leída: " + jsonLine); // Agrega esta línea para imprimir cada línea
            Document libroDoc = Document.parse(jsonLine);
            libros.add(libroDoc);
        }
    } catch (IOException e) {
        e.printStackTrace();
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
