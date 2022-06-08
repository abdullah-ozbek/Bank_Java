package de.ozbek.classe;

import java.util.Random;

/**
 * Die Attribute und Methoden des Kunde-Objekts werden angezeigt
 * @author Alfa
 *
 */
public class Kunde {
	
	/**
	 * Der Vorname des Kunden
	 */
	private String vorname;
	
	/**
	 * Der Nachname des Kunden
	 */
	private String nachname;
	
	/**
	 * Der Kundennummer des Kunden
	 * Als Primärschlüssel in der Datenbank verfügbar
	 */
	private int kundenNummer;
	
	/**
	 * Der Passwort des Kunden
	 * wird beim Anlegen eines neuen Kunden zufällig bestimmt.
	 * später kann der Kunde es selbst ändern
	 */
	private int passwort;
	
	/**
	 * jeder Kunde muss ein Konto haben
	 */
	private double kontoStand;
	
	/**
	 * Zeigt die aktuelle Kreditschuld des Kunden an
	 */
	private double kreditschulden;
	
	/**
	 * Zeigt die Transaktionen, die der Kunde auf seinem Konto getätigt hat
	 */
	private String transaktionInfos;
	
	/**Konstruktor*/
	public Kunde() {
	}
	
	/**
	 * Es nimmt den Vor- und Nachnamen als Parameter.
	 * 
	 * Das Passwort wird zufällig generiert, wenn das erste Kunde-Objekt erstellt wird 
	 * @param vorname
	 * @param nachname
	 */
	public Kunde(String vorname, String nachname) {
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
	public int getKundenNummer() {
		return kundenNummer;
	}

	/**
	 * @param kundenNummer the kundenNummer to set
	 */
	public void setKundenNummer(int kundenNummer) {
		this.kundenNummer = kundenNummer;
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

	/**
	 * @return the kontoStand
	 */
	public double getKontoStand() {
		return kontoStand;
	}


	/**
	 * @param kontoStand the kontoStand to set
	 */
	public void setKontoStand(double kontoStand) {
		this.kontoStand = kontoStand;
	}


	/**
	 * @return the kreditschulden
	 */
	public double getKreditschulden() {
		return kreditschulden;
	}


	/**
	 * @param kreditschulden the kreditschulden to set
	 */
	public void setKreditschulden(double kreditschulden) {
		this.kreditschulden = kreditschulden;
	}


	/**
	 * @return the transaktionInfos
	 */
	public String getTransaktionInfos() {
		return transaktionInfos;
	}

	/**
	 * @param transaktionInfos the transaktionInfos to set
	 */
	public void setTransaktionInfos(String transaktionInfos) {
		this.transaktionInfos = transaktionInfos;
	}

	@Override
	public String toString() {
		return "Kunde [vorname=" + vorname + ", nachname=" + nachname + ", kundenNummer=" + kundenNummer
				+ ", kontoStand=" + kontoStand + ", kreditschulden=" + kreditschulden + "]";
	}
	

}
