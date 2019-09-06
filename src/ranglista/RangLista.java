package ranglista;

import java.util.LinkedList;

public class RangLista {

    LinkedList<Igrac> igraci;

    public RangLista() {
        igraci = new LinkedList<>();
    }

    public void sacuvajIgraca(Igrac ig) {
        if (igraci.isEmpty()) {
            igraci.add(ig);
            return;
        } else {
            for (int i = 0; i < igraci.size(); i++) {
                if (ig.brojPoena > igraci.get(i).getBrojPoena()) {
                    igraci.add(i, ig);
                    return;
                }
            }
        }
        igraci.add(ig);
    }

    public LinkedList<Igrac> vratiIgrace() {
        return igraci;
    }
}
