package stratego.components;
import javafx.scene.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import stratego.application.*;


public class GameScene extends Pane{
	
	protected static boardPiece[] p1Arr;
	protected static boardPiece[] p2Arr;
	
	private int w = 1024;
	private int l = 768;
	private int m = -1;
	private Logic logic;
	private boolean reveal = false;
	private static boolean cheat = false;
	private int winner = -1;
	
	public static void setCheat(boolean c){
		cheat = c ;
	}
	
 	public static boolean getCheat(){
		return cheat;
	}
	
 	public static final int startY = 50;
 	
	public GameScene() {
		// TODO Auto-generated constructor stub
		logic = new Logic();
		logic.arrange();
		Rectangle cursor = new Rectangle(7, 647+startY, 72, 72);
		cursor.setFill(Color.TRANSPARENT);
		cursor.setStroke(Color.RED);
		
		p1Arr = new boardPiece[40];
		int x = 8;
		int y = startY;

		p2Arr = new boardPiece[40];
		int x2 = 8;
		int y2 = 432+startY;
		
		for (int i = 0; i < 40; i++) {

			p1Arr[i] = new boardPiece("Test", (i % 10 * 70) + x, y, logic);
			this.getChildren().add(p1Arr[i].getRec());
			x += 2;
			p1Arr[i].getRec().setFill(Color.GREEN);
			if ((i + 1) % 10 == 0 && i != 0) {
				y += 72;
				x = 8;
			}

		}
		
		

		for (int i = 0; i < 40; i++) {

			p2Arr[i] = new boardPiece("Test", (i % 10 * 70) + x2, y2, logic);
			this.getChildren().add(p2Arr[i].getRec());
			x2 += 2;

			if ((i + 1) % 10 == 0 && i != 0) {
				y2 += 72;
				x2 = 8;
			}

		}
		
		///////////////////////
		HBox sizingBoxes = null;
		for(int i = 0; i < 10; i+=10){
			//sizingBoxes = new HBox(p1Arr[i].getRec(), p1Arr[i+1].getRec(), p1Arr[i+2].getRec(), p1Arr[i+3].getRec(), p1Arr[i+4].getRec(), p1Arr[i+5].getRec(), p1Arr[i+6].getRec(), p1Arr[i+7].getRec(), p1Arr[i+8].getRec(), p1Arr[i+9].getRec());
			
		}
		//this.getChildren().add(sizingBoxes);
		//////////////////////
		
		
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
				if (cursor.getY() <= 650 -24 + startY) {
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
									logic.actualBoard[p2Arr[i].getY()][p2Arr[i].getX()] = '?';
									for (int j = 0; j < 40; j++) {
										if (p1Arr[j].getY() == p2Arr[i].getY() && p1Arr[j].getX() == p2Arr[i].getX()) {
											logic.actualBoard[p2Arr[i].getY()][p2Arr[i].getX()] = p1Arr[j].getId();
										}
									}
									p2Arr[i].getRec().setStroke(Color.GREEN);
									break;
								}
							}

						}

					}
				} else {
					if (logic.isEmpty((int) (cursor.getX() + 1), (int) (cursor.getY() + 1))) {
						if (!(p2Arr[m].getId() == '2')) {
							if (((Math.abs((cursor.getX() + 1) - p2Arr[m].getRec().getX()) == 72)
									&& (Math.abs((cursor.getY() + 1) - p2Arr[m].getRec().getY()) == 0))
									|| ((Math.abs((cursor.getX() + 1) - p2Arr[m].getRec().getX()) == 0)
											&& (Math.abs((cursor.getY() + 1) - p2Arr[m].getRec().getY()) == 72))) {

								p2Arr[m].getRec().setX(cursor.getX() + 1);
								p2Arr[m].getRec().setY(cursor.getY() + 1);
								p2Arr[m].getRec().setStroke(Color.BLACK);

								if (!logic.computeResult(m, this, logic)) {
									logic.actualBoard[p2Arr[m].getY()][p2Arr[m].getX()] = p2Arr[m].getId();
								}
								try {

									logic.computeCpu(logic.cpuMove(), this, logic);
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

									if (!logic.computeResult(m, this, logic)) {
										logic.actualBoard[p2Arr[m].getY()][p2Arr[m].getX()] = p2Arr[m].getId();
									}
									try {

										logic.computeCpu(logic.cpuMove(),this, logic);
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
							p1Arr[i].refreshImg(logic);
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
					// System.out.print(" " + logic.actualBoard[i][j]);
				}
				// System.out.println("");
			}

		});
		
		
		
		
		
		
		this.getChildren().addAll(cursor);
	}
	
	
	
	
	
	

	
	
}
