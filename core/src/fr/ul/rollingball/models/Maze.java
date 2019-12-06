package fr.ul.rollingball.models;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.TextureData;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import fr.ul.rollingball.dataFactories.MaskFactories;
import fr.ul.rollingball.dataFactories.TextureFactory;

import java.util.ArrayList;
import java.util.Iterator;


/**
 * Représente le labyrinthe constitué de murs créés à partir d'un masque et qui fera rebondir la bille.
 */
public class Maze
{
    private int numLabyrinthe;
    private GameWorld mondeJeu;
    private Pixmap masque;
    private Texture imgLabyrinthe;
    private Texture imgMasque;
    private Vector2 positionInitialeBille;
    private ArrayList<Body> bodiesMurs;
    private FixtureDef physiqueDef; // Propriétés physiques d'un mur

    public Maze(GameWorld mondeJeu)
    {
        this.mondeJeu = mondeJeu;
        imgLabyrinthe = TextureFactory.getInstance().getImgMur();
        bodiesMurs = new ArrayList<>();
        positionInitialeBille = new Vector2();

        /* Propriétés physiques d'un mur */
        physiqueDef = new FixtureDef();
        CircleShape circle = new CircleShape();
        circle.setRadius(0.1f); // Pourquoi, je ne sais pas
        physiqueDef.shape = circle;
        physiqueDef.density = 1;
        physiqueDef.restitution = (float) 0.25; // Elasticité de l'objet
        physiqueDef.friction = 0;
        circle.dispose(); // On n'a plus besoin de la forme, on la détruit
    }

    public Texture getImgLabyrinthe()
    {
        return imgLabyrinthe;
    }

    public void setImgLabyrinthe(Texture imgLabyrinthe)
    {
        this.imgLabyrinthe = imgLabyrinthe;
    }

    public Vector2 getPositionInitialeBille()
    {
        return positionInitialeBille;
    }

    public void setPositionInitialeBille(Vector2 positionInitialeBille)
    {
        this.positionInitialeBille = positionInitialeBille;
    }

    /**
     * Détruit les murs précédents et lit le masque du labyrinthe courant
     * @param pastilles listes des pastilles du labyrinthe courant
     */
    public void loadLaby(ArrayList<Pastille> pastilles)
    {
        /* Destruction des murs précédents */
        /*
        dispose();
        Iterator<Body> it = pixelsMurs.iterator();
        while(it.hasNext())
        {
            Body mur = it.next();
            mondeJeu.getMonde().destroyBody(mur);
        }*/

        /* Choisit le masque correspondant au labyrinthe courant */
        imgMasque = MaskFactories.getInstance().getMasqueLaby0();

        /* Lecture du masque */
        readObjects(pastilles);
    }

    /**
     * Obtenir les position de chaque élément du monde
     * @param pastilles listes des pastilles du labyrinthe courant
     */
    private void readObjects(ArrayList<Pastille> pastilles)
    {
        int couleurPixel;

        /* Créer la pixmap du masque à partir de son image */
        masque = creerPixmap(imgMasque);

        /* Parcours de la pixmap */
        for(int i = 0; i < masque.getWidth(); i++)
        {
            for(int j = 0; j < masque.getHeight(); j++)
            {
                couleurPixel = masque.getPixel(i, j) & 255; // &255 je ne sais pas pourquoi

                /* Créer l'élément associé à la couleur du pixel sur le masque */
                switch(couleurPixel)
                {
                    case 0: // Mur
                        if(isToBeBuilt(masque, i, j)) // Si le pixel doit avoir un body = s'il est sur la frontière du mur
                            bodiesMurs.add(creerBodyMur(i, j)); // Ajoute le body correspondant au pixel
                        break;

                    case 100: // Position initiale de la bille
                        positionInitialeBille.set((float)i, (float)j);
                        //TODO: Où crée-t-on la bille et comment lui donne-t-on sa position - cf Ball.setPosition()
                        break;

                    case 128: // Pastille score
                        pastilles.add(new ScorePastille(mondeJeu.getMonde(), new Vector2(i, j))); // Ajoute la pastille dans la liste des pastilles
                        masque.fillCircle(i, j, (int)(2 * Pastille.RAYON)); // Efface les autres pixels colorés qui forment cette pastille pour ne pas recréer d'autres pastilles à cet endroit en dessinant un cercle blanc
                        break;

                    case 200: // Pastille taille
                        pastilles.add(new TaillePastille(mondeJeu.getMonde(), new Vector2(i, j)));
                        masque.fillCircle(i, j, (int)(2 * Pastille.RAYON));
                        break;

                    case 225: // Pastille temps
                        pastilles.add(new TempsPastille(mondeJeu.getMonde(), new Vector2(i, j)));
                        masque.fillCircle(i, j, (int)(2 * Pastille.RAYON));
                        break;

                    default: // Zone vide (couleurPixel = 255 = blanc)
                        break;
                }
            }
        }
    }

    /**
     * Créer la pixmap à partir de la texture du masque
     * @param imgMasque texture du masque
     */
    private Pixmap creerPixmap(Texture imgMasque)
    {
        TextureData td = imgMasque.getTextureData();
        if(!td.isPrepared())
            td.prepare();
        return td.consumePixmap();
    }

    /**
     * Détermine si le pixel correspondant à un mur doit avoir un body physique dans le monde ou pas.
     * Seuls les pixels à la frontière des murs auront un body, c'est-à-dire seuls ceux qui ont 4 pixels voisins correspondant à un mur (couleur noire aussi).
     * @param masquePM le pixmap du masque
     * @param x position en abscisse du pixel
     * @param y position en ordonnée du pixel
     * @return vrai si on doit créer un body pour le pixel, faux sinon
     */
    private boolean isToBeBuilt(Pixmap masquePM, int x, int y)
    {
        boolean construction;

        // Si les 4 voisins du pixel (x, y) sont noirs, alors on ne construira pas de body
        construction = (masquePM.getPixel(x - 1, y) & 255) != 0
                || (masquePM.getPixel(x, y - 1) & 255) != 0
                || (masquePM.getPixel(x + 1, y) & 255) != 0
                || (masquePM.getPixel(x, y + 1) & 255) != 0;
        
        return construction;
    }

    /**
     * Créer un body à la position passée en paramètre correspondant à un pixel de la limite extérieur du mur
     * @param x position en abscisse du pixel
     * @param y position en ordonnée du pixel
     * @return le body créé
     */
    private Body creerBodyMur(int x, int y)
    {
        Body mur;

        /* Création du body */
        BodyDef defMur = new BodyDef();
        defMur.type = BodyDef.BodyType.StaticBody;
        defMur.position.set(x, y);
        mur = mondeJeu.getMonde().createBody(defMur); // On crée le body dans le monde

        mur.createFixture(physiqueDef); // On relie les propriétés physiques au body
        mur.setUserData("Mur"); // Servira pour les différencier des autres éléments lors des collisions

        return mur;
    }


    /**
     * Ajoute dans la liste d'affichage les éléments du monde
     * @param affMonde liste d'affichage du monde
     */
    public void draw(SpriteBatch affMonde)
    {

    }

    /**
     * Détruit les images internes du labyrinthe
     */
    public void dispose()
    {
        masque.dispose();
        imgMasque.dispose();
        imgLabyrinthe.dispose();
    }
}
