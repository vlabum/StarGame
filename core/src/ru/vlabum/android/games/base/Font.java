package ru.vlabum.android.games.base;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;

public class Font extends BitmapFont {

    public Font(final String fontFile, final String imageFile) {
        super(Gdx.files.internal(fontFile), Gdx.files.internal(imageFile), false, false);
        getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
    }

    public void setFontSize(final float size) {
        getData().setScale(size / getCapHeight());
    }

    public GlyphLayout draw(final Batch batch, final CharSequence str, final float x, final float y, final int align) {
        return super.draw(batch, str, x, y, 0f, align, false);
    }

}
