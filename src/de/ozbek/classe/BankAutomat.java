package de.ozbek.classe;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;

import de.ozbek.exceptions.NichtAusreichendeGeldException;
import de.ozbek.exceptions.NichtRichtigePasswortException;
import de.ozbek.interfaces.Auszahler;
import de.ozbek.interfaces.EinZahler;
import de.ozbek.interfaces.Ueberweiser;

/**
 * Enthält Methoden, die in Bankautomaten durchgeführt werden
 * implementiert Ueberweiser, Einzahler und Auszahler Interfaces
 * @author Alfa
 *
 */
public class BankAutomat implements Ueberweiser, EinZahler, Auszahler {
	
	public static final String URL = "jdbc:mysql://127.0.0.1:3306/bank_db";
	public static final String USER = "root";
	public static final String PASSWORD = "";
	public static final String TABELLE_KUNDE = "kunden";
	
	/**
	 * Überprüft, ob der Kunde das richtige Passwort eingegeben hat.
	 * kann zweimal falsch eingegeben werden
	 * Wenn das Passwort dreimal falsch eingegeben wird, wird die NichtRichtigeException aufgerufen.
	 * 
	 * @param kunde
	 * @throws NichtRichtigePasswortException
	 */
	@SuppressWarnings("resource")
	public void einloggen(Kunde kunde) throws NichtRichtigePasswortException{
		int zaehler =0;
		while(zaehler <3) {
			System.out.println("Schreibe bitte dein Passwort ein. Du hast " + (3-zaehler) + " Versuch");
			Scanner eingabe = new Scanner(System.in);
			int pass = eingabe.nextInt();
			if(pass == kunde.getPasswort()) {
				break;
			}
			zaehler ++;
			if(pass!= kunde.getPasswort() && zaehler ==3 ) {
				throw new NichtRichtigePasswortException();
			}
		}
		System.out.println("Willkommen \"" + kunde.getVorname() + " " +kunde.getNachname() + "\"");
	}

	/**
	 * Der Betrag wird vom Konto des Absenders reduziert und auf dem Konto des Empfängers erhöht.
	 * neue Werte werden in der Datenbank aktualisiert
	 * 
	 * Befindet sich ein registrierter Kunde mit der eingegebenen Kundennummer nicht in der Datenbank,
	 * warnt uns das Programm auf der Konsole.
	 * 
	 * Zusätzlich wird eine Geldtransfernachricht (mit Betrags- und Datumsinformationen) 
	 * an das Sender- und Empfänger-Transaktionsinformationsfeld gesendet. 
	 * Diese Informationen werden auch in der Datenbank aktualisiert
	 */
	@Override
	public void ueberweisen(Kunde absender, Kunde empfaenger, double menge) {
		
		try(Connection verbindung = DriverManager.getConnection(URL, USER, PASSWORD);
				Statement verpackung = verbindung.createStatement();){
			String sqlAbsender = "SELECT * FROM " + TABELLE_KUNDE + " WHERE kundenNummer =" + absender.getKundenNummer();
			String sqlEmpfaenger = "SELECT * FROM " + TABELLE_KUNDE + " WHERE kundenNummer =" + empfaenger.getKundenNummer();
			String sqlAnzahl = "SELECT COUNT(kundenNummer) FROM " + TABELLE_KUNDE + " WHERE kundenNummer =" + empfaenger.getKundenNummer();
			
			ResultSet anzahl = verpackung.executeQuery(sqlAnzahl);
			anzahl.next();
			if(anzahl.getInt(1) == 1) {
				ResultSet absenderInfo = verpackung.executeQuery(sqlAbsender);
				absenderInfo.next();
				absender.setKontoStand(absenderInfo.getDouble("menge")-menge);
				
				//Wenn das Guthaben auf dem Kundenkonto nicht ausreicht, wird eine NichtAusreichendeGeldException ausgegeben.
				if(menge > absenderInfo.getDouble("menge")) {
					throw new NichtAusreichendeGeldException();
				}
				
				String nachrichtFuerEmpfaenger = "Am " + LocalDate.now() + " hat " + absenderInfo.getString("vorname").toUpperCase() +
                                                 " " + absenderInfo.getString("nachname").toUpperCase() +
                                                 " dir " + menge + " Euro überwiesen\n";	
				
				ResultSet empfaengerInfo = verpackung.executeQuery(sqlEmpfaenger);
				empfaengerInfo.next();
				empfaenger.setKontoStand(empfaengerInfo.getDouble("menge")+menge);
				
				String nachrichtFuerAbsender = "Am " + LocalDate.now() + " hast du an " + empfaengerInfo.getString("vorname").toUpperCase() +
											   " " + empfaengerInfo.getString("nachname").toUpperCase() +
											   " " + menge + " Euro überwiesen\n";
				
				empfaenger.setTransaktionInfos(empfaengerInfo.getString("transaktionInfos") + nachrichtFuerEmpfaenger);
				//Das Transaktionsinformationsfeld des Empfängers wird aktualisiert.
				
				ResultSet absenderInfoNoch = verpackung.executeQuery(sqlAbsender);
				absenderInfoNoch.next();
				absender.setTransaktionInfos(absenderInfoNoch.getString("transaktionInfos") + nachrichtFuerAbsender);
				//Das Transaktionsinformationsfeld des Absenders wird aktualisiert.
				
				String sqlUpdateAbsender =  "UPDATE " + TABELLE_KUNDE + " SET menge=" + absender.getKontoStand() +
				                   ", transaktionInfos='" + absender.getTransaktionInfos() + "' WHERE kundenNummer=" +absender.getKundenNummer();
				
				String sqlupdateEmpfaenger =  "UPDATE " +TABELLE_KUNDE + " SET menge=" + empfaenger.getKontoStand()  +
						           ", transaktionInfos='" + empfaenger.getTransaktionInfos() + "' WHERE kundenNummer=" +empfaenger.getKundenNummer();
				verpackung.executeUpdate(sqlUpdateAbsender);
				verpackung.executeUpdate(sqlupdateEmpfaenger);
			}else {
				System.out.println("Gib bitte eine gültige Kundennummer ein.");
			}
		}catch (SQLException ausnahme) {
			ausnahme.printStackTrace();
		}catch (NichtAusreichendeGeldException e) {
			System.out.println("Für diese Überweisung ist nicht genug Geld auf deinem Konto vorhanden");
		}
	}

	/**
	 * Einzahlung auf eigenes Konto
	 * 
	 * Der aktuelle Kontostand wird in der Datenbank aktualisiert 
	 * und die Einzahlungsnachricht wird im Transaktionsinfosfeld des Kunden gespeichert
	 */
	@Override
	public void einzahlen(Kunde kunde, double menge) {
		String sql =  "SELECT * FROM " +TABELLE_KUNDE + " WHERE kundenNummer=" +kunde.getKundenNummer();
		String sqlAnzahl =  "SELECT COUNT(kundenNummer) FROM " +TABELLE_KUNDE + " WHERE kundenNummer=" +kunde.getKundenNummer();
		try(Connection verbindung = DriverManager.getConnection(URL, USER, PASSWORD);
				Statement verpackung = verbindung.createStatement();){
			ResultSet kundeAnzahl = verpackung.executeQuery(sqlAnzahl);
			kundeAnzahl.next();
			if(kundeAnzahl.getInt(1) == 1) {
				String einzahlungsNachricht = "Am " + LocalDate.now() + " hast du " + menge + " Euro auf dein eigenes Konto eingezahlt\n";
				ResultSet kundeInfo = verpackung.executeQuery(sql);
				kundeInfo.next();
				kunde.setKontoStand(kundeInfo.getDouble("menge")+menge);
				kunde.setTransaktionInfos(kundeInfo.getString("transaktionInfos") + einzahlungsNachricht);
				String sqlPrep = "UPDATE " +TABELLE_KUNDE + " SET menge=" + kunde.getKontoStand()  +
				           		 ", transaktionInfos='" + kunde.getTransaktionInfos() + "' WHERE kundenNummer=" +kunde.getKundenNummer();
				verpackung.executeUpdate(sqlPrep);
			}else {
				System.out.println("Es gibt keinen solchen Kunden");
			}
		}catch (SQLException ausnahme) {
			ausnahme.printStackTrace();
		}
	}

	/**
	 * Bargeld vom eigenen Konto abheben
	 * 
	 * Der aktuelle Kontostand wird in der Datenbank aktualisiert 
	 * und die Auszahlungsnachricht wird im Transaktionsinfosfeld des Kunden gespeichert
	 */
	@Override
	public void auszahlen(Kunde kunde, double menge) {
		String sql =  "SELECT * FROM " +TABELLE_KUNDE + " WHERE kundenNummer=" +kunde.getKundenNummer();
		String sqlAnzahl =  "SELECT COUNT(kundenNummer) FROM " +TABELLE_KUNDE + " WHERE kundenNummer=" +kunde.getKundenNummer();
		try(Connection verbindung = DriverManager.getConnection(URL, USER, PASSWORD);
				Statement verpackung = verbindung.createStatement();){
			ResultSet kundeAnzahl = verpackung.executeQuery(sqlAnzahl);
			kundeAnzahl.next();
			if(kundeAnzahl.getInt(1) == 1) {
				String auszahlungsNachricht = "Am " + LocalDate.now() + " hast du " + menge + " Euro von deinem Konto abgehoben\n";
				ResultSet kundeInfo = verpackung.executeQuery(sql);
				kundeInfo.next();
				
				//Wenn das Guthaben auf dem Kundenkonto nicht ausreicht, wird eine NichtAusreichendeGeldException ausgegeben.
				if(menge > kundeInfo.getDouble("menge")) {
					throw new NichtAusreichendeGeldException();
				}
				
				kunde.setKontoStand(kundeInfo.getDouble("menge")-menge);
				kunde.setTransaktionInfos(kundeInfo.getString("transaktionInfos") + auszahlungsNachricht);
				String sqlPrep = "UPDATE " +TABELLE_KUNDE + " SET menge=" + kunde.getKontoStand()  +
						         ", transaktionInfos='" + kunde.getTransaktionInfos() + "' WHERE kundenNummer=" +kunde.getKundenNummer();
				verpackung.executeUpdate(sqlPrep);
			}else {
				System.out.println("Es gibt keinen solchen Kunden");
			}
		}catch (SQLException ausnahme) {
			ausnahme.printStackTrace();
		}catch (NichtAusreichendeGeldException e) {
			System.out.println("Für diese Auszahlung ist nicht genug Geld auf deinem Konto vorhanden");
		}
		
	}
	
	/**
	 * Bei dieser Methode kann der Kunde seinen eigenen Kontoauszug einsehen
	 * 
	 * Zunächst wird geprüft, 
	 * ob es einen registrierten Kunden mit der eingegebenen Kundennummer gibt.
	 * 
	 * Das Ergebnis sehen wir auch in der Konsole
	 * @param kundennummer
	 * @return
	 */
	
	public String getKontoauszug(int kundennummer) {
		String kontoauszugText = null;	
		String sql = "SELECT * FROM " + TABELLE_KUNDE + " WHERE kundenNummer =" + kundennummer;
		String sqlCount = "SELECT COUNT(kundenNummer) FROM " + TABELLE_KUNDE + " WHERE kundenNummer =" + kundennummer;
		try(Connection verbindung = DriverManager.getConnection(URL, USER, PASSWORD);
				Statement verpackung = verbindung.createStatement();){
			ResultSet kundeCount = verpackung.executeQuery(sqlCount);
			kundeCount.next();
			if(kundeCount.getInt(1) == 1) {
				ResultSet kunde = verpackung.executeQuery(sql);
				kunde.next();
				kontoauszugText = "--------------------------------------------\n";
				kontoauszugText = kontoauszugText + "KONTOAUSZUG:" + LocalDate.now() + ", " +LocalTime.now().getHour() + ":" +LocalTime.now().getMinute(); 
				kontoauszugText = kontoauszugText +	"\n" + kunde.getString("vorname").toUpperCase() + " " + kunde.getString("nachname").toUpperCase();
				kontoauszugText = kontoauszugText + "\nDein aktuelle Kontostand : €" + kunde.getDouble("menge") + "\nDu hast " + kunde.getDouble("kreditSchulden");
				kontoauszugText = kontoauszugText + " Euro Kreditschulden\n" + kunde.getString("transaktionInfos");
				kontoauszugText = kontoauszugText + "--------------------------------------------";
			}else {
				System.out.println("Es ist kein Kunde mit der Kundennummer:" + kundennummer + " registriert");
			}
		} catch (SQLException ausnahme) {
			ausnahme.printStackTrace();
		}
		System.out.println(kontoauszugText);
		return kontoauszugText;
	}

}
