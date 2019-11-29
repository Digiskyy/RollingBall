package fr.ul.rollingball.models;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import fr.ul.rollingball.dataFactories.TextureFactory;
import fr.ul.rollingball.views.GameScreen;

import java.util.ArrayList;


/**
 * Gère le monde phsique du jeu
 *
 * lien utile pour les body et le world avec libgdx et box2d : https://github.com/libgdx/libgdx/wiki/Box2d
 */
public class GameWorld
{
    public static final int LARGEUR = 80;
    public static final int HAUTEUR = 60;
    private GameScreen ecranJeu;
    private Ball bille;
    private World monde;
    private Vector2 positionBille;
    private ArrayList<Pastille> pastilles;
    // Textures
    private TextureFactory textureFactory;
    private Texture imgFond;


    public GameWorld(GameScreen ecranJeu)
    {
        this.ecranJeu = ecranJeu;

        /* Récupération de la texture du monde*/
        textureFactory = TextureFactory.getInstance();
        imgFond = textureFactory.getImageFond();

        /* Création du monde */
        monde = new World(new Vector2(0, 0), true); // Le paramètre true permet d'améliorer les performances en ne simulant pas les corps inactifs.

        /* Création de la bille et position au milieu */
        positionBille = new Vector2((float) LARGEUR/2, (float) HAUTEUR/2);
        bille = new Ball2D(monde, positionBille);
        
        /* Création des pastilles */
        pastilles = new ArrayList<>();
        pastilles.add(new ScorePastille(monde, new Vector2((float)LARGEUR/3, (float)HAUTEUR/3)));
    }

    public Ball getBille()
    {
        return bille;
    }

    public World getMonde()
    {
        return monde;
    }

    /**
     * Affiche le monde et ses élements
     * @param listeAffichageMonde liste d'affichage du monde et de ses éléments (billes, pastilles, murs)
     */
    public void draw(SpriteBatch listeAffichageMonde)
    {
        /* Affichage de l'image du fond */
        listeAffichageMonde.draw(imgFond, 0, 0, LARGEUR, HAUTEUR); // Paramètres 0 et 0 définissent le point d'origine de l'image, en bas à gauche

        /* Affichage de l'image de la bille */
        bille.draw(listeAffichageMonde);

        /* Affichage des pastilles de type Score */
        for(Pastille pastille : pastilles)
        {
            pastille.draw(listeAffichageMonde);
        }
    }
}
