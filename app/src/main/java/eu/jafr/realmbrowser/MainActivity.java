package eu.jafr.realmbrowser;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import eu.jafr.realmbrowser.Models.Dog;
import eu.jafr.realmbrowser.Models.Person;
import eu.jafr.realmbrowser.library.RealmBrowser;
import io.realm.Realm;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView ipAdress = (TextView) findViewById(R.id.ipAdress);

        if (BuildConfig.DEBUG) {
            RealmBrowser rb = new RealmBrowser();
            rb.start();
            String serverAdress = rb.getServerAddress(this);
            ipAdress.setText(serverAdress);
        }

        createData();

    }

    private void createData() {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        for (int i = 0; i < 5; i++) {
            Dog dog = new Dog();
            dog.setName("Rex " + i);
            dog.setAge(i);
            realm.insertOrUpdate(dog);

            Person person = new Person();
            person.setId((long) i);
            person.setName("Andrew " + i);
            realm.insertOrUpdate(person);
        }

        realm.commitTransaction();
    }
}
