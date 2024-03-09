package Modelo;

import com.mongodb.client.MongoCollection;
import org.bson.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Historial {

    private User usuario;
    private List<Document> prestamos; // Cambié la lista a Document para almacenar directamente los documentos de MongoDB
    private MongoCollection<Document> historialCollection;
    private static Historial instance;
    private UsuarioDAO usuarioDAO; // Agregamos esta variable de instancia

    private Historial() {
        this.prestamos = new ArrayList<>();
    }

    public static Historial getInstance() {
        if (instance == null) {
            instance = new Historial();
        }
        return instance;
    }

    public void setHistorialCollection(MongoCollection<Document> historialCollection) {
        this.historialCollection = historialCollection;
    }

    public void setUsuarioDAO(UsuarioDAO usuarioDAO) {
        this.usuarioDAO = usuarioDAO;
    }

    public List<Document> getPrestamos() {
        return prestamos;
    }

 public void agregarPrestamo(String libro, User usuario) {
    // Obtén la fecha actual como String
    String fechaPrestamo = Utils.getFechaActual();

    // Calcula la fecha de devolución sumando 30 días a la fecha actual
    Date fechaDevolucionDate = Utils.sumarDias(Utils.stringToDate(fechaPrestamo), 30);
    String fechaDevolucion = Utils.dateToString(fechaDevolucionDate);

    // Crea un documento para el nuevo préstamo
    Document nuevoPrestamo = new Document()
            .append("libro", libro)
            .append("fechaPrestamo", fechaPrestamo)
            .append("fechaDevolucion", fechaDevolucion);

    // Agrega el nuevo préstamo a la lista
    prestamos.add(nuevoPrestamo);

    // Actualiza la información en MongoDB
    guardarEnMongoDB();
}


    public void guardarEnMongoDB() {
        try {
            User usuarioActual = usuarioDAO.getUsuarioActual();
            if (usuarioActual != null) {
                usuario = usuarioActual;
                // Crear un documento para almacenar toda la información del usuario y sus préstamos
                Document documentoHistorial = new Document("usuario", new Document("usuarioId", usuario.getId())
                        .append("nombre", usuario.getNombre())
                        .append("direccion", usuario.getDireccion())
                        .append("telefono", usuario.getTelf())
                        .append("correo", usuario.getEmail())
                        .append("contrasenia", usuario.getPassword()))
                        .append("prestamos", prestamos);

                historialCollection.insertOne(documentoHistorial);
            } else {
                System.out.println("El usuario o UsuarioDAO es nulo, no se puede guardar en MongoDB.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void cargarDesdeMongoDB() {
        prestamos.clear();
        // Implementa la carga desde MongoDB según tus necesidades
    }
}

