package a2m.ingegneria.com.a2emme.Model;

import java.util.List;

/**
 * Created by vlad on 22/06/17.
 */

public class Performer {
    private final String stageName;
    private final String category;
    private final int birthYear;
    private String instruments;

    public Performer(String stageName, String category, int birthYear, String instruments){
        this.stageName = stageName;
        this.category = category;
        this.birthYear = birthYear;
        this.instruments = instruments;
    }

    public String getName() {
        return stageName;
    }

    public String getCategory() {
        return category;
    }

    public int getBirth() {
        return birthYear;
    }

    public String getInstruments() {
        return instruments;
    }

    public String toString() {
        return "Nome d'arte: " + stageName + "\nCategoria: " + category +
                "\nAnno di nascita: " + birthYear + "\nStrumenti musicali: " + instruments;
    }
}
