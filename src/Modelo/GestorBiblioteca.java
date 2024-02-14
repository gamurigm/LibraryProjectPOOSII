package Modelo;

import Controlador.ControladorAdmin;
import Controlador.ControladorLibros;
import Controlador.ControladorUsuario;
import Vista.FrmAdminLogin;
import Vista.FrmAeLibros;
import Vista.FrmRegistroAdmin;
import Vista.FrmBienvenida;
import Vista.FrmBusquedaLibro;
import Vista.FrmUsuario;
import Vista.FrmUsuarioLogin;
import com.mongodb.client.MongoCollection;
import org.bson.Document;

import javax.swing.*;

public class GestorBiblioteca {

    private static Conexion conexion;
    public static FrmBienvenida frmBienvenida;
    public static FrmUsuarioLogin frmUsuarioLogin;
    public static FrmAdminLogin frmAdminLogin;
    public static FrmRegistroAdmin frmRegistroAdmin;
    public static FrmUsuario frmUsuario;
    public static FrmBusquedaLibro frmBusquedaLibro;
    public static LibroDAO libroDAO; // Una única instancia de LibroDAO

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                inicializarApp();
            } catch (Exception e) {
                mostrarError("Error al iniciar la aplicación");
                e.printStackTrace();
            }
        });
    }

    private static void inicializarApp() {
        conexion = new Conexion();
        MongoCollection<Document> adminCollection = conexion.getBaseDatos().getCollection("Admin");
        AdminDAO adminDAO = new AdminDAO(adminCollection);

        MongoCollection<Document> usuarioCollection = conexion.getBaseDatos().getCollection("Usuarios");
        UsuarioDAO usuarioDAO = new UsuarioDAO(usuarioCollection);

        frmBienvenida = new FrmBienvenida();
        frmAdminLogin = new FrmAdminLogin();
        frmRegistroAdmin = new FrmRegistroAdmin();
        frmUsuarioLogin = new FrmUsuarioLogin();
        frmUsuario = new FrmUsuario();
        frmBusquedaLibro = new FrmBusquedaLibro();

        libroDAO = new LibroDAO(conexion.getColeccion("Libros")); // Instancia única de LibroDAO

        ControladorAdmin controladorAdmin = new ControladorAdmin(frmAdminLogin, frmRegistroAdmin, adminDAO);
        ControladorUsuario controladorUsuario = new ControladorUsuario(frmUsuarioLogin, frmUsuario, frmBusquedaLibro, usuarioDAO, libroDAO);

        // Lógica para el administrador
        frmBienvenida.btnInitAdmin.addActionListener(e -> mostrarVistaAdmin());

        frmAdminLogin.btnRegistrarse.addActionListener(e -> mostrarVistaRegistroAdmin());

        // Lógica para el usuario
        frmBienvenida.btnInitUsuario.addActionListener(e -> mostrarVistaUsuario());

        frmUsuarioLogin.btnRegistrarse.addActionListener(e -> mostrarVistaRegistroUsuario());

        frmBienvenida.setVisible(true);
    }

    private static void mostrarVistaAdmin() {
        frmAdminLogin.setVisible(true);
        frmBienvenida.setVisible(false);
    }

    private static void mostrarVistaRegistroAdmin() {
        frmRegistroAdmin.setVisible(true);
        frmAdminLogin.setVisible(false);
    }

    private static void mostrarVistaUsuario() {
        frmUsuarioLogin.setVisible(true);
        frmBienvenida.setVisible(false);
    }

    private static void mostrarVistaRegistroUsuario() {
        frmUsuario.setVisible(true);
        frmUsuarioLogin.setVisible(false);
    }

    private static void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(null, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public static void iniciarSesionAdmin(String correo, String contrasenia) {
        FrmAeLibros frmAeLibros = new FrmAeLibros();
        ControladorLibros controladorLibros = new ControladorLibros(frmAeLibros, libroDAO);
        frmAeLibros.setVisible(true);
    }

    public static void iniciarSesionUsuario(String correo, String contrasenia) {
        try {
            UsuarioDAO usuarioDAO = new UsuarioDAO(conexion.getColeccion("Usuarios"));

            if (usuarioDAO.autenticarUsuario(correo, contrasenia)) {
                // Mostrar la ventana de búsqueda de libros
                FrmBusquedaLibro frmBusquedaLibro = new FrmBusquedaLibro();
                frmBusquedaLibro.setVisible(true);

                // Cerrar la ventana de bienvenida
                frmBienvenida.setVisible(false);
            } else {
                mostrarError("Credenciales incorrectas");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
