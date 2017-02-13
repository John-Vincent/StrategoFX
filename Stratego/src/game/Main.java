/**
 * @author Manthan
 */
package game;

import java.util.Scanner;
import java.util.Random;

public class Main {
	/*
	 * Basic Rules Flag - 1 (F) Bomb - 6 (B) Spy - 1 (S) Scout - 8 (9) Miner - 5
	 * (8) Sergant - 4 (7) Lieutenant - 4 (6) Captain - 4 (5) Major - 3 (4)
	 * Colonel - 2 (3) General - 1 (2) Marshall - 1 (1)
	 * 
	 */
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
	final static char scout = '9';
	final static char miner = '8';
	final static char sergant = '7';
	final static char lieutenant = '7';
	final static char captain = '6';
	final static char major = '5';
	final static char colonel = '4';
	final static char general = '3';
	final static char marshall = '2';

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

	public static void main(String args[]) {
		boolean vsAI = false;
		boolean vsPlayer = false;
		boolean random = false;
		System.out.println("Start Game? (Y/N)");
		Scanner scan = new Scanner(System.in);
		String str = scan.nextLine();
		// Game Start loop
		while (true) {
			if (str.compareToIgnoreCase("y") == 0) {
				System.out.println("Start");
				break;
			} else if (str.compareToIgnoreCase("n") == 0) {
				System.out.println("Stop");
				System.exit(0);
			} else {
				System.out.println("Wrong Input, Enter again.");
				str = scan.nextLine();

			}
		}
		System.out.println("1. vs AI \n2. vs Player");
		str = scan.nextLine();
		// Game Mode
		while (true) {
			if (str.compareToIgnoreCase("1") == 0) {
				vsAI = true;
				System.out.println("AI mode selected");
				break;
			} else if (str.compareToIgnoreCase("2") == 0) {
				System.out.println("Not available yet, try again.");
				str = scan.nextLine();
				// break;
			} else {
				System.out.println("Wrong Input, Enter again.");
				str = scan.nextLine();

			}
		}

		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				if (i == 4 || i == 5) {
					if (j == 2 || j == 3 || j == 6 || j == 7) {
						board[i][j] = 'X';
						actualBoard[i][j] = 'X';
					} else {
						board[i][j] = 95;
						actualBoard[i][j] = 95;
					}
				} else {
					board[i][j] = '?';
					actualBoard[i][j] = '?';
				}
			}
		}

		// Player piece arrangement
		System.out.println("Arrange pieces randomly? (Y/N)");
		str = scan.nextLine();
		if (vsAI == true) {
			arrangeCpu();
			while (true) {
				if (str.compareToIgnoreCase("y") == 0) {
					System.out.println("Arranging pieces randomly...");
					random = true;
					break;
				} else if (str.compareToIgnoreCase("n") == 0) {
					random = false;
					System.out.println("Unfortunately, this is unavailable. Enter again.");
					str = scan.nextLine();
				} else {
					System.out.println("Wrong Input, Enter again.");
					str = scan.nextLine();

				}

			}
			System.out.println("");
			if (random == true) {
				arrangeRandomly();

			}

			for (int i = 0; i < 10; i++) {
				for (int j = 0; j < 10; j++) {
					System.out.print(actualBoard[i][j] + "  ");
				}
				System.out.println();
			}

			System.out.println();

			for (int i = 0; i < 10; i++) {
				for (int j = 0; j < 10; j++) {
					//System.out.print(board[i][j] + "  ");
				}
				//System.out.println();
			}
		

		}
	}
}
