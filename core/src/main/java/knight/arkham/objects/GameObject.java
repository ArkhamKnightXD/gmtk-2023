package knight.arkham.objects;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import knight.arkham.helpers.Box2DHelper;

import static knight.arkham.helpers.Constants.PIXELS_PER_METER;

public abstract class GameObject {
    protected final Rectangle actualBounds;
    protected final World actualWorld;
    protected final Body body;
    private final TextureRegion actualRegion;

    protected GameObject(Rectangle bounds, World world, TextureRegion region) {

        actualBounds = bounds;
        actualWorld = world;
        actualRegion = region;

        body = createBody();
    }

    protected abstract Body createBody();

    public void draw(Batch batch) {

        Rectangle drawBounds = Box2DHelper.getDrawBounds(actualBounds, body);

        batch.draw(actualRegion, drawBounds.x, drawBounds.y, drawBounds.width, drawBounds.height);
    }

    public Body getBody() {return body;}

    protected void applyLinealImpulse(Vector2 impulseDirection) {
        body.applyLinearImpulse(impulseDirection, body.getWorldCenter(), true);
    }

    public Vector2 getPixelPosition() {

        return new Vector2(body.getPosition().x * PIXELS_PER_METER, body.getPosition().y * PIXELS_PER_METER);
    }

    public Vector2 getWorldPosition() {return body.getPosition();}

    public void dispose() {actualRegion.getTexture().dispose();}
}
