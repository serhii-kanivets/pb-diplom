package com.pb.kanivets.bki.swingclient.userinterface.bank;

import com.pb.kanivets.bki.core.entities.Bank;
import com.pb.kanivets.bki.core.services.IBankService;
import com.pb.kanivets.bki.swingclient.AppConfig;
import com.pb.kanivets.bki.swingclient.userinterface.AbstractEntityFrame;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

public class BanksFrame extends AbstractEntityFrame {

    private final IBankService bankService = AppConfig.getServiceFactory().getBankService();
    private final BanksTableModel model;

    public BanksFrame() {
        super("Справочник банков", new BanksTableModel());
        model = (BanksTableModel) getTable().getModel();
    }

    @Override
    public void doRefresh() {
        new SwingWorker<List<Bank>, Void>() {

            @Override
            protected List<Bank> doInBackground() throws Exception {
                return bankService.listBanks();
            }

            @Override
            protected void done() {
                try {
                    model.setList(get());
                } catch (InterruptedException | ExecutionException ex) {
                    Logger.getLogger(BanksFrame.class.getName()).log(Level.SEVERE, null, ex);
                    JOptionPane.showMessageDialog(BanksFrame.this,
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
            BankDetails bd = new BankDetails(model.get(selectedRow));
            bd.setVisible(true);
            if (bd.isSaved()) {
                model.modify(selectedRow, bd.getBank());
            }
        }
    }

    @Override
    public void doAdd() {
        BankDetails bd = new BankDetails();
        bd.setVisible(true);
        if (bd.isSaved()) {
            model.add(bd.getBank());
        }
    }

}
