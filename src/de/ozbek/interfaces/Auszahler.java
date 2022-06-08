package de.ozbek.interfaces;

/**
 * Interface, die in Bankautomat-Klasse implementiert wird
 */
import de.ozbek.classe.Kunde;

@FunctionalInterface
public interface Auszahler {
	
	void auszahlen(Kunde kunde, double menge);

}
