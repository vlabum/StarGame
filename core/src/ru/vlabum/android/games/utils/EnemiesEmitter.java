package ru.vlabum.android.games.utils;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.vlabum.android.games.math.Rect;
import ru.vlabum.android.games.math.Rnd;
import ru.vlabum.android.games.pool.EnemyShipPool;
import ru.vlabum.android.games.sprite.EnemyShip;

public class EnemiesEmitter {

    private static int SMALL = 0;
    private static int MIDDLE = 1;
    private static int LARGE = 2;

    private static final float[] ENEMY_HEIGHT           = new float[]   {  0.1f,   0.1f,  0.1f };
    private static final float[] ENEMY_BULLET_HEIGHT    = new float[]   { 0.01f, 0.015f,  0.2f };
    private static final float[] ENEMY_BULLET_VY        = new float[]   { -0.3f,  -0.3f, -0.3f };
    private static final int[]   ENEMY_BULLET_DAMAGE    = new int[]     {     1,      2,     3 };
    private static final float[] ENEMY_RELOAD_INTERVAL  = new float[]   {    1f,     1f,    1f };
    private static final int[]   ENEMY_HP               = new int[]     {     1,      5,    10 };

    private Rect worldBounds;

    private float generateInterval = 4f;
    private float generateTimer;

    private TextureRegion[][] enemyRegion = new TextureRegion[3][];

    private final Vector2[] enemyV = new Vector2[] {
            new Vector2(0f, -0.2f),
            new Vector2(0f, -0.15f),
            new Vector2(0f, -0.10f)
    };

    private TextureRegion bulletRegion;

    private EnemyShipPool enemyPool;

    public EnemiesEmitter(final Rect worldBounds, final EnemyShipPool enemyPool, final TextureAtlas atlas) {
        this.worldBounds = worldBounds;
        this.enemyPool = enemyPool;
        this.enemyRegion[SMALL] = Regions.split(atlas.findRegion("enemy0"), 1, 2, 2);
        this.enemyRegion[MIDDLE] = Regions.split(atlas.findRegion("enemy1"), 1, 2, 2);
        this.enemyRegion[LARGE] = Regions.split(atlas.findRegion("enemy2"), 1, 2, 2);
        this.bulletRegion = atlas.findRegion("bulletEnemy");
    }

    public void generate(final float delta) {
        generateTimer += delta;
        final int num = Rnd.nextInt(LARGE + 1);
        generateShip(num);

    }

    private void generateShip(final int num) {
        if (generateTimer >= generateInterval) {
            generateTimer = 0f;
            EnemyShip enemy = enemyPool.obtain();
            enemy.set(
                    enemyRegion[num],
                    enemyV[num],
                    bulletRegion,
                    ENEMY_BULLET_HEIGHT[num],
                    ENEMY_BULLET_VY[num],
                    ENEMY_BULLET_DAMAGE[num],
                    ENEMY_RELOAD_INTERVAL[num],
                    ENEMY_HEIGHT[num],
                    ENEMY_HP[num]
            );
            enemy.position.x = Rnd.nextFloat(worldBounds.getLeft() + enemy.getHalfWidth(), worldBounds.getRight() - enemy.getHalfWidth());
            enemy.setBottom(worldBounds.getTop());
        }
    }
}