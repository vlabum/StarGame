package ru.vlabum.android.games.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.vlabum.android.games.base.Base2DScreen;
import ru.vlabum.android.games.math.Rect;
import ru.vlabum.android.games.sprite.Background;
import ru.vlabum.android.games.sprite.ButtonExit;
import ru.vlabum.android.games.sprite.ButtonPlay;
import ru.vlabum.android.games.sprite.StarSprite;

public class MenuScreen extends Base2DScreen {

    private static final int STAR_COUNT = 64;

    private Texture txBackground;

    private TextureAtlas txAtlasMenu;

    private Background sprBackground;

    private StarSprite[] sprStars;

    private ButtonPlay sprPlayButton;

    private ButtonExit sprExitButton;

    public MenuScreen(Game game) {
        super(game);
    }

    @Override
    public void show() {
        super.show();
        txBackground = new Texture("textures/bg.png");
        txAtlasMenu = new TextureAtlas("textures/menuAtlas.tpack");
        sprBackground = new Background(new TextureRegion(txBackground));
        sprPlayButton = new ButtonPlay(txAtlasMenu, game);
        sprExitButton = new ButtonExit(txAtlasMenu);
        sprStars = new StarSprite[STAR_COUNT];
        for (int i = 0; i < sprStars.length; i++) {
            sprStars[i] = new StarSprite(txAtlasMenu);
        }
    }

    @Override
    public void render(final float delta) {
        super.render(delta);
        update(delta);
        draw();
    }

    public void update(final float delta) {
        for (int i = 0; i < sprStars.length; i++) {
            sprStars[i].update(delta);
        }
    }

    public void draw() {
        Gdx.gl.glClearColor(1, 0.3f, 0.6f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        sprBackground.draw(batch);
        for (int i = 0; i < sprStars.length; i++) {
            sprStars[i].draw(batch);
        }
        sprPlayButton.draw(batch);
        sprExitButton.draw(batch);
        batch.end();
    }

    @Override
    public void resize(final Rect worldBounds) {
        super.resize(worldBounds);
        sprBackground.resize(worldBounds);
        for (int i = 0; i < sprStars.length; i++) {
            sprStars[i].resize(worldBounds);
        }
        sprPlayButton.resize(worldBounds);
        sprExitButton.resize(worldBounds);
    }

    @Override
    public void dispose() {
        txAtlasMenu.dispose();
        txBackground.dispose();
        super.dispose();
    }

    @Override
    public boolean touchDown(final Vector2 touch, final int pointer) {
        sprPlayButton.touchDown(touch, pointer);
        sprExitButton.touchDown(touch, pointer);
        return false;
    }

    @Override
    public boolean touchUp(final Vector2 touch, final int pointer) {
        sprPlayButton.touchUp(touch, pointer);
        sprExitButton.touchUp(touch, pointer);
        return false;
    }

}
