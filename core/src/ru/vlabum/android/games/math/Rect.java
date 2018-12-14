package ru.vlabum.android.games.math;

import com.badlogic.gdx.math.Vector2;

public class Rect {

    public final Vector2 position = new Vector2(); // позиция по центру

    protected float halfWidth; // половина ширины

    protected float halfHeight; // половина высоты

    public Rect() { }

    public Rect(final Rect from) {
        this(from.position.x, from.position.y, from.getHalfWidth(), from.getHalfHeight());
    }

    public Rect(final float x, final float y, final float halfWidth, final float halfHeight) {
        position.set(x, y);
        this.halfWidth = halfWidth;
        this.halfHeight = halfHeight;
    }

    public float getLeft() {
        return position.x - halfWidth;
    }

    public float getTop() {
        return position.y + halfHeight;
    }

    public float getRight() {
        return position.x + halfWidth;
    }

    public float getBottom() {
        return position.y - halfHeight;
    }

    public float getHalfWidth() {
        return halfWidth;
    }

    public float getHalfHeight() {
        return halfHeight;
    }

    public float getWidth() {
        return halfWidth * 2f;
    }

    public float getHeight() {
        return halfHeight * 2f;
    }

    public void set(final Rect from) {
        position.set(from.position);
        halfWidth = from.halfWidth;
        halfHeight = from.halfHeight;
    }

    public void setLeft(final float left) {
        position.x = left + halfWidth;
    }

    public void setTop(final float top) {
        position.y = top - halfHeight;
    }

    public void setRight(final float right) {
        position.x = right - halfWidth;
    }

    public void setBottom(final float bottom) {
        position.y = bottom + halfHeight;
    }

    public void setWidth(final float width) {
        this.halfWidth = width / 2f;
    }

    public void setHeight(final float height) {
        this.halfHeight = height / 2f;
    }

    public void setSize(final float width, final float height) {
        this.halfWidth = width / 2f;
        this.halfHeight = height / 2f;
    }

    public boolean isMe(final Vector2 touch) {
        return touch.x >= getLeft() && touch.x <= getRight() && touch.y >= getBottom() && touch.y <= getTop();
    }

    public boolean isOutside(final Rect other) {
        return getLeft() > other.getRight() || getRight() < other.getLeft() || getBottom() > other.getTop() || getTop() < other.getBottom();
    }

    public boolean isCollision(final Rect other) {
        return !(other.getRight() < getLeft()
                || other.getLeft() > getRight()
                || other.getBottom() > getTop()
                || other.getTop() < getBottom());
    }

    @Override
    public String toString() {
        return "Rectangle: position" + position + " size(" + getWidth() + ", " + getHeight() + ")";
    }

}
