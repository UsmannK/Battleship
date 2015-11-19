package com.usmann.battleship;

public interface Client {
		
	public void triggerTurn();
	
	public void setUpShips();
	
	public void alertGameOver(int status);
}
