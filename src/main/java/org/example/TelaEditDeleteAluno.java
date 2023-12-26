package org.example;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

public class TelaEditDeleteAluno extends JFrame{
    private JPanel PanelEditDeleteAluno;
    private JList list1;
    private JButton editarAlunoButton;
    private JButton deletarAlunoButton;
    private JButton sairButton;
    private JScrollPane scrollpane;
    private JLabel txt;
    private Curso cur;
    private CursoDAO dao;
    public TelaEditDeleteAluno(Curso cur, CursoDAO dao){
        this.cur = cur;
        this.dao = dao;

        setContentPane(PanelEditDeleteAluno);
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

        editarAlunoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (list1.getSelectedIndex() != -1) {
                    String itemSelecionado = (String) list1.getSelectedValue();
                    String[] parts = itemSelecionado.split(" - ");
                    int idAlunoSelecionado = Integer.parseInt(parts[0]);
                    String nomeAlunoSelecionado = parts[1];

                    dispose();
                    new TelaEditAluno(cur, dao, idAlunoSelecionado, nomeAlunoSelecionado);
                }
                else{
                    txt.setText("Selecione um aluno");
                }
            }
        });
        deletarAlunoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (list1.getSelectedIndex() != -1) {
                    try {
                        cur.deleteAluno(cur.getIdAluno().get(list1.getSelectedIndex()));
                        dao.excluirAlunosNaoPresentes(cur);
                        JOptionPane.showMessageDialog(null, "Aluno deletado", "Confirmação", JOptionPane.INFORMATION_MESSAGE);
                        dispose();
                        new TelaInicial(cur, dao);
                    } catch (CodigoNaoExisteException ex) {
                        throw new RuntimeException(ex);
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
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
