package entities;

import java.sql.Date;

public class Emprestimo {
    private int id;
    private Aluno aluno;
    private Livro livro;
    private Date dataEmprestimo;
    private String status; // NOVO CAMPO

    public Emprestimo(int id, Aluno aluno, Livro livro, Date dataEmprestimo, String status) {
        this.id = id;
        this.aluno = aluno;
        this.livro = livro;
        this.dataEmprestimo = dataEmprestimo;
        this.status = status;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public Aluno getAluno() { return aluno; }
    public void setAluno(Aluno aluno) { this.aluno = aluno; }

    public Livro getLivro() { return livro; }
    public void setLivro(Livro livro) { this.livro = livro; }

    public Date getDataEmprestimo() { return dataEmprestimo; }
    public void setDataEmprestimo(Date dataEmprestimo) { this.dataEmprestimo = dataEmprestimo; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    @Override
    public String toString() {
        return aluno.getNome() + " - " + livro.getTitulo() + " (" + status + ")";
    }
}