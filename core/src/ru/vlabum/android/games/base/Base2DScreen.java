package ru.vlabum.android.games.base;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;

import ru.vlabum.android.games.math.MatrixUtils;
import ru.vlabum.android.games.math.Rect;

public class Base2DScreen implements Screen, InputProcessor {

    public static final float DEF_HEIGHT = 1f;

    protected Game game;
    protected final SpriteBatch batch;
    private final Rect screenBounds;  // границы области рисования в пикселях
    protected Rect worldBounds;   // границы проекции мировых координат
    private final Rect glBounds;      // дефолтные границы проекции мир - gl
    private final Matrix4 worldToGl;
    private final Matrix3 screenToWorld;
    private final Vector2 vTouch;

    public Base2DScreen(final Game game) {
        this.game = game;
        this.batch = new SpriteBatch();
        this.screenBounds = new Rect();
        this.worldBounds = new Rect();
        this.glBounds = new Rect(0,0, DEF_HEIGHT, DEF_HEIGHT);
        this.worldToGl = new Matrix4();
        this.screenToWorld = new Matrix3();
        this.vTouch = new Vector2();
    }

    @Override
    public void show() {
        batch.getProjectionMatrix().idt();
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void resize(final int width, final int height) {
        screenBounds.setSize(width, height);
        screenBounds.setLeft(0);
        screenBounds.setBottom(0);
        final float aspect = width / (float) height;
        worldBounds.setHeight(DEF_HEIGHT);
        worldBounds.setWidth(DEF_HEIGHT * aspect);
        MatrixUtils.calcTransitionMatrix(worldToGl, worldBounds, glBounds);
        batch.setProjectionMatrix(worldToGl);
        MatrixUtils.calcTransitionMatrix(screenToWorld, screenBounds, worldBounds);
        resize(worldBounds);
    }

    public void resize(final Rect worldBounds) {
        System.out.println("resize worldBounds width = " + worldBounds.getWidth() + " worldBounds height = " + worldBounds.getHeight());
    }

    public Rect getWorldBounds() {
        return worldBounds;
    }

    public Rect getScreenBounds() {
        return screenBounds;
    }

    public Rect getGlBounds() {
        return glBounds;
    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        batch.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(final int screenX, final int screenY, final int pointer, final int button) {
        vTouch.set(screenX, screenBounds.getHeight() - screenY).mul(screenToWorld);
        touchDown(vTouch, pointer);
        return false;
    }

    public boolean touchDown(final Vector2 touch, final int pointer) {
        System.out.println("touchDown touch.x = " + touch.x + " touch.y = " + touch.y);
        return false;
    }

    @Override
    public boolean touchUp(final int screenX, final int screenY, final int pointer, final int button) {
        vTouch.set(screenX, screenBounds.getHeight() - screenY).mul(screenToWorld);
        touchUp(vTouch, pointer);
        return false;
    }

    public boolean touchUp(final Vector2 touch, final int pointer) {
        System.out.println("touchUp touch.x = " + touch.x + " touch.y = " + touch.y);
        return false;
    }

    @Override
    public boolean touchDragged(final int screenX, final int screenY, final int pointer) {
        vTouch.set(screenX, screenBounds.getHeight() - screenY).mul(screenToWorld);
        touchDragged(vTouch, pointer);
        return false;
    }

    public boolean touchDragged(final Vector2 touch, final int pointer) {
        System.out.println("touchDragged touch.x = " + touch.x + " touch.y = " + touch.y);
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    @Override
    public void render(float delta) { }

    @Override
    public void pause() { }

    @Override
    public void resume() { }

}
