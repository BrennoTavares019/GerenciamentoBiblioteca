package entities;

public class Aluno {
    private int id;
    private String ra, nome, email;

    public Aluno(int id, String ra, String nome, String email) {
        this.id = id; this.ra = ra; this.nome = nome; this.email = email;
    }
    public int getId() { return id; }
    public String getRa() { return ra; }
    public String getNome() { return nome; }
    public String getEmail() { return email; }
    @Override public String toString() { return nome + " (" + ra + ")"; }
}