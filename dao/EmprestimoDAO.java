package dao;

import entities.Emprestimo;
import java.util.List;

public interface EmprestimoDAO {
    void salvar(Emprestimo emprestimo);
    void deletar(int id);
    List<Emprestimo> listar();
}