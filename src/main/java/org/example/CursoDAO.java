package org.example;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;

public class CursoDAO {
    private static final String URL = "jdbc:postgresql://localhost:5432/curso";
    private static final String USUARIO = "postgres";
    private static final String SENHA = "postgres";

    public void salvar(Curso cur) {
        Connection conexao = null;
        PreparedStatement preparedStatement = null;

        try {
            conexao = DriverManager.getConnection(URL, USUARIO, SENHA);

            for (int i = 0; i < cur.getAluno().size(); i++) {
                int idAluno = cur.getIdAluno().get(i);
                String nomeAluno = cur.getAluno().get(i);

                if (!alunoExiste(idAluno, conexao)) {
                    String sqlAluno = "INSERT INTO aluno (id_aluno, nome_aluno) VALUES (?, ?)";
                    preparedStatement = conexao.prepareStatement(sqlAluno);

                    preparedStatement.setInt(1, idAluno);
                    preparedStatement.setString(2, nomeAluno);

                    preparedStatement.executeUpdate();
                    preparedStatement.close();
                }
            }

            for (int i = 0; i < cur.getId_curso().size(); i++) {
                int idCurso = cur.getId_curso().get(i);
                String nomeCurso = cur.getNomecurso().get(i);

                if (!cursoExiste(idCurso, conexao)) {
                    String sqlCurso = "INSERT INTO curso (id_curso, nome_curso) VALUES (?, ?)";
                    preparedStatement = conexao.prepareStatement(sqlCurso);

                    preparedStatement.setInt(1, idCurso);
                    preparedStatement.setString(2, nomeCurso);

                    preparedStatement.executeUpdate();
                    preparedStatement.close();
                }
            }

            for(int i=0; i<cur.getId_inscricao().size();i++){
                int idInscricao = cur.getId_inscricao().get(i);
                int idAlunoInscrito = cur.getId_aluno_inscrito().get(i);
                int idCursoInscrito = cur.getId_curso_inscrito().get(i);
                LocalDate localDate = cur.getData_inscricao().get(i);
                String obs = cur.getObservacao().get(i);
                double valor = cur.getValor().get(i);

                if(!inscricaoExiste(idInscricao, conexao)){
                    String sqlInscricao = "INSERT INTO inscricao (id_inscricao, data_inscricao, id_curso, id_aluno, observacao, valor) VALUES (?, ?, ?, ?, ?, ?)";
                    preparedStatement = conexao.prepareStatement(sqlInscricao);

                    preparedStatement.setInt(1, idInscricao);
                    preparedStatement.setDate(2, Date.valueOf(localDate));
                    preparedStatement.setInt(3, idCursoInscrito);
                    preparedStatement.setInt(4, idAlunoInscrito);
                    preparedStatement.setString(5, obs);
                    preparedStatement.setDouble(6, valor);

                    preparedStatement.executeUpdate();
                    preparedStatement.close();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (conexao != null) {
                    conexao.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public Curso ler() {
        Connection conexao = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        Curso cur = new Curso();

        try {
            conexao = DriverManager.getConnection(URL, USUARIO, SENHA);

            String sqlAluno = "SELECT * FROM aluno ORDER BY id_aluno";
            preparedStatement = conexao.prepareStatement(sqlAluno);

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int idAluno = resultSet.getInt("id_aluno");
                String nomeAluno = resultSet.getString("nome_aluno");

                cur.addAluno(idAluno, nomeAluno);
            }

            String sqlCurso = "SELECT * FROM curso ORDER BY id_curso";
            preparedStatement = conexao.prepareStatement(sqlCurso);

            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int idCurso = resultSet.getInt("id_curso");
                String nomeCurso = resultSet.getString("nome_curso");

                cur.addCurso(idCurso, nomeCurso);
            }

            String sqlinscricao = "SELECT * FROM inscricao ORDER BY data_inscricao ";
            preparedStatement = conexao.prepareStatement(sqlinscricao);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                int id_inscricao = resultSet.getInt("id_inscricao");
                Date data_inscricao = resultSet.getDate("data_inscricao");
                int id_curso = resultSet.getInt("id_curso");
                int id_aluno = resultSet.getInt("id_aluno");
                String obs = resultSet.getString("observacao");
                double valor = resultSet.getDouble("valor");

                cur.addInscricao(id_inscricao, id_aluno, id_curso, data_inscricao.toLocalDate(), obs, valor);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (CodigoExistenteException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (conexao != null) {
                    conexao.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return cur;
    }

    private boolean alunoExiste(int idAluno, Connection conexao) throws SQLException {
        String sql = "SELECT COUNT(*) FROM aluno WHERE id_aluno = ?";
        try (PreparedStatement preparedStatement = conexao.prepareStatement(sql)) {
            preparedStatement.setInt(1, idAluno);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    return count > 0;
                }
            }
        }
        return false;
    }

    private boolean cursoExiste(int id_curso, Connection conexao) throws SQLException {
        String sql = "SELECT COUNT(*) FROM curso WHERE id_curso = ?";
        try (PreparedStatement preparedStatement = conexao.prepareStatement(sql)) {
            preparedStatement.setInt(1, id_curso);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    return count > 0;
                }
            }
        }
        return false;
    }
    private boolean inscricaoExiste(int id_inscricao, Connection conexao)throws SQLException{
        String sql = "SELECT COUNT(*) FROM inscricao WHERE id_inscricao = ?";
        try(PreparedStatement preparedStatement = conexao.prepareStatement(sql)){
            preparedStatement.setInt(1, id_inscricao);
            try(ResultSet resultSet = preparedStatement.executeQuery()){
                if(resultSet.next()){
                    int count = resultSet.getInt(1);
                    return count > 0;
                }
            }
        }
        return false;
    }

    public void excluirAlunosNaoPresentes(Curso cur) throws SQLException {
        Connection conexao = null;
        PreparedStatement preparedStatement = null;

        ArrayList<Integer> idAlunos = cur.getIdAluno();

        try {
            conexao = DriverManager.getConnection(URL, USUARIO, SENHA);

            if(idAlunos == null || idAlunos.isEmpty()){
                String sql = "DELETE FROM aluno";
                preparedStatement = conexao.prepareStatement(sql);
                preparedStatement.executeUpdate();
            }
            else {
                String sql = "DELETE FROM aluno WHERE id_aluno NOT IN (" + String.join(",", Collections.nCopies(idAlunos.size(), "?")) + ")";
                preparedStatement = conexao.prepareStatement(sql);

                for (int i = 0; i < idAlunos.size(); i++) {
                    preparedStatement.setInt(i + 1, idAlunos.get(i));
                }

                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (conexao != null) {
                    conexao.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    public void excluirCursosNaoPresentes(Curso cur) throws SQLException{
        Connection conexao = null;
        PreparedStatement preparedStatement = null;

        ArrayList<Integer> idCursos = cur.getId_curso();

        try {
            conexao = DriverManager.getConnection(URL, USUARIO, SENHA);

            if(idCursos == null || idCursos.isEmpty()){
                String sql = "DELETE FROM curso";
                preparedStatement = conexao.prepareStatement(sql);
                preparedStatement.executeUpdate();
            }
            else {
                String sql = "DELETE FROM curso WHERE id_curso NOT IN (" + String.join(",", Collections.nCopies(idCursos.size(), "?")) + ")";
                preparedStatement = conexao.prepareStatement(sql);

                for (int i = 0; i < idCursos.size(); i++) {
                    preparedStatement.setInt(i + 1, idCursos.get(i));
                }

                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (conexao != null) {
                    conexao.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    public void excluirInscricoesNaoPresentes(Curso cur) throws SQLException {
        Connection conexao = null;
        PreparedStatement preparedStatement = null;
        ArrayList<Integer> idInscricao = cur.getId_inscricao();

        try {
            conexao = DriverManager.getConnection(URL, USUARIO, SENHA);

                if(idInscricao == null || idInscricao.isEmpty()){
                    String sql = "DELETE FROM inscricao";
                    preparedStatement = conexao.prepareStatement(sql);
                    preparedStatement.executeUpdate();
                }
                else {
                    String sql = "DELETE FROM inscricao WHERE id_inscricao NOT IN (" + String.join(",", Collections.nCopies(idInscricao.size(), "?")) + ")";
                    preparedStatement = conexao.prepareStatement(sql);

                    for (int i = 0; i < idInscricao.size(); i++) {
                        preparedStatement.setInt(i + 1, idInscricao.get(i));
                    }
                    preparedStatement.executeUpdate();
                }
            }
        catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (conexao != null) {
                    conexao.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void editarAluno(int idAluno, String nomeAluno) throws SQLException {
        Connection conexao = null;
        PreparedStatement preparedStatement = null;

        try {
            conexao = DriverManager.getConnection(URL, USUARIO, SENHA);

            String sql = "UPDATE aluno SET nome_aluno = ? WHERE id_aluno = ?";
            preparedStatement = conexao.prepareStatement(sql);

            preparedStatement.setString(1, nomeAluno);
            preparedStatement.setInt(2, idAluno);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (conexao != null) {
                    conexao.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    public void editarCurso(int idCurso, String nomeCurso) throws SQLException {
        Connection conexao = null;
        PreparedStatement preparedStatement = null;

        try {
            conexao = DriverManager.getConnection(URL, USUARIO, SENHA);

            String sql = "UPDATE curso SET nome_curso = ? WHERE id_curso = ?";
            preparedStatement = conexao.prepareStatement(sql);

            preparedStatement.setString(1, nomeCurso);
            preparedStatement.setInt(2, idCurso);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (conexao != null) {
                    conexao.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    public void editarInscricao(int idInscricao, String obs, LocalDate data, double valor)throws SQLException{
        Connection conexao = null;
        PreparedStatement preparedStatement = null;

        try {
            conexao = DriverManager.getConnection(URL, USUARIO, SENHA);

            String sql = "UPDATE inscricao SET data_inscricao = ?, observacao = ?, valor = ? WHERE id_inscricao = ?";
            preparedStatement = conexao.prepareStatement(sql);

            preparedStatement.setDate(1, java.sql.Date.valueOf(data));
            preparedStatement.setString(2, obs);
            preparedStatement.setDouble(3, valor);
            preparedStatement.setInt(4, idInscricao);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (conexao != null) {
                    conexao.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    public double gerarRelatorioValorTotalCursos(LocalDate dataInicial, LocalDate dataFinal)throws SQLException{
        Connection conexao = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            conexao = DriverManager.getConnection(URL, USUARIO, SENHA);

            String sql = "SELECT SUM(valor) AS total FROM inscricao WHERE data_inscricao BETWEEN ? AND ?";
            preparedStatement = conexao.prepareStatement(sql);

            java.sql.Date sqlDataInicial = java.sql.Date.valueOf(dataInicial);
            java.sql.Date sqlDataFinal = java.sql.Date.valueOf(dataFinal);

            preparedStatement.setDate(1, sqlDataInicial);
            preparedStatement.setDate(2, sqlDataFinal);

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getDouble("total");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (conexao != null) {
                    conexao.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return 0;
    }
    public double gerarRelatorioAlunoInvestiu(LocalDate dataInicial, LocalDate dataFinal, int idAluno) throws SQLException{
        Connection conexao = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            conexao = DriverManager.getConnection(URL, USUARIO, SENHA);

            String sql = "SELECT SUM(valor) AS total FROM inscricao WHERE data_inscricao BETWEEN ? AND ? AND id_aluno = ?";
            preparedStatement = conexao.prepareStatement(sql);

            java.sql.Date sqlDataInicial = java.sql.Date.valueOf(dataInicial);
            java.sql.Date sqlDataFinal = java.sql.Date.valueOf(dataFinal);

            preparedStatement.setDate(1, sqlDataInicial);
            preparedStatement.setDate(2, sqlDataFinal);
            preparedStatement.setInt(3, idAluno);

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getDouble("total");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (conexao != null) {
                    conexao.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return 0;
    }
    public double gerarRelatorioValorCurso(LocalDate dataInicial, LocalDate dataFinal, int idCurso) throws SQLException{
        Connection conexao = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            conexao = DriverManager.getConnection(URL, USUARIO, SENHA);

            String sql = "SELECT SUM(valor) AS total FROM inscricao WHERE data_inscricao BETWEEN ? AND ? AND id_curso = ?";
            preparedStatement = conexao.prepareStatement(sql);

            java.sql.Date sqlDataInicial = java.sql.Date.valueOf(dataInicial);
            java.sql.Date sqlDataFinal = java.sql.Date.valueOf(dataFinal);

            preparedStatement.setDate(1, sqlDataInicial);
            preparedStatement.setDate(2, sqlDataFinal);
            preparedStatement.setInt(3, idCurso);

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getDouble("total");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (conexao != null) {
                    conexao.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return 0;
    }

}
