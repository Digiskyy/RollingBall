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
     * @param affMonde la liste d'affichage auquelle on ajoute la bille à afficher
     */
    public void draw(SpriteBatch affMonde)
    {
        //System.out.println("Position boule : X = " + getPosition().x + " | Y = " + getPosition().y);
        affMonde.draw(imgBall, getPosition().x - taille, getPosition().y - taille, taille * 2f, taille * 2f);
        // On fait la position - taille (= rayon) car sinon la texture est décalée et le point en bas à gauche correspond au centre du body. On réaligne les centres de l'image et du body
    }
}
