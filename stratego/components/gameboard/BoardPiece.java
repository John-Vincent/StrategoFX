package stratego.components.gameboard;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

/**
 * Creates an instance of a piece on the board
 * 
 * @author manthan
 *
 */
public class BoardPiece {
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

	/**
	 * Stores the info for the color of the piece
	 */
	private Color color;

	/*
	 * The instances below initialize the images for the pieces
	 */
	static final Image f = new Image(
			BoardPiece.class.getClass().getResourceAsStream("/stratego/components/gameboard/images/FLAG.png"));
	static final ImagePattern flag = new ImagePattern(f);

	static final Image b = new Image(
			BoardPiece.class.getClass().getResourceAsStream("/stratego/components/gameboard/images/BOMB.png"));
	static final ImagePattern bomb = new ImagePattern(b);

	static final Image sc = new Image(
			BoardPiece.class.getClass().getResourceAsStream("/stratego/components/gameboard/images/SCOUT.png"));
	static final ImagePattern scout = new ImagePattern(sc);

	static final Image m = new Image(
			BoardPiece.class.getClass().getResourceAsStream("/stratego/components/gameboard/images/MINER.png"));
	static final ImagePattern miner = new ImagePattern(m);

	static final Image se = new Image(
			BoardPiece.class.getClass().getResourceAsStream("/stratego/components/gameboard/images/SERGEANT.png"));
	static final ImagePattern sergeant = new ImagePattern(se);

	static final Image lieu = new Image(
			BoardPiece.class.getClass().getResourceAsStream("/stratego/components/gameboard/images/LIEUTENANT.png"));
	static final ImagePattern lieutenant = new ImagePattern(lieu);

	static final Image c = new Image(
			BoardPiece.class.getClass().getResourceAsStream("/stratego/components/gameboard/images/CAPTAIN.png"));
	static final ImagePattern captain = new ImagePattern(c);

	static final Image maj = new Image(
			BoardPiece.class.getClass().getResourceAsStream("/stratego/components/gameboard/images/MAJOR.png"));
	static final ImagePattern major = new ImagePattern(maj);

	static final Image col = new Image(
			BoardPiece.class.getClass().getResourceAsStream("/stratego/components/gameboard/images/COLONEL.png"));
	static final ImagePattern colonel = new ImagePattern(col);

	static final Image gen = new Image(
			BoardPiece.class.getClass().getResourceAsStream("/stratego/components/gameboard/images/GENERAL.png"));
	static final ImagePattern general = new ImagePattern(gen);

	static final Image mar = new Image(
			BoardPiece.class.getClass().getResourceAsStream("/stratego/components/gameboard/images/MARSHAL.png"));
	static final ImagePattern marshal = new ImagePattern(mar);

	static final Image s = new Image(
			BoardPiece.class.getClass().getResourceAsStream("/stratego/components/gameboard/images/SPY.png"));
	static final ImagePattern spy = new ImagePattern(s);

	/**
	 * Constructor for BoardPiece.
	 * 
	 * @param str
	 *            takes in name of the board piece
	 * @param x
	 *            takes in horizontal location of BoardPiece on the javafx
	 *            scene.=
	 * @param y
	 *            takes in vertical location of BoardPiece on the javafx scene
	 */
	BoardPiece(String str, int x, int y) {
		r = new Rectangle(x, y, 70, 70);
		name = str;
		char id = '?';
		if (getY() < 10 && getX() < 10) {

			id = Logic.actualBoard[getY()][getX()];
		}
		this.id = id;

		refreshImg();
	}

	/**
	 * Returns the rectangle object for the BoardPiece.
	 * 
	 * @return r rectangle object of this BoardPiece
	 */

	Rectangle getRec() {
		return r;
	}

	/**
	 * Returns the name of the BoardPiece
	 * 
	 * @return name String that contains the name of this board piece.
	 */
	String getName() {
		return name;
	}

	/**
	 * Returns the horizontal location of the board piece.
	 * 
	 * @return horizontal location of the board piece
	 */
	int getX() {

		return (int) ((r.getX() - 8) / (72 * GameScene.wFactor));

	}

	/**
	 * Returns the vertical location of the board piece.
	 * 
	 * @return vertical location of the board piece
	 */
	int getY() {

		return (int) ((r.getY() - GameScene.startY) / (72 * GameScene.hFactor));

	}

	/**
	 * Return 1-letter char id of the board piece.
	 * 
	 * @return char id of this board piece
	 */
	char getId() {
		return this.id;
	}

	/**
	 * Initializes the board piece with an image according to its char ID.
	 */
	void refreshImg() {

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

	/**
	 * Sets the color of the piece
	 * 
	 * @param color
	 *            color of the piece on board
	 */
	public void setColor(Color color) {
		this.color = color;
	}

	/**
	 * Returns color of the board piece
	 * 
	 * @return Returns a color object of type javafx.scene.paint
	 */
	public Color getColor() {
		return color;
	}
}
