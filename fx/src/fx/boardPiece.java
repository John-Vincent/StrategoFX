package fx;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class boardPiece {
	private Rectangle r;
	private String name;
	private int xIndex;
	private int yIndex;
	private char id;

	Image f = new Image("FLAG.png");
	ImagePattern flag = new ImagePattern(f);

	Image b = new Image("BOMB.png");
	ImagePattern bomb = new ImagePattern(b);

	Image sc = new Image("SCOUT.png");
	ImagePattern scout = new ImagePattern(sc);

	Image m = new Image("MINER.png");
	ImagePattern miner = new ImagePattern(m);

	Image se = new Image("SERGEANT.png");
	ImagePattern sergeant = new ImagePattern(se);

	Image lieu = new Image("LIEUTENANT.png");
	ImagePattern lieutenant = new ImagePattern(lieu);

	Image c = new Image("CAPTAIN.png");
	ImagePattern captain = new ImagePattern(c);

	Image maj = new Image("MAJOR.png");
	ImagePattern major = new ImagePattern(maj);

	Image col = new Image("COLONEL.png");
	ImagePattern colonel = new ImagePattern(col);

	Image gen = new Image("GENERAL.png");
	ImagePattern general = new ImagePattern(gen);

	Image mar = new Image("MARSHAL.png");
	ImagePattern marshal = new ImagePattern(mar);

	Image s = new Image("SPY.png");
	ImagePattern spy = new ImagePattern(s);

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

	Rectangle getRec() {
		return r;
	}

	String getName() {
		return name;
	}

	int getX() {

		return (int) ((r.getX() - 8) / 72);

	}

	int getY() {

		return (int) ((r.getY() - 24) / 72);

	}

	char getId() {
		return this.id;
	}

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
