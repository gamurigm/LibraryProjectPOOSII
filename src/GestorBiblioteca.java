package biblioteca.Model;

public class GestorBiblioteca {

    public static void main(String[] args) {
        
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
        
        
        
