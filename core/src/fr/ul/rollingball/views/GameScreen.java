package fr.ul.rollingball.views;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.utils.Timer;
import fr.ul.rollingball.models.GameState;
import fr.ul.rollingball.models.GameWorld;


/**
 * Gère l'écran de jeu et tous ses composants
 *
 * Liens utiles :
 *  Caméra orthographique : https://github.com/libgdx/libgdx/wiki/Orthographic-camera
 *  Accéléromètre : https://github.com/libgdx/libgdx/wiki/Accelerometer
 *  Démo. d'un jeu mobile avec libGDx : https://github.com/libgdx/libgdx-demo-superjumper
 *
 */

public class GameScreen extends ScreenAdapter
{
    private GameWorld mondeJeu;
    private OrthographicCamera camera; // Visualise une partie du monde virtuel via une fenêtre de taille donnée
    private OrthographicCamera cameraTexte;
    private SpriteBatch listeAffichageMonde; // Liste d'affichage qui regroupe les différents éléments du fond à afficher et fait un envoi groupé à la carte graphique
    private SpriteBatch listeAffichageTexte;
    private BitmapFont police;
    private static int dureeIteration;
    private GameState etatJeu;
    private Timer.Task tacheChgtLaby;

    public GameScreen()
    {
        mondeJeu = new GameWorld(this);
        etatJeu = new GameState();

        /* Création d'une caméra pour le monde et d'une zone d'affichage */
        camera = new OrthographicCamera(GameWorld.LARGEUR, GameWorld.HAUTEUR);
        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0); // On positionne le centre de la caméra sur le centre de la fenêtre
        camera.update();

        listeAffichageMonde = new SpriteBatch();

        /* Création de la caméra pour le texte */
        cameraTexte = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()); // Camera de la taille de la fenêtre
        cameraTexte.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);

        listeAffichageTexte = new SpriteBatch();

        dureeIteration = 1000; // durée d'une itération en ms
    }

    /**
     * Met à jour l'affichage du monde et de ses éléments
     * @param delta
     */
    @Override
    public void render(float delta)
    {
        // Gestion de la gravité
        update();

        // Mise à jour de la caméra (du viewport)
        camera.update();
        listeAffichageMonde.setProjectionMatrix(camera.combined);

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // Efface l'écran qui est affiché (en réalité le buffer de couleur)

        // Affichage du monde et de ses composants
        listeAffichageMonde.begin(); // Prépare la liste à être dessinée
        mondeJeu.draw(listeAffichageMonde); // Affiche
        listeAffichageMonde.end(); // Finit l'affichage

        /* Mode debug qui affiche le rayon exact des bodies pour savoir s'ils correspondent aux images affichées */
        /*Box2DDebugRenderer box2DDebugRenderer = new Box2DDebugRenderer();
        box2DDebugRenderer.render(mondeJeu.getMonde(), camera.combined);*/
    }

    /**
     * Gestion de la caméra quand les dimensions du monde change
     * @param largeur de la fenêtre
     * @param hauteur de la fenêtre
     */
    @Override
    public void resize(int largeur, int hauteur)
    {
        System.out.println("Largeur : " + largeur + " | Hauteur : " + hauteur);
        System.out.println("ViewPortWidth : " + camera.viewportWidth + " | ViewPortHeight : " + camera.viewportHeight);

        camera.viewportWidth = GameWorld.LARGEUR;
        camera.viewportHeight = GameWorld.HAUTEUR; // Tout le monde sera affiché mais les objets seront déformés
        // GameWorld.LARGEUR, GameWorld.LARGEUR * ((float) hauteur / (float) largeur) : On calcule la hauteur de la zone d'affichage pour que le ratio (hauteur/largeur) soit égal à celui du monde (hautMonde / largeurMonde) et ne déforme pas les objets
        camera.update();
    }

    /**
     * Met à jour les informations sur le monde (gravité calculée depuis l'accéléromètre)
     */
    public void update()
    {
        float accelX = Gdx.input.getAccelerometerX() * 5f;
        float accelY = Gdx.input.getAccelerometerY() * 5f;
        Vector2 gravite = new Vector2(accelY, -accelX);

        mondeJeu.getBille().applyGravite(gravite);
        mondeJeu.getMonde().step(Gdx.graphics.getDeltaTime(), 6, 2); // Ajoute un pas de temps dans le monde

        /* Ramassage de toutes les pastilles */
        mondeJeu.ramassePastilles();

        /* Changement du labyrinthe si manche gagnée */
        if(mondeJeu.isVictory())
            mondeJeu.changeLaby();
    }

    public void getEtat()
    {
        //etatJeu =
    }

    /**
     * Détruit le monde
     */
    @Override
    public void dispose()
    {
        listeAffichageMonde.dispose();
        listeAffichageTexte.dispose();
        //police.dispose();
    }
}
