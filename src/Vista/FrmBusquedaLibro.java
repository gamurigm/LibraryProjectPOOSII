package Vista;

import Modelo.Conexion;
import Modelo.Libro;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import org.bson.Document;

/**
 *
 * @author alejo
 */
public class FrmBusquedaLibro extends javax.swing.JFrame {

    public FrmBusquedaLibro() {
        initComponents();
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        txtAutor = new javax.swing.JTextField();
        txtGenero = new javax.swing.JTextField();
        txtCodigo = new javax.swing.JTextField();
        btnBuscar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaMostrar = new javax.swing.JTable();
        btnReservar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("BUSQUEDA Y RESERVA LIBRO"));

        jLabel1.setText("Nombre Libro:");

        jLabel2.setText("Autor:");

        jLabel3.setText("Genero Libro:");

        jLabel4.setText("Codigo:");

        txtNombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNombreActionPerformed(evt);
            }
        });

        txtAutor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtAutorActionPerformed(evt);
            }
        });

        txtGenero.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtGeneroActionPerformed(evt);
            }
        });

        txtCodigo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodigoActionPerformed(evt);
            }
        });

        btnBuscar.setText("BUSCAR");
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });

        tablaMostrar.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "NOMBRE", "AUTOR", "GENERO", "CODIGO"
            }
        ));
        jScrollPane1.setViewportView(tablaMostrar);

        btnReservar.setText("RESERVAR");
        btnReservar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReservarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtNombre)
                            .addComponent(txtAutor)
                            .addComponent(txtGenero)
                            .addComponent(txtCodigo, javax.swing.GroupLayout.DEFAULT_SIZE, 359, Short.MAX_VALUE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(58, 58, 58)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(174, 174, 174)
                        .addComponent(btnBuscar)
                        .addGap(65, 65, 65)
                        .addComponent(btnReservar)))
                .addContainerGap(71, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jLabel2))
                    .addComponent(txtAutor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtGenero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnBuscar)
                    .addComponent(btnReservar))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(55, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(27, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(43, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnReservarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReservarActionPerformed
        // TODO add your handling code here:
        int selectedRow = tablaMostrar.getSelectedRow();

    if (selectedRow != -1) {
        int libroId = (int) tablaMostrar.getValueAt(selectedRow, 3); // Obtener el ID del libro
        reservarLibro(libroId);
    } else {
        JOptionPane.showMessageDialog(this, "Seleccione un libro antes de reservar.");
    }
    
    }//GEN-LAST:event_btnReservarActionPerformed

    private void txtNombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNombreActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombreActionPerformed

    private void txtAutorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtAutorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAutorActionPerformed

    private void txtGeneroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtGeneroActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtGeneroActionPerformed

    private void txtCodigoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodigoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCodigoActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
//        // TODO add your handling code here:
//            String nombre = txtNombre.getText().trim();
//    String autor = txtAutor.getText().trim();
//    String genero = txtGenero.getText().trim();
//    String codigo = txtCodigo.getText().trim();
//
//    // Lógica para buscar libros
//    List<Libro> resultados = buscarLibros(nombre, autor, genero, codigo);
//
//    // Mostrar los resultados en la JTable
//    mostrarResultadosEnTabla(resultados);
    }//GEN-LAST:event_btnBuscarActionPerformed

    
    private List<Libro> buscarLibros(String nombre, String autor, String genero, String codigo) {
    List<Libro> resultados = new ArrayList<>();

    try {
        MongoCollection<Document> collection = Conexion.getColeccion("Libros"); 

        Document query = new Document();
        if (!nombre.isEmpty()) query.append("nombre", new Document("$regex", nombre));
        if (!autor.isEmpty()) query.append("autor", new Document("$regex", autor));
        if (!genero.isEmpty()) query.append("genero", new Document("$regex", genero));
        if (!codigo.isEmpty()) query.append("codigo", Integer.parseInt(codigo));

        try (MongoCursor<Document> cursor = collection.find(query).iterator()) {
            while (cursor.hasNext()) {
                Document libroDoc = cursor.next();
                Libro libro = new Libro(
                        libroDoc.getString("nombre"),
                        libroDoc.getString("autor"),
                        libroDoc.getString("genero")
                );
                libro.setDisponible(libroDoc.getBoolean("disponible"));
                libro.setStock(libroDoc.getInteger("stock", 0));
                resultados.add(libro);
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    }

    return resultados;
}

public void mostrarResultadosEnTabla(List<Libro> resultados) {
    DefaultTableModel model = new DefaultTableModel();
    model.setColumnIdentifiers(new Object[]{"NOMBRE", "AUTOR", "GENERO", "CODIGO"});

    for (Libro libro : resultados) {
        model.addRow(new Object[]{libro.getTitulo(), libro.getAutor(), libro.getGenero(), libro.getId()});
    }

    tablaMostrar.setModel(model);
}

    private void reservarLibro(int libroId) {
    try {
        MongoCollection<Document> collection = Conexion.getColeccion("Libros"); // Ajusta el nombre de la colección según tu caso

        // Buscar el libro por su ID
        Document query = new Document("_id", libroId); // Asumiendo que el ID del libro está en la clave "_id"
        try (MongoCursor<Document> cursor = collection.find(query).iterator()) {
            if (cursor.hasNext()) {
                Document libroDoc = cursor.next();
                Libro libro = new Libro(
                        libroDoc.getString("nombre"),
                        libroDoc.getString("autor"),
                        libroDoc.getString("genero")
                );

                if (libro.estaDisponible()) {
                    // Actualizar la disponibilidad del libro en la base de datos
                    collection.updateOne(query, new Document("$set", new Document("disponible", false)));

                    JOptionPane.showMessageDialog(this, "Libro reservado con éxito.");
                } else {
                    JOptionPane.showMessageDialog(this, "El libro ya está reservado.");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Libro no encontrado.");
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
}


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton btnBuscar;
    public javax.swing.JButton btnReservar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    public javax.swing.JTable tablaMostrar;
    public javax.swing.JTextField txtAutor;
    public javax.swing.JTextField txtCodigo;
    public javax.swing.JTextField txtGenero;
    public javax.swing.JTextField txtNombre;
    // End of variables declaration//GEN-END:variables
}
