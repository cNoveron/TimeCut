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
import javax.swing.JOptionPane;
import net.sf.timecut.model.MailerActivationCode;


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

    public void registrarUsuario(String nom, String ap, String am, String usu, String psw, String correo) throws SQLException {
        String valCode="";
        for(int i=0;i<5;i++){
            switch((int)(Math.random()*3)){
                case 0:
                    valCode += (char)((Math.random()*10)+48);
                    break;
                case 1:
                    valCode += (char)((Math.random()*26)+65);
                    break;
                case 2:
                    valCode += (char)((Math.random()*26)+97);
                    break;
                default:
                    break;
            }
        }
        String sql = "insert into usuario (nombre,aPaterno,aMaterno,usuario,contrasenia,correo,vCode,validado) values (?)";
        PreparedStatement statement = con.prepareStatement(sql);
        statement.setString(0, nom);
        statement.setString(1, ap);
        statement.setString(2, am);
        statement.setString(3, usu);
        statement.setString(4, psw);
        statement.setString(5, correo);
        statement.setString(6, valCode);
        MailerActivationCode mac = new MailerActivationCode();
        mac.sendMail(correo,valCode);
        statement.setInt(7, 0);
        statement.executeUpdate();
    }

    public boolean checkUser(String usu, String psw) throws SQLException {
        String sql = "select * from usuario where usuario = " + usu;
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(sql);
        String contraseña;
        if(rs.next()){
            contraseña = rs.getString(4);
//            if(rs.getInt(5)!=0){
                return (contraseña.equals(psw));
//            }
//            else{
//                JOptionPane.showMessageDialog(null, "Su usuario no ha sido confirmado", "Falta confirmacion", JOptionPane.INFORMATION_MESSAGE);
//                return (false);
//            }
        }
        else{
            return false;
        }
        
    }
    
    public boolean checkValidacion(String usu) throws SQLException {
        String sql = "select * from usuario where usuario = " + usu;
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(sql);
        
        if(rs.next()){
            if(rs.getInt(7)!=0){
                return (true);
            }
            else{
                JOptionPane.showMessageDialog(null, "Su usuario no ha sido confirmado", "Falta confirmacion", JOptionPane.INFORMATION_MESSAGE);
                return (false);
            }
        }
        else{
            return false;
        }
        
    }

    public boolean updateValidation(String usu,String code) throws SQLException {
        String sql = "select * from usuario where usuario = " + usu;
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(sql);
        if(rs.next()){
            if(rs.getInt(7)!=0){
                if(rs.getString(6).equals(code)){
                    String sql2 = "update usuario set validado = ? where usuario = " + usu;
                    PreparedStatement statement = con.prepareStatement(sql);
                    statement.setInt(0, 1);
                    statement.executeUpdate(sql2);
                    return true;
                }
                else{
                    JOptionPane.showMessageDialog(null, "Codigo de confirmacion invalido", "Falta confirmacion", JOptionPane.INFORMATION_MESSAGE);
                return false;
                }
            }
            else{
                return false;
            }
        }
        else{
            return false;
        }
        
    }
}