package knight.arkham.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import knight.arkham.GameJam;
import knight.arkham.helpers.GameContactListener;
import knight.arkham.helpers.GameData;
import knight.arkham.helpers.GameDataHelper;
import knight.arkham.helpers.TileMapHelper;
import knight.arkham.objects.Enemy;
import knight.arkham.objects.Player;
import knight.arkham.objects.structures.MovingStructure;

import static knight.arkham.helpers.Constants.GAME_DATA_FILENAME;

public class GameScreen extends ScreenAdapter {
    private final GameJam game;
    private final OrthographicCamera camera;
    private final World world;
    private final OrthogonalTiledMapRenderer mapRenderer;
    private final Player player;
    private final TileMapHelper tileMap;
    private final TextureAtlas textureAtlas;
    private boolean isDebug;
    private boolean isDisposed;


    public GameScreen() {
        game = GameJam.INSTANCE;

        camera = game.globalCamera;

        world = new World(new Vector2(0, -40), true);

        GameContactListener contactListener = new GameContactListener();

        world.setContactListener(contactListener);

        textureAtlas = new TextureAtlas("images/atlas/Mario_and_Enemies.pack");

        TextureRegion playerRegion = textureAtlas.findRegion("little_mario");

        player = new Player(new Rectangle(450, 60, 32, 32), world, playerRegion);

        GameData gameDataToSave = new GameData("GameScreen", player.getWorldPosition());
        GameDataHelper.saveGameData(GAME_DATA_FILENAME, gameDataToSave);

        tileMap = new TileMapHelper(world, textureAtlas, "maps/playground/test.tmx");

        mapRenderer = tileMap.setupMap();

        isDebug = true;

        GameJam.INSTANCE.setToDispose = false;
    }

    @Override
    public void resize(int width, int height) {

        game.viewport.update(width, height);
    }


    private void update(float deltaTime){

        world.step(1 / 60f, 6, 2);

        updateCameraPosition();

        player.update(deltaTime);

        for (Enemy enemy : new Array.ArrayIterator<>(tileMap.getEnemies())){

            if (player.getDistanceInBetween(enemy.getPixelPosition()) < 170)
                enemy.getBody().setActive(true);

            enemy.update(deltaTime);
        }

        for (MovingStructure structure : new Array.ArrayIterator<>(tileMap.getMovingStructures()))
            structure.update(deltaTime);

        if (Gdx.input.isKeyJustPressed(Input.Keys.F1))
            isDebug = !isDebug;

        if (Gdx.input.isKeyJustPressed(Input.Keys.F2))
            GameJam.INSTANCE.setToDispose = true;

        game.manageExitTheGame();
    }

    private void disposeWorld() {

        world.dispose();
        mapRenderer.dispose();

        isDisposed = true;
    }

    private void updateCameraPosition(){

        if (Gdx.input.isKeyJustPressed(Input.Keys.F3))
            camera.zoom += 0.2f;

        if (Gdx.input.isKeyJustPressed(Input.Keys.F4))
            camera.zoom -= 0.2f;

        boolean isPlayerInsideMapBounds = tileMap.isPlayerInsideMapBounds(player.getPixelPosition());

        if (isPlayerInsideMapBounds)
            camera.position.set(player.getWorldPosition().x,9.5f, 0);

        camera.update();

        mapRenderer.setView(camera);
    }


    @Override
    public void render(float delta) {

        if (GameJam.INSTANCE.setToDispose && !isDisposed)
            disposeWorld();

        else if (!isDisposed){

            update(delta);

            draw();
        }
    }

    private void draw() {

        ScreenUtils.clear(0,0,0,0);

        if (!isDebug){

            mapRenderer.render();

            game.batch.setProjectionMatrix(camera.combined);

            game.batch.begin();

            player.draw(game.batch);

            for (Enemy enemy : new Array.ArrayIterator<>(tileMap.getEnemies()))
                enemy.draw(game.batch);

            for (MovingStructure structure : new Array.ArrayIterator<>(tileMap.getMovingStructures()))
                structure.draw(game.batch);

            tileMap.getFinishFlag().draw(game.batch);

            game.batch.end();
        }

        else
            game.debugRenderer.render(world, camera.combined);
    }

    @Override
    public void hide() {

        dispose();
    }

    @Override
    public void dispose() {

        player.dispose();
        textureAtlas.dispose();
        tileMap.getFinishFlag().dispose();

        for (Enemy enemy : new Array.ArrayIterator<>(tileMap.getEnemies()))
            enemy.dispose();

        for (MovingStructure structure : new Array.ArrayIterator<>(tileMap.getMovingStructures()))
            structure.dispose();
    }
}
