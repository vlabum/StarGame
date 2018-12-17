package ru.vlabum.android.games.sprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.vlabum.android.games.base.Ship;
import ru.vlabum.android.games.math.Rect;
import ru.vlabum.android.games.pool.BulletPool;
import ru.vlabum.android.games.pool.ExplosionPool;

public class MainShip extends Ship {

    private static final int INVALID_POINTER = -1;  // когда нет номера пальца

    private static final int CODE_LEFT1 = Input.Keys.LEFT;
    private static final int CODE_LEFT2 = Input.Keys.A;
    private static final int CODE_RIGHT1 = Input.Keys.RIGHT;
    private static final int CODE_RIGHT2 = Input.Keys.D;
    private static final int CODE_SHOOT = Input.Keys.SPACE;

    private static final int SCORE1 = 30;
    private static final int SCORE2 = 60;
    private static final int SCORE3 = 100;

    private boolean pressedLeft = false;        // нажата левая сторона
    private boolean pressedRight = false;       // нажата правая сторона
    private int leftPointer = INVALID_POINTER;  // номер пальца на левой стороне
    private int rightPointer = INVALID_POINTER; // номер пальца на правой стороне

    private BulletPool bulletPool;
    private final Vector2 v0 = new Vector2(0.4f, 0);
    private final Vector2 v = new Vector2();

    public MainShip(
            final TextureAtlas atlas,
            final BulletPool bulletPool,
            final ExplosionPool explosionPool
    ) {
        super(atlas.findRegion("main_ship"), 1, 2, 2, null);
        setHeightProportion(0.15f);
        this.shootSound = Gdx.audio.newSound(Gdx.files.internal("sounds/leszek_szary_shoot1.wav"));
        this.bulletPool = bulletPool;
        this.reloadInterval = 0.2f;
        this.bulletRegion = atlas.findRegion("bulletMainShip");
        this.bulletHeight = 0.01f;
        this.bulletV.set(0, 0.5f);
        this.bulletDamage = 1;
        this.bulletRegion = atlas.findRegion("bulletMainShip");
        this.explosionPool = explosionPool;
        this.hp = 100;
    }

    @Override
    public void update(final float delta) {
        super.update(delta);
        position.mulAdd(v, delta);
        reloadTimer += delta;
        if (reloadTimer >= reloadInterval) {
            reloadTimer = 0f;
            shoot();
        }
        if (getRight() > worldBounds.getRight()) {
            setRight(worldBounds.getRight());
            stop();
        }
        if (getLeft() < worldBounds.getLeft()) {
            setLeft(worldBounds.getLeft());
            stop();
        }
    }

    @Override
    public void resize(final Rect worldBounds) {
        super.resize(worldBounds);
        setBottom(worldBounds.getBottom() + 0.05f);
    }

    public boolean keyDown(final int keyCode) {
        switch (keyCode) {
            case CODE_LEFT1:
            case CODE_LEFT2:
                pressedLeft = true;
                moveLeft();
                break;
            case CODE_RIGHT1:
            case CODE_RIGHT2:
                pressedRight = true;
                moveRight();
                break;
            case CODE_SHOOT:
                shoot();
                break;
        }
        return false;
    }

    public boolean keyUp(final int keyCode) {
        switch (keyCode) {
            case CODE_LEFT1:
            case CODE_LEFT2:
                pressedLeft = false;
                if (pressedRight) moveRight();
                else stop();
                break;
            case CODE_RIGHT1:
            case CODE_RIGHT2:
                pressedRight = false;
                if (pressedLeft) moveLeft();
                else stop();
                break;
        }
        return false;
    }

    @Override
    public boolean touchDown(final Vector2 touch, final int pointer) {
        if (touch.x < worldBounds.position.x) {
            if (leftPointer != INVALID_POINTER) return false;
            leftPointer = pointer;
            moveLeft();
        } else {
            if (rightPointer != INVALID_POINTER) return false;
            rightPointer = pointer;
            moveRight();
        }
        return false;
    }

    @Override
    public boolean touchUp(final Vector2 touch, final int pointer) {
        if (pointer == leftPointer) {
            leftPointer = INVALID_POINTER;
            if (rightPointer != INVALID_POINTER) moveRight();
            else stop();
        } else if (pointer == rightPointer) {
            rightPointer = INVALID_POINTER;
            if (leftPointer != INVALID_POINTER) moveLeft();
            else stop();
        }
        return false;
    }

    private void moveRight() {
        v.set(v0);
    }

    private void moveLeft() {
        v.set(v0).rotate(180);
    }

    private void stop() {
        v.setZero();
    }

    @Override
    public void newGame() {
        super.newGame();
        hp = 100;
        stop();
    }

    @Override
    public void shoot() {
        if (score < SCORE1 || score >= SCORE2) {
            final Bullet bulletC = bulletPool.obtain();
            bulletC.set(
                    this, bulletRegion, position, new Vector2(0, 0.5f), 0.01f,
                    worldBounds, 1, 0, halfHeight);
        }
        if (score >= SCORE1) {
            final Bullet bulletR = bulletPool.obtain();
            final Bullet bulletL = bulletPool.obtain();
            bulletR.set(this, bulletRegion, position, new Vector2(0, 0.5f), 0.01f,
                    worldBounds, 1, -halfWidth / 1.3f, halfHeight / 2);
            bulletL.set(this, bulletRegion, position, new Vector2(0, 0.5f), 0.01f,
                    worldBounds, 1, halfWidth / 1.3f, halfHeight / 2);
            shootSound.play(1.0f);
        }
        if (score >= SCORE3) {
            final Bullet bulletR = bulletPool.obtain();
            final Bullet bulletL = bulletPool.obtain();
            bulletR.set(this, bulletRegion, position, new Vector2(0, 0.5f), 0.01f,
                    worldBounds, 1, -halfWidth / 1.3f / 2f , halfHeight / 4);
            bulletL.set(this, bulletRegion, position, new Vector2(0, 0.5f), 0.01f,
                    worldBounds, 1, halfWidth / 1.3f / 2f, halfHeight / 4);
            shootSound.play(0.7f);
        }
        shootSound.play(0.7f);
    }

    public boolean isBulletCollision(Rect bullet) {
        return !(bullet.getRight() < getLeft()
                || bullet.getLeft() > getRight()
                || bullet.getBottom() > position.y
                || bullet.getTop() < getBottom());
    }

    public void addScore(int score) {
        this.score += score;
        if (this.score % 100 == 0) hp = 100; // бонус
    }

    public void dispose() {
        shootSound.dispose();
        super.dispose();
    }

}
