package mavenrest.autenticacao;

import javax.inject.Inject;
import mavenrest.auth.AutenticacaoFiltro;
import mavenrest.auth.AutenticacaoUser;
import mavenrest.user.UserDAO;

/**
 *
 * @author pedro
 */
public class UsuarioAuthFiltro extends AutenticacaoFiltro {

    @Inject
    UserDAO userDAO;

    @Override
    public AutenticacaoUser getUser(String subject) {
        return userDAO.getUserByEmail(subject);
    }

}
