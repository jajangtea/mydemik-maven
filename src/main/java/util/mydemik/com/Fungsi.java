/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.mydemik.com;

import entiti.mydemik.com.Surat;
import entiti.mydemik.com.Thajaran;
import java.awt.Component;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.swing.JTable;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.jdesktop.swingx.JXDatePicker;

/**
 *
 * @author User
 */
public class Fungsi {
    private Integer ta_aktif;
    private String tahun,semester;
    private Date tgl;
    public String xnosurat,xnama,xalamat,xprodi,xsemesterTahun,xtanggal,xisipermohonan,xjudul,xperusahaan,xta,xjkps;
    public Integer xnim;
    public int getTa() 
    {
        try
        {
            
            SessionFactory sf=HibernateUtil.getSessionFactory();
            Session s=sf.openSession();
            Transaction tx = s.beginTransaction();
            Query q = s.createQuery("FROM Thajaran where status=1");
            List resultList = q.list();
            for(Object o : resultList) 
            {
                Thajaran tj = (Thajaran)o;
                ta_aktif=tj.getIdThajaran();
            }
            s.flush();
            tx.commit();
            s.close();
            
        }
        catch (ClassCastException e) 
        {
            System.out.println("Err :" + e);
        }
        
        return ta_aktif;
    }
    
    public String getSemesterTahun() 
    {
        
        
        try
        {
            
            SessionFactory sf=HibernateUtil.getSessionFactory();
            Session s=sf.openSession();
            Transaction tx = s.beginTransaction();
            Query q = s.createQuery("FROM Thajaran where status=1");
            List resultList = q.list();
            for(Object o : resultList) 
            {
                Thajaran tj = (Thajaran)o;
                semester=tj.getSemester();
                tahun=tj.getTahun();
            }
            s.flush();
            tx.commit();
            s.close();
            
        }
        catch (ClassCastException e) 
        {
            System.out.println("Err :" + e);
        }
        
        return "terdaftar sebagai mahasiswa pada semester "+semester+" T.A "+tahun+".";
    }
    
    public String getTanggal() 
    {
        int year;
        int month;
        int day;
        Date date=new Date();
        LocalDate lc=date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        year=lc.getYear();
        month=lc.getMonthValue();
        day=lc.getDayOfMonth();
        return "Tanjungpinang, "+day+"-"+month+"-"+year;
    }
    
    public int getTahun() 
    {
        int year;
        Date date=new Date();
        LocalDate lc=date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        year=lc.getYear();
        return year;
    }
    
    public int getBulan() 
    {
        int month;
        Date date=new Date();
        LocalDate lc=date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        month=lc.getMonthValue();
        return month;
    }
    
    public int getDay() 
    {
        int day;
        Date date=new Date();
        LocalDate lc=date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        day=lc.getDayOfMonth();
        return day;
    }
    
    public Date getTampilTanggal(JXDatePicker jd)
    {
        if(jd.getDate()==null)
        {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd.hhmmss");
            Calendar cal = Calendar.getInstance();
            try 
            {
                tgl=dateFormat.parse(dateFormat.format(cal.getTime()).toString());
                jd.setDate(tgl);
            } catch (ParseException ex) 
            {
                System.out.println(ex);
            }
        }
        else
        {
            System.out.println(jd.getDate());
        }
        return tgl;
    }
    
    public String hurufromawai(int bln)
    {
       int bulan = bln;
       String rom;
        switch(bulan)
        {
            case 1:  rom="I";
                     break;
            case 2:  rom="II";
                     break;
            case 3:  rom="III";
                     break;
            case 4:  rom="IV";
                     break;
            case 5:  rom="V";
                     break;
            case 6:  rom="VI";
                     break;
            case 7:  rom="VII";
                     break;
            case 8:  rom="VIII";
                     break;
            case 9:  rom="IX";
                     break;
            case 10:  rom="X";
                     break;
            case 11:  rom="XI";
                     break;
            case 12:  rom="XII";
                     break;
            default: rom = "Bulan Belum dimasukan";
            break;
        }
        
        return rom;
    }
    
    public static void main(String[] args) {
        // TODO code application logic here
        Fungsi f=new Fungsi();
        System.out.println(f.hurufromawai(10));
    }
    
    public void setLebarKolom(JTable tbl) {
        int a;
        for (a = 0; a < tbl.getColumnCount(); a++) {
                setColumnWidth(a,tbl);
        }
    }
    
    public void setColumnWidth(int kolom,JTable table) {
        DefaultTableColumnModel modelKolom = (DefaultTableColumnModel) 
        table.getColumnModel();
        TableColumn kolomTabel = modelKolom.getColumn(kolom);
        int lebar = 0;
        int margin = 10;
        int a;

        TableCellRenderer renderer = kolomTabel.getHeaderRenderer();
        if (renderer == null) {
                renderer = table.getTableHeader().getDefaultRenderer();
        }
        Component komponen = renderer.getTableCellRendererComponent(table,
                        kolomTabel.getHeaderValue(), false, false, 0, 0);
        lebar = komponen.getPreferredSize().width;
        for (a = 0; a < table.getRowCount(); a++) {
                renderer = table.getCellRenderer(a, kolom);
                komponen = renderer.getTableCellRendererComponent(table,
                                table.getValueAt(a, kolom), false, false, a, kolom);
                int lebarKolom = komponen.getPreferredSize().width;
                lebar = Math.max(lebar, lebarKolom);

        }
        lebar = lebar + margin;
        kolomTabel.setPreferredWidth(lebar);
    }
    
    public String getPermohonan_kp_skripsi(String jp,String judul,String tempat)
    {
        String isi="";
        char kutip;
        kutip='"';
        isi="Berkenaan dengan penulisan "+jp+" yang harus dilaksanakan dan ditempuh oleh seluruh mahasiswa "
                + "program Sarjana (S-1)  STT INDONESIA TANJUNGPINANG, dengan ini kami mohon kesediaan Bapak/Ibu "
                + "memberikan ijin Penelitian "+jp+" pada mahasiswa kami yang akan melakukan penelitian di "
                + tempat +" dengan judul "+kutip+judul.toUpperCase()+kutip+""
                + " adapun identitas mahasiswa tersebut adalah :";
        return isi;
    }
    
    public String getSurat(int idSurat)
    {
        try
        {
            
            SessionFactory sf=HibernateUtil.getSessionFactory();
            Session s=sf.openSession();
            Transaction tx = s.beginTransaction();
            Query q = s.createQuery("FROM Surat where idSurat=:idSurat");
            q.setParameter("idSurat",idSurat);
            List resultList = q.list();
            for(Object o : resultList) 
            {
                Surat sr = (Surat)o;
                xalamat=sr.getMahasiswa().getAlamat();
                xnama=sr.getMahasiswa().getNama();
                xnim=sr.getMahasiswa().getNim();
                xnosurat=sr.getNoSurat();
                xprodi=sr.getMahasiswa().getProdi().getNamaProdi();
                xtanggal=sr.getTanggalSurat().toString();
                xjudul=sr.getJudul();
                xperusahaan=sr.getPerusahaan().getNamaPerusahaan();
                xjkps=sr.getJenissurat().getJenisSurat();
            }
            s.flush();
            tx.commit();
            s.close();
            
        }
        catch (ClassCastException e) 
        {
            System.out.println("Err :" + e);
        }
        
        return "terdaftar sebagai mahasiswa pada semester "+semester+" T.A "+tahun+".";
    }
    public void hideColumn(JTable tbl,int kolom)
    {
        TableColumnModel tcm =tbl.getColumnModel();
        tcm.removeColumn(tcm.getColumn(kolom));
    }
    
    
}
