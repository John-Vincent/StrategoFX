package stratego.components.gameboard;

import java.util.Random;

import javafx.scene.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import stratego.application.*;
import stratego.mode.singleplayer.*;

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
	protected static BoardPiece[] p1Arr;
	/**
	 * Board piece for player 1
	 */
	protected static BoardPiece[] p2Arr;

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
	public static int kX = 0;
	public static int kY = 0;
	public static double wFactor = 1;
	public static double hFactor = 1;
	protected static boolean wChange = false;
	protected static boolean hChange = false;
	double[] p1X = new double[41];
	double[] p1Y = new double[41];
	double[] p2X = new double[40];
	double[] p2Y = new double[40];
	int freeForm = 0;
	
	/**
	 * Constructor that initializes game scene
	 */
	public GameScene() {
		Logic.arrange();
		Logic.printBoard();
		Rectangle kursor = new Rectangle(7, 647 + startY, 72, 72);
		kursor.setFill(Color.TRANSPARENT);
		kursor.setStrokeWidth(5);
		kursor.setStroke(Color.RED);
		Text t = new Text("Cursor Position: " + kX + ": " + kY);
		t.setY(25 * hFactor);
		t.setX(750 * wFactor - 200);

		

		p1X[40] = kursor.getX();
		p1Y[40] = kursor.getY();

		p1Arr = new BoardPiece[40];
		int x = 8;
		int y = startY;

		p2Arr = new BoardPiece[40];
		int x2 = 8;
		int y2 = 432 + startY;

		for (int i = 0; i < 40; i++) {

			p1Arr[i] = new BoardPiece("Test", (i % 10 * 70) + x, y);
			this.getChildren().add(p1Arr[i].getRec());
			x += 2;
			Random rand = new Random();
			int colour = rand.nextInt(12);

			switch (colour) {
			case 0:
				p1Arr[i].getRec().setFill(Color.PALEVIOLETRED);
				p1Arr[i].setColor(Color.PALEVIOLETRED);
				break;
			case 1:
				p1Arr[i].getRec().setFill(Color.BLUE);
				p1Arr[i].setColor(Color.BLUE);
				break;
			case 2:
				p1Arr[i].getRec().setFill(Color.GREEN);
				p1Arr[i].setColor(Color.GREEN);
				break;
			case 3:
				p1Arr[i].getRec().setFill(Color.BLUEVIOLET);
				p1Arr[i].setColor(Color.BLUEVIOLET);
				break;
			case 4:
				p1Arr[i].getRec().setFill(Color.BROWN);
				p1Arr[i].setColor(Color.BROWN);
				break;
			case 5:
				p1Arr[i].getRec().setFill(Color.ORANGE);
				p1Arr[i].setColor(Color.ORANGE);
				break;
			case 6:
				p1Arr[i].getRec().setFill(Color.DARKCYAN);
				p1Arr[i].setColor(Color.DARKCYAN);
				break;
			case 7:
				p1Arr[i].getRec().setFill(Color.DARKMAGENTA);
				p1Arr[i].setColor(Color.DARKMAGENTA);
				break;
			case 8:
				p1Arr[i].getRec().setFill(Color.DARKGRAY);
				p1Arr[i].setColor(Color.DARKGRAY);
				break;
			case 9:
				p1Arr[i].getRec().setFill(Color.FIREBRICK);
				p1Arr[i].setColor(Color.FIREBRICK);
				break;
			case 10:
				p1Arr[i].getRec().setFill(Color.YELLOW);
				p1Arr[i].setColor(Color.YELLOW);
				break;
			case 11:
				p1Arr[i].getRec().setFill(Color.BLACK);
				p1Arr[i].setColor(Color.BLACK);
				break;

			default:
				p1Arr[i].getRec().setFill(Color.BLACK);
				p1Arr[i].setColor(Color.BLACK);
				break;
			}

			if ((i + 1) % 10 == 0 && i != 0) {
				y += 72;
				x = 8;
			}

		}

		for (int i = 0; i < 40; i++) {

			p2Arr[i] = new BoardPiece("Test", (i % 10 * 70) + x2, y2);
			this.getChildren().add(p2Arr[i].getRec());
			x2 += 2;

			if ((i + 1) % 10 == 0 && i != 0) {
				y2 += 72;
				x2 = 8;
			}

		}
		
		//initialize arrays that keep track of board piece positions
		for (int i = 0; i < 40; i++) {
			p1X[i] = p1Arr[i].getRec().getX();
			p2X[i] = p2Arr[i].getRec().getX();
			p1Y[i] = p1Arr[i].getRec().getY();
			p2Y[i] = p2Arr[i].getRec().getY();

		}

		this.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
			Rectangle temp = null;
			if (key.getCode() == KeyCode.RIGHT) {
				if (kursor.getX() < 650 * wFactor) {
					kX++;
					kursor.setX(kursor.getX() + 72 * wFactor);
				}

			} else if (key.getCode() == KeyCode.LEFT) {
				if (kursor.getX() >= 8 * wFactor) {
					kX--;
					kursor.setX(kursor.getX() - 72 * wFactor);
				}
			} else if (key.getCode() == KeyCode.UP) {
				if (kursor.getY() > startY * hFactor) {
					kY++;
					kursor.setY(kursor.getY() - 72 * hFactor);
				}
			} else if (key.getCode() == KeyCode.DOWN) {
				if (kursor.getY() <= (626 + startY) * hFactor) {
					kY--;
					kursor.setY(kursor.getY() + 72 * hFactor);
				}
			} else if (key.getCode() == KeyCode.ENTER) {
				if (m == -1) {
					for (int i = 0; i < 40; i++) {
						if (((p2Arr[i].getRec().getX() >= kursor.getX() - 3)
								&& (p2Arr[i].getRec().getX() <= kursor.getX() + 3))
								&& ((p2Arr[i].getRec().getY() >= kursor.getY() - 3)
										&& (p2Arr[i].getRec().getY() <= kursor.getY() + 3))) {
							m = i;
							if (m != -1) {
								if (p2Arr[m].getId() == 'F' || p2Arr[m].getId() == 'B') {
									m = -1;
								} else {

									kursor.setFill(Color.LIGHTBLUE);
									kursor.setStroke(Color.BLUE);
									Logic.actualBoard[p2Arr[i].getY()][p2Arr[i].getX()] = '?';
									for (int j = 0; j < 40; j++) {
										if (p1Arr[j].getY() == p2Arr[i].getY() && p1Arr[j].getX() == p2Arr[i].getX()) {
											Logic.actualBoard[p2Arr[i].getY()][p2Arr[i].getX()] = p1Arr[j].getId();
										}
									}
									p2Arr[i].getRec().setStroke(Color.BLUE);
									p2Arr[i].getRec().setStrokeWidth(5);
									break;
								}
							}

						}

					}
				} else {
					//decides how the move is interpreted
					if (Logic.isEmpty((int) (kursor.getX() + 1), (int) (kursor.getY() + 1))) {
						if (!(p2Arr[m].getId() == '2')) {
							if (((((Math.abs((kursor.getX() + 1) - p2Arr[m].getRec().getX()) >= (72 * wFactor) - 3))
									&& ((Math.abs((kursor.getX() + 1) - p2Arr[m].getRec().getX()) <= (72 * wFactor)
											+ 3)))
									&& (((Math.abs((kursor.getY() + 1) - p2Arr[m].getRec().getY()) >= -2))
											&& ((Math.abs((kursor.getY() + 1) - p2Arr[m].getRec().getY()) <= 2))))
									|| ((((Math.abs((kursor.getX() + 1) - p2Arr[m].getRec().getX()) >= -2))
											&& ((Math.abs((kursor.getX() + 1) - p2Arr[m].getRec().getX()) <= 2)))
											&& (((Math.abs(
													(kursor.getY() + 1) - p2Arr[m].getRec().getY()) >= (72 * hFactor)
															- 3))
													&& ((Math.abs((kursor.getY() + 1)
															- p2Arr[m].getRec().getY()) <= (72 * hFactor) + 3))))) {

								p2Arr[m].getRec().setX(kursor.getX() + 1);
								p2Arr[m].getRec().setY(kursor.getY() + 1);
								p2Arr[m].getRec().setStroke(Color.BLACK);
								p2Arr[m].getRec().setStrokeWidth(1);

								if (!Logic.computeResult(m, this)) {
									Logic.actualBoard[p2Arr[m].getY()][p2Arr[m].getX()] = p2Arr[m].getId();
								}

								Logic.computeCpu(Logic.cpuMove(), this);

								m = -1;
								//changes the display of the kursor

								kursor.setFill(Color.TRANSPARENT);
								kursor.setStroke(Color.RED);
							}

						} else {

							boolean between = false;

							if (((kursor.getX() + 1) - p2Arr[m].getRec().getX() >= -2)
									&& ((kursor.getX() + 1) - p2Arr[m].getRec().getX() <= 2)) {
								for (int i = 0; i < 40; i++) {
									if (p2Arr[i].getRec().getY() > (kursor.getY() + 1)
											&& p2Arr[i].getRec().getY() < p2Arr[m].getRec().getY()
											&& p2Arr[i].getRec().getX() == p2Arr[m].getRec().getX()) {
										between = true;
									} else if (p2Arr[i].getRec().getY() < (kursor.getY() + 1)
											&& p2Arr[i].getRec().getY() > p2Arr[m].getRec().getY()
											&& p2Arr[i].getRec().getX() == p2Arr[m].getRec().getX()) {
										between = true;
									}
								}
							} else if (((kursor.getY() + 1) - p2Arr[m].getRec().getY() >= -2)
									&& ((kursor.getY() + 1) - p2Arr[m].getRec().getY() <= 2)) {
								for (int i = 0; i < 40; i++) {
									if (p2Arr[i].getRec().getX() > (kursor.getX() + 1)
											&& p2Arr[i].getRec().getX() < p2Arr[m].getRec().getX()
											&& p2Arr[i].getRec().getY() == p2Arr[m].getRec().getY()) {
										between = true;
									} else if (p2Arr[i].getRec().getX() < (kursor.getX() + 1)
											&& p2Arr[i].getRec().getX() > p2Arr[m].getRec().getX()
											&& p2Arr[i].getRec().getY() == p2Arr[m].getRec().getY()) {
										between = true;
									}
								}
							}

							if (((kursor.getX() + 1) - p2Arr[m].getRec().getX() >= -2)
									&& ((kursor.getX() + 1) - p2Arr[m].getRec().getX() <= 2)) {
								for (int i = 0; i < 40; i++) {
									if (p1Arr[i].getRec().getY() > (kursor.getY() + 1)
											&& p1Arr[i].getRec().getY() < p2Arr[m].getRec().getY()
											&& p1Arr[i].getRec().getX() == p2Arr[m].getRec().getX()) {
										between = true;
									} else if (p1Arr[i].getRec().getY() < (kursor.getY() + 1)
											&& p1Arr[i].getRec().getY() > p2Arr[m].getRec().getY()
											&& p1Arr[i].getRec().getX() == p2Arr[m].getRec().getX()) {
										between = true;
									}
								}
							} else if (((kursor.getY() + 1) - p2Arr[m].getRec().getY() >= -2)
									&& ((kursor.getY() + 1) - p2Arr[m].getRec().getY() <= 2)) {
								for (int i = 0; i < 40; i++) {
									if (p1Arr[i].getRec().getX() > (kursor.getX() + 1)
											&& p1Arr[i].getRec().getX() < p2Arr[m].getRec().getX()
											&& p1Arr[i].getRec().getY() == p2Arr[m].getRec().getY()) {
										between = true;
									} else if (p1Arr[i].getRec().getX() < (kursor.getX() + 1)
											&& p1Arr[i].getRec().getX() > p2Arr[m].getRec().getX()
											&& p1Arr[i].getRec().getY() == p2Arr[m].getRec().getY()) {
										between = true;
									}
								}
							}

							if (between == false) {
								if (((Math.abs((kursor.getX() + 1) - p2Arr[m].getRec().getX()) >= (72 * wFactor) - 3)
										&& (((Math.abs((kursor.getY() + 1) - p2Arr[m].getRec().getY()) >= -2))
												&& ((Math.abs((kursor.getY() + 1) - p2Arr[m].getRec().getY()) <= 2))))
										|| ((((Math.abs((kursor.getX() + 1) - p2Arr[m].getRec().getX()) >= -2))
												&& ((Math.abs((kursor.getX() + 1) - p2Arr[m].getRec().getX()) <= 2)))
												&& (Math.abs((kursor.getY() + 1)
														- p2Arr[m].getRec().getY()) >= (72 * hFactor) - 3))) {

									p2Arr[m].getRec().setX(kursor.getX() + 1);
									p2Arr[m].getRec().setY(kursor.getY() + 1);
									p2Arr[m].getRec().setStroke(Color.BLACK);
									p2Arr[m].getRec().setStrokeWidth(1);

									if (!Logic.computeResult(m, this)) {
										Logic.actualBoard[p2Arr[m].getY()][p2Arr[m].getX()] = p2Arr[m].getId();
									}

									Logic.computeCpu(Logic.cpuMove(), this);

									m = -1;
									kursor.setFill(Color.TRANSPARENT);
									kursor.setStroke(Color.RED);
								}
							} else {
								between = false;
							}

						}

					} else if (((kursor.getX() + 1 >= p2Arr[m].getRec().getX() - 3)
							&& (kursor.getX() + 1 <= p2Arr[m].getRec().getX() + 3))
							&& ((kursor.getY() + 1 >= p2Arr[m].getRec().getY() - 3)
									&& (kursor.getY() + 1 <= p2Arr[m].getRec().getY() + 3))) {

						p2Arr[m].getRec().setStroke(Color.BLACK);
						p2Arr[m].getRec().setStrokeWidth(1);
						m = -1;
						kursor.setFill(Color.TRANSPARENT);
						kursor.setStroke(Color.RED);

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
							p1Arr[i].getRec().setFill(p1Arr[i].getColor());
						}
					}
				}
			} else if (key.getCode() == KeyCode.ESCAPE) {
				if (m != -1) {
					p2Arr[m].getRec().setStroke(Color.BLACK);
					p2Arr[m].getRec().setStrokeWidth(1);
					m = -1;
					kursor.setFill(Color.TRANSPARENT);
					kursor.setStroke(Color.RED);
				}
			} else if (key.getCode() == KeyCode.T) {
				for (int i = 0; i < 40; i++) {
					p2Arr[i].refreshImg();
					if (p2Arr[i].getY() == 6) {
						System.out.println(p2Arr[i].getY());
					}
				}
			}

			t.setText("Cursor Position: " + kX + ", " + kY);

			p1X[40] = kursor.getX() / wFactor;
			p1Y[40] = kursor.getY() / hFactor;

			for (int i = 0; i < 40; i++) {
				p1X[i] = p1Arr[i].getRec().getX() / wFactor;
				p2X[i] = p2Arr[i].getRec().getX() / wFactor;
				p1Y[i] = p1Arr[i].getRec().getY() / hFactor;
				p2Y[i] = p2Arr[i].getRec().getY() / hFactor;

			}

		});

		this.getChildren().addAll(kursor, t);

		this.widthProperty().addListener((obs) -> {
			
			if(freeForm==0){
				wFactor = this.getWidth() / 750;
			}else{
				if(this.getWidth()<this.getHeight()){
					wFactor = this.getWidth() / 750;
				}
			}
		

			for (int i = 0; i < p1Arr.length; i++) {
				p1Arr[i].getRec().setWidth(70 * wFactor);
				p2Arr[i].getRec().setWidth(70 * wFactor);
				p1Arr[i].getRec().setX(p1X[i] * wFactor);
				p2Arr[i].getRec().setX(p2X[i] * wFactor);
			}
			kursor.setWidth(72 * wFactor);
			kursor.setX(p1X[40] * wFactor);
			t.setX(750 * wFactor - 200);

		});

		this.heightProperty().addListener((obs, oldVal, newVal) -> {
			
			hFactor = this.getHeight() / 800;
			if(freeForm==1){
				wFactor = hFactor;
				
				for (int i = 0; i < p1Arr.length; i++) {
					p1Arr[i].getRec().setWidth(70 * wFactor);
					p2Arr[i].getRec().setWidth(70 * wFactor);
					p1Arr[i].getRec().setX(p1X[i] * wFactor);
					p2Arr[i].getRec().setX(p2X[i] * wFactor);
				}
				kursor.setWidth(72 * wFactor);
				kursor.setX(p1X[40] * wFactor);
				t.setX(750 * wFactor - 200);
			}
			
			for (int i = 0; i < p1Arr.length; i++) {
				p1Arr[i].getRec().setHeight(70 * hFactor);
				p2Arr[i].getRec().setHeight(70 * hFactor);
				p1Arr[i].getRec().setY(p1Y[i] * hFactor);
				p2Arr[i].getRec().setY(p2Y[i] * hFactor);
			}
			kursor.setHeight(72 * hFactor);
			kursor.setY(p1Y[40] * hFactor);
			t.setY(25 * hFactor);
		});
		
	}

}
