package ru.vlabum.android.games.sprite;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import ru.vlabum.android.games.base.Sprite;
import ru.vlabum.android.games.math.Rect;

public class BackgroundSprite extends Sprite {

    public BackgroundSprite(final TextureRegion region) {
        super(region);
    }

    @Override
    public void resize(final Rect worldBounds) {
        setHeightProportion(worldBounds.getHeight());
        position.set(worldBounds.position);
    }

}
