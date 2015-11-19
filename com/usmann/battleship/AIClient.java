package com.usmann.battleship;

public class AIClient implements Client{

	BattleshipGame game;
	private final int[] SHIP_LENGTHS = {5,4,3,3,2};
	
	public AIClient(BattleshipGame game) {
		this.game = game;
	}
	
	@Override
	public void triggerTurn() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setUpShips() {
	}

	@Override
	public void alertGameOver(int status) {
		
	}

}
