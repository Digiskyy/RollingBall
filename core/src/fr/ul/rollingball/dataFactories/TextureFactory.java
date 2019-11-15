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

    private TextureFactory()
    {
        imgIntro = new Texture("images/Intro.jpg");
        imgFond = new Texture("images/Piste.jpg");
        imgBall2D = new Texture("images/Bille2D.png");
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
}
