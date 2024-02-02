package biblioteca.Model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;

public class LibroFactory {

    private static final String LIBROS_JSON_FILE = "libros.json";

    public static void saveLibroToJson(Libro libro) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        try (FileWriter writer = new FileWriter(LIBROS_JSON_FILE, true)) {
            // Si el archivo está vacío, escribe el corchete de apertura
            if (Files.size(Paths.get(LIBROS_JSON_FILE)) == 0) {
                writer.write("[");
            } else {
                // Si no está vacío, añade una coma para separar los objetos
                writer.write(",");
            }

            gson.toJson(libro, writer);
            writer.write(System.lineSeparator());  // Utiliza System.lineSeparator() para añadir un salto de línea
            System.out.println("Libro guardado en " + LIBROS_JSON_FILE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ListaLibros loadLibrosFromJson() {
        Gson gson = new Gson();

        try {
            String json = new String(Files.readAllBytes(Paths.get(LIBROS_JSON_FILE)));
            Libro[] librosArray = gson.fromJson(json, Libro[].class);

            ListaLibros listaLibros = new ListaLibros();
            for (Libro libro : librosArray) {
                listaLibros.agregarLibro(libro);
            }

            return listaLibros;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
