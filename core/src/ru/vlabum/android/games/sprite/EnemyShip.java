package ru.vlabum.android.games.sprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.vlabum.android.games.base.Ship;
import ru.vlabum.android.games.math.Rect;
import ru.vlabum.android.games.pool.BulletPool;
import ru.vlabum.android.games.pool.ExplosionPool;

public class EnemyShip extends Ship {

    private MainShip mainShip;
    private Vector2 v0 = new Vector2();
    private Sound sound = Gdx.audio.newSound(Gdx.files.internal("sounds/leszek_szary_shoot1.wav"));
    private boolean isFarAway = true;

    public EnemyShip(
            final TextureAtlas atlas,
            final ExplosionPool explosionPool,
            final BulletPool bulletPool,
            final MainShip mainShip,
            final Rect worldBounds) {
        this.bulletPool = bulletPool;
        this.bulletRegion = atlas.findRegion("bulletEnemy");
        this.mainShip = mainShip;
        this.worldBounds = worldBounds;
        this.v.set(v0);
        this.explosionPool = explosionPool;
    }

    @Override
    public void update(final float delta) {
        super.update(delta);
        position.mulAdd(v, delta);
        if (getTop() <= worldBounds.getBottom()) {
            this.setDestroyed(true);
        }
        reloadTimer += delta;
        if (getTop() > worldBounds.getTop()) reloadTimer = 0f;
        if (reloadTimer >= reloadInterval || isFarAway && getTop() < worldBounds.getTop()) {
            reloadTimer = 0f;
            isFarAway = false;
            shoot();
        }
    }
    public void set(
            TextureRegion[] regions,
            Vector2 v0,
            TextureRegion bulletRegion,
            float bulletHeight,
            float bulletVY,
            int bulletDamage,
            float reloadInterval,
            float height,
            int hp,
            int score
    ) {
        this.regions = regions;
        this.v0.set(v0);
        this.bulletRegion = bulletRegion;
        this.bulletHeight = bulletHeight;
        this.bulletV.set(0f, bulletVY);
        this.bulletDamage = bulletDamage;
        this.reloadInterval = reloadInterval;
        this.hp = hp;
        this.score = score;
        setHeightProportion(height);
        reloadTimer = reloadInterval;
        v.set(v0);
        isFarAway = true;
    }

    public void shoot() {
        final Bullet bulletC = bulletPool.obtain();
        bulletC.set(
                this,
                bulletRegion,
                position,
                bulletV,
                bulletHeight,
                worldBounds,
                bulletDamage,
                0,
                -halfHeight
        );
        sound.play(1.0f);
    }

    public boolean isBulletCollision(Rect bullet) {
        return !(bullet.getRight() < getLeft()
                || bullet.getLeft() > getRight()
                || bullet.getBottom() > getTop()
                || bullet.getTop() < position.y);
    }

    @Override
    public void boom() {
        super.boom();
        mainShip.addScore(score);
    }

    public void dispose() {
        sound.dispose();
        super.dispose();
    }

}
