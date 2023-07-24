package knight.arkham.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.math.Vector2;

public class GameDataHelper {

    public static void saveGameData(String filename, Vector2 position){

        Preferences preferences = Gdx.app.getPreferences(filename);

        preferences.putFloat("positionX", position.x);
        preferences.putFloat("positionY", position.y);

        preferences.flush();
    }

    public static Vector2 loadGameData(String filename){

        Preferences preferences = Gdx.app.getPreferences(filename);

        float positionX = preferences.getFloat("positionX");
        float positionY = preferences.getFloat("positionY");

        return new Vector2(positionX, positionY);
    }
}
