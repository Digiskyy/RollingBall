package fr.ul.rollingball.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import fr.ul.rollingball.dataFactories.SoundFactory;
import fr.ul.rollingball.dataFactories.TextureFactory;

/**
 * Ecran d'intro du jeu
 */

public class SplashScreen extends ScreenAdapter
{
    private SpriteBatch affIntro; // SpriteBatch est une liste d'affichage qui regroupe les différents éléments à afficher et fait un envoi groupé à la carte graphique
    private TextureFactory textureFactory;
    private Texture imgIntro;
    private OrthographicCamera camera; // Visualise une partie du monde virtuel via une fenêtre de taille donnée

    public SplashScreen()
    {
        affIntro = new SpriteBatch(); // On ne précise pas de caméra comme on veut afficher l'image sur tout l'écran
        textureFactory = TextureFactory.getInstance();
        imgIntro = textureFactory.getImageIntro();

        /* Création d'une caméra et d'une zone d'affichage */
        camera = new OrthographicCamera(1024, 720); // Taille de l'image
        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0); // On positionne le centre de la caméra sur le centre de la fenêtre
        camera.update();
    }


    /**
     * Affiche l'image d'intro
     * @param delta ??
     */
    @Override
    public void render(float delta)
    {
        // Mise à jour de la caméra
        camera.update();
        affIntro.setProjectionMatrix(camera.combined);

        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Affichage
        affIntro.begin();
        affIntro.draw(imgIntro, 0, 0);
        affIntro.end();

        // Son
        //SoundFactory.getInstance().getSonGagne().play(0.1f);
    }

    @Override
    public void resize(int largeur, int hauteur)
    {
        camera.viewportWidth = 1024;//Gdx.graphics.getWidth();
        camera.viewportHeight = 720;//Gdx.graphics.getHeight();
        camera.update();
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
