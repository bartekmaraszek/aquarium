package pl.bartek;

import java.awt.*;
import java.awt.event.*;
import java.util.Vector;

public class Aquarium extends Frame implements Runnable {

	/**
	 * Auto
	 */
	private static final long serialVersionUID = 6275446867061904874L;

	/**
	 * Alerts the user if there is a problem while loading *.png files.
	 */
	MediaTracker tracker;
	Thread thread;

	Image aquariumImage;
	Image[] neonImages = new Image[2];
	Image[] ramirezImages = new Image[2];
	Image[] corydorasImages = new Image[2];

	/**
	 * For double buffering.
	 */
	Image memoryImage;
	Graphics memoryGraphics;

	int numberOfNeons = 10;
	int numberOfRamirez = 2;
	int numberOfCorydoras = 1;
	int numberOfFish = 0;
	int sleepTime = 110;
	Vector<Fish> fishes = new Vector<Fish>();
	boolean runOK = true;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new Aquarium();
	}

	void loadImages() {
		tracker = new MediaTracker(this);

		neonImages[0] = Toolkit.getDefaultToolkit().getImage(
				Aquarium.class.getResource("img/neon_left.png"));
		tracker.addImage(neonImages[0], 0);

		neonImages[1] = Toolkit.getDefaultToolkit().getImage(
				Aquarium.class.getResource("img/neon_right.png"));
		tracker.addImage(neonImages[1], 0);

		ramirezImages[0] = Toolkit.getDefaultToolkit().getImage(
				Aquarium.class.getResource("img/ramirez_left.png"));
		tracker.addImage(ramirezImages[0], 0);

		ramirezImages[1] = Toolkit.getDefaultToolkit().getImage(
				Aquarium.class.getResource("img/ramirez_right.png"));
		tracker.addImage(ramirezImages[1], 0);

		corydorasImages[0] = Toolkit.getDefaultToolkit().getImage(
				Aquarium.class.getResource("img/corydoras_left.png"));
		tracker.addImage(corydorasImages[0], 0);

		corydorasImages[1] = Toolkit.getDefaultToolkit().getImage(
				Aquarium.class.getResource("img/corydoras_right.png"));
		tracker.addImage(corydorasImages[1], 0);

		aquariumImage = Toolkit.getDefaultToolkit().getImage(
				Aquarium.class.getResource("img/aquarium.png"));
		tracker.addImage(aquariumImage, 0);

		try {
			tracker.waitForID(0);
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}

	/**
	 * Resize the window to fit the background image and prevent changes in
	 * size.
	 */
	void fixSize() {
		setSize(aquariumImage.getWidth(this), aquariumImage.getHeight(this));
		setResizable(false);
		setVisible(true);
	}

	Aquarium() {
		setTitle("Bartek Maraszek - The Aquarium");
		loadImages();
		fixSize();

		numberOfFish = numberOfNeons + numberOfRamirez + numberOfCorydoras;

		/**
		 * The actual double buffering (?)
		 */
		memoryImage = createImage(getSize().width, getSize().height);
		memoryGraphics = memoryImage.getGraphics();

		thread = new Thread(this);
		thread.start();

		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {
				runOK = false;
				System.exit(0);
			}
		});
	}

	void spawnFish(Rectangle edges) {
		for (int i = 0; i < numberOfNeons; ++i) {
			fishes.add(new Neon(neonImages[0], neonImages[1], edges, this));
			try {
				Thread.sleep(20);
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}

		for (int i = 0; i < numberOfRamirez; ++i) {
			fishes.add(new Ramirez(ramirezImages[0], ramirezImages[1], edges,
					this));
			try {
				Thread.sleep(40);
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}

		for (int i = 0; i < numberOfCorydoras; ++i) {
			fishes.add(new Corydoras(corydorasImages[0], corydorasImages[1],
					edges, this));
			try {
				Thread.sleep(30);
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
	}

	@Override
	public void update(Graphics g) {
		memoryGraphics.drawImage(aquariumImage, 0, 0, this);

		for (int i = 0; i < numberOfFish; ++i) {
			((Fish) fishes.elementAt(i)).drawFishImage(memoryGraphics);
		}

		g.drawImage(memoryImage, 0, 0, this);
	}

	@Override
	public void run() {
		/**
		 * Computes the actual size of working area (the rectangle in which the
		 * fish swim).
		 */
		Rectangle edges = new Rectangle(0 + getInsets().left,
				(0 + getInsets().top), getSize().width
						- (getInsets().left + getInsets().right),
				(getSize().height - (getInsets().top + getInsets().bottom)));

		spawnFish(edges);

		Fish fish;

		while (runOK) {
			for (int i = 0; i < numberOfFish; ++i) {
				fish = (Fish) fishes.elementAt(i);
				fish.swim();
			}

			try {
				Thread.sleep(sleepTime);
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}

			repaint();

		}

	}

}
