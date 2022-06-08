package de.ozbek.frontend_gui;

import javax.swing.JFrame;

public class GuiBank extends JFrame{
	
	/**
	 * Die Klasse, in der das Register Jtabbedpane erstellt wird
	 * 
	 * die Schnittstelle, die wir sehen, wenn die Anwendung zum ersten Mal ausgeführt wird
	 */
	private static final long serialVersionUID = -3930132712084999665L;
	
	private Register register = new Register();
	
	public GuiBank() {
		setTitle("Özbek Bank");
		setSize(350, 200);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		
		bauenHauptbereich();
	}
	
	private void bauenHauptbereich() {
		this.add(register); 
	}

}
