package ru.vlabum.android.games;

import com.badlogic.gdx.Game;

import ru.vlabum.android.games.screen.MainScreen;

public class StarGame extends Game {

	@Override
	public void create () {
		final MainScreen mainScreen = new MainScreen();
		setScreen(mainScreen);
	}

}
