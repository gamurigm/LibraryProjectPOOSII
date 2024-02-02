package biblioteca.Model;

import com.google.gson.Gson;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class User {

    private String id;
    private String nombre;
    private String direccion;
    private String telf;
    private String email;
    private String password;
    private String creado;

    public User() {
        this.id = UUID.randomUUID().toString();
        this.creado = LocalDateTime.now().toString();
    }

    public User(String nombre, String direccion, String telf, String email, String password) {
        this.id = UUID.randomUUID().toString();
        this.nombre = nombre;
        this.direccion = direccion;
        this.telf = telf;
        this.email = email;
        this.password = password;
        this.creado = LocalDateTime.now().toString();
    }

    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getTelf() {
        return telf;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String fechaCreacion() {
        return creado;
    }

    public void setId(String id) {
        this.id = id;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public void setTelf(String telf) {
        this.telf = telf;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean iniciarSesion(String correo, String pass) {
        return autenticar(correo, pass);
    }

    protected boolean autenticar(String correo, String pass) {
        return this.email.equals(correo) && this.password.equals(pass);
    }

    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public static User fromJson(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, User.class);
    }

    private void saveToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter("user_data.csv", true))) {
            String csvData = this.toCsv();
            writer.println(csvData);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<User> loadFromCsv() {
        List<User> users = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("user_data.csv"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                User user = User.fromCsv(line);
                users.add(user);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return users;
    }

    public static User fromCsv(String csvLine) {
        String[] data = csvLine.split(",");
        User user = new User();
        user.setId(data[0]);
        user.setNombre(data[1]);
        user.setDireccion(data[2]);
        user.setTelf(data[3]);
        user.setEmail(data[4]);
        user.setPassword(data[5]);
        user.setCreado(data[6]);
        return user;
    }

    private String toCsv() {
        return String.join(",", this.id, this.nombre, this.direccion, this.telf, this.email, this.password, this.creado);
    }

    public void setCreado(String creado) {
        this.creado = creado;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", nombre='" + nombre + '\'' +
                ", direccion='" + direccion + '\'' +
                ", telf='" + telf + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", creado='" + creado + '\'' +
                '}';
    }
}
