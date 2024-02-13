package Modelo;

import com.mongodb.client.MongoCollection;
import javax.swing.JOptionPane;
import org.bson.Document;

public class UsuarioDAO {
  private final MongoCollection<Document> usuarioCollection;

    public UsuarioDAO(MongoCollection<Document> usuarioCollection) {
        this.usuarioCollection = usuarioCollection;  
    }    
    public void registrarUsuario(Usuario user1) {
        try {
            if (existeUsuario(user1.getEmail())) {
                JOptionPane.showMessageDialog(null, "Ya existe un usuario con este correo.", "Error de Registro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Document usuarioDoc = new Document("nombre", user1.getNombre())
                    .append("direccion", user1.getDireccion())
                    .append("telefono", user1.getTelf())
                    .append("correo", user1.getEmail())
                    .append("contrasenia", user1.getPassword());

            usuarioCollection.insertOne(usuarioDoc);

            JOptionPane.showMessageDialog(null, "Registro exitoso");
        } catch (Exception ex) {
            System.err.println("Error al insertar usuario en la base de datos: " + ex.getMessage());
            ex.printStackTrace();
            throw new RuntimeException("Error al insertar usuario en la base de datos.", ex);
        }
    }



    public boolean existeUsuario(String correo) {
        Document query = new Document("correo", correo);
        return usuarioCollection.countDocuments(query) > 0;
    }
    
    public boolean autenticarUsuario(String correo, String contrasenia) {
        Document query = new Document("correo", correo).append("contrasenia", contrasenia);
        return usuarioCollection.countDocuments(query) > 0;
    }

}
