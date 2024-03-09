package Modelo;

import javax.swing.JOptionPane;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.bson.types.ObjectId;

public class UsuarioDAO {
  private final MongoCollection<Document> usuarioCollection;

    public UsuarioDAO(MongoCollection<Document> usuarioCollection) {
        this.usuarioCollection = usuarioCollection;  
    }    
    
    public User getUsuarioActual() {
        return GestorBiblioteca.usuario;
    }

    
    public void registrarUsuario(User user1) {
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
    
    public User autenticarUsuario(String correo, String contrasenia) {
        Document query = new Document("correo", correo).append("contrasenia", contrasenia);
        Document usuarioDoc = usuarioCollection.find(query).first();

        if (usuarioDoc != null) {
            // Crear un objeto User basado en el Document obtenido
            User usuario = documentoToUser(usuarioDoc);
            return usuario;
        } else {
            return null; // No se encontró un usuario con las credenciales proporcionadas
        }
    }

    // Método auxiliar para convertir un Document a un objeto User
    private User documentoToUser(Document usuarioDoc) {
    String nombre = usuarioDoc.getString("nombre");
    String direccion = usuarioDoc.getString("direccion");
    String telefono = usuarioDoc.getString("telefono");
    String correo = usuarioDoc.getString("correo");
    String contrasenia = usuarioDoc.getString("contrasenia");
    
  
    User usuario = new User(nombre, direccion, telefono, correo, contrasenia);
   

    return usuario;
}

    
    public User obtenerUsuarioPorId(String usuarioId) {
        try {
            // Crear el filtro para buscar el usuario por su _id
            Document filtro = new Document("_id", new ObjectId(usuarioId));

            // Realizar la consulta en la colección de usuarios
            Document usuarioDoc = usuarioCollection.find(filtro).first();

            // Verificar si se encontró el usuario
            if (usuarioDoc != null) {
                // Convertir el documento a un objeto User
                User usuario = documentoToUser(usuarioDoc);
                return usuario;
            } else {
                // El usuario no se encontró
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null; // Manejo básico de errores
        }
    }


}
