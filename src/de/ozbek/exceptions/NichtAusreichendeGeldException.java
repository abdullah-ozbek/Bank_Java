package de.ozbek.exceptions;

/**
 * Es wird in den Methoden ueberweisen() und auszahlen() verwendet,
 * die in der Klasse Bankautomat definiert sind. 
 * 
 * Wenn auf dem Konto nicht genug Geld vorhanden ist, wird diese Exception aufgerufen.
 * @author Alfa
 *
 */
public class NichtAusreichendeGeldException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -942114745347276222L;

}
