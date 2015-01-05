package de.scouthero.util;


public class Defs {
	public static enum AccountTyp {
		VEREIN(1),
		SPIELER(2);
		
		private int value;
		private AccountTyp(int value) {
			this.value = value;
		}
		
		public int value() {
			return this.value;
		}
	}
	
	public static enum MessageTyp {
		TRANSFER_ANFRAGE(1);
		
		private int value;
		private MessageTyp(int value) {
			this.value = value;
		}
		
		public int value() {
			return this.value;
		}
		
		public MessageTyp getTyp(int value) {
			if (value == 1) {
				return TRANSFER_ANFRAGE;
			}
			return null;
		}
	}

	public static final String[] ALTERSKLASSEN = {"U10", "U14", "U16", "U19", "U23", "Ü30", "Ü55"};
	public static final String[] LEISTUNGSKLASSEN = {"Kreisklasse", "Kreisliga", "Kreisoberliga", "Landesklasse", "Landesliga", "Bezirksklasse", "Bezirksliga", "Oberliga", "Regionalliga"};
}
