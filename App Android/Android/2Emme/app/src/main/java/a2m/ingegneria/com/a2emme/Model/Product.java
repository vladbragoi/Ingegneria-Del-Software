package a2m.ingegneria.com.a2emme.Model;

import android.graphics.drawable.Drawable;
import android.text.Editable;

/**
 * Created by vlad on 05/07/17.
 */

public class Product {

    public String id;
    private String title;
    private String tracks;
    private Drawable cover;
    private Drawable smallCover;
    private float price;
    private String insertDate;
    private String performer;
    private String description;
    private String category;
    private String performers;
    private String musicalInstruments;

    public Product(String id, String title, String tracks, Drawable cover, Drawable smallCover, float price,
                   String insertDate, String performer, String description, String category,
                   String performers, String muscialInstruments) {
        this.id = id;
        this.title = title;
        this.tracks = tracks;
        this.cover = cover;
        this.smallCover = smallCover;
        this.price = price;
        this.insertDate = insertDate;
        this.performer = performer;
        this.description = description;
        this.category = category;
        this.performers = performers;
        this.musicalInstruments = muscialInstruments;
    }

    public String getID() {
        return id;
    }

    public String getInsertDate() {
        return insertDate;
    }

    public String getMusicalInstruments() {
        return musicalInstruments;
    }

    public String getPerformers() {
        return performers;
    }

    public String getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return performer;
    }

    public String getTracks() {
        return tracks;
    }

    public float getPrice() {
        return price;
    }

    public Drawable getCover() {
        return cover;
    }

    public Drawable getSmallCover() {
        return smallCover;
    }

    @Override
    public String toString() {
        return "Titolo: " + title
                + "\nBrani: " + tracks
                + "\nPrezzo " + price
                + "\nPresente nel catalogo dal: " + insertDate
                + "\nArtista: " + performer
                + "\nDescrizione: " + description
                + "\nGenere: " + category
                + "\nMusicisti: " + performers
                + "\nStrumenti: " + musicalInstruments;
    }

    public boolean find(Editable text) {
        String s = String.valueOf(text);
        if (title.toLowerCase().contains(s.toLowerCase()))
            return true;
        if (performer.toLowerCase().contains(s.toLowerCase()))
            return true;
        if (tracks.toLowerCase().contains(s.toLowerCase()))
            return true;
        if (description.toLowerCase().contains(s.toLowerCase()))
            return true;
        return false;
    }
}
