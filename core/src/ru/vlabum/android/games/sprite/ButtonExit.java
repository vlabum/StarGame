package ru.vlabum.android.games.sprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.vlabum.android.games.base.SpriteButton;
import ru.vlabum.android.games.math.Rect;

public class ButtonExit extends SpriteButton {

    public ButtonExit(final TextureAtlas atlas) {
        super(atlas.findRegion("btExit"));
        setHeightProportion(0.1f);
    }

    @Override
    public void resize(final Rect worldBounds) {
        super.resize(worldBounds);
        setBottom(worldBounds.getBottom() + halfHeight / 2);
        setRight(worldBounds.getRight() - halfWidth / 2);
    }

    @Override
    protected void actionPerformed() {
        Gdx.app.exit();
    }

}
