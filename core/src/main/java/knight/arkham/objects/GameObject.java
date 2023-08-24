package knight.arkham.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import knight.arkham.helpers.Box2DHelper;

public abstract class GameObject {
    protected final Rectangle actualBounds;
    protected final World actualWorld;
    protected final Body body;
    private final Texture sprite;

    protected GameObject(Rectangle bounds, World world, Texture texture) {

        actualBounds = bounds;
        actualWorld = world;
        sprite = texture;

        body = createBody();
    }

    protected abstract Body createBody();

    public void draw(Batch batch) {

        Rectangle drawBounds = Box2DHelper.getDrawBounds(actualBounds, body);

        batch.draw(sprite, drawBounds.x, drawBounds.y, drawBounds.width, drawBounds.height);
    }

    public void dispose() {sprite.dispose();}
}
