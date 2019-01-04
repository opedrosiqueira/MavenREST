package mavenrest;

import java.util.Set;
import javax.ws.rs.core.Application;

/**
 *
 * @author pedro
 */
@javax.ws.rs.ApplicationPath("rest")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }

    /**
     * Do not modify addRestResourceClasses() method. It is automatically
     * populated with all resources defined in the project. If required, comment
     * out calling this method in getClasses().
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(mavenrest.autenticacao.AutenticacaoREST.class);
        resources.add(mavenrest.exemplo.ExemploREST.class);
        resources.add(mavenrest.exemplo.FiltroRequisicaoResposta.class);
        resources.add(mavenrest.exemplo.FiltroSelecionado.class);
        resources.add(mavenrest.user.UserREST.class);
    }

}
