package de.ozbek.frontend_gui;


import java.awt.GridLayout;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.*;

/**
 * Dies ist das Kunde-Panel, das in Register Jtabbedpane erstellt wurde
 * 
 * Der Kunde meldet sich mit seinem/ihrem Kundennummer und Passwort auf diesem Panel an.
 */
public class GuiKunde extends JPanel {
	
	
	private static final long serialVersionUID = 2630881013471383553L;
	public static final String URL = "jdbc:mysql://127.0.0.1:3306/bank_db";
	public static final String USER = "root";
	public static final String PASSWORD = "";
	public static final String TABELLE_KUNDEN = "kunden";
	
	
	private JTextField kundenNummer = new JTextField();
	private JPasswordField kundenPasswort = new JPasswordField();
	
	private JLabel nummerLabel = new JLabel("Kundennummer : ");
	private JLabel passwortLabel = new JLabel("Passwort : ");
	
	private JButton login = new JButton("-Login-");
	
	public GuiKunde() {
		setLayout(new GridLayout(3,3,25,25));
		bauen();
		einloggen();
	}

	/**
	 * Bei dieser Methode kann sich der Kunde mit seinem Passwort und seiner Kundennummer einloggen.
	 * Lambda-Ausdruck wird verwendet
	 * passwort und kundenNummer in der Datenbank werden geprüft
	 * 
     * Wenn das Passwort oder die Kundennummer falsch eingegeben wird, warnt uns das Programm auf der Konsole.
	 */
	private void einloggen() {
		login.addActionListener(event ->{
			String nummer = kundenNummer.getText();
			char[] pass = kundenPasswort.getPassword();
			if(nummer.matches("\\d+") && String.valueOf(pass).matches("\\d+")) { //Die eingegebene Kundennummer und Passwort muss nur aus Zahlen bestehen. wenn nicht, wird gewarnt
				int kundenNumber = Integer.parseInt(nummer);  //Artenumwandlung durchgeführt
				int passwort = Integer.parseInt(String.valueOf(pass));
				//SQL-Code, der entsprechend dem eingegebenen Passwort und Kundennummer Kundeninformationen aus der Datenbank zurückgibt
				String sqlPrep = "SELECT * FROM "+ TABELLE_KUNDEN + " WHERE kundenNummer='"+kundenNumber+"' AND passwort="+passwort; 
				//SQL-Code, der entsprechend dem eingegebenen Passwort und Kundennummer eine Zahl aus der Datenbank zurückgibt
				String sqlPrepAnzahl="SELECT COUNT(kundenNummer) AS kunde FROM "+ TABELLE_KUNDEN + " WHERE kundenNummer='"+kundenNumber+"' AND passwort="+passwort;
				
				try(Connection verbindung = DriverManager.getConnection(URL, USER, PASSWORD);
						Statement verpackung = verbindung.createStatement();){
						ResultSet kundeAnzahl = verpackung.executeQuery(sqlPrepAnzahl);
						try {
							while(kundeAnzahl.next()) {
								if(kundeAnzahl.getInt("kunde") ==1){ //Wenn 1 Kunde vom SQL-Code zurückgegeben wird
									
									//Bei korrekter Eingabe von Kundennummer und Passwort werden Kundeninformationen aus der Datenbank abgefragt.
									ResultSet ergebnis = verpackung.executeQuery(sqlPrep);
									while(ergebnis.next()) {
										
										//bei korrekter Eingabe öffnet sich die Kundenschnittstelle.
										System.out.println("Wilkommen " +ergebnis.getString("vorname").toUpperCase() + " " + ergebnis.getString("nachname").toUpperCase());
										Kundenschnittstelle meinSchnitstelle = new Kundenschnittstelle();
										meinSchnitstelle.setTitle("Kunde: " +ergebnis.getString("vorname").toUpperCase() + " " + ergebnis.getString("nachname").toUpperCase());
										meinSchnitstelle.setVisible(true);
										
										//Kundeninformationen werden dem neu geöffneten Fenster hinzugefügt
										meinSchnitstelle.getTextVorname().setText(ergebnis.getString("vorname"));
										meinSchnitstelle.getTextNachname().setText(ergebnis.getString("nachname"));
										Integer  kundennummer = ergebnis.getInt("kundenNummer");
										meinSchnitstelle.getTextKundenNummer().setText(kundennummer.toString());
										Double  kontostand = ergebnis.getDouble("menge");
										meinSchnitstelle.getTextKontoStand().setText(kontostand.toString());
										Double  kredit = ergebnis.getDouble("kreditSchulden");
										meinSchnitstelle.getTextKredit().setText(kredit.toString());
									}
								}else {
									System.out.println("Dein Kundennummer oder Passwort ist falch");
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
		nummerLabel.setBounds(53, 60, 77, 14);
		add(nummerLabel);
		kundenNummer.setBounds(152, 57, 145, 20);
		kundenNummer.setColumns(5);
		add(kundenNummer);
		passwortLabel.setBounds(53, 95, 46, 14);
		add(passwortLabel);
		kundenPasswort.setColumns(5);
		kundenPasswort.setBounds(152, 92, 145, 20);
		add(kundenPasswort);
		add(login);
	}

}
