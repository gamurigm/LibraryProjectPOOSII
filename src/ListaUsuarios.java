package biblioteca.Model;

import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ListaUsuarios {

    private final List<Usuario> listaUsuarios;

    public ListaUsuarios() {
        this.listaUsuarios = new ArrayList<>();
    }

    public void agregarUsuario(Usuario usuario) {
        listaUsuarios.add(usuario);
    }

    public void mostrarContenido() {
        System.out.println("Contenido de la lista de usuarios: ");

        for (Usuario usuario : listaUsuarios) {
            System.out.print(usuario.toString());
        }
    }

    public void eliminarUsuario(Usuario usuario) {
        Iterator<Usuario> iterator = listaUsuarios.iterator();
        while (iterator.hasNext()) {
            Usuario usuarioActual = iterator.next();
            if (usuarioActual.equals(usuario)) {
                iterator.remove();
                System.out.println("Usuario eliminado:\n" + usuario.toString());
                return;
            }
        }
        System.out.println("El usuario no se encontró en la lista.");
    }

    public Usuario buscarPorEmail(String email) {
        for (Usuario usuario : listaUsuarios) {
            if (usuario.getEmail().equalsIgnoreCase(email)) {
                return usuario;
            }
        }
        return null;
    }

    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(listaUsuarios);
    }

    public static ListaUsuarios fromJson(String json) {
        Gson gson = new Gson();
        Usuario[] usuariosArray = gson.fromJson(json, Usuario[].class);

        ListaUsuarios listaUsuarios = new ListaUsuarios();
        for (Usuario usuario : usuariosArray) {
            listaUsuarios.agregarUsuario(usuario);
        }

        return listaUsuarios;
    }
}
