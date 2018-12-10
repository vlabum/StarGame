package ru.vlabum.android.games.pool;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.vlabum.android.games.math.Rect;
import ru.vlabum.android.games.sprite.EnemyShip;
import ru.vlabum.android.games.base.SpritesPool;
import ru.vlabum.android.games.sprite.MainShip;

public class EnemyShipPool extends SpritesPool<EnemyShip> {

    private BulletPool bulletPool;
    private MainShip mainShip;
    private Rect worldBounds;
    private TextureAtlas atlas;

    public EnemyShipPool(final TextureAtlas atlas, final BulletPool bulletPool, final MainShip mainShip, final Rect worldBounds) {
        this.bulletPool = bulletPool;
        this.mainShip = mainShip;
        this.worldBounds = worldBounds;
        this.atlas = atlas;
    }

    @Override
    protected EnemyShip newObject() {
        return new EnemyShip(atlas, bulletPool, mainShip, worldBounds);
    }

}
