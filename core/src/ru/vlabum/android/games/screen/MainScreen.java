package ru.vlabum.android.games.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.vlabum.android.games.base.Base2DScreen;

public class MainScreen extends Base2DScreen {

    private static final Vector2 V_DIRECT_X = new Vector2(1, 0); // направление оси X
    private static final Vector2 V_DIRECT_Y = new Vector2(0, 1); // направление оси Y
    private static final float DEF_SPEED = 2f;

    private final Texture txSpace = new Texture("space.jpg");
    private final Texture txShuttle = new Texture("shuttle.png");
    private final TextureRegion txSpaceRegion = new TextureRegion(txSpace, 300, 100, 700, 480);
    private final Vector2 vShuttlePosition = new Vector2(txShuttle.getWidth() / 2, txShuttle.getHeight() / 2); // центр шаттла
    private final Vector2 vTargetPosition = vShuttlePosition.cpy(); // целевая позизия шаттла
    private final Vector2 vSpeed = new Vector2(0, DEF_SPEED); // вектор скорости

    private int k = vTargetPosition.x < vShuttlePosition.x ? 1 : -1;
    private float rotation = 0;
    private float speed = DEF_SPEED;
    private float prevTargetDistance = 0;
    private boolean move = false;

    @Override
    public void show() {
        super.show();
    }

    @Override
    public void render (float delta) {
        super.render(delta);
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(txShuttle, -2.65f / Base2DScreen.DEF_HEIGHT, 0f, Base2DScreen.DEF_HEIGHT / 10, Base2DScreen.DEF_HEIGHT / 10);
        batch.draw(txShuttle, 0f, 0f, Base2DScreen.DEF_HEIGHT / 10, Base2DScreen.DEF_HEIGHT / 10);
        batch.end();
    }

    @Override
    public void dispose () {
        batch.dispose();
        txSpace.dispose();
        txShuttle.dispose();
        super.dispose();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        super.touchDown(screenX, screenY, pointer, button);
        vTargetPosition.set(screenX, this.getScreenBounds().getHeight() - screenY).mul(super.screenToWorld);
        System.out.println("X: " + vTargetPosition.x + " Y: " + vTargetPosition.y);
        calcParams();
        move = true;
        return false;
    }

    private void calcParams() {
        //вычисляем угол поворота и дистанцию
        final Vector2 vSub = vTargetPosition.cpy().sub(vShuttlePosition);
        prevTargetDistance = vSub.len2(); // без разницы какой, смотрим чтобы не увеличивался
        final Vector2 vNorTarget = vSub.cpy().nor();
        final int k = vTargetPosition.x < vShuttlePosition.x ? 1 : -1;
        final float cos = vNorTarget.dot(V_DIRECT_X);
        final float sin = vNorTarget.dot(V_DIRECT_Y);
        rotation = (float) Math.toDegrees(k * Math.acos(sin));
        // вычисляем скорость по осям
        final float ySpeed = speed * sin;
        final float xSpeed = speed * cos;
        vSpeed.set(xSpeed, ySpeed);
        System.out.printf("sin(x) = %f, cos(x) = %f, rotation = %f\n", sin, cos, rotation);
    }

    private void calcTarget() {
        // вычисляем точку остановки и скорость по осям
        final float cos = (float) Math.cos(Math.toRadians(rotation));
        final float sin = (float) Math.sin(Math.toRadians(rotation));
        final float ySpeed = speed * cos;
        final float xSpeed = -speed * sin;
        vSpeed.set(xSpeed, ySpeed);
        vTargetPosition.set(30f * xSpeed + vShuttlePosition.x, 30f * ySpeed + vShuttlePosition.y);
        prevTargetDistance = vTargetPosition.cpy().sub(vShuttlePosition).len2();
        System.out.printf("xTarget = %f, yTarget = %f\n", xSpeed, ySpeed);
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == 22) rotation -= 5;
        if (keycode == 22 && rotation < -180) rotation = 180;
        if (keycode == 21) rotation += 5;
        if (keycode == 21 && rotation > 180) rotation = -180;
        calcTarget();
        if (keycode == 19) {
            move = true;
        }
        System.out.println(keycode);
        return false;
    }

}
