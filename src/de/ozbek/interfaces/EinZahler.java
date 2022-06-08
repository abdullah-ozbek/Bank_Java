package de.ozbek.interfaces;

/**
 * Interface, die in Bankautomat-Klasse implementiert wird
 */
import de.ozbek.classe.Kunde;

@FunctionalInterface
public interface EinZahler {
	
	void einzahlen(Kunde kunde, double menge);

}
