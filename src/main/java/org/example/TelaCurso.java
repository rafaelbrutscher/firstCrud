package org.example;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TelaCurso extends JFrame{
    private JButton adicionarCursoButton;
    private JPanel panelCurso;
    private JButton editarDeletarCursoButton;
    private JButton sairButton;
public TelaCurso(Curso cur, CursoDAO dao) {

    setContentPane(panelCurso);
    setSize(800,600);
    setLocationRelativeTo(null);
    setVisible(true);
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

    adicionarCursoButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            dispose();
            new TelaAddCurso(cur, dao);
        }
    });
    editarDeletarCursoButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            dispose();
            new TelaEditDeleteCurso(cur, dao);
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
