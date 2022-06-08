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

/**
 * Es ist das Mitarbeiterfenster, das mit der richtigen Mitarbeiternummer 
 * und dem richtigen Passwort im Fenster in der GuiBank-Klasse eingegeben wurde.
 * 
 * Hier werden alle Kundeninformationen in der Datenbank in einer Tabelle angezeigt mit de Methode anzeigenAlleKunden()
 * 
 * Hier kann ein neuer Kunde hinzugefügt werden mit der Methode neuKundeerstellen()
 * 
 * Der Mitarbeiter kann jedem Kunden einen Kredit gewähren
 * @author Alfa
 *
 */
public class MitarbeiterSchnittstelle extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1804596676925899237L;
	
	private JPanel contentPane;
	private JTable tabelle;
	
	private DefaultTableModel meinModel= new DefaultTableModel();
	private Object[] spalten={"Kundennummer","Vorname","Nachname","Kontostand","Kreditschulden"};
	private Object[] linien =new Object[5];
	
	private JButton anzeigeButton = new JButton("Anzeigen");
	private JButton neuKunde = new JButton("Neu Kunde");
	private JButton speichern = new JButton("Speichern");
	private JButton kredit = new JButton("Kredit");
	private JButton bestaetigen = new JButton("Bestätigen");
	
	
	private JTextField textVorname = new JTextField();
	private JTextField textNachname = new JTextField();
	private JTextField textKontoStand = new JTextField();
	private JLabel vorname = new JLabel("Vorname:");
	private JLabel nachname = new JLabel("Nachname:");
	private JLabel kontoStand = new JLabel("Menge:");
	
	private JLabel kreditNehmer = new JLabel("Kreditnehmer:");
	private JLabel betrag = new JLabel("Menge:");
	private JTextField kundenNummerDerKreditnehmer = new JTextField();
	private JTextField textBetrag = new JTextField();


	/**
	 * Konstructor
	 * Create the frame.
	 */
	public MitarbeiterSchnittstelle() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 620, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.setBackground(new Color(240,248,255));
		
		anzeigeButton.setBounds(500, 15, 100, 25);
		contentPane.add(anzeigeButton);
	
		neuKunde.setBounds(500, 50, 100, 25);
		contentPane.add(neuKunde);
		
		kredit.setBounds(500, 85, 100, 25);
		contentPane.add(kredit);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 10, 480, 250);
		contentPane.add(scrollPane);
		
		tabelle = new JTable();
		meinModel.setColumnIdentifiers(spalten);
		tabelle.setModel(meinModel);
		scrollPane.setViewportView(tabelle);
		tabelle.setBackground(new Color(255,250,250));
		
		anzeigenAlleKunden();		
		neuKundeerstellen();
		kreditgeben();

	}


	/**
	 * Es ist die Methode, die die Kundenliste anzeigt
	 * 
	 * Die Kundenliste wird über die in der Klasse Bank definierte Methode getAlleKunden() abgerufen.
	 */
	private void anzeigenAlleKunden() {
		anzeigeButton.addActionListener(event ->{
			setBounds(100, 100, 620, 300);
			
			textBetrag.setEnabled(false);
			kundenNummerDerKreditnehmer.setEnabled(false);
			bestaetigen.setEnabled(false);
			textVorname.setEnabled(false);
			textNachname.setEnabled(false);
			textKontoStand.setEnabled(false);
			speichern.setEnabled(false);
			
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
	 * Es ist die Methode, mit der einen neuen Kunde hinzufügt wird.
	 * 
	 * Dies geschieht über die in der Klasse Bank definierte Methode neuKundenanlegen(Kunde kunde).
	 */
	private void neuKundeerstellen() {
		
		neuKunde.addActionListener(event -> {
			this.setBounds(100, 100, 620, 400);	
			textVorname.setEnabled(true);
			textNachname.setEnabled(true);
			textKontoStand.setEnabled(true);
			speichern.setEnabled(true);
			
			textBetrag.setEnabled(false);
			kundenNummerDerKreditnehmer.setEnabled(false);
			bestaetigen.setEnabled(false);
			
			vorname.setBounds(10, 265, 100, 14);
			contentPane.add(vorname);
			textVorname.setBounds(100, 265, 145, 20);
			contentPane.add(textVorname);
			
			nachname.setBounds(10, 300, 100, 14);
			contentPane.add(nachname);
			textNachname.setBounds(100, 300, 145, 20);
			contentPane.add(textNachname);
			
			kontoStand.setBounds(10, 335, 100, 14);
			contentPane.add(kontoStand);
			textKontoStand.setBounds(100, 335, 50, 20);
			contentPane.add(textKontoStand);;
			
			speichern.setBounds(380, 265, 100, 25);
			contentPane.add(speichern);
		
		});
		
		speichern.addActionListener(event ->{
			String vorname = textVorname.getText();
			String nachname = textNachname.getText();
			String menge = textKontoStand.getText();
			if(vorname != null && nachname != null && menge.matches("\\d+(\\.\\d*)?")) {
				Bank meinbank = new Bank();
				Kunde neuKunde = new Kunde(vorname, nachname);
				neuKunde.setKontoStand(Double.parseDouble(menge));
				meinbank.neuKundenanlegen(neuKunde);
				this.setBounds(100, 100, 620, 300);
				
				textVorname.setText("");
				textNachname.setText("");
				textKontoStand.setText("");
				
				textVorname.setEnabled(false);
				textNachname.setEnabled(false);
				textKontoStand.setEnabled(false);
				speichern.setEnabled(false);
				
			}
			else {
				System.out.println("Das Feld für Vorname und Nachname darf nicht leer sein.\r\n"
						+ "Das Mengenfeld muss aus Zahlen bestehen");
				
				textVorname.setText("");
				textNachname.setText("");
				textKontoStand.setText("");
				
			}
		});
	}
	
	/**
	 * Es ist die Methode, mit der einem Kunden ein Kredit gegeben wird.
	 * 
	 * Dies geschieht über die in der Klasse Bank definierte Methode kreditgeben(Kunde kunde , double betrag ).
	 */
	
	private void kreditgeben() {
		
		kredit.addActionListener(event ->{
			
			this.setBounds(100, 100, 620, 500);	
			
			textVorname.setEnabled(false);
			textNachname.setEnabled(false);
			textKontoStand.setEnabled(false);
			speichern.setEnabled(false);
			
			textBetrag.setEnabled(true);
			kundenNummerDerKreditnehmer.setEnabled(true);
			bestaetigen.setEnabled(true);
			
			kreditNehmer.setBounds(10, 380, 100, 20);
			contentPane.add(kreditNehmer);
			kundenNummerDerKreditnehmer.setBounds(100, 380, 50, 20);
			contentPane.add(kundenNummerDerKreditnehmer);
			
			betrag.setBounds(10, 415, 100, 20);
			contentPane.add(betrag);
			textBetrag.setBounds(100, 415, 50, 20);
			contentPane.add(textBetrag);
			
			bestaetigen.setBounds(380, 380, 100, 25);
			contentPane.add(bestaetigen);
		});
		
		bestaetigen.addActionListener(event ->{
			String kreditnehmerNummer = kundenNummerDerKreditnehmer.getText();
			String betrag = textBetrag.getText();
			
			if(kreditnehmerNummer.matches("\\d+") && betrag !=null && betrag.matches("\\d+(\\.\\d*)?")) {
				int kreditnehmerNumber = Integer.parseInt(kreditnehmerNummer);
				double kreditBetrag = Double.parseDouble(betrag);
				
				Bank meinBank = new Bank();
				Kunde kunde = new Kunde();
				kunde.setKundenNummer(kreditnehmerNumber);
				meinBank.kreditgeben(kunde, kreditBetrag);
				
				kundenNummerDerKreditnehmer.setText("");
				textBetrag.setText("");
				
				kundenNummerDerKreditnehmer.setEnabled(false);
				textBetrag.setEnabled(false);
				bestaetigen.setEnabled(false);
				
			}else {
				System.out.println("Kundennummer des Empfängers und Betrag müssen aus Zahlen bestehen");
				
				kundenNummerDerKreditnehmer.setText("");
				textBetrag.setText("");
			}
		});
	}
	
}
