package knight.arkham.objects.structures;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import knight.arkham.helpers.Box2DBody;
import knight.arkham.helpers.Box2DHelper;

public class NeutralPlatform extends InteractiveStructure {
    private final Texture sprite;

    public NeutralPlatform(Rectangle rectangle, World world) {
        super(rectangle, world);

        sprite = new Texture("images/gray.jpg");
    }

    public void draw(Batch batch) {

        super.draw(batch, sprite);
    }

    @Override
    protected Fixture createFixture() {

        return Box2DHelper.createStaticFixture(
            new Box2DBody(actualBounds,0, actualWorld, this)
        );
    }
    public void dispose() {sprite.dispose();}
}
