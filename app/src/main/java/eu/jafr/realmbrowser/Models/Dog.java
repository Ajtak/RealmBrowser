package eu.jafr.realmbrowser.Models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * @author Jakub Fridrich (http://fb.com/xfridrich)
 * @package eu.jafr.realmbrowser.Models
 * @since 5.3.2017
 */

public class Dog extends RealmObject {
    @PrimaryKey
    private String name;
    private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
