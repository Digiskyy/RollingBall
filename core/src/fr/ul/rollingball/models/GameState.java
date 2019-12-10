package fr.ul.rollingball.models;

import com.badlogic.gdx.utils.Timer;
import fr.ul.rollingball.dataFactories.SoundFactory;

/**
 * Gère l'état du jeu (en jeu, perdu, gagne, ...), le temps en jeu, le score, les niveaux du jeu
 */
public class GameState
{
    public static int TEMPS_DEPART;
    private int tempsRestant;
    private int score;
    public enum Etat
    {
        EN_JEU, ARRETE, GAGNE, PERDU
    }
    Etat etat;
    private int pastilleScoresRamassees;
    private Timer.Task decompte;

    public GameState()
    {
        etat = Etat.ARRETE;
        score = 0;
        pastilleScoresRamassees = 0;
        tempsRestant = TEMPS_DEPART;

        /* Création de la tâche pour le timer du temps de chargement de l'image d'intro */
        decompte = new Timer.Task() {
            @Override // Surcharge de la méthode abstraite run de la class Timer.Task
            public void run() // On met les instructions qu'on doit faire à intervalle de temps régulier
            {
                countDown();
            }
        };

        /* Lancement du timer */
        Timer.schedule(decompte, 1f, 1f); // 1f délai, 1f : 1s avant chaque tick
    }

    public boolean isARRETE()
    {
        return etat == Etat.ARRETE;
    }

    public boolean isEN_JEU()
    {
        return etat == Etat.EN_JEU;
    }

    public boolean isGAGNE()
    {
        return etat == Etat.GAGNE;
    }

    public boolean isPERDU()
    {
        return etat == Etat.PERDU;
    }

    public void setState(Etat nouvelEtat)
    {
        etat = nouvelEtat;
    }

    public int getTempsRestant()
    {
        return tempsRestant;
    }

    public int getScore()
    {
        return score;
    }

    public int getPastilleScoresRamassees()
    {
        return pastilleScoresRamassees;
    }

    public void countDown()
    {
        if(etat == Etat.EN_JEU)
        {
            tempsRestant--;

            if(tempsRestant < 10)
                SoundFactory.getInstance().getSonAlerte().play(0.1f);
        }
    }
}
