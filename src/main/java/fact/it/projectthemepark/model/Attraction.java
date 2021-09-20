//Michael Camerlynck r0831153 ACS01
package fact.it.projectthemepark.model;

public class Attraction {
    private String name, photo;
    private int duration;
    private Staff responsible;

    public Attraction() {
    }

    public Attraction(String name) {
        this.name = name;
    }

    public Attraction(String name, int duration) {
        this.name = name;
        this.duration = duration;
    }

    public String getName() {
        return name;
    }

    public String getPhoto() {
        return photo;
    }

    public int getDuration() {
        return duration;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public Staff getResponsible(){
        return responsible;
    }

    public void setResponsible(Staff responsible) {
        this.responsible = responsible;
    }
}
