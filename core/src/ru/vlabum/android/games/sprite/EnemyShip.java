package ru.vlabum.android.games.sprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.vlabum.android.games.base.Sprite;
import ru.vlabum.android.games.math.Rect;
import ru.vlabum.android.games.pool.BulletPool;

public class EnemyShip extends Sprite {

    private TextureAtlas atlas;

    private  BulletPool bulletPool;

    private Rect worldBounds;

    private final Vector2 speed = new Vector2(0.0f, -0.2f);

    private Sound sound = Gdx.audio.newSound(Gdx.files.internal("sounds/leszek_szary_shoot1.wav"));

    public EnemyShip(final TextureAtlas atlas, final BulletPool bulletPool) {
        super(atlas.findRegion("enemy0"),1,2,2);
        setHeightProportion(0.15f);
        this.bulletPool = bulletPool;
        this.atlas = atlas;
    }

    public void set(
            final Rect worldBounds,
            float height
    ) {
        this.worldBounds = worldBounds;
        this.position.set(0f, this.worldBounds.getTop());
        setHeightProportion(height);
    }

    @Override
    public void update(final float delta) {
        super.update(delta);
        position.mulAdd(speed, delta);
        if (isOutside(worldBounds)) setDestroyed(true);
    }

    @Override
    public void resize(final Rect worldBounds) {
        this.worldBounds = worldBounds;
        setTop(this.worldBounds.getTop());
    }

    public void shoot() {
        final Bullet bulletC = bulletPool.obtain();
        bulletC.set(
                this,
                atlas.findRegion("bulletEnemy"),
                position,
                new Vector2(0, -0.5f),
                0.01f,
                worldBounds,
                1,
                0,
                -halfHeight
        );
        sound.play(1.0f);

    }

}
