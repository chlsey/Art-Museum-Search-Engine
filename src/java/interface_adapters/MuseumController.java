package interface_adapters;

import entities.Museum;

public class MuseumController {
private final Museum museum;

    public MuseumController(Museum museum) {
        this.museum = museum;
    }
    public Museum getMuseum() {
        return museum;
    }




}
