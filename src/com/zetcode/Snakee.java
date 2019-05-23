package com.zetcode;

import java.awt.EventQueue;
import javax.swing.JFrame;

public class Snakee extends JFrame  {

    public Snakee() {
        
        initUI();
    }
    
    private void initUI() {
        
        add(new Snake());
        
        setResizable(false);
        pack();
        
        setTitle("Snake");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    public static void main(String[] args) {
        
        EventQueue.invokeLater(() -> {
            JFrame ex = new Snakee();
            ex.setVisible(true);
        });
    }
}
