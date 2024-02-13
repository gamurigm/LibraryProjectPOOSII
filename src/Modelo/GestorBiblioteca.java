package Modelo;

import Controlador.ControladorAdmin;
import Controlador.ControladorLibros;
import Controlador.ControladorUsuario;
import Vista.FrmAdminLogin;
import Vista.FrmAeLibros;
import Vista.FrmRegistroAdmin;
import Vista.FrmBienvenida;
import Vista.FrmUsuario;
import Vista.FrmUsuarioLogin;
import com.mongodb.client.MongoCollection;
import org.bson.Document;

import javax.swing.*;

public class GestorBiblioteca {

    private static Conexion conexion;
    public static FrmBienvenida frmBienvenida;
    public static FrmUsuarioLogin frmUsuarioLogin;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
            
            conexion = new Conexion();

            MongoCollection<Document> adminCollection = conexion.getBaseDatos().getCollection("Admin");
            AdminDAO adminDAO = new AdminDAO(adminCollection);

            MongoCollection<Document> usuarioCollection = conexion.getBaseDatos().getCollection("Usuarios");
            UsuarioDAO usuarioDAO = new UsuarioDAO(usuarioCollection);

            frmBienvenida = new FrmBienvenida();

            FrmAdminLogin frmAdminLogin = new FrmAdminLogin();
            FrmRegistroAdmin frmRegistroAdmin = new FrmRegistroAdmin();

            FrmUsuarioLogin frmUsuarioLogin = new FrmUsuarioLogin();
            FrmUsuario frmUsuario = new FrmUsuario();

            ControladorAdmin controladorAdmin = new ControladorAdmin(frmAdminLogin, frmRegistroAdmin, adminDAO);
            ControladorUsuario controladorUsuario = new ControladorUsuario(frmUsuarioLogin, frmUsuario, usuarioDAO);

            // Lógica para el administrador
            frmBienvenida.btnInitAdmin.addActionListener(e -> {
                frmAdminLogin.setVisible(true);
                frmBienvenida.setVisible(false);
            });

            frmAdminLogin.btnRegistrarse.addActionListener(e -> {
                frmRegistroAdmin.setVisible(true);
                frmAdminLogin.setVisible(false);
            });

            // Lógica para el usuario
            frmBienvenida.btnInitUsuario.addActionListener(e -> {
                frmUsuarioLogin.setVisible(true);
                frmBienvenida.setVisible(false);
            });

            frmUsuarioLogin.btnRegistrarse.addActionListener(e -> {
                frmUsuario.setVisible(true);
                frmUsuarioLogin.setVisible(false);
            });

            frmBienvenida.setVisible(true);

        } catch (Exception e) {
            e.printStackTrace();
        }   
    });
    }

    public static void iniciarSesionAdmin(String correo, String contrasenia) {
        LibroDAO libroDAO = new LibroDAO(conexion.getColeccion("Libros"));
        FrmAeLibros frmAeLibros = new FrmAeLibros();
        ControladorLibros controladorLibros = new ControladorLibros(frmAeLibros, libroDAO);
        frmAeLibros.setVisible(true);
    }
    
     public static void iniciarSesionUsuario(String correo, String contrasenia) {
        UsuarioDAO usuarioDAO = new UsuarioDAO(conexion.getColeccion("Usuarios"));
        if (usuarioDAO.autenticarUsuario(correo, contrasenia)) {
            frmBienvenida.setVisible(false);
        } else {
            JOptionPane.showMessageDialog(null, "Credenciales incorrectas", "Error de inicio de sesión", JOptionPane.ERROR_MESSAGE);
        }
    }


}
