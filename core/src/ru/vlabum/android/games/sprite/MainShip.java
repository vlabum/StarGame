package ru.vlabum.android.games.sprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.vlabum.android.games.base.Sprite;
import ru.vlabum.android.games.math.Rect;
import ru.vlabum.android.games.pool.BulletPool;

public class MainShip extends Sprite {

    private static final int CODE_LEFT1 = Input.Keys.LEFT;

    private static final int CODE_LEFT2 = Input.Keys.A;

    private static final int CODE_RIGHT1 = Input.Keys.RIGHT;

    private static final int CODE_RIGHT2 = Input.Keys.D;

    private final TextureAtlas atlas;

    private BulletPool bulletPool;

    private Rect worldBounds;

    private boolean pressedLeft = false;

    private boolean pressedRight = false;

    private final Vector2 v0 = new Vector2(0.4f, 0);

    private final Vector2 v = new Vector2();

    private Sound sound = Gdx.audio.newSound(Gdx.files.internal("sounds/leszek_szary_shoot1.wav"));

    public MainShip(final TextureAtlas atlas, final BulletPool bulletPool) {
        super(atlas.findRegion("main_ship"), 1, 2, 2);
        setHeightProportion(0.15f);
        this.bulletPool = bulletPool;
        this.atlas = atlas;
    }

    @Override
    public void update(final float delta) {
        super.update(delta);
        position.mulAdd(v, delta);
        if (getLeft() < -worldBounds.getHalfWidth()) stop();
        if (getRight() > worldBounds.getHalfWidth()) stop();
    }

    @Override
    public void resize(final Rect worldBounds) {
        this.worldBounds = worldBounds;
        setBottom(worldBounds.getBottom() + 0.05f);
    }

    public boolean keyDown(final int keyCode) {
        switch (keyCode) {
            case Input.Keys.A:
            case Input.Keys.LEFT:
                pressedLeft = true;
                moveLeft();
                break;
            case Input.Keys.D:
            case Input.Keys.RIGHT:
                pressedRight = true;
                moveRight();
                break;
            case Input.Keys.SPACE:
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
        if (touch.x > 0) keyDown(CODE_RIGHT1);
        if (touch.x < 0) keyDown(CODE_LEFT1);
        return super.touchDown(touch, pointer);
    }

    @Override
    public boolean touchUp(final Vector2 touch, final int pointer) {
        if (touch.x > 0) keyUp(CODE_RIGHT1);
        if (touch.x < 0) keyUp(CODE_LEFT1);
        if (v.x != 0) stop();
        return super.touchUp(touch, pointer);
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

    public void shoot() {
        final Bullet bulletR = bulletPool.obtain();
        final Bullet bulletL = bulletPool.obtain();
        final Bullet bulletC = bulletPool.obtain();
        bulletR.set(
                this,
                atlas.findRegion("bulletMainShip"),
                position,
                new Vector2(0, 0.5f),
                0.01f,
                worldBounds,
                1,
                -halfWidth / 1.3f,
                halfHeight / 2
        );
        bulletL.set(
                this,
                atlas.findRegion("bulletMainShip"),
                position,
                new Vector2(0, 0.5f),
                0.01f,
                worldBounds,
                1,
                halfWidth / 1.3f,
                halfHeight / 2
        );
        bulletC.set(
                this,
                atlas.findRegion("bulletMainShip"),
                position,
                new Vector2(0, 0.5f),
                0.01f,
                worldBounds,
                1,
                0,
                halfHeight
        );
        sound.play(1.0f);
    }

    public void dispose() {
        sound.dispose();
    }

}
