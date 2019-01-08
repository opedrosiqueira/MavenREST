package mavenrest.exemplo3;

import authrest.AuthUser;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.Email;

@Entity
public class Usuario extends AuthUser {

    @Column(unique = true, nullable = false)
    @Email(message = "Email deve ser valido")
    private String email;

    public Usuario() {
    }

    public Usuario(String name, String email, String password, String role) throws NoSuchAlgorithmException, InvalidKeySpecException {
        super(name, password, role);
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
