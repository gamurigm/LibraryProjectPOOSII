package Controlador;

import Modelo.GestorBiblioteca;
import Modelo.Usuario;
import Modelo.UsuarioDAO;
import Vista.FrmUsuario;
import Vista.FrmUsuarioLogin;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

public class ControladorUsuario implements ActionListener {

    private final FrmUsuarioLogin viewLoginUsuario;
    private final FrmUsuario viewUsuario;
    private final UsuarioDAO usuarioDAO;

    public ControladorUsuario(FrmUsuarioLogin viewLoginUsuario, FrmUsuario viewUsuario, UsuarioDAO usuarioDAO) {
        this.viewLoginUsuario = viewLoginUsuario;
        this.usuarioDAO = usuarioDAO;
        this.viewUsuario = viewUsuario;

        viewLoginUsuario.btnInicioUser.addActionListener(this);
        viewLoginUsuario.btnRegistrarse.addActionListener(this);
        viewUsuario.btnAceptar.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == viewUsuario.btnAceptar) {
            registrarUsuario();
        } else if (e.getSource().equals(viewLoginUsuario.btnInicioUser)) {
            iniciarSesionUsuario();
        } else if (e.getSource().equals(viewLoginUsuario.btnRegistrarse)) {
            abrirFrmUsuario();
        }
    }

    public void registrarUsuario() {
    try {
        
        String nombre = viewUsuario.txtNombre.getText();
        String direccion = viewUsuario.txtDireccion.getText();
        String telefono = viewUsuario.txtTelef.getText();
        String email = viewUsuario.txtEmail.getText();  
        String contrasenia = viewUsuario.txtPassword.getText();

        if (nombre.isEmpty() || direccion.isEmpty() || telefono.isEmpty() || email.isEmpty() || contrasenia.isEmpty()) {
            JOptionPane.showMessageDialog(viewUsuario, "Por favor, complete todos los campos.", "Campos Vacíos", JOptionPane.WARNING_MESSAGE);
        } else {
     
            Usuario nuevoUsuario = new Usuario();
            nuevoUsuario.setNombre(nombre);
            nuevoUsuario.setDireccion(direccion);
            nuevoUsuario.setTelf(telefono);
            nuevoUsuario.setEmail(email);
            nuevoUsuario.setPassword(contrasenia);

            usuarioDAO.registrarUsuario(nuevoUsuario);

            JOptionPane.showMessageDialog(viewUsuario, "Registro exitoso");
        }
    } catch (Exception ex) {
        System.out.println("Error de registro");
        ex.printStackTrace();
    }
}


    private void abrirFrmUsuario() {
        viewUsuario.setVisible(true);
    }

    public void iniciarSesionUsuario() {
        try {
            String correoUsuario = viewLoginUsuario.txtCorreoLogin.getText();

            if (usuarioDAO.existeUsuario(correoUsuario)) {
                String contraseniaUsuario = viewLoginUsuario.txtContraLogin.getText();

                if (usuarioDAO.autenticarUsuario(correoUsuario, contraseniaUsuario)) {
                    JOptionPane.showMessageDialog(viewLoginUsuario, "Inicio de sesión exitoso");
                    GestorBiblioteca.iniciarSesionUsuario(correoUsuario, contraseniaUsuario);
                    viewLoginUsuario.setVisible(false);
                } else {
                    JOptionPane.showMessageDialog(viewLoginUsuario, "Credenciales incorrectas", "Error de inicio de sesión", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(viewLoginUsuario, "El usuario no existe", "Error de inicio de sesión", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            System.out.println("Error al iniciar sesión");
            ex.printStackTrace();
        }
    }
}
