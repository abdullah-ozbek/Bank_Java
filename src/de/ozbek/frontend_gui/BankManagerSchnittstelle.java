package de.ozbek.frontend_gui;

import java.awt.Color;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import de.ozbek.classe.Bank;
import de.ozbek.classe.Kunde;
import de.ozbek.classe.Mitarbeiter;

/**
 * Dies ist die Fensterklasse, die erscheint, 
 * wenn sich der Bankmanager mit der eigenen Benutzernummer und dem Passwort anmeldet.
 * 
 * Über diese Schnittstelle kann der Manager die Kunden- und Mitarbeiterlisten einsehen, 
 * neue Mitarbeiter einstellen und 
 * Gehälter an die Mitarbeiter zahlen.
 * 
 * Es führt diese Operationen mit den definierten JButtons aus.
 * @author Alfa
 *
 */
public class BankManagerSchnittstelle extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7801317312697745332L;
	
	private JPanel contentPane;
	
	private JTable tabelleFuerKunden = new JTable();
	private JTable tabelleFuerMitarbeiter = new JTable();
	
	private DefaultTableModel meinModel= new DefaultTableModel();
	private Object[] spaltenFurKunden={"Kundennummer","Vorname","Nachname","Kontostand","Kreditschulden"};
	private Object[] spaltenFuerMitarbeiter={"Nummer","Vorname","Nachname","Kontostand","Lohn"};
	private Object[] linien =new Object[5];
	
	private JButton arbeitnehmen = new JButton("Arbeitnehmer");
	private JButton kunden = new JButton("Kunden");
	private JButton neuArbeitnehmer = new JButton("Neu-Arbeitnehmer");
	private JButton speichern = new JButton("Speichern");
	private JButton gehaltZahlen = new JButton("Gehälter Zahlen");
	
	private JTextField textVorname = new JTextField();
	private JTextField textNachname = new JTextField();
	private JTextField textLohn = new JTextField();
	
	private JLabel vorname = new JLabel("Vorname:");
	private JLabel nachname = new JLabel("Nachname:");
	private JLabel lohn = new JLabel("Lohn:");
	
	private JScrollPane scrollPane = new JScrollPane();
	
	/**
	 * Konstructor
	 * Create the frame.
	 */
	public BankManagerSchnittstelle() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 660, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.setBackground(new Color(240,248,255));
		
		
		scrollPane.setBounds(10, 10, 480, 250);
		contentPane.add(scrollPane);
		
		arbeitnehmen.setBounds(500, 15, 138, 25);
		contentPane.add(arbeitnehmen);
		
		kunden.setBounds(500, 50, 138, 25);
		contentPane.add(kunden);
		
		neuArbeitnehmer.setBounds(500, 85, 138, 25);
		contentPane.add(neuArbeitnehmer);
		
		gehaltZahlen.setBounds(500, 120, 138, 25);
		contentPane.add(gehaltZahlen);
		
//		gehaltZahlen.setEnabled(false);
		
		arbeitgebernAnzeigen();
		
		kundenAnzeigen();
		
		personalEinstellen();
		
		gehaelterZahlen();
		
	

	}


	/**
	 * Es ist die Methode, die die Mitarbeiterliste anzeigt
	 * 
	 * Die Mitarbeiterliste wird über die in der Klasse Bank definierte Methode getAlleMitarbeiter() abgerufen. 
	 */
	private void arbeitgebernAnzeigen() {
		arbeitnehmen.addActionListener(event ->{
			meinModel.setRowCount(0);
			
			textVorname.setEnabled(false);
			textNachname.setEnabled(false);
			textLohn.setEnabled(false);
			speichern.setEnabled(false);
			
			meinModel.setColumnIdentifiers(spaltenFuerMitarbeiter);
			tabelleFuerMitarbeiter.setModel(meinModel);
			scrollPane.setViewportView(tabelleFuerMitarbeiter);
			tabelleFuerMitarbeiter.setBackground(new Color(255,250,250));	
			
			meinModel.setRowCount(0);
			Bank meinbank = new Bank();
			List<Mitarbeiter> alleMitarbeiter = meinbank.gettAlleMitarbeiter();
			for(Mitarbeiter mitarbeiter : alleMitarbeiter) {
				linien[0] = mitarbeiter.getKundenNummer();
				linien[1] = mitarbeiter.getVorname();
				linien[2] = mitarbeiter.getNachname();
				linien[3] = mitarbeiter.getKontoStand();
				linien[4] = mitarbeiter.getLohn();
				meinModel.addRow(linien);
			}
		});	
	}
	
	/**
	 * Es ist die Methode, die die Kundenliste anzeigt
	 * 
	 * Die Kundenliste wird über die in der Klasse Bank definierte Methode getAlleKunden() abgerufen.
	 */
	private void kundenAnzeigen() {
		kunden.addActionListener(event ->{		
			meinModel.setRowCount(0);
			
			textVorname.setEnabled(false);
			textNachname.setEnabled(false);
			textLohn.setEnabled(false);
			speichern.setEnabled(false);
			
			meinModel.setColumnIdentifiers(spaltenFurKunden);
			tabelleFuerKunden.setModel(meinModel);
			scrollPane.setViewportView(tabelleFuerKunden);
			tabelleFuerKunden.setBackground(new Color(255,250,250));	
			
			meinModel.setRowCount(0);
			
			Bank meinbank = new Bank();
			List<Kunde> alleKunden = meinbank.gettAlleKunden();
			for(Kunde kunde : alleKunden) {
				linien[0] = kunde.getKundenNummer();
				linien[1] = kunde.getVorname();
				linien[2] = kunde.getNachname();
				linien[3] = kunde.getKontoStand();
				linien[4] = kunde.getKreditschulden();
				meinModel.addRow(linien);
			}
		});
	}
	
	/**
	 * Es ist die Methode, mit der ein neuer Mitarbeiter eingestellt wird.
	 * 
	 * Dies geschieht über die in der Klasse Bank definierte Methode neuMitarbeiternanlegen(Mitarbeiter mitarbeiter).
	 */
	private void personalEinstellen() {
		neuArbeitnehmer.addActionListener(event -> {
			this.setBounds(100, 100, 650, 400);	
			
			textVorname.setEnabled(true);
			textNachname.setEnabled(true);
			textLohn.setEnabled(true);
			speichern.setEnabled(true);
				
			vorname.setBounds(10, 265, 100, 14);
			contentPane.add(vorname);
			textVorname.setBounds(100, 265, 145, 20);
			contentPane.add(textVorname);
			
			nachname.setBounds(10, 300, 100, 14);
			contentPane.add(nachname);
			textNachname.setBounds(100, 300, 145, 20);
			contentPane.add(textNachname);
			
			lohn.setBounds(10, 335, 100, 14);
			contentPane.add(lohn);
			textLohn.setBounds(100, 335, 50, 20);
			contentPane.add(textLohn);
			
			speichern.setBounds(380, 265, 100, 25);
			contentPane.add(speichern);
		
		});
		
		speichern.addActionListener(event ->{
			String vorname = textVorname.getText();
			String nachname = textNachname.getText();
			String lohn = textLohn.getText();
			if(vorname != "" && nachname != "" && lohn.matches("\\d+(\\.\\d*)?")) {
				Bank meinbank = new Bank();
				Mitarbeiter neumitarbeiter = new Mitarbeiter(vorname, nachname);
				neumitarbeiter.setLohn(Double.parseDouble(lohn));
				meinbank.neuMitarbeiternanlegen(neumitarbeiter);
				this.setBounds(100, 100, 650, 300);
				
				textVorname.setText("");
				textNachname.setText("");
				textLohn.setText("");
				
				textVorname.setEnabled(false);
				textNachname.setEnabled(false);
				textLohn.setEnabled(false);
				speichern.setEnabled(false);
				
			}
			else {
				System.out.println("Das Feld für Vorname und Nachname darf nicht leer sein.\r\n"
						+ "Das Mengenfeld muss aus Zahlen bestehen");
				
				textVorname.setText("");
				textNachname.setText("");
				textLohn.setText("");
				
			}
		});
	}
		
	/**
	 * Methode, die es Managern ermöglicht, Mitarbeitergehälter zu zahlen
	 * 
	 * Dies geschieht über die in der Klasse Bank definierte Methode gehaelterZahlen().
	 */
	
	private void gehaelterZahlen() {

		gehaltZahlen.addActionListener(event ->{
			new Bank().gehaelterZahlen();
		});
		
	}

	
}
