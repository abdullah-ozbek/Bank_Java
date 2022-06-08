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
 * Dies ist das Bankmanager-Panel, das in Register Jtabbedpane erstellt wurde
 * 
 * Der Manager meldet sich mit seinem Benutzernamen und Passwort auf diesem Panel an.
 */
public class GuiBankManager extends JPanel{
	

	private static final long serialVersionUID = 1L;
	
	public static final String URL = "jdbc:mysql://127.0.0.1:3306/bank_db";
	public static final String USER = "root";
	public static final String PASSWORD = "";
	public static final String TABELLE = "kunden";
	public static final String TABELLE2 = "mitarbeiter";
	public static final String TABELLE3 = "bankmanager";
	
	
	private JTextField manegerNummer = new JTextField(15);
	private JPasswordField managerPasswort = new JPasswordField(15);
	
	private JLabel nummerLabel = new JLabel("Bankmanagernummer : ");
	private JLabel passwortLabel = new JLabel("Passwort : ");
	
	private JButton login = new JButton("-Login-");
	
	public GuiBankManager() {
		setLayout(new GridLayout(3,3,25,25));	
		bauen();
		einloggen();
	}
	
	/**
	 * Bei dieser Methode loggt sich der Bankmanager mit seiner/ihrer eigenen Usernummer und seinem/ihrer Passwort in das System ein.
     * Wenn das Passwort oder die Usernummer falsch eingegeben wird, warnt uns das Programm auf der Konsole.
	 */

	private void einloggen() {
		login.addActionListener(event ->{

			String nummer =manegerNummer.getText();
			char[] pass = managerPasswort.getPassword();
			if(nummer.matches("\\d+") && String.valueOf(pass).matches("\\d+")) { //Die eingegebene Managerernummer und Passwort muss nur aus Zahlen bestehen. wenn nicht, wird gewarnt
				int managerNumber = Integer.parseInt(nummer);  //Artenumwandlung durchgeführt
				int passwort = Integer.parseInt(String.valueOf(pass));
				//SQL-Code, der entsprechend dem eingegebenen Passwort und Mitarbeiternummer Kundeninformationen aus der Datenbank zurückgibt
				String sqlPrep = "SELECT * FROM "+ TABELLE3 + " WHERE userNummer='"+managerNumber+"' AND passwort="+passwort; 
				//SQL-Code, der entsprechend dem eingegebenen Passwort und Kundennummer eine Zahl aus der Datenbank zurückgibt
				String sqlPrepAnzahl="SELECT COUNT(userNummer) AS manager FROM "+ TABELLE3 + " WHERE userNummer='"+managerNumber+"' AND passwort="+passwort;
				
				try(Connection verbindung = DriverManager.getConnection(URL, USER, PASSWORD);
						Statement verpackung = verbindung.createStatement();){
						ResultSet kundeAnzahl = verpackung.executeQuery(sqlPrepAnzahl);
						try {
							while(kundeAnzahl.next()) {
								if(kundeAnzahl.getInt("manager") ==1){ //Wenn 1 Manager vom SQL-Code zurückgegeben wird
									
									//Bei korrekter Eingabe von Usernummer und Passwort werden Bankmanageriformationen aus der Datenbank abgefragt.
									ResultSet ergebnis = verpackung.executeQuery(sqlPrep);
									while(ergebnis.next()) {
										
										//bei korrekter Eingabe öffnet sich die BankManagerschnittstelle.
										System.out.println("Wilkommen " +ergebnis.getString("vorname").toUpperCase() + " " + ergebnis.getString("nachname").toUpperCase());
										BankManagerSchnittstelle meinSchnitstelle = new BankManagerSchnittstelle();
										meinSchnitstelle.setTitle("Bank-Manager: " +ergebnis.getString("vorname").toUpperCase() + " " + ergebnis.getString("nachname").toUpperCase());
										meinSchnitstelle.setVisible(true);
									}
								}else {
									System.out.println("Dein Usernummer oder Passwort ist falch");
								}	
							}
						}catch (Exception e) {
							// TODO: handle exception
						}
				}catch(SQLException ausnahme) {
					ausnahme.printStackTrace();
				}
			}else {
				System.out.println("Passwort und Kundennummer bestehen nur aus Zahlen.");
			}
		});
		
	}

	private void bauen() {
		add(nummerLabel);
		add(manegerNummer);
		add(passwortLabel);
		add(managerPasswort);
		add(login);
	}


}
