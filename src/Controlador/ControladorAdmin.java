package Controlador;

import Modelo.Admin;
import Modelo.AdminDAO;
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
        // Crear una nueva instancia de FrmRegistroAdmin y mostrarla
        viewRegistroAdmin.setVisible(true);
    }

    private void registrarAdmin() {
    try {
        // Verificar si ya hay algún administrador registrado
        if (adminDAO.existeAdminRegistrado()) {
            JOptionPane.showMessageDialog(viewRegistroAdmin, "Ya existe un administrador registrado. Solo se permite un administrador.", "Registro no permitido", JOptionPane.WARNING_MESSAGE);
        } else {
            String nombre = viewRegistroAdmin.txtNombreRegistro.getText();
            String direccion = viewRegistroAdmin.txtApellidoRegistro.getText(); // Ajusta según tus necesidades
            String telf = viewRegistroAdmin.txtCorreoRegistro.getText(); // Ajusta según tus necesidades
            String email = viewRegistroAdmin.txtPasRegistro.getText(); // Ajusta según tus necesidades

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

            if (adminDAO.autenticarAdmin(correo, contrasenia)) {
                // Si la autenticación es exitosa, puedes realizar las acciones correspondientes, como abrir otra ventana, etc.
                JOptionPane.showMessageDialog(viewLoginAdmin, "Inicio de sesión exitoso");
            } else {
                JOptionPane.showMessageDialog(viewLoginAdmin, "Credenciales incorrectas", "Error de inicio de sesión", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            System.out.println("Error al iniciar sesión");
            ex.printStackTrace();
        }
    }
}
