package Controlador;

import Modelo.Admin;
import Modelo.AdminDAO;
import Modelo.GestorBiblioteca;
import Vista.FrmAdminLogin;
import Vista.FrmRegistroAdmin;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import javax.swing.JOptionPane;

public class ControladorAdmin implements ActionListener {

    private final FrmAdminLogin viewLoginAdmin;
    private final FrmRegistroAdmin viewRegistroAdmin;
    private final AdminDAO adminDAO;

    public ControladorAdmin(FrmAdminLogin viewLoginAdmin, FrmRegistroAdmin viewRegistroAdmin, AdminDAO adminDAO) {
        this.viewLoginAdmin = viewLoginAdmin;
        this.viewRegistroAdmin = viewRegistroAdmin;
        this.adminDAO = adminDAO;

        viewLoginAdmin.btnInicioAdmin.addActionListener(this);
        viewLoginAdmin.btnRegistrarse.addActionListener(this);
        viewRegistroAdmin.btnNuevoAdmin.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(viewRegistroAdmin.btnNuevoAdmin)) {
            registrarAdmin();
        } else if (e.getSource().equals(viewLoginAdmin.btnInicioAdmin)) {
            iniciarSesionAdmin();
        } else if (e.getSource().equals(viewLoginAdmin.btnRegistrarse)) {
            abrirFrmRegistroAdmin();
        }
    }

    private void abrirFrmRegistroAdmin() {
        viewRegistroAdmin.setVisible(true);
    }

    private void registrarAdmin() {
    try {
        String nombre = viewRegistroAdmin.txtNombreRegistro.getText();
        String direccion = viewRegistroAdmin.txtDireccion.getText();
        String telf = viewRegistroAdmin.txtTelf.getText();
        String correo = viewRegistroAdmin.txtCorreoRegistro.getText(); // Cambiado a correo
        String contrasenia = viewRegistroAdmin.txtPasRegistro.getText(); // Contraseña desde JTextField

        if (nombre.isEmpty() || direccion.isEmpty() || telf.isEmpty() || correo.isEmpty() || contrasenia.isEmpty()) {
            JOptionPane.showMessageDialog(viewRegistroAdmin, "Por favor, complete todos los campos.", "Campos Vacíos", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (adminDAO.existeAdminRegistrado()) {
            JOptionPane.showMessageDialog(viewRegistroAdmin, "Ya existe un administrador registrado. Solo se permite un administrador.", "Registro no permitido", JOptionPane.WARNING_MESSAGE);
        } else {
            Admin nuevoAdmin = new Admin();
            nuevoAdmin.setNombre(nombre);
            nuevoAdmin.setDireccion(direccion);
            nuevoAdmin.setTelf(telf);
            nuevoAdmin.setEmail(correo); // Se establece el correo correctamente
            nuevoAdmin.setPassword(contrasenia);

            adminDAO.registrarAdmin(nuevoAdmin);

            JOptionPane.showMessageDialog(viewRegistroAdmin, "Registro exitoso");
        }
    } catch (Exception ex) {
        System.out.println("Error de registro");
        ex.printStackTrace();
    }
}


    private void iniciarSesionAdmin() {
    try {
        String correo = viewLoginAdmin.txtCorreoAdmin.getText();
        String contraseniaChars = viewLoginAdmin.txtPassAdmin.getText();
        String contrasenia = new String(contraseniaChars);

        if (correo.isEmpty() || contrasenia.isEmpty()) {
            JOptionPane.showMessageDialog(viewLoginAdmin, "Por favor, complete todos los campos.", "Campos Vacíos", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Ahora utilizamos la contraseña obtenida desde el JPasswordField
        if (adminDAO.autenticarAdmin(correo, contrasenia)) {
            JOptionPane.showMessageDialog(viewLoginAdmin, "Inicio de sesión exitoso");
            GestorBiblioteca.iniciarSesionAdmin(correo, contrasenia);
            viewLoginAdmin.setVisible(false);

     
        } else {
            JOptionPane.showMessageDialog(viewLoginAdmin, "Credenciales incorrectas", "Error de inicio de sesión", JOptionPane.ERROR_MESSAGE);
        }
    } catch (Exception ex) {
        System.out.println("Error al iniciar sesión");
    }
}

}
