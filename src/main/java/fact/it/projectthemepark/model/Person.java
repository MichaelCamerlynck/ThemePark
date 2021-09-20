//Michael Camerlynck r0831153 ACS01
package fact.it.projectthemepark.model;

import java.util.Locale;

public class Person {
    private String firstName, surName;

    public Person() {
    }

    public Person(String firstName, String surName) {
        this.firstName = firstName;
        this.surName = surName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getSurName() {
        return surName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setSurName(String surName) {
        this.surName = surName;
    }

    public String toString(){
        return String.format("%s %s", surName.toUpperCase(), firstName);
    }
}
