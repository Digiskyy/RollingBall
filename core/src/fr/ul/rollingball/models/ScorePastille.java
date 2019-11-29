package fr.ul.rollingball.models;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import fr.ul.rollingball.dataFactories.TextureFactory;


/**
 * Pastille de type score, qui augmente le score quand la bille la rammasse
 */
public class ScorePastille extends Pastille
{
    private Texture imgPastilleScore;

    public ScorePastille(World monde, Vector2 position)
    {
        super(monde, position);
        bodyPastille.setUserData("pastille_score"); // Permet d'identifier que c'est une pastille de type score quand la bille rentrera en collission avec elle
        imgPastilleScore = TextureFactory.getInstance().getImgPastilleScore();
    }

    @Override
    /**
     * Affiche la pastille
     * @param affMonde la liste d'affichage auquelle on ajoute la pastille à afficher
     */
    public void draw(SpriteBatch affMonde)
    {
        System.out.println("Position pastille : X = " + getPosition().x + " | Y = " + getPosition().y);
        affMonde.draw(imgPastilleScore, getPosition().x, getPosition().y, getRAYON() * 2f, getRAYON() * 2f);
    }

    @Override
    /**
     * Applique l'effet de la pastille à la bille qui l'a rammassée
     */
    public void effect()
    {

    }
}