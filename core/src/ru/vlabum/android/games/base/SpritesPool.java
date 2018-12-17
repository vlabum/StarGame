package ru.vlabum.android.games.base;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.List;

public abstract class SpritesPool<T extends Sprite> {

    // список активных объектов
    protected List<T> activeObjects = new ArrayList<T>();

    // список свободных объектов
    protected List<T> freeObjects = new ArrayList<T>();

    public T obtain() {
        T object;
        if (freeObjects.isEmpty()) object = newObject();
        else object = freeObjects.remove(freeObjects.size() - 1);
        activeObjects.add(object);
        return object;
    }

    protected abstract T newObject();

    public void updateActiveSprites(final float delta) {
        for (T sprite : activeObjects) {
            if (!sprite.isDestroyed()) sprite.update(delta);
        }
    }

    public void drawActiveSprites(final SpriteBatch batch) {
        for (T sprite : activeObjects) {
            if (!sprite.isDestroyed()) sprite.draw(batch);
        }
    }

    public void freeAllDestroyedActiveSprites() {
        for (int i = 0; i < activeObjects.size(); i++) {
            T sprite = activeObjects.get(i);
            if (sprite.isDestroyed()) {
                free(sprite);
                i--;
                sprite.setDestroyed(false);
            }
        }
    }

    private void free(final T object) {
        if (activeObjects.remove(object)) {
            freeObjects.add(object);
        }
    }

    public List<T> getActiveObjects() {
        return activeObjects;
    }

    public void dispose() {
        activeObjects.clear();
        freeObjects.clear();
    }

    public void freeAllActiveObjects() {
        freeObjects.addAll(activeObjects);
        activeObjects.clear();
    }

}
