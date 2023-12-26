package org.example;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class TelaNovaInscricao2 extends JFrame{
    private JPanel panel1;
    private JList list1;
    private JButton selecionarCursoButton;
    private JButton sairButton;
    private JLabel txt;
    private JScrollPane scrollpane;

    public TelaNovaInscricao2(Curso cur, CursoDAO dao, int id_aluno, String nome_aluno) {
        setContentPane(panel1);
        setSize(800,600);
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        ArrayList<String> cursos = new ArrayList<>();

        for(int i =0; i<cur.getId_curso().size();i++){
            cursos.add(cur.getId_curso().get(i) + " - " + cur.getNomecurso().get(i));
        }

        DefaultListModel<String> listModel = new DefaultListModel<>();
        for(String curso : cursos){
            listModel.addElement(curso);
        }

        list1.setModel(listModel);

        scrollpane.setViewportView(list1);


    selecionarCursoButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (list1.getSelectedIndex() != -1) {
                String itemSelecionado = (String) list1.getSelectedValue();
                String[] parts = itemSelecionado.split(" - ");
                int idCursoSelecionado = Integer.parseInt(parts[0]);
                String nomeCursoSelecionado = parts[1];
                dispose();
                new TelaNovaInscricao3(cur, dao, id_aluno, nome_aluno, idCursoSelecionado, nomeCursoSelecionado);
            }
            else{
                txt.setText("Selecione um curso");
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
