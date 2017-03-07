package stratego.components.gameboard;

import javafx.scene.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import stratego.application.*;

/**
 * Class for javafx scene that runs game
 *
 * @author manthan
 *
 */
public class GameScene extends Pane {

	/**
	 * Board piece for player 2
	 */
	protected static boardPiece[] p1Arr;
	/**
	 * Board piece for player 1
	 */
	protected static boardPiece[] p2Arr;

	/**
	 * Integer value for index of the board piece.
	 */
	private int m = -1;

	/**
	 * Boolean value that decides whether enemy pieces should be enabled
	 */
	private boolean reveal = false;
	/**
	 * Cheat function value
	 */
	private static boolean cheat = false;

	/**
	 * Mutator method to enable or disable cheats
	 *
	 * @param c
	 */
	public static void setCheat(boolean c) {
		cheat = c;
	}

	/**
	 * Accesor method to get value of cheat setting
	 *
	 * @return cheat returns the currect setting for cheat
	 */
	public static boolean getCheat() {
		return cheat;
	}

	/**
	 * Vertical resolution at which board starts displaying
	 */
	public static final int startY = 50;

	/**
	 * Constructor that initializes game scene
	 */
	public GameScene() {
		// TODO Auto-generated constructor stub
		System.out.println("into GameScene");
		Logic.arrange();
		System.out.println("past arrange");
		Rectangle cursor = new Rectangle(7, 647 + startY, 72, 72);
		cursor.setFill(Color.TRANSPARENT);
		cursor.setStroke(Color.RED);

		p1Arr = new boardPiece[40];
		int x = 8;
		int y = startY;

		p2Arr = new boardPiece[40];
		int x2 = 8;
		int y2 = 432 + startY;

		for (int i = 0; i < 40; i++) {

			p1Arr[i] = new boardPiece("Test", (i % 10 * 70) + x, y);
			this.getChildren().add(p1Arr[i].getRec());
			x += 2;
			p1Arr[i].getRec().setFill(Color.GREEN);
			if ((i + 1) % 10 == 0 && i != 0) {
				y += 72;
				x = 8;
			}

		}

		for (int i = 0; i < 40; i++) {

			p2Arr[i] = new boardPiece("Test", (i % 10 * 70) + x2, y2);
			this.getChildren().add(p2Arr[i].getRec());
			x2 += 2;

			if ((i + 1) % 10 == 0 && i != 0) {
				y2 += 72;
				x2 = 8;
			}

		}



		this.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {

			Rectangle temp = null;
			if (key.getCode() == KeyCode.RIGHT) {
				if (cursor.getX() < 650) {
					cursor.setX(cursor.getX() + 72);
				}
			} else if (key.getCode() == KeyCode.LEFT) {
				if (cursor.getX() >= 8) {
					cursor.setX(cursor.getX() - 72);
				}
			} else if (key.getCode() == KeyCode.UP) {
				if (cursor.getY() > startY) {
					cursor.setY(cursor.getY() - 72);
				}
			} else if (key.getCode() == KeyCode.DOWN) {
				if (cursor.getY() <= 650 - 24 + startY) {
					cursor.setY(cursor.getY() + 72);
				}
			} else if (key.getCode() == KeyCode.ENTER) {
				try {

				} catch (NullPointerException e) {

				}
				if (m == -1) {
					for (int i = 0; i < 40; i++) {
						if (p2Arr[i].getRec().getX() == cursor.getX() + 1
								&& p2Arr[i].getRec().getY() == cursor.getY() + 1) {
							m = i;
							if (m != -1) {
								if (p2Arr[m].getId() == 'F' || p2Arr[m].getId() == 'B') {
									m = -1;
								} else {

									cursor.setFill(Color.GREY);
									cursor.setStroke(Color.BLUE);
									Logic.actualBoard[p2Arr[i].getY()][p2Arr[i].getX()] = '?';
									for (int j = 0; j < 40; j++) {
										if (p1Arr[j].getY() == p2Arr[i].getY() && p1Arr[j].getX() == p2Arr[i].getX()) {
											Logic.actualBoard[p2Arr[i].getY()][p2Arr[i].getX()] = p1Arr[j].getId();
										}
									}
									p2Arr[i].getRec().setStroke(Color.GREEN);
									break;
								}
							}

						}

					}
				} else {
					if (Logic.isEmpty((int) (cursor.getX() + 1), (int) (cursor.getY() + 1))) {
						if (!(p2Arr[m].getId() == '2')) {
							if (((Math.abs((cursor.getX() + 1) - p2Arr[m].getRec().getX()) == 72)
									&& (Math.abs((cursor.getY() + 1) - p2Arr[m].getRec().getY()) == 0))
									|| ((Math.abs((cursor.getX() + 1) - p2Arr[m].getRec().getX()) == 0)
											&& (Math.abs((cursor.getY() + 1) - p2Arr[m].getRec().getY()) == 72))) {

								p2Arr[m].getRec().setX(cursor.getX() + 1);
								p2Arr[m].getRec().setY(cursor.getY() + 1);
								p2Arr[m].getRec().setStroke(Color.BLACK);

								if (!Logic.computeResult(m, this)) {
									Logic.actualBoard[p2Arr[m].getY()][p2Arr[m].getX()] = p2Arr[m].getId();
								}
								try {

									Logic.computeCpu(Logic.cpuMove(), this);
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								m = -1;

								cursor.setFill(Color.TRANSPARENT);
								cursor.setStroke(Color.RED);
							}
						} else {

							boolean between = false;

							if ((cursor.getX() + 1) - p2Arr[m].getRec().getX() == 0) {
								for (int i = 0; i < 40; i++) {
									if (p2Arr[i].getRec().getY() > (cursor.getY() + 1)
											&& p2Arr[i].getRec().getY() < p2Arr[m].getRec().getY()
											&& p2Arr[i].getRec().getX() == p2Arr[m].getRec().getX()) {
										between = true;
									} else if (p2Arr[i].getRec().getY() < (cursor.getY() + 1)
											&& p2Arr[i].getRec().getY() > p2Arr[m].getRec().getY()
											&& p2Arr[i].getRec().getX() == p2Arr[m].getRec().getX()) {
										between = true;
									}
								}
							} else if ((cursor.getY() + 1) - p2Arr[m].getRec().getY() == 0) {
								for (int i = 0; i < 40; i++) {
									if (p2Arr[i].getRec().getX() > (cursor.getX() + 1)
											&& p2Arr[i].getRec().getX() < p2Arr[m].getRec().getX()
											&& p2Arr[i].getRec().getY() == p2Arr[m].getRec().getY()) {
										between = true;
									} else if (p2Arr[i].getRec().getX() < (cursor.getX() + 1)
											&& p2Arr[i].getRec().getX() > p2Arr[m].getRec().getX()
											&& p2Arr[i].getRec().getY() == p2Arr[m].getRec().getY()) {
										between = true;
									}
								}
							}

							if ((cursor.getX() + 1) - p2Arr[m].getRec().getX() == 0) {
								for (int i = 0; i < 40; i++) {
									if (p1Arr[i].getRec().getY() > (cursor.getY() + 1)
											&& p1Arr[i].getRec().getY() < p2Arr[m].getRec().getY()
											&& p1Arr[i].getRec().getX() == p2Arr[m].getRec().getX()) {
										between = true;
									} else if (p1Arr[i].getRec().getY() < (cursor.getY() + 1)
											&& p1Arr[i].getRec().getY() > p2Arr[m].getRec().getY()
											&& p1Arr[i].getRec().getX() == p2Arr[m].getRec().getX()) {
										between = true;
									}
								}
							} else if ((cursor.getY() + 1) - p2Arr[m].getRec().getY() == 0) {
								for (int i = 0; i < 40; i++) {
									if (p1Arr[i].getRec().getX() > (cursor.getX() + 1)
											&& p1Arr[i].getRec().getX() < p2Arr[m].getRec().getX()
											&& p1Arr[i].getRec().getY() == p2Arr[m].getRec().getY()) {
										between = true;
									} else if (p1Arr[i].getRec().getX() < (cursor.getX() + 1)
											&& p1Arr[i].getRec().getX() > p2Arr[m].getRec().getX()
											&& p1Arr[i].getRec().getY() == p2Arr[m].getRec().getY()) {
										between = true;
									}
								}
							}

							if (between == false) {
								if (((Math.abs((cursor.getX() + 1) - p2Arr[m].getRec().getX()) >= 72)
										&& (Math.abs((cursor.getY() + 1) - p2Arr[m].getRec().getY()) == 0))
										|| ((Math.abs((cursor.getX() + 1) - p2Arr[m].getRec().getX()) == 0)
												&& (Math.abs((cursor.getY() + 1) - p2Arr[m].getRec().getY()) >= 72))) {

									p2Arr[m].getRec().setX(cursor.getX() + 1);
									p2Arr[m].getRec().setY(cursor.getY() + 1);
									p2Arr[m].getRec().setStroke(Color.BLACK);

									if (!Logic.computeResult(m, this)) {
										Logic.actualBoard[p2Arr[m].getY()][p2Arr[m].getX()] = p2Arr[m].getId();
									}
									try {

										Logic.computeCpu(Logic.cpuMove(), this);
									} catch (InterruptedException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									m = -1;
									cursor.setFill(Color.TRANSPARENT);
									cursor.setStroke(Color.RED);
								}
							} else {
								between = false;
							}

						}

					}

				}
			} else if (key.getCode() == KeyCode.R) {
				if (cheat == true) {
					if (reveal == false) {
						reveal = true;
						for (int i = 0; i < 40; i++) {
							p1Arr[i].refreshImg();
						}
					} else if (reveal == true) {
						reveal = false;
						for (int i = 0; i < 40; i++) {
							p1Arr[i].getRec().setFill(Color.GREEN);
						}
					}
				}
			} else if (key.getCode() == KeyCode.ESCAPE) {
				if (m != -1) {
					p2Arr[m].getRec().setStroke(Color.BLACK);
					m = -1;
					cursor.setFill(Color.TRANSPARENT);
					cursor.setStroke(Color.RED);
				}
			}

			for (int i = 0; i < 10; i++) {
				for (int j = 0; j < 10; j++) {
					// System.out.print(" " + Logic.actualBoard[i][j]);
				}
				// System.out.println("");
			}

		});

		this.getChildren().addAll(cursor);
	}

}
