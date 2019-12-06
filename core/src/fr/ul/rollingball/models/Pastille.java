package fr.ul.rollingball.models;


import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

/**
 * Représente un obje de type pastille (normale, temps, taille) dans le monde
 */
public abstract class Pastille
{
    public static float RAYON = (float)GameWorld.LARGEUR / 150;
    protected Body bodyPastille;
    protected boolean isPicked; // Si la pastille est ramassée ou pas

    public Pastille(World monde, Vector2 position)
    {
        isPicked = false;

        /* Création du body */
        BodyDef bodyDefP = new BodyDef();
        bodyDefP.type = BodyDef.BodyType.StaticBody;
        bodyDefP.position.set(position.x, position.y);
        bodyPastille = monde.createBody(bodyDefP); // On crée le body dans le monde

        /* Propriétés physiques */
        FixtureDef physiqueDef = new FixtureDef();
        CircleShape circle = new CircleShape();
        circle.setRadius(RAYON);
        physiqueDef.shape = circle;
        physiqueDef.density = 1;
        physiqueDef.restitution = (float) 0.25; // Elasticité de l'objet
        physiqueDef.friction = 0;
        physiqueDef.isSensor = true; // A sensor shape collects contact information but never generates a collision response.
        bodyPastille.createFixture(physiqueDef); // On relie les propriétés physiques au body

        circle.dispose(); // On n'a plus besoin de la forme, on la détruit
    }

    public Vector2 getPosition()
    {
        return bodyPastille.getPosition();
    }

    public Body getBodyPastille()
    {
        return bodyPastille;
    }

    public boolean isPicked()
    {
        return isPicked;
    }

    public void setPicked(boolean picked)
    {
        isPicked = picked;
    }

    /**
     * Affiche la pastille
     * @param affMonde la liste d'affichage auquelle on ajoute la pastille à afficher
     */
    public abstract void draw(SpriteBatch affMonde);

    /**
     * Applique l'effet de la pastille à la bille qui l'a rammassée
     */
    public abstract void effect();

}
