package org.example;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TelaAluno extends JFrame{
    private JPanel PanelAluno;
    private JButton adicionarAlunoButton;
    private JButton editarDeletarAlunoButton;
    private JButton sairButton;
public TelaAluno(Curso cur, CursoDAO dao) {
    setContentPane(PanelAluno);
    setSize(800,600);
    setLocationRelativeTo(null);
    setVisible(true);
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

    adicionarAlunoButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            dispose();
            new TelaAddAluno(cur, dao);
        }
    });
    editarDeletarAlunoButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            dispose();
            new TelaEditDeleteAluno(cur, dao);
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
