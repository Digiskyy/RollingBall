package fr.ul.rollingball.models;


import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

/**
 * Représente un obje de type pastille (normale, temps, taille) dans le monde
 */
public abstract class Pastille
{
    private static float RAYON = GameWorld.LARGEUR / 150;
    private Body bodyPastille;

    public Pastille(World monde, Vector2 position)
    {
        /* Création du body */
        BodyDef bodyDefP = new BodyDef();
        bodyDefP.type = BodyDef.BodyType.StaticBody;
        bodyDefP.position.set(position);
        CircleShape circle = new CircleShape();
        circle.setRadius(RAYON);
        bodyPastille = monde.createBody(bodyDefP); // On crée le body dans le monde

        /* Propriétés phyisiques */
        FixtureDef physiqueDef = new FixtureDef();
        physiqueDef.shape = circle;
        physiqueDef.density = 1;
        physiqueDef.restitution = (float) 0.25; // Elasticité de l'objet
        physiqueDef.friction = 0;
        bodyPastille.createFixture(physiqueDef); // On relie les propriétés physiques au body

        circle.dispose(); // On n'a plus besoin de la forme, on la détruit
    }

    public Vector2 getPosition()
    {
        return bodyPastille.getPosition();
    }

    /**
     * Affiche la pastille
     * @param affPastille la liste d'affichage concernant la pastille
     */
    public abstract void draw(SpriteBatch affPastille);

}
