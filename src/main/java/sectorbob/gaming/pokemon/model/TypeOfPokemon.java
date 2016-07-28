package sectorbob.gaming.pokemon.model;

/**
 * Created by ltm688 on 7/28/16.
 */
public class TypeOfPokemon {

    int no;
    String name;

    public TypeOfPokemon() {}

    public TypeOfPokemon(int no, String name) {
        setNo(no);
        setName(name);
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
