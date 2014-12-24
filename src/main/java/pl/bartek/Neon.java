package pl.bartek;

import java.awt.Image;
import java.awt.Rectangle;

public class Neon extends Fish {
	
	Neon(Image left, Image right, Rectangle edges, Aquarium tank) {
		super(left, right, edges, tank);
		this.maxSpeed = 8;
        this.turn = 7;
        this.bannedUp = 0f;
        this.bannedDown = 0.3f;
	}
	
}
