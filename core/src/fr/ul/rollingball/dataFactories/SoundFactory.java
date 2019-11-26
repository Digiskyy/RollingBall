package fr.ul.rollingball.dataFactories;


import com.badlogic.gdx.Audio;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;


/**
 * Banque de données des sons - singleton
 */
public class SoundFactory
{
    private static final SoundFactory INSTANCE = new SoundFactory();
    private Sound sonAlerte;
    private Sound sonCollision;
    private Sound sonPerdu;
    private Sound sonGagne;
    private Sound sonPastilleNormale;
    private Sound sonPastilleTemps;
    private Sound sonPastilleTaille;


    private SoundFactory()
    {
        sonAlerte = Gdx.audio.newSound(Gdx.files.internal("sounds/alerte.mp3")); // Crée un nouveau son (Sound est une interface)
        sonCollision = Gdx.audio.newSound(Gdx.files.internal("sounds/collision.wav"));
        sonGagne = Gdx.audio.newSound(Gdx.files.internal("sounds/victoire.mp3"));
        sonPerdu = Gdx.audio.newSound(Gdx.files.internal("sounds/perte.mp3"));
        sonPastilleNormale = Gdx.audio.newSound(Gdx.files.internal("sounds/pastille.wav"));
        sonPastilleTemps = Gdx.audio.newSound(Gdx.files.internal("sounds/ptemps.wav"));
        sonPastilleTaille = Gdx.audio.newSound(Gdx.files.internal("sounds/ptaille.wav"));
    }

    public static final SoundFactory getInstance()
    {
        return INSTANCE;
    }

    public Sound getSonAlerte()
    {
        return sonAlerte;
    }

    public Sound getSonCollision()
    {
        return sonCollision;
    }

    public Sound getSonPerdu()
    {
        return sonPerdu;
    }

    public Sound getSonGagne()
    {
        return sonGagne;
    }

    public Sound getSonPastilleNormale()
    {
        return sonPastilleNormale;
    }

    public Sound getSonPastilleTemps()
    {
        return sonPastilleTemps;
    }

    public Sound getSonPastilleTaille()
    {
        return sonPastilleTaille;
    }
}
