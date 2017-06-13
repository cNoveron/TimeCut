/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sf.timecut.ui.dbconection;

import java.sql.*;
import java.io.File;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
//import java.sql.Statement;
import java.sql.PreparedStatement;


public class Conection {
    Connection con = null;
//    Statement st = null;

    public Conection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        con = DriverManager.getConnection("jdbc:mysql://localhost:3306","root","root");
        Statement st = con.createStatement();
        st.executeQuery("use timecut");
    }

    public void saveFile(String absolutePath) throws FileNotFoundException, SQLException, IOException{
        File file = new File(absolutePath);
        InputStream inputStream = new FileInputStream(file);
        String sql = "insert into person (archivo) values (?)";
        PreparedStatement statement = con.prepareStatement(sql);
        statement.setBlob(1, inputStream);
        statement.executeUpdate();
    }

    public void registrarUsuario(String nom, String ap, String am, String usu, String psw) throws SQLException {
        String sql = "insert into usuario (nombre,aPaterno,aMaterno,usuario,contrasenia) values (?)";
        PreparedStatement statement = con.prepareStatement(sql);
        statement.setString(0, nom);
        statement.setString(1, ap);
        statement.setString(2, am);
        statement.setString(3, usu);
        statement.setString(4, psw);
        statement.executeUpdate();
    }

    public boolean checkUser(String usu, String psw) throws SQLException {
        String sql = "select * from usuario where usuario = " + usu;
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(sql);
        String contraseña;
        if(rs.next()){
            contraseña = rs.getString(4);
            return (contraseña.equals(psw));
        }
        else{
            return false;
        }
        
    }
}