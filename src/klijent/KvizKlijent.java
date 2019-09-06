package klijent;

import java.io.*;
import java.net.*;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Rectangle;
import javax.swing.JScrollPane;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.Timer;

import javax.swing.ImageIcon;

public class KvizKlijent extends JFrame implements Runnable {

    public RangListaGui rangListaGui = new RangListaGui();
    private static final long serialVersionUID = 1L;
    static Socket soketZaKomunikaciju = null;
    static PrintStream izlazniTokKaServeru = null;
    static BufferedReader ulazniTokOdServera = null;
    static BufferedReader ulazKonzola = null;
    static boolean kraj = false;
    static DatagramSocket datagramSoket;
    private JTextField textField;
    private JTextArea textArea;
    private JButton btnPosalji;
    private JButton btnOsvezi;
    public JButton btnA;
    public JButton btnB;
    public JButton btnC;
    public JButton btnD;
    public JPanel panel_1;
    public JPanel panelKviz;
    public JPanel panel;
    public JLabel lblPitanje;
    public JLabel lblBrojPoena;
    public JLabel lblBrojPoenaText;
    public JLabel lblPoeniProtivnikaText;
    public JLabel lblPoeniProtivnika;
    public int ukupnoVreme = 10;
    public JLabel lblVreme;
    public String odgovorProtivnika = "";
    public String mojOdgovor = "";
    public String tacanOdgovor = "";
    public Timer t;
    public JLabel ikonaProtivnik;
    public String protivnik;
    public String nickname;
    public int rezultat = 0;
    public int rezultatProtivnika = 0;
    public boolean odgovorio = false;

    public KvizKlijent() {

        setTitle("Dobrodosli u kviz - UPOZNAJ EVROPU");
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                izlazniTokKaServeru.println("Ugasio prozor");
            }
        });
        setBounds(new Rectangle(0, 0, 500, 500));
        setLocationRelativeTo(null);
        getContentPane().setLayout(null);
        panelKviz = new JPanel();
        panelKviz.setVisible(false);
        panelKviz.setBounds(0, 0, 484, 461);
        getContentPane().add(panelKviz);
        panelKviz.setLayout(null);
        btnA = new JButton("");
        btnA.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                upisiUDatoteku(nickname + " je odgovorio " + btnA.getText(), "odgovori.txt");
                odgovorio = true;
                mojOdgovor = btnA.getText().substring(2);
                btnA.setBackground(Color.YELLOW);
                onemoguciButtone();
                izlazniTokKaServeru.println(btnA.getText());//slanje odgovora protivniku
            }
        });
        btnA.setBounds(50, 194, 152, 23);
        panelKviz.add(btnA);

        btnB = new JButton("");
        btnB.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                upisiUDatoteku(nickname + btnA.getText(), "odgovori.txt");
                odgovorio = true;
                mojOdgovor = btnB.getText().substring(2);
                btnB.setBackground(Color.YELLOW);
                onemoguciButtone();
                izlazniTokKaServeru.println(btnB.getText());
            }
        });
        btnB.setBounds(269, 194, 152, 23);
        panelKviz.add(btnB);

        btnC = new JButton("");
        btnC.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                upisiUDatoteku(nickname + btnA.getText(), "odgovori.txt");
                odgovorio = true;
                mojOdgovor = btnC.getText().substring(2);
                btnC.setBackground(Color.YELLOW);
                onemoguciButtone();
                izlazniTokKaServeru.println(btnC.getText());
            }
        });
        btnC.setBounds(50, 247, 152, 23);
        panelKviz.add(btnC);

        btnD = new JButton("");
        btnD.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                upisiUDatoteku(nickname + btnA.getText(), "odgovori.txt");
                odgovorio = true;
                mojOdgovor = btnD.getText().substring(2);
                btnD.setBackground(Color.YELLOW);
                onemoguciButtone();
                izlazniTokKaServeru.println(btnD.getText());
            }
        });
        btnD.setBounds(269, 247, 152, 23);
        panelKviz.add(btnD);

        lblPitanje = new JLabel("");
        lblPitanje.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblPitanje.setForeground(new Color(255, 255, 255));
        lblPitanje.setBounds(50, 52, 394, 112);
        panelKviz.add(lblPitanje);

        panel_1 = new JPanel();
        panel_1.setLayout(null);
        panel_1.setBounds(0, 360, 484, 101);
        panelKviz.add(panel_1);

        lblProtivnik = new JLabel("odgovor protivnika");
        lblProtivnik.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 11));
        lblProtivnik.setIcon(new ImageIcon(KvizKlijent.class.getResource("/slike/protivnikk.png")));
        lblProtivnik.setBounds(10, 48, 189, 35);
        panel_1.add(lblProtivnik);

        lblBrojPoenaText = new JLabel("Broj poena:");
        lblBrojPoenaText.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 15));
        lblBrojPoenaText.setBounds(209, 0, 166, 35);
        panel_1.add(lblBrojPoenaText);

        JLabel label_3 = new JLabel("");
        label_3.setBounds(254, 15, 0, 0);
        panel_1.add(label_3);

        lblBrojPoena = new JLabel("");
        lblBrojPoena.setFont(new Font("Tempus Sans ITC", Font.BOLD | Font.ITALIC, 15));
        lblBrojPoena.setBounds(387, 0, 59, 35);
        panel_1.add(lblBrojPoena);

        lblVreme = new JLabel("");
        lblVreme.setBounds(93, 0, 46, 57);
        panel_1.add(lblVreme);
        lblVreme.setFont(new Font("Tahoma", Font.BOLD, 20));
        lblVreme.setForeground(Color.RED);

        JLabel lblVremeNaslov = new JLabel("Vreme:");
        lblVremeNaslov.setForeground(Color.BLACK);
        lblVremeNaslov.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 15));
        lblVremeNaslov.setBounds(10, 0, 73, 61);
        panel_1.add(lblVremeNaslov);

        lblPoeniProtivnikaText = new JLabel("Broj poena protivnika:");
        lblPoeniProtivnikaText.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 15));
        lblPoeniProtivnikaText.setBounds(209, 46, 166, 35);
        panel_1.add(lblPoeniProtivnikaText);

        lblPoeniProtivnika = new JLabel("");
        lblPoeniProtivnika.setFont(new Font("Tempus Sans ITC", Font.BOLD | Font.ITALIC, 15));
        lblPoeniProtivnika.setBounds(387, 46, 59, 35);
        panel_1.add(lblPoeniProtivnika);

        ikonaProtivnik = new JLabel("");
        ikonaProtivnik.setVisible(false);
        ikonaProtivnik.setIcon(new ImageIcon(KvizKlijent.class.getResource("/slike/protivnikk.png")));
        ikonaProtivnik.setBounds(212, 207, 53, 54);
        panelKviz.add(ikonaProtivnik);

        JLabel lblPozadina = new JLabel("");
        lblPozadina.setIcon(new ImageIcon(KvizKlijent.class.getResource("/slike/pozadinakviza.jpeg")));
        lblPozadina.setBounds(0, 0, 484, 461);
        panelKviz.add(lblPozadina);
        panel = new JPanel();
        panel.setBackground(new Color(255, 228, 196));
        panel.setBounds(0, 0, 484, 461);
        getContentPane().add(panel);
        panel.setLayout(null);
        textField = new JTextField();
        textField.setBackground(new Color(255, 255, 224));
        textField.setBounds(10, 356, 464, 31);
        panel.add(textField);
        textField.setColumns(10);
        btnPosalji = new JButton("Posalji");
        btnPosalji.setIcon(new ImageIcon(KvizKlijent.class.getResource("/slike/send1.png")));
        btnPosalji.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                izlazniTokKaServeru.println(textField.getText());
            }
        });
        btnPosalji.setBounds(75, 398, 155, 36);
        panel.add(btnPosalji);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(10, 38, 464, 286);
        panel.add(scrollPane);

        textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setBackground(new Color(255, 255, 224));
        scrollPane.setViewportView(textArea);

        btnOsvezi = new JButton("Osvezi");
        btnOsvezi.setIcon(new ImageIcon(KvizKlijent.class.getResource("/slike/refresh.png")));
        btnOsvezi.setEnabled(false);
        btnOsvezi.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                izlazniTokKaServeru.println("PrikaziOnline");
            }
        });
        btnOsvezi.setBounds(262, 398, 155, 36);
        panel.add(btnOsvezi);
        setVisible(true);
    }

    public static void main(String[] args) {
        KvizKlijent k = new KvizKlijent();
        try {
            int port = 2222;
            if (args.length > 0) {
                port = Integer.parseInt(args[0]);
            }

            soketZaKomunikaciju = new Socket("localhost", port);
            ulazKonzola = new BufferedReader(new InputStreamReader(System.in));
            izlazniTokKaServeru = new PrintStream(soketZaKomunikaciju.getOutputStream());
            ulazniTokOdServera = new BufferedReader(new InputStreamReader(soketZaKomunikaciju.getInputStream()));

            new Thread(k).start(); //pravi se nit koja ce da cita poruke

            while (!kraj) {

            }
            soketZaKomunikaciju.close();
        } catch (UnknownHostException e) {
            System.err.println("Dont know about host");
        } catch (IOException e) {
            System.err.println("IOException: " + e);
        }
    }

    ActionListener taskPerformer = new ActionListener() {
        public void actionPerformed(ActionEvent evt) {
            if (ukupnoVreme == -1) {
                if (!odgovorio) {
                    izlazniTokKaServeru.println("");
                }
                odgovorio = true;
                t.stop();
            }
            if (ukupnoVreme > -1) {
                lblVreme.setText(ukupnoVreme + "");
                ukupnoVreme--;
            }
        }

    };
    private JLabel lblProtivnik;

    private void ispisOdgovora() throws InterruptedException {
        t.stop();
        //JOptionPane.showMessageDialog(panelKviz, "Tacan odgovor je "+tacanOdgovor + " Protivnikov odgovor je: "+odgovorProtivnika);
        prikazOdgovoraProtivnika();
        prikazTacnogOdgovora();
        ispisPoena();
        Thread.sleep(4000); // sacekaj 4 sek da bi igraci videli odgovore 
        ikonaProtivnik.setVisible(false);
        omoguciButtoneVratiBoju();
        ukupnoVreme = 10;
        odgovorProtivnika = "";
        mojOdgovor = "";
    }

    private void prikazTacnogOdgovora() {
        if (btnA.getText().contains(tacanOdgovor)) {
            btnA.setBackground(Color.green);
        }
        if (btnB.getText().contains(tacanOdgovor)) {
            btnB.setBackground(Color.green);
        }
        if (btnC.getText().contains(tacanOdgovor)) {
            btnC.setBackground(Color.green);
        }
        if (btnD.getText().contains(tacanOdgovor)) {
            btnD.setBackground(Color.green);
        }
    }

    private void prikazOdgovoraProtivnika() {
        if (odgovorProtivnika.startsWith("a)")) {
            ikonaProtivnik.setBounds(194, 175, 53, 54);
            ikonaProtivnik.setVisible(true);
        }
        if (odgovorProtivnika.startsWith("b)")) {
            ikonaProtivnik.setBounds(408, 182, 53, 54);
            ikonaProtivnik.setVisible(true);
        }
        if (odgovorProtivnika.startsWith("c)")) {
            ikonaProtivnik.setBounds(194, 233, 53, 54);
            ikonaProtivnik.setVisible(true);
        }
        if (odgovorProtivnika.startsWith("d)")) {
            ikonaProtivnik.setBounds(408, 240, 53, 54);
            ikonaProtivnik.setVisible(true);
        }

    }

    private void ispisPoena() {
        if (mojOdgovor.equals("")) {

        } else if (mojOdgovor.equals(tacanOdgovor)) {
            rezultat += 10;
        } else {
            rezultat -= 5;
        }
        lblBrojPoena.setText(Integer.toString(rezultat));

        if (odgovorProtivnika.equals("")) {

        } else if (odgovorProtivnika.substring(2).equals(tacanOdgovor)) {
            rezultatProtivnika += 10;
        } else {
            rezultatProtivnika -= 5;
        }
        lblPoeniProtivnika.setText(Integer.toString(rezultatProtivnika));

    }

    public void run() {
        String linijaOdServera;
        try {
            while ((linijaOdServera = ulazniTokOdServera.readLine()) != null) {
                textArea.setText(textArea.getText() + linijaOdServera + "\n");

                if (linijaOdServera.startsWith("Molimo sacekajte odgovor protivnika: ")) {
                    protivnik = linijaOdServera.split(" ")[4];
                }

                if (linijaOdServera.contains("dobrodosli u kviz Upoznaj Evropu")) {
                    nickname = linijaOdServera.split(" ")[0].replace(",", "");
                    btnOsvezi.setEnabled(true);
                }
                if (linijaOdServera.equals("Rezultat")) {
                    if (rezultat > rezultatProtivnika) {
                        JOptionPane.showMessageDialog(panelKviz, "Osvojili ste " + rezultat + " poena. Cestitamo, pobedili ste!");
                        rangListaGui.upisiURangListu(nickname, rezultat);
                        upisiUDatoteku(nickname + " je osvojio " + rezultat + " poena.\n", "pobednici.txt");
                        upisiUDatoteku(nickname + " je osvojio " + rezultat + " poena.\n", "rezultati.txt");
                    } else if (rezultat < rezultatProtivnika) {
                        JOptionPane.showMessageDialog(panelKviz, "Osvojili ste " + rezultat + " poena. Zao nam je, izgubili ste.!");
                        upisiUDatoteku(nickname + " je osvojio " + rezultat + " poena.\n", "rezultati.txt");
                    } else {
                        JOptionPane.showMessageDialog(panelKviz, "Osvojili ste " + rezultat + " poena. Nereseno!");
                        rangListaGui.upisiURangListu(nickname, rezultat);
                        upisiUDatoteku(nickname + " je osvojio " + rezultat + " poena.\n", "rezultati.txt");
                    }
                    rangListaGui.prikaziRangListu(panelKviz);

                }
                if (linijaOdServera.equals("PRIMANJE ODGOVORA PROTIVNIKA")) {

                    odgovorProtivnika = ulazniTokOdServera.readLine();
                    while (!odgovorio) {
                        btnA.setBackground(Color.white);
                        continue;
                    }
                    ispisOdgovora();
                    odgovorio = false;

                }

                if (linijaOdServera.startsWith("SALJEM PITANJE")) {
                    lblPitanje.setText(ulazniTokOdServera.readLine());
                    btnA.setText(ulazniTokOdServera.readLine());
                    btnB.setText(ulazniTokOdServera.readLine());
                    btnC.setText(ulazniTokOdServera.readLine());
                    btnD.setText(ulazniTokOdServera.readLine());
                    tacanOdgovor = ulazniTokOdServera.readLine();
                    t = new Timer(1000, taskPerformer);
                    t.start();
                }

                if (linijaOdServera.startsWith("Zao nam je")) {
                    btnPosalji.setEnabled(false);
                } else {
                    btnPosalji.setEnabled(true);
                }
                if (linijaOdServera.indexOf("Dovidjenja. Dodjite nam ponovo - ") == 0) {
                    kraj = true;
                    return;
                }

                if (linijaOdServera.equals("signal-prihvatiozahtev")) {
                    izlazniTokKaServeru.println("signal-prihvatiozahtev");
                    panel.setVisible(false);
                    panelKviz.setVisible(true);
                }
                if (linijaOdServera.startsWith("Stigao je zahtev za igru")) {
                    protivnik = linijaOdServera.split(" ")[6];
                    if (JOptionPane.showConfirmDialog(this, linijaOdServera, "Zahtev", 0, 3) == JOptionPane.YES_OPTION) {
                        izlazniTokKaServeru.println("YES " + protivnik);
                        panel.setVisible(false);
                        panelKviz.setVisible(true);
                    } else {
                        izlazniTokKaServeru.println("NO");
                        protivnik = null;
                    }
                }

                textField.setText("");
            }
        } catch (IOException | InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void onemoguciButtone() {
        // TODO Auto-generated method stub
        btnA.setEnabled(false);
        btnB.setEnabled(false);
        btnC.setEnabled(false);
        btnD.setEnabled(false);

    }

    private void omoguciButtoneVratiBoju() {
        // TODO Auto-generated method stub
        btnA.setEnabled(true);
        btnB.setEnabled(true);
        btnC.setEnabled(true);
        btnD.setEnabled(true);
        btnA.setBackground(Color.white);
        btnB.setBackground(Color.white);
        btnC.setBackground(Color.white);
        btnD.setBackground(Color.white);
    }

    public static void upisiUDatoteku(String s, String nazivDatoteke) {
        try {
            PrintStream out = new PrintStream(new FileOutputStream(nazivDatoteke, true));
            out.println(s);
            out.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            System.out.println(e.getMessage());
        }

    }
}
