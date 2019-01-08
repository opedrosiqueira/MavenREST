package mavenrest.exemplo1;

import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

@Provider
public class UserProvider implements ContextResolver<User> {

    @Override
    public User getContext(Class<?> type) {
        return type.equals(User.class)
                ? new User("pedro", "henrique", "topsecret") : null;
    }

}
