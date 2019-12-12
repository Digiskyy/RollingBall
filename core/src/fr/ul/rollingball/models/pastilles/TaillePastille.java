package fr.ul.rollingball.models.pastilles;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import fr.ul.rollingball.dataFactories.SoundFactory;
import fr.ul.rollingball.dataFactories.TextureFactory;
import fr.ul.rollingball.models.GameWorld;

/**
 * Pastille de type taille, qui augmente le taille de la bille quand elle est ramassée.
 */
public class TaillePastille extends Pastille
{
    private Texture imgPastilleTaille;

    public TaillePastille(GameWorld mondeJeu, Vector2 position)
    {
        super(mondeJeu, position);
        bodyPastille.setUserData(this);
        imgPastilleTaille = TextureFactory.getInstance().getImgPastilleTaille();
    }

    @Override
    /**
     * Affiche la pastille
     * @param affMonde la liste d'affichage auquelle on ajoute la pastille à afficher
     */
    public void draw(SpriteBatch affMonde)
    {
        affMonde.draw(imgPastilleTaille, getPosition().x - RAYON, getPosition().y - RAYON, RAYON * 2f, RAYON * 2f);
        // On soustrait le rayon de la pastille pour réaligner le centre de la texture affichée et le centre du body
    }

    @Override
    /**
     * Applique l'effet de la pastille à la bille qui l'a ramassée
     */
    public void effect()
    {
        SoundFactory.getInstance().getSonPastilleTaille().play(0.1f);

    }
}
