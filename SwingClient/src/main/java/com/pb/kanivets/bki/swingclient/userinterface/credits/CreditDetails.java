package com.pb.kanivets.bki.swingclient.userinterface.credits;

import com.pb.kanivets.bki.core.entities.Credit;
import com.pb.kanivets.bki.core.exceptions.BusinessException;
import com.pb.kanivets.bki.core.services.ICreditService;
import com.pb.kanivets.bki.swingclient.AppConfig;
import com.pb.kanivets.bki.swingclient.userinterface.CaptionedTextField;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

public class CreditDetails extends JDialog {

    private Credit credit;
    private boolean saved = false;
    private final ICreditService creditService = AppConfig.getServiceFactory().getCreditService();

    private final JTextField idField = new JTextField();
    private final JTextField clientIdField = new JTextField();
    private final JTextField mfoField = new JTextField();
    private final JTextField currIdField = new JTextField();
    private final JTextField initSumField = new JTextField();
    private final JTextField issueDateField = new JTextField();
    private final JTextField bodyField = new JTextField();
    private final JTextField closeDateField = new JTextField();
    private final JTextField delayField = new JTextField();
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY.MM.dd");

    public CreditDetails(Credit c) {
        super(AppConfig.getMainFrame(), "Редактировать кредит " + c.getId(), true);
        this.credit = c;
        init();
        showData();
    }

    public CreditDetails() {
        super(AppConfig.getMainFrame(), "Новый кредит", true);
        credit = null;
        init();
    }

    private void init() {
        Container c = getContentPane();
        c.setLayout(new BoxLayout(c, BoxLayout.Y_AXIS));
        idField.setEditable(false);
        c.add(new CaptionedTextField(new JLabel("ИД"), idField));
        c.add(new CaptionedTextField(new JLabel("ИД клиента"), clientIdField));
        c.add(new CaptionedTextField(new JLabel("Код банка"), mfoField));
        c.add(new CaptionedTextField(new JLabel("Код валюты"), currIdField));
        c.add(new CaptionedTextField(new JLabel("Начальная сумма"), initSumField));
        c.add(new CaptionedTextField(new JLabel("Дата выдачи"), issueDateField));
        c.add(new CaptionedTextField(new JLabel("Тело кредита"), bodyField));
        c.add(new CaptionedTextField(new JLabel("Дата закрытия"), closeDateField));
        c.add(new CaptionedTextField(new JLabel("Выходы на просрочку"), delayField));
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton saveBtn = new JButton("Сохранить");
        saveBtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                new SwingWorker<Credit, Void>() {
                    @Override
                    protected Credit doInBackground() throws Exception {
                        Credit c = parseData();
                        if (c.getId() == 0) {
                            return creditService.addCredit(c);
                        } else {
                            creditService.updateCredit(c);
                        }
                        return c;
                    }

                    @Override
                    protected void done() {
                        try {
                            Credit result = get();
                            saved = true;
                            credit = result;                             
                            showData();
                            JOptionPane.showMessageDialog(CreditDetails.this,
                                    "Сохранено", "Информация",
                                    JOptionPane.INFORMATION_MESSAGE);
                        } catch (InterruptedException | ExecutionException ex) {
                            Logger.getLogger(CreditDetails.class.getName()).log(Level.SEVERE, null, ex);
                            JOptionPane.showMessageDialog(CreditDetails.this,
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
                CreditDetails.this.dispose();
            }
        });
        btnPanel.add(closeBtn);
        btnPanel.add(saveBtn);
        c.add(btnPanel);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(400, 400);
        setLocationRelativeTo(AppConfig.getMainFrame());
    }

    private Credit parseData() {
        Credit c = new Credit();
        try {
            if (idField.getText().length() > 0) {
                c.setId(Integer.parseInt(idField.getText()));
            }
            c.setClientId(Integer.parseInt(clientIdField.getText()));
            c.setMfo(Integer.parseInt(mfoField.getText()));
            c.setCurrId(currIdField.getText());
            c.setInitSum(new BigDecimal(initSumField.getText()));
            c.setIssueDate(dateFormat.parse(issueDateField.getText()));
            c.setBody(new BigDecimal(bodyField.getText()));
            c.setCloseDate(closeDateField.getText().length() == 0 ? null : dateFormat.parse(closeDateField.getText()));
            c.setDelay(delayField.getText());
        } catch (ParseException ex) {
            throw new BusinessException("Неправильный формат даты (YYYY.MM.dd)");
        }
        return c;
    }

    private void showData() {
        idField.setText("" + credit.getId());
        clientIdField.setText("" + credit.getClientId());
        mfoField.setText("" + credit.getMfo());
        currIdField.setText(credit.getCurrId());
        initSumField.setText("" + credit.getInitSum());
        issueDateField.setText(dateFormat.format(credit.getIssueDate()));
        bodyField.setText("" + credit.getBody());
        String closeDate = credit.getCloseDate() == null ? "" : dateFormat.format(credit.getCloseDate());
        closeDateField.setText(closeDate);
        delayField.setText("" + credit.getDelay());
    }

    public Credit getCredit() {
        return credit;
    }

    public boolean isSaved() {
        return saved;
    }
}
