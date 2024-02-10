package Modelo;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class Conexion {

    private MongoDatabase baseDatos;
    private MongoCollection<Document> coleccionLibros;
    private MongoClient mongoClient;

    public Conexion() {
        try {
            String password = "789456123g";
            String clusterUrl = "cluster0.kurfcc8.mongodb.net";
            String databaseName = "Biblioteca";

            MongoClientURI uri = new MongoClientURI("mongodb+srv://gnuxxx:" + password + "@" + clusterUrl + "/" + databaseName);

            mongoClient = new MongoClient(uri);

            baseDatos = mongoClient.getDatabase(databaseName);

            // Colección "Libros"
            coleccionLibros = baseDatos.getCollection("Libros");

            System.out.println("Conexion Exitosa");
        } catch (Exception e) {
            System.err.println("Error al establecer la conexion: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public MongoDatabase getBaseDatos() {
        return this.baseDatos;
    }

    public MongoCollection<Document> getColeccionLibros() {
        return this.coleccionLibros;
    }

    public void cerrarConexion() {
        if (mongoClient != null) {
            mongoClient.close();
            System.out.println("Conexion cerrada");
        }
    }
}
