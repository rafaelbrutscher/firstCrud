package org.example;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class TelaEditAluno extends JFrame{
    private JPanel PanelEditAluno;
    private JTextField nomealuno;
    private JLabel idaluno;
    private JButton salvarButton;
public TelaEditAluno(Curso cur, CursoDAO dao, int id_aluno, String nome_aluno) {

    setContentPane(PanelEditAluno);
    setSize(600,400);
    setLocationRelativeTo(null);
    setVisible(true);
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

    idaluno.setText(String.valueOf(id_aluno));

    for(int i=0;i<cur.getIdAluno().size();i++){
        if(id_aluno == cur.getIdAluno().get(i)){
            nomealuno.setText(cur.getAluno().get(i));
        }
    }
    salvarButton.setEnabled(false);
    nomealuno.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(!nomealuno.getText().isEmpty()){
                salvarButton.setEnabled(true);
            }
        }
    });
    salvarButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            try{
                if(cur.editAluno(id_aluno, nomealuno.getText())) {
                    JOptionPane.showMessageDialog(null, "Nome salvo", "Confirmação", JOptionPane.INFORMATION_MESSAGE);
                    dao.editarAluno(Integer.parseInt(idaluno.getText()), nomealuno.getText());
                    dispose();
                    new TelaInicial(cur, dao);
            }
            } catch (CodigoNaoExisteException ex) {
                JOptionPane.showMessageDialog(null, ex, "Erro", JOptionPane.ERROR);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
    });
}
}
