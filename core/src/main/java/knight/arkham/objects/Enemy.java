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
import knight.arkham.helpers.AssetsHelper;
import knight.arkham.helpers.Box2DBody;
import knight.arkham.helpers.Box2DHelper;

public class Enemy extends GameObject {
    private float stateTimer;

    public boolean isMovingRight;
    private boolean setToDestroy;
    private boolean isDestroyed;

    public Enemy(Rectangle rectangle, World world) {
        super(
            rectangle, world,
            new TextureRegion(new Texture("images/pink.jpg"), 0, 0, 16, 16)
        );

        body.setActive(false);

        stateTimer = 0;
    }

    @Override
    protected Body createBody() {

        return Box2DHelper.createBody(

            new Box2DBody(actualBounds, BodyDef.BodyType.DynamicBody,
                10, actualWorld, this)
        );
    }

    private void destroyEnemy() {

        actualWorld.destroyBody(body);
        isDestroyed = true;

        stateTimer = 0;
    }

    public void update(float deltaTime) {

        stateTimer += deltaTime;

        if (setToDestroy && !isDestroyed)
            destroyEnemy();

        else if (!isDestroyed && body.isActive()) {

            if (isMovingRight && body.getLinearVelocity().x <= 4)
                applyLinealImpulse(new Vector2(2, 0));

            else if (!isMovingRight && body.getLinearVelocity().x >= -4)
                applyLinealImpulse(new Vector2(-2, 0));

            if (getPixelPosition().y < -50)
                setToDestroy = true;
        }
    }

    @Override
    public void draw(Batch batch) {
        if (!isDestroyed || stateTimer < 1)
            super.draw(batch);
    }

    public void hitOnHead() {
        setToDestroy = true;

        Sound sound = AssetsHelper.loadSound("stomp.wav");

        sound.play();
    }
}
