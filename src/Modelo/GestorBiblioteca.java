package Modelo;

import com.mongodb.client.MongoCollection;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class GestorBiblioteca {

    public static void main(String[] args) {
        Conexion conexion = new Conexion();

        MongoCollection<Document> coleccionLibros = conexion.getBaseDatos().getCollection("Libros");

        // Crear una ListaLibros vacía
        List<Libro> listaLibros = new ArrayList<>();
        ListaLibros biblioteca = new ListaLibros(coleccionLibros, listaLibros);

        // Cargar libros desde archivo al inicio si es necesario
        biblioteca.cargarLibrosDesdeArchivo();

        // Crear un administrador
        Admin admin = Admin.getInstance();
        // Inicializar datos del administrador si aún no se ha hecho
        Admin.inicializarDatos();

    

        // Guardar los cambios en el archivo y cerrar la conexión
        biblioteca.guardarLibrosEnArchivo();
        conexion.cerrarConexion();
    }
}
