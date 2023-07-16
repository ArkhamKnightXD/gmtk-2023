package knight.arkham.helpers;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import knight.arkham.objects.Enemy;
import knight.arkham.objects.Player;
import knight.arkham.objects.structures.Checkpoint;
import knight.arkham.objects.structures.FinishFlag;

import static knight.arkham.helpers.Constants.*;

public class Box2DHelper {

    public static Fixture createStaticFixture(Box2DBody box2DBody){

        PolygonShape shape = new PolygonShape();

        FixtureDef fixtureDef = createStandardFixtureDef(box2DBody, shape);

        if (box2DBody.userData instanceof Checkpoint)
            fixtureDef.filter.categoryBits = CHECKPOINT_BIT;

        else if (box2DBody.userData instanceof FinishFlag)
            fixtureDef.filter.categoryBits = FINISH_BIT;

        else
            fixtureDef.filter.categoryBits = GROUND_BIT;

        Body body = createBox2DBodyByType(box2DBody);

        Fixture fixture = body.createFixture(fixtureDef);

        fixture.setUserData(box2DBody.userData);

        shape.dispose();

        return fixture;
    }

    private static Body createBox2DBodyByType(Box2DBody box2DBody) {

        BodyDef bodyDef = new BodyDef();

        bodyDef.type = box2DBody.bodyType;

        bodyDef.position.set(box2DBody.bounds.x / PIXELS_PER_METER, box2DBody.bounds.y / PIXELS_PER_METER);

        bodyDef.fixedRotation = true;

        return box2DBody.world.createBody(bodyDef);
    }


    public static Body createBody(Box2DBody box2DBody) {

        PolygonShape shape = new PolygonShape();

        FixtureDef fixtureDef = createStandardFixtureDef(box2DBody, shape);

        Body body = createBox2DBodyByType(box2DBody);

        if (box2DBody.userData instanceof Player)
            createPlayerBody(box2DBody, fixtureDef, body);

        else if (box2DBody.userData instanceof Enemy)
            createEnemyBody(box2DBody, fixtureDef, body);

        else {

            fixtureDef.filter.categoryBits = GROUND_BIT;

            body.createFixture(fixtureDef);
        }

        shape.dispose();

        return body;
    }

    private static FixtureDef createStandardFixtureDef(Box2DBody box2DBody, PolygonShape shape) {
        shape.setAsBox(box2DBody.bounds.width / 2 / PIXELS_PER_METER, box2DBody.bounds.height / 2 / PIXELS_PER_METER);

        FixtureDef fixtureDef = new FixtureDef();

        fixtureDef.shape = shape;

        fixtureDef.density = box2DBody.density;
        return fixtureDef;
    }

    private static void createEnemyBody(Box2DBody box2DBody, FixtureDef fixtureDef, Body body) {

        fixtureDef.filter.categoryBits = ENEMY_BIT;

        fixtureDef.filter.maskBits = (short) (GROUND_BIT | PINK_GROUND_BIT | BLUE_GROUND_BIT | ENEMY_BIT | PLAYER_BIT);

        body.createFixture(fixtureDef).setUserData(box2DBody.userData);

        PolygonShape headCollider = getEnemyHeadHeadCollider();

        fixtureDef.shape = headCollider;

        fixtureDef.restitution = 0.6f;
        fixtureDef.filter.categoryBits = ENEMY_HEAD_BIT;

        body.createFixture(fixtureDef).setUserData(box2DBody.userData);

        headCollider.dispose();
    }

    private static PolygonShape getEnemyHeadHeadCollider() {

        PolygonShape head = new PolygonShape();

        Vector2[] vertices = new Vector2[4];

        vertices[0] = new Vector2(-10, 15).scl(1 / PIXELS_PER_METER);
        vertices[1] = new Vector2(10, 15).scl(1 / PIXELS_PER_METER);
        vertices[2] = new Vector2(-8, 10).scl(1 / PIXELS_PER_METER);
        vertices[3] = new Vector2(8, 10).scl(1 / PIXELS_PER_METER);

        head.set(vertices);

        return head;
    }


    private static void createPlayerBody(Box2DBody box2DBody, FixtureDef fixtureDef, Body body) {

        fixtureDef.filter.categoryBits = PLAYER_BIT;

        fixtureDef.filter.maskBits = (short) (GROUND_BIT | BLUE_GROUND_BIT | CHECKPOINT_BIT | FINISH_BIT | ENEMY_BIT | ENEMY_HEAD_BIT);

        body.createFixture(fixtureDef).setUserData(box2DBody.userData);
    }

    public static Rectangle getDrawBounds(Rectangle bounds, Body body){

        return new Rectangle(
            body.getPosition().x - bounds.width / 2 / PIXELS_PER_METER,
            body.getPosition().y - bounds.height / 2 / PIXELS_PER_METER,
            bounds.width / PIXELS_PER_METER,
            bounds.height / PIXELS_PER_METER
        );
    }
}
