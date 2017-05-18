/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package form.mydemik.com;

import db.mydemik.com.Koneksi;
import db.service.mydemik.com.SuratService;
import entiti.mydemik.com.Dosen;
import entiti.mydemik.com.Keperluan;
import entiti.mydemik.com.Matkul;
import entiti.mydemik.com.Ruangan;
import entiti.mydemik.com.Jadwal;
import entiti.mydemik.com.Jenisujian;
import entiti.mydemik.com.Thajaran;
import java.awt.Dialog;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;
import org.hibernate.Criteria;
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
public final class __EntryJadwal extends javax.swing.JFrame {

    /**
     * Creates new form NewJFrame
     */
    Fungsi fs=new Fungsi();
    private JPanel contentPane;
    private JLabel lblWall;
    Integer iddosen,idkat,idJadwal=0,idUjian;
    
    String xnosurat,xnama,xalamat,xprodi,xsemesterTahun,xtanggal,xisipermohonan,xhal,idjeniskpskripsi;
    Integer xnim;
    Date tgl;
    Fungsi nf=new Fungsi();
  
    SuratService  SuratService;
    
    public __EntryJadwal() throws SQLException {
        initComponents();
        fillTable(TJadwal);
        //cetak();
        Koneksi koneksi=new Koneksi();
        SuratService=new SuratService(koneksi.getConnection());
        loadMataKuliah();
        loadRuangan();
        nf.setLebarKolom(TJadwal);
        lbtanggal.setText(nf.getTanggal());
        nf.getTampilTanggal(jXDatePicker1);
        if(RUTS.isSelected())
        {
            idUjian=1;
            fillTable(TJadwal);
            nf.setLebarKolom(TJadwal);
        }
        else
        {
            idUjian=2;
            fillTable(TJadwal);
            nf.setLebarKolom(TJadwal);
        }
        tampilBtnCetak();
        btnTujuan.setVisible(false);
        
    }
    
    private void tampilBtnCetak()
    {
        if(idJadwal!=0)
        {
            btnCetak.setVisible(true);
        }
        else
        {
            btnCetak.setVisible(false);
        }
    }
    private void loadMataKuliah() throws SQLException{
        CMataKuliah.removeAllItems();
        List<Matkul> kper=SuratService.getAllMatkul();
        for(Matkul mhs:kper){
            CMataKuliah.addItem(mhs);
        }
    }
    private void loadRuangan() throws SQLException{
        CRuangan.removeAllItems();
        List<Ruangan> kper=SuratService.getAllRuangan();
        for(Ruangan prs:kper){
            CRuangan.addItem(prs);
        }
    }
    
    
    
    public void cetak()
    {
        TJadwal.addMouseListener(new MouseAdapter() {
        public void mousePressed(MouseEvent me) {
            JTable table =(JTable) me.getSource();
            Point p = me.getPoint();
            int row = table.rowAtPoint(p);
            if (me.getClickCount() == 2) {
                int index = TJadwal.getSelectedRow();
                TableModel model = TJadwal.getModel();
                String value3 = model.getValueAt(index, 3).toString();
                int dialogbtn = JOptionPane.YES_NO_OPTION;
                int dr=JOptionPane.showConfirmDialog(null, "Cetak Jadwal", "Pertanyaan", dialogbtn);
                if(dr==0)
                {
                    
                    File namafile= new File("src/laporan/mydemik/com/s_aktif_kuliah.jasper"); 
                    System.out.println("tampilkan laporan");
                    int baris =TJadwal.getSelectedRow();
                    Fungsi fs=new Fungsi();
                    xsemesterTahun=fs.getSemesterTahun();
                    xtanggal=fs.getTanggal();
                    idJadwal = (Integer)   TJadwal.getModel().getValueAt(baris, 1);
                    xnosurat=(String) TJadwal.getModel().getValueAt(baris, 2);
                    xnim= (Integer) TJadwal.getModel().getValueAt(baris, 3);
                    xnama=(String) TJadwal.getModel().getValueAt(baris, 4);
                    xprodi=(String) TJadwal.getModel().getValueAt(baris, 6);
                    xalamat=(String) TJadwal.getModel().getValueAt(baris, 8);
                   
                    
                    System.out.println(idJadwal);
                    System.out.println(xnosurat);
                    System.out.println(xnim);
                    System.out.println(xnama);
                    System.out.println(xprodi);
                    System.out.println(xalamat);

                    Map parameters = new HashMap();
                    parameters.put("xnoJadwal", xnosurat);
                    parameters.put("xnim", xnim);
                    parameters.put("xnama", xnama);
                    parameters.put("xprodi", xprodi);
                    parameters.put("xalamat", xalamat);
                    parameters.put("xsemesterTahun", xsemesterTahun);
                    parameters.put("xtanggal", xtanggal);
                    parameters.put("xisipermohonan", xisipermohonan);
                    //parameters.put("field1", xnim);
                      
                    try 
                    {
                        JasperPrint print = JasperFillManager.fillReport(namafile.getPath(), parameters, new JREmptyDataSource());
                        JasperViewer.viewReport(print);
                    } catch (JRException ex) {
                       JOptionPane.showMessageDialog(null, "Gagal Membuka Laporan" + ex,"Cetak Laporan",JOptionPane.ERROR_MESSAGE);
                    }
                }
                else
                {
                   txtKodeDosen.setText("");
                   txtKodeDosen.requestFocus();
                }
            }
        }
    });
    }

    private void alignCenter(JTable tbl,int kolom)
    {
        DefaultTableCellRenderer centerRenderer=new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        tbl.getColumnModel().getColumn(kolom).setCellRenderer(centerRenderer);
    }
    
    private void headerAlignCenter(JTable tbl)
    {
        ((DefaultTableCellRenderer)tbl.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
    }
    
    private void alignRight(JTable tbl,int kolom)
    {
        DefaultTableCellRenderer rightRenderer=new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(JLabel.RIGHT);
        tbl.getColumnModel().getColumn(kolom).setCellRenderer(rightRenderer);
    }
    
    private void hideColumn(JTable tbl,int kolom)
    {
        TableColumnModel tcm =tbl.getColumnModel();
        tcm.removeColumn(tcm.getColumn(kolom));
    }
    
    private void boldHeader(JTable tbl,int ukuran)
    {
       // tbl.getTableHeader().setFont(new Font("SanSerif",Font.ITALIC,12));
        JTableHeader header=tbl.getTableHeader();
        header.setFont(new Font("Dialog",Font.BOLD,ukuran));
    }
    private void fillTable(final JTable table) 
    {
        try
        {
            int count=1;
            SessionFactory sf=HibernateUtil.getSessionFactory();
            Session s=sf.openSession();
            Transaction tx = s.beginTransaction();
            Query q;
            if(RUTS.isSelected())
            {
                String q1="FROM Jadwal where idJenisUjian=1";
                q = s.createQuery(q1);
            }
            else
            {
                String q2="FROM Jadwal where idJenisUjian=2";
                q = s.createQuery(q2);
            }
            List resultList = q.list();
            Vector<String> tableHeaders = new Vector<String>();
            Vector tableData = new Vector();
            tableHeaders.add("No");
            tableHeaders.add("idJadwal");
            tableHeaders.add("No.Jadwal");
            tableHeaders.add("NIM");
            tableHeaders.add("Nama");
            tableHeaders.add("Telepon");
            tableHeaders.add("Prodi");
            tableHeaders.add("Tanggal Jadwal"); 
            tableHeaders.add("Alamat");
            
            if(RUAS.isSelected())
            {
                tableHeaders.add("Tujuan");
            }
            else
            {
                tableHeaders.add("Keperluan");
            }
            for(Object o : resultList) 
            {
                Jadwal sr = (Jadwal)o;
                Vector<Object> oneRow = new Vector<Object>();
                oneRow.add(count);
                oneRow.add(sr.getIdJadwal());
                count++;
                tableData.add(oneRow);
            }
            TJadwal.setModel(new DefaultTableModel(tableData, tableHeaders));
            alignCenter(TJadwal, 0);
            alignCenter(TJadwal, 3);
            alignCenter(TJadwal, 7);
            headerAlignCenter(TJadwal);
//            hideColumn(TJadwal, 10);
            hideColumn(TJadwal, 1);
            hideColumn(TJadwal, 2);
            boldHeader(TJadwal,12);
            s.flush();
            tx.commit();
            s.close();
            //TJadwal.setEditingRow(false);
            

            TJadwal.setDefaultEditor(Object.class, null);


           
        }
        catch (ClassCastException e) 
        {
                e.printStackTrace();
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

                Query q = s.createQuery("Delete From Jadwal where idJadwal=:idJadwal");
                q.setParameter("idJadwal", hps);
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
 
    public final void cari (final String kodeDosen) 
    {
        try
        {
            SessionFactory sf=HibernateUtil.getSessionFactory();
            Session s=sf.openSession();
            Transaction tx = s.beginTransaction();
           // String sql="FROM Barang b where b.kodeBarang=:kodeBarang";
            String sql="select * from Dosen where kodeDosen=:kodeDosen";
            Query q = s.createSQLQuery(sql);
            q.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
            q.setParameter("kodeDosen", kodeDosen);
            List mhs=q.list();
            if(txtKodeDosen.getText().equals(""))
            {
                __ListDosen f =new __ListDosen();
                f.setVisible(rootPaneCheckingEnabled);
                this.setVisible(false);
            }
            else if (mhs.isEmpty())
            {
                int dialogbtn = JOptionPane.YES_NO_OPTION;
                int dr=JOptionPane.showConfirmDialog(this, "Form Pencarian.", "Pertanyaan", dialogbtn);
                if(dr==0)
                {
                    __ListDosen f =new __ListDosen();
                    f.setModalExclusionType(Dialog.ModalExclusionType.NO_EXCLUDE);
                    f.setVisible(rootPaneCheckingEnabled);
                    f.txtKodeDosen.setText("");
                    f.txtKodeDosen.requestFocus();
                    this.setVisible(false);
                }
                else
                {
                   txtKodeDosen.setText("");
                   txtKodeDosen.requestFocus();
               }
                
            }
            else
            {
                for (Object br : mhs )
                {
                    Map row = (Map)br;
                    iddosen=Integer.parseInt(row.get("idDosen").toString());
                    lbNim.setText(row.get("kodeDosen").toString());
                    lbNama.setText(row.get("namaDosen").toString());
                    lbTlp.setText(row.get("hp").toString());
                }
                s.flush();
                tx.commit();
                s.close();
            }
        }
        catch (Exception e) 
        {
                System.out.println(e);
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

        buttonGroupRadioKeterangan = new javax.swing.ButtonGroup();
        jXDatePicker1 = new org.jdesktop.swingx.JXDatePicker();
        btnCari = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        lbNim = new javax.swing.JLabel();
        lbNama = new javax.swing.JLabel();
        lbTlp = new javax.swing.JLabel();
        CMataKuliah = new javax.swing.JComboBox();
        RUTS = new javax.swing.JRadioButton();
        RUAS = new javax.swing.JRadioButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        btnHapus = new javax.swing.JButton();
        btnSimpan = new javax.swing.JButton();
        txtKodeDosen = new javax.swing.JTextField();
        lbtanggal = new javax.swing.JLabel();
        CRuangan = new javax.swing.JComboBox();
        lbtujuan = new javax.swing.JLabel();
        lbKeperluan = new javax.swing.JLabel();
        lbtanggal1 = new javax.swing.JLabel();
        btnCetak = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        TJadwal = new javax.swing.JTable();
        btnClose = new javax.swing.JButton();
        btnClear = new javax.swing.JButton();
        btnTujuan = new javax.swing.JButton();
        mjam = new javax.swing.JSpinner();
        mmenit = new javax.swing.JSpinner();
        sjam = new javax.swing.JSpinner();
        smenit = new javax.swing.JSpinner();
        lbtujuan1 = new javax.swing.JLabel();
        lbtujuan2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));
        setFocusTraversalPolicyProvider(true);
        setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        setLocationByPlatform(true);
        setResizable(false);
        setSize(new java.awt.Dimension(0, 0));

        btnCari.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        btnCari.setText("Cari");
        btnCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCariActionPerformed(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N

        jPanel2.setBackground(new java.awt.Color(51, 0, 51));

        jLabel1.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Data Mahasiswa");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel6.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel6.setText("NIM :");

        jLabel7.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel7.setText("Nama :");

        jLabel9.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel9.setText("Telepon :");

        lbNim.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        lbNim.setText("#####");

        lbNama.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        lbNama.setText("#####");

        lbTlp.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        lbTlp.setText("#####");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(99, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel7)
                    .addComponent(jLabel6)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(lbNama, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbTlp, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbNim, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(23, 23, 23))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(lbNim, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbNama, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbTlp, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21))
        );

        CMataKuliah.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N

        buttonGroupRadioKeterangan.add(RUTS);
        RUTS.setSelected(true);
        RUTS.setText("UTS");
        RUTS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RUTSActionPerformed(evt);
            }
        });

        buttonGroupRadioKeterangan.add(RUAS);
        RUAS.setText("UAS");
        RUAS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RUASActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel3.setText("Tanggal Ujian :");

        jLabel4.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel4.setText("Dosen :");

        jLabel5.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel5.setText("Keterangan :");

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

        txtKodeDosen.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        txtKodeDosen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtKodeDosenActionPerformed(evt);
            }
        });

        lbtanggal.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lbtanggal.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbtanggal.setText("Tanggal : ");

        CRuangan.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        CRuangan.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                CRuanganItemStateChanged(evt);
            }
        });
        CRuangan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CRuanganActionPerformed(evt);
            }
        });

        lbtujuan.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        lbtujuan.setText("Ruangan :");

        lbKeperluan.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        lbKeperluan.setText("Matakuliah :");

        lbtanggal1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lbtanggal1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lbtanggal1.setText("FORM INI DIGUNAKAN UNTUK CETAK SURAT AKTIF DAN PERMOHONAN KP & SKRIPSI");

        btnCetak.setText("Cetak");
        btnCetak.setPreferredSize(new java.awt.Dimension(69, 25));
        btnCetak.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCetakActionPerformed(evt);
            }
        });

        TJadwal.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
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

        btnClose.setText("Keluar");
        btnClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCloseActionPerformed(evt);
            }
        });

        btnClear.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        btnClear.setText("\"#\"");
        btnClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearActionPerformed(evt);
            }
        });

        btnTujuan.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        btnTujuan.setText("Baru");
        btnTujuan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTujuanActionPerformed(evt);
            }
        });

        lbtujuan1.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        lbtujuan1.setText("Jam :");

        lbtujuan2.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        lbtujuan2.setText("s/d");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(lbtanggal1, javax.swing.GroupLayout.PREFERRED_SIZE, 653, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lbtanggal, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(50, 50, 50)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtKodeDosen, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(8, 8, 8)
                                .addComponent(btnCari)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnClear))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jXDatePicker1, javax.swing.GroupLayout.PREFERRED_SIZE, 258, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(lbKeperluan)
                                    .addComponent(jLabel5)
                                    .addComponent(lbtujuan))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(2, 2, 2)
                                        .addComponent(CMataKuliah, javax.swing.GroupLayout.PREFERRED_SIZE, 258, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(RUTS)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(RUAS))
                                    .addGroup(layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(CRuangan, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnSimpan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnTujuan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnHapus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnCetak, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnClose)))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(103, 103, 103)
                .addComponent(lbtujuan1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(mjam, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(mmenit)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbtujuan2, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(sjam, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(smenit, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(592, 592, 592))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbtanggal1)
                    .addComponent(lbtanggal))
                .addGap(13, 13, 13)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtKodeDosen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnCari)
                            .addComponent(btnClear)
                            .addComponent(btnTujuan)
                            .addComponent(jLabel4))
                        .addGap(3, 3, 3)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jXDatePicker1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnSimpan)
                            .addComponent(jLabel3))
                        .addGap(6, 6, 6)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lbKeperluan)
                                .addGap(12, 12, 12)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel5)
                                    .addComponent(RUTS)
                                    .addComponent(RUAS))
                                .addGap(1, 1, 1)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lbtujuan)
                                    .addComponent(CRuangan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(CMataKuliah, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnHapus))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnCetak, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(4, 4, 4)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(mjam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(mmenit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sjam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(smenit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbtujuan2)
                    .addComponent(lbtujuan1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 267, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnClose))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCariActionPerformed
        try 
        {
            
            cari(txtKodeDosen.getText());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e);
        }
    }//GEN-LAST:event_btnCariActionPerformed

    private void btnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanActionPerformed
        // TODO add your handling code here:
        String jamawal,menitawal,jamakhir,menitakhir;
        jamawal= Integer.toString((int) mjam.getValue());
        menitawal= Integer.toString((int) mmenit.getValue());
        jamakhir=Integer.toString((int)sjam.getValue());
        menitakhir=Integer.toString((int)smenit.getValue());
        
        String jam;
        jam=jamawal+":"+menitawal+"-"+jamakhir+":"+menitakhir;
        
        SessionFactory sf=HibernateUtil.getSessionFactory();
        Session s=sf.openSession();
        Jadwal jd=new Jadwal();
        Thajaran t=new Thajaran();
        Matkul mtk=new Matkul();
        Ruangan rng=new Ruangan();
        Dosen dsn=new Dosen();
        Jenisujian jns=new Jenisujian();
        Fungsi fs=new Fungsi();
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        System.out.println(dateFormat.format(cal.getTime())); 
        Matkul m=(Matkul) CMataKuliah.getSelectedItem();
        Ruangan r = (Ruangan) CRuangan.getSelectedItem();
        jd.setMatkul(mtk);
        jd.setRuangan(rng);
        jd.setDosen(dsn);
        jd.setThajaran(t);
        t.setIdThajaran(fs.getTa());
        dsn.setIdDosen(iddosen);
        mtk.setIdMatkul(m.getIdMatkul());
        rng.setIdRuangan(r.getIdRuangan());
        if(jXDatePicker1.getDate()==null)
        {
            try 
            {
                tgl=dateFormat.parse(dateFormat.format(cal.getTime()).toString());
                jd.setTanggal(tgl);
                jXDatePicker1.setDate(tgl);
            } catch (ParseException ex) 
            {
                System.out.println(ex);
            }
        }
        else
        {
              jd.setTanggal(jXDatePicker1.getDate());
        }
        //Date sekarang =new Date();
        jd.setJam(jam);
        t.setIdThajaran(fs.getTa());
        jd.setJenisujian(jns);
        jns.setIdJenisUjian(idUjian);
        Transaction tx=s.beginTransaction();
        s.saveOrUpdate(jd);
        tx.commit();
        s.flush();
        s.close();
        fillTable(TJadwal);
        nf.setLebarKolom(TJadwal);
    }//GEN-LAST:event_btnSimpanActionPerformed

    private void txtKodeDosenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtKodeDosenActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtKodeDosenActionPerformed

    private void btnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusActionPerformed
        // TODO add your handling code here:
        Hapus(idJadwal);
        fillTable(TJadwal);
        nf.setLebarKolom(TJadwal);
    }//GEN-LAST:event_btnHapusActionPerformed

    private void CRuanganItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_CRuanganItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_CRuanganItemStateChanged

    private void CRuanganActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CRuanganActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_CRuanganActionPerformed

    private void TJadwalMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TJadwalMouseClicked
        int baris =TJadwal.getSelectedRow();
        
        idJadwal = (Integer)   TJadwal.getModel().getValueAt(baris, 1);
        tampilBtnCetak();
//        xnosurat=(String) TJadwal.getModel().getValueAt(baris, 2);
//        xnim= (Integer) TJadwal.getModel().getValueAt(baris, 3);
//        xnama=(String) TJadwal.getModel().getValueAt(baris, 4);
//        xprodi=(String) TJadwal.getModel().getValueAt(baris, 6);
//        xalamat=(String) TJadwal.getModel().getValueAt(baris, 8);

//        System.out.println(idJadwal);
//        System.out.println(xnosurat);
//        System.out.println(xnim);
//        System.out.println(xnama);
//        System.out.println(xprodi);
//        System.out.println(xalamat);
    }//GEN-LAST:event_TJadwalMouseClicked

    private void RUASActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RUASActionPerformed
        idJadwal=2;
        fillTable(TJadwal);
        nf.setLebarKolom(TJadwal);
    }//GEN-LAST:event_RUASActionPerformed

    private void RUTSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RUTSActionPerformed
        idJadwal=1;
        fillTable(TJadwal);
        nf.setLebarKolom(TJadwal);
    }//GEN-LAST:event_RUTSActionPerformed

    private void btnCetakActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCetakActionPerformed
//        // TODO add your handling code here:
//        
//        if (idJadwal==0)
//        {
//            JOptionPane.showMessageDialog(this, "Pilih Jadwal yang akan dicetak");
//            
//        }
//        else if(idJadwal!=0 && RUTS.isSelected())
//        {
//            try (InputStream inputStream = __EntryJadwal.class.getClassLoader().getResourceAsStream("laporan/header.jpg")) 
//            {
//                System.out.println("tampilkan laporan");
//                xsemesterTahun=fs.getSemesterTahun();
//                xtanggal=fs.getTanggal();
//            
//                Map parameters = new HashMap();
////            
//                fs.getJadwal(idJadwal);
//            
//                parameters.put("header", ImageIO.read(new ByteArrayInputStream(JRLoader.loadBytes(inputStream))));
//                parameters.put("xsemesterTahun", xsemesterTahun);
//                parameters.put("xtanggal", xtanggal);
//                parameters.put("xnoJadwal", fs.xnosurat);
//                parameters.put("xnim", fs.xnim);
//                parameters.put("xnama", fs.xnama);
//                parameters.put("xprodi", fs.xprodi);
//                parameters.put("xalamat", fs.xalamat);
//                JasperPrint print = JasperFillManager.fillReport(getClass().getResourceAsStream("/laporan/s_aktif_kuliah.jasper"), parameters, new JREmptyDataSource());
//                JasperExportManager.exportReportToPdfFile(print, "D:/aktif_kuliah_"+fs.xnim+"_"+fs.xnama+".pdf");
//                JasperViewer.viewReport(print,false);
//            } catch (JRException | IOException ex) 
//            {
//               JOptionPane.showMessageDialog(null, "Gagal Membuka Laporan" + ex,"Cetak Laporan",JOptionPane.ERROR_MESSAGE);
//            }
//        }
//        else if(idJadwal!=0 && RUAS.isSelected())
//        {     
//           try (InputStream inputStream = __EntryJadwal.class.getClassLoader().getResourceAsStream("laporan/header.jpg")) 
//           {
//            //URL namafile=FJadwal.class.getResource("/laporan/s_kp_skripsi.jasper");
//            xsemesterTahun=fs.getSemesterTahun();
//            xtanggal=fs.getTanggal();
//            
//            fs.getJadwal(idJadwal);
//            Map parameters = new HashMap();
//            parameters.put("header", ImageIO.read(new ByteArrayInputStream(JRLoader.loadBytes(inputStream))));
//            parameters.put("xnoJadwal", fs.xnosurat);
//            parameters.put("xnim", fs.xnim);
//            parameters.put("xnama", fs.xnama);
//            parameters.put("xprodi", fs.xprodi);
//            parameters.put("xalamat", fs.xalamat);
//            parameters.put("xsemesterTahun", xsemesterTahun);
//            parameters.put("xtanggal", xtanggal);
//            parameters.put("xjudul", fs.xjudul);
//            parameters.put("xperusahaan", fs.xperusahaan);
//            xisipermohonan=fs.getPermohonan_kp_skripsi(fs.xjkps,fs.xjudul ,fs.xperusahaan);
//            parameters.put("xisipermohonan", xisipermohonan);
//            xhal="Permohonan Penelitian "+fs.xjkps;
//            parameters.put("xhal",xhal);
//                JasperPrint print = JasperFillManager.fillReport(getClass().getResourceAsStream("/laporan/s_kp_skripsi.jasper"), parameters, new JREmptyDataSource());
//                JasperExportManager.exportReportToPdfFile(print, "D:/aktif_kuliah_"+fs.xnim+"_"+fs.xnama+".pdf");
//                JasperViewer.viewReport(print,false);
//            } catch (JRException | IOException ex) {
//               JOptionPane.showMessageDialog(null, "Gagal Membuka Laporan" + ex,"Cetak Laporan",JOptionPane.ERROR_MESSAGE);
//            }
//        }
       
        
    }//GEN-LAST:event_btnCetakActionPerformed

    private void btnCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCloseActionPerformed
        // TODO add your handling code here:
        dispose();
    }//GEN-LAST:event_btnCloseActionPerformed

    private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearActionPerformed
        // TODO add your handling code here:
        txtKodeDosen.setText("");
        txtKodeDosen.requestFocus();
    }//GEN-LAST:event_btnClearActionPerformed

    private void btnTujuanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTujuanActionPerformed
        // TODO add your handling code here:
        
        
    }//GEN-LAST:event_btnTujuanActionPerformed

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
            java.util.logging.Logger.getLogger(__EntryJadwal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(__EntryJadwal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(__EntryJadwal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(__EntryJadwal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new __EntryJadwal().setVisible(true);
                } catch (SQLException ex) {
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox CMataKuliah;
    public javax.swing.JComboBox CRuangan;
    private javax.swing.JRadioButton RUAS;
    private javax.swing.JRadioButton RUTS;
    public javax.swing.JTable TJadwal;
    private javax.swing.JButton btnCari;
    private javax.swing.JButton btnCetak;
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnClose;
    private javax.swing.JButton btnHapus;
    private javax.swing.JButton btnSimpan;
    private javax.swing.JButton btnTujuan;
    private javax.swing.ButtonGroup buttonGroupRadioKeterangan;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private org.jdesktop.swingx.JXDatePicker jXDatePicker1;
    private javax.swing.JLabel lbKeperluan;
    private javax.swing.JLabel lbNama;
    private javax.swing.JLabel lbNim;
    private javax.swing.JLabel lbTlp;
    private javax.swing.JLabel lbtanggal;
    private javax.swing.JLabel lbtanggal1;
    private javax.swing.JLabel lbtujuan;
    private javax.swing.JLabel lbtujuan1;
    private javax.swing.JLabel lbtujuan2;
    private javax.swing.JSpinner mjam;
    private javax.swing.JSpinner mmenit;
    private javax.swing.JSpinner sjam;
    private javax.swing.JSpinner smenit;
    public javax.swing.JTextField txtKodeDosen;
    // End of variables declaration//GEN-END:variables
}
