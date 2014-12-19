package com.pb.kanivets.bki.swingclient.userinterface.bank;

import com.pb.kanivets.bki.core.entities.Bank;
import com.pb.kanivets.bki.core.services.IBankService;
import com.pb.kanivets.bki.swingclient.AppConfig;
import com.pb.kanivets.bki.swingclient.userinterface.CaptionedTextField;
import com.pb.kanivets.bki.swingclient.userinterface.credits.CreditDetails;
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

public class BankDetails extends JDialog {

    private final JTextField mfoField = new JTextField();
    private final JTextField nameField = new JTextField();
    private final IBankService bankService = AppConfig.getServiceFactory().getBankService();
    private Bank bank = null;
    private boolean saved = false;

    public BankDetails() {
        super(AppConfig.getMainFrame(), "Новый банк", true);
        init();
    }

    public BankDetails(Bank bank) {
        super(AppConfig.getMainFrame(), "Банк " + bank.getMfo(), true);
        this.bank = bank;
        init();
        showData();
    }

    private void init() {
        Container c = getContentPane();
        c.setLayout(new BoxLayout(c, BoxLayout.Y_AXIS));
        c.add(new CaptionedTextField(new JLabel("МФО"), mfoField));
        c.add(new CaptionedTextField(new JLabel("Название"), nameField));
        JButton saveBtn = new JButton("Сохранить");

        saveBtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                saved = false;
                new SwingWorker<Bank, Void>() {

                    @Override
                    protected Bank doInBackground() throws Exception {
                        Bank bank = parseData();                        
                        bankService.updateBank(bank);
                        return bank;
                    }

                    @Override
                    protected void done() {
                        try {
                            Bank result = get();
                            bank = result;
                            saved = true;
                            showData();
                            JOptionPane.showMessageDialog(BankDetails.this,
                                    "Сохранено", "Информация",
                                    JOptionPane.INFORMATION_MESSAGE);
                        } catch (InterruptedException | ExecutionException ex) {
                            Logger.getLogger(BankDetails.class.getName()).log(Level.SEVERE, null, ex);
                            JOptionPane.showMessageDialog(BankDetails.this,
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
                BankDetails.this.dispose();
            }
        });
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonsPanel.add(closeBtn);
        buttonsPanel.add(saveBtn);
        c.add(buttonsPanel);
        setSize(400, 200);
        setLocationRelativeTo(AppConfig.getMainFrame());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    private Bank parseData() {
        Bank bank = new Bank();
        bank.setMfo(Integer.parseInt(mfoField.getText()));        
        bank.setName(nameField.getText());
        return bank;
    }

    private void showData() {
        mfoField.setText("" + bank.getMfo());
        nameField.setText(bank.getName());
    }

    public Bank getBank() {
        return bank;
    }

    public boolean isSaved() {
        return saved;
    }

}
