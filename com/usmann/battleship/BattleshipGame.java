package com.usmann.battleship;

public class BattleshipGame {
	
	Board[] boards = new Board[2];
	int numClients;
	Client[] clients = new Client[2];
	int curTurn;
	
	//initializes board
	public BattleshipGame() {
		for(Board brd : boards) {
			brd = new Board();
		}
		numClients = 0;
		curTurn = 0;
	}
	
	//returns true for success, false for failure
	public boolean addClient(Client client) {
		if(numClients >= 2)
			return false;
		else if(numClients == 1) {
			numClients++;
			this.clients[1] = client;
			return true;
		} else {
			numClients++;
			this.clients[0] = client;
			return true;
		}
	}
	
	//Attack a spot on the board
	public int attack(Client client, int x, int y) {
		int result;
		if(client.equals(clients[0])) {
			result = boards[1].attack(x, y);
			if(result == 1)
				boards[0].updateHit(x, y);
		} else if(client.equals(clients[1])) {
			result = boards[0].attack(x, y);
			if(result == 1)
				boards[1].updateHit(x, y);
		} else return -1;
		
		switchTurn();
		return result;
	}
	
	public void switchTurn() {
		curTurn = 1-curTurn;
		clients[curTurn].triggerTurn();
	}
	
	//Return the status of your board
	public int[][] getOwnBoard(Client client) {
		if(client.equals(clients[0]))
			return boards[1].getBoard();
		else if(client.equals(clients[1]))
			return boards[0].getBoard();
		else return null;
	}
	
	//Return the places and results of your attacks
	public int[][] getHitBoard(Client client) {
		if(client.equals(clients[0]))
			return boards[0].getHitBoard();
		else if(client.equals(clients[1]))
			return boards[1].getHitBoard();
		else return null;
	}
	
	
	private class Board {
		private final static int EMPTY = -1;
		private final static int HIT = 1;
		private final static int SHIP = 0;
		private final static int MISS = 2;
		private final static int ERROR = -1;
		
		int[][] board;
		int[][] hitBoard;
		
		public Board() {
			board = new int[5][5];
			hitBoard = new int[5][5];
			
			for(int[] row : board) {
				for(int cell = 0; cell < row.length; cell++) {
					row[cell] = EMPTY;
				}
			}
		}
		
		public int attack(int row, int col) {
			if(this.board[row][col] > 0) {
				return ERROR;
			} else if(board[row][col] == EMPTY) {
				this.board[row][col] = MISS;
				return MISS;
			} else {
				this.board[row][col] = HIT;
				return HIT;
			}
		}
		
		public int[][] getBoard() {
			return this.board;
		}
		
		public int[][] getHitBoard() {
			return this.hitBoard;
		}
		
		public void updateHit(int x, int y) {
			hitBoard[x][y] = 1;
		}
		
	}
}
