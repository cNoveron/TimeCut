/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sf.timecut;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import net.sf.timecut.ui.dbconection.Conection;

class RegistrarUsuario extends JFrame implements ActionListener {

    JTextField jt1 = new JTextField();
    JTextField jt2 = new JTextField();
    JTextField jt3 = new JTextField();
    JTextField jt4 = new JTextField();
    JTextField jt5 = new JTextField();
    JTextField jt6 = new JTextField();
    JLabel jl1 = new JLabel("Nombre");
    JLabel jl2 = new JLabel("Apellido Materno");
    JLabel jl3 = new JLabel("Apellido paterno");
    JLabel jl4 = new JLabel("Usuario");
    JLabel jl5 = new JLabel("Contraseña");
    JLabel jl6 = new JLabel("Repita contraseña");
    JButton bot1 = new JButton("Registrarse");
    JButton bot2 = new JButton("Regresar");

    public RegistrarUsuario() {
        setLayout(null);
        setResizable(false);
        setBounds(20, 20, 700, 590);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(Color.getHSBColor((float) 0.5, (float) .9, (float) 0.9));
        setVisible(true);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        
        jt1.setBounds(getWidth()/2+20, 250, 300, 40);
        jt2.setBounds(getWidth()/2+20, 300, 300, 40);
        jt3.setBounds(getWidth()/2+20, 350, 260, 40);
        jt4.setBounds(getWidth()/2+20, 400, 260, 40);
        jt5.setBounds(getWidth()/2+20, 450, 260, 40);
        jt6.setBounds(getWidth()/2+20, 500, 260, 40);
        jl1.setBounds(getWidth()/2-230, 250, 260, 40);
        jl2.setBounds(getWidth()/2-230, 300, 260, 40);
        jl3.setBounds(getWidth()/2-230, 350, 260, 40);
        jl4.setBounds(getWidth()/2-230, 400, 260, 40);
        jl5.setBounds(getWidth()/2-230, 450, 260, 40);
        jl6.setBounds(getWidth()/2-230, 500, 260, 40);
        bot1.setBounds(getWidth()/2-130, 600, 260, 40);
        bot2.setBounds(getWidth()/2-130, 650, 260, 40);
        
        Font font = new Font("Arial",10,30);
        jt1.setFont(font);
        jt2.setFont(font);
        jt3.setFont(font);
        jt4.setFont(font);
        jt5.setFont(font);
        jt6.setFont(font);
        jl1.setFont(font);
        jl2.setFont(font);
        jl3.setFont(font);
        jl4.setFont(font);
        jl5.setFont(font);
        jl6.setFont(font);
        bot1.setFont(font);
        bot2.setFont(font);

        add(jt1);
        add(jt2);
        add(jt3);
        add(jt4);
        add(jt5);
        add(jt6);
        add(jl1);
        add(jl2);
        add(jl3);
        add(jl4);
        add(jl5);
        add(jl6);
        add(bot1);
        add(bot2);

        bot1.addActionListener(this);
        bot2.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == bot1) {
            try {
                if (jt5.getText().equals(jt6.getText())) {
                    Conection con = new Conection();

                    con.registrarUsuario(jt1.getText(), jt2.getText(), jt3.getText(), jt4.getText(), jt5.getText());
                } else {
                    JOptionPane.showMessageDialog(null, "Contraseñas no coinciden","ERROR",JOptionPane.ERROR_MESSAGE);
                }
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(RegistrarUsuario.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(RegistrarUsuario.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (e.getSource() == bot2) {
            dispose();
            TimeCut tc = new TimeCut();
//            ad.setVisible(true);
        }
    }

}
