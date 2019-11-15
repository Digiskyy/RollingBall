package fr.ul.rollingball.models;


import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.FixtureDef;


/**
 * Représente les billes de notre jeu
 */
public abstract class Ball
{
    protected static final int rayonGrand = 40; // A CHANGER - A METTRE EN FONCTION DES DIMENSIONS DU MONDE
    protected static final int rayonPetit = 10;
    protected int taille;
    protected Body bodyBall; // Représente le corps physique de la bille
    protected FixtureDef physique; // Paramètres physique de la bille


    public Ball()
    {
        taille = Ball.rayonGrand; // On initialise la taille de la bille avec le plus grand rayon

        /* Propriété physique de la bille */
        physique = new FixtureDef();
        physique.density = 1;
        physique.restitution = (float) 0.25; // Elasticité de l'objet
        physique.friction = 0;
    }

    public int getTaille()
    {
        return taille;
    }

    public Vector2 getPosition()
    {
        return bodyBall.getPosition();
    }
}
