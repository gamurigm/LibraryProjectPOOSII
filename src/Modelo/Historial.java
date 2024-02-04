package Modelo;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.bson.Document;

public class Historial {

    private User usuario;
    private List<Prestamo> prestamos;
    private MongoCollection<Document> historialCollection;

    public Historial(User usuario, MongoCollection<Document> historialCollection) {
        this.usuario = usuario;
        this.prestamos = new ArrayList<>();
        this.historialCollection = historialCollection;
    }

    public List<Prestamo> getPrestamos() {
        return prestamos;
    }

    public void agregarPrestamo(Libro libro, LocalDate fechaPrestamo, LocalDate fechaDevolucion) {
        Prestamo prestamo = new Prestamo((Usuario) usuario, libro, fechaPrestamo, fechaDevolucion);
        prestamos.add(prestamo);
        guardarEnMongoDB();
    }

    private void guardarEnMongoDB() {
        // Guardar el historial en MongoDB
        Document document = new Document();
        // Agregar información relevante a 'document'
        historialCollection.insertOne(document);
    }

    public void cargarDesdeMongoDB() {
        // Cargar historial desde MongoDB y actualizar la lista 'prestamos'
        prestamos.clear();
        // Obtener información desde MongoDB y actualizar 'prestamos'
    }
}
