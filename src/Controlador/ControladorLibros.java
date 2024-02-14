package Controlador;

import Modelo.Libro;
import Modelo.LibroDAO;
import Vista.FrmAeLibros;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ControladorLibros implements ActionListener {

    private FrmAeLibros vista;
    private LibroDAO libroDAO;
    private String libroSeleccionado;

    public ControladorLibros(FrmAeLibros vista, LibroDAO libroDAO) {
        this.vista = vista;
        this.libroDAO = libroDAO;
        this.vista.getBtnCrear().addActionListener(this);
        this.vista.getBtnEliminar().addActionListener(this);
        this.vista.getBtnEditar().addActionListener(this);
        this.vista.getBtnMostrar().addActionListener(this);
        this.vista.getJtblDatosLibros().getSelectionModel().addListSelectionListener(new TableSelectionListener());
        actualizarTabla(); // Actualizar la tabla al inicio
    }

    private void crearLibro() {
        String titulo = vista.getTxtTituloLibro().getText();
        String autor = vista.getTxtAutorLibro().getText();
        String genero = vista.getTxtgeneroLibro().getText();
        if (!titulo.isEmpty() && !autor.isEmpty() && !genero.isEmpty()) {
            Libro libro = new Libro(titulo, autor, genero);
            libroDAO.agregarLibro(libro);
            JOptionPane.showMessageDialog(vista, "El libro ha sido creado.");
            limpiarCampos();
            actualizarTabla(); // Actualizar la tabla
        } else {
            JOptionPane.showMessageDialog(vista, "Todos los campos son obligatorios.");
        }
    }

    private void eliminarLibro() {
        if (libroSeleccionado != null) {
            int confirmacion = JOptionPane.showConfirmDialog(vista, "¿Estás seguro de eliminar el libro?\n" + libroSeleccionado, "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
            if (confirmacion == JOptionPane.YES_OPTION) {
                libroDAO.eliminarLibro(libroSeleccionado);
                JOptionPane.showMessageDialog(vista, "El libro ha sido eliminado.");
                actualizarTabla(); // Actualizar la tabla
            }
        } else {
            JOptionPane.showMessageDialog(vista, "Seleccione un libro de la tabla para eliminar.");
        }
    }

    private void editarLibro() {
        DefaultTableModel modeloT = (DefaultTableModel) vista.getJtblDatosLibros().getModel();
        int filaSeleccionada = vista.getJtblDatosLibros().getSelectedRow();

        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(vista, "Selecciona un libro para modificar", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            String tituloAntiguo = (String) modeloT.getValueAt(filaSeleccionada, 0);
            String autorAntiguo = (String) modeloT.getValueAt(filaSeleccionada, 1);
            String generoAntiguo = (String) modeloT.getValueAt(filaSeleccionada, 2);

            String nuevoTitulo = JOptionPane.showInputDialog(vista, "Nuevo título:", "Modificar Libro", JOptionPane.QUESTION_MESSAGE);
            String nuevoAutor = JOptionPane.showInputDialog(vista, "Nuevo autor:", "Modificar Libro", JOptionPane.QUESTION_MESSAGE);
            String nuevoGenero = JOptionPane.showInputDialog(vista, "Nuevo género:", "Modificar Libro", JOptionPane.QUESTION_MESSAGE);

            if (nuevoTitulo == null || nuevoAutor == null || nuevoGenero == null) {
                return;
            }

            if (nuevoTitulo.trim().isEmpty() || nuevoAutor.trim().isEmpty() || nuevoGenero.trim().isEmpty()) {
                JOptionPane.showMessageDialog(vista, "Por favor, completa todos los campos", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                // Modificar el libro
                libroDAO.modificarLibro(tituloAntiguo, autorAntiguo, generoAntiguo, nuevoTitulo.trim(), nuevoAutor.trim(), nuevoGenero.trim());
                actualizarTabla(); // Actualizar la tabla
            }
        }
    }

    private void limpiarCampos() {
        vista.getTxtTituloLibro().setText("");
        vista.getTxtAutorLibro().setText("");
        vista.getTxtgeneroLibro().setText("");
    }

    private void actualizarTabla() {
        DefaultTableModel modeloT = (DefaultTableModel) vista.getJtblDatosLibros().getModel();
        modeloT.setRowCount(0); // Limpiar la tabla
        try {
            List<Libro> libros = libroDAO.obtenerTodosLosLibros();
            for (Libro libro : libros) {
                Object[] columna = new Object[3];
                columna[0] = libro.getTitulo();
                columna[1] = libro.getAutor();
                columna[2] = libro.getGenero();
                modeloT.addRow(columna);
            }

            // Seleccionar la última fila agregada
            if (!libros.isEmpty()) {
                int lastRow = modeloT.getRowCount() - 1;
                vista.getJtblDatosLibros().setRowSelectionInterval(lastRow, lastRow);
            }

            // Actualizar el modelo de la tabla
            vista.getJtblDatosLibros().repaint();
            vista.getJtblDatosLibros().revalidate();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(vista, "Error al actualizar la tabla: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private class TableSelectionListener implements ListSelectionListener {
        @Override
        public void valueChanged(ListSelectionEvent e) {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = vista.getJtblDatosLibros().getSelectedRow();
                if (selectedRow != -1) {
                    libroSeleccionado = (String) vista.getJtblDatosLibros().getValueAt(selectedRow, 0);
                }
            }
        }
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
            actualizarTabla(); // Actualizar la tabla
        }
    }
}
