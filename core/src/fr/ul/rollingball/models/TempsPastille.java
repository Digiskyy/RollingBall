package fr.ul.rollingball.models;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import fr.ul.rollingball.dataFactories.SoundFactory;
import fr.ul.rollingball.dataFactories.TextureFactory;

/**
 * Pastille de type temps, qui augmente le temps quand la bille la ramasse
 */
public class TempsPastille extends Pastille
{
    private Texture imgPastilleTemps;

    public TempsPastille(World monde, Vector2 position)
    {
        super(monde, position);
        bodyPastille.setUserData(this); // Permet d'identifier que c'est une pastille de type temps lors des collisions
        imgPastilleTemps = TextureFactory.getInstance().getImgPastilleTemps();
    }
    @Override
    /**
     * Affiche la pastille
     * @param affMonde la liste d'affichage auquelle on ajoute la pastille à afficher
     */
    public void draw(SpriteBatch affMonde)
    {
        affMonde.draw(imgPastilleTemps, getPosition().x - RAYON, getPosition().y - RAYON, RAYON * 2f, RAYON * 2f);
        // On soustrait le rayon de la pastille pour réaligner le centre de la texture affichée et le centre du body
    }

    @Override
    /**
     * Applique l'effet de la pastille à la bille qui l'a ramassée
     */
    public void effect()
    {
        SoundFactory.getInstance().getSonPastilleTemps().play(0.1f);
    }
}
