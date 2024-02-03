package Model;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class GestorBiblioteca {

    public static void main(String[] args) {

        Conexion conexion = new Conexion();
         
        MongoDatabase baseDatos = conexion.getBaseDatos();
        MongoCollection<Document> coleccion = conexion.getColeccion();

        Document documentoPrueba = new Document("nombre", "Libro de Prueba")
                .append("autor", "Autor de Prueba")
                .append("anio", 2022);
           coleccion.insertOne(documentoPrueba);
            System.out.println("Documento insertado con éxito.");
        
      
   
//        ListaLibros listaLibros = inicializarListaLibros();
//        MenuLogin.mainMenu(listaLibros);
        
    }
    
    
//    private static ListaLibros inicializarListaLibros() {
      

//        ListaLibros listaLibros = new ListaLibros();
//
//        Libro libro1 = new Libro("Cien años de soledad", "Gabriel Garcia Marquez", "Ficcion");
//        Libro libro2 = new Libro("1984", "George Orwell", "");
//        listaLibros.agregarLibro(libro1);
//        listaLibros.agregarLibro(libro2);
//
//        return listaLibros;
//  
//    }
}
        
        
        
