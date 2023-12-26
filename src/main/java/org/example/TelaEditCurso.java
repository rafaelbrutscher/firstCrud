package org.example;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class TelaEditCurso extends JFrame{
    private JPanel panel1;
    private JTextField nomecurso;
    private JButton salvarButton;
    private JLabel idcurso;
public TelaEditCurso(Curso cur, CursoDAO dao, int id_curso) {
    setContentPane(panel1);
    setSize(600,400);
    setLocationRelativeTo(null);
    setVisible(true);
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

    for(int i=0; i<cur.getId_curso().size();i++){
        if(cur.getId_curso().get(i) == id_curso){
            nomecurso.setText(cur.getNomecurso().get(i));
        }
    }
    idcurso.setText(String.valueOf(id_curso));
    salvarButton.setEnabled(false);

    nomecurso.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(!nomecurso.getText().isEmpty()){
                salvarButton.setEnabled(true);
            }
        }
    });
    salvarButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                if(cur.editCurso(Integer.parseInt(idcurso.getText()), nomecurso.getText())){
                    JOptionPane.showMessageDialog(null, "Nome salvo", "Confirmação", JOptionPane.INFORMATION_MESSAGE);
                    dao.editarCurso(Integer.parseInt(idcurso.getText()), nomecurso.getText());
                    dispose();
                    new TelaInicial(cur, dao);
                }
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            } catch (CodigoNaoExisteException ex) {
                throw new RuntimeException(ex);
            }
        }
    });
}
}
