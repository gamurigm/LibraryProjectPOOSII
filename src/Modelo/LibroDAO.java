package Modelo;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import org.bson.Document;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import org.bson.types.ObjectId;

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
    
    public void modificarLibro(String tituloAntiguo, String autorAntiguo, String generoAntiguo, String nuevoTitulo, String nuevoAutor, String nuevoGenero) {
    Document filtro = new Document("titulo", tituloAntiguo)
                        .append("autor", autorAntiguo)
                        .append("genero", generoAntiguo);

    Document libroExistente = coleccionLibros.find(filtro).first();

    if (libroExistente != null) {
  
        Document nuevoDato = new Document("$set", new Document("titulo", nuevoTitulo)
                                                    .append("autor", nuevoAutor)
                                                    .append("genero", nuevoGenero));

        coleccionLibros.updateOne(filtro, nuevoDato);
    }
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
    
      

    public List<Libro> buscarLibros(String nombre, String autor, String genero, String codigo) {
        List<Libro> resultados = new ArrayList<>();

        try {
            Document query = new Document();
            if (!nombre.isEmpty()) query.append("titulo", new Document("$regex", nombre));
            if (!autor.isEmpty()) query.append("autor", new Document("$regex", autor));
            if (!genero.isEmpty()) query.append("genero", new Document("$regex", genero));
            if (!codigo.isEmpty()) query.append("codigo", Integer.valueOf(codigo));

            try (MongoCursor<Document> cursor = coleccionLibros.find(query).iterator()) {
                while (cursor.hasNext()) {
                    Document libroDoc = cursor.next();
                    Libro libro = documentoToLibro(libroDoc);

                    // Obtener el _id y establecerlo en el Libro
                    ObjectId mongoId = libroDoc.getObjectId("_id");
                    libro.setMongoId(mongoId);

                    resultados.add(libro);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return resultados;
    }

    public void mostrarResultadosEnTabla(List<Libro> resultados, JTable tablaMostrar) {
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(new Object[]{"NOMBRE", "AUTOR", "GENERO", "CODIGO"});

        for (Libro libro : resultados) {
            model.addRow(new Object[]{libro.getTitulo(), libro.getAutor(), libro.getGenero(), libro.getMongoId()});
        }
        tablaMostrar.setModel(model);
    }     
}
