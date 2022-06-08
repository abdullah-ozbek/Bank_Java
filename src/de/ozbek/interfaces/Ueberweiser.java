package de.ozbek.interfaces;


/**
 * Interface, die in Bankautomat-Klasse implementiert wird
 */
import de.ozbek.classe.Kunde;

public interface Ueberweiser {
	
	void ueberweisen(Kunde absender, Kunde empfaenger,double menge);

}
