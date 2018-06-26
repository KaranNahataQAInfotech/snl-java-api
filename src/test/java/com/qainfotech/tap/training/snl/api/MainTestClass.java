package com.qainfotech.tap.training.snl.api;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.json.JSONObject;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import Pages.BoardPage;

public class MainTestClass {
	BoardPage page= new BoardPage(); 
	

	@Test(expectedExceptions = MaxPlayersReachedExeption.class)
	public void moreThanFourPlayerJoining() throws FileNotFoundException, UnsupportedEncodingException, IOException,
			PlayerExistsException, GameInProgressException, MaxPlayersReachedExeption {
		page.createPlayers(5, "Ka");
	}

	@Test(expectedExceptions = PlayerExistsException.class)
	public void playerJoiningWithSameName() throws FileNotFoundException, UnsupportedEncodingException, IOException,
			PlayerExistsException, GameInProgressException, MaxPlayersReachedExeption {
		page.createPlayers(3, "Ka");
		page.createPlayers(1, "Ka");
	}

	@Test(expectedExceptions = GameInProgressException.class)
	public void playerJoiningAfterGameStarted() throws FileNotFoundException, UnsupportedEncodingException, IOException,
			PlayerExistsException, GameInProgressException, MaxPlayersReachedExeption, InvalidTurnException {
		page.createPlayers(3, "Ka");
		page.rollDiceMethod(0);
		page.createPlayers(1, "Na");
	}
	
	@Test(expectedExceptions = InvalidTurnException.class)
	public void invalidTurnByPlayer() throws FileNotFoundException, UnsupportedEncodingException, IOException,
			PlayerExistsException, GameInProgressException, MaxPlayersReachedExeption, InvalidTurnException {
		page.createPlayers(4, "Ka");
		page.rollDiceMethod(1);
	}
	
	@Test (expectedExceptions = NoUserWithSuchUUIDException.class)
	public void deleteInvalidPlayer() throws FileNotFoundException, UnsupportedEncodingException, IOException,
			PlayerExistsException, GameInProgressException, MaxPlayersReachedExeption, NoUserWithSuchUUIDException {
		page.createPlayers(4, "Ka");
		page.deletePlayer();
	}
	
	@Test 
	public void jumpingForwardWithLadder() throws FileNotFoundException, UnsupportedEncodingException, IOException,
			PlayerExistsException, GameInProgressException, MaxPlayersReachedExeption, InvalidTurnException {
		page.createPlayers(4, "Ka");
		page.whenLadderAppear();
		
	}
	
	@Test 
	public void jumpingBackwardWithSnake() throws FileNotFoundException, UnsupportedEncodingException, IOException,
			PlayerExistsException, GameInProgressException, MaxPlayersReachedExeption, InvalidTurnException {
		page.createPlayers(4, "Ka");
		page.whenSnakeAppear();
		
	}
	
	@Test
	public void newPositionGreaterThan100() throws FileNotFoundException, UnsupportedEncodingException, IOException,
			PlayerExistsException, GameInProgressException, MaxPlayersReachedExeption, InvalidTurnException {
		page.createPlayers(4, "Ka");
		page.incorrectRollOfDice();
	}
	
	
	@AfterMethod
	public void afterMethod() {
		page=new BoardPage();
	}

}
