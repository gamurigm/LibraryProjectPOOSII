package biblioteca.Model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UserFactory {

    private static final String USERS_JSON_FILE = "users.json";

    public static Usuario createUser(Scanner scanner) {
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();

        System.out.print("Direccion: ");
        String direccion = scanner.nextLine();

        System.out.print("Telefono: ");
        String telf = scanner.nextLine();

        System.out.print("Correo electronico: ");
        String email = scanner.nextLine();

        System.out.print("Password: ");
        String password = scanner.nextLine();

        Usuario nuevoUsuario = new Usuario(nombre, direccion, telf, email, password);
        saveUserToJson(nuevoUsuario);

        return nuevoUsuario;
    }

    public static void saveUsersToJson(List<Usuario> users) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        try (FileWriter writer = new FileWriter(USERS_JSON_FILE)) {
            gson.toJson(users, writer);
            System.out.println("Usuarios guardados en " + USERS_JSON_FILE);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Usuario> loadUsersFromJson() {
        List<Usuario> usuarios = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(USERS_JSON_FILE))) {
            Gson gson = new Gson();
            StringBuilder jsonContent = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                jsonContent.append(line);
            }

            usuarios = gson.fromJson(jsonContent.toString(), new TypeToken<List<Usuario>>() {}.getType());
        } catch (IOException e) {
            e.printStackTrace();
    }

        return usuarios != null ? usuarios : new ArrayList<>();
        }

    public static Usuario findUserByEmail(List<Usuario> usuarios, String email) {
        for (Usuario usuario : usuarios) {
            if (usuario.getEmail().equals(email)) {
                return usuario;
            }
        }
        return null;
    }

    public static void saveUserToJson(Usuario user) {
        List<Usuario> users = loadUsersFromJson();
        users.add(user);

        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        try (FileWriter writer = new FileWriter(USERS_JSON_FILE)) {
            gson.toJson(users, writer);
            System.out.println("Usuario guardado en " + USERS_JSON_FILE);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
