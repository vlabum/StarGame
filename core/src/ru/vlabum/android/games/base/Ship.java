package ru.vlabum.android.games.base;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.vlabum.android.games.math.Rect;
import ru.vlabum.android.games.pool.BulletPool;
import ru.vlabum.android.games.pool.ExplosionPool;
import ru.vlabum.android.games.sprite.Bullet;
import ru.vlabum.android.games.sprite.Explosion;

public class Ship extends Sprite {

    protected Rect worldBounds;
    protected Vector2 v = new Vector2();
    protected BulletPool bulletPool;
    protected TextureRegion bulletRegion;
    protected Vector2 bulletV = new Vector2();
    protected ExplosionPool explosionPool;
    protected float bulletHeight;
    protected int bulletDamage;
    protected int hp;                           // health point

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    protected int score;
    private float damageAnimateInterval = 0.1f;
    private float damageAnimateTimer = damageAnimateInterval;

    // TODO: создать Weapon
    protected float reloadInterval;             // интервал перезарадки орудия
    protected float reloadTimer;                // таймер перезарядки орудия
    protected Sound shootSound;                 // звук выстрела

    public Ship(final TextureRegion region, final int rows, final int cols, final int frames, final Sound shootSound) {
        super(region, rows, cols, frames);
        this.shootSound = shootSound;
    }

    public Ship() { }

    @Override
    public void update(final float delta) {
        super.update(delta);
        damageAnimateTimer += delta;
        if (damageAnimateTimer > damageAnimateInterval) {
            currentFrame = 0;
            damageAnimateTimer = 0;
        }
    }

    @Override
    public void resize(final Rect worldBounds) {
        super.resize(worldBounds);
        this.worldBounds = worldBounds;
    }

    public void shoot() {
        Bullet bullet = bulletPool.obtain();
        bullet.set(this, bulletRegion, position, bulletV, bulletHeight, worldBounds, bulletDamage, 0, 0);
        if (shootSound != null) shootSound.play();
    }

    public void boom() {
        final Explosion explosion = explosionPool.obtain();
        explosion.set(getHeight(), position, v);
    }

    public void damage(int damage) {
        hp -= damage;
        if (hp <= 0) {
            setDestroyed(true);
            boom();
        }
        damageAnimateTimer = 0f;
        currentFrame = 1;
    }

    public void dispose() {
        shootSound.dispose();
    }

    public int getHp() {
        return hp;
    }

    public void newGame() {
        this.hp = 10;
        position.x = worldBounds.position.x;
        resize(worldBounds);
        setScore(0);
        setDestroyed(false);
    }
}
