/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.service.mydemik.com;

import entiti.mydemik.com.Jenissurat;
import entiti.mydemik.com.Keperluan;
import entiti.mydemik.com.Perusahaan;
import entiti.mydemik.com.Prodi;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jajangtea
 */
public class SuratService {
    

    Connection connection;
    PreparedStatement preparedStatement;
    Jenissurat jns;
    Keperluan kpr;
    Perusahaan prs;
    Prodi prd;
    List<Jenissurat> Jenissurats=new ArrayList<>();
    List<Keperluan> keperluans=new ArrayList<>();
    List<Perusahaan> perusahaans=new ArrayList<>();
    List<Prodi> prodis=new ArrayList<>();
    
    public SuratService(Connection connection){
        this.connection=connection;
    }
    
    public List<Jenissurat> getAllJenis() throws SQLException{
        preparedStatement=connection.prepareStatement("SELECT * FROM jenissurat");
        ResultSet rs=preparedStatement.executeQuery();
        while(rs.next()){
            jns=new Jenissurat();
            jns.setIdJenis(rs.getInt("idJenis"));
            jns.setJenisSurat(rs.getString("JenisSurat"));
            Jenissurats.add(jns);
        }
        return Jenissurats;
    }
    public List<Keperluan> getAllKeperluan() throws SQLException{
        preparedStatement=connection.prepareStatement("SELECT * FROM Keperluan");
        ResultSet rs=preparedStatement.executeQuery();
        while(rs.next()){
            kpr=new Keperluan();
            kpr.setIdKeperluan(rs.getInt("idKeperluan"));
            kpr.setKeperluan(rs.getString("Keperluan"));
            keperluans.add(kpr);
        }
        return keperluans;
    }
    
    public List<Perusahaan> getAllPerusahaan() throws SQLException{
        preparedStatement=connection.prepareStatement("SELECT * FROM Perusahaan");
        ResultSet rs=preparedStatement.executeQuery();
        while(rs.next()){
            prs=new Perusahaan();
            prs.setIdPerusahaan(rs.getInt("idPerusahaan"));
            prs.setNamaPerusahaan(rs.getString("namaPerusahaan"));
            perusahaans.add(prs);
        }
        return perusahaans;
    }
    
    public List<Prodi> getAllProdi() throws SQLException{
        preparedStatement=connection.prepareStatement("SELECT * FROM Prodi");
        ResultSet rs=preparedStatement.executeQuery();
        while(rs.next()){
            prd=new Prodi();
            prd.setIdProdi(rs.getInt("idProdi"));
            prd.setNamaProdi(rs.getString("namaProdi"));
            prodis.add(prd);
        }
        return prodis;
    }
}