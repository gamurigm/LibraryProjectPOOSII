package Modelo;

import Modelo.AdminDAO;
import Controlador.ControladorAdmin;
import Modelo.Conexion;
import Vista.FrmAdminLogin;
import Vista.FrmRegistroAdmin;
import com.mongodb.client.MongoCollection;
import org.bson.Document;

import javax.swing.*;

public class GestorBiblioteca {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                // Crea la conexión a la base de datos MongoDB
                Conexion conexion = new Conexion();
                MongoCollection<Document> adminCollection = conexion.getBaseDatos().getCollection("Admin");

                // Crea el DAO y las vistas
                AdminDAO adminDAO = new AdminDAO(adminCollection);
                FrmAdminLogin frmAdminLogin = new FrmAdminLogin();
                FrmRegistroAdmin frmRegistroAdmin = new FrmRegistroAdmin();

                // Crea el controlador
                ControladorAdmin controladorAdmin = new ControladorAdmin(frmAdminLogin, frmRegistroAdmin, adminDAO);

                // Muestra la vista de inicio de sesión
                frmAdminLogin.setVisible(true);

            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
