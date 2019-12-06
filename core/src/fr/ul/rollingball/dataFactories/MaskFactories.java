package fr.ul.rollingball.dataFactories;

import com.badlogic.gdx.graphics.Texture;

/**
 * Banque de données des masques du labyrinthe - singleton
 */
public class MaskFactories
{
    private static final MaskFactories INSTANCE = new MaskFactories();
    private Texture masqueLaby0;
    private Texture masqueLaby1;
    private Texture masqueLaby2;
    private Texture masqueLaby3;
    private Texture masqueLaby4;
    private Texture masqueLaby5;

    private MaskFactories()
    {
        masqueLaby0 = new Texture("images/Laby0.png");
        masqueLaby1 = new Texture("images/Laby1.png");
        masqueLaby2 = new Texture("images/Laby2.png");
        masqueLaby3 = new Texture("images/Laby3.png");
        masqueLaby4 = new Texture("images/Laby4.png");
        masqueLaby5 = new Texture("images/Laby5.png");
    }

    public static MaskFactories getInstance()
    {
        return INSTANCE;
    }

    public Texture getMasqueLaby0()
    {
        return masqueLaby0;
    }

    public Texture getMasqueLaby1()
    {
        return masqueLaby1;
    }

    public Texture getMasqueLaby2()
    {
        return masqueLaby2;
    }

    public Texture getMasqueLaby3()
    {
        return masqueLaby3;
    }

    public Texture getMasqueLaby4()
    {
        return masqueLaby4;
    }

    public Texture getMasqueLaby5()
    {
        return masqueLaby5;
    }
}
