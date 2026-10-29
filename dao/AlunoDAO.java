package dao;

import entities.Aluno;
import java.util.List;

public interface AlunoDAO {
    void salvar(Aluno aluno);
    void atualizar(Aluno aluno);
    void deletar(String ra);
    List<Aluno> listar(String ordem, String busca);
}