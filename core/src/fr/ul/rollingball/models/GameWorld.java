package fr.ul.rollingball.models;


import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import fr.ul.rollingball.views.GameScreen;


/**
 * Gère le monde phsique du jeu
 */
public class GameWorld
{
    private static int LARGEUR = 80;
    private static int HAUTEUR = 60;
    private GameScreen ecranJeu;
    private Ball2D bille2D;

    public GameWorld()
    {
        
    }

    public Ball2D getBille2D()
    {
        return bille2D;
    }

    public void draw(SpriteBatch affMonde)
    {
        ecranJeu.render(0);
    }
}
