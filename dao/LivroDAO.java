package dao;

import entities.Livro;
import java.util.List;

public interface LivroDAO {
    void salvar(Livro livro);
    void atualizar(Livro livro);
    void deletar(String titulo);
    List<Livro> listar(String ordem, String busca);
}