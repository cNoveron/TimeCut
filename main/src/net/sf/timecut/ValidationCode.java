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
public class ValidationCode extends JFrame implements ActionListener{

    JLabel jl1 = new JLabel("Usuario");
    JLabel jl2 = new JLabel("");
    JLabel jl3 = new JLabel("Codigo de validación");
    JTextField jt1 = new JTextField();
    JButton bot1 = new JButton("Validar Usuario");
    
    ValidationCode(String usu) {
        
        setLayout(null);
        setResizable(false);
        setBounds(20,20,350,590);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(Color.getHSBColor((float)0.5, (float).9, (float)0.9 ));
        
        setVisible(true);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        jl1.setBounds((getWidth()/2)-150,200,300,30);        
        jl2.setBounds((getWidth()/2)-150,230,300,50);
        jl3.setBounds((getWidth()/2)-150,290,300,30);
        jt1.setBounds((getWidth()/2)-150,320,300,50);
        bot1.setBounds((getWidth()/2)-130,450,260,40);
        
        jl2.setText(usu);
        
        Font font = new Font("Arial",10,30);
        jt1.setFont(font);
        jl1.setFont(font);
        jl2.setFont(font);
        jl3.setFont(font);
        bot1.setFont(font);
        
        add(jt1);
        add(jl1);
        add(jl2);
        add(jl3);
        add(bot1);

        bot1.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            Conection con = new Conection();
            con.updateValidation(jl2.getText(),jt1.getText());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ValidationCode.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ValidationCode.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
