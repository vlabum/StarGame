package ru.vlabum.android.games.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.vlabum.android.games.base.Base2DScreen;
import ru.vlabum.android.games.math.Rect;
import ru.vlabum.android.games.pool.BulletPool;
import ru.vlabum.android.games.pool.EnemyShipPool;
import ru.vlabum.android.games.sprite.Background;
import ru.vlabum.android.games.sprite.Bullet;
import ru.vlabum.android.games.sprite.EnemyShip;
import ru.vlabum.android.games.sprite.MainShip;
import ru.vlabum.android.games.sprite.StarSprite;
import ru.vlabum.android.games.utils.EnemiesEmitter;

public class GameScreen extends Base2DScreen {

    private static final int STAR_COUNT = 32;

    private Texture txBackground;
    private TextureAtlas txAtlas;
    private Background background;
    private StarSprite[] stars;
    private MainShip mainShip;
    private BulletPool bulletPool;
    private EnemyShipPool enemyShipPool;
    private EnemiesEmitter enemiesEmitter;
    float animateTimer = 0f;
    float animateInterval = 2f;
    private Music music = Gdx.audio.newMusic(Gdx.files.internal("sounds/x3nus__space-syndrome.ogg"));

    public GameScreen(Game game) {
        super(game);
    }

    @Override
    public void show() {
        super.show();
        txAtlas = new TextureAtlas("textures/mainAtlas.tpack");
        txBackground = new Texture("textures/bg.png");
        background = new Background(new TextureRegion(txBackground));
        stars = new StarSprite[STAR_COUNT];
        for (int i = 0; i < stars.length; i++) {
            stars[i] = new StarSprite(txAtlas);
        }
        bulletPool = new BulletPool();
        mainShip = new MainShip(txAtlas, bulletPool);
        enemyShipPool = new EnemyShipPool(txAtlas, bulletPool, mainShip, worldBounds);
        enemiesEmitter = new EnemiesEmitter(worldBounds, enemyShipPool, txAtlas);
        music.setLooping(true);
        music.play();
    }

    @Override
    public void render(final float delta) {
        update(delta);
        checkCollisions();
        deleteAllDestroyed();
        draw();
    }

    private void update(final float delta) {
        for (StarSprite star : stars) {
            star.update(delta);
        }
        mainShip.update(delta);
        bulletPool.updateActiveSprites(delta);
        animateTimer += delta;
        if (animateTimer >= animateInterval) {
            animateTimer = 0f; // действие
            enemyStart();
        }
        enemyShipPool.updateActiveSprites(delta);
        enemiesEmitter.generate(delta);
    }

    private void checkCollisions() {
        boolean mainShipCollision = false;
        boolean enemyShipCollisionBullet = false;
        for (EnemyShip enemySheep : enemyShipPool.getActiveObjects()) {

            for (Bullet bullet : bulletPool.getActiveObjects()) {
                if (enemySheep.isCollision(bullet)) enemyShipCollisionBullet = true;
                else enemyShipCollisionBullet = false;
            }

            if (mainShip.isCollision(enemySheep)) mainShipCollision = true;

            if (enemyShipCollisionBullet) enemySheep.setCurrentFrame(1);
            else enemySheep.setCurrentFrame(0);

        }
        if (mainShipCollision) mainShip.setCurrentFrame(1);
        else mainShip.setCurrentFrame(0);
    }

    private void deleteAllDestroyed() {
        bulletPool.freeAllDestroyedActiveSprites();
        enemyShipPool.freeAllDestroyedActiveSprites();
    }

    private void draw() {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        background.draw(batch);
        for (StarSprite star : stars) {
            star.draw(batch);
        }
        mainShip.draw(batch);
        bulletPool.drawActiveSprites(batch);
        enemyShipPool.drawActiveSprites(batch);
        batch.end();
    }

    @Override
    public void resize(final Rect worldBounds) {
        super.resize(worldBounds);
        this.worldBounds = worldBounds;
        background.resize(worldBounds);
        for (StarSprite star : stars) {
            star.resize(worldBounds);
        }
        mainShip.resize(worldBounds);
    }

    @Override
    public void dispose() {
        txBackground.dispose();
        txAtlas.dispose();
        bulletPool.dispose();
        enemyShipPool.dispose();
        mainShip.dispose();
        music.dispose();
        super.dispose();
    }

    @Override
    public boolean touchDown(final Vector2 touch, final int pointer) {
        mainShip.touchDown(touch, pointer);
        return super.touchDown(touch, pointer);
    }

    @Override
    public boolean touchUp(final Vector2 touch, final int pointer) {
        mainShip.touchUp(touch, pointer);
        return super.touchUp(touch, pointer);
    }

    @Override
    public boolean keyDown(final int keycode) {
        mainShip.keyDown(keycode);
        return super.keyDown(keycode);
    }

    @Override
    public boolean keyUp(final int keycode) {
        mainShip.keyUp(keycode);
        return super.keyUp(keycode);
    }

    public void enemyStart() {
        if (worldBounds == null) return;
//        EnemyShip enemyShip = enemyShipPool.obtain();
//        enemyShip.set(worldBounds, 0.15f);
    }

}
