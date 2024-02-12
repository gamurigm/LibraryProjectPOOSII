package Controlador;

import Modelo.Conexion;
import Modelo.Libro;
import Modelo.LibroDAO;
import Vista.FrmAeLibros;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ControladorLibros implements ActionListener {
    private final FrmAeLibros vista;
    private final LibroDAO libroDAO;

    public ControladorLibros(FrmAeLibros vista, LibroDAO libroDAO) {
        this.vista = vista;
        this.libroDAO = libroDAO;

        this.vista.getBtnCrear().addActionListener(this);
        this.vista.getBtnMostrar().addActionListener(this);
        this.vista.getBtnEditar().addActionListener(this);
        this.vista.getBtnEliminar().addActionListener(this);
    }

    private void crearLibro() {
        String titulo = vista.getTxtTituloLibro().getText();
        String autor = vista.getTxtAutorLibro().getText();
        String genero = vista.getTxtgeneroLibro().getText();

        if (titulo.isEmpty() || autor.isEmpty() || genero.isEmpty()) {
            JOptionPane.showMessageDialog(vista, "Todos los campos deben estar rellenos.");
            return;
        }

        Libro libro = new Libro(titulo, autor, genero);
        libroDAO.agregarLibro(libro);
        JOptionPane.showMessageDialog(vista, "El libro ha sido creado.");
        limpiarCampos();
    }

    private void mostrarLibros() {
        List<Libro> libros = libroDAO.obtenerTodosLosLibros();
        StringBuilder resultado = new StringBuilder("Libros: \n");

        for (Libro libro : libros) {
            resultado.append("Titulo: ").append(libro.getTitulo()).append("\n");
            resultado.append("Autor: ").append(libro.getAutor()).append("\n");
            resultado.append("Genero: ").append(libro.getGenero()).append("\n");
            resultado.append("--------------------\n");
        }

        JOptionPane.showMessageDialog(vista, resultado.toString());
    }

    private void editarLibro() {
        String id = JOptionPane.showInputDialog(vista, "Introduce el ID del libro a editar:");
        String titulo = vista.getTxtTituloLibro().getText();
        String autor = vista.getTxtAutorLibro().getText();
        String genero = vista.getTxtgeneroLibro().getText();

        if (titulo.isEmpty() || autor.isEmpty() || genero.isEmpty()) {
            JOptionPane.showMessageDialog(vista, "Todos los campos deben estar rellenos.");
            return;
        }

        Libro libro = new Libro(titulo, autor, genero);
        libroDAO.actualizarLibro(libro);
        JOptionPane.showMessageDialog(vista, "El libro ha sido editado.");
        limpiarCampos();
    }

    private void eliminarLibro() {
        String id = JOptionPane.showInputDialog(vista, "Introduce el ID del libro a eliminar:");

        if (id != null && !id.isEmpty()) {
            libroDAO.eliminarLibro(id);
            JOptionPane.showMessageDialog(vista, "El libro ha sido eliminado.");
        } else {
            JOptionPane.showMessageDialog(vista, "ID no válido.");
        }
    }

    private void limpiarCampos() {
        vista.getTxtTituloLibro().setText("");
        vista.getTxtAutorLibro().setText("");
        vista.getTxtgeneroLibro().setText("");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vista.getBtnCrear()) {
            crearLibro();
        } else if (e.getSource() == vista.getBtnEliminar()) {
            eliminarLibro();
        } else if (e.getSource() == vista.getBtnEditar()) {
            editarLibro();
        } else if (e.getSource() == vista.getBtnMostrar()) {
            mostrarLibros();
        }
    }
}
