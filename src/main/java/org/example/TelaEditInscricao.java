package org.example;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

public class TelaEditInscricao extends JFrame {
    private JPanel panel1;
    private JLabel id_inscricao;
    private JLabel aluno;
    private JTextField obs;
    private JLabel curso;
    private JFormattedTextField data;
    private JTextField valor;
    private JButton salvarButton;
public TelaEditInscricao(Curso cur, CursoDAO dao, int idInscricao) {
    setContentPane(panel1);
    setSize(600, 400);
    setLocationRelativeTo(null);
    setVisible(true);
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

    id_inscricao.setText(String.valueOf(idInscricao));

    salvarButton.setEnabled(false);

    for(int i=0;i<cur.getId_inscricao().size();i++){
        if(idInscricao == cur.getId_inscricao().get(i)) {
            int idaluno = cur.getId_aluno_inscrito().get(i);
            for (int a = 0; a < cur.getIdAluno().size(); a++) {
                if (cur.getIdAluno().get(a) == idaluno) {
                    aluno.setText(idaluno + " - " + cur.getAluno().get(a));
                }
            }
            obs.setText(cur.getObservacao().get(i));

                int idcurso = cur.getId_curso_inscrito().get(i);
                for (int c = 0; c < cur.getId_curso().size(); c++) {
                    if (idcurso == cur.getId_curso().get(c)) {
                        curso.setText(idcurso + " - " + cur.getNomecurso().get(c));
                    }
                }
            data.setText(String.valueOf(cur.getData_inscricao().get(i)));
            valor.setText(String.valueOf(cur.getValor().get(i)));
        }
    }

    salvarButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date dataInscricaoDate = dateFormat.parse(data.getText());
                LocalDate dataInscricao = dataInscricaoDate.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();
                double valorInscricao = Double.parseDouble(valor.getText());
                try {
                    cur.editInscricao(idInscricao, obs.getText(), dataInscricao, valorInscricao);
                    dao.editarInscricao(idInscricao, obs.getText(), dataInscricao, valorInscricao);
                    JOptionPane.showMessageDialog(null, "Inscrição salvo", "Confirmação", JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                    new TelaInicial(cur, dao);
                } catch (CodigoNaoExisteException ex) {
                    throw new RuntimeException(ex);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
            catch (ParseException ex) {
                throw new RuntimeException(ex);
            }
        }
    });
    valor.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(!data.getText().isEmpty() && !valor.getText().isEmpty()){
                salvarButton.setEnabled(true);
            }
        }
    });
}
}
