package knight.arkham;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.loaders.SkinLoader;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
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
    public AssetDescriptor<Skin> uiSkin;
    public int screenWidth;
    public int screenHeight;


    public GameJam() {

        INSTANCE = this;
    }

    @Override
    public void create() {

        batch = new SpriteBatch();

        font = new BitmapFont();

        globalCamera = new OrthographicCamera();

        screenWidth = Gdx.graphics.getWidth();
        screenHeight = Gdx.graphics.getHeight();

        viewport = new FitViewport(screenWidth / 32f, screenHeight / 32f, globalCamera);

        uiSkin = new AssetDescriptor<>("images/ui/uiskin.json", Skin.class, new SkinLoader.SkinParameter("images/ui/uiskin.atlas"));

        setScreen(new MainMenuScreen());
    }

    public void closeTheGame() {

        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE))
            Gdx.app.exit();
    }
}
