package Modelo;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import java.util.ArrayList;
import org.bson.Document;

public class GestorBiblioteca {

    public static void main(String[] args) {
        Conexion conexion = new Conexion();

        MongoDatabase baseDatos = conexion.getBaseDatos();
        MongoCollection<Document> coleccionLibros = baseDatos.getCollection("Libros");

        ListaLibros listaLibros = new ListaLibros(coleccionLibros, new ArrayList<>());

        // Cargar libros desde archivo al inicio si es necesario
        listaLibros.cargarLibrosDesdeArchivo();

        // Ejemplo: Agregar un nuevo libro
        Libro nuevoLibro = new Libro("Nuevo Libro", "Autor Desconocido", "Fantasía");
        nuevoLibro.setStock(10);
        listaLibros.agregarLibro(nuevoLibro);

     
      

        // Ejemplo: Buscar libros por género
        ListaLibros librosFantasia = listaLibros.buscarPorGenero("Fantasía");
//        librosFantasia.mostrarContenido();
//
//        // Ejemplo: Actualizar un libro
//        Libro libroActualizado = listaLibros.buscarPorTitulo("Nuevo Libro");
//        if (libroActualizado != null) {
//            libroActualizado.setStock(15);
//            listaLibros.actualizarLibro(nuevoLibro, libroActualizado);
//        }
//
//        // Mostrar el contenido actualizado
//        listaLibros.mostrarContenido();
//
//        // Ejemplo: Eliminar un libro
//        listaLibros.eliminarLibro(nuevoLibro);
//
//        // Mostrar el contenido después de la eliminación
//        listaLibros.mostrarContenido();

        // Guardar los cambios en el archivo y cerrar la conexión
        listaLibros.guardarLibrosEnArchivo();
        conexion.cerrarConexion();
    }
}
