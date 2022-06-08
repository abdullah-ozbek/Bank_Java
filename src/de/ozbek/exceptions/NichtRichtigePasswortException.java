package de.ozbek.exceptions;

public class NichtRichtigePasswortException extends Exception {

	/**
	 * Es wird in der Methode einloggen() verwendet, 
	 * die in der Klasse Bankautomat definiert ist. 
	 * 
	 * Wenn das Passwort dreimal falsch eingegeben wird, wird diese Exception aufgerufen.
	 */
	private static final long serialVersionUID = -4717594994268318142L;

}
