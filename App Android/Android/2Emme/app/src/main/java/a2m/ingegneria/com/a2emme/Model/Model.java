package a2m.ingegneria.com.a2emme.Model;

import android.app.ProgressDialog;
import android.content.Context;
import android.widget.Toast;

import com.couchbase.lite.CouchbaseLiteException;
import com.couchbase.lite.Database;
import com.couchbase.lite.Document;
import com.couchbase.lite.Manager;
import com.couchbase.lite.Query;
import com.couchbase.lite.QueryEnumerator;
import com.couchbase.lite.QueryRow;
import com.couchbase.lite.Revision;
import com.couchbase.lite.SavedRevision;
import com.couchbase.lite.UnsavedRevision;
import com.couchbase.lite.android.AndroidContext;
import com.couchbase.lite.auth.Authenticator;
import com.couchbase.lite.auth.AuthenticatorFactory;
import com.couchbase.lite.replicator.RemoteRequestResponseException;
import com.couchbase.lite.replicator.Replication;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import a2m.ingegneria.com.a2emme.Controller.ProgressDialogListener;

/**
 * Created by vlad on 27/06/17.
 */

public class Model {

    private Context mContext;
    protected Database mDatabase;
    private Manager mManager;
    private String dbName;
    private String url = "http:157.27.138.218:5984/"; //"http://192.168.2.6:5984/";
    private Replication pullReplication;
    private Replication pushReplication;
    private final ProgressDialog progressDialog;
    private Authenticator auth;

    public Model(Context context, String name) {
        mContext = context;
        dbName = name;
        progressDialog = ProgressDialog.show(mContext,
                "Attendere...", "Sto sincronizzando il database", false);
        initialize();
    }
    
    private void initialize() {
        try {
            mManager = new Manager(new AndroidContext(mContext), Manager.DEFAULT_OPTIONS);
            mDatabase = mManager.getDatabase(dbName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setAuthentication(String username, String password) {
        auth = AuthenticatorFactory.createBasicAuthenticator(username, password);
    }

    public void setPullReplication() {
        try {
            pullReplication = mDatabase.createPullReplication(new URL(url + dbName));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }


        pullReplication.addChangeListener(new Replication.ChangeListener() {
            @Override
            public void changed(Replication.ChangeEvent event) {
                // will be called back when the pull replication status changes
                boolean active = pullReplication.getStatus() == Replication.ReplicationStatus.REPLICATION_ACTIVE;

                if (!active) {
                    progressDialog.dismiss();
                } else {
                    int total = pullReplication.getCompletedChangesCount();
                    progressDialog.setMax(total);
                    progressDialog.setProgress(pullReplication.getChangesCount());
                }

                if (event.getError() != null) {
                    Throwable lastError = event.getError();
                    if (lastError instanceof RemoteRequestResponseException) {
                        RemoteRequestResponseException exception = (RemoteRequestResponseException) lastError;
                        if (exception.getCode() == 401) {
                            Toast.makeText(mContext, "Errore di autenticazione al database", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });

        pullReplication.setAuthenticator(auth);
        pullReplication.setContinuous(true);
        pullReplication.start();
    }

    public void setPushReplication() {
        try {
            pushReplication = mDatabase.createPushReplication(new URL(url + dbName));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }


        pushReplication.addChangeListener(new Replication.ChangeListener() {
            @Override
            public void changed(Replication.ChangeEvent event) {
                // will be called back when the push replication status changes
                boolean active = pushReplication.getStatus() == Replication.ReplicationStatus.REPLICATION_ACTIVE;

                if (!active) {
                    progressDialog.dismiss();
                } else {
                    int total = pushReplication.getCompletedChangesCount();
                    progressDialog.setMax(total);
                    progressDialog.setProgress(pushReplication.getChangesCount());
                }

                if (event.getError() != null) {
                    Throwable lastError = event.getError();
                    if (lastError instanceof RemoteRequestResponseException) {
                        RemoteRequestResponseException exception = (RemoteRequestResponseException) lastError;
                        if (exception.getCode() == 401) {
                            Toast.makeText(mContext, "Errore di autenticazione", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });

        pushReplication.setAuthenticator(auth);
        pushReplication.setContinuous(true);
        pushReplication.start();
    }

    public void onStopUpdating() {
        progressDialog.setOnDismissListener(new ProgressDialogListener());
    }

    public void resolveConflicts(final Map<String, Object> properties, Document doc) {
        List<SavedRevision> conflicts = null;
        Revision current = doc.getCurrentRevision();
        try {
            conflicts = doc.getConflictingRevisions();
        } catch (CouchbaseLiteException e1) {
            e1.printStackTrace();
        }

        for (SavedRevision r : conflicts) {
            UnsavedRevision newRev = r.createRevision();
            if (r.getId().equals(current.getId())) {
                newRev.setProperties(properties);

            } else
                newRev.setIsDeletion(true);

            try {
                newRev.save(true);
            } catch (CouchbaseLiteException e1) {
                e1.printStackTrace();
            }
        }
        System.out.println("Conflicts resolved!");
    }

    public Document getDocument(String docName) {
        return mDatabase.getDocument(docName);
    }

    public List<Document> getAllDocuments() {
        List<Document> documents = new ArrayList<>();
        QueryEnumerator result = null;
        Query query = mDatabase.createAllDocumentsQuery();
        query.setAllDocsMode(Query.AllDocsMode.ALL_DOCS);

        try {
            result = query.run();
        } catch (CouchbaseLiteException e) {
            e.printStackTrace();
        }

        for (Iterator<QueryRow> it = result; it.hasNext(); ) {
            QueryRow row = it.next();
            documents.add(row.getDocument());
        }

        return documents;
    }
}
