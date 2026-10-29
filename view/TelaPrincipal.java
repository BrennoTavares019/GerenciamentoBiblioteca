package view;

import controller.BibliotecaController;
import dao.*;
import entities.*;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.util.List;

public class TelaPrincipal extends JFrame implements View {

    private JTextField txtAlunoRA = new JTextField(8), txtAlunoNome = new JTextField(10), txtAlunoEmail = new JTextField(10), txtAlunoBusca = new JTextField(8);
    private JComboBox<String> cbAlunoOrdem = new JComboBox<>(new String[]{"nome", "ra"});
    private JTable tabelaAlunos = new JTable();
    private DefaultTableModel modelAlunos = new DefaultTableModel(new String[]{"RA", "Nome", "Email"}, 0);
    private JButton btnSalvarAluno = new JButton("Salvar"), btnAtualizarAluno = new JButton("Atualizar"), btnDelAluno = new JButton("Apagar"), btnBuscarAluno = new JButton("Filtrar");

    private JTextField txtLivroTitulo = new JTextField(8), txtLivroAutor = new JTextField(8), txtLivroAno = new JTextField(4), txtLivroQtd = new JTextField(3), txtLivroBusca = new JTextField(8);
    private JComboBox<String> cbLivroOrdem = new JComboBox<>(new String[]{"titulo", "autor"});
    private JTable tabelaLivros = new JTable();
    private DefaultTableModel modelLivros = new DefaultTableModel(new String[]{"Título", "Autor", "Ano", "Qtd"}, 0);
    private JButton btnSalvarLivro = new JButton("Salvar"), btnAtualizarLivro = new JButton("Atualizar"), btnDelLivro = new JButton("Apagar"), btnBuscarLivro = new JButton("Filtrar");

    private JComboBox<Aluno> cbEmpAluno = new JComboBox<>();
    private JComboBox<Livro> cbEmpLivro = new JComboBox<>();
    private JTable tabelaEmprestimos = new JTable();
    private DefaultTableModel modelEmprestimos = new DefaultTableModel(new String[]{"ID", "Aluno", "Livro", "Data", "Status"}, 0);
    private JButton btnSalvarEmp = new JButton("Emprestar"), btnDelEmp = new JButton("Devolver Livro");

    public TelaPrincipal() {
        setTitle("Sistema Biblioteca Unip Campinas");
        setSize(980, 550);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JTabbedPane abas = new JTabbedPane();
        abas.addTab("Alunos", criarPainelAlunos());
        abas.addTab("Livros", criarPainelLivros());
        abas.addTab("Empréstimos", criarPainelEmprestimos());
        add(abas);

        tabelaAlunos.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent event) {
                int row = tabelaAlunos.getSelectedRow();
                if (row >= 0) {
                    txtAlunoRA.setText((String) modelAlunos.getValueAt(row, 0));
                    txtAlunoNome.setText((String) modelAlunos.getValueAt(row, 1));
                    txtAlunoEmail.setText((String) modelAlunos.getValueAt(row, 2));
                    txtAlunoRA.setEditable(false);
                }
            }
        });

        tabelaLivros.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent event) {
                int row = tabelaLivros.getSelectedRow();
                if (row >= 0) {
                    txtLivroTitulo.setText((String) modelLivros.getValueAt(row, 0));
                    txtLivroAutor.setText((String) modelLivros.getValueAt(row, 1));
                    txtLivroAno.setText(String.valueOf(modelLivros.getValueAt(row, 2)));
                    txtLivroQtd.setText(String.valueOf(modelLivros.getValueAt(row, 3)));
                    txtLivroTitulo.setEditable(false);
                }
            }
        });

        new BibliotecaController(new AlunoDAOSQL(), new LivroDAOSQL(), new EmprestimoDAOSQL(), this);
    }

    private JPanel criarPainelAlunos() {
        JPanel p = new JPanel(new BorderLayout()), f = new JPanel();
        f.add(new JLabel("RA:")); f.add(txtAlunoRA);
        f.add(new JLabel("Nome:")); f.add(txtAlunoNome);
        f.add(new JLabel("Email:")); f.add(txtAlunoEmail);
        f.add(btnSalvarAluno); f.add(btnAtualizarAluno); f.add(btnDelAluno); 
        f.add(new JLabel("Busca:")); f.add(txtAlunoBusca); f.add(btnBuscarAluno); f.add(cbAlunoOrdem);

        tabelaAlunos.setModel(modelAlunos);
        p.add(f, BorderLayout.NORTH); p.add(new JScrollPane(tabelaAlunos), BorderLayout.CENTER);
        return p;
    }

    private JPanel criarPainelLivros() {
        JPanel p = new JPanel(new BorderLayout()), f = new JPanel();
        f.add(new JLabel("Título:")); f.add(txtLivroTitulo);
        f.add(new JLabel("Autor:")); f.add(txtLivroAutor);
        f.add(new JLabel("Ano:")); f.add(txtLivroAno);
        f.add(new JLabel("Qtd:")); f.add(txtLivroQtd);
        f.add(btnSalvarLivro); f.add(btnAtualizarLivro); f.add(btnDelLivro); 
        f.add(new JLabel("Busca:")); f.add(txtLivroBusca); f.add(btnBuscarLivro); f.add(cbLivroOrdem);

        tabelaLivros.setModel(modelLivros);
        p.add(f, BorderLayout.NORTH); p.add(new JScrollPane(tabelaLivros), BorderLayout.CENTER);
        return p;
    }

    private JPanel criarPainelEmprestimos() {
        JPanel p = new JPanel(new BorderLayout()), f = new JPanel();
        f.add(new JLabel("Aluno:")); f.add(cbEmpAluno);
        f.add(new JLabel("Livro:")); f.add(cbEmpLivro);
        f.add(btnSalvarEmp); f.add(btnDelEmp);

        tabelaEmprestimos.setModel(modelEmprestimos);
        p.add(f, BorderLayout.NORTH); p.add(new JScrollPane(tabelaEmprestimos), BorderLayout.CENTER);
        return p;
    }

    public void addAcaoSalvarAluno(ActionListener al) { btnSalvarAluno.addActionListener(al); }
    public void addAcaoAtualizarAluno(ActionListener al) { btnAtualizarAluno.addActionListener(al); }
    public void addAcaoDeletarAluno(ActionListener al) { btnDelAluno.addActionListener(al); }
    public void addAcaoFiltrarAluno(ActionListener al) { 
        btnBuscarAluno.addActionListener(al); 
        cbAlunoOrdem.addActionListener(al);
        txtAlunoBusca.addActionListener(al);
    }

    public void addAcaoSalvarLivro(ActionListener al) { btnSalvarLivro.addActionListener(al); }
    public void addAcaoAtualizarLivro(ActionListener al) { btnAtualizarLivro.addActionListener(al); }
    public void addAcaoDeletarLivro(ActionListener al) { btnDelLivro.addActionListener(al); }
    public void addAcaoFiltrarLivro(ActionListener al) { 
        btnBuscarLivro.addActionListener(al); 
        cbLivroOrdem.addActionListener(al);
        txtLivroBusca.addActionListener(al);
    }

    public void addAcaoSalvarEmprestimo(ActionListener al) { btnSalvarEmp.addActionListener(al); }
    public void addAcaoDeletarEmprestimo(ActionListener al) { btnDelEmp.addActionListener(al); }

    public Aluno getAlunoDoUsuario() throws Exception {
        if(txtAlunoRA.getText().isEmpty() || txtAlunoNome.getText().isEmpty()) 
            throw new Exception("RA e Nome do aluno são obrigatórios.");
        return new Aluno(0, txtAlunoRA.getText(), txtAlunoNome.getText(), txtAlunoEmail.getText());
    }

    public String getRaAlunoSelecionado() throws Exception {
        int row = tabelaAlunos.getSelectedRow();
        if(row < 0) throw new Exception("Selecione um aluno na tabela.");
        return (String) modelAlunos.getValueAt(row, 0);
    }

    public String getAlunoOrdem() { return (String) cbAlunoOrdem.getSelectedItem(); }
    public String getAlunoBusca() { return txtAlunoBusca.getText(); }

    public Livro getLivroDoUsuario() throws Exception {
        if(txtLivroTitulo.getText().isEmpty()) throw new Exception("Título do livro é obrigatório.");
        int ano = Integer.parseInt(txtLivroAno.getText());
        int qtd = Integer.parseInt(txtLivroQtd.getText());
        return new Livro(0, txtLivroTitulo.getText(), txtLivroAutor.getText(), ano, qtd);
    }

    public String getTituloLivroSelecionado() throws Exception {
        int row = tabelaLivros.getSelectedRow();
        if(row < 0) throw new Exception("Selecione um livro na tabela.");
        return (String) modelLivros.getValueAt(row, 0);
    }

    public String getLivroOrdem() { return (String) cbLivroOrdem.getSelectedItem(); }
    public String getLivroBusca() { return txtLivroBusca.getText(); }

    public Emprestimo getEmprestimoDoUsuario() throws Exception {
        Aluno a = (Aluno) cbEmpAluno.getSelectedItem();
        int lIdx = cbEmpLivro.getSelectedIndex();
        if(a == null || lIdx < 0) throw new Exception("Selecione um aluno e um livro válidos.");
        Livro l = (Livro) cbEmpLivro.getSelectedItem();
        return new Emprestimo(0, a, l, new Date(System.currentTimeMillis()), "Ativo");
    }

    public int getIdEmprestimoSelecionado() throws Exception {
        int row = tabelaEmprestimos.getSelectedRow();
        if(row < 0) throw new Exception("Selecione um empréstimo na tabela.");
        return (int) modelEmprestimos.getValueAt(row, 0);
    }

    public void mostrarAlunos(List<Aluno> lista) {
        modelAlunos.setRowCount(0);
        for (Aluno a : lista) {
            modelAlunos.addRow(new Object[]{a.getRa(), a.getNome(), a.getEmail()});
        }
        txtAlunoRA.setEditable(true);
        txtAlunoRA.setText(""); txtAlunoNome.setText(""); txtAlunoEmail.setText("");
    }

    public void mostrarLivros(List<Livro> lista) {
        modelLivros.setRowCount(0);
        for (Livro l : lista) {
            modelLivros.addRow(new Object[]{l.getTitulo(), l.getAutor(), l.getAno(), l.getQuantidade()});
        }
        txtLivroTitulo.setEditable(true);
        txtLivroTitulo.setText(""); txtLivroAutor.setText(""); txtLivroAno.setText(""); txtLivroQtd.setText("");
    }

    public void mostrarEmprestimos(List<Emprestimo> lista) {
        modelEmprestimos.setRowCount(0);
        for (Emprestimo e : lista) {
            modelEmprestimos.addRow(new Object[]{e.getId(), e.getAluno().getNome(), e.getLivro().getTitulo(), e.getDataEmprestimo(), e.getStatus()});
        }
    }

    public void atualizarCombos(List<Aluno> alunos, List<Livro> livros) {
        cbEmpAluno.removeAllItems();
        for (Aluno a : alunos) cbEmpAluno.addItem(a);
        cbEmpLivro.removeAllItems();
        // Filtra para exibir nos combos apenas livros que têm estoque maior que 0
        for (Livro l : livros) {
            if (l.getQuantidade() > 0) {
                cbEmpLivro.addItem(l);
            }
        }
    }

    public void mostrarMensagem(String msg) {
        JOptionPane.showMessageDialog(this, msg);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TelaPrincipal().setVisible(true));
    }
}