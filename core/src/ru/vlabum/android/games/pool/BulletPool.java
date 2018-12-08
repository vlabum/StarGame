package ru.vlabum.android.games.pool;

import ru.vlabum.android.games.base.SpritesPool;
import ru.vlabum.android.games.sprite.Bullet;

public class BulletPool extends SpritesPool<Bullet> {
    @Override
    protected Bullet newObject() {
        return new Bullet();
    }
}
