package ru.vlabum.android.games.base;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public abstract class SpriteButton extends Sprite {

    private static final float PRESS_SCALE_KOEF = 0.9f;

    private float scaleOrig;

    private int pointer;

    private boolean pressed;

    public SpriteButton(final TextureRegion region) {
        super(region);
    }

    @Override
    public boolean touchDown(final Vector2 touch, final int pointer) {
        if (pressed || !isMe(touch)) return false;
        pressed = true;
        this.pointer = pointer;
        scaleOrig = scale;
        scale *= PRESS_SCALE_KOEF;
        return false;
    }

    @Override
    public boolean touchUp(final Vector2 touch, final int pointer) {
        if (!pressed || this.pointer != pointer) return false;
        scale = scaleOrig;
        pressed = false;
        if (isMe(touch)) actionPerformed();
        return false;
    }

    protected abstract void actionPerformed();

}
