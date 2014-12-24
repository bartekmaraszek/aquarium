package pl.bartek;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.Random;

public abstract class Fish {

	Aquarium tank;
	Image left, right;
	Point location, velocity;
	Rectangle edges;
	Random random;
	int maxSpeed;
	int turn;
	float bannedUp;
	float bannedDown;

	Fish(Image left, Image right, Rectangle edges, Aquarium tank) {
		random = new Random(System.currentTimeMillis());
		this.tank = tank;
		this.left = left;
		this.right = right;
		this.edges = edges;
		this.maxSpeed = 8;
		this.turn = 7;
		this.bannedUp = 0f;
		this.bannedDown = 0.1f;
		this.location = new Point(100 + (Math.abs(random.nextInt()) % 300),
				100 + (Math.abs(100 + random.nextInt()) % 100));

		this.velocity = new Point(random.nextInt() % 8, random.nextInt() % 8);

	}

	void swim() {
		if (random.nextInt() % turn <= 1) {

			velocity.x += random.nextInt() % (maxSpeed / 2);

			velocity.x = Math.min(velocity.x, maxSpeed);
			velocity.x = Math.max(velocity.x, -maxSpeed);

			velocity.y += random.nextInt() % (maxSpeed / 2);

			velocity.y = Math.min(velocity.y, maxSpeed);
			velocity.y = Math.max(velocity.y, -maxSpeed);
		}

		location.x += velocity.x;
		location.y += velocity.y;

		if (location.x < edges.x) {
			location.x = edges.x;
			velocity.x = -velocity.x;
		}

		if ((location.x + left.getWidth(tank)) > (edges.x + edges.width)) {
			location.x = edges.x + edges.width - left.getWidth(tank);
			velocity.x = -velocity.x;
		}

		/**
		 * Exceeds upwards?
		 */
		if (location.y < (int) (edges.y + bannedUp * tank.getHeight())) {
			location.y = (int) (edges.y + bannedUp * tank.getHeight());
			velocity.y = -velocity.y;
		}

		/**
		 * Exceeds downwards?
		 */
		if ((location.y + left.getHeight(tank)) > (int)(edges.y + edges.height - bannedDown*tank.getHeight())) {
			location.y = edges.y + edges.height - left.getHeight(tank) - (int)(bannedDown*tank.getHeight());
			velocity.y = -velocity.y;
		}

	}

	void drawFishImage(Graphics g) {
		if (velocity.x < 0) {
			g.drawImage(left, location.x, location.y, tank);
		} else {
			g.drawImage(right, location.x, location.y, tank);
		}
	}

}
