package Modelo;

import Controlador.ControladorAdmin;
import Vista.FrmAdminLogin;
import Vista.FrmRegistroAdmin;
import Vista.FrmBienvenida;
import com.mongodb.client.MongoCollection;
import org.bson.Document;

import javax.swing.*;

public class GestorBiblioteca {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                
                Conexion conexion = new Conexion();
                MongoCollection<Document> adminCollection = conexion.getBaseDatos().getCollection("Admin");

                // Crea el DAO y las vistas
                AdminDAO adminDAO = new AdminDAO(adminCollection);
                FrmBienvenida frmBienvenida = new FrmBienvenida();
                FrmAdminLogin frmAdminLogin = new FrmAdminLogin();
                FrmRegistroAdmin frmRegistroAdmin = new FrmRegistroAdmin();

                // Crea el controlador
                ControladorAdmin controladorAdmin = new ControladorAdmin(frmAdminLogin, frmRegistroAdmin, adminDAO);

                // Agrega un ActionListener al botón "Iniciar" en FrmBienvenida
                frmBienvenida.btnInitAdmin.addActionListener(e -> {
                    // Muestra la vista de inicio de sesión al hacer clic en el botón "Iniciar"
                    frmAdminLogin.setVisible(true);
                    // También puedes ocultar la ventana actual si es necesario
                    frmBienvenida.setVisible(false);
                });

                // Agrega un ActionListener al botón "Registrarse" en FrmAdminLogin
                frmAdminLogin.btnRegistrarse.addActionListener(e -> {
                    // Abre la ventana FrmRegistroAdmin al hacer clic en el botón "Registrarse"
                    frmRegistroAdmin.setVisible(true);
                    // También puedes ocultar la ventana actual si es necesario
                    frmAdminLogin.setVisible(false);
                });

                // Muestra la vista de bienvenida
                frmBienvenida.setVisible(true);

            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
