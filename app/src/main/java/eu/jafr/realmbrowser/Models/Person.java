package eu.jafr.realmbrowser.Models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * @author Jakub Fridrich (http://fb.com/xfridrich)
 * @package eu.jafr.realmbrowser.Models
 * @since 5.3.2017
 */

public class Person extends RealmObject {
    @PrimaryKey
    private long id;
    private String name;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
