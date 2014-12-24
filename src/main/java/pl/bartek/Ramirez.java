package pl.bartek;

import java.awt.Image;
import java.awt.Rectangle;

public class Ramirez extends Fish {

	Ramirez(Image left, Image right, Rectangle edges, Aquarium tank) {
		super(left, right, edges, tank);
		this.maxSpeed = 3;
        this.turn = 7;
        this.bannedUp = 0.4f;
        this.bannedDown = 0.1f;
	}
	
}
