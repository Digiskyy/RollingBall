package fr.ul.rollingball.models;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import fr.ul.rollingball.dataFactories.TextureFactory;
import fr.ul.rollingball.views.GameScreen;


/**
 * Gère le monde phsique du jeu
 *
 * lien utile pour les body et le world avce libgdx et box2d : https://github.com/libgdx/libgdx/wiki/Box2d
 */
public class GameWorld
{
    private static int LARGEUR = 80;
    private static int HAUTEUR = 60;
    private GameScreen ecranJeu;
    private Ball bille2D;
    private World monde;
    private Vector2 gravite;
    private Vector2 position;
    // Affichage - Textures
    private SpriteBatch listeAffichageMonde; // Liste d'affichage qui regroupe les différents éléments du fond à afficher et fait un envoi groupé à la carte graphique
    private TextureFactory textureFactory;
    private Texture imgFond;


    public GameWorld(GameScreen ecranJeu)
    {
        this.ecranJeu = ecranJeu;

        /* Affichage - Textures */
        listeAffichageMonde = new SpriteBatch();
        textureFactory = TextureFactory.getInstance();
        imgFond = textureFactory.getImageFond();

        /* Création du monde */
        gravite = new Vector2(0, -10f);
        position = new Vector2(GameWorld.LARGEUR/2, GameWorld.HAUTEUR/2);
        monde = new World(gravite, true); // Le paramètre true permet d'améliorer les performances en ne simulant pas les coprs inactifs.
        bille2D = new Ball2D(monde, position);
    }

    public Ball getBille()
    {
        return bille2D;
    }

    /**
     * Affiche le monde
     */
    public void draw()
    {
        listeAffichageMonde.begin(); // Prépare la liste à être dessiné

        // Affichage de l'image du fond
        listeAffichageMonde.draw(imgFond, 0, 0); // Paramètres 0 et 0 définissent le point d'origine de l'image, en bas à gauche

        // Affichage de l'image de la bille
        bille2D.draw(listeAffichageMonde);

        listeAffichageMonde.end(); // Finit l'affichage
    }

    /**
     * Termine l'affichage
     */
    public void dispose()
    {
        listeAffichageMonde.dispose();
    }
}
