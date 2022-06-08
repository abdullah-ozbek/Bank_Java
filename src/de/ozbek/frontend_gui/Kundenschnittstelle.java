package de.ozbek.frontend_gui;

import java.awt.Color;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import de.ozbek.classe.Bank;
import de.ozbek.classe.BankAutomat;
import de.ozbek.classe.Kunde;
import de.ozbek.exceptions.NichtAusreichendeGeldException;

/**
 * Es ist das Kundenfenster, das mit der richtigen Kundennummer 
 * und dem richtigen Passwort im Fenster in der GuiBank-Klasse eingegeben wurde.
 * 
 * Auf dieser Oberfläche kann der Kunde sein Passwort ändern, 
 * Geld an einen anderen Kunden überweisen und seinen eigenen Kontoauszug einsehen.
 *
 */
public class Kundenschnittstelle extends JFrame {
	

	private static final long serialVersionUID = -2454712333519434612L;
	
	public static final String URL = "jdbc:mysql://127.0.0.1:3306/bank_db";
	public static final String USER = "root";
	public static final String PASSWORD = "";
	public static final String TABELLE_KUNDE = "kunden";
	
	private JPanel contentPane;
	private JLabel textKundenNummer;
	private JLabel textVorname;
	private JLabel textNachname;
	private JLabel textKontoStand;
	private JLabel textKredit;
	
	private JButton ueberweisen = new JButton("Überweisen");
	private JButton aendern = new JButton("Passwort Ändern");
	private JButton aktualisieren = new JButton("Aktuelisieren");
	private JButton speichern = new JButton("Speichern");
	private JButton bestaetigen = new JButton("Bestätigen");
	private JButton kontoauszug = new JButton("Kontoauszug");
	
	
	private JLabel altePasswort = new JLabel("Aktuelles Passwort:");
	private JLabel neuPasswort = new JLabel("Neues Passwort:");
	private JLabel neuPasswortWieder = new JLabel("Neues Passwort:");
	private JPasswordField altePassEingabe = new JPasswordField();
	private JPasswordField neuePassEingabe = new JPasswordField();
	private JPasswordField neuePassWiedereingabe = new JPasswordField();
	
	private JLabel empfanger = new JLabel("An Wen:");
	private JTextField textEmpfaenger = new JTextField();
	private JLabel betrag = new JLabel("Wie Viel:");
	private JTextField textBetrag = new JTextField();
	

	/**
	 * Konstructor
	 * Create the frame.
	 */
	public Kundenschnittstelle() throws NichtAusreichendeGeldException {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.setBackground( new Color(255,245,238) );
		
		JLabel kundenNummer = new JLabel("Kundennummer:");
		kundenNummer.setBounds(53, 25, 100, 14);
		contentPane.add(kundenNummer);
		
		JLabel vorname = new JLabel("Vorname:");
		vorname.setBounds(53, 60, 100, 14);
		contentPane.add(vorname);
		
		JLabel nachname = new JLabel("Nachname:");
		nachname.setBounds(53, 95, 100, 14);
		contentPane.add(nachname);
		
		JLabel kontoStand = new JLabel("Kontostand:");
		kontoStand.setBounds(53, 130, 100, 14);
		contentPane.add(kontoStand);
		
		JLabel kredit = new JLabel("Kreditschulden:");
		kredit.setBounds(53, 165, 100, 14);
		contentPane.add(kredit);
		
		textKundenNummer = new JLabel();
		textKundenNummer.setBounds(170, 25, 145, 20);
		contentPane.add(textKundenNummer);
		
		textVorname = new JLabel();
		textVorname.setBounds(170, 60, 145, 20);
		contentPane.add(textVorname);
		
		textNachname = new JLabel();
		textNachname.setBounds(170, 95, 145, 20);
		contentPane.add(textNachname);
		
		textKontoStand = new JLabel();
		textKontoStand.setBounds(170, 130, 145, 20);
		contentPane.add(textKontoStand);
		
		textKredit = new JLabel();
		textKredit.setBounds(170, 165, 145, 20);
		contentPane.add(textKredit);
		
		ueberweisen.setBounds(280, 60, 135, 23);
		contentPane.add(ueberweisen);
		
		aendern.setBounds(280, 25, 135, 23);
		contentPane.add(aendern);
		
		aktualisieren.setBounds(280, 95, 135, 23);
		contentPane.add(aktualisieren);
		
		kontoauszug.setBounds(280, 130, 135, 23);
		contentPane.add(kontoauszug);
		
		passwortaendern();
		
		ueberweisen();
		
		
		aktualisieren();
		
		kontoauszugAnzeigen();
		
	}


	/**
	 * Bei dieser Methode ändert der Kunde sein eigenes Passwort.
	 * 
	 * Wenn das Button „Passwort Ändern“ gedrückt wird, wird das Kundenfenster größer und neue Komponenten werden hinzugefügt.
	 * 
	 * Zunächst ist es notwendig, das aktuelle Passwort korrekt einzugeben.
	 * Das eingegebene aktuelle Passwort wird mit dem in der Datenbank registrierten Passwort verglichen.
	 * Außerdem muss zweimal ein neues Passwort eingegeben werden.
	 * 
	 * Sind die beiden eingegebenen neuen Passwörter gleich und bestehen nur aus Zahlen, 
	 * wird das neue Passwort nach dem Drücken der Speichern-Button in der Datenbank gespeichert.
	 */
	private void passwortaendern() {
	
		aendern.addActionListener(event ->{
			setBounds(100, 100, 500, 350);
			
			bestaetigen.setEnabled(false);
			speichern.setEnabled(true);
			
			altePassEingabe.setEnabled(true);
			neuePassEingabe.setEnabled(true);
			neuePassWiedereingabe.setEnabled(true);
			
			textEmpfaenger.setEnabled(false);
			textBetrag.setEnabled(false);
			
			altePasswort.setBounds(53, 210, 145, 20);
			contentPane.add(altePasswort);
			altePassEingabe.setBounds(200, 210, 80, 20);
			contentPane.add(altePassEingabe);
			
			neuPasswort.setBounds(53, 245, 145, 20);
			contentPane.add(neuPasswort);
			neuePassEingabe.setBounds(200, 245, 80, 20);
			contentPane.add(neuePassEingabe);
			
			neuPasswortWieder.setBounds(53, 280, 145, 20);
			contentPane.add(neuPasswortWieder);
			neuePassWiedereingabe.setBounds(200, 280, 80, 20);
			contentPane.add(neuePassWiedereingabe);
			
			speichern.setBounds(350, 210, 100, 20);
			contentPane.add(speichern);
			
		});
		
		speichern.addActionListener(event ->{
			char[] altespasswort = altePassEingabe.getPassword();
			char[] neuespasswort = neuePassEingabe.getPassword();
			char[] neuespasswortwieder = neuePassWiedereingabe.getPassword();
			
			String sql = "SELECT passwort FROM " + TABELLE_KUNDE + " WHERE kundenNummer =" + Integer.parseInt(textKundenNummer.getText());
			try(Connection verbindung = DriverManager.getConnection(URL, USER, PASSWORD);
					Statement verpackung = verbindung.createStatement();){
					ResultSet passwort = verpackung.executeQuery(sql);
					passwort.next();
					if(String.valueOf(altespasswort).matches("\\d+") 
						&& String.valueOf(neuespasswort).matches("\\d+") 
						&& String.valueOf(neuespasswort).matches("\\d+")
						&& passwort.getInt(1) == Integer.parseInt(String.valueOf(altespasswort))
						&& Integer.parseInt(String.valueOf(neuespasswort)) == Integer.parseInt(String.valueOf(neuespasswortwieder))) {
							String sqlUpdate ="UPDATE " +TABELLE_KUNDE + " SET passwort=" + Integer.parseInt(String.valueOf(neuespasswort))
							                  + " WHERE kundenNummer=" +Integer.parseInt(textKundenNummer.getText());
							//Es ist der SQL-Code, der das neue eingegebene Passwort in die Datenbank aktualisiert
							verpackung.executeUpdate(sqlUpdate);
							
							neuePassEingabe.setText("");
							altePassEingabe.setText("");
							neuePassWiedereingabe.setText("");
							
							altePassEingabe.setEnabled(false);
							neuePassEingabe.setEnabled(false);
							neuePassWiedereingabe.setEnabled(false);
					}else {
						System.out.println("Du hast dein aktuelles oder neues Passwort falsch eingegeben.");
						
						neuePassEingabe.setText("");
						altePassEingabe.setText("");
						neuePassWiedereingabe.setText("");
					}
			}catch(SQLException ausnahme) {
				ausnahme.printStackTrace();
			}
		});
	}
	
	/**
	 * Bei dieser Methode überweist der Kunde Geld an einen anderen Kunden.
	 * 
	 * Beim Drücken der Überweisen-Button wird das Fenster vergrößert und neue Komponenten werden hinzugefügt
	 * 
	 * Es muss eine gültige Kundennummer eingegeben werden
	 * (Dies wird in der in der Bakautomat-Klasse geschriebenen Methode überprüft)
	 * 
	 * Der Betrag kann Integer oder Double sein
	 * 
	 * Dies geschieht durch die in der Bankautomat-Klasse geschriebene ueberweisen() Methode
	 */
	private void ueberweisen() {
		ueberweisen.addActionListener(event ->{
			
			speichern.setEnabled(false);
			bestaetigen.setEnabled(true);
			
			textEmpfaenger.setEnabled(true);
			textBetrag.setEnabled(true);
			
			altePassEingabe.setEnabled(false);
			neuePassEingabe.setEnabled(false);
			neuePassWiedereingabe.setEnabled(false);
			
			setBounds(100, 100, 500, 420);
			
			empfanger.setBounds(53, 320, 145, 20);
			contentPane.add(empfanger);
			textEmpfaenger.setBounds(200, 320, 80, 20);
			contentPane.add(textEmpfaenger);
			
			betrag.setBounds(53, 355, 145, 20);
			contentPane.add(betrag);
			textBetrag.setBounds(200, 355, 80, 20);
			contentPane.add(textBetrag);
			
			bestaetigen.setBounds(350, 320, 100, 20);
			contentPane.add(bestaetigen);
			});
		
		bestaetigen.addActionListener(event ->{
			String empfaengerNummer = textEmpfaenger.getText();
			String betrag = textBetrag.getText();
			
			if(empfaengerNummer.matches("\\d+") && betrag !=null && betrag.matches("\\d+(\\.\\d*)?")) {
				int absenderNumber = Integer.parseInt(textKundenNummer.getText());
				int empfaengerNumber = Integer.parseInt(textEmpfaenger.getText());
				double ueberweisungsbetrag = Double.parseDouble(textBetrag.getText());
				
				BankAutomat meinBankautomat = new Bank();
				Kunde absender = new Kunde();
				Kunde empfaenger = new Kunde();
				absender.setKundenNummer(absenderNumber);
				empfaenger.setKundenNummer(empfaengerNumber);
				
				meinBankautomat.ueberweisen(absender, empfaenger, ueberweisungsbetrag);
				
				textEmpfaenger.setText("");
				textBetrag.setText("");
				textEmpfaenger.setEnabled(false);
				textBetrag.setEnabled(false);
			}else {
				System.out.println("Kundennummer des Empfängers und Betrag müssen aus Zahlen bestehen");
				
				textEmpfaenger.setText("");
				textBetrag.setText("");
			}
			
		});
		
	}
	
	/**
	 * Bei dieser Methode werden die Änderungen im Kundenfenster aktualisiert.
	 * Wenn wir beispielsweise Geld an jemanden überweisen, wird der KontoStand aktualisiert.
	 */
	private void aktualisieren() {
		aktualisieren.addActionListener(event ->{
			int kundenNummer = Integer.parseInt(textKundenNummer.getText());
			Bank meinBank = new Bank();
			Kunde aktuelleKunde = meinBank.getEinKunde(kundenNummer);
			Double aktuelleKontoStand = aktuelleKunde.getKontoStand();
			Double aktuelleKreditSchulden = aktuelleKunde.getKreditschulden();
			textKontoStand.setText(aktuelleKontoStand.toString());
			textKredit.setText(aktuelleKreditSchulden.toString());
		});
	}
	
	/**
	 * Hier zeigt der Kunde seine Kontoauszug-Informationen an, indem er den Kontouszug-Button drückt.
	 * 
	 * Dies geschieht durch die in der Klasse Bankautomat geschriebene Methode getKontoauszug().
	 */
	private void kontoauszugAnzeigen() {
		
		kontoauszug.addActionListener(event ->{
			int kundenNummer = Integer.parseInt(textKundenNummer.getText());
			Bank bank = new Bank();
			bank.getKontoauszug(kundenNummer);
		});
	}

	
	
	//Getter und Setter-Methode

	/**
	 * @return the textVorname
	 */
	public JLabel getTextVorname() {
		return textVorname;
	}


	/**
	 * @return the text_kundenNummer
	 */
	public JLabel getTextKundenNummer() {
		return textKundenNummer;
	}


	/**
	 * @return the textNachname
	 */
	public JLabel getTextNachname() {
		return textNachname;
	}


	/**
	 * @return the textKontoStand
	 */
	public JLabel getTextKontoStand() {
		return textKontoStand;
	}


	/**
	 * @return the textKredit
	 */
	public JLabel getTextKredit() {
		return textKredit;
	}

}
