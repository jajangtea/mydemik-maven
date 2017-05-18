/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package form.mydemik.com;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import db.mydemik.com.Koneksi;
import db.service.mydemik.com.SuratService;
import entiti.mydemik.com.Jadwal;
import java.awt.BorderLayout;
import java.awt.Dialog.ModalityType;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.AbstractButton;
import javax.swing.Icon;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.table.DefaultTableModel;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.jdesktop.swingx.calendar.AbstractDateSelectionModel;
import util.mydemik.com.Fungsi;
import util.mydemik.com.HibernateUtil;

/**
 *
 * @author jajangtea
 */
public class __ImportJadwal extends javax.swing.JFrame {

    /**
     * Creates new form __ImportJadwal
     */
    File file;
    SuratService  SuratService;
    String a;
    int idJadwal,i;
    public  static final long SLEEP_TIME = 2 * 1000;
    public __ImportJadwal() {
        initComponents();
        Koneksi koneksi=new Koneksi();
        SuratService=new SuratService(koneksi.getConnection());
       // lbloading.setVisible(false);
       jXDatePicker1.addActionListener(new jXDatePickerListener());
    }
    
    private void getJadwal()
    {
        Date day=jXDatePicker1.getDate();
        DateFormat odate=new SimpleDateFormat("yyyy-MM-dd");
        a=odate.format(day);
        System.out.println(a);
        try 
        {
            loadJadwal(a);
        } catch (SQLException ex) {
            Logger.getLogger(__ImportJadwal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void importjadwal()
    {
        try
            {
           // lbloading = new JLabel(new ShowWaitAction("Tunggu"));
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/mydemik","root","");
            con.setAutoCommit(false);
            PreparedStatement pstm = null ;
            FileInputStream input = new FileInputStream(file);
            XSSFWorkbook wb = new XSSFWorkbook(input);
            XSSFSheet sheet = wb.getSheetAt(0);
            Row row;
            for(i=1; i<=sheet.getLastRowNum(); i++)
            {
                row = sheet.getRow(i);
                Fungsi fs = new Fungsi();
                //int idJadwal = (int) row.getCell(1).getNumericCellValue();
                int nim = (int)row.getCell(1).getNumericCellValue();
                //String address = row.getCell(2).getStringCellValue();
                String sql = "INSERT INTO jadwalmhs (idJadwal,nim) VALUES('"+idJadwal+"','"+fs.getIdNIM(nim)+"')";
                pstm = (PreparedStatement) con.prepareStatement(sql);
                pstm.execute();
                System.out.println("Import rows "+i);
            }
            con.commit();
            pstm.close();
            con.close();
            input.close();
            System.out.println("Success import excel to mysql table");
            JOptionPane.showMessageDialog(null,"Data Berhasil diimpor sebanyak "+i+" Baris", "Informasi",JOptionPane.INFORMATION_MESSAGE);
           
        }
        catch(ClassNotFoundException e)
        {
            JOptionPane.showMessageDialog(null,"error "+e+" Baris", "Informasi",JOptionPane.INFORMATION_MESSAGE);
        }catch(SQLException ex)
        {
            JOptionPane.showMessageDialog(null,"error "+ex+" Baris", "Informasi",JOptionPane.INFORMATION_MESSAGE);
        }catch(IOException ioe)
        {
            JOptionPane.showMessageDialog(null,"error "+ioe+" Baris", "Inane warning",JOptionPane.INFORMATION_MESSAGE);
        }
    }
    private void loadJadwal(String date) throws SQLException{
        
        List<Jadwal> kper=SuratService.getAllJadwal(date);
        if(kper.isEmpty())
        {
            JOptionPane.showMessageDialog(null,"Jadwal Tidak Ditemukan.", "Informasi",JOptionPane.INFORMATION_MESSAGE);
            CJam.removeAllItems();
        }
        else
        {
            CJam.removeAllItems();
            for(Jadwal jdw:kper)
            {
                CJam.addItem(jdw);
            }
        }
    }
    
    private void fillTable(final JTable table,final String jam,final String tanggal) 
    {
       
        int count=1;
        SessionFactory sf=HibernateUtil.getSessionFactory();
        Session s=sf.openSession();
        Transaction tx = s.beginTransaction();
        Query q;
        String q1="FROM Jadwal where tanggal='"+tanggal+"' and jam='"+jam+"'";
        q = s.createQuery(q1);
        List resultList = q.list();
        Vector<String> tableHeaders = new Vector<String>();
        Vector tableData = new Vector();
        tableHeaders.add("No");
        tableHeaders.add("idJadwal");
        tableHeaders.add("idMatkul");
        tableHeaders.add("idRuangan");
        for(Object o : resultList) 
        {
            Jadwal sr = (Jadwal)o;
            Vector<Object> oneRow = new Vector<Object>();
            oneRow.add(count);
            oneRow.add(sr.getIdJadwal());
            oneRow.add(sr.getMatkul().getNamaMatkul());
            oneRow.add(sr.getRuangan().getNoRuang());
            count++;
            tableData.add(oneRow);
        }
        TJadwal.setModel(new DefaultTableModel(tableData, tableHeaders));
        Fungsi fs=new Fungsi();
        fs.hideColumn(TJadwal, 1);
        fs.setLebarKolom(TJadwal);
        s.flush();
        tx.commit();
        s.close();
        //TJadwal.setEditingRow(false);
        TJadwal.setDefaultEditor(Object.class, null);
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jFileChooser1 = new javax.swing.JFileChooser();
        jButton1 = new javax.swing.JButton(new ShowWaitAction("Sedang Menunggu"));
        btnPilih = new javax.swing.JButton();
        CJam = new javax.swing.JComboBox();
        jXDatePicker1 = new org.jdesktop.swingx.JXDatePicker();
        jScrollPane1 = new javax.swing.JScrollPane();
        TJadwal = new javax.swing.JTable();
        txtFile = new javax.swing.JTextField();
        lbloading = new javax.swing.JLabel();

        jFileChooser1.setDialogTitle("Pilih File Ms.Excell");
        jFileChooser1.setFileFilter(new MyCustomFilter());

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jButton1.setText("Import");

        btnPilih.setText("Pilih File");
        btnPilih.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPilihActionPerformed(evt);
            }
        });

        CJam.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CJamActionPerformed(evt);
            }
        });

        jXDatePicker1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jXDatePicker1ActionPerformed(evt);
            }
        });

        TJadwal.setModel(new javax.swing.table.DefaultTableModel(
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
        TJadwal.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TJadwalMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(TJadwal);

        lbloading.setIcon(new javax.swing.ImageIcon("C:\\Users\\jajangtea\\Documents\\NetBeansProjects\\mydemik-maven\\MavenDemik\\src\\main\\resources\\images\\ajax-loader.gif")); // NOI18N
        lbloading.setText("Loading");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(txtFile, javax.swing.GroupLayout.PREFERRED_SIZE, 312, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnPilih)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton1))
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jXDatePicker1, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(CJam, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(lbloading)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jXDatePicker1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(CJam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbloading))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 216, Short.MAX_VALUE)
                .addGap(3, 3, 3)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(txtFile, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnPilih))
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnPilihActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPilihActionPerformed
        // TODO add your handling code here:
        int returnVal = jFileChooser1.showOpenDialog(this);
    if (returnVal == JFileChooser.APPROVE_OPTION) {
        file = jFileChooser1.getSelectedFile();
        txtFile.setText(file.getAbsolutePath());
//        try {
//          // What to do with the file, e.g. display it in a TextArea
//          
//        } catch (IOException ex) {
//          System.out.println("problem accessing file"+file.getAbsolutePath());
//        }
    } else {
        System.out.println("File access cancelled by user.");
    }
    }//GEN-LAST:event_btnPilihActionPerformed

    private void CJamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CJamActionPerformed
        // TODO add your handling code here:
        fillTable(TJadwal, CJam.getSelectedItem().toString(),a);
    }//GEN-LAST:event_CJamActionPerformed

    private void TJadwalMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TJadwalMouseClicked
        // TODO add your handling code here:
        int baris =TJadwal.getSelectedRow();
        
        idJadwal = (Integer)   TJadwal.getModel().getValueAt(baris, 1);
    }//GEN-LAST:event_TJadwalMouseClicked

    private void jXDatePicker1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jXDatePicker1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jXDatePicker1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(__ImportJadwal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(__ImportJadwal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(__ImportJadwal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(__ImportJadwal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new __ImportJadwal().setVisible(true);
            }
        });
    }

private class MyCustomFilter extends javax.swing.filechooser.FileFilter 
{
        @Override
        public boolean accept(File file) {
            // Allow only directories, or files with ".txt" extension
            return file.isDirectory() || file.getAbsolutePath().endsWith(".xlsx")||file.getAbsolutePath().endsWith(".xls");
        }
        @Override
        public String getDescription() {
            // This description will be displayed in the dialog,
            // hard-coded = ugly, should be done via I18N
            return "File M.S Excell (*.xlsx)";
        }
} 
    
private class ShowWaitAction extends AbstractAction 
{
       

        private ShowWaitAction(String name) {
             super(name);
        }

        @Override
        public void actionPerformed(ActionEvent evt) 
        {
            SwingWorker<Void, Void> mySwingWorker = new SwingWorker<Void, Void>()
            {
                @Override
                protected Void doInBackground() throws Exception 
                {
                    // mimic some long-running process here...
                    Thread.sleep(SLEEP_TIME);
                    importjadwal();
                    return null;
                }
            };

            Window win = SwingUtilities.getWindowAncestor((AbstractButton)evt.getSource());
            final JDialog dialog = new JDialog(win, "Dialog", ModalityType.APPLICATION_MODAL);

            mySwingWorker.addPropertyChangeListener(new PropertyChangeListener() {

            @Override
        public void propertyChange(PropertyChangeEvent evt) 
        {
            if (evt.getPropertyName().equals("state")) {
               if (evt.getNewValue() == SwingWorker.StateValue.DONE) {
                  dialog.dispose();
               }
            }
         }
        });
            
        mySwingWorker.execute();

        JProgressBar progressBar = new JProgressBar();
        progressBar.setIndeterminate(true);
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(progressBar, BorderLayout.CENTER);
        panel.add(new JLabel("Please wait......."), BorderLayout.PAGE_START);
        dialog.add(panel);
        dialog.pack();
        dialog.setLocationRelativeTo(win);
        dialog.setVisible(true);
    }
}
private class jXDatePickerListener implements ActionListener {

    public jXDatePickerListener() {
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        System.out.println("tes");
        CJam.removeAllItems();
        SwingWorker<Void, Void> mySwingWorker = new SwingWorker<Void, Void>()
            {
                @Override
                protected Void doInBackground() throws Exception 
                {
                   
                    Thread.sleep(SLEEP_TIME);
                    getJadwal();
                    return null;
                }
            };

            final JDialog dialog = new JDialog(null, "Dialog", ModalityType.APPLICATION_MODAL);

            mySwingWorker.addPropertyChangeListener(new PropertyChangeListener() {

            @Override
        public void propertyChange(PropertyChangeEvent evt) 
        {
            if (evt.getPropertyName().equals("state")) {
               if (evt.getNewValue() == SwingWorker.StateValue.DONE) {
                  dialog.dispose();
               }
            }
         }
        });
            
        mySwingWorker.execute();

        JProgressBar progressBar = new JProgressBar();
        progressBar.setIndeterminate(true);
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(progressBar, BorderLayout.CENTER);
        panel.add(new JLabel("Please wait......."), BorderLayout.PAGE_START);
        dialog.add(panel);
        dialog.pack();
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }
    
}
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox CJam;
    private javax.swing.JTable TJadwal;
    private javax.swing.JButton btnPilih;
    private javax.swing.JButton jButton1;
    private javax.swing.JFileChooser jFileChooser1;
    private javax.swing.JScrollPane jScrollPane1;
    private org.jdesktop.swingx.JXDatePicker jXDatePicker1;
    private javax.swing.JLabel lbloading;
    private javax.swing.JTextField txtFile;
    // End of variables declaration//GEN-END:variables
}
