package stratego.components;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
/**
 * Creates an instance of a piece on the board
 * @author manthan
 *
 */
public class boardPiece {
	/**
	 * Rectangle object for the piece
	 */
	private Rectangle r;
	/**
	 * Name of the piece
	 */
	private String name;
	/**
	 * Horizontal location on board
	 */
	private int xIndex;
	/**
	 * Vertical location on board
	 */
	private int yIndex;
	/**
	 * 1-letter char id for the piece
	 */
	private char id;

	/*
	 * The instances below initialize the images for the pieces
	 */
	static final  Image f = new Image("FLAG.png");
	static final  ImagePattern flag = new ImagePattern(f);

	static final  Image b = new Image("BOMB.png");
	static final  ImagePattern bomb = new ImagePattern(b);

	static final  Image sc = new Image("SCOUT.png");
	static final  ImagePattern scout = new ImagePattern(sc);

	static final  Image m = new Image("MINER.png");
	static final  ImagePattern miner = new ImagePattern(m);

	static final  Image se = new Image("SERGEANT.png");
	static final  ImagePattern sergeant = new ImagePattern(se);

	static final  Image lieu = new Image("LIEUTENANT.png");
	static final  ImagePattern lieutenant = new ImagePattern(lieu);

	static final  Image c = new Image("CAPTAIN.png");
	static final  ImagePattern captain = new ImagePattern(c);

	static final  Image maj = new Image("MAJOR.png");
	static final  ImagePattern major = new ImagePattern(maj);

	static final  Image col = new Image("COLONEL.png");
	static final  ImagePattern colonel = new ImagePattern(col);

	static final  Image gen = new Image("GENERAL.png");
	static final  ImagePattern general = new ImagePattern(gen);

	static final  Image mar = new Image("MARSHAL.png");
	static final  ImagePattern marshal = new ImagePattern(mar);

	static final  Image s = new Image("SPY.png");
	static final  ImagePattern spy = new ImagePattern(s);
	
	/**
	 * Constructor for boardpiece.
	 * @param str takes in name of the board piece
	 * @param x takes in horizontal location of boardpiece on the javafx scene.=
	 * @param y takes in vertical  location of boardpiece on the javafx scene
	 * @param logic instance of stratego.components.logic
	 */
	boardPiece(String str, int x, int y, Logic logic) {
		r = new Rectangle(x, y, 70, 70);
		name = str;
		char id = '?';
		if (getY() < 10 && getX() < 10) {

			id = logic.actualBoard[getY()][getX()];
		}
		this.id = id;

		refreshImg(logic);
	}
	
	/**
	 * Returns the rectangle object for the boardpiece.
	 * @return r rectangle object of this boardpiece
	 */
	
	Rectangle getRec() {
		return r;
	}

	/**
	 * Returns the name of the boardpiece
	 * @return name String that contains the name of this board piece.
	 */
	String getName() {
		return name;
	}

	/**
	 * Returns the horizontal location of the board piece.
	 * @return horizontal location of the board piece
	 */
	int getX() {

		return (int) ((r.getX() - 8) / 72);

	}
	
	
	/**
	 * Returns the vertical location of the board piece.
	 * @return vertical location of the board piece
	 */
	int getY() {

		return (int) ((r.getY() - GameScene.startY) / 72);

	}

	/**
	 * Return 1-letter char id of the board piece.
	 * @return char id of this board piece
	 */
	char getId() {
		return this.id;
	}

	/**
	 * Initializes the board piece with an image according to its char ID.
	 * @param logic instance of stratego.components.logic
	 */
	void refreshImg(Logic logic) {

		if (id == 'F') {
			r.setFill(flag);
		} else if (id == '2') {
			r.setFill(scout);
		} else if (id == '3') {
			r.setFill(miner);
		} else if (id == '4') {
			r.setFill(sergeant);
		} else if (id == '5') {
			r.setFill(lieutenant);
		} else if (id == '6') {
			r.setFill(captain);
		} else if (id == '7') {
			r.setFill(major);
		} else if (id == '8') {
			r.setFill(colonel);
		} else if (id == '9') {
			r.setFill(general);
		} else if (id == 'T') {
			r.setFill(marshal);
		} else if (id == 'B') {
			r.setFill(bomb);
		} else if (id == 'S') {
			r.setFill(spy);
		}
	}
}
