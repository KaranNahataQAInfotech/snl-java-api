package Pages;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.UUID;

import org.json.JSONObject;
import org.testng.Assert;

import com.qainfotech.tap.training.snl.api.Board;
import com.qainfotech.tap.training.snl.api.GameInProgressException;
import com.qainfotech.tap.training.snl.api.InvalidTurnException;
import com.qainfotech.tap.training.snl.api.MaxPlayersReachedExeption;
import com.qainfotech.tap.training.snl.api.NoUserWithSuchUUIDException;
import com.qainfotech.tap.training.snl.api.PlayerExistsException;

public class BoardPage {
	Board board;

	public BoardPage() {
		try {
			board = new Board();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void createPlayers(int n, String name) throws FileNotFoundException, UnsupportedEncodingException,
			IOException, PlayerExistsException, GameInProgressException, MaxPlayersReachedExeption {

		for (int i = 0; i < n; i++) {
			board.registerPlayer(name + " " + i);
		}
	}

	public void rollDiceMethod(int flag)
			throws FileNotFoundException, UnsupportedEncodingException, InvalidTurnException {
		JSONObject data = board.getData();
		Integer turn = data.getInt("turn");
		if (flag == 1)
			turn += 1;
		UUID id = (UUID) data.getJSONArray("players").getJSONObject(turn).get("uuid");
		board.rollDice(id);
	}

	public void deletePlayer() throws FileNotFoundException, UnsupportedEncodingException, NoUserWithSuchUUIDException {
		board.deletePlayer(UUID.randomUUID());
	}

	public void whenLadderAppear() throws FileNotFoundException, UnsupportedEncodingException, InvalidTurnException {
		while (true) {
			JSONObject data = board.getData();
			Integer turn = data.getInt("turn");
			UUID id = (UUID) data.getJSONArray("players").getJSONObject(turn).get("uuid");
			board.rollDice(id);
			int arr[] = { 2, 11, 25, 37, 68, 79 };
			for (int position : arr) {
				JSONObject step = data.getJSONArray("steps").getJSONObject(position);
				if (step.getInt("type") == 2) {
					Assert.assertTrue(true);
					return;
				}
			}
		}
	}

	public void whenSnakeAppear() throws FileNotFoundException, UnsupportedEncodingException, InvalidTurnException {
		while (true) {
			JSONObject data = board.getData();
			Integer turn = data.getInt("turn");
			UUID id = (UUID) data.getJSONArray("players").getJSONObject(turn).get("uuid");
			board.rollDice(id);
			int arr[] = { 99, 93, 55, 70, 23 };
			for (int position : arr) {
				JSONObject step = data.getJSONArray("steps").getJSONObject(position);
				if (step.getInt("type") == 1) {
					Assert.assertTrue(true);
					return;
				}
			}
		}
	}

	public void incorrectRollOfDice() throws FileNotFoundException, UnsupportedEncodingException, InvalidTurnException {
		while (true) {
			JSONObject data = board.getData();
			Integer turn = data.getInt("turn");
			UUID id = (UUID) data.getJSONArray("players").getJSONObject(turn).get("uuid");
			JSONObject response = board.rollDice(id);
			String message = response.getString("message");
			if (message.contains("Incorrect roll")) {
				Assert.assertTrue(true);
				return;
			}
		}
	}
}
