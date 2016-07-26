package sectorbob.gaming.pokemon.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.OneToOne;

/**
 * Created by ltm688 on 7/25/16.
 */
@Entity
public class SoughtAfterPokemon {

    @Id
    Long encounterId;

    @OneToOne
    @JoinTable(name="USER_GROUP")
    Pokemon pokemon;

    boolean notificationSent;
    String receipt;

    public SoughtAfterPokemon() { }

    public SoughtAfterPokemon(Pokemon pokemon) {
        setPokemon(pokemon);
        setEncounterId(pokemon.getEncounterId());
        setNotificationSent(false);
    }

    public Long getEncounterId() {
        return encounterId;
    }

    public void setEncounterId(Long encounterId) {
        this.encounterId = encounterId;
    }

    public Pokemon getPokemon() {
        return pokemon;
    }

    public void setPokemon(Pokemon pokemon) {
        this.pokemon = pokemon;
    }

    public boolean isNotificationSent() {
        return notificationSent;
    }

    public void setNotificationSent(boolean notificationSent) {
        this.notificationSent = notificationSent;
    }

    public String getReceipt() {
        return receipt;
    }

    public void setReceipt(String receipt) {
        this.receipt = receipt;
    }
}
