package Modelo;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;



public class Conexion {

    private static MongoDatabase baseDatos;
    private MongoClient mongoClient;

    public Conexion() {
        try {
            String password = "789456123";
            String clusterUrl = "cluster0.kurfcc8.mongodb.net";
            String databaseName = "Biblioteca";

            MongoClientURI uri = new MongoClientURI("mongodb+srv://gnuxxx:" + password + "@" + clusterUrl + "/" + databaseName);
            //MongoClientURI uri = new MongoClientURI("mongodb://localhost:27017/" + databaseName);
            mongoClient = new MongoClient(uri);

            baseDatos = mongoClient.getDatabase(databaseName);

            System.out.println("Conexion Exitosa");
        } catch (Exception e) {
            System.err.println("Error al establecer la conexión: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public MongoDatabase getBaseDatos() {
        return this.baseDatos;
    }

    public static  MongoCollection<Document> getColeccion(String nombreColeccion) {
        return baseDatos.getCollection(nombreColeccion);
    }

    public void cerrarConexion() {
        if (mongoClient != null) {
            mongoClient.close();
            System.out.println("Conexion cerrada");
        }
    }
    
    
}
