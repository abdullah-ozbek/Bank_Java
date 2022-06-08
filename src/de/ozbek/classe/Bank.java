package de.ozbek.classe;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


/**
 * Es ist die Klasse, in der Banktransaktionen durchgeführt werden.
 * 
 * Hier können neue Kunden hinzugefügt werden.
 * 
 * Neue Mitarbeiter können übernommen werden.
 * 
 * dem Kunden kann Kredit gegeben werden
 * 
 * Informationen von Kunden und Mitarbeitern können als <Liste> aus der Datenbank entnommen werden.
 * 
 * @author Alfa
 *
 */
public class Bank extends BankAutomat{
	
	public static final String URL = "jdbc:mysql://127.0.0.1:3306/bank_db";
	public static final String USER = "root";
	public static final String PASSWORD = "";
	public static final String TABELLE_KUNDE = "kunden";
	public static final String TABELLE_MITARBEITER = "mitarbeiter";
	public static final String TABELLE_BANKMANAGER = "bankmanager";
	
	
	public Bank() {
	}
	
	/**
	 * Bei dieser Methode wird ein neues Kundenobjekt erstellt.
	 * und das erstellte Objekt wird in der Datenbank gespeichert
	 */
	
	public void neuKundenanlegen(Kunde neukunde) {
		String sqlPrep = "INSERT INTO " + TABELLE_KUNDE + " VALUES(NULL,?,?,?,?,0,' ')"; 
		try(Connection verbindung = DriverManager.getConnection(URL, USER, PASSWORD);
				PreparedStatement besserVerpackt = verbindung.prepareStatement(sqlPrep);){
			besserVerpackt.setString(1, neukunde.getVorname()); //"Füllt" des 1. Fragenzeichen
			besserVerpackt.setString(2, neukunde.getNachname());
			besserVerpackt.setInt(3, neukunde.getPasswort());
			besserVerpackt.setDouble(4, neukunde.getKontoStand());
			
			besserVerpackt.execute();
			
			String sqlFeuerSchluessel = "SELECT * FROM " +TABELLE_KUNDE + " ORDER BY kundenNummer DESC";
			ResultSet antwort = verbindung.createStatement().executeQuery(sqlFeuerSchluessel);
			antwort.next();
			neukunde.setKundenNummer(antwort.getInt(1));
			neukunde.setPasswort(antwort.getInt(4));
			
		}catch(SQLException ausnahme) {
			ausnahme.printStackTrace();
		}
	}
	
	/**
	 * Bei dieser Methode wird ein neues Mitarbeiterobjekt erstellt.
	 * und das erstellte Objekt wird in der Datenbank gespeichert
	 */
	public void neuMitarbeiternanlegen(Mitarbeiter neumitarbeiter) {
		String sqlPrep = "INSERT INTO " + TABELLE_MITARBEITER + " VALUES(NULL,?,?,?,?,?)"; 
		try(Connection verbindung = DriverManager.getConnection(URL, USER, PASSWORD);
				PreparedStatement besserVerpackt = verbindung.prepareStatement(sqlPrep);){
			besserVerpackt.setString(1, neumitarbeiter.getVorname()); //"Füllt" des 1. Fragenzeichen
			besserVerpackt.setString(2, neumitarbeiter.getNachname());
			besserVerpackt.setInt(3, neumitarbeiter.getPasswort());
			besserVerpackt.setDouble(4, neumitarbeiter.getKontoStand());
			besserVerpackt.setDouble(5, neumitarbeiter.getLohn());
			
			besserVerpackt.execute();
			
			String sqlFeuerSchluessel = "SELECT mitarbeiterNummer FROM " +TABELLE_MITARBEITER + " ORDER BY mitarbeiterNummer DESC";
			ResultSet antwort = verbindung.createStatement().executeQuery(sqlFeuerSchluessel);
			antwort.next();
			neumitarbeiter.setKundenNummer(antwort.getInt(1));
			
		}catch(SQLException ausnahme) {
			ausnahme.printStackTrace();
		}
	}
	
	/**
	 * Bei dieser Methode wird ein neues Bankmanagernobjekt erstellt.
	 * und das erstellte Objekt wird in der Datenbank gespeichert
	 */
	public void neuManagernanlegen(BankManager neumanager) {
		String sqlPrep = "INSERT INTO " + TABELLE_BANKMANAGER + " VALUES(NULL,?,?,?)"; 
		try(Connection verbindung = DriverManager.getConnection(URL, USER, PASSWORD);
				PreparedStatement besserVerpackt = verbindung.prepareStatement(sqlPrep);){
			besserVerpackt.setString(1, neumanager.getVorname()); //"Füllt" des 1. Fragenzeichen
			besserVerpackt.setString(2, neumanager.getNachname());
			besserVerpackt.setInt(3, neumanager.getPasswort());
			
			besserVerpackt.execute();
			
			String sqlFeuerSchluessel = "SELECT userNummer FROM " +TABELLE_BANKMANAGER + " ORDER BY userNummer DESC";
			ResultSet antwort = verbindung.createStatement().executeQuery(sqlFeuerSchluessel);
			antwort.next();
			neumanager.setUserNummer(antwort.getInt(1));
			
		}catch(SQLException ausnahme) {
			ausnahme.printStackTrace();
		}
	}
	
	/**
	 * Bank gibt einem Kunden einen Kredit
	 * 
	 * Zunächst wird geprüft, ob der gutzuschreibende Kunde in der Datenbank registriert ist. 
	 * 
	 * Die Kreditschuldenvariable für den Kunden wird aktualisiert
	 * 
	 * Zusätzlich wird eine Nachricht, die anzeigt, dass der Kunde einen Kredit erhalten hat, 
	 * im Transaktionsinformationsfeld(mit Betrag- und Datum-Info ) aufgezeichnet.
	 * @param kunde
	 * @param kredit
	 */
	public void kreditgeben(Kunde kunde, double kredit) {
		
		String nachricht = "Am " + LocalDate.now() + " hast du einen Kredit in Höhe von " + kredit + " Euro aufgenommen\n";
		
		try(Connection verbindung = DriverManager.getConnection(URL, USER, PASSWORD);
				Statement verpackung = verbindung.createStatement();){
			
			String sqlKreditnehmer = "SELECT * FROM " + TABELLE_KUNDE + " WHERE kundenNummer =" + kunde.getKundenNummer();
			String sqlAnzahl = "SELECT COUNT(kundenNummer) FROM " + TABELLE_KUNDE + " WHERE kundenNummer =" + kunde.getKundenNummer();
			
			ResultSet anzahl = verpackung.executeQuery(sqlAnzahl);
			anzahl.next();
			if(anzahl.getInt(1) == 1) {
				ResultSet kreditSchulden = verpackung.executeQuery(sqlKreditnehmer);
				kreditSchulden.next();
				kunde.setKreditschulden(kreditSchulden.getDouble("kreditSchulden")+ kredit);	
				kunde.setTransaktionInfos(kreditSchulden.getString("transaktionInfos") + nachricht);
				String sqlPrep =  "UPDATE " + TABELLE_KUNDE + " SET kreditSchulden=" + kunde.getKreditschulden()  + ", transaktionInfos='" + kunde.getTransaktionInfos() +
						"' WHERE kundenNummer=" +kunde.getKundenNummer();
				verpackung.executeUpdate(sqlPrep);
			}else {
				System.out.println("Gib bitte eine gültige Kundennummer ein.");
			}
		}catch (SQLException ausnahme) {
			ausnahme.printStackTrace();
		}
	}
	
	/**
	 * Gibt alle in der Datenbank registrierten Kunden zurück
	 * @return alleKunden
	 */
	public List<Kunde> gettAlleKunden() {	
		List<Kunde> alleKunden = new ArrayList<>();
		String sql= "SELECT * FROM " + TABELLE_KUNDE;
		try(Connection verbindung = DriverManager.getConnection(URL, USER, PASSWORD);
				Statement verpackung = verbindung.createStatement();){
				ResultSet kunden = verpackung.executeQuery(sql);
				while(kunden.next()) {
					Kunde kunde = new Kunde(kunden.getString("vorname"), kunden.getString("nachname"));
					kunde.setPasswort(kunden.getInt("passwort"));
					kunde.setKundenNummer(kunden.getInt("kundenNummer"));
					kunde.setKontoStand(kunden.getDouble("menge"));
					kunde.setKreditschulden(kunden.getDouble("kreditSchulden"));
					kunde.setTransaktionInfos(kunden.getString("transaktionInfos"));
					alleKunden.add(kunde);
				}
		}catch(SQLException ausnahme) {
			ausnahme.printStackTrace();
		}
		return alleKunden;	
	}
	
	/**
	 * ist die Methode, die die Mitarbeiterliste in der Datanbank zurückgibt
	 * @return
	 */
	public List<Mitarbeiter> gettAlleMitarbeiter() {	
		List<Mitarbeiter> alleMitarbeiter = new ArrayList<>();
		String sql= "SELECT * FROM " + TABELLE_MITARBEITER;
		try(Connection verbindung = DriverManager.getConnection(URL, USER, PASSWORD);
				Statement verpackung = verbindung.createStatement();){
				ResultSet mitarbeiter = verpackung.executeQuery(sql);
				while(mitarbeiter.next()) {
					Mitarbeiter arbeitnehmer = new Mitarbeiter(mitarbeiter.getString("vorname"), mitarbeiter.getString("nachname"));
					arbeitnehmer.setPasswort(mitarbeiter.getInt("passwort"));
					arbeitnehmer.setKundenNummer(mitarbeiter.getInt("mitarbeiterNummer"));
					arbeitnehmer.setKontoStand(mitarbeiter.getDouble("menge"));
					arbeitnehmer.setLohn(mitarbeiter.getDouble("lohn"));
					alleMitarbeiter.add(arbeitnehmer);
				}
		}catch(SQLException ausnahme) {
			ausnahme.printStackTrace();
		}
		return alleMitarbeiter;	
	}
	
	/**
	 * Liefert den Kunden der als Parameter angegebenen Kundennummer zurück
	 * @param kundenNummer
	 * @return
	 */
	public Kunde getEinKunde(int kundenNummer) {
		Kunde kunde = new Kunde();
		String sql= "SELECT * FROM " + TABELLE_KUNDE + " WHERE KundenNummer=" + kundenNummer;
		try(Connection verbindung = DriverManager.getConnection(URL, USER, PASSWORD);
				Statement verpackung = verbindung.createStatement();){
				ResultSet meinKunde = verpackung.executeQuery(sql);
				while(meinKunde.next()) {
					kunde.setVorname(meinKunde.getString("vorname"));
					kunde.setNachname(meinKunde.getString("nachname"));
					kunde.setKontoStand(meinKunde.getDouble("menge"));
					kunde.setKundenNummer(meinKunde.getInt("kundenNummer"));
					kunde.setKreditschulden(meinKunde.getDouble("kreditSchulden"));
					kunde.setPasswort(meinKunde.getInt("passwort"));
					kunde.setTransaktionInfos(meinKunde.getString("transaktionInfos"));
				}
		}catch(SQLException ausnahme) {
			ausnahme.printStackTrace();
		}	
		return kunde;
	}
	
	
	/**
	 * Es ist die Methode, bei der Geld auf das Bankkonto der Mitarbeiter in Höhe ihres Gehalts überwiesen wird.
	 * 
	 * In Gui zahlt der Bankmanager das Gehalt der Mitarbeiter über diese Methode
	 */
	public void gehaelterZahlen() {
		String sql= "SELECT * FROM " + TABELLE_MITARBEITER;
		try(Connection verbindung = DriverManager.getConnection(URL, USER, PASSWORD);
				Statement verpackung = verbindung.createStatement();){
				ResultSet mitarbeiter = verpackung.executeQuery(sql);
				while(mitarbeiter.next()) {
					double lohn = mitarbeiter.getDouble("lohn");
					double kontoStand = mitarbeiter.getDouble("menge");	
					int mitarbeiterNummer = mitarbeiter.getInt("mitarbeiterNummer");
					String sqlUpdate =  "UPDATE " + TABELLE_MITARBEITER + " SET menge=" + (kontoStand+lohn)  + " WHERE mitarbeiterNummer=" + mitarbeiterNummer;
					Statement verpackungZahlen = verbindung.createStatement();
					verpackungZahlen.executeUpdate(sqlUpdate);
				}
		}catch(SQLException ausnahme) {
			ausnahme.printStackTrace();
		}
	}

}
