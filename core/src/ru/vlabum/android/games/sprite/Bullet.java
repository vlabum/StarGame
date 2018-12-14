package ru.vlabum.android.games.sprite;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.vlabum.android.games.base.Sprite;
import ru.vlabum.android.games.math.Rect;

public class Bullet extends Sprite {

    private Vector2 speed = new Vector2();
    private Rect worldBounds;
    private int damage;
    private Object owner;

    public Bullet() {
        regions = new TextureRegion[1];
    }

    public Bullet(final TextureRegion region) {
        super(region);
    }

    public void set(
            final Object owner,
            final TextureRegion region,
            final Vector2 position0,
            final Vector2 speed0,
            final float height,
            final Rect worldBounds,
            final int damage,
            final float positionShiftX,
            final float positionShiftY
    ) {
        this.owner = owner;
        this.regions[0] = region;
        this.position.set(position0.x + positionShiftX, position0.y + positionShiftY);
        this.speed.set(speed0);
        setHeightProportion(height);
        this.worldBounds = worldBounds;
        this.damage = damage;
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        position.mulAdd(speed, delta);
        if (isOutside(worldBounds)) setDestroyed(true);
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public Object getOwner() {
        return owner;
    }

    public void setOwner(Object owner) {
        this.owner = owner;
    }

}
