package fr.ul.rollingball.models;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.TextureData;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import fr.ul.rollingball.dataFactories.MaskFactories;
import fr.ul.rollingball.dataFactories.TextureFactory;
import fr.ul.rollingball.models.pastilles.Pastille;
import fr.ul.rollingball.models.pastilles.ScorePastille;
import fr.ul.rollingball.models.pastilles.TaillePastille;
import fr.ul.rollingball.models.pastilles.TempsPastille;

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
    private Texture imgMasque; // Image du masque
    private Texture imgLabyrinthe; // Image des murs
    private Texture imgFond; // Image du decor
    private Texture decorFinal;
    private Pixmap murs;
    private Pixmap decor;
    private Vector2 positionInitialeBille;
    private ArrayList<Body> bodiesMurs;
    private FixtureDef physiqueDef; // Propriétés physiques des murs
    private CircleShape circle; // Utile pour créer la FixtureDef

    public Maze(GameWorld mondeJeu)
    {
        this.mondeJeu = mondeJeu;
        numLabyrinthe = 0;
        bodiesMurs = new ArrayList<>();
        positionInitialeBille = new Vector2();
        
        imgLabyrinthe = TextureFactory.getInstance().getImgMur();
        imgFond = TextureFactory.getInstance().getImageFond();

        /* Propriétés physiques d'un murs */
        physiqueDef = new FixtureDef();
        circle = new CircleShape();
        circle.setRadius(0.1f); // Pourquoi, je ne sais pas
        physiqueDef.shape = circle;
        physiqueDef.density = 1;
        physiqueDef.restitution = (float) 0.25; // Elasticité de l'objet
        physiqueDef.friction = 0;
        //circle.dispose(); // On n'a plus besoin de la forme, on la détruit
    }

    public Vector2 getPositionInitialeBille()
    {
        return positionInitialeBille;
    }

    /**
     * Détruit les murs précédents et lit le masque du labyrinthe courant
     * @param pastilles listes des pastilles du labyrinthe courant
     */
    public void loadLaby(ArrayList<Pastille> pastilles)
    {
        /* Destruction des murs précédents */
        Iterator<Body> it = bodiesMurs.iterator();
        while(it.hasNext())
        {
            Body murs = it.next();
            mondeJeu.getMonde().destroyBody(murs);
        }

        /* Choisit le masque correspondant au labyrinthe courant */
        imgMasque = MaskFactories.getInstance().getMasqueLaby(numLabyrinthe);

        /* Lecture du masque */
        readObjects(pastilles);

        /* Construction de la texture composé du décor final (fond + murs) */
        buildTextureLaby();
    }

    /**
     * Obtenir les positions de chaque élément du monde
     * @param pastilles listes des pastilles du labyrinthe courant
     */
    private void readObjects(ArrayList<Pastille> pastilles)
    {
        int couleurPixel;

        /* Créer la pixmap du masque à partir de son image */
        masque = creerPixmap(imgMasque);

        masque.setColor(Color.WHITE); // Met la couleur à blanc pour les prochaines opérations de dessin

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
                        if(isToBeBuilt(masque, i, j)) // Si le pixel doit avoir un body = s'il est sur la frontière du murs
                            bodiesMurs.add(creerBodyMur(convertitCoordonnees(i, GameWorld.LARGEUR), convertitCoordonnees(j, GameWorld.HAUTEUR))); // Ajoute le body correspondant au pixel
                        break;

                    case 100: // Position initiale de la bille
                        positionInitialeBille.set(convertitCoordonnees(i, GameWorld.LARGEUR), convertitCoordonnees(j, GameWorld.HAUTEUR));
                        break;

                    case 128: // Pastille score
                        pastilles.add(new ScorePastille(mondeJeu.getMonde(), new Vector2(convertitCoordonnees(i, GameWorld.LARGEUR), convertitCoordonnees(j, GameWorld.HAUTEUR)))); // Ajoute la pastille dans la liste des pastilles
                        masque.fillCircle(i, j, 10); // Efface les autres pixels colorés qui forment cette pastille pour ne pas recréer d'autres pastilles à cet endroit en dessinant un cercle blanc
                        break;

                    case 200: // Pastille taille
                        pastilles.add(new TaillePastille(mondeJeu.getMonde(), new Vector2(convertitCoordonnees(i, GameWorld.LARGEUR),  convertitCoordonnees(j, GameWorld.HAUTEUR))));
                        masque.fillCircle(i, j, 10);
                        break;

                    case 225: // Pastille temps
                        pastilles.add(new TempsPastille(mondeJeu.getMonde(), new Vector2(convertitCoordonnees(i, GameWorld.LARGEUR), convertitCoordonnees(j, GameWorld.HAUTEUR))));
                        masque.fillCircle(i, j, 10);
                        break;

                    default: // Zone vide (couleurPixel = 255 = blanc)
                        break;
                }
            }
        }
    }

    /**
     * Convertit un point de la pixmap (1024*720) sur un des axes en point dans le repère du monde (80*60) en multipliant par le bon ratio de largeur et hauteur.
     * De plus, le pixmap obtenu inverse le masque sur l'axe des ordonnées, donc on doit prendre l'inverse pour l'axe des ordonnées.
     * @param x la valeur à convertir
     * @param axe l'axe où est cette valeur, accepte que 2 valeurs GameWorld.LARGEUR et GameWolrd.HAUTEUR
     * @return la valeur de x converti pour le repère du monde
     */
    private float convertitCoordonnees(float x, int axe)
    {
        if(axe == GameWorld.LARGEUR)
            x *= ((float)GameWorld.LARGEUR / imgMasque.getWidth());
        else if(axe == GameWorld.HAUTEUR)
            x = GameWorld.HAUTEUR - x * ((float)GameWorld.HAUTEUR / imgMasque.getHeight());
        else
            x = 0;
        return x;
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
     * Détermine si le pixel correspondant à un murs doit avoir un body physique dans le monde ou pas.
     * Seuls les pixels à la frontière des murs auront un body, c'est-à-dire seuls ceux qui ont 4 pixels voisins correspondant à un murs (couleur noire aussi).
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
     * Créer un body à la position passée en paramètre correspondant à un pixel de la limite extérieur du murs
     * @param x position en abscisse du pixel
     * @param y position en ordonnée du pixel
     * @return le body créé
     */
    private Body creerBodyMur(float x, float y)
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
     * Construit l'image du décor final en remplaçant les pixels du fond par les pixels des murs (connus grâce au masque)
     */
    private void buildTextureLaby()
    {
        int couleurPixel;
        Color couleurPixelPiste;

        /* Créer les pixmap à partir des textures */
        murs = creerPixmap(imgLabyrinthe);
        decor = creerPixmap(imgFond);

        /* Parcours de l'image du masque */
        for(int x = 0; x < masque.getWidth(); x++)
        {
            for(int y = 0; y < masque.getHeight(); y++)
            {
                couleurPixel = masque.getPixel(x, y) & 255;

                if(couleurPixel == 0) // C'est un mur
                    decor.drawPixel(x, y, murs.getPixel(x, y)); // Remplace la couleur du pixel du décor par la couleur du murs / 4 pour l'assombrir
                else
                {
                    couleurPixelPiste = new Color(decor.getPixel(x, y));
                    couleurPixelPiste.r *= 0.25;
                    couleurPixelPiste.g *= 0.25;
                    couleurPixelPiste.b *= 0.25;
                    decor.setColor(couleurPixelPiste);
                    decor.drawPixel(x, y); // On assombrit la couleur des pixels qui forment le fond de la piste
                }
            }
        }

        /* Transforme la pixmap du décor en une texture */
        decorFinal = new Texture(decor);

        /* On détruit les pixmaps temporaires */
        murs.dispose();
        decor.dispose();
    }

    /**
     * Met à jour le numéro du prochain labyrinthe
     */
    public void nextLaby()
    {
        numLabyrinthe++;
    }

    /**
     * Ajoute dans la liste d'affichage les éléments du monde
     * @param affMonde liste d'affichage du monde
     */
    public void draw(SpriteBatch affMonde)
    {
        affMonde.draw(decorFinal, 0, 0, GameWorld.LARGEUR, GameWorld.HAUTEUR); // Paramètres 0 et 0 définissent le point d'origine de l'image, en bas à gauche
    }

    /**
     * Détruit les images internes du labyrinthe
     */
    public void dispose()
    {
        masque.dispose();
        imgMasque.dispose();
        imgLabyrinthe.dispose();
        circle.dispose();
    }
}
