package fr.ul.rollingball.dataFactories;

import com.badlogic.gdx.graphics.Texture;

/**
 * Banque de données des textures - singleton
 */

public class TextureFactory
{
    private static TextureFactory INSTANCE = new TextureFactory();
    private static Texture imgIntro;
    private static Texture imgFond;
    private static Texture imgBall2D;
    private static Texture imgMur;
    private static Texture imgPerdu;
    private static Texture imgGagne;
    private static Texture imgPastilleScore;
    private static Texture imgPastilleTemps;
    private static Texture imgPastilleTaille;

    private TextureFactory()
    {
        imgIntro = new Texture("images/Intro.jpg");
        imgFond = new Texture("images/Piste.jpg");
        imgBall2D = new Texture("images/Bille2D.png");
        imgMur = new Texture("images/Murs.jpg");
        imgPerdu = new Texture("images/Perte.bmp");
        imgGagne = new Texture("images/Bravo.bmp");
        imgPastilleScore = new Texture("images/pastilleNormale.bmp");
        imgPastilleTemps = new Texture("images/pastilleTemps.bmp");
        imgPastilleTaille = new Texture("images/pastilleTaille.bmp");
        
    }

    public static final TextureFactory getInstance()
    {
        return INSTANCE;
    }

    public final Texture getImageIntro()
    {
        return imgIntro;
    }

    public final Texture getImageFond()
    {
        return imgFond;
    }

    public final Texture getImageBall2D()
    {
        return imgBall2D;
    }

    public final Texture getImgMur()
    {
        return imgMur;
    }

    public final Texture getImgPerdu()
    {
        return imgPerdu;
    }

    public final Texture getImgGagne()
    {
        return imgGagne;
    }

    public final Texture getImgPastilleScore()
    {
        return imgPastilleScore;
    }

    public final Texture getImgPastilleTemps()
    {
        return imgPastilleTemps;
    }

    public final Texture getImgPastilleTaille()
    {
        return imgPastilleTaille;
    }
}
