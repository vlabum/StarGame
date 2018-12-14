package ru.vlabum.android.games.sprite;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import ru.vlabum.android.games.base.Sprite;
import ru.vlabum.android.games.math.Rect;

public class GameOverMessage extends Sprite {

    public GameOverMessage(final TextureRegion region) {
        super(region);
        setHeight(0.06f);
    }

    @Override
    public void resize(final Rect worldBounds) {
        setHeightProportion(getHeight());
        position.set(worldBounds.position);
    }

}
