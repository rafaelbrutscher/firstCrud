package org.example;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TelaInicial extends JFrame{
    private JPanel PanelInicial;
    private JButton InscricaoButton;
    private JButton AlunosButton;
    private JButton CursosButton;
    private JButton relatoriosButton;
    private JButton sairButton;
    private JButton editarInformaçãoButton;
    private JButton deletarInformaçãoButton;
    private Curso cur;
    private CursoDAO dao;
    public TelaInicial(Curso cur, CursoDAO dao){
        this.cur = cur;
        this.dao = dao;
        setContentPane(PanelInicial);
        setSize(800,600);
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        InscricaoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new TelaInscricao(cur, dao);
            }
        });
        AlunosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new TelaAluno(cur, dao);
            }
        });
        CursosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new TelaCurso(cur, dao);
            }
        });
        relatoriosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            dispose();
            new TelaRelatorio(cur, dao);
            }
        });
        sairButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }
}
