package mavenrest.exemplo;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.ext.Provider;

/**
 * como este filtro foi anotado com a anotacao @FiltroSelecionadoAnotacao, este
 * filtro sera executado somente em metodos que tambem tenham essa anotacao
 *
 * @author pedro
 */
@Provider
@FiltroSelecionadoAnotacao
@Priority(Priorities.AUTHORIZATION)

public class FiltroSelecionado implements ContainerRequestFilter {

    @Override
    public void filter(ContainerRequestContext requestContext) {
        System.out.println("somente algumas requisicoes selecionadas passam por esse filtro");
    }

}
