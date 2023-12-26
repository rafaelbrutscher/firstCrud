package org.example;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TelaAddCurso extends JFrame{
    private JPanel PanelAddCurso;
    private JTextField nomecurso;
    private JTextField idcurso;
    private JButton adicionarbutton;
    private JButton sair;
    private Curso cur;
    private CursoDAO dao;
public TelaAddCurso(Curso cur, CursoDAO dao) {
    this.cur = cur;
    this.dao = dao;
    setContentPane(PanelAddCurso);
    setSize(800,600);
    setLocationRelativeTo(null);
    setVisible(true);
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

    adicionarbutton.setEnabled(false);

    if(cur.getId_curso() != null) {
        int maior = cur.getId_curso().get(0);
        for(int i=0;i<cur.getId_curso().size();i++){
            if(cur.getId_curso().get(i)> maior){
                maior = cur.getId_curso().get(i);
            }
        }
        idcurso.setText(String.valueOf(maior + 1));
    }

    idcurso.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(!idcurso.getText().isEmpty() && !nomecurso.getText().isEmpty()){
                adicionarbutton.setEnabled(true);
            }
        }
    });

    adicionarbutton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            try{
                if(cur.addCurso(Integer.parseInt(idcurso.getText()), nomecurso.getText())){
                    dao.salvar(cur);
                    JOptionPane.showMessageDialog(null, "Curso adicionado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                    new TelaAddCurso(cur, dao);
                }
            }
            catch (CodigoExistenteException ex){
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    });
    sair.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            dispose();
            new TelaInicial(cur, dao);
        }
    });
}
}