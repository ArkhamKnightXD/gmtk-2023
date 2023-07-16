package knight.arkham.objects.structures;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import knight.arkham.helpers.Box2DBody;
import knight.arkham.helpers.Box2DHelper;

public class Platform extends InteractiveStructure {

    private final Texture sprite;
    private final Rectangle drawBounds;
    public Platform(Rectangle rectangle, World world, TiledMap tiledMap, boolean isBlue) {
        super(rectangle, world, tiledMap);

        if (isBlue)
            sprite = new Texture("images/blue.jpg");

        else
            sprite = new Texture("images/pink.jpg");


        drawBounds = Box2DHelper.getDrawBounds(rectangle, body);
    }

    public void draw(Batch batch) {

        batch.draw(sprite, drawBounds.x, drawBounds.y, drawBounds.width, drawBounds.height);
    }

    @Override
    protected Fixture createFixture() {
        return Box2DHelper.createStaticFixture(
            new Box2DBody(actualBounds, actualWorld, this)
        );
    }
}
