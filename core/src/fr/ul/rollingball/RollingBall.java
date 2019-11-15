package fr.ul.rollingball;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.utils.Timer;
import fr.ul.rollingball.views.GameScreen;
import fr.ul.rollingball.views.SplashScreen;


/**
 * Classe principale du jeu
 */
public class RollingBall extends Game
{
	private SplashScreen ecranIntro;
	private GameScreen ecranJeu;
	private Timer.Task chronoChargementIntro;

	@Override
	public void create()
	{
		ecranIntro = new SplashScreen();
		ecranJeu = new GameScreen();

		setScreen(ecranIntro); // Sélectionne l'écran d'intro (méthode dans Game)

		/* Création de la tâche pour le timer du temps de chargement de l'image d'intro */
		chronoChargementIntro = new Timer.Task() {
			@Override // Surcharge de la méthode abstraite run de la class Timer.Task
			public void run() // On met les instructions qu'on doit faire à intervalle de temps régulier
			{
				loadGameScreen();
			}
		};

		/* Lancement du timer Intro */
		Timer.schedule(chronoChargementIntro, 3f); // 3f : 3s avant chaque frame
	}
	
	@Override
	public void dispose()
	{
		/* Desctruction des écrans */
		ecranIntro.dispose();
		ecranJeu.dispose();
		//Timer.instance().stop();
	}

	public void loadGameScreen()
	{
		ecranIntro.dispose();
		setScreen(ecranJeu);

		/* Arrêt du chrono Intro et des appels à la tâche */
		Timer.instance().stop();
	}
}
