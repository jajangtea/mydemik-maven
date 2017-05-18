package entiti.mydemik.com;
// Generated May 3, 2017 9:29:35 AM by Hibernate Tools 4.3.1


import java.util.HashSet;
import java.util.Set;

/**
 * Prodi generated by hbm2java
 */
public class Prodi  implements java.io.Serializable {


     private Integer idProdi;
     private String kodeProdi;
     private String namaProdi;
     private Set mahasiswas = new HashSet(0);

    public Prodi() {
    }

	
    public Prodi(String kodeProdi, String namaProdi) {
        this.kodeProdi = kodeProdi;
        this.namaProdi = namaProdi;
    }
    public Prodi(String kodeProdi, String namaProdi, Set mahasiswas) {
       this.kodeProdi = kodeProdi;
       this.namaProdi = namaProdi;
       this.mahasiswas = mahasiswas;
    }
   
    public Integer getIdProdi() {
        return this.idProdi;
    }
    
    public void setIdProdi(Integer idProdi) {
        this.idProdi = idProdi;
    }
    public String getKodeProdi() {
        return this.kodeProdi;
    }
    
    public void setKodeProdi(String kodeProdi) {
        this.kodeProdi = kodeProdi;
    }
    public String getNamaProdi() {
        return this.namaProdi;
    }
    
    public void setNamaProdi(String namaProdi) {
        this.namaProdi = namaProdi;
    }
    public Set getMahasiswas() {
        return this.mahasiswas;
    }
    
    public void setMahasiswas(Set mahasiswas) {
        this.mahasiswas = mahasiswas;
    }




}


