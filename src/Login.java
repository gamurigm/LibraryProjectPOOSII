package biblioteca.Model;

import java.util.List;
import java.util.Scanner;

public class Login {

    public static User iniciarSesionAdmin() {
        Admin.inicializarDatos();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Iniciar sesion como Admin:");
        System.out.print("Correo: ");
        String correo = scanner.nextLine();
        System.out.print("Pass: ");
        String contraseña = scanner.nextLine();

        if (autenticarAdmin(correo, contraseña)) {
            System.out.println("login como Admin exitoso.");
            return Admin.getInstance();
        } else {
            System.out.println("Credenciales incorrectas. Login fallido.");
            return null;
        }
    }

    public static Usuario iniciarSesionUsuario() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Iniciar sesion como Usuario:");
        System.out.print("Correo: ");
        String correo = scanner.nextLine();
        System.out.print("Password: ");
        String contraseña = scanner.nextLine();

        List<Usuario> usuarios = UserFactory.loadUsersFromJson();
        Usuario usuario = UserFactory.findUserByEmail(usuarios, correo);

        if (usuario != null && autenticarUsuario(usuario, contraseña)) {
            System.out.println("Inicio de sesion como Usuario exitoso.");
            return usuario;
        } else {
            System.out.println("Credenciales incorrectas. Inicio de sesion fallido.");
            return null;
        }
    }

    private static boolean autenticarAdmin(String correo, String pass) {
        return Admin.getInstance().getEmail().equals(correo) && Admin.getInstance().getPassword().equals(pass);
    }

    private static boolean autenticarUsuario(Usuario usuario, String pass) {
        return usuario.getPassword().equals(pass);
    }
}
