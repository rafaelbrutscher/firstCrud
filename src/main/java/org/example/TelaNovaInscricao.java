package org.example;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class TelaNovaInscricao extends JFrame{
    private JPanel panel1;
    private JList list1;
    private JButton selecionarAlunoButton;
    private JButton sairButton;
    private JScrollPane scrollpane;
    private JLabel txt;

    public TelaNovaInscricao(Curso cur, CursoDAO dao) {
        setContentPane(panel1);
        setSize(800,600);
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

    ArrayList<String> alunos = new ArrayList<>();

    for(int i =0; i<cur.getIdAluno().size();i++){
        alunos.add(cur.getIdAluno().get(i) + " - " + cur.getAluno().get(i));
    }

    DefaultListModel<String> listModel = new DefaultListModel<>();
    for(String aluno : alunos){
        listModel.addElement(aluno);
    }

    list1.setModel(listModel);

    scrollpane.setViewportView(list1);

    selecionarAlunoButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (list1.getSelectedIndex() != -1) {
                String itemSelecionado = (String) list1.getSelectedValue();
                String[] parts = itemSelecionado.split(" - ");
                int idAlunoSelecionado = Integer.parseInt(parts[0]);
                String nomeAlunoSelecionado = parts[1];
                dispose();
                new TelaNovaInscricao2(cur, dao, idAlunoSelecionado, nomeAlunoSelecionado);
            }
            else{
                txt.setText("Selecione um aluno");
            }

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
