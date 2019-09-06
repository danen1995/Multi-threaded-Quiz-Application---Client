package model;

import java.util.LinkedList;

import javax.swing.table.AbstractTableModel;
import ranglista.*;

public class IgraciTableModel extends AbstractTableModel {

    private final String[] kolone = new String[]{"Rang", "Ime", "Poeni"};

    private LinkedList<Igrac> igraci;

    public IgraciTableModel(LinkedList<Igrac> igraci) {
        if (igraci == null) {
            this.igraci = new LinkedList<>();
        } else {
            this.igraci = igraci;
        }
    }

    @Override
    public int getRowCount() {
        // TODO Auto-generated method stub
        return igraci.size();
    }

    @Override
    public int getColumnCount() {
        // TODO Auto-generated method stub
        return kolone.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Igrac u = igraci.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return rowIndex + 1;
            case 1:
                return u.getIme();
            case 2:
                return u.getBrojPoena();
            default:
                return "NN";
        }
    }

    @Override
    public String getColumnName(int column) {
        return kolone[column];
    }

    public void ucitajIgrace(LinkedList<Igrac> ucesnici) {
        this.igraci = ucesnici;
        fireTableDataChanged();
    }

    public Igrac getUcesnikByIndex(int index) {
        return igraci.get(index);
    }

    public LinkedList<Igrac> vratiIgrace() {
        return igraci;
    }

}
