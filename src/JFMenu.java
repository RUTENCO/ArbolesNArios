/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.Color;
import java.awt.Font;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JTextPane;

import arboles.ArbolLg;
import arboles.Arboles;

public class JFMenu extends javax.swing.JFrame {

    public static ArbolLg A = null;

    /**
     * Creates new form JFMenu
     */
    public JFMenu() {
        initComponents();
        this.setLocationRelativeTo(null);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated
    // Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        btnconstuirArbol = new javax.swing.JButton();
        btnmostrarArbol = new javax.swing.JButton();
        btnalturaArbol = new javax.swing.JButton();
        btngradoArbol = new javax.swing.JButton();
        btngradoRegistro = new javax.swing.JButton();
        btnnumeroHojas = new javax.swing.JButton();
        btnnivelDato = new javax.swing.JButton();
        btnancestrosDato = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        printArea = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("MENU");

        jPanel2.setBackground(java.awt.Color.black);
        jPanel2.setPreferredSize(new java.awt.Dimension(700, 280));
        jPanel2.setRequestFocusEnabled(false);

        btnconstuirArbol.setBackground(new java.awt.Color(0, 204, 255));
        btnconstuirArbol.setText("Construir Arbol");
        btnconstuirArbol.setPreferredSize(new java.awt.Dimension(172, 36));
        btnconstuirArbol.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnconstuirArbolActionPerformed(evt);
            }
        });

        btnmostrarArbol.setBackground(new java.awt.Color(0, 204, 255));
        btnmostrarArbol.setText("Mostrar Arbol");
        btnmostrarArbol.setPreferredSize(new java.awt.Dimension(172, 36));
        btnmostrarArbol.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnmostrarArbolActionPerformed(evt);
            }
        });

        btnalturaArbol.setBackground(new java.awt.Color(0, 204, 255));
        btnalturaArbol.setText("Altura del Arbol");
        btnalturaArbol.setPreferredSize(new java.awt.Dimension(172, 36));
        btnalturaArbol.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnalturaArbolActionPerformed(evt);
            }
        });

        btngradoArbol.setBackground(new java.awt.Color(0, 204, 255));
        btngradoArbol.setText("Grado del Arbol");
        btngradoArbol.setPreferredSize(new java.awt.Dimension(172, 36));
        btngradoArbol.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btngradoArbolActionPerformed(evt);
            }
        });

        btngradoRegistro.setBackground(new java.awt.Color(0, 204, 255));
        btngradoRegistro.setText("Grado de un Registro");
        btngradoRegistro.setPreferredSize(new java.awt.Dimension(172, 36));
        btngradoRegistro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btngradoRegistroActionPerformed(evt);
            }
        });

        btnnumeroHojas.setBackground(new java.awt.Color(0, 204, 255));
        btnnumeroHojas.setText("Numero de Hojas");
        btnnumeroHojas.setPreferredSize(new java.awt.Dimension(172, 36));
        btnnumeroHojas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnnumeroHojasActionPerformed(evt);
            }
        });

        btnnivelDato.setBackground(new java.awt.Color(0, 204, 255));
        btnnivelDato.setText("Nivel de un Dato");
        btnnivelDato.setPreferredSize(new java.awt.Dimension(172, 36));
        btnnivelDato.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnnivelDatoActionPerformed(evt);
            }
        });

        btnancestrosDato.setBackground(new java.awt.Color(0, 204, 255));
        btnancestrosDato.setText("Ancestro de un Dato");
        btnancestrosDato.setPreferredSize(new java.awt.Dimension(172, 36));
        btnancestrosDato.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnancestrosDatoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(90, 90, 90)
                                .addGroup(jPanel2Layout
                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(btngradoArbol, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btnalturaArbol, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btnconstuirArbol, javax.swing.GroupLayout.Alignment.LEADING,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(btnmostrarArbol, javax.swing.GroupLayout.Alignment.LEADING,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 228,
                                        Short.MAX_VALUE)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel2Layout
                                                .createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                .addComponent(btnnumeroHojas, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                        172, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(btngradoRegistro, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(btnnivelDato, javax.swing.GroupLayout.PREFERRED_SIZE, 172,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(btnancestrosDato, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(126, 126, 126)));
        jPanel2Layout.setVerticalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(37, 37, 37)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(btnconstuirArbol, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btngradoRegistro, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(31, 31, 31)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(btnnumeroHojas, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btnmostrarArbol, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(33, 33, 33)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(btnnivelDato, javax.swing.GroupLayout.Alignment.TRAILING,
                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btnalturaArbol, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(26, 26, 26)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(btngradoArbol, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btnancestrosDato, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(48, Short.MAX_VALUE)));

        jPanel1.setBackground(java.awt.Color.black);
        jPanel1.setPreferredSize(new java.awt.Dimension(700, 340));

        printArea.setBackground(new java.awt.Color(255, 0, 51));
        printArea.setBackground(Color.gray);
        printArea.setColumns(20);
        printArea.setFont(new java.awt.Font("Consolas", 1, 12)); // NOI18N
        printArea.setRows(5);
        jScrollPane2.setViewportView(printArea);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jScrollPane2)
                                .addContainerGap()));
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 339, Short.MAX_VALUE)
                                .addContainerGap()));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 788,
                                                Short.MAX_VALUE)
                                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 788,
                                                Short.MAX_VALUE))
                                .addContainerGap()));
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 351, Short.MAX_VALUE)
                                .addGap(18, 18, 18)
                                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 319, Short.MAX_VALUE)
                                .addContainerGap()));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnconstuirArbolActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnconstuirArbolActionPerformed

        String respuesta = JOptionPane
                .showInputDialog(" Ingrese hilera de paréntesis izquierdos, átomos, comas\n y paréntesis " +
                        "derechos.");
        if(respuesta == null){
            return;
        }
                        
        try {
            A = Arboles.consArbolLg(respuesta);
            printArea.setText(A.toString());
        } catch (Exception e) {

            JTextPane errorTextPane = new JTextPane();
            errorTextPane.setFont(new Font("Consolas", Font.ITALIC, 12));
            errorTextPane.setText(e.getMessage());
            JOptionPane.showMessageDialog(null, errorTextPane, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }// GEN-LAST:event_btnconstuirArbolActionPerformed

    private void btnmostrarArbolActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnmostrarArbolActionPerformed

        if (A == null) {
            JOptionPane.showMessageDialog(null, " No se ha construido un Arbol", "ADVERTENCIA",
                    JOptionPane.WARNING_MESSAGE);
        } else {
            printArea.setText("");
            this.setVisible(false);
            new JFmostrarArbol().setVisible(true);
        }
    }// GEN-LAST:event_btnmostrarArbolActionPerformed

    private void btnalturaArbolActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnalturaArbolActionPerformed

        if (A == null) {
            JOptionPane.showMessageDialog(null, " No se ha construido un Arbol", "ADVERTENCIA",
                    JOptionPane.WARNING_MESSAGE);
        } else {
            int altura = A.getHeight();
            JOptionPane.showMessageDialog(null, "La altura del Arbol es : " + altura);
        }

    }// GEN-LAST:event_btnalturaArbolActionPerformed

    private void btngradoArbolActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btngradoArbolActionPerformed

        if (A == null) {
            JOptionPane.showMessageDialog(null, " No se ha construido un Arbol", "ADVERTENCIA",
                    JOptionPane.WARNING_MESSAGE);
        } else {
            int grado = A.getDegreeNonRecursive();
            JOptionPane.showMessageDialog(null, "El grado del Arbol es : " + grado);
        }

    }// GEN-LAST:event_btngradoArbolActionPerformed

    private void btngradoRegistroActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btngradoRegistroActionPerformed

        String registro = "";

        if (A == null) {
            JOptionPane.showMessageDialog(null, " No se ha construido un Arbol", "ADVERTENCIA",
                    JOptionPane.WARNING_MESSAGE);
        } else {
            registro = JOptionPane.showInputDialog("Ingrese un registro del Arbol");
            registro = registro.toLowerCase();

            try {
                int gradoR = A.degreeOf(registro);
                JOptionPane.showMessageDialog(null, "El grado de " + registro + " es : " + gradoR);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

    }// GEN-LAST:event_btngradoRegistroActionPerformed

    private void btnnumeroHojasActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnnumeroHojasActionPerformed

        if (A == null) {
            JOptionPane.showMessageDialog(null, " No se ha construido un Arbol", "ADVERTENCIA",
                    JOptionPane.WARNING_MESSAGE);
        } else {
            int hojas = A.countLeafs2();
            JOptionPane.showMessageDialog(null, "El numero de hojas del arbol es : " + hojas);
        }
    }// GEN-LAST:event_btnnumeroHojasActionPerformed

    private void btnnivelDatoActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnnivelDatoActionPerformed

        String registro = "";

        if (A == null) {
            JOptionPane.showMessageDialog(null, " No se ha construido un Arbol", "ADVERTENCIA",
                    JOptionPane.WARNING_MESSAGE);
        } else {
            registro = JOptionPane.showInputDialog("Ingrese un registro del Arbol");
            registro = registro.toLowerCase();

            try {
                int nivel = A.levelOf(registro);
                JOptionPane.showMessageDialog(null, "El nivel de " + registro + " es: " + nivel);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }

        }

    }// GEN-LAST:event_btnnivelDatoActionPerformed

    private void btnancestrosDatoActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnancestrosDatoActionPerformed

        String registro = "";
        List<Object> b = null;

        if (A == null) {
            JOptionPane.showMessageDialog(null, " No se ha construido un Arbol", "ADVERTENCIA",
                    JOptionPane.WARNING_MESSAGE);
        } else {
            registro = JOptionPane.showInputDialog("Ingrese un registro del Arbol");
            registro = registro.toLowerCase();

            if (A.find(registro) == null) {
                JOptionPane.showMessageDialog(null, " Registro no encontrado ", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                b = A.getAncestors(A.find(registro));
                JOptionPane.showMessageDialog(null, "Los Ancestros de " + registro + " son: " + b.toString());
            }

        }
    }// GEN-LAST:event_btnancestrosDatoActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        // <editor-fold defaultstate="collapsed" desc=" Look and feel setting code
        // (optional) ">
        /*
         * If Nimbus (introduced in Java SE 6) is not available, stay with the default
         * look and feel.
         * For details see
         * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(JFMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JFMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JFMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JFMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        // </editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new JFMenu().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton btnalturaArbol;
    public javax.swing.JButton btnancestrosDato;
    public javax.swing.JButton btnconstuirArbol;
    public javax.swing.JButton btngradoArbol;
    public javax.swing.JButton btngradoRegistro;
    public javax.swing.JButton btnmostrarArbol;
    public javax.swing.JButton btnnivelDato;
    public javax.swing.JButton btnnumeroHojas;
    public javax.swing.JPanel jPanel1;
    public javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane2;
    public javax.swing.JTextArea printArea;
    // End of variables declaration//GEN-END:variables
}
