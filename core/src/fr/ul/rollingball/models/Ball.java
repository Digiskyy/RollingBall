package fr.ul.rollingball.models;


import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;


/**
 * Représente les billes de notre jeu
 *
 * Lien utile pour Box2D (body) et libGDx : https://github.com/libgdx/libgdx/wiki/Box2d
 */
public abstract class Ball
{
    protected static final float RAYON_GRAND = GameWorld.LARGEUR / 50;
    protected static final float RAYON_PETIT = GameWorld.LARGEUR / 100;
    protected float taille;
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
        CircleShape circle = new CircleShape(); // On créé une forme de cercle
        circle.setRadius(RAYON_GRAND); // On lui donne un diamètre
        bodyBall = monde.createBody(bodyDef); // On créé le body dans le monde en utilisant le bodydef

        /* Propriété physique de la bille */
        FixtureDef physiqueDef = new FixtureDef();
        physiqueDef.shape = circle;
        physiqueDef.density = 1;
        physiqueDef.restitution = (float) 0.25; // Elasticité de l'objet
        physiqueDef.friction = 0;
        bodyBall.createFixture(physiqueDef); // On relie les propriétés physiques au body

        circle.dispose(); // On n'a plus besoin de la forme, on la détruit
    }

    public Vector2 getPosition()
    {
        return bodyBall.getPosition();
    }

    /**
     * Applique la gravité à la bille
     * @param gravite la force à appliquer
     */
    public void applyGravite(Vector2 gravite)
    {
        //bodyBall.applyForceToCenter(gravite, true); // Applique la gravité à la boule - On peut "endormir" l'objet pour ne pas faire les calculs dessus pour améliorer les performances si l'objet n'est pas mobile (forces stables)
        bodyBall.setLinearVelocity(gravite); // Fonctionne mieux
    }

    /**
     * Affiche la bille
     * @param affMonde la liste d'affichage que l'on affichera où l'on rajoute la texture de la bille
     */
    public abstract void draw(SpriteBatch affMonde);
}
