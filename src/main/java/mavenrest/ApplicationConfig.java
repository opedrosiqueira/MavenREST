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
        resources.add(mavenrest.exemplo1.ExemploREST.class);
        resources.add(mavenrest.exemplo1.FiltroRequisicaoResposta.class);
        resources.add(mavenrest.exemplo1.FiltroSelecionado.class);
        resources.add(mavenrest.exemplo1.UserProvider.class);
        resources.add(mavenrest.exemplo2.UserREST.class);
        resources.add(mavenrest.exemplo3.UsuarioREST.class);
    }

}
