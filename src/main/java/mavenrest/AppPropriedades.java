package mavenrest;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class AppPropriedades {

    Properties appProps;

    @PostConstruct
    public void init() {
        try {
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("app.properties");
//          InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("app.properties"); //alternativa
            appProps = new Properties();
            appProps.load(inputStream);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(AppPropriedades.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(AppPropriedades.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getSecret() {
        return appProps.getProperty("authentication.jwt.secret", "sBVv63uLzY0YfmzbfjamgTbCSdHTXqf3epish834khU=");
    }

    public String getIssuer() {
        return appProps.getProperty("authentication.jwt.issuer", "MavenREST");
    }

    public long getExpiresAfter() {
        return Long.parseLong(appProps.getProperty("authentication.jwt.expirationTime", "90000"));
    }
}
