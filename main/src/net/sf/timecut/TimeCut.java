/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sf.timecut;

import java.awt.Color;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import net.sf.timecut.ui.dbconection.Conection;

/**
 *
 * @author cavca
 */
public class TimeCut extends JFrame implements ActionListener {
    
    JLabel jl1 = new JLabel("Usuario");
    JLabel jl2 = new JLabel("Contraseña");
    JTextField jt1 = new JTextField();
    JPasswordField jp2 = new JPasswordField();
    JButton bot1 = new JButton("Iniciar Sesion");
    JButton bot2 = new JButton("Registrarse");
    
    public TimeCut() throws HeadlessException {
        setLayout(null);
        setResizable(false);
        setBounds(20,20,350,590);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(Color.getHSBColor((float)0.5, (float).9, (float)0.9 ));
        
        setVisible(true);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        jl1.setBounds((getWidth()/2)-150,200,300,30);
        jl2.setBounds((getWidth()/2)-150,290,300,30);
        jt1.setBounds((getWidth()/2)-150,230,300,50);
        jp2.setBounds((getWidth()/2)-150,320,300,50);
        bot1.setBounds((getWidth()/2)-130,450,260,40);
        bot2.setBounds((getWidth()/2)-130,500,260,40);
        Font font = new Font("Arial",10,30);
        Font font2 = new Font("Arial",10,20);
        jl1.setFont(font2);
        jl2.setFont(font2);
        jt1.setFont(font);
        jp2.setFont(font);
        bot1.setFont(font);
        bot2.setFont(font);
        add(jl1);
        add(jl2);
        add(jt1);
        add(jp2);
        add(bot1);
        add(bot2);
        
        bot1.addActionListener(this);
        bot2.addActionListener(this);
        
    }

    
    public static void main(String[] args) {
        TimeCut ad = new TimeCut();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //jt1.setText(e.getSource());
        if(e.getSource()==bot1){
            try {
                Conection con = new Conection();
                if(con.checkUser(jt1.getText(),jp2.getText())){
//                    TimeTracker tt = new TimeTracker();
                    if(con.checkValidacion(jt1.getText())){
                        TimeTracker timeTracker = new TimeTracker();
                        timeTracker.loadConfiguration(); 
                            timeTracker.resetWorkspace();
                            if (timeTracker.getAppPreferences().isAutoOpenRecentFile()) {
                                timeTracker.openRecentFile();
                            }

                        timeTracker.startUI();
                    }
                    else{
                        ValidationCode valc = new ValidationCode(jt1.getText());
                    }
                }
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(TimeCut.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(TimeCut.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        if(e.getSource()==bot2){
            dispose();
            RegistrarUsuario ru = new RegistrarUsuario();
        }
    }
    
}
