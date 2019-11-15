package fr.ul.rollingball.views;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import fr.ul.rollingball.dataFactories.TextureFactory;


/**
 * Gère l'écran de jeu et tous ses composants
 */

public class GameScreen extends ScreenAdapter
{
    private SpriteBatch affFond; // Liste d'affichage qui regroupe les différents éléments du fond à afficher et fait un envoi groupé à la carte graphique
    private TextureFactory textureFactory;
    private Texture imgFond;

    public GameScreen()
    {
        affFond = new SpriteBatch(); // On ne précise pas de caméra comme on veut afficher l'image sur tout l'écran
        textureFactory = TextureFactory.getInstance();
        imgFond = textureFactory.getImageFond();
    }

    @Override
    public void render(float delta)
    {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        affFond.begin();
        affFond.draw(imgFond, 0, 0); // paramètre 0 et 0 définissent le point d'origine de l'image, en bas à gauche
        affFond.end();
    }

    @Override
    public void dispose()
    {
        imgFond.dispose();
        affFond.dispose();
    }
}
