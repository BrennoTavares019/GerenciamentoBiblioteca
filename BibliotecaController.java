package controller;

import dao.*;
import entities.*;
import view.View;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class BibliotecaController {
    
    private AlunoDAO alunoDAO;
    private LivroDAO livroDAO;
    private EmprestimoDAO emprestimoDAO;
    private View view;

    public BibliotecaController(AlunoDAO alunoDAO, LivroDAO livroDAO, EmprestimoDAO emprestimoDAO, View view) {
        this.alunoDAO = alunoDAO;
        this.livroDAO = livroDAO;
        this.emprestimoDAO = emprestimoDAO;
        this.view = view;

        this.view.addAcaoSalvarAluno(new AcaoSalvarAluno());
        this.view.addAcaoAtualizarAluno(new AcaoAtualizarAluno());
        this.view.addAcaoDeletarAluno(new AcaoDeletarAluno());
        this.view.addAcaoFiltrarAluno(new AcaoFiltrarAluno());

        this.view.addAcaoSalvarLivro(new AcaoSalvarLivro());
        this.view.addAcaoAtualizarLivro(new AcaoAtualizarLivro());
        this.view.addAcaoDeletarLivro(new AcaoDeletarLivro());
        this.view.addAcaoFiltrarLivro(new AcaoFiltrarLivro());

        this.view.addAcaoSalvarEmprestimo(new AcaoSalvarEmprestimo());
        this.view.addAcaoDeletarEmprestimo(new AcaoDeletarEmprestimo());

        atualizarTabelas();
    }

    public void atualizarTabelas() {
        view.mostrarAlunos(alunoDAO.listar(view.getAlunoOrdem(), view.getAlunoBusca()));
        view.mostrarLivros(livroDAO.listar(view.getLivroOrdem(), view.getLivroBusca()));
        view.mostrarEmprestimos(emprestimoDAO.listar());
        view.atualizarCombos(alunoDAO.listar("nome", ""), livroDAO.listar("titulo", ""));
    }

    // --- ALUNOS ---
    class AcaoSalvarAluno implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                Aluno a = view.getAlunoDoUsuario();
                alunoDAO.salvar(a);
                view.mostrarMensagem("Aluno salvo com sucesso!");
                atualizarTabelas();
            } catch (Exception ex) { view.mostrarMensagem("Erro: " + ex.getMessage()); }
        }
    }

    class AcaoAtualizarAluno implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                Aluno a = view.getAlunoDoUsuario();
                alunoDAO.atualizar(a);
                view.mostrarMensagem("Aluno atualizado com sucesso!");
                atualizarTabelas();
            } catch (Exception ex) { view.mostrarMensagem("Erro: " + ex.getMessage()); }
        }
    }

    class AcaoDeletarAluno implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String ra = view.getRaAlunoSelecionado();
                alunoDAO.deletar(ra);
                view.mostrarMensagem("Aluno removido com sucesso!");
                atualizarTabelas();
            } catch (Exception ex) { view.mostrarMensagem("Erro: " + ex.getMessage()); }
        }
    }

    class AcaoFiltrarAluno implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) { atualizarTabelas(); }
    }

    // --- LIVROS ---
    class AcaoSalvarLivro implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                Livro l = view.getLivroDoUsuario();
                livroDAO.salvar(l);
                view.mostrarMensagem("Livro salvo com sucesso!");
                atualizarTabelas();
            } catch (Exception ex) { view.mostrarMensagem("Erro: " + ex.getMessage()); }
        }
    }

    class AcaoAtualizarLivro implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                Livro l = view.getLivroDoUsuario();
                livroDAO.atualizar(l);
                view.mostrarMensagem("Livro atualizado com sucesso!");
                atualizarTabelas();
            } catch (Exception ex) { view.mostrarMensagem("Erro: " + ex.getMessage()); }
        }
    }

    class AcaoDeletarLivro implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String titulo = view.getTituloLivroSelecionado();
                livroDAO.deletar(titulo);
                view.mostrarMensagem("Livro removido com sucesso!");
                atualizarTabelas();
            } catch (Exception ex) { view.mostrarMensagem("Erro: " + ex.getMessage()); }
        }
    }

    class AcaoFiltrarLivro implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) { atualizarTabelas(); }
    }

    // --- EMPRÉSTIMOS ---
    class AcaoSalvarEmprestimo implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                Emprestimo emp = view.getEmprestimoDoUsuario();
                emprestimoDAO.salvar(emp);
                view.mostrarMensagem("Empréstimo realizado com sucesso!");
                atualizarTabelas();
            } catch (Exception ex) { view.mostrarMensagem("Erro: " + ex.getMessage()); }
        }
    }

    class AcaoDeletarEmprestimo implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                int id = view.getIdEmprestimoSelecionado();
                emprestimoDAO.deletar(id);
                view.mostrarMensagem("Empréstimo devolvido/excluído com sucesso!");
                atualizarTabelas();
            } catch (Exception ex) { view.mostrarMensagem("Erro: " + ex.getMessage()); }
        }
    }
}