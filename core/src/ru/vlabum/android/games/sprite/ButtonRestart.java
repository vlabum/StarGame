package ru.vlabum.android.games.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.vlabum.android.games.base.SpriteButton;
import ru.vlabum.android.games.math.Rect;
import ru.vlabum.android.games.screen.GameScreen;

public class ButtonRestart extends SpriteButton {

    final GameScreen gameScreen;

    public ButtonRestart(final TextureAtlas atlas, final GameScreen gameScreen) {
        super(atlas.findRegion("button_new_game"));
        setHeightProportion(0.1f);
        this.gameScreen = gameScreen;
        setHeightProportion(0.05f);
    }

    @Override
    public void resize(final Rect worldBounds) {
        super.resize(worldBounds);
        setBottom(worldBounds.getBottom() + halfHeight / 2);
        setRight(worldBounds.getRight());
    }

    @Override
    protected void actionPerformed() {
        gameScreen.restart();
    }

}
