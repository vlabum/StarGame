package ru.vlabum.android.games.sprite;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.vlabum.android.games.base.SpriteButton;
import ru.vlabum.android.games.math.Rect;
import ru.vlabum.android.games.screen.GameScreen;

public class ButtonPlay extends SpriteButton {

    private Game game;

    public ButtonPlay(final TextureAtlas atlas, final Game game) {
        super(atlas.findRegion("btPlay"));
        this.game = game;
        setHeightProportion(0.12f);
    }

    @Override
    public void resize(final Rect worldBounds) {
        super.resize(worldBounds);
        setBottom(worldBounds.getBottom() + halfHeight / 2);
        setLeft(worldBounds.getLeft() + halfWidth / 2);
    }

    @Override
    protected void actionPerformed() {
        game.setScreen(new GameScreen(game));
    }

}
