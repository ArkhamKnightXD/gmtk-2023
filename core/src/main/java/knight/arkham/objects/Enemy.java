package knight.arkham.objects;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import knight.arkham.GameJam;
import knight.arkham.helpers.AssetsHelper;
import knight.arkham.helpers.Box2DBody;
import knight.arkham.helpers.Box2DHelper;

import static knight.arkham.helpers.Constants.PIXELS_PER_METER;

public class Enemy extends GameObject {
    private boolean setToDestroy;
    private boolean isDestroyed;
    private final Vector2 initialPosition;

    public Enemy(Rectangle rectangle, World world) {
        super(
            rectangle, world,
            new TextureRegion(new Texture("images/gray.jpg"), 0, 0, 16, 16)
        );
        initialPosition = new Vector2(rectangle.x/ PIXELS_PER_METER, rectangle.y/ PIXELS_PER_METER);
    }

    @Override
    protected Body createBody() {

        return Box2DHelper.createBody(

            new Box2DBody(actualBounds, BodyDef.BodyType.DynamicBody,
                10, actualWorld, this)
        );
    }

    public void update() {

        if (GameJam.INSTANCE.resetStage){
            setToDestroy = false;
            isDestroyed = false;

            body.setTransform(initialPosition, 0);
        }

        if (setToDestroy && !isDestroyed)
            isDestroyed = true;
    }

    @Override
    public void draw(Batch batch) {
        if (!isDestroyed)
            super.draw(batch);
    }

    public void hitOnHead() {
        setToDestroy = true;

        Sound sound = AssetsHelper.loadSound("stomp.wav");

        sound.play();

        GameJam.INSTANCE.resetStage = false;
    }
}
