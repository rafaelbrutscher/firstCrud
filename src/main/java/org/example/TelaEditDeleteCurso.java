package org.example;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

public class TelaEditDeleteCurso extends JFrame{
    private JPanel panel1;
    private JList list1;
    private JButton deletarCursoButton;
    private JButton editarCursoButton;
    private JButton sairButton;
    private JScrollPane scrollpane;
    private JLabel txt;

    public TelaEditDeleteCurso(Curso cur, CursoDAO dao) {
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

    editarCursoButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (list1.getSelectedIndex() != -1) {
                String itemSelecionado = (String) list1.getSelectedValue();
                String[] parts = itemSelecionado.split(" - ");
                int idCursoSelecionado = Integer.parseInt(parts[0]);
                String nomeCursoSelecionado = parts[1];

                dispose();
                new TelaEditCurso(cur, dao, idCursoSelecionado);
            }
            else {
                txt.setText("Selecione um curso");
            }
        }
    });
    deletarCursoButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (list1.getSelectedIndex() != -1) {
                try {
                    cur.deleteCurso(cur.getId_curso().get(list1.getSelectedIndex()));
                    dao.excluirCursosNaoPresentes(cur);
                    JOptionPane.showMessageDialog(null, "Curso deletado", "Confirmação", JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                    new TelaInicial(cur, dao);
                } catch (CodigoNaoExisteException ex) {
                    throw new RuntimeException(ex);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
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
