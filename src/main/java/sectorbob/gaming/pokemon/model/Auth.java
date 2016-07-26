package sectorbob.gaming.pokemon.model;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by ltm688 on 7/26/16.
 */
@Entity
public class Auth {

    @Id
    String username;
    String encryptedPassword;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEncryptedPassword() {
        return encryptedPassword;
    }

    public void setEncryptedPassword(String encryptedPassword) {
        this.encryptedPassword = encryptedPassword;
    }
}
