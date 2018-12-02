package ru.vlabum.android.games.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import ru.vlabum.android.games.base.Sprite;
import ru.vlabum.android.games.math.Rect;
import ru.vlabum.android.games.math.Rnd;

public class StarSprite extends Sprite {

    private static final float MAX_SPEED_Y = 0.5f;
    private static final float MIN_SPEED_Y = 0.1f;

    private Rect worldBounds;

    private final Vector2 vSpeed = new Vector2();

    public StarSprite(final TextureAtlas atlas) {
        super(atlas.findRegion("star"));
        setHeightProportion(0.01f);
        final float speedY = Rnd.nextFloat(-MAX_SPEED_Y, -MIN_SPEED_Y);
        scale = scale - (scale / (Math.abs(speedY) * 15) ); // эффект далекой звезды (медленнее скорость, дальше звезда)
        vSpeed.set(Rnd.nextFloat(-0.005f, 0.005f), speedY);
    }

    @Override
    public void resize(final Rect worldBounds) {
        super.resize(worldBounds);
        this.worldBounds = worldBounds;
        final float posX = Rnd.nextFloat(worldBounds.getLeft(), worldBounds.getRight());
        final float posY = Rnd.nextFloat(worldBounds.getBottom(), worldBounds.getTop());
        position.set(posX, posY);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        position.mulAdd(vSpeed, delta);
        checkAndHandleBounds();
    }

    private void checkAndHandleBounds() {
        if (getTop() < worldBounds.getBottom()) setBottom(worldBounds.getTop());
        if (getRight() < worldBounds.getLeft()) setLeft(worldBounds.getRight());
        if (getLeft() > worldBounds.getRight()) setRight(worldBounds.getLeft());
        if (getBottom() > worldBounds.getTop()) setTop(worldBounds.getBottom());
    }

}
