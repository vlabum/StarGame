package ru.vlabum.android.games.sprite;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.vlabum.android.games.base.Sprite;

public class Explosion extends Sprite {

    private float animateInterval = 0.017f;
    private float animateTimer;
    private Sound explosionSound;
    private final Vector2 speed = new Vector2();

    public Explosion(
            final TextureRegion region,
            final int rows,
            final int cols,
            final int frames,
            final Sound explosionSound) {
        super(region, rows, cols, frames);
        this.explosionSound = explosionSound;
    }

    public void set(
            final float height,
            final Vector2 pos,
            final Vector2 v0
    ) {
        this.position.set(pos);
        setHeightProportion(height);
        this.speed.set(v0);
        explosionSound.play();
    }

    @Override
    public void update(final float delta) {
        animateTimer += delta;
        position.mulAdd(speed, delta);
        if (animateTimer >= animateInterval) {
            animateTimer = 0f;
            if (++currentFrame == regions.length) {
                setDestroyed(true);
                currentFrame = 0;
            }
        }
    }

}
