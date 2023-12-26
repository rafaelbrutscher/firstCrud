package org.example;

public class Main {
    public static void main(String[] args) {
        CursoDAO dao = new CursoDAO();
        Curso cur = dao.ler();
        new TelaInicial(cur, dao);
    }
}