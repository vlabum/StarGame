package ru.vlabum.android.games.base;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.vlabum.android.games.math.Rect;

public class Sprite extends Rect {

    protected float angle;
    protected float scale = 1f;
    protected final TextureRegion[] regions;
    protected int currentFrame;

    public Sprite(final TextureRegion region) {
        regions = new TextureRegion[1];
        regions[0] = region;
    }

    public void draw(final SpriteBatch batch) {
        batch.draw(
                regions[currentFrame],      // текущий регион
                getLeft(), getBottom(),     // точка отрисовки
                halfWidth, halfHeight,      // точка вращения
                getWidth(), getHeight(),    // ширина и высота
                scale, scale,               // масштаб по оси x и по оси y
                angle                       // угол вращения
        );
    }

    public void setHeightProportion(final float height) {
        setHeight(height);
        float aspect = regions[currentFrame].getRegionWidth() / (float) regions[currentFrame].getRegionHeight();
        setWidth(height * aspect);
    }

    public void resize(final Rect worldBounds) { }

    public void resize(final Base2DScreen screen) { }

    public void update(final float delta) { }

    public boolean touchDown(final Vector2 touch, final int pointer) {
        return false;
    }

    public boolean touchUp(final Vector2 touch, final int pointer) {
        return false;
    }

    public float getAngle() {
        return angle;
    }

    public void setAngle(final float angle) {
        this.angle = angle;
    }

    public float getScale() {
        return scale;
    }

    public void setScale(final float scale) {
        this.scale = scale;
    }

}
