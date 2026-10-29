package view;

import entities.*;
import java.awt.event.ActionListener;
import java.util.List;

public interface View {
    void mostrarAlunos(List<Aluno> lista);
    void mostrarLivros(List<Livro> lista);
    void mostrarEmprestimos(List<Emprestimo> lista);
    
    Aluno getAlunoDoUsuario() throws Exception;
    String getRaAlunoSelecionado() throws Exception;
    String getAlunoOrdem();
    String getAlunoBusca();

    Livro getLivroDoUsuario() throws Exception;
    String getTituloLivroSelecionado() throws Exception;
    String getLivroOrdem();
    String getLivroBusca();

    Emprestimo getEmprestimoDoUsuario() throws Exception;
    int getIdEmprestimoSelecionado() throws Exception;

    void atualizarCombos(List<Aluno> alunos, List<Livro> livros);
    void mostrarMensagem(String msg);

    // Listeners de Ações
    void addAcaoSalvarAluno(ActionListener al);
    void addAcaoAtualizarAluno(ActionListener al);
    void addAcaoDeletarAluno(ActionListener al);
    void addAcaoFiltrarAluno(ActionListener al);

    void addAcaoSalvarLivro(ActionListener al);
    void addAcaoAtualizarLivro(ActionListener al);
    void addAcaoDeletarLivro(ActionListener al);
    void addAcaoFiltrarLivro(ActionListener al);

    void addAcaoSalvarEmprestimo(ActionListener al);
    void addAcaoDeletarEmprestimo(ActionListener al);
}