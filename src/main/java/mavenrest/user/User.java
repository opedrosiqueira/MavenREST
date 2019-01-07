package mavenrest.user;

import authrest.AutenticacaoUser;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.Email;

@Entity
public class User extends AutenticacaoUser {

    @Column(unique = true, nullable = false)
    @Email(message = "Email deve ser valido")
    private String email;

    public User() {
    }

    public User(String name, String email, String senha) throws NoSuchAlgorithmException, InvalidKeySpecException {
        super(name, senha);
        this.email = email;
    }

    public User(String name, String email, String senha, String role) throws NoSuchAlgorithmException, InvalidKeySpecException {
        super(name, senha, role);
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return this.getName() + ", " + this.getEmail();
    }
}
