package knight.arkham.objects.structures;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import knight.arkham.helpers.Box2DHelper;

public abstract class InteractiveStructure {
    protected final Rectangle actualBounds;
    protected final World actualWorld;
    protected final Fixture fixture;
    protected final Body body;
    private final Rectangle drawBounds;

    public InteractiveStructure(Rectangle rectangle, World world) {

        actualBounds = rectangle;
        actualWorld = world;

        fixture = createFixture();

        body = fixture.getBody();

        drawBounds = Box2DHelper.getDrawBounds(rectangle, body);
    }

    protected abstract Fixture createFixture();

    public void draw (Batch batch, Texture sprite){

        batch.draw(sprite, drawBounds.x, drawBounds.y, drawBounds.width, drawBounds.height);
    }
}
