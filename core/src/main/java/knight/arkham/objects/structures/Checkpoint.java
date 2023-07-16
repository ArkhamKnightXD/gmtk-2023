package knight.arkham.objects.structures;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import knight.arkham.helpers.*;

import static knight.arkham.helpers.Constants.DESTROYED_BIT;
import static knight.arkham.helpers.Constants.GAME_DATA_FILENAME;

public class Checkpoint extends InteractiveStructure {
    private final Rectangle drawBounds;
    private final Texture sprite;
    private boolean isDestroyed;

    public Checkpoint(Rectangle rectangle, World world) {
        super(rectangle, world);

        sprite = new Texture("images/gray.jpg");

        drawBounds = Box2DHelper.getDrawBounds(rectangle, body);
    }

    public void draw(Batch batch) {

        if (!isDestroyed)
            batch.draw(sprite, drawBounds.x, drawBounds.y, drawBounds.width, drawBounds.height);
    }

    @Override
    protected Fixture createFixture() {

        return Box2DHelper.createStaticFixture(
            new Box2DBody(actualBounds, actualWorld, this)
        );
    }

    public void createCheckpoint() {

        Filter filter = new Filter();

        filter.categoryBits = DESTROYED_BIT;
        fixture.setFilterData(filter);

        Sound sound = AssetsHelper.loadSound("coin.wav");
        sound.play();

        GameData gameDataToSave = new GameData("GameScreen", body.getPosition());
        GameDataHelper.saveGameData(GAME_DATA_FILENAME, gameDataToSave);

        isDestroyed = true;
    }
}
