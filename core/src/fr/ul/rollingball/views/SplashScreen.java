package fr.ul.rollingball.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import fr.ul.rollingball.dataFactories.TextureFactory;

/**
 * Ecran d'intro du jeu
 */

public class SplashScreen extends ScreenAdapter
{
    private SpriteBatch affIntro; // SpriteBatch est une liste d'affichage qui regroupe les différents éléments à afficher et fait un envoi groupé à la carte graphique
    private TextureFactory textureFactory;
    private Texture imgIntro;

    public SplashScreen()
    {
        affIntro = new SpriteBatch(); // On ne précise pas de caméra comme on veut afficher l'image sur tout l'écran
        textureFactory = TextureFactory.getInstance();
        imgIntro = textureFactory.getImageIntro();
    }


    /**
     * Affiche l'image d'intro
     * @param delta ??
     */
    @Override
    public void render(float delta)
    {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        affIntro.begin();
        affIntro.draw(imgIntro, 0, 0);
        affIntro.end();
    }

    /**
     * Détruit la liste d'affichage
     */
    @Override
    public void dispose ()
    {
        imgIntro.dispose();
        affIntro.dispose();
    }
}
