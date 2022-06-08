package de.ozbek.frontend_gui;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import java.awt.*;

/**
 * Diese Klasse ist ein JTabbedpane, in dem Kunden-, Mitarbeiter- und BankmanagerJpanels gespeichert werden
 *
 */
public class Register extends JTabbedPane {
	
	private static final long serialVersionUID = -1314367131775171511L;
	
	private JPanel kunde = new GuiKunde();
	private JPanel mitarbeiter = new GuiMitarbeiter();
	private JPanel bankmaneger = new GuiBankManager();
	
	public Register() {
		kunde.setBackground(Color.gray);
		mitarbeiter.setBackground(Color.cyan);
		
		this.add("Kunde", kunde);
		this.add("Mitarbeiter", mitarbeiter);
		this.add("Bank-Manager", bankmaneger);
	}

}
