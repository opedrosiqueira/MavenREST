package mavenrest;

import java.io.IOException;
import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;

/**
 * todas as requisicoes passam pelo metodo {@link filter} antes. nao se esqueca
 * de anotas um filtro com a anotacao @Provider. a anotacao @Priority eh
 * opcional e se presente, filtros com valor de prioridade menor sao realizados
 * primeiro
 *
 * @author pedro
 */
@Provider
@Priority(Priorities.AUTHENTICATION)
public class FiltroRequisicaoResposta implements ContainerRequestFilter, ContainerResponseFilter {

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        System.out.println("todas as requisicoes passam por aqui antes");
        System.out.println("Headers da requisicao: " + requestContext.getHeaders());
    }

    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {
        System.out.println("todas as requisicoes passam por aqui depois");
        System.out.println("Headers da resposta: " + responseContext.getHeaders());
        System.out.println("Corpo da resposta: " + responseContext.getEntity());
    }

}
