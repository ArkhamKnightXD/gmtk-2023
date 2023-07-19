package knight.arkham.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import knight.arkham.GameJam;
import knight.arkham.helpers.Box2DBody;
import knight.arkham.helpers.Box2DHelper;
import knight.arkham.helpers.GameDataHelper;

import static knight.arkham.helpers.Constants.GAME_DATA_FILENAME;

public class Player extends GameObject {

    public Player(Rectangle bounds, World world) {
        super(
            bounds, world,
            new TextureRegion(new Texture("images/white.jpg"), 0, 0, 16, 16)
        );
    }

    @Override
    protected Body createBody() {

        return Box2DHelper.createBody(
            new Box2DBody(actualBounds, 10, actualWorld, this)
        );
    }

    public void update() {

        if (body.getLinearVelocity().x <= 10)
            applyLinealImpulse(new Vector2(5, 0));

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) && body.getLinearVelocity().y == 0)
            applyLinealImpulse(new Vector2(0, 170));

        playerFallToDead();
    }

    private void playerFallToDead() {

        if (getPixelPosition().y < -100) {

            body.setLinearVelocity(0, 0);

            Vector2 position = GameDataHelper.loadGameData(GAME_DATA_FILENAME).position;

            body.setTransform(position, 0);

            GameJam.INSTANCE.resetStage = true;
        }
    }

    public void getHitByEnemy() {
        applyLinealImpulse(new Vector2(-5000, 0));
    }
}
