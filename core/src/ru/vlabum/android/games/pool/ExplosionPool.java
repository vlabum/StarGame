package ru.vlabum.android.games.pool;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import ru.vlabum.android.games.base.SpritesPool;
import ru.vlabum.android.games.sprite.Explosion;

public class ExplosionPool extends SpritesPool<Explosion> {

    private TextureRegion explosionRegion;
    private Sound explosionSound;

    public ExplosionPool(final TextureAtlas atlas, final Sound explosionSound) {
        explosionRegion = atlas.findRegion("explosion");
        this.explosionSound = explosionSound;
    }

    @Override
    protected Explosion newObject() {
        return new Explosion(explosionRegion, 9, 9, 74, explosionSound);
    }
}
