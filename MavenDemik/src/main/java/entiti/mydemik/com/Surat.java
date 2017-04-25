package entiti.mydemik.com;
// Generated Apr 17, 2017 8:12:10 AM by Hibernate Tools 4.3.1


import java.util.Date;

/**
 * Surat generated by hbm2java
 */
public class Surat  implements java.io.Serializable {


     private Integer idSurat;
     private Jenissurat jenissurat;
     private Kategori kategori;
     private Keperluan keperluan;
     private Mahasiswa mahasiswa;
     private Perusahaan perusahaan;
     private Thajaran thajaran;
     private Date tanggalSurat;
     private Date tanggalBuat;
     private String noSurat;
     private String judul;

    public Surat() {
    }

	
    public Surat(Jenissurat jenissurat, Kategori kategori, Keperluan keperluan, Mahasiswa mahasiswa, Perusahaan perusahaan, Thajaran thajaran, Date tanggalSurat, Date tanggalBuat, String noSurat) {
        this.jenissurat = jenissurat;
        this.kategori = kategori;
        this.keperluan = keperluan;
        this.mahasiswa = mahasiswa;
        this.perusahaan = perusahaan;
        this.thajaran = thajaran;
        this.tanggalSurat = tanggalSurat;
        this.tanggalBuat = tanggalBuat;
        this.noSurat = noSurat;
    }
    public Surat(Jenissurat jenissurat, Kategori kategori, Keperluan keperluan, Mahasiswa mahasiswa, Perusahaan perusahaan, Thajaran thajaran, Date tanggalSurat, Date tanggalBuat, String noSurat, String judul) {
       this.jenissurat = jenissurat;
       this.kategori = kategori;
       this.keperluan = keperluan;
       this.mahasiswa = mahasiswa;
       this.perusahaan = perusahaan;
       this.thajaran = thajaran;
       this.tanggalSurat = tanggalSurat;
       this.tanggalBuat = tanggalBuat;
       this.noSurat = noSurat;
       this.judul = judul;
    }
   
    public Integer getIdSurat() {
        return this.idSurat;
    }
    
    public void setIdSurat(Integer idSurat) {
        this.idSurat = idSurat;
    }
    public Jenissurat getJenissurat() {
        return this.jenissurat;
    }
    
    public void setJenissurat(Jenissurat jenissurat) {
        this.jenissurat = jenissurat;
    }
    public Kategori getKategori() {
        return this.kategori;
    }
    
    public void setKategori(Kategori kategori) {
        this.kategori = kategori;
    }
    public Keperluan getKeperluan() {
        return this.keperluan;
    }
    
    public void setKeperluan(Keperluan keperluan) {
        this.keperluan = keperluan;
    }
    public Mahasiswa getMahasiswa() {
        return this.mahasiswa;
    }
    
    public void setMahasiswa(Mahasiswa mahasiswa) {
        this.mahasiswa = mahasiswa;
    }
    public Perusahaan getPerusahaan() {
        return this.perusahaan;
    }
    
    public void setPerusahaan(Perusahaan perusahaan) {
        this.perusahaan = perusahaan;
    }
    public Thajaran getThajaran() {
        return this.thajaran;
    }
    
    public void setThajaran(Thajaran thajaran) {
        this.thajaran = thajaran;
    }
    public Date getTanggalSurat() {
        return this.tanggalSurat;
    }
    
    public void setTanggalSurat(Date tanggalSurat) {
        this.tanggalSurat = tanggalSurat;
    }
    public Date getTanggalBuat() {
        return this.tanggalBuat;
    }
    
    public void setTanggalBuat(Date tanggalBuat) {
        this.tanggalBuat = tanggalBuat;
    }
    public String getNoSurat() {
        return this.noSurat;
    }
    
    public void setNoSurat(String noSurat) {
        this.noSurat = noSurat;
    }
    public String getJudul() {
        return this.judul;
    }
    
    public void setJudul(String judul) {
        this.judul = judul;
    }




}


