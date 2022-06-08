package de.ozbek.frontend_gui;

import java.awt.GridLayout;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 * Dies ist das Mitarbeiter-Panel, das in Register Jtabbedpane erstellt wurde
 * 
 * Der Mitarbeiter meldet sich mit seinem/ihrem Benutzernamen und Passwort auf diesem Panel an.
 */
public class GuiMitarbeiter extends JPanel {
	
	private static final long serialVersionUID = -1754568541285172387L;
	
	public static final String URL = "jdbc:mysql://127.0.0.1:3306/bank_db";
	public static final String USER = "root";
	public static final String PASSWORD = "";
	public static final String TABELLE_MITARBEITER = "mitarbeiter";
	
	private JTextField mitarbeiterNummer = new JTextField(15);
	private JPasswordField mitarbeiterPasswort = new JPasswordField(15);
	
	private JLabel nummerLabel = new JLabel("Mitarbeiternummer : ");
	private JLabel passwortLabel = new JLabel("Passwort : ");
	
	private JButton login = new JButton("-Login-");
	
	public GuiMitarbeiter() {
		setLayout(new GridLayout(3,3,25,25));	
		bauen();
		einloggen();
	}
	
	/**
	 * Bei dieser Methode loggt sich der Mitarbeiter mit seiner eigenen Mitarbeiternummer und seinem Passwort in das System ein.
     * Wenn das Passwort oder die Mitarbeiternummer falsch eingegeben wird, warnt uns das Programm auf der Konsole.
	 */

	private void einloggen() {
		login.addActionListener(event ->{

			String nummer = mitarbeiterNummer.getText();
			char[] pass = mitarbeiterPasswort.getPassword();
			if(nummer.matches("\\d+") && String.valueOf(pass).matches("\\d+")) { //Die eingegebene Mitarbeiternummer und Passwort muss nur aus Zahlen bestehen. wenn nicht, wird gewarnt
				int mitarbeiterNumber = Integer.parseInt(nummer);  //Artenumwandlung durchgeführt
				int passwort = Integer.parseInt(String.valueOf(pass));
				//SQL-Code, der entsprechend dem eingegebenen Passwort und Mitarbeiternummer Kundeninformationen aus der Datenbank zurückgibt
				String sqlPrep = "SELECT * FROM "+ TABELLE_MITARBEITER + " WHERE mitarbeiterNummer='"+mitarbeiterNumber+"' AND passwort="+passwort; 
				//SQL-Code, der entsprechend dem eingegebenen Passwort und Kundennummer eine Zahl aus der Datenbank zurückgibt
				String sqlPrepAnzahl="SELECT COUNT(mitarbeiterNummer) AS mitarbeiter FROM "+ TABELLE_MITARBEITER + " WHERE mitarbeiterNummer='"+mitarbeiterNumber+"' AND passwort="+passwort;
				
				try(Connection verbindung = DriverManager.getConnection(URL, USER, PASSWORD);
						Statement verpackung = verbindung.createStatement();){
						ResultSet kundeAnzahl = verpackung.executeQuery(sqlPrepAnzahl);
						try {
							while(kundeAnzahl.next()) {
								if(kundeAnzahl.getInt("mitarbeiter") ==1){ //Wenn 1 Kunde vom SQL-Code zurückgegeben wird
									
									//Bei korrekter Eingabe von Kundennummer und Passwort werden Kundeninformationen aus der Datenbank abgefragt.
									ResultSet ergebnis = verpackung.executeQuery(sqlPrep);
									while(ergebnis.next()) {
										
										//bei korrekter Eingabe öffnet sich die Kundenschnittstelle.
										System.out.println("Wilkommen " +ergebnis.getString("vorname").toUpperCase() + " " + ergebnis.getString("nachname").toUpperCase());
										MitarbeiterSchnittstelle meinSchnitstelle = new MitarbeiterSchnittstelle();
										meinSchnitstelle.setTitle("Bankangestellter: "+ ergebnis.getString("vorname").toUpperCase() + " " + ergebnis.getString("nachname").toUpperCase());
										meinSchnitstelle.setVisible(true);
			
									}
								}else {
									System.out.println("Dein Mitarbeiternummer oder Passwort ist falch");
								}	
							}
						}catch (Exception e) {
							// TODO: handle exception
						}
				}catch(SQLException ausnahme) {
					ausnahme.printStackTrace();
				}
			}else {
				System.out.println("Passwort und Mitarbeiternummer bestehen nur aus Zahlen.");
			}
		});
		
	}

	private void bauen() {
		add(nummerLabel);
		add(mitarbeiterNummer);
		add(passwortLabel);
		add(mitarbeiterPasswort);
		add(login);
	}

}
