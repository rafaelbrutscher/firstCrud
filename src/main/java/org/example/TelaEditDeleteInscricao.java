package org.example;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class TelaEditDeleteInscricao extends JFrame{
    private JPanel panel1;
    private JList list1;
    private JButton editarInscriçãoButton;
    private JButton deletarInscriçãoButton;
    private JButton sairButton;
    private JScrollPane scrollpane;
    private JLabel txt;

    public TelaEditDeleteInscricao(Curso cur, CursoDAO dao) {
    setContentPane(panel1);
    setSize(800,600);
    setLocationRelativeTo(null);
    setVisible(true);
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

    ArrayList<String> inscricoes = new ArrayList<>();

    for(int i =0; i<cur.getId_inscricao().size();i++){
        String aluno = "";
        String curso = "";
        for(int a=0;a<cur.getIdAluno().size();a++){
            if(cur.getId_aluno_inscrito().get(i) == cur.getIdAluno().get(a)){
                aluno = cur.getAluno().get(a);
            }
        }
        for(int c=0;c<cur.getId_curso().size();c++){
            if(cur.getId_curso_inscrito().get(i) == cur.getId_curso().get(c)){
                curso = cur.getNomecurso().get(c);
            }
        }
        inscricoes.add(cur.getId_inscricao().get(i) + " - " + cur.getId_aluno_inscrito().get(i) + " - " + aluno +
                " - " + cur.getId_curso_inscrito().get(i) + " - " + curso + " - " + cur.getData_inscricao().get(i) +
                " - " + cur.getObservacao().get(i) + " - " + cur.getValor().get(i));
    }

    DefaultListModel<String> listModel = new DefaultListModel<>();
    for(String inscricao : inscricoes){
        listModel.addElement(inscricao);
    }

    list1.setModel(listModel);

    scrollpane.setViewportView(list1);


    editarInscriçãoButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (list1.getSelectedIndex() != -1) {

                String itemSelecionado = (String) list1.getSelectedValue();
                String[] parts = itemSelecionado.split(" - ");
                int idInscricao = Integer.parseInt(parts[0]);

                dispose();
                new TelaEditInscricao(cur, dao, idInscricao);

            }
            else {
                txt.setText("Selecione uma inscrição");
            }
        }
    });
    deletarInscriçãoButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (list1.getSelectedIndex() != -1) {

                String itemSelecionado = (String) list1.getSelectedValue();
                String[] parts = itemSelecionado.split(" - ");
                int idInscricao = Integer.parseInt(parts[0]);

                try {
                    cur.deleteInscricao(idInscricao);
                    dao.excluirInscricoesNaoPresentes(cur);
                    JOptionPane.showMessageDialog(null, "Inscrição deletada", "Confirmação", JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                    new TelaInicial(cur, dao);

                } catch (CodigoNaoExisteException ex) {
                    throw new RuntimeException(ex);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
            else {
                txt.setText("Selecione uma inscrição");
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
