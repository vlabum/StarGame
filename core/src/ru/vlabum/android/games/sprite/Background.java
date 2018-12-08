package ru.vlabum.android.games.sprite;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import ru.vlabum.android.games.base.Sprite;
import ru.vlabum.android.games.math.Rect;

public class Background extends Sprite {

    public Background(final TextureRegion region) {
        super(region);
    }

    @Override
    public void resize(final Rect worldBounds) {
        setHeightProportion(worldBounds.getHeight());
        position.set(worldBounds.position);
    }

}
