package knight.arkham.objects.structures;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;

public abstract class InteractiveStructure {
    protected final Rectangle actualBounds;
    protected final World actualWorld;
    protected final Fixture fixture;
    protected final Body body;

    public InteractiveStructure(Rectangle rectangle, World world) {

        actualBounds = rectangle;
        actualWorld = world;

        fixture = createFixture();

        body = fixture.getBody();
    }

    protected abstract Fixture createFixture();
}
