package dao;

import entities.Livro;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LivroDAOSQL implements LivroDAO {
    @Override
    public void salvar(Livro l) {
        String sql = "INSERT INTO livro (titulo, autor, ano, quantidade) VALUES (?, ?, ?, ?)";
        try (Connection conn = ConnectionFactory.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, l.getTitulo()); 
            stmt.setString(2, l.getAutor()); 
            stmt.setInt(3, l.getAno());
            stmt.setInt(4, l.getQuantidade());
            stmt.executeUpdate();
        } catch (SQLException ex) { throw new RuntimeException(ex); }
    }

    @Override
    public void atualizar(Livro l) {
        String sql = "UPDATE livro SET autor = ?, ano = ?, quantidade = ? WHERE titulo = ?";
        try (Connection conn = ConnectionFactory.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, l.getAutor()); 
            stmt.setInt(2, l.getAno()); 
            stmt.setInt(3, l.getQuantidade());
            stmt.setString(4, l.getTitulo());
            stmt.executeUpdate();
        } catch (SQLException ex) { throw new RuntimeException(ex); }
    }

    @Override
    public void deletar(String titulo) {
        String sql = "DELETE FROM livro WHERE titulo = ?";
        try (Connection conn = ConnectionFactory.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, titulo); stmt.executeUpdate();
        } catch (SQLException ex) { throw new RuntimeException("Não é possível apagar livro com empréstimo ativo."); }
    }

    @Override
    public List<Livro> listar(String ordem, String busca) {
        List<Livro> lista = new ArrayList<>();
        String col = "autor".equalsIgnoreCase(ordem) ? "autor" : "titulo";
        String sql = "SELECT * FROM livro WHERE titulo LIKE ? ORDER BY " + col;
        try (Connection conn = ConnectionFactory.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + (busca == null ? "" : busca) + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                lista.add(new Livro(
                    rs.getInt("id_livro"), 
                    rs.getString("titulo"), 
                    rs.getString("autor"), 
                    rs.getInt("ano"),
                    rs.getInt("quantidade")
                ));
            }
        } catch (SQLException ex) { throw new RuntimeException(ex); }
        return lista;
    }
}