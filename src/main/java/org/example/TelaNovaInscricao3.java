package org.example;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Collections;

public class TelaNovaInscricao3 extends JFrame {
    private JPanel panel1;
    private JFormattedTextField formattedTextField1;
    private JLabel curso;
    private JLabel aluno;
    private JTextField obs;
    private JButton inscreverButton;
    private JButton sairButton;
    private JTextField valor;
    private JLabel txt;
    private JTextField idInscricao;

    public TelaNovaInscricao3(Curso cur, CursoDAO dao, int id_aluno, String nome_aluno, int id_curso, String nome_curso) {
        setContentPane(panel1);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        curso.setText("Código: " + id_curso + " // Nome curso: " + nome_curso);
        aluno.setText("Código: " + id_aluno + " // Nome aluno: " + nome_aluno);

        if (cur.getId_inscricao() != null || !cur.getId_inscricao().isEmpty()) {
            int maior = Collections.max(cur.getId_inscricao()) + 1;
            idInscricao.setText(String.valueOf(maior));
        } else {
            idInscricao.setText("1");
        }

        formattedTextField1.setFormatterFactory(new DefaultFormatterFactory(createFormatter()));
        valor.setText(".00");

        inscreverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String formattedDate = formattedTextField1.getText();
                String formattedValue = valor.getText();
                if (isValidDate(formattedDate)) {
                    try {
                        double numericValue = Double.parseDouble(formattedValue);

                        int idInscricaoSelecionado = Integer.parseInt(idInscricao.getText());

                        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                        LocalDate date = LocalDate.parse(formattedDate, inputFormatter);

                        String formattedDateOutput = date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

                        cur.addInscricao(idInscricaoSelecionado, id_aluno, id_curso, date, obs.getText(), numericValue);
                        dao.salvar(cur);

                        String mensagem = String.format("Id da inscrição: %d\nId do aluno: %d\nNome aluno: %s\nId curso: %d\nNome curso: %s\nObservação: %s\nData da inscrição: %s\nValor pago: %.2f",
                                idInscricaoSelecionado, id_aluno, nome_aluno, id_curso, nome_curso, obs.getText(), formattedDateOutput, numericValue);

                        JOptionPane.showMessageDialog(TelaNovaInscricao3.this, mensagem);
                        dispose();
                        new TelaInicial(cur, dao);
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(TelaNovaInscricao3.this, "Por favor, insira um valor numérico válido.");
                    } catch (DateTimeParseException ex) {
                        JOptionPane.showMessageDialog(TelaNovaInscricao3.this, "Por favor, preencha a data corretamente.");
                    } catch (CodigoExistenteException ex) {
                        throw new RuntimeException(ex);
                    }
                } else {
                    JOptionPane.showMessageDialog(TelaNovaInscricao3.this, "Por favor, preencha a data corretamente.");
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

    private boolean isValidDate(String formattedDate) {
        return !formattedDate.contains("_");
    }

    private MaskFormatter createFormatter() {
        MaskFormatter formatter = null;
        try {
            formatter = new MaskFormatter("##/##/####");
            formatter.setPlaceholderCharacter('_');
            formatter.setAllowsInvalid(false);
            formatter.setOverwriteMode(true);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return formatter;
    }
}
