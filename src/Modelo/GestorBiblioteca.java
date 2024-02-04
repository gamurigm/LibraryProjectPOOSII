package Modelo;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import java.time.LocalDate;
import org.bson.Document;

public class GestorBiblioteca {

    public static void main(String[] args) {

        Conexion conexion = new Conexion();
         
        MongoDatabase baseDatos = conexion.getBaseDatos();
        MongoCollection<Document> coleccion = conexion.getColeccionPrueba();

        Document documentoPrueba = new Document("nombre", "Libro de Prueba")
                .append("autor", "Autor de Prueba")
                .append("anio", 2022);
           coleccion.insertOne(documentoPrueba);
            System.out.println("Documento insertado.");
        // En algún lugar de tu código...

MongoCollection<Document> historialCollection = conexion.getColeccionHistorial();
User usuario = new Usuario("Nombre", "Dirección", "Teléfono", "Correo", "Contraseña");
Historial historial = new Historial(usuario, historialCollection);
        Libro libro = null;

// Agregar un préstamo
historial.agregarPrestamo(libro, LocalDate.now(), LocalDate.now().plusDays(14));


      
   
        ListaLibros listaLibros = inicializarListaLibros();
        MenuLogin.mainMenu(listaLibros);
        
    }
    
    
    private static ListaLibros inicializarListaLibros() {
      

        ListaLibros listaLibros = new ListaLibros();

        Libro libro1 = new Libro("Cien años de soledad", "Gabriel Garcia Marquez", "Ficcion");
        Libro libro2 = new Libro("1984", "George Orwell", "");
        listaLibros.agregarLibro(libro1);
        listaLibros.agregarLibro(libro2);

        return listaLibros;
  
    }
}
        
        
        
