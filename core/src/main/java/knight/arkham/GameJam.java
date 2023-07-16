package knight.arkham;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import knight.arkham.screens.GameScreen;

public class GameJam extends Game {
    public static GameJam INSTANCE;
    public SpriteBatch batch;
    public OrthographicCamera globalCamera;
    public Viewport viewport;
    public Box2DDebugRenderer debugRenderer;
    public boolean setToDispose;

    public GameJam() {

        INSTANCE = this;
    }

    @Override
    public void create() {

        debugRenderer = new Box2DDebugRenderer();

        batch = new SpriteBatch();

        globalCamera = new OrthographicCamera();

        int screenWidth = Gdx.graphics.getWidth();
        int screenHeight = Gdx.graphics.getHeight();

        viewport = new FitViewport(screenWidth / 32f, screenHeight / 32f, globalCamera);

        setScreen(new GameScreen());
    }

    public void manageExitTheGame() {

        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE))
            Gdx.app.exit();
    }
}
