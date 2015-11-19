package com.usmann.battleship;

import java.util.Scanner;
import java.util.regex.Pattern;

public class PlayerClient implements Client{
	BattleshipGame game;
	private final int[] SHIP_LENGTHS = {5,4,3,3,2};
	
	public PlayerClient(BattleshipGame game) {
		this.game = game;
	}

	@Override
	public void triggerTurn() {
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Your turn!");
		int[][] board = game.getOwnBoard(this);
		int[][] oppBoard = game.getHitBoard(this);
		System.out.println("Opponent's board:");
		printOppBoard(oppBoard);
		System.out.println("Your board:");
		printOwnBoard(board);
		
		String entry;
		do{
			System.out.println("Pick a spot to attack");
			System.out.print("Enter a cell in the form A,1: ");
			entry = sc.nextLine();
		}while(!validateHit(entry, board));
		
	}
	
	//process to place ships on the map
	@Override
	public void setUpShips() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Place Ships");
		for(int length : this.SHIP_LENGTHS) {
			int[][] board = game.getOwnBoard(this);
			printOwnBoard(board);
			String entry;
			do{
				System.out.printf("Place a ship of length %d\n", length);
				System.out.print("Enter a cell in the form A,1,d (3rd value is direction. Either d,u,l, or r): ");
				entry = sc.nextLine();
			}while(!validateShipPlacement(entry, board, length));
		}
		sc.close();
	}
	
	private boolean validateHit(String entry, int[][] hitboard) {
		
		if(!Pattern.matches("^[A-E],[0-4]$", entry)) {
				System.out.println("Invalid cell\n");
				return false;
		}
		
		String[] sections = entry.split(",");
		int row = Integer.parseInt(sections[1]);
		int col = entry.charAt(0) - 'A';
		
		return hitboard[row][col] == -1;
	}
	
	private void printOwnBoard(int[][] board) {

		System.out.print("\n  ");
		for(char ch = 'A'; ch <= 'E'; ch++) {
			System.out.print(ch + " ");
		}
		System.out.println();
		for(int i = 0; i < board.length; i++) {
			System.out.print(i + " ");
			for(int j : board[i]) {
				if(j == -1)
					System.out.print("- ");
				else if(j == 0)
					System.out.print("s ");
				else if(j == 1)
					System.out.print("X ");
			}
			System.out.println("\n");
		}
	}
	
	private void printOppBoard(int[][] board) {

		System.out.print("\n  ");
		for(char ch = 'A'; ch <= 'E'; ch++) {
			System.out.print(ch + " ");
		}
		System.out.println();
		for(int i = 0; i < board.length; i++) {
			System.out.print(i + " ");
			for(int j : board[i]) {
				if(j == -1)
					System.out.print("- ");
				else if(j == 1)
					System.out.print("X ");
				else if(j == 2)
					System.out.print("M ");
			}
			System.out.println("\n");
		}
	}
	
	//Validates initial cell and prompts for further input accordingly
	private boolean validateShipPlacement(String entry, int[][] board, int length) {
		length -= 1;
		
		if(!Pattern.matches("^[A-E],[0-4],[dulr]$", entry)) {
				System.out.println("Invalid cell\n");
				return false;
		}
		String[] sections = entry.split(",");
		int row = Integer.parseInt(sections[1]);
		int col = entry.charAt(0) - 'A';
		int maxRow = row;
		int maxCol = col;
		
		if(sections[2].equals("d"))
			maxRow += length;
		else if(sections[2].equals("u"))
			row -= length;
		else if(sections[2].equals("l"))
			col -= length;
		else if(sections[2].equals("r"))
			maxCol += length;

		for(int i = row; i <= maxRow; i++) {
			for(int j = col; j <= maxCol; j++) {
				if(!(i >= 0 && i <= 4) || !(j >= 0 && j <= 4)) {
					System.out.println("Invalid cell\n");
					return false;
				}
				if(board[i][j] != -1) {
					System.out.println("Invalid cell\n");
					return false;
				}
			}
		}
		
		game.addShip(this, row, col, maxRow, maxCol);
		
		return true;
	}

	@Override
	public void alertGameOver(int status) {
		if(status == 1)
			System.out.println("Congratulations! You Won!");
		else
			System.out.println("You lost.");
	}
	
	public static void main(String[] args) {
		BattleshipGame game = new BattleshipGame();
		PlayerClient c1 = new PlayerClient(game);
		PlayerClient c2 = new PlayerClient(game);
		game.addClient(c1);
		game.addClient(c2);
		game.startGame();
	}

}
