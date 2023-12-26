package org.example;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TelaAddAluno extends JFrame{
    private JPanel PanelAddAluno;
    private JTextField nomealuno;
    private JTextField idaluno;
    private JButton adicionar;
    private JButton sair;
    private Curso cur;
    private CursoDAO dao;
    public TelaAddAluno(Curso cur, CursoDAO dao){
        this.cur = cur;
        this.dao = dao;
        setContentPane(PanelAddAluno);
        setSize(800,600);
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        adicionar.setEnabled(false);

        // O comando abaixo usei para deixar pré-definido como id do aluno o próximo da lista, mas é possível mudar
        // se o usuário desejar
        if(cur.getIdAluno() != null) {
            int maior = cur.getIdAluno().get(0);
            for(int i=0;i<cur.getIdAluno().size();i++){
                if(cur.getIdAluno().get(i)> maior){
                    maior = cur.getIdAluno().get(i);
                }
            }
            idaluno.setText(String.valueOf(maior + 1));
        }

        adicionar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if(cur.addAluno(Integer.parseInt(idaluno.getText()), nomealuno.getText())){
                        dao.salvar(cur);
                        JOptionPane.showMessageDialog(null, "Aluno adicionado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                        dispose();
                        new TelaAddAluno(cur, dao);
                    }
                }
                catch(CodigoExistenteException ex){
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        sair.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new TelaInicial(cur, dao);
            }
        });
        idaluno.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!nomealuno.getText().isEmpty() && !idaluno.getText().isEmpty()){
                    adicionar.setEnabled(true);
                }
            }
        });
    }
}