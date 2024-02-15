package Modelo;

import com.mongodb.client.MongoCollection;
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
        Document document = new Document();
        historialCollection.insertOne(document);
    }

    public void cargarDesdeMongoDB() {
        prestamos.clear();
    }
}
