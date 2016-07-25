package sectorbob.gaming.pokemon.model;

/**
 * Created by ltm688 on 7/25/16.
 */
public class SoughtAfterPokemon {

    Pokemon pokemon;
    boolean notificationSent;
    String receipt;


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
