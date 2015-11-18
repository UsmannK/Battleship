package com.usmann.battleship;

public class Client {
	
	public void triggerTurn() {
		
	}
	
	public static void main(String[] args) {
		Client player = new Client();
		BattleshipGame game = new BattleshipGame();
		game.addClient(player);
	}
}
