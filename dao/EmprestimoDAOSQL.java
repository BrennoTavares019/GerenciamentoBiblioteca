package dao;

import entities.Emprestimo;
import entities.Aluno;
import entities.Livro;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmprestimoDAOSQL implements EmprestimoDAO {
    @Override
    public void salvar(Emprestimo e) {
        if (e.getLivro().getQuantidade() <= 0) {
            throw new RuntimeException("Livro esgotado! Não há unidades disponíveis.");
        }

        String sqlEmp = "INSERT INTO emprestimo (id_aluno, id_livro, data_emprestimo, status) VALUES (?, ?, ?, 0)";
        String sqlLivro = "UPDATE livro SET quantidade = quantidade - 1 WHERE id_livro = ?";

        Connection conn = null;
        try {
            conn = ConnectionFactory.getConnection();
            conn.setAutoCommit(false);

            try (PreparedStatement stmt1 = conn.prepareStatement(sqlEmp);
                 PreparedStatement stmt2 = conn.prepareStatement(sqlLivro)) {
                
                stmt1.setInt(1, e.getAluno().getId());
                stmt1.setInt(2, e.getLivro().getId());
                stmt1.setDate(3, e.getDataEmprestimo());
                stmt1.executeUpdate();

                stmt2.setInt(1, e.getLivro().getId());
                stmt2.executeUpdate();

                conn.commit();
            } catch (SQLException ex) {
                conn.rollback();
                throw ex;
            }
        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao realizar empréstimo: " + ex.getMessage());
        }
    }

    @Override
    public void deletar(int id) {
        // Atualiza o status para 1 (Devolvido)
        String sqlBuscaLivro = "SELECT id_livro FROM emprestimo WHERE id_emprestimo = ?";
        String sqlUpdateStatus = "UPDATE emprestimo SET status = 1 WHERE id_emprestimo = ?";
        String sqlLivro = "UPDATE livro SET quantidade = quantidade + 1 WHERE id_livro = ?";

        Connection conn = null;
        try {
            conn = ConnectionFactory.getConnection();
            conn.setAutoCommit(false);

            int idLivro = -1;
            try (PreparedStatement stmtBusca = conn.prepareStatement(sqlBuscaLivro)) {
                stmtBusca.setInt(1, id);
                ResultSet rs = stmtBusca.executeQuery();
                if (rs.next()) idLivro = rs.getInt("id_livro");
            }

            if (idLivro != -1) {
                try (PreparedStatement stmtUp = conn.prepareStatement(sqlUpdateStatus);
                     PreparedStatement stmtLivro = conn.prepareStatement(sqlLivro)) {
                    
                    stmtUp.setInt(1, id);
                    stmtUp.executeUpdate();

                    stmtLivro.setInt(1, idLivro);
                    stmtLivro.executeUpdate();

                    conn.commit();
                } catch (SQLException ex) {
                    conn.rollback();
                    throw ex;
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao devolver empréstimo: " + ex.getMessage());
        }
    }

    @Override
    public List<Emprestimo> listar() {
        List<Emprestimo> lista = new ArrayList<>();
        // Traz apenas os empréstimos com status 0 (ativos)
        String sql = "SELECT e.id_emprestimo, e.data_emprestimo, e.status, a.id_aluno, a.ra, a.nome, a.email, l.id_livro, l.titulo, l.autor, l.ano, l.quantidade " +
                     "FROM emprestimo e JOIN aluno a ON e.id_aluno = a.id_aluno JOIN livro l ON e.id_livro = l.id_livro " +
                     "WHERE e.status = 0";
        try (Connection conn = ConnectionFactory.getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Aluno a = new Aluno(rs.getInt("id_aluno"), rs.getString("ra"), rs.getString("nome"), rs.getString("email"));
                Livro l = new Livro(rs.getInt("id_livro"), rs.getString("titulo"), rs.getString("autor"), rs.getInt("ano"), rs.getInt("quantidade"));
                lista.add(new Emprestimo(rs.getInt("id_emprestimo"), a, l, rs.getDate("data_emprestimo"), String.valueOf(rs.getInt("status"))));
            }
        } catch (SQLException ex) { throw new RuntimeException(ex); }
        return lista;
    }
}