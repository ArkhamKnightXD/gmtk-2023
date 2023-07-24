package knight.arkham.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ScreenUtils;
import knight.arkham.GameJam;
import knight.arkham.helpers.*;
import knight.arkham.objects.Enemy;
import knight.arkham.objects.Player;
import knight.arkham.objects.structures.Checkpoint;
import knight.arkham.objects.structures.NeutralPlatform;
import knight.arkham.objects.structures.Platform;

import static knight.arkham.helpers.Constants.*;

public class GameScreen extends ScreenAdapter {
    private final GameJam game;
    private final OrthographicCamera camera;
    private final World world;
    private final Player player;
    private final TileMapHelper tileMap;
    private final Music music;
    private boolean isPaused;

    public GameScreen() {
        game = GameJam.INSTANCE;

        camera = game.globalCamera;

        world = new World(new Vector2(0, -40), true);

        GameContactListener contactListener = new GameContactListener();

        world.setContactListener(contactListener);

        player = new Player(new Rectangle(400, 50, 16, 16), world);

        GameDataHelper.saveGameData(GAME_DATA_FILENAME, player.getWorldPosition());

        tileMap = new TileMapHelper(world, "maps/test.tmx");

        tileMap.setupMap();
        music = AssetsHelper.loadMusic("pixel3.mp3");

        music.play();
        music.setLooping(true);
    }

    @Override
    public void resize(int width, int height) {

        game.viewport.update(width, height);
    }

    private void update() {

        world.step(1 / 60f, 6, 2);

        updateCameraPosition();

        player.update();

        for (Enemy enemy : tileMap.getEnemies())
            enemy.update();

        game.closeTheGame();
    }

    private void updateCameraPosition() {

        boolean isPlayerInsideMapBounds = tileMap.isPlayerInsideMapBounds(player.getPixelPosition());

        if (isPlayerInsideMapBounds)
            camera.position.set(player.getWorldPosition().x, 9.5f, 0);

        camera.update();
    }


    @Override
    public void render(float delta) {

        if (Gdx.input.isKeyJustPressed(Input.Keys.F1))
            isPaused = !isPaused;

        if (Gdx.input.isKeyJustPressed(Input.Keys.F2))
            game.setScreen(new MainMenuScreen());

        if (!isPaused){
            music.setVolume(0.3f);
            update();
        }
        else
            music.setVolume(0.1f);

        draw();
    }

    private void draw() {

        ScreenUtils.clear(0, 0, 0, 0);

        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();

        player.draw(game.batch);

        for (Enemy enemy : tileMap.getEnemies())
            enemy.draw(game.batch);

        for (Platform platform : tileMap.getPlatforms()){

            if (Gdx.input.isKeyJustPressed(Input.Keys.C))
                platform.changeColor();

            platform.draw(game.batch);
        }

        for (NeutralPlatform neutralPlatform : tileMap.getNeutralPlatforms())
            neutralPlatform.draw(game.batch);

        for (Checkpoint checkpoint : tileMap.getCheckpoints())
            checkpoint.draw(game.batch);

        tileMap.getFinishFlag().draw(game.batch);

        game.batch.end();
    }



    @Override
    public void hide() {

        dispose();
    }

    @Override
    public void dispose() {

        player.dispose();
        tileMap.getFinishFlag().dispose();
        music.dispose();

        for (Enemy enemy : tileMap.getEnemies())
            enemy.dispose();

        for (Platform platform : tileMap.getPlatforms())
            platform.dispose();

        for (NeutralPlatform neutralPlatform : tileMap.getNeutralPlatforms())
            neutralPlatform.dispose();

        for (Checkpoint checkpoint : tileMap.getCheckpoints())
            checkpoint.dispose();
    }
}
