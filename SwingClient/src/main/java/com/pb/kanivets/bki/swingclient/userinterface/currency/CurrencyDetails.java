package com.pb.kanivets.bki.swingclient.userinterface.currency;

import com.pb.kanivets.bki.core.entities.Bank;
import com.pb.kanivets.bki.core.entities.Currency;
import com.pb.kanivets.bki.core.services.ICurrencyService;
import com.pb.kanivets.bki.swingclient.AppConfig;
import com.pb.kanivets.bki.swingclient.userinterface.CaptionedTextField;
import com.pb.kanivets.bki.swingclient.userinterface.bank.BankDetails;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingWorker;

public class CurrencyDetails extends JDialog {

    private final JTextField idField = new JTextField();
    private final JTextField nameField = new JTextField();
    private final ICurrencyService currService = AppConfig.getServiceFactory().getCurrencyService();
    private Currency curr = null;
    private boolean saved = false;

    public CurrencyDetails() {
        super(AppConfig.getMainFrame(), "Новая валюта", true);
        init();
    }

    public CurrencyDetails(Currency curr) {
        super(AppConfig.getMainFrame(), "Валюта " + curr.getCurrId(), true);
        this.curr = curr;
        init();
        showData();
    }

    private void init() {
        Container c = getContentPane();
        c.setLayout(new BoxLayout(c, BoxLayout.Y_AXIS));
        c.add(new CaptionedTextField(new JLabel("Код"), idField));
        c.add(new CaptionedTextField(new JLabel("Описание"), nameField));
        JButton saveBtn = new JButton("Сохранить");
        saveBtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                saved = false;
                new SwingWorker<Currency, Void>() {

                    @Override
                    protected Currency doInBackground() throws Exception {
                        Currency curr = parseData();
                        currService.updateCurrency(curr);
                        return curr;
                    }

                    @Override
                    protected void done() {
                        try {
                            Currency result = get();
                            saved = true;
                            curr = result;
                            showData();
                            JOptionPane.showMessageDialog(CurrencyDetails.this,
                                    "Сохранено", "Информация",
                                    JOptionPane.INFORMATION_MESSAGE);
                        } catch (InterruptedException | ExecutionException ex) {
                            Logger.getLogger(CurrencyDetails.class.getName()).log(Level.SEVERE, null, ex);
                            JOptionPane.showMessageDialog(CurrencyDetails.this,
                                    ex.getMessage(), "Ошибка",
                                    JOptionPane.ERROR_MESSAGE);
                        }
                    }

                }.execute();

            }
        });
        JButton closeBtn = new JButton("Закрыть");
        closeBtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                CurrencyDetails.this.dispose();
            }
        });
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonsPanel.add(saveBtn);
        buttonsPanel.add(closeBtn);
        c.add(buttonsPanel);
        setSize(400, 200);
        setLocationRelativeTo(AppConfig.getMainFrame());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    private Currency parseData() {
        Currency curr = new Currency();
        curr.setCurrId(idField.getText());
        curr.setName(nameField.getText());
        return curr;
    }

    private void showData() {
        idField.setText(curr.getCurrId());
        nameField.setText(curr.getName());
    }

    public Currency getCurr() {
        return curr;
    }

    public boolean isSaved() {
        return saved;
    }

}
