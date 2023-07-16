package knight.arkham.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ScreenUtils;
import knight.arkham.GameJam;
import knight.arkham.helpers.GameContactListener;
import knight.arkham.helpers.GameData;
import knight.arkham.helpers.GameDataHelper;
import knight.arkham.helpers.TileMapHelper;
import knight.arkham.objects.Enemy;
import knight.arkham.objects.Player;
import knight.arkham.objects.structures.Checkpoint;
import knight.arkham.objects.structures.NeutralPlatform;
import knight.arkham.objects.structures.Platform;

import static knight.arkham.helpers.Constants.GAME_DATA_FILENAME;

public class GameScreen extends ScreenAdapter {
    private final GameJam game;
    private final OrthographicCamera camera;
    private final World world;
    private final OrthogonalTiledMapRenderer mapRenderer;
    private final Player player;
    private final TileMapHelper tileMap;
    private boolean isDebug;

    public GameScreen() {
        game = GameJam.INSTANCE;

        camera = game.globalCamera;

        world = new World(new Vector2(0, -40), true);

        GameContactListener contactListener = new GameContactListener();

        world.setContactListener(contactListener);

        player = new Player(new Rectangle(450, 60, 16, 16), world);

        GameData gameDataToSave = new GameData("GameScreen", player.getWorldPosition());
        GameDataHelper.saveGameData(GAME_DATA_FILENAME, gameDataToSave);

        tileMap = new TileMapHelper(world, "maps/playground/test.tmx");

        mapRenderer = tileMap.setupMap();

//        isDebug = false;
    }

    @Override
    public void resize(int width, int height) {

        game.viewport.update(width, height);
    }


    private void update(float deltaTime) {

        world.step(1 / 60f, 6, 2);

        updateCameraPosition();

        player.update(deltaTime);

        for (Enemy enemy : tileMap.getEnemies()) {

            if (player.getDistanceInBetween(enemy.getPixelPosition()) < 170)
                enemy.getBody().setActive(true);

            enemy.update(deltaTime);
        }

        for (Platform platform : tileMap.getPlatforms()) {

            if (Gdx.input.isKeyJustPressed(Input.Keys.C))
                platform.changeColor();
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.F1))
            isDebug = !isDebug;

        game.manageExitTheGame();
    }

    private void updateCameraPosition() {

        boolean isPlayerInsideMapBounds = tileMap.isPlayerInsideMapBounds(player.getPixelPosition());

        if (isPlayerInsideMapBounds)
            camera.position.set(player.getWorldPosition().x, 9.5f, 0);

        camera.update();
    }


    @Override
    public void render(float delta) {

        update(delta);

        draw();

    }

    private void draw() {

        ScreenUtils.clear(0, 0, 0, 0);

        if (!isDebug) {

            mapRenderer.setView(camera);

            mapRenderer.render();

            game.batch.setProjectionMatrix(camera.combined);

            game.batch.begin();

            player.draw(game.batch);

            for (Enemy enemy : tileMap.getEnemies())
                enemy.draw(game.batch);

            for (Platform platform : tileMap.getPlatforms())
                platform.draw(game.batch);

            for (NeutralPlatform neutralPlatform : tileMap.getNeutralPlatforms())
                neutralPlatform.draw(game.batch);

            for (Checkpoint checkpoint : tileMap.getCheckpoints())
                checkpoint.draw(game.batch);

            tileMap.getFinishFlag().draw(game.batch);

            game.batch.end();

        } else
            game.debugRenderer.render(world, camera.combined);
    }

    @Override
    public void hide() {

        dispose();
    }

    @Override
    public void dispose() {

        player.dispose();
        tileMap.getFinishFlag().dispose();

        for (Enemy enemy : tileMap.getEnemies())
            enemy.dispose();
    }
}
