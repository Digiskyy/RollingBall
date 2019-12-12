package fr.ul.rollingball.dataFactories;


import com.badlogic.gdx.Audio;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;


/**
 * Banque de données des sons - singleton
 */
public class SoundFactory
{
    private static final SoundFactory INSTANCE = new SoundFactory();
    private Sound sonAlerte;
    private Music sonCollision;
    private Music sonPerdu;
    private Music sonGagne;
    private Sound sonPastilleScore;
    private Sound sonPastilleTemps;
    private Sound sonPastilleTaille;


    private SoundFactory()
    {
        sonAlerte = Gdx.audio.newSound(Gdx.files.internal("sounds/alerte.mp3")); // Crée un nouveau son (Sound est une interface)
        sonCollision = Gdx.audio.newMusic(Gdx.files.internal("sounds/collision.wav"));
        sonGagne = Gdx.audio.newMusic(Gdx.files.internal("sounds/victoire.mp3"));
        sonPerdu = Gdx.audio.newMusic(Gdx.files.internal("sounds/perte.mp3"));
        sonPastilleScore = Gdx.audio.newSound(Gdx.files.internal("sounds/pastille.wav"));
        sonPastilleTemps = Gdx.audio.newSound(Gdx.files.internal("sounds/ptemps.wav"));
        sonPastilleTaille = Gdx.audio.newSound(Gdx.files.internal("sounds/ptaille.wav"));
    }

    public static SoundFactory getInstance()
    {
        return INSTANCE;
    }

    public Sound getSonAlerte()
    {
        return sonAlerte;
    }

    public void joueSonCollision()
    {
        if(!sonCollision.isPlaying())
            sonCollision.play();
    }

    public void joueSonPerdu()
    {
        if(!sonPerdu.isPlaying())
            sonPerdu.play();
    }

    public void joueSonGagne()
    {
        if(!sonGagne.isPlaying())
            sonGagne.play();
    }

    public Sound getSonPastilleScore()
    {
        return sonPastilleScore;
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
