package klijent;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.util.LinkedList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import db.DatabaseConnection;
import model.IgraciTableModel;
import ranglista.Igrac;
import ranglista.RangLista;

import javax.swing.JTable;
import javax.swing.JScrollPane;

public class RangListaGui extends JFrame {

    DatabaseConnection db = new DatabaseConnection();

    private JPanel contentPane;
    private JTable table;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    RangListaGui frame = new RangListaGui();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public RangListaGui() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);

        JPanel panel = new JPanel();
        contentPane.add(panel, BorderLayout.CENTER);
        panel.setLayout(null);

        table = getTable();
        table.setBounds(10, 11, 404, 212);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(10, 11, 404, 225);
        panel.add(scrollPane);
        scrollPane.setViewportView(table);
    }

    public JTable getTable() {
        if (table == null) {
            table = new JTable();
            table.setGridColor(new Color(0, 0, 0));
            table.setFont(new Font("Trebuchet MS", Font.BOLD, 11));
            table.setBackground(new Color(255, 255, 255));
            IgraciTableModel model = new IgraciTableModel(null);
            table.setModel(model);
        }
        return table;
    }

    public void osveziTabelu(JTable table, LinkedList<Igrac> igraci) {
        IgraciTableModel model = (IgraciTableModel) table.getModel();
        model.ucitajIgrace(igraci);
    }

    public void upisiURangListu(String nickname, int brojPoena) {
        Igrac i = new Igrac(nickname, brojPoena);
        db.ubaciIgraca(nickname, brojPoena);

    }

    public void prikaziRangListu(JPanel panelKviz) {
        LinkedList<Igrac> igraci = db.ucitajRangListu();
        RangListaGui prikaz = new RangListaGui();
        prikaz.setVisible(true);
        prikaz.setLocationRelativeTo(panelKviz);
        panelKviz.setVisible(false);
        osveziTabelu(prikaz.getTable(), igraci);
        prikaz.setTitle("Prikaz ucesnika");
    }
}
