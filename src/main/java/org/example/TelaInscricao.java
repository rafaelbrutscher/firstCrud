package org.example;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TelaInscricao extends JFrame{
    private JButton novaInscriçãoButton;
    private JPanel panel1;
    private JButton editarDeletarInscriçãoButton;
    private JButton sairButton;
public TelaInscricao(Curso cur, CursoDAO dao) {
    setContentPane(panel1);
    setSize(800,600);
    setLocationRelativeTo(null);
    setVisible(true);
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

    novaInscriçãoButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            dispose();
            new TelaNovaInscricao(cur, dao);
        }
    });
    editarDeletarInscriçãoButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            dispose();
            new TelaEditDeleteInscricao(cur, dao);
        }
    });
    sairButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            dispose();
            new TelaInicial(cur, dao);
        }
    });
}
}
