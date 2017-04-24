package stratego.components.gameboard;

import java.io.File;
import java.util.Random;
import java.util.Scanner;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

/**
 * Class for game logic
 *
 * @author manthan
 *
 */
public class Logic {

	private final static char flag = 'F';
	private final static char bomb = 'B';
	private final static char spy = 'S';
	private final static char scout = '2';
	private final static char miner = '3';
	private final static char sergant = '4';
	private final static char lieutenant = '5';
	private final static char captain = '6';
	private final static char major = '7';
	private final static char colonel = '8';
	private final static char general = '9';
	private final static char marshall = 'T';

	static char board[][] = new char[10][10];
	static char actualBoard[][] = new char[10][10];

	/*
	 * Number of pieces of each piece for player 1
	 */
	static int flagCount = 0;
	static int bombCount = 0;
	static int spyCount = 0;
	static int scoutCount = 0;
	static int minerCount = 0;
	static int sergantCount = 0;
	static int lieutenantCount = 0;
	static int captainCount = 0;
	static int majorCount = 0;
	static int colonelCount = 0;
	static int generalCount = 0;
	static int marshallCount = 0;

	/*
	 * Number of pieces of each piece for player 2
	 */
	static int cpuflagCount = 0;
	static int cpubombCount = 0;
	static int cpuspyCount = 0;
	static int cpuscoutCount = 0;
	static int cpuminerCount = 0;
	static int cpusergantCount = 0;
	static int cpulieutenantCount = 0;
	static int cpucaptainCount = 0;
	static int cpumajorCount = 0;
	static int cpucolonelCount = 0;
	static int cpugeneralCount = 0;
	static int cpumarshallCount = 0;
	static Random rand = new Random();

	/**
	 * Arranges the player board pieces randomly
	 */
	static void arrangeRandomly() {

		for (int i = 0; i < 40; i++) {
			int dice = rand.nextInt(12);

			int x = 6 + rand.nextInt(4);
			int y = rand.nextInt(10);

			while (actualBoard[x][y] != '?') {
				x = 6 + rand.nextInt(4);
				y = rand.nextInt(10);
			}

			switch (dice) {
			case 0:
				if (cpumarshallCount < 1) {
					board[x][y] = marshall;
					actualBoard[x][y] = marshall;
					cpumarshallCount++;
				} else {
					i--;
					break;
				}
				break;
			case 1:
				if (cpugeneralCount < 1) {
					board[x][y] = general;
					actualBoard[x][y] = general;
					cpugeneralCount++;
				} else {
					i--;
					break;
				}
				break;
			case 2:
				if (cpucolonelCount < 2) {
					board[x][y] = colonel;
					actualBoard[x][y] = colonel;
					cpucolonelCount++;
				} else {
					i--;
					break;
				}
				break;
			case 3:
				if (cpumajorCount < 3) {
					board[x][y] = major;
					actualBoard[x][y] = major;
					cpumajorCount++;
				} else {
					i--;
					break;
				}
				break;
			case 4:
				if (cpucaptainCount < 4) {
					board[x][y] = captain;
					actualBoard[x][y] = captain;
					cpucaptainCount++;
				} else {
					i--;
					break;
				}
				break;
			case 5:
				if (cpulieutenantCount < 4) {
					board[x][y] = lieutenant;
					actualBoard[x][y] = lieutenant;
					cpulieutenantCount++;
				} else {
					i--;
					break;
				}
				break;
			case 6:
				if (cpusergantCount < 4) {
					board[x][y] = sergant;
					actualBoard[x][y] = sergant;
					cpusergantCount++;
				} else {
					i--;
					break;
				}
				break;
			case 7:
				if (cpuminerCount < 5) {
					board[x][y] = miner;
					actualBoard[x][y] = miner;
					cpuminerCount++;
				} else {
					i--;
					break;
				}
				break;
			case 8:
				if (cpuscoutCount < 8) {
					board[x][y] = scout;
					actualBoard[x][y] = scout;
					cpuscoutCount++;
				} else {
					i--;
					break;
				}
				break;
			case 9:
				if (cpuspyCount < 1) {
					board[x][y] = spy;
					actualBoard[x][y] = spy;
					cpuspyCount++;
				} else {
					i--;
					break;
				}
				break;
			case 10:
				if (cpubombCount < 6) {
					board[x][y] = bomb;
					actualBoard[x][y] = bomb;
					cpubombCount++;
				} else {
					i--;
					break;
				}
				break;
			case 11:
				if (cpuflagCount < 1) {
					board[x][y] = flag;
					actualBoard[x][y] = flag;
					cpuflagCount++;
				} else {
					i--;
					break;
				}
				break;

			}

		}
	}

	/**
	 * Arranges the cpu pieces randomly
	 */
	static void arrangeCpu() {
		for (int i = 0; i < 40; i++) {
			int dice = rand.nextInt(12);

			int x = rand.nextInt(4);
			int y = rand.nextInt(10);

			while (actualBoard[x][y] != '?') {
				x = rand.nextInt(4);
				y = rand.nextInt(10);
			}

			switch (dice) {
			case 0:
				if (marshallCount < 1) {
					actualBoard[x][y] = marshall;
					marshallCount++;
				} else {
					i--;
					break;
				}
				break;
			case 1:
				if (generalCount < 1) {
					actualBoard[x][y] = general;
					generalCount++;
				} else {
					i--;
					break;
				}
				break;
			case 2:
				if (colonelCount < 2) {
					actualBoard[x][y] = colonel;
					colonelCount++;
				} else {
					i--;
					break;
				}
				break;
			case 3:
				if (majorCount < 3) {
					actualBoard[x][y] = major;
					majorCount++;
				} else {
					i--;
					break;
				}
				break;
			case 4:
				if (captainCount < 4) {
					actualBoard[x][y] = captain;
					captainCount++;
				} else {
					i--;
					break;
				}
				break;
			case 5:
				if (lieutenantCount < 4) {
					actualBoard[x][y] = lieutenant;
					lieutenantCount++;
				} else {
					i--;
					break;
				}
				break;
			case 6:
				if (sergantCount < 4) {
					actualBoard[x][y] = sergant;
					sergantCount++;
				} else {
					i--;
					break;
				}
				break;
			case 7:
				if (minerCount < 5) {
					actualBoard[x][y] = miner;
					minerCount++;
				} else {
					i--;
					break;
				}
				break;
			case 8:
				if (scoutCount < 8) {
					actualBoard[x][y] = scout;
					scoutCount++;
				} else {
					i--;
					break;
				}
				break;
			case 9:
				if (spyCount < 1) {
					actualBoard[x][y] = spy;
					spyCount++;
				} else {
					i--;
					break;
				}
				break;
			case 10:
				if (bombCount < 6) {
					actualBoard[x][y] = bomb;
					bombCount++;
				} else {
					i--;
					break;
				}
				break;
			case 11:
				if (flagCount < 1) {
					actualBoard[x][y] = flag;
					flagCount++;
				} else {
					i--;
					break;
				}
				break;

			}

		}
	}

	/**
	 * Puts the randomly selected pieces on board
	 */
	public static void arrange() {

		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				actualBoard[i][j] = '?';
			}
		}

		flagCount = 0;
		bombCount = 0;
		spyCount = 0;
		scoutCount = 0;
		minerCount = 0;
		sergantCount = 0;
		lieutenantCount = 0;
		captainCount = 0;
		majorCount = 0;
		colonelCount = 0;
		generalCount = 0;
		marshallCount = 0;

		cpuflagCount = 0;
		cpubombCount = 0;
		cpuspyCount = 0;
		cpuscoutCount = 0;
		cpuminerCount = 0;
		cpusergantCount = 0;
		cpulieutenantCount = 0;
		cpucaptainCount = 0;
		cpumajorCount = 0;
		cpucolonelCount = 0;
		cpugeneralCount = 0;
		cpumarshallCount = 0;

		arrangeRandomly();
		arrangeCpu();

		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				// System.out.print(" " + actualBoard[i][j]);
			}
			// System.out.println("");
		}

	}

	/**
	 * Computes the result after player 1 moves
	 *
	 * @param m
	 *            index of the piece that moved
	 * @param game
	 *            instance of game pance
	 * @param logic
	 *            instance of stratego.components.Logic
	 * @return true if a piece was eliminated else returns false
	 */
	static boolean computeResult(int m, Pane game) {
		int n = -1;
		for (int i = 0; i < 40; i++) {

			if (GameScene.p1Arr[i].getX() == GameScene.p2Arr[m].getX()
					&& GameScene.p1Arr[i].getY() == GameScene.p2Arr[m].getY()) {
				n = i;
				break;
			}
		}
		if (n != -1) {
			if (GameScene.p1Arr[n].getId() == 'F') {

				System.out.print("Player Wins");
				GameScene.win.setVisible(true);
				
				double volume = stratego.components.MusicPlayer.effectPlayer.getVolume();
				stratego.components.MusicPlayer.effectPlayer.stop();
				stratego.components.MusicPlayer.effectPlayer = new MediaPlayer(new Media(new File("ffvi-fanfare.mp3").toURI().toString()));
				stratego.components.MusicPlayer.effectPlayer.setCycleCount(MediaPlayer.INDEFINITE);
				stratego.components.MusicPlayer.effectPlayer.setVolume(volume);
				stratego.components.MusicPlayer.effectPlayer.play();

				// wg.setText("Player Wins");
				// primaryStage.setScene(win);
			}
			if (GameScene.p2Arr[m].getId() != 'S') {
				if (GameScene.p1Arr[n].getId() == 'B') {
					game.getChildren().remove(GameScene.p2Arr[m].getRec());
					BoardPiece temp = new BoardPiece("Null", 12000, 12000);
					GameScene.p2Arr[m] = temp;

					game.getChildren().remove(GameScene.p1Arr[n].getRec());
					BoardPiece temp2 = new BoardPiece("Null", 12000, 12000);
					GameScene.p1Arr[n] = temp2;

					return true;
				} else if (GameScene.p1Arr[n].getId() == '2') {

					if (GameScene.p2Arr[m].getId() > '2' && GameScene.p2Arr[m].getId() != 'B'
							&& GameScene.p2Arr[m].getId() != 'F' && GameScene.p2Arr[m].getId() != 'S') {
						game.getChildren().remove(GameScene.p1Arr[n].getRec());
						BoardPiece temp = new BoardPiece("Null", 12000, 12000);
						GameScene.p1Arr[n] = temp;
					} else if (GameScene.p2Arr[m].getId() == '2' && GameScene.p2Arr[m].getId() != 'B'
							&& GameScene.p2Arr[m].getId() != 'F' && GameScene.p2Arr[m].getId() != 'S') {
						game.getChildren().remove(GameScene.p1Arr[n].getRec());
						BoardPiece temp = new BoardPiece("Null", 12000, 12000);
						GameScene.p1Arr[n] = temp;

						game.getChildren().remove(GameScene.p2Arr[m].getRec());
						BoardPiece temp2 = new BoardPiece("Null", 12000, 12000);
						GameScene.p2Arr[m] = temp2;
					}

					return true;
				} else if (GameScene.p1Arr[n].getId() == '3') {

					if (GameScene.p2Arr[m].getId() > '3' && GameScene.p2Arr[m].getId() != 'B'
							&& GameScene.p2Arr[m].getId() != 'F' && GameScene.p2Arr[m].getId() != 'S') {
						game.getChildren().remove(GameScene.p1Arr[n].getRec());
						BoardPiece temp = new BoardPiece("Null", 12000, 12000);
						GameScene.p1Arr[n] = temp;
					} else if (GameScene.p2Arr[m].getId() == '3' && GameScene.p2Arr[m].getId() != 'B'
							&& GameScene.p2Arr[m].getId() != 'F' && GameScene.p2Arr[m].getId() != 'S') {
						game.getChildren().remove(GameScene.p1Arr[n].getRec());
						BoardPiece temp = new BoardPiece("Null", 12000, 12000);
						GameScene.p1Arr[n] = temp;

						game.getChildren().remove(GameScene.p2Arr[m].getRec());
						BoardPiece temp2 = new BoardPiece("Null", 12000, 12000);
						GameScene.p2Arr[m] = temp2;
					} else if (GameScene.p2Arr[m].getId() < '3' && GameScene.p2Arr[m].getId() != 'B'
							&& GameScene.p2Arr[m].getId() != 'F' && GameScene.p2Arr[m].getId() != 'S') {
						game.getChildren().remove(GameScene.p2Arr[m].getRec());
						BoardPiece temp = new BoardPiece("Null", 12000, 12000);
						GameScene.p2Arr[m] = temp;
					}

					return true;
				} else if (GameScene.p1Arr[n].getId() == '4') {

					if (GameScene.p2Arr[m].getId() > '4' && GameScene.p2Arr[m].getId() != 'B'
							&& GameScene.p2Arr[m].getId() != 'F' && GameScene.p2Arr[m].getId() != 'S') {
						game.getChildren().remove(GameScene.p1Arr[n].getRec());
						BoardPiece temp = new BoardPiece("Null", 12000, 12000);
						GameScene.p1Arr[n] = temp;
					} else if (GameScene.p2Arr[m].getId() == '4' && GameScene.p2Arr[m].getId() != 'B'
							&& GameScene.p2Arr[m].getId() != 'F' && GameScene.p2Arr[m].getId() != 'S') {
						game.getChildren().remove(GameScene.p1Arr[n].getRec());
						BoardPiece temp = new BoardPiece("Null", 12000, 12000);
						GameScene.p1Arr[n] = temp;

						game.getChildren().remove(GameScene.p2Arr[m].getRec());
						BoardPiece temp2 = new BoardPiece("Null", 12000, 12000);
						GameScene.p2Arr[m] = temp2;
					} else if (GameScene.p2Arr[m].getId() < '4' && GameScene.p2Arr[m].getId() != 'B'
							&& GameScene.p2Arr[m].getId() != 'F' && GameScene.p2Arr[m].getId() != 'S') {
						game.getChildren().remove(GameScene.p2Arr[m].getRec());
						BoardPiece temp = new BoardPiece("Null", 12000, 12000);
						GameScene.p2Arr[m] = temp;
					}

					return true;
				} else if (GameScene.p1Arr[n].getId() == '5') {

					if (GameScene.p2Arr[m].getId() > '5' && GameScene.p2Arr[m].getId() != 'B'
							&& GameScene.p2Arr[m].getId() != 'F' && GameScene.p2Arr[m].getId() != 'S') {
						game.getChildren().remove(GameScene.p1Arr[n].getRec());
						BoardPiece temp = new BoardPiece("Null", 12000, 12000);
						GameScene.p1Arr[n] = temp;
					} else if (GameScene.p2Arr[m].getId() == '5' && GameScene.p2Arr[m].getId() != 'B'
							&& GameScene.p2Arr[m].getId() != 'F' && GameScene.p2Arr[m].getId() != 'S') {
						game.getChildren().remove(GameScene.p1Arr[n].getRec());
						BoardPiece temp = new BoardPiece("Null", 12000, 12000);
						GameScene.p1Arr[n] = temp;

						game.getChildren().remove(GameScene.p2Arr[m].getRec());
						BoardPiece temp2 = new BoardPiece("Null", 12000, 12000);
						GameScene.p2Arr[m] = temp2;
					} else if (GameScene.p2Arr[m].getId() < '5' && GameScene.p2Arr[m].getId() != 'B'
							&& GameScene.p2Arr[m].getId() != 'F' && GameScene.p2Arr[m].getId() != 'S') {
						game.getChildren().remove(GameScene.p2Arr[m].getRec());
						BoardPiece temp = new BoardPiece("Null", 12000, 12000);
						GameScene.p2Arr[m] = temp;
					}

					return true;
				} else if (GameScene.p1Arr[n].getId() == '6') {

					if (GameScene.p2Arr[m].getId() > '6' && GameScene.p2Arr[m].getId() != 'B'
							&& GameScene.p2Arr[m].getId() != 'F' && GameScene.p2Arr[m].getId() != 'S') {
						game.getChildren().remove(GameScene.p1Arr[n].getRec());
						BoardPiece temp = new BoardPiece("Null", 12000, 12000);
						GameScene.p1Arr[n] = temp;
					} else if (GameScene.p2Arr[m].getId() == '6' && GameScene.p2Arr[m].getId() != 'B'
							&& GameScene.p2Arr[m].getId() != 'F' && GameScene.p2Arr[m].getId() != 'S') {
						game.getChildren().remove(GameScene.p1Arr[n].getRec());
						BoardPiece temp = new BoardPiece("Null", 12000, 12000);
						GameScene.p1Arr[n] = temp;

						game.getChildren().remove(GameScene.p2Arr[m].getRec());
						BoardPiece temp2 = new BoardPiece("Null", 12000, 12000);
						GameScene.p2Arr[m] = temp2;
					} else if (GameScene.p2Arr[m].getId() < '6' && GameScene.p2Arr[m].getId() != 'B'
							&& GameScene.p2Arr[m].getId() != 'F' && GameScene.p2Arr[m].getId() != 'S') {
						game.getChildren().remove(GameScene.p2Arr[m].getRec());
						BoardPiece temp = new BoardPiece("Null", 12000, 12000);
						GameScene.p2Arr[m] = temp;
					}

					return true;
				} else if (GameScene.p1Arr[n].getId() == '7') {

					if (GameScene.p2Arr[m].getId() > '7' && GameScene.p2Arr[m].getId() != 'B'
							&& GameScene.p2Arr[m].getId() != 'F' && GameScene.p2Arr[m].getId() != 'S') {
						game.getChildren().remove(GameScene.p1Arr[n].getRec());
						BoardPiece temp = new BoardPiece("Null", 12000, 12000);
						GameScene.p1Arr[n] = temp;
					} else if (GameScene.p2Arr[m].getId() == '7' && GameScene.p2Arr[m].getId() != 'B'
							&& GameScene.p2Arr[m].getId() != 'F' && GameScene.p2Arr[m].getId() != 'S') {
						game.getChildren().remove(GameScene.p1Arr[n].getRec());
						BoardPiece temp = new BoardPiece("Null", 12000, 12000);
						GameScene.p1Arr[n] = temp;

						game.getChildren().remove(GameScene.p2Arr[m].getRec());
						BoardPiece temp2 = new BoardPiece("Null", 12000, 12000);
						GameScene.p2Arr[m] = temp2;
					} else if (GameScene.p2Arr[m].getId() < '7' && GameScene.p2Arr[m].getId() != 'B'
							&& GameScene.p2Arr[m].getId() != 'F' && GameScene.p2Arr[m].getId() != 'S') {
						game.getChildren().remove(GameScene.p2Arr[m].getRec());
						BoardPiece temp = new BoardPiece("Null", 12000, 12000);
						GameScene.p2Arr[m] = temp;
					}

					return true;
				} else if (GameScene.p1Arr[n].getId() == '8') {

					if (GameScene.p2Arr[m].getId() > '8' && GameScene.p2Arr[m].getId() != 'B'
							&& GameScene.p2Arr[m].getId() != 'F' && GameScene.p2Arr[m].getId() != 'S') {
						game.getChildren().remove(GameScene.p1Arr[n].getRec());
						BoardPiece temp = new BoardPiece("Null", 12000, 12000);
						GameScene.p1Arr[n] = temp;
					} else if (GameScene.p2Arr[m].getId() == '8' && GameScene.p2Arr[m].getId() != 'B'
							&& GameScene.p2Arr[m].getId() != 'F' && GameScene.p2Arr[m].getId() != 'S') {
						game.getChildren().remove(GameScene.p1Arr[n].getRec());
						BoardPiece temp = new BoardPiece("Null", 12000, 12000);
						GameScene.p1Arr[n] = temp;

						game.getChildren().remove(GameScene.p2Arr[m].getRec());
						BoardPiece temp2 = new BoardPiece("Null", 12000, 12000);
						GameScene.p2Arr[m] = temp2;
					} else if (GameScene.p2Arr[m].getId() < '8' && GameScene.p2Arr[m].getId() != 'B'
							&& GameScene.p2Arr[m].getId() != 'F' && GameScene.p2Arr[m].getId() != 'S') {
						game.getChildren().remove(GameScene.p2Arr[m].getRec());
						BoardPiece temp = new BoardPiece("Null", 12000, 12000);
						GameScene.p2Arr[m] = temp;
					}

					return true;
				} else if (GameScene.p1Arr[n].getId() == '9') {

					if (GameScene.p2Arr[m].getId() > '9' && GameScene.p2Arr[m].getId() != 'B'
							&& GameScene.p2Arr[m].getId() != 'F' && GameScene.p2Arr[m].getId() != 'S') {
						game.getChildren().remove(GameScene.p1Arr[n].getRec());
						BoardPiece temp = new BoardPiece("Null", 12000, 12000);
						GameScene.p1Arr[n] = temp;
					} else if (GameScene.p2Arr[m].getId() == '9' && GameScene.p2Arr[m].getId() != 'B'
							&& GameScene.p2Arr[m].getId() != 'F' && GameScene.p2Arr[m].getId() != 'S') {
						game.getChildren().remove(GameScene.p1Arr[n].getRec());
						BoardPiece temp = new BoardPiece("Null", 12000, 12000);
						GameScene.p1Arr[n] = temp;

						game.getChildren().remove(GameScene.p2Arr[m].getRec());
						BoardPiece temp2 = new BoardPiece("Null", 12000, 12000);
						GameScene.p2Arr[m] = temp2;
					} else if (GameScene.p2Arr[m].getId() < '9' && GameScene.p2Arr[m].getId() != 'B'
							&& GameScene.p2Arr[m].getId() != 'F' && GameScene.p2Arr[m].getId() != 'S') {
						game.getChildren().remove(GameScene.p2Arr[m].getRec());
						BoardPiece temp = new BoardPiece("Null", 12000, 12000);
						GameScene.p2Arr[m] = temp;
					}

					return true;
				} else if (GameScene.p1Arr[n].getId() == 'T') {

					if (GameScene.p2Arr[m].getId() > 'T' && GameScene.p2Arr[m].getId() != 'B'
							&& GameScene.p2Arr[m].getId() != 'F' && GameScene.p2Arr[m].getId() != 'S') {
						game.getChildren().remove(GameScene.p1Arr[n].getRec());
						BoardPiece temp = new BoardPiece("Null", 12000, 12000);
						GameScene.p1Arr[n] = temp;
					} else if (GameScene.p2Arr[m].getId() == 'T' && GameScene.p2Arr[m].getId() != 'B'
							&& GameScene.p2Arr[m].getId() != 'F' && GameScene.p2Arr[m].getId() != 'S') {
						game.getChildren().remove(GameScene.p1Arr[n].getRec());
						BoardPiece temp = new BoardPiece("Null", 12000, 12000);
						GameScene.p1Arr[n] = temp;

						game.getChildren().remove(GameScene.p2Arr[m].getRec());
						BoardPiece temp2 = new BoardPiece("Null", 12000, 12000);
						GameScene.p2Arr[m] = temp2;
					} else if (GameScene.p2Arr[m].getId() < 'T' && GameScene.p2Arr[m].getId() != 'B'
							&& GameScene.p2Arr[m].getId() != 'F' && GameScene.p2Arr[m].getId() != 'S') {
						game.getChildren().remove(GameScene.p2Arr[m].getRec());
						BoardPiece temp = new BoardPiece("Null", 12000, 12000);
						GameScene.p2Arr[m] = temp;
					}

					return true;
				} else if (GameScene.p1Arr[n].getId() == 'S') {
					game.getChildren().remove(GameScene.p1Arr[n].getRec());
					BoardPiece temp = new BoardPiece("Null", 12000, 12000);
					GameScene.p1Arr[n] = temp;

					return true;
				}
			} else {
				if (GameScene.p1Arr[n].getId() == 'T') {
					game.getChildren().remove(GameScene.p1Arr[n].getRec());
					BoardPiece temp = new BoardPiece("Null", 12000, 12000);
					GameScene.p1Arr[n] = temp;
				} else {
					game.getChildren().remove(GameScene.p2Arr[m].getRec());
					BoardPiece temp = new BoardPiece("Null", 12000, 12000);
					GameScene.p2Arr[m] = temp;
				}
				return true;
			}

		}
		return false;
	}

	/**
	 * Computes the result after player 2 moves
	 *
	 * @param m
	 *            index of the piece that moved
	 * @param game
	 *            instance of game pance
	 * @param logic
	 *            instance of stratego.components.Logic
	 * @return true if a piece was eliminated else returns false
	 */
	static boolean computeCpu(int m, Pane game) {
		int n = -1;
		for (int i = 0; i < 40; i++) {
			if (GameScene.p2Arr[i].getX() == GameScene.p1Arr[m].getX()
					&& GameScene.p2Arr[i].getY() == GameScene.p1Arr[m].getY()) {
				n = i;
				break;
			}
		}
		if (n != -1) {
			if (GameScene.p2Arr[n].getId() == 'F') {

				System.out.println("CPU Wins");
				GameScene.win2.setVisible(true);
			}
			if (GameScene.p1Arr[m].getId() != 'S') {
				if (GameScene.p2Arr[n].getId() == 'B') {
					game.getChildren().remove(GameScene.p1Arr[m].getRec());
					BoardPiece temp = new BoardPiece("Null", 12000, 12000);
					GameScene.p1Arr[m] = temp;

					game.getChildren().remove(GameScene.p2Arr[n].getRec());
					BoardPiece temp2 = new BoardPiece("Null", 12000, 12000);
					GameScene.p2Arr[n] = temp2;

					return true;
				} else if (GameScene.p2Arr[n].getId() == '2') {

					if (GameScene.p1Arr[m].getId() > '2' && GameScene.p1Arr[m].getId() != 'B'
							&& GameScene.p1Arr[m].getId() != 'F' && GameScene.p1Arr[m].getId() != 'S') {
						game.getChildren().remove(GameScene.p2Arr[n].getRec());
						BoardPiece temp = new BoardPiece("Null", 12000, 12000);
						GameScene.p2Arr[n] = temp;
					} else if (GameScene.p1Arr[m].getId() == '2' && GameScene.p1Arr[m].getId() != 'B'
							&& GameScene.p1Arr[m].getId() != 'F' && GameScene.p1Arr[m].getId() != 'S') {
						game.getChildren().remove(GameScene.p2Arr[n].getRec());
						BoardPiece temp = new BoardPiece("Null", 12000, 12000);
						GameScene.p2Arr[n] = temp;

						game.getChildren().remove(GameScene.p1Arr[m].getRec());
						BoardPiece temp2 = new BoardPiece("Null", 12000, 12000);
						GameScene.p1Arr[m] = temp2;
					}

					return true;
				} else if (GameScene.p2Arr[n].getId() == '3') {

					if (GameScene.p1Arr[m].getId() > '3' && GameScene.p1Arr[m].getId() != 'B'
							&& GameScene.p1Arr[m].getId() != 'F' && GameScene.p1Arr[m].getId() != 'S') {
						game.getChildren().remove(GameScene.p2Arr[n].getRec());
						BoardPiece temp = new BoardPiece("Null", 12000, 12000);
						GameScene.p2Arr[n] = temp;
					} else if (GameScene.p1Arr[m].getId() == '3' && GameScene.p1Arr[m].getId() != 'B'
							&& GameScene.p1Arr[m].getId() != 'F' && GameScene.p1Arr[m].getId() != 'S') {
						game.getChildren().remove(GameScene.p2Arr[n].getRec());
						BoardPiece temp = new BoardPiece("Null", 12000, 12000);
						GameScene.p2Arr[n] = temp;

						game.getChildren().remove(GameScene.p1Arr[m].getRec());
						BoardPiece temp2 = new BoardPiece("Null", 12000, 12000);
						GameScene.p1Arr[m] = temp2;
					} else if (GameScene.p1Arr[m].getId() < '3' && GameScene.p1Arr[m].getId() != 'B'
							&& GameScene.p1Arr[m].getId() != 'F' && GameScene.p1Arr[m].getId() != 'S') {
						game.getChildren().remove(GameScene.p1Arr[m].getRec());
						BoardPiece temp = new BoardPiece("Null", 12000, 12000);
						GameScene.p1Arr[m] = temp;
					}

					return true;
				} else if (GameScene.p2Arr[n].getId() == '4') {

					if (GameScene.p1Arr[m].getId() > '4' && GameScene.p1Arr[m].getId() != 'B'
							&& GameScene.p1Arr[m].getId() != 'F' && GameScene.p1Arr[m].getId() != 'S') {
						game.getChildren().remove(GameScene.p2Arr[n].getRec());
						BoardPiece temp = new BoardPiece("Null", 12000, 12000);
						GameScene.p2Arr[n] = temp;
					} else if (GameScene.p1Arr[m].getId() == '4' && GameScene.p1Arr[m].getId() != 'B'
							&& GameScene.p1Arr[m].getId() != 'F' && GameScene.p1Arr[m].getId() != 'S') {
						game.getChildren().remove(GameScene.p2Arr[n].getRec());
						BoardPiece temp = new BoardPiece("Null", 12000, 12000);
						GameScene.p2Arr[n] = temp;

						game.getChildren().remove(GameScene.p1Arr[m].getRec());
						BoardPiece temp2 = new BoardPiece("Null", 12000, 12000);
						GameScene.p1Arr[m] = temp2;
					} else if (GameScene.p1Arr[m].getId() < '4' && GameScene.p1Arr[m].getId() != 'B'
							&& GameScene.p1Arr[m].getId() != 'F' && GameScene.p1Arr[m].getId() != 'S') {
						game.getChildren().remove(GameScene.p1Arr[m].getRec());
						BoardPiece temp = new BoardPiece("Null", 12000, 12000);
						GameScene.p1Arr[m] = temp;
					}

					return true;
				} else if (GameScene.p2Arr[n].getId() == '5') {

					if (GameScene.p1Arr[m].getId() > '5' && GameScene.p1Arr[m].getId() != 'B'
							&& GameScene.p1Arr[m].getId() != 'F' && GameScene.p1Arr[m].getId() != 'S') {
						game.getChildren().remove(GameScene.p2Arr[n].getRec());
						BoardPiece temp = new BoardPiece("Null", 12000, 12000);
						GameScene.p2Arr[n] = temp;
					} else if (GameScene.p1Arr[m].getId() == '5' && GameScene.p1Arr[m].getId() != 'B'
							&& GameScene.p1Arr[m].getId() != 'F' && GameScene.p1Arr[m].getId() != 'S') {
						game.getChildren().remove(GameScene.p2Arr[n].getRec());
						BoardPiece temp = new BoardPiece("Null", 12000, 12000);
						GameScene.p2Arr[n] = temp;

						game.getChildren().remove(GameScene.p1Arr[m].getRec());
						BoardPiece temp2 = new BoardPiece("Null", 12000, 12000);
						GameScene.p1Arr[m] = temp2;
					} else if (GameScene.p1Arr[m].getId() < '5' && GameScene.p1Arr[m].getId() != 'B'
							&& GameScene.p1Arr[m].getId() != 'F' && GameScene.p1Arr[m].getId() != 'S') {
						game.getChildren().remove(GameScene.p1Arr[m].getRec());
						BoardPiece temp = new BoardPiece("Null", 12000, 12000);
						GameScene.p1Arr[m] = temp;
					}

					return true;
				} else if (GameScene.p2Arr[n].getId() == '6') {

					if (GameScene.p1Arr[m].getId() > '6' && GameScene.p1Arr[m].getId() != 'B'
							&& GameScene.p1Arr[m].getId() != 'F' && GameScene.p1Arr[m].getId() != 'S') {
						game.getChildren().remove(GameScene.p2Arr[n].getRec());
						BoardPiece temp = new BoardPiece("Null", 12000, 12000);
						GameScene.p2Arr[n] = temp;
					} else if (GameScene.p1Arr[m].getId() == '6' && GameScene.p1Arr[m].getId() != 'B'
							&& GameScene.p1Arr[m].getId() != 'F' && GameScene.p1Arr[m].getId() != 'S') {
						game.getChildren().remove(GameScene.p2Arr[n].getRec());
						BoardPiece temp = new BoardPiece("Null", 12000, 12000);
						GameScene.p2Arr[n] = temp;

						game.getChildren().remove(GameScene.p1Arr[m].getRec());
						BoardPiece temp2 = new BoardPiece("Null", 12000, 12000);
						GameScene.p1Arr[m] = temp2;
					} else if (GameScene.p1Arr[m].getId() < '6' && GameScene.p1Arr[m].getId() != 'B'
							&& GameScene.p1Arr[m].getId() != 'F' && GameScene.p1Arr[m].getId() != 'S') {
						game.getChildren().remove(GameScene.p1Arr[m].getRec());
						BoardPiece temp = new BoardPiece("Null", 12000, 12000);
						GameScene.p1Arr[m] = temp;
					}

					return true;
				} else if (GameScene.p2Arr[n].getId() == '7') {

					if (GameScene.p1Arr[m].getId() > '7' && GameScene.p1Arr[m].getId() != 'B'
							&& GameScene.p1Arr[m].getId() != 'F' && GameScene.p1Arr[m].getId() != 'S') {
						game.getChildren().remove(GameScene.p2Arr[n].getRec());
						BoardPiece temp = new BoardPiece("Null", 12000, 12000);
						GameScene.p2Arr[n] = temp;
					} else if (GameScene.p1Arr[m].getId() == '7' && GameScene.p1Arr[m].getId() != 'B'
							&& GameScene.p1Arr[m].getId() != 'F' && GameScene.p1Arr[m].getId() != 'S') {
						game.getChildren().remove(GameScene.p2Arr[n].getRec());
						BoardPiece temp = new BoardPiece("Null", 12000, 12000);
						GameScene.p2Arr[n] = temp;

						game.getChildren().remove(GameScene.p1Arr[m].getRec());
						BoardPiece temp2 = new BoardPiece("Null", 12000, 12000);
						GameScene.p1Arr[m] = temp2;
					} else if (GameScene.p1Arr[m].getId() < '7' && GameScene.p1Arr[m].getId() != 'B'
							&& GameScene.p1Arr[m].getId() != 'F' && GameScene.p1Arr[m].getId() != 'S') {
						game.getChildren().remove(GameScene.p1Arr[m].getRec());
						BoardPiece temp = new BoardPiece("Null", 12000, 12000);
						GameScene.p1Arr[m] = temp;
					}

					return true;
				} else if (GameScene.p2Arr[n].getId() == '8') {

					if (GameScene.p1Arr[m].getId() > '8' && GameScene.p1Arr[m].getId() != 'B'
							&& GameScene.p1Arr[m].getId() != 'F' && GameScene.p1Arr[m].getId() != 'S') {
						game.getChildren().remove(GameScene.p2Arr[n].getRec());
						BoardPiece temp = new BoardPiece("Null", 12000, 12000);
						GameScene.p2Arr[n] = temp;
					} else if (GameScene.p1Arr[m].getId() == '8' && GameScene.p1Arr[m].getId() != 'B'
							&& GameScene.p1Arr[m].getId() != 'F' && GameScene.p1Arr[m].getId() != 'S') {
						game.getChildren().remove(GameScene.p2Arr[n].getRec());
						BoardPiece temp = new BoardPiece("Null", 12000, 12000);
						GameScene.p2Arr[n] = temp;

						game.getChildren().remove(GameScene.p1Arr[m].getRec());
						BoardPiece temp2 = new BoardPiece("Null", 12000, 12000);
						GameScene.p1Arr[m] = temp2;
					} else if (GameScene.p1Arr[m].getId() < '8' && GameScene.p1Arr[m].getId() != 'B'
							&& GameScene.p1Arr[m].getId() != 'F' && GameScene.p1Arr[m].getId() != 'S') {
						game.getChildren().remove(GameScene.p1Arr[m].getRec());
						BoardPiece temp = new BoardPiece("Null", 12000, 12000);
						GameScene.p1Arr[m] = temp;
					}

					return true;
				} else if (GameScene.p2Arr[n].getId() == '9') {

					if (GameScene.p1Arr[m].getId() > '9' && GameScene.p1Arr[m].getId() != 'B'
							&& GameScene.p1Arr[m].getId() != 'F' && GameScene.p1Arr[m].getId() != 'S') {
						game.getChildren().remove(GameScene.p2Arr[n].getRec());
						BoardPiece temp = new BoardPiece("Null", 12000, 12000);
						GameScene.p2Arr[n] = temp;
					} else if (GameScene.p1Arr[m].getId() == '9' && GameScene.p1Arr[m].getId() != 'B'
							&& GameScene.p1Arr[m].getId() != 'F' && GameScene.p1Arr[m].getId() != 'S') {
						game.getChildren().remove(GameScene.p2Arr[n].getRec());
						BoardPiece temp = new BoardPiece("Null", 12000, 12000);
						GameScene.p2Arr[n] = temp;

						game.getChildren().remove(GameScene.p1Arr[m].getRec());
						BoardPiece temp2 = new BoardPiece("Null", 12000, 12000);
						GameScene.p1Arr[m] = temp2;
					} else if (GameScene.p1Arr[m].getId() < '9' && GameScene.p1Arr[m].getId() != 'B'
							&& GameScene.p1Arr[m].getId() != 'F' && GameScene.p1Arr[m].getId() != 'S') {
						game.getChildren().remove(GameScene.p1Arr[m].getRec());
						BoardPiece temp = new BoardPiece("Null", 12000, 12000);
						GameScene.p1Arr[m] = temp;
					}

					return true;
				} else if (GameScene.p2Arr[n].getId() == 'T') {

					if (GameScene.p1Arr[m].getId() > 'T' && GameScene.p1Arr[m].getId() != 'B'
							&& GameScene.p1Arr[m].getId() != 'F' && GameScene.p1Arr[m].getId() != 'S') {
						game.getChildren().remove(GameScene.p2Arr[n].getRec());
						BoardPiece temp = new BoardPiece("Null", 12000, 12000);
						GameScene.p2Arr[n] = temp;
					} else if (GameScene.p1Arr[m].getId() == 'T' && GameScene.p1Arr[m].getId() != 'B'
							&& GameScene.p1Arr[m].getId() != 'F' && GameScene.p1Arr[m].getId() != 'S') {
						game.getChildren().remove(GameScene.p2Arr[n].getRec());
						BoardPiece temp = new BoardPiece("Null", 12000, 12000);
						GameScene.p2Arr[n] = temp;

						game.getChildren().remove(GameScene.p1Arr[m].getRec());
						BoardPiece temp2 = new BoardPiece("Null", 12000, 12000);
						GameScene.p1Arr[m] = temp2;
					} else if (GameScene.p1Arr[m].getId() < 'T' && GameScene.p1Arr[m].getId() != 'B'
							&& GameScene.p1Arr[m].getId() != 'F' && GameScene.p1Arr[m].getId() != 'S') {
						game.getChildren().remove(GameScene.p1Arr[m].getRec());
						BoardPiece temp = new BoardPiece("Null", 12000, 12000);
						GameScene.p1Arr[m] = temp;
					}

					return true;
				} else if (GameScene.p2Arr[n].getId() == 'S') {
					game.getChildren().remove(GameScene.p2Arr[n].getRec());
					BoardPiece temp = new BoardPiece("Null", 12000, 12000);
					GameScene.p2Arr[n] = temp;

					return true;
				}
			} else {
				if (GameScene.p2Arr[n].getId() == 'T') {
					game.getChildren().remove(GameScene.p2Arr[n].getRec());
					BoardPiece temp = new BoardPiece("Null", 12000, 12000);
					GameScene.p2Arr[n] = temp;
				} else {
					game.getChildren().remove(GameScene.p1Arr[m].getRec());
					BoardPiece temp = new BoardPiece("Null", 12000, 12000);
					GameScene.p1Arr[m] = temp;
				}
				return true;
			}

		}
		return false;
	}

	/**
	 * Computes the move to be made by player 2, in this case cpu
	 *
	 * @return n An integer with value either 0,1,2 or 3. 0 equals left, 1
	 *         equals right, 2 equals up, 3 equals down
	 * @throws InterruptedException
	 */
	static int cpuMove() {
		Random rand = new Random();
		int n = rand.nextInt(40);
		int move = -1;
		boolean found = false;
		while (!found) {

			n = rand.nextInt(40);
			if (GameScene.p1Arr[n].getId() == 'F' || GameScene.p1Arr[n].getId() == 'B') {
				n = rand.nextInt(40);
				continue;
			}
			if (GameScene.p1Arr[n].getRec().getX() != 12000) {
				if (GameScene.p1Arr[n].getId() != '2') {
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
							if (search(GameScene.p1Arr[n].getRec().getX() - 72 * GameScene.wFactor,
									GameScene.p1Arr[n].getRec().getY()) == -1 && GameScene.p1Arr[n].getX() - 1 > -1) {
								GameScene.p1Arr[n].getRec()
										.setX(GameScene.p1Arr[n].getRec().getX() - 72 * GameScene.wFactor);
								found = true;
								break;
							} else {
								l = 1;
								continue;
							}
						} else if (move == 1) {
							if (search(GameScene.p1Arr[n].getRec().getX() + 72 * GameScene.wFactor,
									GameScene.p1Arr[n].getRec().getY()) == -1
									&& GameScene.p1Arr[n].getRec().getX() < 10) {
								GameScene.p1Arr[n].getRec()
										.setX(GameScene.p1Arr[n].getRec().getX() + 72 * GameScene.wFactor);
								found = true;
								break;
							} else {
								r = 1;
								continue;
							}
						} else if (move == 2) {
							if (search(GameScene.p1Arr[n].getRec().getX(),
									GameScene.p1Arr[n].getRec().getY() - 72 * GameScene.hFactor) == -1
									&& GameScene.p1Arr[n].getY() - 1 > -1) {

								GameScene.p1Arr[n].getRec()
										.setY(GameScene.p1Arr[n].getRec().getY() - 72 * GameScene.hFactor);
								found = true;
								break;
							} else {
								u = 1;
								continue;
							}
						} else if (move == 3) {
							if (search(GameScene.p1Arr[n].getRec().getX(),
									GameScene.p1Arr[n].getRec().getY() + 72 * GameScene.hFactor) == -1
									&& GameScene.p1Arr[n].getY() + 1 < 10) {
								GameScene.p1Arr[n].getRec()
										.setY(GameScene.p1Arr[n].getRec().getY() + 72 * GameScene.hFactor);
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

	/**
	 * Searches if another player piece exists at that spot.
	 *
	 * @param x
	 *            Horizontal location of the spot to be searched
	 * @param y
	 *            Vertical location of the spot to be searched
	 * @return Returns the index of the piece that is present at the spot else
	 *         if no piece is present return -1
	 */
	static int search(double x, double y) {
		int n = -1;
		for (int i = 0; i < 40; i++) {
			if (((GameScene.p1Arr[i].getRec().getX() >= x - 1) && (GameScene.p1Arr[i].getRec().getX() <= x + 1))
					&& ((GameScene.p1Arr[i].getRec().getY() >= y - 1)
							&& (GameScene.p1Arr[i].getRec().getY() <= y + 1))) {
				n = i;
			}
		}
		return n;
	}

	/**
	 * Searches if same player piece exists at that spot.
	 *
	 * @param x
	 *            Horizontal location of the spot to be searched
	 * @param y
	 *            Vertical location of the spot to be searched
	 * @return True if spot is empty and move can be else returns false
	 */
	static boolean isEmpty(int x, int y) {
		boolean empty = true;
		for (int i = 0; i < 40; i++) {
			if (((GameScene.p2Arr[i].getRec().getY() >= (y-3))&&(GameScene.p2Arr[i].getRec().getY()<= (y+3)))
					&& ((GameScene.p2Arr[i].getRec().getX() >= (x-3))&&(GameScene.p2Arr[i].getRec().getX() <= (x+3)))) {
				empty = false;
				break;
			}
		}
		return empty;
	}

	static void printBoard(){
		for(int i=0; i<10; i++){
			for(int j=0; j<10; j++){
				System.out.print(actualBoard[i][j]+" ");
			}
			System.out.println();
		}
	}

}
