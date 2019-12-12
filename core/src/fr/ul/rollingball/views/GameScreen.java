package fr.ul.rollingball.views;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.utils.Timer;
import fr.ul.rollingball.dataFactories.SoundFactory;
import fr.ul.rollingball.models.GameState;
import fr.ul.rollingball.models.GameWorld;

import java.util.TimerTask;


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
        dureeIteration = 1000; // durée d'une itération en ms

        /* Création d'une caméra pour le monde et d'une zone d'affichage */
        camera = new OrthographicCamera(GameWorld.LARGEUR, GameWorld.HAUTEUR);
        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0); // On positionne le centre de la caméra sur le centre de la fenêtre
        camera.update();

        /* Création de la caméra pour le texte */
        cameraTexte = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()); // Camera de la taille de la fenêtre
        cameraTexte.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);

        /* Création desliste d'affichage */
        listeAffichageMonde = new SpriteBatch();
        listeAffichageTexte = new SpriteBatch();

        /* Création de la police */
        FreeTypeFontGenerator policeTTF = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Comic_Sans_MS_Bold.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter policeParam = new FreeTypeFontGenerator.FreeTypeFontParameter();
        policeParam.size = 40;
        policeParam.color = new Color(1f, 1f, 0f, 0.75f); // Jaune avec 75% de transparence
        policeParam.borderColor = Color.BLACK;
        policeParam.borderWidth = 3f;
        police = policeTTF.generateFont(policeParam);
        policeTTF.dispose(); // On n'a plus besoin du générateur une fois que la police est généré
    }

    /**
     * Met à jour l'affichage du monde et de ses éléments
     * @param delta
     */
    @Override
    public void render(float delta)
    {
        /* Si l'application est en état Arrêté, alors on quitte l'application */
        if(etatJeu.isARRETE())
        {
            Gdx.app.exit();
        }

        /* Gestion de la gravité */
        update();

        /* Lancement du timer */
        //Timer.schedule(tacheChgtLaby, 3f); // 1f délai, 1f : 1s avant chaque tick

        /* Mise à jour des caméras */
        camera.update();
        cameraTexte.update();
        listeAffichageMonde.setProjectionMatrix(camera.combined);
        listeAffichageTexte.setProjectionMatrix(cameraTexte.combined);

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // Efface l'écran qui est affiché (en réalité le buffer de couleur)

        /* Affichage du monde et de ses composants */
        listeAffichageMonde.begin(); // Prépare la liste à être dessinée
        mondeJeu.draw(listeAffichageMonde); // Affiche
        listeAffichageMonde.end();
        
        if(etatJeu.isEN_JEU())
        {
            /* Affichage de texte (temps et score) */
            listeAffichageTexte.begin();
            police.draw(listeAffichageTexte, "Temps : " + etatJeu.getTempsRestant(), Gdx.graphics.getWidth() / 4f, Gdx.graphics.getHeight() / 2f);
            police.draw(listeAffichageTexte, "Score : " + etatJeu.getScore(), 0, Gdx.graphics.getHeight() / 2f);
            listeAffichageTexte.end();
        }
        else if(etatJeu.isGAGNE())
        {
            SoundFactory.getInstance().joueSonGagne();
        }
        else if(etatJeu.isPERDU())
        {
            SoundFactory.getInstance().joueSonPerdu();
        }

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
        // System.out.println("Largeur : " + largeur + " | Hauteur : " + hauteur);
        // System.out.println("ViewPortWidth : " + camera.viewportWidth + " | ViewPortHeight : " + camera.viewportHeight);

        /* Caméra du monde */
        camera.viewportWidth = GameWorld.LARGEUR;
        camera.viewportHeight = GameWorld.HAUTEUR; // Tout le monde sera affiché mais les objets seront déformés
        // GameWorld.LARGEUR, GameWorld.LARGEUR * ((float) hauteur / (float) largeur) : On calcule la hauteur de la zone d'affichage pour que le ratio (hauteur/largeur) soit égal à celui du monde (hautMonde / largeurMonde) et ne déforme pas les objets
        camera.update();

        /* Caméra du texte et intermède */
        cameraTexte.viewportWidth = largeur;
        cameraTexte.viewportHeight = hauteur;
        cameraTexte.update();
    }

    /**
     * Met à jour les informations sur le monde (gravité calculée depuis l'accéléromètre)
     */
    public void update()
    {
        /* Création de la tâche pour le timer du temps de chargement de l'image d'intro */
        tacheChgtLaby = new Timer.Task() {
            @Override
            public void run()
            {
                changeLaby();
            }
        };

        float accelX = Gdx.input.getAccelerometerX() * 5f;
        float accelY = Gdx.input.getAccelerometerY() * 5f;
        Vector2 gravite = new Vector2(accelY, -accelX);

        mondeJeu.getBille().applyGravite(gravite);
        mondeJeu.getMonde().step(Gdx.graphics.getDeltaTime(), 6, 2); // Ajoute un pas de temps dans le monde

        /* Ramassage de toutes les pastilles */
        mondeJeu.ramassePastilles();

        System.out.println("ECRAN JEU : " + mondeJeu.getEcranJeu());

        /* Changement du labyrinthe si manche gagnée */
        if(mondeJeu.isVictory())
        {
            etatJeu.setEtat(GameState.Etat.GAGNE);
            Timer.schedule(tacheChgtLaby, 3f);
        }
    }

    @Override
    /**
     * Dès que l'aplication se montre, la fonction show se lance et elle va mettre l'état EN_JEU
     */
    public void show()
    {
        super.show();
        etatJeu.setEtat(GameState.Etat.EN_JEU);
    }

    /**
     * Fonction qui sera appelée par la tâche pour le changement du labyrinthe
     */
    public void changeLaby()
    {
        mondeJeu.changeLaby();
        etatJeu.setEtat(GameState.Etat.EN_JEU);
    }

    /**
     * Récuère l'état du jeu
     * @return l'état du jeu (type Etat déclaré dans GameState)
     */
    public GameState.Etat getEtat()
    {
        return etatJeu.getEtat();
    }

    /**
     * Ajoute un nombre de secondes au temps restant
     * @param nbSecondes que l'on doit ajouter
     */
    public void ajouterSecondes(int nbSecondes)
    {
        etatJeu.ajouterSecondesTempsRestant(nbSecondes);
    }

    /**
     * Ajoute un nombre de secondes au temps restant
     * @param points que l'on doit ajouter
     */
    public void ajouterScore(int points)
    {
        etatJeu.ajouterPointsScores(points);
    }

    public void incrementerPastilleScore()
    {
        System.out.println("++++++++++++++++++++++++++++++11111111111111111111111111111111111111");
        etatJeu.incrementerScore();
    }

    /**
     * Détruit le monde
     */
    @Override
    public void dispose()
    {
        listeAffichageMonde.dispose();
        listeAffichageTexte.dispose();
        police.dispose();
    }
}
