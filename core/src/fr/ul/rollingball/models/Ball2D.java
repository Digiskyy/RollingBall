package fr.ul.rollingball.models;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import fr.ul.rollingball.dataFactories.TextureFactory;


/**
 * Représente la bille 2D
 */
public class Ball2D extends Ball
{
    private Texture imgBall;


    public Ball2D(World monde, Vector2 position)
    {
        super(monde, position);
        imgBall = TextureFactory.getInstance().getImageBall2D();
    }

    /**
     * Affiche la bille à l'écran
     * @param affBall liste d'affichage
     */
    public void draw(SpriteBatch affBall)
    {
        System.out.println("Position boule : X = " + getPosition().x + " | Y = " + getPosition().y);
        affBall.draw(imgBall, getPosition().x, getPosition().y, taille, taille);
    }
}
