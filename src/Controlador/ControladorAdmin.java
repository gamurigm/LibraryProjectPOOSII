package Controlador;

import Modelo.Admin;
import Modelo.AdminDAO;
import Modelo.GestorBiblioteca;
import Vista.FrmAdminLogin;
import Vista.FrmRegistroAdmin;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
            String telf = viewRegistroAdmin.txtCorreoRegistro.getText();
            String email = viewRegistroAdmin.txtPasRegistro.getText();

            // Verificar si algún campo está vacío
            if (nombre.isEmpty() || direccion.isEmpty() || telf.isEmpty() || email.isEmpty()) {
                JOptionPane.showMessageDialog(viewRegistroAdmin, "Por favor, complete todos los campos.", "Campos Vacíos", JOptionPane.WARNING_MESSAGE);
                return; 
            }

            // Verificar si ya hay algún administrador registrado
            if (adminDAO.existeAdminRegistrado()) {
                JOptionPane.showMessageDialog(viewRegistroAdmin, "Ya existe un administrador registrado. Solo se permite un administrador.", "Registro no permitido", JOptionPane.WARNING_MESSAGE);
            } else {
                Admin nuevoAdmin = new Admin();
                nuevoAdmin.setNombre(nombre);
                nuevoAdmin.setDireccion(direccion);
                nuevoAdmin.setTelf(telf);
                nuevoAdmin.setEmail(email);

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
            String contrasenia = new String(viewLoginAdmin.txtPassAdmin.getPassword());

            if (correo.isEmpty() || contrasenia.isEmpty()) {
                JOptionPane.showMessageDialog(viewLoginAdmin, "Por favor, complete todos los campos.", "Campos Vacíos", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (adminDAO.autenticarAdmin(correo, contrasenia)) {
                JOptionPane.showMessageDialog(viewLoginAdmin, "Inicio de sesión exitoso");

                // Llamar al método iniciarSesionAdmin de GestorBiblioteca
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
