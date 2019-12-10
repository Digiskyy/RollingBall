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

    public Texture getMasqueLaby(int idLaby)
    {
        Texture masque;
        switch(idLaby)
        {
            case 0:
                masque = masqueLaby0;
                break;

            case 1:
                masque = masqueLaby1;
                break;

            case 2:
                masque = masqueLaby2;
                break;

            case 3:
                masque = masqueLaby3;
                break;

            case 4:
                masque = masqueLaby4;
                break;

            case 5:
                masque = masqueLaby5;
                break;

            default:
                masque = null;
                break;
        }

        return masque;
    }
}
