package entiti.mydemik.com;
// Generated May 3, 2017 9:29:35 AM by Hibernate Tools 4.3.1


import java.util.HashSet;
import java.util.Set;

/**
 * Jenisujian generated by hbm2java
 */
public class Jenisujian  implements java.io.Serializable {


     private Integer idJenisUjian;
     private String namaJenisUjian;
     private Set jadwals = new HashSet(0);

    public Jenisujian() {
    }

    public Jenisujian(String namaJenisUjian, Set jadwals) {
       this.namaJenisUjian = namaJenisUjian;
       this.jadwals = jadwals;
    }
   
    public Integer getIdJenisUjian() {
        return this.idJenisUjian;
    }
    
    public void setIdJenisUjian(Integer idJenisUjian) {
        this.idJenisUjian = idJenisUjian;
    }
    public String getNamaJenisUjian() {
        return this.namaJenisUjian;
    }
    
    public void setNamaJenisUjian(String namaJenisUjian) {
        this.namaJenisUjian = namaJenisUjian;
    }
    public Set getJadwals() {
        return this.jadwals;
    }
    
    public void setJadwals(Set jadwals) {
        this.jadwals = jadwals;
    }




}

