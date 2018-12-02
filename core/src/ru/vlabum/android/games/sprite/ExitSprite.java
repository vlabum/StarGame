package ru.vlabum.android.games.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.vlabum.android.games.base.Base2DScreen;
import ru.vlabum.android.games.base.Sprite;
import ru.vlabum.android.games.math.Rect;

public class ExitSprite extends Sprite {

    private Rect worldBounds;   // границы проекции мировых координат

    protected float scaleOrig = super.scale;

    public ExitSprite(final TextureAtlas atlas) {
        super(atlas.findRegion("btExit"));
        setHeightProportion(0.1f);
    }

    @Override
    public void resize(final Base2DScreen screen) {
        super.resize(worldBounds);
        this.worldBounds = screen.getWorldBounds();
        final float posX = worldBounds.getRight() - halfWidth - halfWidth / 2;
        final float posY = worldBounds.getBottom() + halfHeight + halfHeight / 2;
        position.set(posX, posY);
    }

    @Override
    public void update(final float delta) {
        super.update(delta);
    }

    @Override
    public boolean touchDown(final Vector2 touch, final int poiter) {
        if (isMe(touch)) scale *= 0.9;
        return false;
    }

    @Override
    public boolean touchUp(final Vector2 touch, final int poiter) {
        scale = scaleOrig;
        return isMe(touch);
    }

    @Override
    public boolean isMe(final Vector2 touch) {
        return super.isMe(touch);
    }

}
