package ru.vlabum.android.games.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import ru.vlabum.android.games.base.Base2DScreen;
import ru.vlabum.android.games.base.Font;
import ru.vlabum.android.games.math.Rect;
import ru.vlabum.android.games.pool.BulletPool;
import ru.vlabum.android.games.pool.EnemyShipPool;
import ru.vlabum.android.games.pool.ExplosionPool;
import ru.vlabum.android.games.sprite.Background;
import ru.vlabum.android.games.sprite.Bullet;
import ru.vlabum.android.games.sprite.ButtonRestart;
import ru.vlabum.android.games.sprite.EnemyShip;
import ru.vlabum.android.games.sprite.GameOverMessage;
import ru.vlabum.android.games.sprite.MainShip;
import ru.vlabum.android.games.sprite.StarSprite;
import ru.vlabum.android.games.utils.EnemiesEmitter;

public class GameScreen extends Base2DScreen {

    private static final int STAR_COUNT = 32;
    private static final float FONT_SIZE = 0.02f;

    private static final String FRAGS = "Frags: ";
    private static final String HP = "HP: ";
    private static final String LEVEL = "Level: ";
    private static final String SCORE = "Score: ";

    private Texture txBackground;
    private TextureRegion txGameOverMessage;
    private GameOverMessage gameOverMessage;
    private TextureAtlas txAtlas;
    private Background background;
    private StarSprite[] stars;
    private MainShip mainShip;
    private BulletPool bulletPool;
    private EnemyShipPool enemyShipPool;
    private EnemiesEmitter enemiesEmitter;
    private Music music = Gdx.audio.newMusic(Gdx.files.internal("sounds/x3nus__space-syndrome.ogg"));
    private ExplosionPool explosionPool;
    private Sound explosionSound;
    private boolean isGameOver = false;
    private ButtonRestart buttonRestart;
    private Font font;

    private int frags;

    private StringBuilder sbFrags = new StringBuilder();
    private StringBuilder sbHP = new StringBuilder();
    private StringBuilder sbLevel = new StringBuilder();
    private StringBuilder sbScore = new StringBuilder();


    public GameScreen(@NotNull Game game) {
        super(game);
    }

    @Override
    public void show() {
        super.show();
        txAtlas = new TextureAtlas("textures/mainAtlas.tpack");
        txBackground = new Texture("textures/bg.png");
        background = new Background(new TextureRegion(txBackground));
        txGameOverMessage = txAtlas.findRegion("message_game_over");
        gameOverMessage = new GameOverMessage(txGameOverMessage);
        stars = new StarSprite[STAR_COUNT];
        for (int i = 0; i < stars.length; i++) {
            stars[i] = new StarSprite(txAtlas);
        }
        bulletPool = new BulletPool();
        explosionSound = Gdx.audio.newSound(Gdx.files.internal("sounds/435413__v-ktor__explosion12.wav"));
        explosionPool = new ExplosionPool(txAtlas, explosionSound);
        mainShip = new MainShip(txAtlas, bulletPool, explosionPool);
        enemyShipPool = new EnemyShipPool(txAtlas, explosionPool, bulletPool, mainShip, worldBounds);
        enemiesEmitter = new EnemiesEmitter(worldBounds, enemyShipPool, txAtlas);
        buttonRestart = new ButtonRestart(txAtlas, this);
        font = new Font("font/font.fnt", "font/font.png");
        font.setFontSize(FONT_SIZE);
        music.setLooping(true);
        music.setVolume(1f);
        music.play();
    }

    @Override
    public void render(final float delta) {
        update(delta);
        if (!isGameOver) checkCollisions();
        deleteAllDestroyed();
        draw();
    }

    private void update(final float delta) {
        for (StarSprite star : stars) {
            star.update(delta);
        }
        if (!mainShip.isDestroyed()) mainShip.update(delta);
        bulletPool.updateActiveSprites(delta);
        enemyShipPool.updateActiveSprites(delta);
        explosionPool.updateActiveSprites(delta);
        if (!isGameOver) enemiesEmitter.generate(delta, frags);
    }

    private void checkCollisions() {
        final List<EnemyShip> enemyList = enemyShipPool.getActiveObjects();
        for (final EnemyShip enemy : enemyList) {
            if (enemy.isDestroyed()) continue;
            final float minDist = enemy.getHalfWidth() + mainShip.getHalfWidth();
            if (enemy.position.dst2(mainShip.position) < minDist * minDist) {
                enemy.setDestroyed(true);
                enemy.boom();
                mainShip.damage(mainShip.getHp());
                gameOver();
                return;
            }
        }

        final List<Bullet> bulletList = bulletPool.getActiveObjects();
        for (final EnemyShip enemy : enemyList) {
            if (enemy.isDestroyed()) continue;
            for (final Bullet bullet : bulletList) {
                if (bullet.getOwner() != mainShip || bullet.isDestroyed()) continue;
                if (enemy.isBulletCollision(bullet)) {
                    enemy.damage(bullet.getDamage());
                    bullet.setDestroyed(true);
                    if (enemy.isDestroyed()) frags++;
                }
            }
        }

        for (final Bullet bullet : bulletList) {
            if (bullet.isDestroyed() || bullet.getOwner() == mainShip) continue;
            if (mainShip.isBulletCollision(bullet)) {
                bullet.setDestroyed(true);
                mainShip.damage(bullet.getDamage());
            }
        }
        if (mainShip.isDestroyed()) gameOver();
    }

    private void deleteAllDestroyed() {
        bulletPool.freeAllDestroyedActiveSprites();
        enemyShipPool.freeAllDestroyedActiveSprites();
        explosionPool.freeAllDestroyedActiveSprites();
    }

    private void draw() {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        background.draw(batch);
        for (StarSprite star : stars) {
            star.draw(batch);
        }
        if (!mainShip.isDestroyed()) mainShip.draw(batch);
        bulletPool.drawActiveSprites(batch);
        enemyShipPool.drawActiveSprites(batch);
        explosionPool.drawActiveSprites(batch);
        if (isGameOver) {
            gameOverMessage.draw(batch);
            buttonRestart.draw(batch);
        }
        printInfo();
        batch.end();
    }

    public void printInfo() {
        sbFrags.setLength(0);
        sbHP.setLength(0);
        sbLevel.setLength(0);
        sbScore.setLength(0);
        font.draw(batch, sbFrags.append(FRAGS).append(frags), worldBounds.getLeft(), worldBounds.getTop());
        font.draw(batch, sbScore.append(SCORE).append(mainShip.getScore()), worldBounds.getLeft(), worldBounds.getTop() - FONT_SIZE * 1.1f);
        font.draw(batch, sbHP.append(HP).append(mainShip.getHp()), worldBounds.position.x, worldBounds.getTop(), Align.center);
        font.draw(batch, sbLevel.append(LEVEL).append(enemiesEmitter.getStage()), worldBounds.getRight(), worldBounds.getTop(), Align.right);
    }

    @Override
    public void resize(@NotNull final Rect worldBounds) {
        super.resize(worldBounds);
        this.worldBounds = worldBounds;
        background.resize(worldBounds);
        for (StarSprite star : stars) {
            star.resize(worldBounds);
        }
        mainShip.resize(worldBounds);
        gameOverMessage.resize(worldBounds);
        buttonRestart.resize(worldBounds);
    }

    private void gameOver() {
        isGameOver = true;
    }

    public void restart() {
        frags = 0;
        mainShip.newGame();
        enemiesEmitter.newGame();
        bulletPool.freeAllActiveObjects();
        enemyShipPool.freeAllActiveObjects();
        explosionPool.freeAllActiveObjects();
        isGameOver = false;
    }

    @Override
    public void dispose() {
        txBackground.dispose();
        txAtlas.dispose();
        bulletPool.dispose();
        enemyShipPool.dispose();
        mainShip.dispose();
        music.dispose();
        explosionPool.dispose();
        explosionSound.dispose();
        font.dispose();
        super.dispose();
    }

    @Override
    public boolean touchDown(@NotNull final Vector2 touch, final int pointer) {
        if (!isGameOver) mainShip.touchDown(touch, pointer);
        else buttonRestart.touchDown(touch, pointer);
        return super.touchDown(touch, pointer);
    }

    @Override
    public boolean touchUp(@NotNull final Vector2 touch, final int pointer) {
        if (!isGameOver) mainShip.touchUp(touch, pointer);
        else buttonRestart.touchUp(touch, pointer);
        return super.touchUp(touch, pointer);
    }

    @Override
    public boolean keyDown(final int keycode) {
        if (!isGameOver) mainShip.keyDown(keycode);
        return super.keyDown(keycode);
    }

    @Override
    public boolean keyUp(final int keycode) {
        if (!isGameOver) mainShip.keyUp(keycode);
        return super.keyUp(keycode);
    }

}
