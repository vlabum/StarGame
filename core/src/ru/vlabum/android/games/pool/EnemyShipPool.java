package ru.vlabum.android.games.pool;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.vlabum.android.games.sprite.EnemyShip;
import ru.vlabum.android.games.base.SpritesPool;

public class EnemyShipPool extends SpritesPool<EnemyShip> {

    final TextureAtlas atlas;

    final BulletPool bulletPool;

    public EnemyShipPool(final TextureAtlas atlas, final BulletPool bulletPool) {
        this.atlas = atlas;
        this.bulletPool = bulletPool;
    }

    @Override
    protected EnemyShip newObject() {
        return new EnemyShip(atlas, bulletPool);
    }

}
