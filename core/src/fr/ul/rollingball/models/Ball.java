package fr.ul.rollingball.models;


import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;


/**
 * Représente les billes de notre jeu
 */
public abstract class Ball
{
    protected static final int RAYON_GRAND = 20; // A CHANGER - A METTRE EN FONCTION DES DIMENSIONS DU MONDE
    protected static final int RAYON_PETIT = 10;
    protected int taille;
    protected Body bodyBall; // Représente le corps physique de la bille


    public Ball()
    {
        taille = Ball.RAYON_GRAND; // On initialise la taille de la bille avec le plus grand rayon
    }

    public Ball(World monde, Vector2 position)
    {
        this();

        /* Création du body */
        BodyDef bodyDef = new BodyDef(); // En premier, on créé un bodyDef
        bodyDef.type = BodyDef.BodyType.DynamicBody; // On lui dit que l'objet est dynamique (il bouge et réagit aux forces)
        bodyDef.position.set(position.x, position.y); // On définit la position du début de la bille
        bodyBall = monde.createBody(bodyDef); // On créé le body dans le monde en utilisant le bodydef
        CircleShape circle = new CircleShape(); // On créé une forme de cercle
        circle.setRadius((float) Ball.RAYON_GRAND); // On lui donne un rayon

        /* Propriété physique de la bille */
        FixtureDef physiqueDef = new FixtureDef();
        physiqueDef.shape = circle;
        physiqueDef.density = 1;
        physiqueDef.restitution = (float) 0.25; // Elasticité de l'objet
        physiqueDef.friction = 0;
        bodyBall.createFixture(physiqueDef); // On relie les propriétés physiques au body
        circle.dispose(); // On n'a plus besoin de la forme, on la détruit
    }

    public int getTaille()
    {
        return taille;
    }

    public Vector2 getPosition()
    {
        return bodyBall.getPosition();
    }

    public abstract void draw(SpriteBatch affBall);
}
