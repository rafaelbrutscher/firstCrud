package org.example;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public class Curso {
    private ArrayList<Integer> id_aluno;
    private ArrayList<String> aluno;
    private ArrayList<Integer> id_curso;
    private ArrayList<String> nomecurso;
    private ArrayList<Integer> id_inscricao;
    private ArrayList<Integer> id_aluno_inscrito;
    private ArrayList<Integer> id_curso_inscrito;
    private ArrayList<LocalDate> data_inscricao;
    private ArrayList<String> observacao;
    private ArrayList<Double> valor;
    public Curso(ArrayList<Integer> id_aluno, ArrayList<String> aluno, ArrayList<Integer> id_curso, ArrayList<String> nomecurso, ArrayList<Integer> id_inscricao,
                 ArrayList<Integer> id_aluno_inscrito, ArrayList<Integer> id_curso_inscrito, ArrayList<LocalDate> data_inscricao, ArrayList<String> observacao, ArrayList<Double> valor){
        this.id_inscricao = id_inscricao;
        this.id_aluno_inscrito = id_aluno_inscrito;
        this.id_curso_inscrito = id_curso_inscrito;
        this.data_inscricao = data_inscricao;
        this.observacao = observacao;
        this.valor = valor;
        this.id_aluno = id_aluno;
        this.aluno = aluno;
        this.id_curso = id_curso;
        this.nomecurso = nomecurso;
    }
    public Curso(){
        id_inscricao = new ArrayList<>();
        id_aluno_inscrito = new ArrayList<>();
        id_curso_inscrito = new ArrayList<>();
        data_inscricao = new ArrayList<>();
        observacao = new ArrayList<>();
        valor = new ArrayList<>();
        id_aluno = new ArrayList<>();
        aluno = new ArrayList<>();
        id_curso = new ArrayList<>();
        nomecurso = new ArrayList<>();
    }
    public boolean addInscricao(int id_inscricao, int id_aluno_inscrito, int id_curso_inscrito, LocalDate data_inscricao, String obs, double valor) throws CodigoExistenteException{
        if(this.id_inscricao != null){
            for(int i=0; i < this.id_inscricao.size(); i++){
                if(this.id_inscricao.get(i).equals((id_inscricao))){
                    throw new CodigoExistenteException();
                }
            }
        }
        this.id_inscricao.add(id_inscricao);
        this.id_aluno_inscrito.add(id_aluno_inscrito);
        this.id_curso_inscrito.add(id_curso_inscrito);
        this.data_inscricao.add(data_inscricao);
        this.observacao.add(obs);
        this.valor.add(valor);
        return true;
    }
    public boolean addAluno(int id_aluno, String nome) throws CodigoExistenteException{
        if(this.id_aluno != null) {
            for(int i = 0; i < this.id_aluno.size(); i++) {
                if (this.id_aluno.get(i).equals(id_aluno)) {
                    throw new CodigoExistenteException();
                }
            }
        }
        this.id_aluno.add(id_aluno);
        aluno.add(nome);

        return true;
    }
    public boolean addCurso(int id_curso, String nome_curso) throws CodigoExistenteException{
        if(this.id_curso != null){
            for(int i=0; i<this.id_curso.size(); i++){
                if(this.id_curso.get(i).equals(id_curso)){
                    throw new CodigoExistenteException();
                }
            }
        }
        this.id_curso.add(id_curso);
        this.nomecurso.add(nome_curso);
        return true;
    }
    public boolean editCurso(int id_curso, String nome_curso)throws CodigoNaoExisteException{
        if(this.id_curso !=null){
            for(int i=0;i<this.id_curso.size();i++){
                if(this.id_curso.get(i) == id_curso){
                    this.nomecurso.set(i, nome_curso);
                    return true;
                }
            }
        }
        throw new CodigoNaoExisteException();
    }
    public boolean editInscricao(int id_inscricao, String obs, LocalDate data, double valor)throws  CodigoNaoExisteException{
        if(this.id_inscricao != null || !this.id_inscricao.isEmpty()){
            for(int i=0;i<this.id_inscricao.size();i++){
                if(this.id_inscricao.get(i) == id_inscricao){
                    if(!observacao.get(i).equals(obs)){
                        observacao.set(i, obs);
                    }
                    if(!data_inscricao.get(i).equals(data)){
                        data_inscricao.set(i, data);
                    }
                    if(this.valor.get(i) != valor){
                        this.valor.set(i, valor);
                    }
                }
            }
            return true;
        }
        throw new CodigoNaoExisteException();
    }
    public boolean deleteAluno(int id_aluno)throws CodigoNaoExisteException{
        if(this.id_aluno != null && !this.id_aluno.isEmpty()){
            for(int i=0;i<this.id_aluno.size();i++){
                if(this.id_aluno.get(i) == id_aluno){
                    this.id_aluno.remove(i);
                    this.aluno.remove(i);
                    return true;
                }
            }
        }
        throw new CodigoNaoExisteException();
    }
    public boolean deleteCurso(int id_curso) throws CodigoNaoExisteException{
        if(this.id_curso != null && !this.id_curso.isEmpty()){
            for(int i=0;i<this.id_curso.size();i++){
                if(this.id_curso.get(i) == id_curso){
                    this.id_curso.remove(i);
                    this.nomecurso.remove(i);
                    return true;
                }
            }
        }
        throw new CodigoNaoExisteException();
    }
    public boolean deleteInscricao(int id_inscricao) throws CodigoNaoExisteException{
        if(this.id_inscricao != null && !this.id_inscricao.isEmpty()){
            for(int i=0;i<this.id_inscricao.size();i++){
                if(this.id_inscricao.get(i) == id_inscricao){
                    this.id_inscricao.remove(i);
                    this.id_aluno_inscrito.remove(i);
                    this.id_curso_inscrito.remove(i);
                    this.data_inscricao.remove(i);
                    this.observacao.remove(i);
                    this.valor.remove(i);
                    return true;
                }
            }
        }
        throw new CodigoNaoExisteException();
    }
    public boolean editAluno(int id_aluno, String novonome)throws CodigoNaoExisteException{
        if (this.id_aluno != null && !this.id_aluno.isEmpty()) {
            for (int i = 0; i < this.id_aluno.size(); i++) {
                if (this.id_aluno.get(i).equals(id_aluno)) {

                    this.aluno.set(i, novonome);
                    return true;
                }
            }
        }
        throw new CodigoNaoExisteException();
    }
    public ArrayList<Integer> getIdAluno(){
        return id_aluno;
    }

    public ArrayList<String> getAluno() {
        return aluno;
    }

    public ArrayList<Integer> getId_curso() {
        return id_curso;
    }

    public ArrayList<String> getNomecurso() {
        return nomecurso;
    }

    public ArrayList<Integer> getId_inscricao() {
        return id_inscricao;
    }

    public ArrayList<String> getObservacao() {
        return observacao;
    }

    public ArrayList<Integer> getId_curso_inscrito() {
        return id_curso_inscrito;
    }

    public ArrayList<Integer> getId_aluno_inscrito() {
        return id_aluno_inscrito;
    }

    public ArrayList<Double> getValor() {
        return valor;
    }

    public ArrayList<LocalDate> getData_inscricao() {
        return data_inscricao;
    }
}
