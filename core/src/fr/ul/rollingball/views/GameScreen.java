package fr.ul.rollingball.views;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
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
    //private FitViewport viewport; // Zone d'affichage de la caméra
    private SpriteBatch listeAffichageMonde; // Liste d'affichage qui regroupe les différents éléments du fond à afficher et fait un envoi groupé à la carte graphique

    public GameScreen()
    {
        mondeJeu = new GameWorld(this);

        /* Création d'une caméra et d'une zone d'affichage */
        camera = new OrthographicCamera(GameWorld.LARGEUR, GameWorld.HAUTEUR);
        //viewport = new FitViewport(GameWorld.LARGEUR,  GameWorld.LARGEUR * ((float)Gdx.graphics.getHeight() / (float)Gdx.graphics.getWidth()), camera); // Largeur et hauteur de la zone d'affichage (=viewport) qu'on relie à la caméra, (la hauteur c'est un ratio de la fenêtre)
        //viewport.apply();
        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0); // On positionne le centre de la caméra sur le centre de la fenêtre
        camera.update();

        listeAffichageMonde = new SpriteBatch();
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
        //viewport.update(GameWorld.LARGEUR, GameWorld.LARGEUR * ((float) hauteur / (float) largeur));
        camera.viewportWidth = GameWorld.LARGEUR;
        camera.viewportHeight = GameWorld.LARGEUR * ((float) hauteur / (float) largeur);
        camera.update();
    }

    /**
     * Détruit le monde
     */
    @Override
    public void dispose()
    {
        listeAffichageMonde.dispose();
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
    }
}
