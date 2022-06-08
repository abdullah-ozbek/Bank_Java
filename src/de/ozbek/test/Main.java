package de.ozbek.test;

import java.util.List;

import de.ozbek.classe.Bank;
import de.ozbek.classe.BankAutomat;
import de.ozbek.classe.BankManager;
import de.ozbek.classe.Kunde;
import de.ozbek.classe.Mitarbeiter;
import de.ozbek.exceptions.NichtRichtigePasswortException;

/**
 * Es ermöglicht uns, den Code in der Konsole zu testen
 * @author Alfa
 *
 */
public class Main {
	
	public static void main(String[] args) {
		
		Bank bank = new Bank();
//		Kunde kundeEins = new Kunde("suzanna", "randal");
//		Kunde kundeZwei = new Kunde("suzanne", "collins");
		
//		BankManager manager = new BankManager("Albert", "Mustermann");
//		bank.neuManagernanlegen(manager);
		
		BankAutomat bankautomat = new BankAutomat();
		
//		bank.neuKundenanlegen(kunde1);
//		bank.neuKundenanlegen(kunde2);
		
//		bank.kreditgeben(kunde1, 345);
//		
//		bankautomat.einzahlen(kunde1, 500);
		
//		bankautomat.ueberweisen(kunde1,kunde2, 300);
		
//		bank.kreditgeben(kunde2, 678);
//		bank.kreditgeben(kunde2, 200);
		
		
//		try {
//			bankautomat.einloggen(kunde2);
//		} catch (NichtRichtigePasswortException e) {
//			System.out.println("Du hast 3 mal falche Passwort eingegeben. Hol bitte deine Karte zurück");
//		}
		
//		Mitarbeiter mitarbeiter1 = new Mitarbeiter("Karl","Max");
//		Mitarbeiter mitarbeiter2 = new Mitarbeiter("Justus","Jonas");
//		bank.neuMitarbeiternanlegen(mitarbeiter1);
//		bank.neuMitarbeiternanlegen(mitarbeiter2);
		
//		bank.getKunden().forEach(kunde ->System.out.println(kunde));
		
//		List<Kunde> alleKunden = bank.gettAlleKunden();	
//		alleKunden.forEach(kunde -> System.out.println(kunde.getTransaktionInfos()));
		
//		System.out.println();
		
//		List<Mitarbeiter> alleMitarbeiter = bank.gettAlleMitarbeiter();
//		alleMitarbeiter.forEach(arbeitnehmer -> System.out.println(arbeitnehmer));
		
		Kunde kunde = bank.getEinKunde(79);
//		System.out.println(k.getTransaktionInfos());
//		bank.einzahlen(kunde, 100);
//		bank.auszahlen(kunde, 100);
		
//		bank.getKontoauszug(75);
		
//		bank.gehaelterZahlen();
		
//		try {
//			bankautomat.einloggen(kunde);
//		} catch (NichtRichtigePasswortException e) {
//			e.printStackTrace();
//		}
	}

}
