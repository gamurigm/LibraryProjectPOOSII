package biblioteca.Model;

import java.util.ArrayList;
import java.util.List;

public class Historial {
    
    private List<Prestamo> prestamos;

    public Historial(User usuario, Libro libro) {
        this.prestamos = new ArrayList<>();
    }

  
    public List<Prestamo> getPrestamos() {
        return prestamos;
    }

    public void agregarPrestamo(Prestamo prestamo) {
        prestamos.add(prestamo);
    }

    public String toJson() {
        com.google.gson.Gson gson = new com.google.gson.Gson();
        return gson.toJson(this);
    }
}
