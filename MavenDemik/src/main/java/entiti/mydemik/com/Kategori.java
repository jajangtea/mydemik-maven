package entiti.mydemik.com;
// Generated Apr 17, 2017 8:12:10 AM by Hibernate Tools 4.3.1


import java.util.HashSet;
import java.util.Set;

/**
 * Kategori generated by hbm2java
 */
public class Kategori  implements java.io.Serializable {


     private Integer idKategori;
     private String namaKategori;
     private Set surats = new HashSet(0);

    public Kategori() {
    }

	
    public Kategori(String namaKategori) {
        this.namaKategori = namaKategori;
    }
    public Kategori(String namaKategori, Set surats) {
       this.namaKategori = namaKategori;
       this.surats = surats;
    }
   
    public Integer getIdKategori() {
        return this.idKategori;
    }
    
    public void setIdKategori(Integer idKategori) {
        this.idKategori = idKategori;
    }
    public String getNamaKategori() {
        return this.namaKategori;
    }
    
    public void setNamaKategori(String namaKategori) {
        this.namaKategori = namaKategori;
    }
    public Set getSurats() {
        return this.surats;
    }
    
    public void setSurats(Set surats) {
        this.surats = surats;
    }




}

