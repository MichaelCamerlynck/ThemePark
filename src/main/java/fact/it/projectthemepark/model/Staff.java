//Michael Camerlynck r0831153 ACS01
package fact.it.projectthemepark.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Staff extends Person{
    private LocalDate startDate;
    private boolean student;
    private ThemePark employedAt;

    public Staff(String firstName, String surName) {
        super(firstName, surName);
        startDate = LocalDate.now();
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public boolean isStudent() {
        return student;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setStudent(boolean student) {
        this.student = student;
    }

    public String toString(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        if (isStudent()){
            return String.format("Staff member %s (working student) is employed since %s", super.toString(), getStartDate().format(dtf));
        }
        else{
            return String.format("Staff member %s is employed since %s", super.toString(), getStartDate().format(dtf));
        }
    }

    public ThemePark getEmployedAt() {
        return employedAt;
    }

    public void setEmployedAt(ThemePark employedAt) {
        this.employedAt = employedAt;
    }
}
