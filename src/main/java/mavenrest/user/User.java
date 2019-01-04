package mavenrest.user;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import static mavenrest.autenticacao.AutenticacaoHash.gerarHash;
import static mavenrest.autenticacao.AutenticacaoHash.gerarSaltAleatorio;

@Entity
public class User {

    @Id
    @GeneratedValue
    private long id;

    @NotEmpty(message = "Nome nao pode estar vazio")
    private String nome;

    @Column(unique = true)
    @NotEmpty
    @Email(message = "Email deve ser valido")
    private String email;

    private String senha;

    private String salt;

    public User() {
    }

    public User(String nome, String email, String senha) throws NoSuchAlgorithmException, InvalidKeySpecException {
        this.nome = nome;
        this.email = email;
        this.setSenha(senha);
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

    public void setSenha(String senha) throws NoSuchAlgorithmException, InvalidKeySpecException {
        this.salt = gerarSaltAleatorio();
        this.senha = gerarHash(senha, salt);
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

}
