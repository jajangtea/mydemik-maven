/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package form.mydemik.com;

import db.mydemik.com.Koneksi;
import db.service.mydemik.com.SuratService;
import entiti.mydemik.com.Mahasiswa;
import entiti.mydemik.com.Prodi;
import java.sql.SQLException;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import util.mydemik.com.Fungsi;
import util.mydemik.com.HibernateUtil;

/**
 *
 * @author jajangtea
 */
public class __EntryMahasiswa extends javax.swing.JFrame {

    /**
     * Creates new form __EntryMahasiswa
     */
    
    SuratService SuratService;
    int idMhs;
    Fungsi nf = new Fungsi();
    public __EntryMahasiswa() throws SQLException {
        initComponents();
        fillTable(TMhs);
        Koneksi koneksi=new Koneksi();
        SuratService=new SuratService(koneksi.getConnection());
        loadprodi();
    }
    
    public javax.swing.JTable getTabel() {
        return TMhs;
    }
    
    private void fillTable(final JTable table) 
    {
        try
        {
            int count=1;
            SessionFactory sf=HibernateUtil.getSessionFactory();
            Session s=sf.openSession();
            Transaction tx = s.beginTransaction();
            Query q = s.createQuery("FROM Mahasiswa");
            List resultList = q.list();
            Vector<String> tableHeaders = new Vector<String>();
            Vector tableData = new Vector();
            tableHeaders.add("No"); 
            tableHeaders.add("idMahasiswa"); 
            tableHeaders.add("NIM"); 
            tableHeaders.add("Nama"); 
            tableHeaders.add("Handphone");
            tableHeaders.add("Alamat");
            tableHeaders.add("Prodi");
            for(Object o : resultList) 
            {
                Mahasiswa pd = (Mahasiswa)o;
                Vector<Object> oneRow = new Vector<Object>();
                oneRow.add(count);
                oneRow.add(pd.getIdMahasiswa());
                oneRow.add(pd.getNim());
                oneRow.add(pd.getNama());
                oneRow.add(pd.getTlp());
                oneRow.add(pd.getAlamat());
                oneRow.add(pd.getProdi().getNamaProdi());
                tableData.add(oneRow);
                count++;
            }
            TMhs.setModel(new DefaultTableModel(tableData, tableHeaders));
            s.flush();
            tx.commit();
            s.close();
            nf.setLebarKolom(table);
            nf.hideColumn(table, 1);
        }
        catch (ClassCastException e) 
        {
                e.printStackTrace();
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel10 = new javax.swing.JLabel();
        lbKeperluan = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtNama = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        TMhs = new javax.swing.JTable();
        btnHapus = new javax.swing.JButton();
        btnSimpan = new javax.swing.JButton();
        cbbProdi = new javax.swing.JComboBox();
        lbtanggal1 = new javax.swing.JLabel();
        txtNIM = new javax.swing.JTextField();
        lbtujuan1 = new javax.swing.JLabel();
        lbtanggal2 = new javax.swing.JLabel();
        txtHP = new javax.swing.JTextField();
        btnKeluar = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtAlamat = new javax.swing.JTextArea();
        btnBaru = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Kelola Mahasiswa");
        setResizable(false);

        jLabel10.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel10.setText("Nama :");

        lbKeperluan.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        lbKeperluan.setText("No Handphone :");

        jLabel4.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel4.setText("Nim :");

        txtNama.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N

        TMhs.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        TMhs.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        TMhs.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TMhsMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(TMhs);

        btnHapus.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        btnHapus.setText("Hapus");
        btnHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHapusActionPerformed(evt);
            }
        });

        btnSimpan.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        btnSimpan.setText("Simpan");
        btnSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpanActionPerformed(evt);
            }
        });

        cbbProdi.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        cbbProdi.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbbProdiItemStateChanged(evt);
            }
        });
        cbbProdi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbbProdiActionPerformed(evt);
            }
        });

        lbtanggal1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lbtanggal1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lbtanggal1.setText("FORM INI DIGUNAKAN UNTUK MENGELOLA DATA MAHASISWA");

        txtNIM.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        txtNIM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNIMActionPerformed(evt);
            }
        });

        lbtujuan1.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        lbtujuan1.setText("Prodi :");

        lbtanggal2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lbtanggal2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lbtanggal2.setText("Alamat :");

        txtHP.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        txtHP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtHPActionPerformed(evt);
            }
        });

        btnKeluar.setText("Keluar");
        btnKeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKeluarActionPerformed(evt);
            }
        });

        txtAlamat.setColumns(20);
        txtAlamat.setRows(5);
        jScrollPane2.setViewportView(txtAlamat);

        btnBaru.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        btnBaru.setText("Baru");
        btnBaru.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBaruActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnKeluar)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(layout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 784, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                            .addGap(44, 44, 44)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel4)
                                .addComponent(jLabel10)
                                .addComponent(lbKeperluan))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(btnBaru)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(btnSimpan)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(btnHapus))
                                .addComponent(txtNama)
                                .addComponent(txtNIM)
                                .addComponent(txtHP, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(lbtujuan1)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(cbbProdi, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(lbtanggal2)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 319, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addContainerGap(12, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(lbtanggal1, javax.swing.GroupLayout.PREFERRED_SIZE, 526, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(270, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(49, 49, 49)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtNIM, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(cbbProdi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lbtujuan1)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtNama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lbtanggal2)
                                    .addComponent(jLabel10))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtHP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lbKeperluan))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(btnSimpan)
                                    .addComponent(btnHapus)
                                    .addComponent(btnBaru)))
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 339, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnKeluar)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(lbtanggal1)
                    .addContainerGap(522, Short.MAX_VALUE)))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void TMhsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TMhsMouseClicked
        int baris =TMhs.getSelectedRow();
        int nim;
        nim=(Integer) TMhs.getModel().getValueAt(baris, 2);
        idMhs = (Integer)TMhs.getModel().getValueAt(baris, 1);
        System.out.println(idMhs);
        txtNIM.setText(Integer.toString(nim));
        txtNama.setText((String) TMhs.getModel().getValueAt(baris, 3));
        txtHP.setText((String) TMhs.getModel().getValueAt(baris, 4));
        txtAlamat.setText((String) TMhs.getModel().getValueAt(baris, 5));
        cbbProdi.setSelectedItem(TMhs.getModel().getValueAt(baris, 6));
        btnSimpan.setText("Ubah");
    }//GEN-LAST:event_TMhsMouseClicked

    private void btnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusActionPerformed
        // TODO add your handling code here:
        Hapus(idMhs);
        fillTable(TMhs);       
    }//GEN-LAST:event_btnHapusActionPerformed

    private void btnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanActionPerformed
        // TODO add your handling code here:
        SessionFactory sf=HibernateUtil.getSessionFactory();
        Session s=sf.openSession();
        Mahasiswa mhs=new Mahasiswa();
        Prodi pr=new Prodi();
        if(btnSimpan.getText().equals("Simpan"))
        {
            Prodi p=(Prodi)cbbProdi.getSelectedItem();
            mhs.setNim(Integer.parseInt(txtNIM.getText()));
            mhs.setNama(txtNama.getText());
            mhs.setTlp(txtHP.getText());
            mhs.setAlamat(txtAlamat.getText());
            mhs.setProdi(pr);
            pr.setIdProdi(p.getIdProdi());
            Transaction tx=s.beginTransaction();
            s.saveOrUpdate(mhs);
            tx.commit();
        }
        else
        {
            Prodi p=(Prodi)cbbProdi.getSelectedItem();
            mhs.setIdMahasiswa(idMhs);
            mhs.setNim(Integer.parseInt(txtNIM.getText()));
            mhs.setNama(txtNama.getText());
            mhs.setTlp(txtHP.getText());
            mhs.setAlamat(txtAlamat.getText());
            mhs.setProdi(pr);
            pr.setIdProdi(p.getIdProdi());
            Transaction tx=s.beginTransaction();
            s.saveOrUpdate(mhs);
            tx.commit();
        }
        s.flush();
        s.close();
        fillTable(TMhs);
    }//GEN-LAST:event_btnSimpanActionPerformed
    private void loadprodi() throws SQLException{
        cbbProdi.removeAllItems();
        List<Prodi> kp=SuratService.getAllProdi();
        for(Prodi pr:kp){
            cbbProdi.addItem(pr);
        }
    }
    
     private void Hapus(int hps) 
    {
        try
        {
            if (JOptionPane.showConfirmDialog(null,"Anda Yakin ? ","Konfirmasi" , JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION)
            {
                SessionFactory sf=HibernateUtil.getSessionFactory();
                Session s=sf.openSession();
                Transaction tx = s.beginTransaction();
                Query q = s.createQuery("Delete From Mahasiswa where idMahasiswa=:idMahasiswa");
                q.setParameter("idMahasiswa", hps);
                q.executeUpdate();
                tx.commit();
                s.flush();
                s.close();
            }
        }
        catch (ClassCastException e) 
        {
            System.out.println("Err :" + e);
        }
    }
    private void cbbProdiItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbbProdiItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_cbbProdiItemStateChanged

    private void cbbProdiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbbProdiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbbProdiActionPerformed

    private void txtNIMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNIMActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNIMActionPerformed

    private void txtHPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtHPActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtHPActionPerformed

    private void btnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKeluarActionPerformed
        // TODO add your handling code here:
        dispose();
    }//GEN-LAST:event_btnKeluarActionPerformed

    private void btnBaruActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBaruActionPerformed
        // TODO add your handling code here:
        txtNIM.setText("");
        txtNama.setText("");
        txtHP.setText("");
        txtAlamat.setText("");
        txtNIM.requestFocus();
        btnSimpan.setText("Simpan");
    }//GEN-LAST:event_btnBaruActionPerformed

    /**
     * @param args the command line arguments
     */
//    public static void main(String args[]) {
//        /* Set the Nimbus look and feel */
//        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
//        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
//         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
//         */
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(__EntryMahasiswa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(__EntryMahasiswa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(__EntryMahasiswa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(__EntryMahasiswa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//
//        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                try {
//                    new __EntryMahasiswa().setVisible(true);
//                } catch (SQLException ex) {
//                    Logger.getLogger(__EntryMahasiswa.class.getName()).log(Level.SEVERE, null, ex);
//                }
//            }
//        });
//    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JTable TMhs;
    private javax.swing.JButton btnBaru;
    private javax.swing.JButton btnHapus;
    private javax.swing.JButton btnKeluar;
    private javax.swing.JButton btnSimpan;
    public javax.swing.JComboBox cbbProdi;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lbKeperluan;
    private javax.swing.JLabel lbtanggal1;
    private javax.swing.JLabel lbtanggal2;
    private javax.swing.JLabel lbtujuan1;
    private javax.swing.JTextArea txtAlamat;
    private javax.swing.JTextField txtHP;
    public javax.swing.JTextField txtNIM;
    private javax.swing.JTextField txtNama;
    // End of variables declaration//GEN-END:variables
}