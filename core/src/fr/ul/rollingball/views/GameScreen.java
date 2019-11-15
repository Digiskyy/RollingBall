package fr.ul.rollingball.views;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import fr.ul.rollingball.models.GameWorld;


/**
 * Gère l'écran de jeu et tous ses composants
 */

public class GameScreen extends ScreenAdapter
{
    private GameWorld mondeJeu;

    public GameScreen()
    {
        mondeJeu = new GameWorld(this);
    }

    @Override
    public void render(float delta)
    {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Affichage du monde et de ses composants
        mondeJeu.draw();
    }

    @Override
    public void dispose()
    {
        mondeJeu.dispose();
    }
}
