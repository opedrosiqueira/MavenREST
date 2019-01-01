package mavenrest;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Entity
public class User {

    @Id
    @GeneratedValue
    private long id;

    @NotEmpty(message = "Nome nao pode estar vazio")
    private String nome;

    @Column(unique = true)
    @NotEmpty
    @Email(message = "Email should be valid")
    private String email;

    @NotEmpty(message = "Senha nao pode estar vazia")
    private String senha;

    public User() {
    }

    public User(String nome, String email, String password) {
        this.nome = nome;
        this.email = email;
        this.senha = password;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

}
