package de.ozbek.classe;


/**
 * jeder Mitarbeiter ist auch ein Kunde
 * wird vom Kunde-Objekt geerbt
 * @author Alfa
 *
 */
public class Mitarbeiter extends Kunde {
	
	/**
	 * Anders als der Kunde hat der Mitarbeiter ein Gehalt
	 */
	private double lohn;
	
	/**
	 * Konstruktor
	 */
	public Mitarbeiter() {
	}

	/**
	 * Hat den gleichen Konstruktor wie die Superklasse
	 * @param vorname
	 * @param nachname
	 */
	public Mitarbeiter(String vorname, String nachname) {
		super(vorname, nachname);
	}
	
	/**
	 * @return the lohn
	 */
	public double getLohn() {
		return lohn;
	}

	/**
	 * @param lohn the lohn to set
	 */
	public void setLohn(double lohn) {
		this.lohn = lohn;
	}

	/**
	 * Die toString-Methode wurde umgeschrieben, da der Mitarbeiter keine kreditSchulden hat.
	 * hat auch eine Gehaltsvariable(lohn)
	 */
	@Override
	public String toString() {
		return "Mitarbeiter [vorname=" + super.getVorname() + ", nachname=" + super.getNachname() + ", mitarbeiterNummer=" + super.getKundenNummer()
		                + ", lohn=" + this.getLohn()+ ", kontoStand=" + super.getKontoStand() +"]";
	}

}
