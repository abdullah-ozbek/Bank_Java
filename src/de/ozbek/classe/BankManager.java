package de.ozbek.classe;

import java.util.Random;

public class BankManager {
	
	/**
	 * Der Vorname des Manager
	 */
	private String vorname;
	
	/**
	 * Der Nachname Manager
	 */
	private String nachname;
	
	/**
	 * Der Usernummer des Manager
	 * Als Primärschlüssel in der Datenbank verfügbar
	 */
	private int userNummer;
	
	/**
	 * Der Passwort des Manager
	 * wird beim Anlegen eines neuen Manager zufällig bestimmt.
	 */
	private int passwort;	
	
	
	/**Konstruktor*/
	public BankManager() {
	}
	
	/**
	 * Es nimmt den Vor- und Nachnamen als Parameter.
	 * 
	 * Das Passwort wird zufällig generiert, wenn das erste Objekt erstellt wird
	 * @param vorname
	 * @param nachname
	 */
	public BankManager(String vorname, String nachname) {
		this.vorname = vorname;
		this.nachname = nachname;
		Random random = new Random();
		passwort = 1000 +  random.nextInt(8999); //Ein 4-stelliges Passwort wird generiert

	}

	//Getter und Setter-Methode

	/**
	 * @return the vorname
	 */
	public String getVorname() {
		return vorname;
	}

	/**
	 * @param vorname the vorname to set
	 */
	public void setVorname(String vorname) {
		this.vorname = vorname;
	}

	/**
	 * @return the nachname
	 */
	public String getNachname() {
		return nachname;
	}

	/**
	 * @param nachname the nachname to set
	 */
	public void setNachname(String nachname) {
		this.nachname = nachname;
	}

	/**
	 * @return the kundenNummer
	 */
	public int getUsernNummer() {
		return userNummer;
	}

	/**
	 * @param kundenNummer the kundenNummer to set
	 */
	public void setUserNummer(int userNummer) {
		this.userNummer = userNummer;
	}

	/**
	 * @return the passwort
	 */
	public int getPasswort() {
		return passwort;
	}

	/**
	 * @param passwort the passwort to set
	 */
	public void setPasswort(int passwort) {
		this.passwort = passwort;
	}

	@Override
	public String toString() {
		return "BankManager [vorname=" + vorname + ", nachname=" + nachname + ", userNummer=" + userNummer + "]";
	}
	

}
