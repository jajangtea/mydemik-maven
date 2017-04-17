/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package form.mydemik.com;

import db.mydemik.com.Koneksi;
import db.service.mydemik.com.SuratService;
import entiti.mydemik.com.Jenissurat;

import entiti.mydemik.com.Kategori;
import entiti.mydemik.com.Keperluan;
import entiti.mydemik.com.Mahasiswa;
import entiti.mydemik.com.Perusahaan;
import entiti.mydemik.com.Surat;
import entiti.mydemik.com.Thajaran;
import java.awt.Dialog;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.net.URI;
import java.net.URL;
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
import net.sf.jasperreports.engine.JasperExportManager;
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
public final class FSurat extends javax.swing.JFrame {

    /**
     * Creates new form NewJFrame
     */
    Fungsi fs=new Fungsi();
    private JPanel contentPane;
    private JLabel lblWall;
    Integer idmhs,idkat,idSurat=0;
    
    String xnosurat,xnama,xalamat,xprodi,xsemesterTahun,xtanggal,xisipermohonan,xhal,idjeniskpskripsi;
    Integer xnim;
    Date tgl;
    Fungsi nf=new Fungsi();
  
    SuratService  SuratService;
    
    public FSurat() throws SQLException {
        initComponents();
        fillTable(jTSurat);
        //cetak();
        Koneksi koneksi=new Koneksi();
        SuratService=new SuratService(koneksi.getConnection());
        loadjenis();
        jCJenisKPSkripsi.setVisible(false);
        loadkeperluan();
        loadperusahaan();
        kodeOtomatis();
        nf.setLebarKolom(jTSurat);
        lbtanggal.setText(nf.getTanggal());
        nf.getTampilTanggal(jXDatePicker1);
        jCJenisKPSkripsi.addActionListener(new ComboBoxListener());
        if(radakt.isSelected())
        {
            idSurat=0;
            jCJenisKPSkripsi.setVisible(false);
            jCPerusahaan.setVisible(false);
            lbtujuan.setVisible(false);
            fillTable(jTSurat);
            nf.setLebarKolom(jTSurat);
            jCKeperluan.setVisible(true);
            lbKeperluan.setVisible(true);
            txtJudul.setVisible(false);
            lbjudul.setVisible(false);
        }
        tampilBtnCetak();
        
    }
    
    private void tampilBtnCetak()
    {
        if(idSurat!=0)
        {
            btnCetak.setVisible(true);
        }
        else
        {
            btnCetak.setVisible(false);
        }
    }
    
    private void kodeOtomatis()
    {
        try
        {
            SessionFactory sf=HibernateUtil.getSessionFactory();
            Session s=sf.openSession();
            Transaction tx = s.beginTransaction();
            String sql="select max(Left(noSurat,3)) from Surat";
            Query q = s.createSQLQuery(sql);
            if(q.uniqueResult()==null)
            {
                Fungsi f=new Fungsi();
                int bulan=f.getBulan();
                String rom=f.hurufromawai(bulan);
                txtNomor.setText("001"+"/Puket-I/"+rom+"/"+f.getTahun());
            }
            else
            {
                System.out.println(q.uniqueResult());
                int i=Integer.parseInt(q.uniqueResult().toString());
                String no = String.valueOf(i+1);
                int noLong = no.length();
                for(int a=0;a<3-noLong;a++)
                {
                    no = "0"+no;
                }
                
                Fungsi f=new Fungsi();
                int bulan=f.getBulan();
                String rom=f.hurufromawai(bulan);
                txtNomor.setText(no+"/Sket/Puket-I/"+rom+"/"+f.getTahun());
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

    private void loadjenis() throws SQLException{
        jCJenisKPSkripsi.removeAllItems();
        List<Jenissurat> jnss=SuratService.getAllJenis();
        for(Jenissurat mhs:jnss){
            jCJenisKPSkripsi.addItem(mhs);
        }
    }
    private void loadkeperluan() throws SQLException{
        jCKeperluan.removeAllItems();
        List<Keperluan> kper=SuratService.getAllKeperluan();
        for(Keperluan mhs:kper){
            jCKeperluan.addItem(mhs);
        }
    }
    private void loadperusahaan() throws SQLException{
        jCPerusahaan.removeAllItems();
        List<Perusahaan> kper=SuratService.getAllPerusahaan();
        for(Perusahaan prs:kper){
            jCPerusahaan.addItem(prs);
        }
    }
    
    public void cetak()
    {
        jTSurat.addMouseListener(new MouseAdapter() {
        public void mousePressed(MouseEvent me) {
            JTable table =(JTable) me.getSource();
            Point p = me.getPoint();
            int row = table.rowAtPoint(p);
            if (me.getClickCount() == 2) {
                int index = jTSurat.getSelectedRow();
                TableModel model = jTSurat.getModel();
                String value3 = model.getValueAt(index, 3).toString();
                int dialogbtn = JOptionPane.YES_NO_OPTION;
                int dr=JOptionPane.showConfirmDialog(null, "Cetak Surat", "Pertanyaan", dialogbtn);
                if(dr==0)
                {
                    
                    File namafile= new File("src/laporan/mydemik/com/s_aktif_kuliah.jasper"); 
                    System.out.println("tampilkan laporan");
                    int baris =jTSurat.getSelectedRow();
                    Fungsi fs=new Fungsi();
                    xsemesterTahun=fs.getSemesterTahun();
                    xtanggal=fs.getTanggal();
                    xisipermohonan=fs.getPermohonan_kp_skripsi(xnama, jCJenisKPSkripsi.getSelectedItem().toString(), xnama);
                    idSurat = (Integer)   jTSurat.getModel().getValueAt(baris, 1);
                    xnosurat=(String) jTSurat.getModel().getValueAt(baris, 2);
                    xnim= (Integer) jTSurat.getModel().getValueAt(baris, 3);
                    xnama=(String) jTSurat.getModel().getValueAt(baris, 4);
                    xprodi=(String) jTSurat.getModel().getValueAt(baris, 6);
                    xalamat=(String) jTSurat.getModel().getValueAt(baris, 8);
                   
                    
                    System.out.println(idSurat);
                    System.out.println(xnosurat);
                    System.out.println(xnim);
                    System.out.println(xnama);
                    System.out.println(xprodi);
                    System.out.println(xalamat);

                    Map parameters = new HashMap();
                    parameters.put("xnoSurat", xnosurat);
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
                   txtNim.setText("");
                   txtNim.requestFocus();
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
    public void loacComboKeperluan(JComboBox jk)
    {
        try {
            SessionFactory sf=HibernateUtil.getSessionFactory();
            Session s=sf.openSession();
            Transaction tx = s.beginTransaction();
            Query q = s.createQuery("FROM Keperluan");
            List resultList = q.list();
            for(Object o : resultList) 
            {
                Keperluan sr = (Keperluan)o;
                jk.addItem(sr.getKeperluan());
            }
            s.flush();
            tx.commit();
            s.close();
            
        } catch (Exception e) {
            System.out.println(e);
        }
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
            if(radakt.isSelected())
            {
                String q1="FROM Surat where idJenis=1";
                q = s.createQuery(q1);
            }
            else
            {
                String q2="FROM Surat where idJenis<>1";
                q = s.createQuery(q2);
            }
            List resultList = q.list();
            Vector<String> tableHeaders = new Vector<String>();
            Vector tableData = new Vector();
            tableHeaders.add("No");
            tableHeaders.add("idSurat");
            tableHeaders.add("No.Surat");
            tableHeaders.add("NIM");
            tableHeaders.add("Nama");
            tableHeaders.add("Telepon");
            tableHeaders.add("Prodi");
            tableHeaders.add("Tanggal Surat"); 
            tableHeaders.add("Alamat");
            
            if(radkpskripsi.isSelected())
            {
                tableHeaders.add("Tujuan");
            }
            else
            {
                tableHeaders.add("Keperluan");
            }
            for(Object o : resultList) 
            {
                Surat sr = (Surat)o;
                Vector<Object> oneRow = new Vector<Object>();
                oneRow.add(count);
                oneRow.add(sr.getIdSurat());
                oneRow.add(sr.getNoSurat());
                oneRow.add(sr.getMahasiswa().getNim());
                oneRow.add(sr.getMahasiswa().getNama());
                oneRow.add(sr.getMahasiswa().getTlp());
                oneRow.add(sr.getMahasiswa().getProdi().getNamaProdi());
                oneRow.add(sr.getTanggalSurat().toString());
                oneRow.add(sr.getMahasiswa().getAlamat());
                if(radkpskripsi.isSelected())
                {
                    oneRow.add(sr.getPerusahaan().getNamaPerusahaan());
                }
                else
                {
                     oneRow.add(sr.getKeperluan().getKeperluan());
                }
                count++;
                tableData.add(oneRow);
            }
            jTSurat.setModel(new DefaultTableModel(tableData, tableHeaders));
            alignCenter(jTSurat, 0);
            alignCenter(jTSurat, 3);
            alignCenter(jTSurat, 7);
            headerAlignCenter(jTSurat);
//            hideColumn(jTSurat, 10);
            hideColumn(jTSurat, 1);
            hideColumn(jTSurat, 2);
            boldHeader(jTSurat,12);
            s.flush();
            tx.commit();
            s.close();
            //jTSurat.setEditingRow(false);
            

            jTSurat.setDefaultEditor(Object.class, null);


           
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

                Query q = s.createQuery("Delete From Surat where idSurat=:idSurat");
                q.setParameter("idSurat", hps);
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
 
    public final void cari (final String nim) 
    {
        try
        {
            SessionFactory sf=HibernateUtil.getSessionFactory();
            Session s=sf.openSession();
            Transaction tx = s.beginTransaction();
           // String sql="FROM Barang b where b.kodeBarang=:kodeBarang";
            String sql="select * from mahasiswa where nim=:nim";
            Query q = s.createSQLQuery(sql);
            q.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
            q.setParameter("nim", nim);
            List mhs=q.list();
            if(txtNim.getText().equals(""))
            {
                FMhs f =new FMhs();
                f.setVisible(rootPaneCheckingEnabled);
                this.setVisible(false);
            }
            else if (mhs.isEmpty())
            {
                int dialogbtn = JOptionPane.YES_NO_OPTION;
                int dr=JOptionPane.showConfirmDialog(this, "Form Pencarian.", "Pertanyaan", dialogbtn);
                if(dr==0)
                {
                    FMhs f =new FMhs();
                    f.setModalExclusionType(Dialog.ModalExclusionType.NO_EXCLUDE);
                    f.setVisible(rootPaneCheckingEnabled);
                    f.txtCari.setText("");
                    f.txtCari.requestFocus();
                    this.setVisible(false);
                }
                else
                {
                   txtNim.setText("");
                   txtNim.requestFocus();
               }
                
            }
            else
            {
                for (Object br : mhs )
                {
                    Map row = (Map)br;
                    idmhs=Integer.parseInt(row.get("idMahasiswa").toString());
                    lbNim.setText(row.get("nim").toString());
                    lbNama.setText(row.get("nama").toString());
                    lbTlp.setText(row.get("tlp").toString());
                    lbAlamat.setText(row.get("alamat").toString());
                    
                   // a=Float.parseFloat(row.get("Harga").toString());
                    //lbHarga.setText(NumberFormat.getNumberInstance().format(a));
                    //txtJml.requestFocus();
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
        jCJenisKPSkripsi = new javax.swing.JComboBox();
        jXDatePicker1 = new org.jdesktop.swingx.JXDatePicker();
        btnCari = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        lbNim = new javax.swing.JLabel();
        lbNama = new javax.swing.JLabel();
        lbTlp = new javax.swing.JLabel();
        lbAlamat = new javax.swing.JLabel();
        jCKeperluan = new javax.swing.JComboBox();
        radakt = new javax.swing.JRadioButton();
        radkpskripsi = new javax.swing.JRadioButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        btnHapus = new javax.swing.JButton();
        btnSimpan = new javax.swing.JButton();
        txtNim = new javax.swing.JTextField();
        lbtanggal = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jCPerusahaan = new javax.swing.JComboBox();
        lbtujuan = new javax.swing.JLabel();
        lbKeperluan = new javax.swing.JLabel();
        txtNomor = new javax.swing.JTextField();
        btnPDF = new javax.swing.JButton();
        lbtanggal1 = new javax.swing.JLabel();
        btnCetak = new javax.swing.JButton();
        txtJudul = new javax.swing.JTextField();
        lbjudul = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTSurat = new javax.swing.JTable();
        btnClose = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));
        setFocusTraversalPolicyProvider(true);
        setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        setLocationByPlatform(true);
        setResizable(false);
        setSize(new java.awt.Dimension(0, 0));

        jCJenisKPSkripsi.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jCJenisKPSkripsi.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jCJenisKPSkripsiItemStateChanged(evt);
            }
        });
        jCJenisKPSkripsi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCJenisKPSkripsiActionPerformed(evt);
            }
        });

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

        jLabel8.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel8.setText("Alamat :");

        jLabel9.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel9.setText("Telepon :");

        lbNim.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        lbNim.setText("#####");

        lbNama.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        lbNama.setText("#####");

        lbTlp.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        lbTlp.setText("#####");

        lbAlamat.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        lbAlamat.setText("#####");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel8)
                    .addComponent(jLabel7)
                    .addComponent(jLabel6)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(lbNama, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbTlp, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbAlamat, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbNim, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbAlamat, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jCKeperluan.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N

        buttonGroupRadioKeterangan.add(radakt);
        radakt.setSelected(true);
        radakt.setText("Aktif Kuliah");
        radakt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radaktActionPerformed(evt);
            }
        });

        buttonGroupRadioKeterangan.add(radkpskripsi);
        radkpskripsi.setText("Permohonan KP & TA");
        radkpskripsi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radkpskripsiActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel3.setText("Tanggal Surat :");

        jLabel4.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel4.setText("Mahasiswa :");

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

        txtNim.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        txtNim.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNimActionPerformed(evt);
            }
        });

        lbtanggal.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lbtanggal.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbtanggal.setText("Tanggal : ");

        jLabel10.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel10.setText("Nomor Surat :");

        jCPerusahaan.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jCPerusahaan.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jCPerusahaanItemStateChanged(evt);
            }
        });
        jCPerusahaan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCPerusahaanActionPerformed(evt);
            }
        });

        lbtujuan.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        lbtujuan.setText("Tujuan :");

        lbKeperluan.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        lbKeperluan.setText("Keperluan :");

        txtNomor.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N

        btnPDF.setText("PDF");
        btnPDF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPDFActionPerformed(evt);
            }
        });

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

        lbjudul.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        lbjudul.setText("Judul KP & Skripsi :");

        jTSurat.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jTSurat.setModel(new javax.swing.table.DefaultTableModel(
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
        jTSurat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTSuratMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTSurat);

        btnClose.setText("Keluar");
        btnClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCloseActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(50, 50, 50)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(14, 14, 14)
                                .addComponent(jLabel4))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(7, 7, 7)
                                .addComponent(jLabel10))
                            .addComponent(jLabel3)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(21, 21, 21)
                                .addComponent(lbKeperluan))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(13, 13, 13)
                                .addComponent(jLabel5)))
                        .addGap(4, 4, 4)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txtNim, javax.swing.GroupLayout.PREFERRED_SIZE, 276, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(6, 6, 6)
                                .addComponent(btnCari, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(txtNomor, javax.swing.GroupLayout.PREFERRED_SIZE, 383, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jXDatePicker1, javax.swing.GroupLayout.PREFERRED_SIZE, 383, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jCKeperluan, javax.swing.GroupLayout.PREFERRED_SIZE, 383, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(radakt)
                                .addGap(2, 2, 2)
                                .addComponent(radkpskripsi)
                                .addGap(2, 2, 2)
                                .addComponent(jCJenisKPSkripsi, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(12, 12, 12)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(22, 22, 22)
                                .addComponent(lbtanggal1, javax.swing.GroupLayout.PREFERRED_SIZE, 653, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(40, 40, 40)
                                .addComponent(lbtanggal, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(91, 91, 91)
                                .addComponent(lbtujuan)
                                .addGap(4, 4, 4)
                                .addComponent(jCPerusahaan, javax.swing.GroupLayout.PREFERRED_SIZE, 383, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(139, 139, 139)
                                .addComponent(btnPDF, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(6, 6, 6)
                                .addComponent(btnSimpan, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(6, 6, 6)
                                .addComponent(btnHapus, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(6, 6, 6)
                                .addComponent(btnCetak, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(24, 24, 24)
                                .addComponent(lbjudul)
                                .addGap(4, 4, 4)
                                .addComponent(txtJudul, javax.swing.GroupLayout.PREFERRED_SIZE, 780, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 923, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(btnClose)))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbtanggal1)
                    .addComponent(lbtanggal))
                .addGap(13, 13, 13)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(jLabel4)
                        .addGap(13, 13, 13)
                        .addComponent(jLabel10)
                        .addGap(11, 11, 11)
                        .addComponent(jLabel3)
                        .addGap(13, 13, 13)
                        .addComponent(lbKeperluan)
                        .addGap(13, 13, 13)
                        .addComponent(jLabel5))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(1, 1, 1)
                                .addComponent(txtNim, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(btnCari))
                        .addGap(5, 5, 5)
                        .addComponent(txtNomor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(jXDatePicker1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(jCKeperluan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(8, 8, 8)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(radakt)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(1, 1, 1)
                                .addComponent(radkpskripsi))
                            .addComponent(jCJenisKPSkripsi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(11, 11, 11)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbjudul)
                    .addComponent(txtJudul, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(lbtujuan))
                    .addComponent(jCPerusahaan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(btnPDF, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(btnSimpan))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(btnHapus))
                    .addComponent(btnCetak, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnClose)
                .addGap(0, 0, 0))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCariActionPerformed
        try 
        {
           
            cari(txtNim.getText());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e);
        }
    }//GEN-LAST:event_btnCariActionPerformed

    private void btnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanActionPerformed
        // TODO add your handling code here:
        SessionFactory sf=HibernateUtil.getSessionFactory();
        Session s=sf.openSession();
        Surat sr=new Surat();
        Jenissurat jns=new Jenissurat();
        Mahasiswa mhs = new Mahasiswa();
        Keperluan prl = new Keperluan();
        Perusahaan prs=new Perusahaan();
        Thajaran t=new Thajaran();
        Kategori kat=new Kategori();
        Fungsi fs=new Fungsi();
        
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        System.out.println(dateFormat.format(cal.getTime())); 
        
        
        Keperluan k=(Keperluan) jCKeperluan.getSelectedItem();
        Jenissurat j=(Jenissurat) jCJenisKPSkripsi.getSelectedItem();
        Perusahaan p = (Perusahaan) jCPerusahaan.getSelectedItem();
        
        sr.setJenissurat(jns);
        sr.setKeperluan(prl);
        sr.setPerusahaan(prs);
        
        jns.setIdJenis(j.getIdJenis());
        prl.setIdKeperluan(k.getIdKeperluan());
        prs.setIdPerusahaan(p.getIdPerusahaan());
        
       
        if(jXDatePicker1.getDate()==null)
        {
           
            try 
            {
                tgl=dateFormat.parse(dateFormat.format(cal.getTime()).toString());
                sr.setTanggalSurat(tgl);
                jXDatePicker1.setDate(tgl);
            } catch (ParseException ex) 
            {
                System.out.println(ex);
            }
        }
        else
        {
              sr.setTanggalSurat(jXDatePicker1.getDate());
        }
        Date sekarang =new Date();
        sr.setTanggalBuat(sekarang);
        sr.setMahasiswa(mhs);
        mhs.setIdMahasiswa(idmhs);
//        if(jradmasuk.isSelected())
//        {
//            idkat=1;
//        }
//        else if (jradkeluar.isSelected())
//        {
//            idkat=2;
//        }
        sr.setKategori(kat);
        kat.setIdKategori(2);
        sr.setNoSurat(txtNomor.getText());
        System.out.println(txtNomor.getText());
        sr.setThajaran(t);
        t.setIdThajaran(fs.getTa());
        sr.setJudul(txtJudul.getText());
        Transaction tx=s.beginTransaction();
        s.saveOrUpdate(sr);
        tx.commit();
        s.flush();
        s.close();
        fillTable(jTSurat);
        nf.setLebarKolom(jTSurat);
    }//GEN-LAST:event_btnSimpanActionPerformed

    private void txtNimActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNimActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNimActionPerformed

    private void jCJenisKPSkripsiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCJenisKPSkripsiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jCJenisKPSkripsiActionPerformed

    private void jCJenisKPSkripsiItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jCJenisKPSkripsiItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_jCJenisKPSkripsiItemStateChanged

    private void btnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusActionPerformed
        // TODO add your handling code here:
        Hapus(idSurat);
        fillTable(jTSurat);
        nf.setLebarKolom(jTSurat);
    }//GEN-LAST:event_btnHapusActionPerformed

    private void jCPerusahaanItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jCPerusahaanItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_jCPerusahaanItemStateChanged

    private void jCPerusahaanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCPerusahaanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jCPerusahaanActionPerformed

    private void jTSuratMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTSuratMouseClicked
        int baris =jTSurat.getSelectedRow();
        
        idSurat = (Integer)   jTSurat.getModel().getValueAt(baris, 1);
        tampilBtnCetak();
//        xnosurat=(String) jTSurat.getModel().getValueAt(baris, 2);
//        xnim= (Integer) jTSurat.getModel().getValueAt(baris, 3);
//        xnama=(String) jTSurat.getModel().getValueAt(baris, 4);
//        xprodi=(String) jTSurat.getModel().getValueAt(baris, 6);
//        xalamat=(String) jTSurat.getModel().getValueAt(baris, 8);

//        System.out.println(idSurat);
//        System.out.println(xnosurat);
//        System.out.println(xnim);
//        System.out.println(xnama);
//        System.out.println(xprodi);
//        System.out.println(xalamat);
    }//GEN-LAST:event_jTSuratMouseClicked

    private void btnPDFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPDFActionPerformed
        if(radakt.isSelected())
        {
            URL namafile=FSurat.class.getResource("/laporan/s_aktif_kuliah.jasper");
            xsemesterTahun=fs.getSemesterTahun();
            xtanggal=fs.getTanggal();

            Map parameters = new HashMap();
            fs.getSurat(idSurat);
            parameters.put("xsemesterTahun", xsemesterTahun);
            parameters.put("xtanggal", xtanggal);
            parameters.put("xnoSurat", fs.xnosurat);
            parameters.put("xnim", fs.xnim);
            parameters.put("xnama", fs.xnama);
            parameters.put("xprodi", fs.xprodi);
            parameters.put("xalamat", fs.xalamat);

            try 
            {
                JasperPrint print = JasperFillManager.fillReport(namafile.getPath(), parameters, new JREmptyDataSource());
                JasperExportManager.exportReportToPdfFile(print, "D:/aktif_kuliah_"+fs.xnim+"_"+fs.xnama+".pdf");
            } catch (JRException ex) 
            {
               JOptionPane.showMessageDialog(null, "Gagal Membuka Laporan" + ex,"Cetak Laporan",JOptionPane.ERROR_MESSAGE);
            }
        }
        else
        {
            URL namafile=FSurat.class.getResource("/laporan/s_kp_skripsi.jasper");
            xsemesterTahun=fs.getSemesterTahun();
            xtanggal=fs.getTanggal();
            
            fs.getSurat(idSurat);
            Map parameters = new HashMap();
            parameters.put("xnoSurat", fs.xnosurat);
            parameters.put("xnim", fs.xnim);
            parameters.put("xnama", fs.xnama);
            parameters.put("xprodi", fs.xprodi);
            parameters.put("xalamat", fs.xalamat);
            parameters.put("xsemesterTahun", xsemesterTahun);
            parameters.put("xtanggal", xtanggal);
            parameters.put("xjudul", fs.xjudul);
            parameters.put("xperusahaan", fs.xperusahaan);
            xisipermohonan=fs.getPermohonan_kp_skripsi(fs.xjkps,fs.xjudul ,fs.xperusahaan);
            parameters.put("xisipermohonan", xisipermohonan);
            xhal="Permohonan Penelitian "+fs.xjkps;
            parameters.put("xhal",xhal);
            try 
            {
                JasperPrint print = JasperFillManager.fillReport(namafile.getPath(), parameters, new JREmptyDataSource());
                JasperExportManager.exportReportToPdfFile(print, "D:/permohonan_"+fs.xjkps+"_"+fs.xnim+"_"+fs.xnama+".pdf");
            } catch (JRException ex) {
               JOptionPane.showMessageDialog(null, "Gagal Membuka Laporan" + ex,"Cetak Laporan",JOptionPane.ERROR_MESSAGE);
            }
        }
        
    }//GEN-LAST:event_btnPDFActionPerformed

    private void radkpskripsiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radkpskripsiActionPerformed
        idSurat=0;
        jCJenisKPSkripsi.setVisible(true);
        jCPerusahaan.setVisible(true);
        lbtujuan.setVisible(true);
        fillTable(jTSurat);
        nf.setLebarKolom(jTSurat);
        jCKeperluan.setVisible(false);
        lbKeperluan.setVisible(false);
        lbjudul.setVisible(true);
        txtJudul.setVisible(true);
    }//GEN-LAST:event_radkpskripsiActionPerformed

    private void radaktActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radaktActionPerformed
        idSurat=0;
        jCJenisKPSkripsi.setVisible(false);
        jCPerusahaan.setVisible(false);
        lbtujuan.setVisible(false);
        fillTable(jTSurat);
        nf.setLebarKolom(jTSurat);
        jCKeperluan.setVisible(true);
        lbKeperluan.setVisible(true);
        txtJudul.setVisible(false);
        lbjudul.setVisible(false);
    }//GEN-LAST:event_radaktActionPerformed

    private void btnCetakActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCetakActionPerformed
        // TODO add your handling code here:
        
        if (idSurat==0)
        {
            JOptionPane.showMessageDialog(this, "Pilih Surat yang akan dicetak");
            
        }
        else if(idSurat!=0 && radakt.isSelected())
        {
            URL namafile=FSurat.class.getResource("/laporan/s_aktif_kuliah.jasper");
            System.out.println("tampilkan laporan");
            int baris =jTSurat.getSelectedRow();
            xsemesterTahun=fs.getSemesterTahun();
            xtanggal=fs.getTanggal();
            
            Map parameters = new HashMap();
//            
            fs.getSurat(idSurat);
            parameters.put("xsemesterTahun", xsemesterTahun);
            parameters.put("xtanggal", xtanggal);
            parameters.put("xnoSurat", fs.xnosurat);
            parameters.put("xnim", fs.xnim);
            parameters.put("xnama", fs.xnama);
            parameters.put("xprodi", fs.xprodi);
            parameters.put("xalamat", fs.xalamat);

            try 
            {
                JasperPrint print = JasperFillManager.fillReport(namafile.getPath(), parameters, new JREmptyDataSource());
                JasperViewer.viewReport(print,false);
            } catch (JRException ex) 
            {
               JOptionPane.showMessageDialog(null, "Gagal Membuka Laporan" + ex,"Cetak Laporan",JOptionPane.ERROR_MESSAGE);
            }
        }
        else if(idSurat!=0 && radkpskripsi.isSelected())
        {     
            URL namafile=FSurat.class.getResource("/laporan/s_kp_skripsi.jasper");
            xsemesterTahun=fs.getSemesterTahun();
            xtanggal=fs.getTanggal();
            
            fs.getSurat(idSurat);
            Map parameters = new HashMap();
            parameters.put("xnoSurat", fs.xnosurat);
            parameters.put("xnim", fs.xnim);
            parameters.put("xnama", fs.xnama);
            parameters.put("xprodi", fs.xprodi);
            parameters.put("xalamat", fs.xalamat);
            parameters.put("xsemesterTahun", xsemesterTahun);
            parameters.put("xtanggal", xtanggal);
            parameters.put("xjudul", fs.xjudul);
            parameters.put("xperusahaan", fs.xperusahaan);
            xisipermohonan=fs.getPermohonan_kp_skripsi(fs.xjkps,fs.xjudul ,fs.xperusahaan);
            parameters.put("xisipermohonan", xisipermohonan);
            xhal="Permohonan Penelitian "+fs.xjkps;
            parameters.put("xhal",xhal);
            try 
            {
                JasperPrint print = JasperFillManager.fillReport(namafile.getPath(), parameters, new JREmptyDataSource());
                JasperViewer.viewReport(print,false);
            } catch (JRException ex) {
               JOptionPane.showMessageDialog(null, "Gagal Membuka Laporan" + ex,"Cetak Laporan",JOptionPane.ERROR_MESSAGE);
            }
        }
       
        
    }//GEN-LAST:event_btnCetakActionPerformed

    private void btnCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCloseActionPerformed
        // TODO add your handling code here:
        dispose();
    }//GEN-LAST:event_btnCloseActionPerformed

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
            java.util.logging.Logger.getLogger(FSurat.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FSurat.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FSurat.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FSurat.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new FSurat().setVisible(true);
                } catch (SQLException ex) {
                }
            }
        });
    }
private class ComboBoxListener implements ActionListener {
        

    public ComboBoxListener() {
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        Jenissurat mahasiswa=(Jenissurat) jCJenisKPSkripsi.getSelectedItem();
            idjeniskpskripsi=(mahasiswa.getJenisSurat());
            System.out.println("xxx"+idjeniskpskripsi);
    }
}
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCari;
    private javax.swing.JButton btnCetak;
    private javax.swing.JButton btnClose;
    private javax.swing.JButton btnHapus;
    private javax.swing.JButton btnPDF;
    private javax.swing.JButton btnSimpan;
    private javax.swing.ButtonGroup buttonGroupRadioKeterangan;
    public javax.swing.JComboBox jCJenisKPSkripsi;
    private javax.swing.JComboBox jCKeperluan;
    public javax.swing.JComboBox jCPerusahaan;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    public javax.swing.JTable jTSurat;
    private org.jdesktop.swingx.JXDatePicker jXDatePicker1;
    private javax.swing.JLabel lbAlamat;
    private javax.swing.JLabel lbKeperluan;
    private javax.swing.JLabel lbNama;
    private javax.swing.JLabel lbNim;
    private javax.swing.JLabel lbTlp;
    private javax.swing.JLabel lbjudul;
    private javax.swing.JLabel lbtanggal;
    private javax.swing.JLabel lbtanggal1;
    private javax.swing.JLabel lbtujuan;
    private javax.swing.JRadioButton radakt;
    private javax.swing.JRadioButton radkpskripsi;
    private javax.swing.JTextField txtJudul;
    public javax.swing.JTextField txtNim;
    private javax.swing.JTextField txtNomor;
    // End of variables declaration//GEN-END:variables
}
