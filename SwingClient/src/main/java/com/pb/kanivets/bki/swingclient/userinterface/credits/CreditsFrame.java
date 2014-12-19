package com.pb.kanivets.bki.swingclient.userinterface.credits;

import com.pb.kanivets.bki.core.entities.Client;
import com.pb.kanivets.bki.core.entities.Credit;
import com.pb.kanivets.bki.core.services.ICreditService;
import com.pb.kanivets.bki.swingclient.AppConfig;
import com.pb.kanivets.bki.swingclient.userinterface.AbstractEntityFrame;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

public class CreditsFrame extends AbstractEntityFrame {

    private final Client client;
    private final ICreditService service = AppConfig.getServiceFactory().getCreditService();
    private final CreditsTableModel model;

    public CreditsFrame() {
        super("Кредиты", new CreditsTableModel());
        this.client = null;
        this.model = (CreditsTableModel) getTable().getModel();
    }

    public CreditsFrame(Client c) {
        super("Кредиты клиента: " + c.getsName() + " " + c.getfName(), new CreditsTableModel());
        this.client = c;
        this.model = (CreditsTableModel) getTable().getModel();
    }

    @Override
    public void doRefresh() {
        new SwingWorker<List<Credit>, Void>() {

            @Override
            protected List<Credit> doInBackground() throws Exception {
                if (client != null) {
                    return service.listCredits(client.getClientId());
                } else {
                    return service.listCredits();
                }
            }

            @Override
            protected void done() {
                try {
                    model.setList(get());
                } catch (InterruptedException | ExecutionException ex) {
                    Logger.getLogger(CreditsFrame.class.getName()).log(Level.SEVERE, null, ex);
                    JOptionPane.showMessageDialog(CreditsFrame.this,
                            ex.getMessage(), "Ошибка",
                            JOptionPane.ERROR_MESSAGE);
                }
            }

        }.execute();

    }

    @Override
    public void doModify() {
        int selectedRow = getTable().getSelectedRow();
        if (selectedRow >= 0) {
            CreditDetails cd = new CreditDetails(model.get(selectedRow));
            cd.setVisible(true);
            if (cd.isSaved()) {
                model.set(selectedRow, cd.getCredit());
            }
        }
    }

    @Override
    public void doAdd() {
        CreditDetails cd = new CreditDetails();
            cd.setVisible(true);
            if (cd.isSaved()) {
                model.add(cd.getCredit());
            }
    }

}
