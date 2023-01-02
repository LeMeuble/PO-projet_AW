package main;

import main.menu.MenuManager;
import main.menu.model.FactoryActionMenu;
import main.menu.model.MainMenu;
import main.weather.Weather;
import main.weather.WeatherManager;

import java.io.IOException;
import java.util.Arrays;


public class Main {

	public static void main(String[] args) throws IOException, InterruptedException {

		try {

			MiniWars miniWars = new MiniWars();

		} catch (Exception e) {
			System.out.println("Erreur lors de l'execution du jeu : " + e.getMessage());
			e.printStackTrace();
		}

	}

}
