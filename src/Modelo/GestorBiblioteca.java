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
    private static MongoCollection<Document> historialCollection; 
    public static FrmBienvenida frmBienvenida;
    public static FrmUsuarioLogin frmUsuarioLogin;
    public static FrmAdminLogin frmAdminLogin;
    public static FrmRegistroAdmin frmRegistroAdmin;
    public static FrmUsuario frmUsuario;
    public static FrmBusquedaLibro frmBusquedaLibro;
    public static LibroDAO libroDAO; // Una única instancia de LibroDAO
    public static Historial historial; 
    public static User usuario; 
    private static UsuarioDAO usuarioDAO;

    
    
    
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
    
    
    public static User getUsuarioActual() {
        return usuario;
    }

    public static void setUsuarioActual(User usuario) {
        GestorBiblioteca.usuario = usuario;
    }

    
    public static void iniciarSesionAdmin(String correo, String contrasenia) {
        FrmAeLibros frmAeLibros = new FrmAeLibros();
        ControladorLibros controladorLibros = new ControladorLibros(frmAeLibros, libroDAO);
        frmAeLibros.setVisible(true);
    }

  private static void inicializarApp() {
    conexion = new Conexion();

    MongoCollection<Document> adminCollection = conexion.getBaseDatos().getCollection("Admin");
    AdminDAO adminDAO = new AdminDAO(adminCollection);

    MongoCollection<Document> usuarioCollection = conexion.getBaseDatos().getCollection("Usuarios");
    usuarioDAO = new UsuarioDAO(usuarioCollection);

    historialCollection = conexion.getBaseDatos().getCollection("Historial");
    System.out.println("Historial Collection: " + historialCollection);

    historial = Historial.getInstance();
    historial.setUsuarioDAO(usuarioDAO);
    historial.setHistorialCollection(historialCollection);

    frmBienvenida = new FrmBienvenida();
    frmAdminLogin = new FrmAdminLogin();
    frmRegistroAdmin = new FrmRegistroAdmin();
    frmUsuarioLogin = new FrmUsuarioLogin();
    frmUsuario = new FrmUsuario();
    frmBusquedaLibro = new FrmBusquedaLibro();

    libroDAO = new LibroDAO(Conexion.getColeccion("Libros")); // Instancia única de LibroDAO

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

    public static void iniciarSesionUsuario(String correo, String contrasenia) {
    try {
        System.out.println("Valor de usuarioDAO antes de la verificación: " + usuarioDAO);

        if (usuarioDAO != null && !correo.isEmpty() && !contrasenia.isEmpty()) {
            User usuarioAutenticado = usuarioDAO.autenticarUsuario(correo, contrasenia);

            if (usuarioAutenticado != null) {
                // Autenticación exitosa, establece el usuario actual
                setUsuarioActual(usuarioAutenticado);

                frmBusquedaLibro.setVisible(true);
                frmBienvenida.setVisible(false);
            } else {
                mostrarError("Credenciales incorrectas");
            }
        } else {
            mostrarError("Ingrese correo y contraseña");
        }
    } catch (Exception e) {
        mostrarError("Error al iniciar sesión");
        e.printStackTrace();
    }
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

    


 
}
