package mavenrest.exemplo3;

import authrest.AutenticacaoDAO;
import authrest.AutenticacaoUser;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

public class UsuarioDAO implements AutenticacaoDAO {

    @PersistenceContext
    EntityManager em;

    public List<Usuario> getAllUsers() {
        return em.createQuery("SELECT u from Usuario u").getResultList();
    }

    public Usuario getUser(long id) {
        return em.find(Usuario.class, id);
    }

    public Usuario getUserByEmail(String email) {
        try {
            return (Usuario) em.createQuery("SELECT u from Usuario u WHERE u.email = ?1").setParameter(1, email).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Transactional
    public void createUser(String nome, String email, String senha, String permissao) throws NoSuchAlgorithmException, InvalidKeySpecException {
        Usuario user = new Usuario(nome, email, senha, permissao);
        em.persist(user);
    }

    public List<Usuario> search(String termo, String valor) {
        Query query;
        if (termo.equals("id")) {
            query = em.createQuery("SELECT u FROM Usuario u WHERE u.id = ?1");
            query.setParameter(1, Long.valueOf(valor));
        } else {
            query = em.createQuery("SELECT u FROM Usuario u WHERE u." + termo + " LIKE ?1");
            query.setParameter(1, "%" + valor + "%");
        }
        return query.getResultList();
    }

    @Transactional
    public void delete(Long id) {
        em.remove(getUser(id));
    }

    @Transactional
    public void updateUser(long id, String nome, String email, String senha) throws NoSuchAlgorithmException, InvalidKeySpecException {
        Usuario u = em.find(Usuario.class, id);
        u.setEmail(email);
        u.setName(nome);
        if (senha != null && !"".equals(senha)) {
            u.setPassword(senha);//so atualiza a senha se foi modificada
        }
    }

    @Override
    public AutenticacaoUser getSubject(String subject) {
        return getUserByEmail(subject);
    }

}
