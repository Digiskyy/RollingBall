package fr.ul.rollingball.models;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import fr.ul.rollingball.dataFactories.SoundFactory;
import fr.ul.rollingball.models.pastilles.Pastille;
import fr.ul.rollingball.views.GameScreen;

import java.util.ArrayList;
import java.util.Iterator;


/**
 * Gère le monde physique du jeu, contient tous ses élements.
 *
 * lien utile pour les body et le world avec libgdx et box2d : https://github.com/libgdx/libgdx/wiki/Box2d
 */
public class GameWorld
{
    public static final int LARGEUR = 80;
    public static final int HAUTEUR = 60;
    private GameScreen ecranJeu;
    private World monde;
    private Maze labyrinthe;
    private Ball bille;
    private Vector2 positionBille;
    private ArrayList<Pastille> pastilles;


    public GameWorld(GameScreen ecranJeu)
    {
        this.ecranJeu = ecranJeu;

        /* Création du monde */
        monde = new World(new Vector2(0, 0), true); // Le paramètre true permet d'améliorer les performances en ne simulant pas les corps inactifs.

        /* Création des pastilles */
        pastilles = new ArrayList<>();

        /* Création du labyrinthe */
        labyrinthe = new Maze(this);

        /* Chargement du labyrinthe */
        labyrinthe.loadLaby(pastilles);

        /* Création de la bille et position au milieu */
        positionBille = labyrinthe.getPositionInitialeBille();
        bille = new Ball2D(monde, positionBille);

        /* Gestion des collisions */
        monde.setContactListener(new ContactListener()
        {
            @Override
            /**
             * Action déclenchée au début du contact
             */
            public void beginContact(Contact contact)
            {
                Body bodyAutre;

                /* Identifier la bille entre les deux objets en collisions */
                if(contact.getFixtureA().getBody().getUserData().getClass().getSimpleName().equals(Ball2D.class.getSimpleName()))
                {
                    bodyAutre = contact.getFixtureB().getBody();
                }
                else
                {
                    bodyAutre = contact.getFixtureA().getBody();
                }

                /* Vérification si la bille est en contact avec une pastille */
                if(bodyAutre.getUserData().getClass().getSuperclass().getSimpleName().equals(Pastille.class.getSimpleName()))
                {
                    ((Pastille)bodyAutre.getUserData()).setPicked(true);
                }
                else // c'est un mur
                {
                    SoundFactory.getInstance().joueSonCollision();
                }
            }

            @Override
            public void endContact(Contact contact)
            { }

            @Override
            public void preSolve(Contact contact, Manifold oldManifold)
            { }

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse)
            { }
        });
        monde.step(Gdx.graphics.getDeltaTime(), 8, 3);
    }

    public Ball getBille()
    {
        return bille;
    }

    public World getMonde()
    {
        return monde;
    }

    public GameScreen getEcranJeu()
    {
        return ecranJeu;
    }

    /**
     * Pour chaque pastille ramassée, on applique son effet, on détruit le corps physique et on la supprime de la liste.
     */
    public void ramassePastilles()
    {
        Iterator<Pastille> it = pastilles.iterator();
        while(it.hasNext())
        {
            Pastille pastille = it.next();
            if(pastille.isPicked())
            {
                pastille.effect();
                monde.destroyBody(pastille.getBodyPastille());
                it.remove();
            }
        }
    }

    /**
     * Change le labyrinthe
     */
    public void changeLaby()
    {
        /* Destruction des murs */
        labyrinthe.detruireBodiesMur();

        /* Destruction des pastilles */
        for(Pastille pastille : pastilles)
        {
            monde.destroyBody(pastille.getBodyPastille());
        }

        /* Vidage de la liste */
        pastilles.clear();

        /* Mise à jour du numéro du labyrinthe */
        labyrinthe.nextLaby();

        /* Chargement du nouveau labyrinthe */
        labyrinthe.loadLaby(pastilles);

        /* Nouvelle position de la bille */
        positionBille = labyrinthe.getPositionInitialeBille(); // Récupération de la nouvelle position
        bille.getBodyBall().setTransform(positionBille, 0);

        /* Vitesse à 0 */
        bille.applyGravite(new Vector2(0f, 0f));
    }

    /**
     * Permet de dire si la manche est gagnée ou pas (si la boule est sortie du monde)
     */
    public boolean isVictory()
    {
        boolean victoire = false;

        if(bille.isOut())
            victoire = true;

        return victoire;
    }

    /**
     * Affiche le monde et ses élements
     * @param listeAffichageMonde liste d'affichage du monde et de ses éléments (billes, pastilles, murs)
     */
    public void draw(SpriteBatch listeAffichageMonde)
    {
        /* Affichage du décor (fond + mur) */
        labyrinthe.draw(listeAffichageMonde);

        /* Affichage des pastilles de type Score */
        for(Pastille pastille : pastilles)
        {
            pastille.draw(listeAffichageMonde);
        }

        /* Affichage de l'image de la bille */
        bille.draw(listeAffichageMonde);
    }
}
