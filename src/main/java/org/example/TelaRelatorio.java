package org.example;

import javax.swing.*;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class TelaRelatorio extends JFrame{
    private JPanel panel1;
    private JComboBox comboBox1;
    private JButton gerarRelatórioButton;
    private JLabel relatorio;
    private JTextField id;
    private JButton sairButton;
    private JFormattedTextField data1;
    private JFormattedTextField data2;

    public TelaRelatorio(Curso cur, CursoDAO dao) {
    setContentPane(panel1);
    setSize(600, 400);
    setLocationRelativeTo(null);
    setVisible(true);
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

    data1.setFormatterFactory(new DefaultFormatterFactory(createFormatter()));
    data2.setFormatterFactory(new DefaultFormatterFactory(createFormatter()));

    DefaultComboBoxModel<String> comboBoxModel = new DefaultComboBoxModel<>();
    comboBoxModel.addElement("Valor total em cursos");
    comboBoxModel.addElement("Valor investido por aluno");
    comboBoxModel.addElement("Valor em curso");

    comboBox1.setModel(comboBoxModel);
    gerarRelatórioButton.setEnabled(false);
    gerarRelatórioButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
                String textoData1 = data1.getText();
                String textoData2 = data2.getText();
                LocalDate localDate1 = LocalDate.parse(textoData1, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                LocalDate localDate2 = LocalDate.parse(textoData2, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                if(comboBox1.getSelectedIndex() == 0){
                    try {
                        relatorio.setText("Valor total de todos os cursos: R$" + dao.gerarRelatorioValorTotalCursos(localDate1, localDate2) + " nesse período");
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                }
                if(comboBox1.getSelectedIndex() == 1){
                    if(id != null && !id.getText().isEmpty()){
                        boolean ok=false;
                        for(int i=0; i<cur.getIdAluno().size();i++){
                            if(cur.getIdAluno().get(i) == Integer.parseInt(id.getText())){
                                ok = true;
                                try {
                                    relatorio.setText("O aluno(a) " + cur.getAluno().get(i) + " gastou um total de R$" + dao.gerarRelatorioAlunoInvestiu(localDate1, localDate2, Integer.parseInt(id.getText())) + " nesse período");
                                } catch (SQLException ex) {
                                    throw new RuntimeException(ex);
                                }
                            }
                        }
                        if(!ok){
                            relatorio.setText("Não existe aluno nesse código");
                        }
                    }
                    else{
                        relatorio.setText("Selecione um código de aluno");
                    }
                }
                if(comboBox1.getSelectedIndex() == 2){
                    if(id != null && !id.getText().isEmpty()){
                        boolean ok=false;
                        for(int i=0; i<cur.getId_curso().size();i++){
                            if(cur.getId_curso().get(i) == Integer.parseInt(id.getText())){
                                ok = true;
                                try {
                                    relatorio.setText("O curso " + cur.getNomecurso().get(i) + " vendeu um total de R$" + dao.gerarRelatorioValorCurso(localDate1, localDate2, Integer.parseInt(id.getText())) + " nesse período");
                                } catch (SQLException ex) {
                                    throw new RuntimeException(ex);
                                }
                            }
                        }
                        if(!ok){
                            relatorio.setText("Não existe curso nesse código");
                        }
                    }
                    else{
                        relatorio.setText("Selecione um código de curso");
                    }
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
        data2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(isValidDate(String.valueOf(data1)) && isValidDate(String.valueOf(data2))){
                    gerarRelatórioButton.setEnabled(true);
                }
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
