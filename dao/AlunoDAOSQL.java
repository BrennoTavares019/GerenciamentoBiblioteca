package dao;

import entities.Aluno;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AlunoDAOSQL implements AlunoDAO {
    @Override
    public void salvar(Aluno a) {
        String sql = "INSERT INTO aluno (ra, nome, email) VALUES (?, ?, ?)";
        try (Connection conn = ConnectionFactory.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, a.getRa()); stmt.setString(2, a.getNome()); stmt.setString(3, a.getEmail());
            stmt.executeUpdate();
        } catch (SQLException ex) { throw new RuntimeException(ex); }
    }

    @Override
    public void atualizar(Aluno a) {
        String sql = "UPDATE aluno SET nome = ?, email = ? WHERE ra = ?";
        try (Connection conn = ConnectionFactory.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, a.getNome()); stmt.setString(2, a.getEmail()); stmt.setString(3, a.getRa());
            stmt.executeUpdate();
        } catch (SQLException ex) { throw new RuntimeException(ex); }
    }

    @Override
    public void deletar(String ra) {
        String sql = "DELETE FROM aluno WHERE ra = ?";
        try (Connection conn = ConnectionFactory.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, ra); stmt.executeUpdate();
        } catch (SQLException ex) { throw new RuntimeException("Não é possível apagar aluno com empréstimo ativo."); }
    }

    @Override
    public List<Aluno> listar(String ordem, String busca) {
        List<Aluno> lista = new ArrayList<>();
        String col = "ra".equalsIgnoreCase(ordem) ? "ra" : "nome";
        String sql = "SELECT * FROM aluno WHERE nome LIKE ? ORDER BY " + col;
        try (Connection conn = ConnectionFactory.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + (busca == null ? "" : busca) + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) lista.add(new Aluno(rs.getInt("id_aluno"), rs.getString("ra"), rs.getString("nome"), rs.getString("email")));
        } catch (SQLException ex) { throw new RuntimeException(ex); }
        return lista;
    }
}