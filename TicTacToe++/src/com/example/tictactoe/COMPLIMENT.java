package com.example.tictactoe;

import java.util.HashMap;
import java.util.Map;

public class COMPLIMENT {

	private static Map<String, String> compliment;

	static {

		compliment = new HashMap<String, String>(6);
		compliment.put("dark_cell.png", "light_cell.png");
		compliment.put("light_cell.png", "dark_cell.png");
		compliment.put("dark_cross.png", "light_cross.png");
		compliment.put("light_cross.png", "dark_cross.png");
		compliment.put("dark_zero.png", "light_zero.png");
		compliment.put("light_zero.png", "dark_zero.png");
	}

	public static String getConjugate(String key) {
		return compliment.get(key);
	}
}
