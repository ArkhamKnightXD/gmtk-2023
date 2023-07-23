package knight.arkham;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import knight.arkham.screens.MainMenuScreen;

public class GameJam extends Game {
    public static GameJam INSTANCE;
    public SpriteBatch batch;
    public BitmapFont font;
    public OrthographicCamera globalCamera;
    public Viewport viewport;
    public boolean resetStage;

    public GameJam() {

        INSTANCE = this;
    }

    @Override
    public void create() {

        batch = new SpriteBatch();

        font = new BitmapFont();

        globalCamera = new OrthographicCamera();

        int screenWidth = Gdx.graphics.getWidth();
        int screenHeight = Gdx.graphics.getHeight();

        viewport = new FitViewport(screenWidth / 32f, screenHeight / 32f, globalCamera);

        setScreen(new MainMenuScreen());
    }

    public void manageExitTheGame() {

        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE))
            Gdx.app.exit();
    }
}
