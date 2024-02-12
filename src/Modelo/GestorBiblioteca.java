package Modelo;

import Controlador.ControladorAdmin;
import Controlador.ControladorLibros;
import Vista.FrmAdminLogin;
import Vista.FrmAeLibros;
import Vista.FrmRegistroAdmin;
import Vista.FrmBienvenida;
import com.mongodb.client.MongoCollection;
import org.bson.Document;

import javax.swing.*;

public class GestorBiblioteca {

    private static Conexion conexion;
    public static FrmBienvenida frmBienvenida;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                conexion = new Conexion();

                MongoCollection<Document> adminCollection = conexion.getBaseDatos().getCollection("Admin");
                AdminDAO adminDAO = new AdminDAO(adminCollection);
                frmBienvenida = new FrmBienvenida();

                FrmAdminLogin frmAdminLogin = new FrmAdminLogin();
                FrmRegistroAdmin frmRegistroAdmin = new FrmRegistroAdmin();

                ControladorAdmin controladorAdmin = new ControladorAdmin(frmAdminLogin, frmRegistroAdmin, adminDAO);

                frmBienvenida.btnInitAdmin.addActionListener(e -> {
                    frmAdminLogin.setVisible(true);
                    frmBienvenida.setVisible(false);
                });

                frmAdminLogin.btnRegistrarse.addActionListener(e -> {
                    frmRegistroAdmin.setVisible(true);
                    frmAdminLogin.setVisible(false);
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
}
