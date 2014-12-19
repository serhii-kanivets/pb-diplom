package com.pb.kanivets.bki.swingclient.userinterface.currency;

import com.pb.kanivets.bki.core.entities.Bank;
import com.pb.kanivets.bki.core.entities.Currency;
import com.pb.kanivets.bki.core.services.ICurrencyService;
import com.pb.kanivets.bki.swingclient.AppConfig;
import com.pb.kanivets.bki.swingclient.userinterface.AbstractEntityFrame;
import com.pb.kanivets.bki.swingclient.userinterface.bank.BankDetails;
import com.pb.kanivets.bki.swingclient.userinterface.bank.BanksFrame;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

public class CurrencyFrame extends AbstractEntityFrame {

    private final ICurrencyService currencyService = AppConfig.getServiceFactory().getCurrencyService();
    private final CurrencyTableModel model;

    public CurrencyFrame() {
        super("Справочник валют", new CurrencyTableModel());
        model = (CurrencyTableModel) getTable().getModel();
    }

    @Override
    public void doRefresh() {
        new SwingWorker<List<Currency>, Void>() {

            @Override
            protected List<Currency> doInBackground() throws Exception {
                return currencyService.listCurrencies();
            }

            @Override
            protected void done() {
                try {
                    model.setList(get());
                } catch (InterruptedException | ExecutionException ex) {
                    Logger.getLogger(BanksFrame.class.getName()).log(Level.SEVERE, null, ex);
                    JOptionPane.showMessageDialog(CurrencyFrame.this,
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
            CurrencyDetails cd = new CurrencyDetails(model.get(selectedRow));
            cd.setVisible(true);
            if (cd.isSaved()) {
                model.modify(selectedRow, cd.getCurr());
            }
        }
    }

    @Override
    public void doAdd() {
        CurrencyDetails cd = new CurrencyDetails();
        cd.setVisible(true);
        if (cd.isSaved()) {
            model.add(cd.getCurr());
        }
    }

}
