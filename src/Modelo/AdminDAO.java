package Modelo;

import com.mongodb.client.MongoCollection;
import org.bson.Document;

public class AdminDAO {

    private final MongoCollection<Document> adminCollection;

    public AdminDAO(MongoCollection<Document> adminCollection) {
        this.adminCollection = adminCollection;
    }

    public void registrarAdmin(Admin admin) {
        Document adminDoc = new Document("nombre", admin.getNombre())
                .append("direccion", admin.getDireccion())
                .append("telefono", admin.getTelf())
                .append("correo", admin.getEmail())
                .append("contrasenia", admin.getPassword());

        adminCollection.insertOne(adminDoc);
    }

    public boolean autenticarAdmin(String correo, String contrasenia) {
        Document query = new Document("correo", correo).append("contrasenia", contrasenia);
        return adminCollection.countDocuments(query) > 0;
    }
    
    public boolean existeAdminRegistrado() {
    return adminCollection.countDocuments() > 0;
    }

}
