package a2m.ingegneria.com.a2emme.Model;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.couchbase.lite.Attachment;
import com.couchbase.lite.CouchbaseLiteException;
import com.couchbase.lite.Document;
import com.couchbase.lite.Revision;
import com.couchbase.lite.UnsavedRevision;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by vlad on 05/07/17.
 */

public class CatalogDb extends Model {

    public CatalogDb(Context context, String name) {
        super(context, name);
    }

    public List<Product> getProducts() {
        List<Product> products = new ArrayList<>();
        List<Document> documents = super.getAllDocuments();

        String id, title, tracks, insertDate, description, performer, category, performers, musicalInstruments;;
        Drawable cover, smallCover;
        float price;

        for(Document document : documents) {
            id = (String)document.getProperty("_id");
            title = (String)document.getProperty("title");
            tracks = (String)document.getProperty("tracks");
            insertDate = (String)document.getProperty("insertDate");
            description = (String)document.getProperty("description");
            String tmp = "" + document.getProperty("price");
            if (tmp != null && !tmp.equals("null"))
                price = Float.parseFloat(tmp);
            else
                price = (float)0.0;
            performer = (String)document.getProperty("performer");
            category = (String) document.getProperty("category");
            cover = setCover(id, document.getCurrentRevision());
            smallCover = setSmallCover(id, document.createRevision());
            performers = (String) document.getProperty("performers");
            musicalInstruments = (String) document.getProperty("musicalInstruments");
            products.add(new Product(id, title, tracks, cover, smallCover, price, insertDate, performer,
                    description, category, performers, musicalInstruments));
        }

        return products;
    }

    private Drawable setSmallCover(String id, UnsavedRevision rev) {
        InputStream stream = null;
        Attachment attachment = rev.getAttachment(id + "_small.jpg");

        if (attachment != null) {
            try {
                stream = attachment.getContent();
            } catch (CouchbaseLiteException e) {
                e.printStackTrace();
            }
        }
        return Drawable.createFromStream(stream, id + "_small.jpg");
    }

    private Drawable setCover(String id, Revision rev) {
        InputStream stream = null;
        Attachment attachment = rev.getAttachment(id + ".jpg");

        if (attachment != null) {
            try {
                stream = attachment.getContent();
            } catch (CouchbaseLiteException e) {
                e.printStackTrace();
            }
        }
        return Drawable.createFromStream(stream, id + ".jpg");
    }
}
