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

	final char flag = 'F';
	final char bomb = 'B';
	final char spy = 'S';
	final char scout = '9';
	final char miner = '8';
	final char sergant = '7';
	final char lieutenant = '7';
	final char captain = '6';
	final char major = '5';
	final char colonel = '4';
	final char general = '3';
	final char marshall = '2';

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

		char board[][] = new char[10][10];
		char actualBoard[][] = new char[10][10];

		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				board[i][j] = '?';
			}
		}

		// Player piece arrangement
		System.out.println("Arrange pieces randomly? (Y/N)");
		str = scan.nextLine();
		if (vsAI == true) {
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
		}
	}
}
