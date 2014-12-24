package pl.bartek;

import java.awt.Image;
import java.awt.Rectangle;

public class Corydoras extends Fish {

	Corydoras(Image left, Image right, Rectangle edges, Aquarium tank) {
		super(left, right, edges, tank);
		this.maxSpeed = 6;
        this.turn = 10;
        this.bannedUp = 0.7f;
        this.bannedDown = 0.05f;
	}
	
}
