package fx;

import java.util.Random;
import java.util.Scanner;

public class Logic {

	final String f = "FLAG";
	final String b = "BOMB";
	final String sp = "SPY";
	final String s = "SCOUT";
	final String m = "MINER";
	final String se = "SERGANT";
	final String l = "LIEUTENANT";
	final String c = "CAPTAIN";
	final String ma = "MAJOR";
	final String co = "COLONEL";
	final String g = "GENERAL";
	final String mar = "MARSHALL";

	final static char flag = 'F';
	final static char bomb = 'B';
	final static char spy = 'S';
	final static char scout = '2';
	final static char miner = '3';
	final static char sergant = '4';
	final static char lieutenant = '5';
	final static char captain = '6';
	final static char major = '7';
	final static char colonel = '8';
	final static char general = '9';
	final static char marshall = 'T';

	static char board[][] = new char[10][10];
	static char actualBoard[][] = new char[10][10];

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

	static int p2mX;
	static int p2mY;

	static boolean quitGame = false;

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

	void cpuMove() {

	}

	public static void arrange() {

		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				actualBoard[i][j] = '?';
			}
		}

		arrangeRandomly();
		arrangeCpu();

		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				System.out.print(" " + actualBoard[i][j]);
			}
			System.out.println("");
		}

	}

}
