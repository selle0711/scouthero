package de.scouthero.util;


public class Defs {
	public static enum AccountTyp {
		VEREIN(1, "Vereinsaccount"),
		SPIELER(2, "Spieleraccount");
		
		private int value;
		private String typAsString;
		private AccountTyp(int value, String name) {
			this.value = value;
			this.typAsString = name;
		}
		
		public String typAsString() {
			return this.typAsString;
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
