package biblioteca.Model;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Libro {

    private static int nextId = 1;
    private final int id;
    private String titulo;
    private String autor;
    private String genero;
    private boolean disponible;
    private int stock;

    public Libro(String titulo, String autor, String genero) {
        this.id = nextId++;
        this.titulo = titulo;
        this.autor = autor;
        this.genero = genero;
        this.disponible = true;
    }

    public int getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getAutor() {
        return autor;
    }

    public String getGenero() {
        return genero;
    }

    public boolean estaDisponible() {
        return disponible;
    }

    public int getStock() {
        return stock;
    }
    
    
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
    
    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public static Libro fromJson(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, Libro.class);
    }

    private void saveToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter("libro_data.csv", true))) {
            String csvData = this.toCsv();
            writer.println(csvData);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Libro> loadFromCsv() {
        List<Libro> libros = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("libro_data.csv"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Libro libro = Libro.fromCsv(line);
                libros.add(libro);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return libros;
    }

    public static Libro fromCsv(String csvLine) {
        String[] data = csvLine.split(",");
        Libro libro = new Libro(data[0], data[1], data[2]);
        libro.setDisponible(Boolean.parseBoolean(data[3]));
        libro.setStock(Integer.parseInt(data[4]));
        return libro;
    }

    private String toCsv() {
        return String.join(",", this.titulo, this.autor, this.genero, String.valueOf(this.disponible), String.valueOf(this.stock));
    }

    public static Libro register(String titulo, String autor, String genero) {
        Libro nuevoLibro = new Libro(titulo, autor, genero);
        nuevoLibro.saveToFile();
        return nuevoLibro;
    }

    @Override
    public String toString() {
        return """
               Libro {
               \tid: """ + id +
                "\n\ttitulo: " + titulo +
                "\n\tautor: " + autor +
                "\n\tgenero: " + genero +
                "\n\tdisponible: " + disponible +
                "\n\tstock: " + stock +
                "\n}\n";
    }
}
