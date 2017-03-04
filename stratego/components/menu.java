package stratego.components;

import java.util.Random;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class menu extends Application {
	public static void main(String args[]) {
		launch(args);

	}

	boolean isEmpty(int x, int y) {
		x = ((x - 8) / 72);
		y = ((y - 24) / 72);
		boolean empty = true;
		for (int i = 0; i < 40; i++) {
			if (p2Arr[i].getY() == y && p2Arr[i].getX() == x) {
				empty = false;
				break;
			}
		}
		return empty;
	}

	private boardPiece[] p1Arr;
	private boardPiece[] p2Arr;

	private int w = 1024;
	private int l = 768;
	private int m = -1;
	private Logic logic;
	private boolean reveal = false;
	private boolean cheat = false;
	private int winner = -1;
	private Text wg;

	@Override
	public void start(Stage primaryStage) throws Exception {

		logic = new Logic();
		logic.arrange();
		// TODO Auto-generated method stub
		primaryStage.setTitle("Stratego");
		Button play = new Button("Play");
		Button settings = new Button("Settings");
		Button exit = new Button("Exit");
		Button yes = new Button("Yes");
		Button no = new Button("No");
		Button login = new Button("Login");
		Button back = new Button("Back");
		Button back2 = new Button("Back");
		Button cheats = new Button("Cheats:" + cheat);
		Button exit2 = new Button("Exit");
		Button rac = new Button("Rules and Controls");
		Button back3 = new Button("Back");
		Rectangle cursor = new Rectangle(7, 671, 72, 72);
		cursor.setFill(Color.TRANSPARENT);
		cursor.setStroke(Color.RED);
		primaryStage.setResizable(false);
		wg = new Text();
		wg.setX(400);
		wg.setY(300);

		Text rules = new Text(
				"The objective is to capture enemy flag\nUse Arrow Keys to Move\nPress Enter to select or release\nWhen cheat  mode enabled, \npressing 'R' will reveal CPU pieces.");
		rules.setX(300);
		rules.setY(300);
		rules.setScaleX(2);
		rules.setScaleY(2);

		p1Arr = new boardPiece[40];
		int x = 8;
		int y = 24;

		p2Arr = new boardPiece[40];
		int x2 = 8;
		int y2 = 456;

		Text t = new Text(500, 200, "Exit Stratego ?");

		Pane root = new Pane();
		Pane ruSure = new Pane();
		Pane game = new Pane();
		Pane setg = new Pane();
		Pane winGame = new Pane();
		Pane rulePane = new Pane();

		// play button
		play.setLayoutX(400);
		play.setLayoutY(230);
		play.setScaleX(2);
		play.setScaleY(2);

		// play button
		rac.setLayoutX(400);
		rac.setLayoutY(430);
		rac.setScaleX(2);
		rac.setScaleY(2);

		// Settings button
		settings.setLayoutX(400);
		settings.setLayoutY(330);
		settings.setScaleX(2);
		settings.setScaleY(2);

		// Exit button
		exit.setLayoutX(400);
		exit.setLayoutY(530);
		exit.setScaleX(2);
		exit.setScaleY(2);

		// Yes button exit screen
		yes.setLayoutX(300);
		yes.setLayoutY(330);
		yes.setScaleX(2);
		yes.setScaleY(2);

		// No Button
		no.setLayoutX(700);
		no.setLayoutY(330);
		no.setScaleX(2);
		no.setScaleY(2);

		// Exit Screen text
		t.setScaleX(4);
		t.setScaleY(4);

		// Back Button
		back.setLayoutX(940);
		back.setLayoutY(720);

		// Back2 Button
		back2.setLayoutX(940);
		back2.setLayoutY(720);

		// Back3 Button
		back3.setLayoutX(940);
		back3.setLayoutY(720);

		// Cheats button
		cheats.setLayoutX(400);
		cheats.setLayoutY(230);
		cheats.setScaleX(2);
		cheats.setScaleY(2);

		// Exit button
		exit2.setLayoutX(400);
		exit2.setLayoutY(430);
		exit2.setScaleX(2);
		exit2.setScaleY(2);

		// Win text
		wg.setScaleX(4);
		wg.setScaleY(4);

		root.getChildren().addAll(play, settings, exit, rac);
		ruSure.getChildren().addAll(yes, no, t);
		game.getChildren().addAll(cursor, back);
		setg.getChildren().addAll(cheats, back2);
		winGame.getChildren().addAll(wg, exit2);
		rulePane.getChildren().addAll(rules, back3);

		for (int i = 0; i < 40; i++) {

			p1Arr[i] = new boardPiece("Test", (i % 10 * 70) + x, y, logic);
			game.getChildren().add(p1Arr[i].getRec());
			x += 2;
			p1Arr[i].getRec().setFill(Color.GREEN);
			if ((i + 1) % 10 == 0 && i != 0) {
				y += 72;
				x = 8;
			}

		}

		for (int i = 0; i < 40; i++) {

			p2Arr[i] = new boardPiece("Test", (i % 10 * 70) + x2, y2, logic);
			game.getChildren().add(p2Arr[i].getRec());
			x2 += 2;

			if ((i + 1) % 10 == 0 && i != 0) {
				y2 += 72;
				x2 = 8;
			}

		}

		Scene mainMenu = new Scene(root, w, l);
		Scene exitScene = new Scene(ruSure, w, l);
		Scene gameScene = new Scene(game, w, l);
		Scene setgScene = new Scene(setg, w, l);
		Scene win = new Scene(winGame, w, l);
		Scene rule = new Scene(rulePane, w, l);
		{
		game.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {

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
				if (cursor.getY() > 24) {
					cursor.setY(cursor.getY() - 72);
				}
			} else if (key.getCode() == KeyCode.DOWN) {
				if (cursor.getY() <= 650) {
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
					if (isEmpty((int) (cursor.getX() + 1), (int) (cursor.getY() + 1))) {
						if (!(p2Arr[m].getId() == '2')) {
							if (((Math.abs((cursor.getX() + 1) - p2Arr[m].getRec().getX()) == 72)
									&& (Math.abs((cursor.getY() + 1) - p2Arr[m].getRec().getY()) == 0))
									|| ((Math.abs((cursor.getX() + 1) - p2Arr[m].getRec().getX()) == 0)
											&& (Math.abs((cursor.getY() + 1) - p2Arr[m].getRec().getY()) == 72))) {

								p2Arr[m].getRec().setX(cursor.getX() + 1);
								p2Arr[m].getRec().setY(cursor.getY() + 1);
								p2Arr[m].getRec().setStroke(Color.BLACK);

								if (!computeResult(m, game, primaryStage, win, logic)) {
									logic.actualBoard[p2Arr[m].getY()][p2Arr[m].getX()] = p2Arr[m].getId();
								}
								try {

									computeCpu(cpuMove(), game, primaryStage, win, logic);
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

									if (!computeResult(m, game, primaryStage, win, logic)) {
										logic.actualBoard[p2Arr[m].getY()][p2Arr[m].getX()] = p2Arr[m].getId();
									}
									try {

										computeCpu(cpuMove(), game, primaryStage, win, logic);
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
					 System.out.print(" " + logic.actualBoard[i][j]);
				}
				 System.out.println("");
			}

		});
	}
		primaryStage.setScene(mainMenu);

		play.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				// System.out.println("Test");
				primaryStage.setScene(gameScene);
			}
		});

		rac.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				// System.out.println("Test");
				primaryStage.setScene(rule);
			}
		});

		exit.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				primaryStage.setScene(exitScene);
			}
		});

		exit2.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				System.exit(0);
			}
		});

		no.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				primaryStage.setScene(mainMenu);
			}
		});

		yes.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				System.exit(0);
			}
		});

		back.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				primaryStage.setScene(mainMenu);
			}
		});

		back2.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				primaryStage.setScene(mainMenu);
			}
		});

		back3.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				primaryStage.setScene(mainMenu);
			}
		});

		settings.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				// System.out.println("Test");
				primaryStage.setScene(setgScene);
			}
		});

		cheats.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				// System.out.println("Test");
				if (cheat == false) {
					cheat = true;
				} else if (cheat == true) {
					cheat = false;
				}
				cheats.setText("Cheats: " + cheat);
			}
		});

		primaryStage.show();
	}

	boolean computeResult(int m, Pane game, Stage primaryStage, Scene win, Logic logic) {
		int n = -1;
		for (int i = 0; i < 40; i++) {
			if (p1Arr[i].getX() == p2Arr[m].getX() && p1Arr[i].getY() == p2Arr[m].getY()) {
				n = i;
				break;
			}
		}
		if (n != -1) {
			if (p1Arr[n].getId() == 'F') {

				wg.setText("Player Wins");
				primaryStage.setScene(win);
			}
			if (p2Arr[m].getId() != 'S') {
				if (p1Arr[n].getId() == 'B') {
					game.getChildren().remove(p2Arr[m].getRec());
					boardPiece temp = new boardPiece("Null", 12000, 12000, logic);
					p2Arr[m] = temp;

					game.getChildren().remove(p1Arr[n].getRec());
					boardPiece temp2 = new boardPiece("Null", 12000, 12000, logic);
					p1Arr[n] = temp2;

					return true;
				} else if (p1Arr[n].getId() == '2') {

					if (p2Arr[m].getId() > '2' && p2Arr[m].getId() != 'B' && p2Arr[m].getId() != 'F'
							&& p2Arr[m].getId() != 'S') {
						game.getChildren().remove(p1Arr[n].getRec());
						boardPiece temp = new boardPiece("Null", 12000, 12000, logic);
						p1Arr[n] = temp;
					} else if (p2Arr[m].getId() == '2' && p2Arr[m].getId() != 'B' && p2Arr[m].getId() != 'F'
							&& p2Arr[m].getId() != 'S') {
						game.getChildren().remove(p1Arr[n].getRec());
						boardPiece temp = new boardPiece("Null", 12000, 12000, logic);
						p1Arr[n] = temp;

						game.getChildren().remove(p2Arr[m].getRec());
						boardPiece temp2 = new boardPiece("Null", 12000, 12000, logic);
						p2Arr[m] = temp2;
					}

					return true;
				} else if (p1Arr[n].getId() == '3') {

					if (p2Arr[m].getId() > '3' && p2Arr[m].getId() != 'B' && p2Arr[m].getId() != 'F'
							&& p2Arr[m].getId() != 'S') {
						game.getChildren().remove(p1Arr[n].getRec());
						boardPiece temp = new boardPiece("Null", 12000, 12000, logic);
						p1Arr[n] = temp;
					} else if (p2Arr[m].getId() == '3' && p2Arr[m].getId() != 'B' && p2Arr[m].getId() != 'F'
							&& p2Arr[m].getId() != 'S') {
						game.getChildren().remove(p1Arr[n].getRec());
						boardPiece temp = new boardPiece("Null", 12000, 12000, logic);
						p1Arr[n] = temp;

						game.getChildren().remove(p2Arr[m].getRec());
						boardPiece temp2 = new boardPiece("Null", 12000, 12000, logic);
						p2Arr[m] = temp2;
					} else if (p2Arr[m].getId() < '3' && p2Arr[m].getId() != 'B' && p2Arr[m].getId() != 'F'
							&& p2Arr[m].getId() != 'S') {
						game.getChildren().remove(p2Arr[m].getRec());
						boardPiece temp = new boardPiece("Null", 12000, 12000, logic);
						p2Arr[m] = temp;
					}

					return true;
				} else if (p1Arr[n].getId() == '4') {

					if (p2Arr[m].getId() > '4' && p2Arr[m].getId() != 'B' && p2Arr[m].getId() != 'F'
							&& p2Arr[m].getId() != 'S') {
						game.getChildren().remove(p1Arr[n].getRec());
						boardPiece temp = new boardPiece("Null", 12000, 12000, logic);
						p1Arr[n] = temp;
					} else if (p2Arr[m].getId() == '4' && p2Arr[m].getId() != 'B' && p2Arr[m].getId() != 'F'
							&& p2Arr[m].getId() != 'S') {
						game.getChildren().remove(p1Arr[n].getRec());
						boardPiece temp = new boardPiece("Null", 12000, 12000, logic);
						p1Arr[n] = temp;

						game.getChildren().remove(p2Arr[m].getRec());
						boardPiece temp2 = new boardPiece("Null", 12000, 12000, logic);
						p2Arr[m] = temp2;
					} else if (p2Arr[m].getId() < '4' && p2Arr[m].getId() != 'B' && p2Arr[m].getId() != 'F'
							&& p2Arr[m].getId() != 'S') {
						game.getChildren().remove(p2Arr[m].getRec());
						boardPiece temp = new boardPiece("Null", 12000, 12000, logic);
						p2Arr[m] = temp;
					}

					return true;
				} else if (p1Arr[n].getId() == '5') {

					if (p2Arr[m].getId() > '5' && p2Arr[m].getId() != 'B' && p2Arr[m].getId() != 'F'
							&& p2Arr[m].getId() != 'S') {
						game.getChildren().remove(p1Arr[n].getRec());
						boardPiece temp = new boardPiece("Null", 12000, 12000, logic);
						p1Arr[n] = temp;
					} else if (p2Arr[m].getId() == '5' && p2Arr[m].getId() != 'B' && p2Arr[m].getId() != 'F'
							&& p2Arr[m].getId() != 'S') {
						game.getChildren().remove(p1Arr[n].getRec());
						boardPiece temp = new boardPiece("Null", 12000, 12000, logic);
						p1Arr[n] = temp;

						game.getChildren().remove(p2Arr[m].getRec());
						boardPiece temp2 = new boardPiece("Null", 12000, 12000, logic);
						p2Arr[m] = temp2;
					} else if (p2Arr[m].getId() < '5' && p2Arr[m].getId() != 'B' && p2Arr[m].getId() != 'F'
							&& p2Arr[m].getId() != 'S') {
						game.getChildren().remove(p2Arr[m].getRec());
						boardPiece temp = new boardPiece("Null", 12000, 12000, logic);
						p2Arr[m] = temp;
					}

					return true;
				} else if (p1Arr[n].getId() == '6') {

					if (p2Arr[m].getId() > '6' && p2Arr[m].getId() != 'B' && p2Arr[m].getId() != 'F'
							&& p2Arr[m].getId() != 'S') {
						game.getChildren().remove(p1Arr[n].getRec());
						boardPiece temp = new boardPiece("Null", 12000, 12000, logic);
						p1Arr[n] = temp;
					} else if (p2Arr[m].getId() == '6' && p2Arr[m].getId() != 'B' && p2Arr[m].getId() != 'F'
							&& p2Arr[m].getId() != 'S') {
						game.getChildren().remove(p1Arr[n].getRec());
						boardPiece temp = new boardPiece("Null", 12000, 12000, logic);
						p1Arr[n] = temp;

						game.getChildren().remove(p2Arr[m].getRec());
						boardPiece temp2 = new boardPiece("Null", 12000, 12000, logic);
						p2Arr[m] = temp2;
					} else if (p2Arr[m].getId() < '6' && p2Arr[m].getId() != 'B' && p2Arr[m].getId() != 'F'
							&& p2Arr[m].getId() != 'S') {
						game.getChildren().remove(p2Arr[m].getRec());
						boardPiece temp = new boardPiece("Null", 12000, 12000, logic);
						p2Arr[m] = temp;
					}

					return true;
				} else if (p1Arr[n].getId() == '7') {

					if (p2Arr[m].getId() > '7' && p2Arr[m].getId() != 'B' && p2Arr[m].getId() != 'F'
							&& p2Arr[m].getId() != 'S') {
						game.getChildren().remove(p1Arr[n].getRec());
						boardPiece temp = new boardPiece("Null", 12000, 12000, logic);
						p1Arr[n] = temp;
					} else if (p2Arr[m].getId() == '7' && p2Arr[m].getId() != 'B' && p2Arr[m].getId() != 'F'
							&& p2Arr[m].getId() != 'S') {
						game.getChildren().remove(p1Arr[n].getRec());
						boardPiece temp = new boardPiece("Null", 12000, 12000, logic);
						p1Arr[n] = temp;

						game.getChildren().remove(p2Arr[m].getRec());
						boardPiece temp2 = new boardPiece("Null", 12000, 12000, logic);
						p2Arr[m] = temp2;
					} else if (p2Arr[m].getId() < '7' && p2Arr[m].getId() != 'B' && p2Arr[m].getId() != 'F'
							&& p2Arr[m].getId() != 'S') {
						game.getChildren().remove(p2Arr[m].getRec());
						boardPiece temp = new boardPiece("Null", 12000, 12000, logic);
						p2Arr[m] = temp;
					}

					return true;
				} else if (p1Arr[n].getId() == '8') {

					if (p2Arr[m].getId() > '8' && p2Arr[m].getId() != 'B' && p2Arr[m].getId() != 'F'
							&& p2Arr[m].getId() != 'S') {
						game.getChildren().remove(p1Arr[n].getRec());
						boardPiece temp = new boardPiece("Null", 12000, 12000, logic);
						p1Arr[n] = temp;
					} else if (p2Arr[m].getId() == '8' && p2Arr[m].getId() != 'B' && p2Arr[m].getId() != 'F'
							&& p2Arr[m].getId() != 'S') {
						game.getChildren().remove(p1Arr[n].getRec());
						boardPiece temp = new boardPiece("Null", 12000, 12000, logic);
						p1Arr[n] = temp;

						game.getChildren().remove(p2Arr[m].getRec());
						boardPiece temp2 = new boardPiece("Null", 12000, 12000, logic);
						p2Arr[m] = temp2;
					} else if (p2Arr[m].getId() < '8' && p2Arr[m].getId() != 'B' && p2Arr[m].getId() != 'F'
							&& p2Arr[m].getId() != 'S') {
						game.getChildren().remove(p2Arr[m].getRec());
						boardPiece temp = new boardPiece("Null", 12000, 12000, logic);
						p2Arr[m] = temp;
					}

					return true;
				} else if (p1Arr[n].getId() == '9') {

					if (p2Arr[m].getId() > '9' && p2Arr[m].getId() != 'B' && p2Arr[m].getId() != 'F'
							&& p2Arr[m].getId() != 'S') {
						game.getChildren().remove(p1Arr[n].getRec());
						boardPiece temp = new boardPiece("Null", 12000, 12000, logic);
						p1Arr[n] = temp;
					} else if (p2Arr[m].getId() == '9' && p2Arr[m].getId() != 'B' && p2Arr[m].getId() != 'F'
							&& p2Arr[m].getId() != 'S') {
						game.getChildren().remove(p1Arr[n].getRec());
						boardPiece temp = new boardPiece("Null", 12000, 12000, logic);
						p1Arr[n] = temp;

						game.getChildren().remove(p2Arr[m].getRec());
						boardPiece temp2 = new boardPiece("Null", 12000, 12000, logic);
						p2Arr[m] = temp2;
					} else if (p2Arr[m].getId() < '9' && p2Arr[m].getId() != 'B' && p2Arr[m].getId() != 'F'
							&& p2Arr[m].getId() != 'S') {
						game.getChildren().remove(p2Arr[m].getRec());
						boardPiece temp = new boardPiece("Null", 12000, 12000, logic);
						p2Arr[m] = temp;
					}

					return true;
				} else if (p1Arr[n].getId() == 'T') {

					if (p2Arr[m].getId() > 'T' && p2Arr[m].getId() != 'B' && p2Arr[m].getId() != 'F'
							&& p2Arr[m].getId() != 'S') {
						game.getChildren().remove(p1Arr[n].getRec());
						boardPiece temp = new boardPiece("Null", 12000, 12000, logic);
						p1Arr[n] = temp;
					} else if (p2Arr[m].getId() == 'T' && p2Arr[m].getId() != 'B' && p2Arr[m].getId() != 'F'
							&& p2Arr[m].getId() != 'S') {
						game.getChildren().remove(p1Arr[n].getRec());
						boardPiece temp = new boardPiece("Null", 12000, 12000, logic);
						p1Arr[n] = temp;

						game.getChildren().remove(p2Arr[m].getRec());
						boardPiece temp2 = new boardPiece("Null", 12000, 12000, logic);
						p2Arr[m] = temp2;
					} else if (p2Arr[m].getId() < 'T' && p2Arr[m].getId() != 'B' && p2Arr[m].getId() != 'F'
							&& p2Arr[m].getId() != 'S') {
						game.getChildren().remove(p2Arr[m].getRec());
						boardPiece temp = new boardPiece("Null", 12000, 12000, logic);
						p2Arr[m] = temp;
					}

					return true;
				} else if (p1Arr[n].getId() == 'S') {
					game.getChildren().remove(p1Arr[n].getRec());
					boardPiece temp = new boardPiece("Null", 12000, 12000, logic);
					p1Arr[n] = temp;

					return true;
				}
			} else {
				if (p1Arr[n].getId() == 'T') {
					game.getChildren().remove(p1Arr[n].getRec());
					boardPiece temp = new boardPiece("Null", 12000, 12000, logic);
					p1Arr[n] = temp;
				} else {
					game.getChildren().remove(p2Arr[m].getRec());
					boardPiece temp = new boardPiece("Null", 12000, 12000, logic);
					p2Arr[m] = temp;
				}
				return true;
			}

		}
		return false;
	}

	boolean computeCpu(int m, Pane game, Stage primaryStage, Scene win, Logic logic) {
		int n = -1;
		for (int i = 0; i < 40; i++) {
			if (p2Arr[i].getX() == p1Arr[m].getX() && p2Arr[i].getY() == p1Arr[m].getY()) {
				n = i;
				break;
			}
		}
		if (n != -1) {
			if (p2Arr[n].getId() == 'F') {

				wg.setText("CPU Wins");
				primaryStage.setScene(win);
			}
			if (p1Arr[m].getId() != 'S') {
				if (p2Arr[n].getId() == 'B') {
					game.getChildren().remove(p1Arr[m].getRec());
					boardPiece temp = new boardPiece("Null", 12000, 12000, logic);
					p1Arr[m] = temp;

					game.getChildren().remove(p2Arr[n].getRec());
					boardPiece temp2 = new boardPiece("Null", 12000, 12000, logic);
					p2Arr[n] = temp2;

					return true;
				} else if (p2Arr[n].getId() == '2') {

					if (p1Arr[m].getId() > '2' && p1Arr[m].getId() != 'B' && p1Arr[m].getId() != 'F'
							&& p1Arr[m].getId() != 'S') {
						game.getChildren().remove(p2Arr[n].getRec());
						boardPiece temp = new boardPiece("Null", 12000, 12000, logic);
						p2Arr[n] = temp;
					} else if (p1Arr[m].getId() == '2' && p1Arr[m].getId() != 'B' && p1Arr[m].getId() != 'F'
							&& p1Arr[m].getId() != 'S') {
						game.getChildren().remove(p2Arr[n].getRec());
						boardPiece temp = new boardPiece("Null", 12000, 12000, logic);
						p2Arr[n] = temp;

						game.getChildren().remove(p1Arr[m].getRec());
						boardPiece temp2 = new boardPiece("Null", 12000, 12000, logic);
						p1Arr[m] = temp2;
					}

					return true;
				} else if (p2Arr[n].getId() == '3') {

					if (p1Arr[m].getId() > '3' && p1Arr[m].getId() != 'B' && p1Arr[m].getId() != 'F'
							&& p1Arr[m].getId() != 'S') {
						game.getChildren().remove(p2Arr[n].getRec());
						boardPiece temp = new boardPiece("Null", 12000, 12000, logic);
						p2Arr[n] = temp;
					} else if (p1Arr[m].getId() == '3' && p1Arr[m].getId() != 'B' && p1Arr[m].getId() != 'F'
							&& p1Arr[m].getId() != 'S') {
						game.getChildren().remove(p2Arr[n].getRec());
						boardPiece temp = new boardPiece("Null", 12000, 12000, logic);
						p2Arr[n] = temp;

						game.getChildren().remove(p1Arr[m].getRec());
						boardPiece temp2 = new boardPiece("Null", 12000, 12000, logic);
						p1Arr[m] = temp2;
					} else if (p1Arr[m].getId() < '3' && p1Arr[m].getId() != 'B' && p1Arr[m].getId() != 'F'
							&& p1Arr[m].getId() != 'S') {
						game.getChildren().remove(p1Arr[m].getRec());
						boardPiece temp = new boardPiece("Null", 12000, 12000, logic);
						p1Arr[m] = temp;
					}

					return true;
				} else if (p2Arr[n].getId() == '4') {

					if (p1Arr[m].getId() > '4' && p1Arr[m].getId() != 'B' && p1Arr[m].getId() != 'F'
							&& p1Arr[m].getId() != 'S') {
						game.getChildren().remove(p2Arr[n].getRec());
						boardPiece temp = new boardPiece("Null", 12000, 12000, logic);
						p2Arr[n] = temp;
					} else if (p1Arr[m].getId() == '4' && p1Arr[m].getId() != 'B' && p1Arr[m].getId() != 'F'
							&& p1Arr[m].getId() != 'S') {
						game.getChildren().remove(p2Arr[n].getRec());
						boardPiece temp = new boardPiece("Null", 12000, 12000, logic);
						p2Arr[n] = temp;

						game.getChildren().remove(p1Arr[m].getRec());
						boardPiece temp2 = new boardPiece("Null", 12000, 12000, logic);
						p1Arr[m] = temp2;
					} else if (p1Arr[m].getId() < '4' && p1Arr[m].getId() != 'B' && p1Arr[m].getId() != 'F'
							&& p1Arr[m].getId() != 'S') {
						game.getChildren().remove(p1Arr[m].getRec());
						boardPiece temp = new boardPiece("Null", 12000, 12000, logic);
						p1Arr[m] = temp;
					}

					return true;
				} else if (p2Arr[n].getId() == '5') {

					if (p1Arr[m].getId() > '5' && p1Arr[m].getId() != 'B' && p1Arr[m].getId() != 'F'
							&& p1Arr[m].getId() != 'S') {
						game.getChildren().remove(p2Arr[n].getRec());
						boardPiece temp = new boardPiece("Null", 12000, 12000, logic);
						p2Arr[n] = temp;
					} else if (p1Arr[m].getId() == '5' && p1Arr[m].getId() != 'B' && p1Arr[m].getId() != 'F'
							&& p1Arr[m].getId() != 'S') {
						game.getChildren().remove(p2Arr[n].getRec());
						boardPiece temp = new boardPiece("Null", 12000, 12000, logic);
						p2Arr[n] = temp;

						game.getChildren().remove(p1Arr[m].getRec());
						boardPiece temp2 = new boardPiece("Null", 12000, 12000, logic);
						p1Arr[m] = temp2;
					} else if (p1Arr[m].getId() < '5' && p1Arr[m].getId() != 'B' && p1Arr[m].getId() != 'F'
							&& p1Arr[m].getId() != 'S') {
						game.getChildren().remove(p1Arr[m].getRec());
						boardPiece temp = new boardPiece("Null", 12000, 12000, logic);
						p1Arr[m] = temp;
					}

					return true;
				} else if (p2Arr[n].getId() == '6') {

					if (p1Arr[m].getId() > '6' && p1Arr[m].getId() != 'B' && p1Arr[m].getId() != 'F'
							&& p1Arr[m].getId() != 'S') {
						game.getChildren().remove(p2Arr[n].getRec());
						boardPiece temp = new boardPiece("Null", 12000, 12000, logic);
						p2Arr[n] = temp;
					} else if (p1Arr[m].getId() == '6' && p1Arr[m].getId() != 'B' && p1Arr[m].getId() != 'F'
							&& p1Arr[m].getId() != 'S') {
						game.getChildren().remove(p2Arr[n].getRec());
						boardPiece temp = new boardPiece("Null", 12000, 12000, logic);
						p2Arr[n] = temp;

						game.getChildren().remove(p1Arr[m].getRec());
						boardPiece temp2 = new boardPiece("Null", 12000, 12000, logic);
						p1Arr[m] = temp2;
					} else if (p1Arr[m].getId() < '6' && p1Arr[m].getId() != 'B' && p1Arr[m].getId() != 'F'
							&& p1Arr[m].getId() != 'S') {
						game.getChildren().remove(p1Arr[m].getRec());
						boardPiece temp = new boardPiece("Null", 12000, 12000, logic);
						p1Arr[m] = temp;
					}

					return true;
				} else if (p2Arr[n].getId() == '7') {

					if (p1Arr[m].getId() > '7' && p1Arr[m].getId() != 'B' && p1Arr[m].getId() != 'F'
							&& p1Arr[m].getId() != 'S') {
						game.getChildren().remove(p2Arr[n].getRec());
						boardPiece temp = new boardPiece("Null", 12000, 12000, logic);
						p2Arr[n] = temp;
					} else if (p1Arr[m].getId() == '7' && p1Arr[m].getId() != 'B' && p1Arr[m].getId() != 'F'
							&& p1Arr[m].getId() != 'S') {
						game.getChildren().remove(p2Arr[n].getRec());
						boardPiece temp = new boardPiece("Null", 12000, 12000, logic);
						p2Arr[n] = temp;

						game.getChildren().remove(p1Arr[m].getRec());
						boardPiece temp2 = new boardPiece("Null", 12000, 12000, logic);
						p1Arr[m] = temp2;
					} else if (p1Arr[m].getId() < '7' && p1Arr[m].getId() != 'B' && p1Arr[m].getId() != 'F'
							&& p1Arr[m].getId() != 'S') {
						game.getChildren().remove(p1Arr[m].getRec());
						boardPiece temp = new boardPiece("Null", 12000, 12000, logic);
						p1Arr[m] = temp;
					}

					return true;
				} else if (p2Arr[n].getId() == '8') {

					if (p1Arr[m].getId() > '8' && p1Arr[m].getId() != 'B' && p1Arr[m].getId() != 'F'
							&& p1Arr[m].getId() != 'S') {
						game.getChildren().remove(p2Arr[n].getRec());
						boardPiece temp = new boardPiece("Null", 12000, 12000, logic);
						p2Arr[n] = temp;
					} else if (p1Arr[m].getId() == '8' && p1Arr[m].getId() != 'B' && p1Arr[m].getId() != 'F'
							&& p1Arr[m].getId() != 'S') {
						game.getChildren().remove(p2Arr[n].getRec());
						boardPiece temp = new boardPiece("Null", 12000, 12000, logic);
						p2Arr[n] = temp;

						game.getChildren().remove(p1Arr[m].getRec());
						boardPiece temp2 = new boardPiece("Null", 12000, 12000, logic);
						p1Arr[m] = temp2;
					} else if (p1Arr[m].getId() < '8' && p1Arr[m].getId() != 'B' && p1Arr[m].getId() != 'F'
							&& p1Arr[m].getId() != 'S') {
						game.getChildren().remove(p1Arr[m].getRec());
						boardPiece temp = new boardPiece("Null", 12000, 12000, logic);
						p1Arr[m] = temp;
					}

					return true;
				} else if (p2Arr[n].getId() == '9') {

					if (p1Arr[m].getId() > '9' && p1Arr[m].getId() != 'B' && p1Arr[m].getId() != 'F'
							&& p1Arr[m].getId() != 'S') {
						game.getChildren().remove(p2Arr[n].getRec());
						boardPiece temp = new boardPiece("Null", 12000, 12000, logic);
						p2Arr[n] = temp;
					} else if (p1Arr[m].getId() == '9' && p1Arr[m].getId() != 'B' && p1Arr[m].getId() != 'F'
							&& p1Arr[m].getId() != 'S') {
						game.getChildren().remove(p2Arr[n].getRec());
						boardPiece temp = new boardPiece("Null", 12000, 12000, logic);
						p2Arr[n] = temp;

						game.getChildren().remove(p1Arr[m].getRec());
						boardPiece temp2 = new boardPiece("Null", 12000, 12000, logic);
						p1Arr[m] = temp2;
					} else if (p1Arr[m].getId() < '9' && p1Arr[m].getId() != 'B' && p1Arr[m].getId() != 'F'
							&& p1Arr[m].getId() != 'S') {
						game.getChildren().remove(p1Arr[m].getRec());
						boardPiece temp = new boardPiece("Null", 12000, 12000, logic);
						p1Arr[m] = temp;
					}

					return true;
				} else if (p2Arr[n].getId() == 'T') {

					if (p1Arr[m].getId() > 'T' && p1Arr[m].getId() != 'B' && p1Arr[m].getId() != 'F'
							&& p1Arr[m].getId() != 'S') {
						game.getChildren().remove(p2Arr[n].getRec());
						boardPiece temp = new boardPiece("Null", 12000, 12000, logic);
						p2Arr[n] = temp;
					} else if (p1Arr[m].getId() == 'T' && p1Arr[m].getId() != 'B' && p1Arr[m].getId() != 'F'
							&& p1Arr[m].getId() != 'S') {
						game.getChildren().remove(p2Arr[n].getRec());
						boardPiece temp = new boardPiece("Null", 12000, 12000, logic);
						p2Arr[n] = temp;

						game.getChildren().remove(p1Arr[m].getRec());
						boardPiece temp2 = new boardPiece("Null", 12000, 12000, logic);
						p1Arr[m] = temp2;
					} else if (p1Arr[m].getId() < 'T' && p1Arr[m].getId() != 'B' && p1Arr[m].getId() != 'F'
							&& p1Arr[m].getId() != 'S') {
						game.getChildren().remove(p1Arr[m].getRec());
						boardPiece temp = new boardPiece("Null", 12000, 12000, logic);
						p1Arr[m] = temp;
					}

					return true;
				} else if (p2Arr[n].getId() == 'S') {
					game.getChildren().remove(p2Arr[n].getRec());
					boardPiece temp = new boardPiece("Null", 12000, 12000, logic);
					p2Arr[n] = temp;

					return true;
				}
			} else {
				if (p2Arr[n].getId() == 'T') {
					game.getChildren().remove(p2Arr[n].getRec());
					boardPiece temp = new boardPiece("Null", 12000, 12000, logic);
					p2Arr[n] = temp;
				} else {
					game.getChildren().remove(p1Arr[m].getRec());
					boardPiece temp = new boardPiece("Null", 12000, 12000, logic);
					p1Arr[m] = temp;
				}
				return true;
			}

		}
		return false;
	}

	int cpuMove() throws InterruptedException {

		Random rand = new Random();
		int n = rand.nextInt(40);
		int move = -1;
		boolean found = false;
		while (!found) {
			n = rand.nextInt(40);
			if (p1Arr[n].getId() == 'F' || p1Arr[n].getId() == 'B') {
				n = rand.nextInt(40);
				continue;
			}
			if (p1Arr[n].getRec().getX() != 12000) {
				if (p1Arr[n].getId() != '2') {
					int l = 0, r = 0, u = 0, d = 0;
					while (true) {
						if (l == 1 && r == 1 && u == 1 && d == 1) {
							l = 0;
							r = 0;
							u = 0;
							d = 0;
							break;
						}
						move = rand.nextInt(4);
						if (move == 0) {
							if (search(p1Arr[n].getX() - 1, p1Arr[n].getY()) == -1 && p1Arr[n].getX() - 1 != -1) {
								p1Arr[n].getRec().setX(p1Arr[n].getRec().getX() - 72);
								found = true;
								break;
							} else {
								l = 1;
								continue;
							}
						} else if (move == 1) {
							if (search(p1Arr[n].getX() + 1, p1Arr[n].getY()) == -1 && p1Arr[n].getX() + 1 != 10) {
								p1Arr[n].getRec().setX(p1Arr[n].getRec().getX() + 72);
								found = true;
								break;
							} else {
								r = 1;
								continue;
							}
						} else if (move == 2) {
							if (search(p1Arr[n].getX(), p1Arr[n].getY() - 1) == -1 && p1Arr[n].getY() - 1 != -1) {
								p1Arr[n].getRec().setY(p1Arr[n].getRec().getY() - 72);
								found = true;
								break;
							} else {
								u = 1;
								continue;
							}
						} else if (move == 3) {
							if (search(p1Arr[n].getX(), p1Arr[n].getY() + 1) == -1 && p1Arr[n].getY() + 1 != 10) {
								p1Arr[n].getRec().setY(p1Arr[n].getRec().getY() + 72);
								found = true;
								break;
							} else {
								d = 1;
								continue;
							}
						}

					}
				}
			}

		}

		return n;
	}

	int search(int x, int y) {
		int n = -1;
		for (int i = 0; i < 40; i++) {
			if (p1Arr[i].getX() == x && p1Arr[i].getY() == y) {
				n = i;
			}
		}
		return n;
	}
}
