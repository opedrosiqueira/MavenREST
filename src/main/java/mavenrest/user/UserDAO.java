package mavenrest.user;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

public class UserDAO {

    @PersistenceContext
    EntityManager em;

    public List<User> getAllUsers() {
        return em.createQuery("SELECT u from User u").getResultList();
    }

    public User getUser(long id) {
        return em.find(User.class, id);
    }

    public User getUserByEmail(String email) {
        return (User) em.createQuery("SELECT u from User u WHERE u.email = ?1").setParameter(1, email).getSingleResult();
    }

    @Transactional
    public void createUser(String nome, String email, String senha) throws NoSuchAlgorithmException, InvalidKeySpecException {
        User user = new User(nome, email, senha);
        em.persist(user);
    }

    public List<User> search(String termo, String valor) {
        String q = "SELECT u FROM User u WHERE u." + termo + " = ?1";
        Query query = em.createQuery(q);
        if (termo.equals("id")) {
            query.setParameter(1, Long.valueOf(valor));
        } else {
            query.setParameter(1, valor);
        }
        return query.getResultList();
    }

    @Transactional
    public void delete(Long id) {
        em.remove(getUser(id));
    }

    @Transactional
    public void updateUser(long id, String nome, String email, String senha) throws NoSuchAlgorithmException, InvalidKeySpecException {
        User u = em.find(User.class, id);
        u.setEmail(email);
        u.setNome(nome);
        if (senha != null && !"".equals(senha)) {
            u.setSenha(senha);//so atualiza a senha se foi modificada
        }
    }
}
