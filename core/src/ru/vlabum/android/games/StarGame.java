package ru.vlabum.android.games;

import com.badlogic.gdx.Game;

import ru.vlabum.android.games.screen.MenuScreen;

public class StarGame extends Game {

	@Override
	public void create () {
		final MenuScreen menuScreen = new MenuScreen(this);
		setScreen(menuScreen);
	}

}
