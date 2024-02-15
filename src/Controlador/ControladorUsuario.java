package Controlador;

import Modelo.GestorBiblioteca;
import Modelo.Libro;
import Modelo.LibroDAO;
import Modelo.Usuario;
import Modelo.UsuarioDAO;
import Vista.FrmBusquedaLibro;
import Vista.FrmUsuario;
import Vista.FrmUsuarioLogin;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JOptionPane;


public class ControladorUsuario implements ActionListener {

    private final FrmUsuarioLogin viewLoginUsuario;
    private final FrmUsuario viewUsuario;
    private final FrmBusquedaLibro viewBusqueda;
    private final UsuarioDAO usuarioDAO;
    private final LibroDAO libroDAO;
    

    public ControladorUsuario(FrmUsuarioLogin viewLoginUsuario, FrmUsuario viewUsuario, FrmBusquedaLibro viewBusqueda, UsuarioDAO usuarioDAO, LibroDAO libroDAO) {
        this.viewLoginUsuario = viewLoginUsuario;
        this.viewUsuario = viewUsuario;
        this.viewBusqueda = viewBusqueda;
        this.usuarioDAO = usuarioDAO;
        this.libroDAO = libroDAO; 
       
        
        viewLoginUsuario.btnInicioUser.addActionListener(this);
        viewLoginUsuario.btnRegistrarse.addActionListener(this);
        viewUsuario.btnAceptar.addActionListener(this);
        viewBusqueda.btnBuscar.addActionListener(this);
        viewBusqueda.btnReservar.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == viewUsuario.btnAceptar) {
            registrarUsuario();
        } else if (e.getSource().equals(viewLoginUsuario.btnInicioUser)) {
            iniciarSesionUsuario();
        } else if (e.getSource().equals(viewLoginUsuario.btnRegistrarse)) {
            abrirFrmUsuario();
        } else if (e.getSource() == viewBusqueda.btnBuscar) {
        realizarBusqueda();
        }
    }

    private void realizarBusqueda() {
        String nombre = viewBusqueda.txtNombre.getText().trim();
        String autor = viewBusqueda.txtAutor.getText().trim();
        String genero = viewBusqueda.txtGenero.getText().trim();
        String codigo = viewBusqueda.txtCodigo.getText().trim();

        if (nombre.isEmpty() && autor.isEmpty() && genero.isEmpty() && codigo.isEmpty()) {
            JOptionPane.showMessageDialog(viewBusqueda, "Por favor, ingrese al menos un criterio de búsqueda.", "Campos Vacíos", JOptionPane.WARNING_MESSAGE);
        } else {
            List<Libro> resultados = libroDAO.buscarLibros(nombre, autor, genero, codigo);
            libroDAO.mostrarResultadosEnTabla(resultados, viewBusqueda.tablaMostrar);
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
        String contraseniaUsuario = viewLoginUsuario.txtContraLogin.getText();

        if (usuarioDAO.existeUsuario(correoUsuario)) {
            JOptionPane.showMessageDialog(viewLoginUsuario, "Inicio de sesión exitoso");
            
            GestorBiblioteca.iniciarSesionUsuario(correoUsuario, contraseniaUsuario);
            viewLoginUsuario.setVisible(false);
            viewBusqueda.setVisible(true);

        } else {
            JOptionPane.showMessageDialog(viewLoginUsuario, "Credenciales incorrectas", "Error de inicio de sesión", JOptionPane.ERROR_MESSAGE);
        }
    } catch (Exception ex) {
        System.out.println("Error al iniciar sesión");
        ex.printStackTrace();
        }
    }
}
