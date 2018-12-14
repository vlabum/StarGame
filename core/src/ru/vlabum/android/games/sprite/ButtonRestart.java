package ru.vlabum.android.games.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.vlabum.android.games.base.SpriteButton;
import ru.vlabum.android.games.math.Rect;

public class ButtonRestart extends SpriteButton {

    Runnable method;

    public ButtonRestart(final TextureAtlas atlas, final Runnable method) {
        super(atlas.findRegion("button_new_game"));
        setHeightProportion(0.1f);
        this.method = method;
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
        method.run();
    }

}
