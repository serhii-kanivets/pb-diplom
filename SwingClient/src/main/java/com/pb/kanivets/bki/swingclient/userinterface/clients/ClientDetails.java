package com.pb.kanivets.bki.swingclient.userinterface.clients;

import com.pb.kanivets.bki.core.entities.Client;
import com.pb.kanivets.bki.core.exceptions.BusinessException;
import com.pb.kanivets.bki.core.services.IClientService;
import com.pb.kanivets.bki.swingclient.AppConfig;
import com.pb.kanivets.bki.swingclient.userinterface.CaptionedTextField;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

public class ClientDetails extends JDialog {

    private Client client = null;
    private boolean saved = false;
    private final JTextField idField = new JTextField();
    private final JTextField innField = new JTextField();
    private final JTextField sNameField = new JTextField();
    private final JTextField fNameField = new JTextField();
    private final JTextField mNameField = new JTextField();
    private final JTextField dateField = new JTextField();
    private final JTextField passpField = new JTextField();
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY.MM.dd");
    private final IClientService clientService = AppConfig.getServiceFactory().getClientService();

    public ClientDetails() {
        super(AppConfig.getMainFrame(), "Новый клиент");
        init();
    }

    public ClientDetails(Client c) {
        super(AppConfig.getMainFrame(), "Редактиовать информацию о клиенте");
        this.client = c;
        init();
        showData();
    }

    private void init() {
        Container c = getContentPane();
        c.setLayout(new BoxLayout(c, BoxLayout.Y_AXIS));

        idField.setEditable(false);
        c.add(new CaptionedTextField(new JLabel("ИД"), idField));
        c.add(new CaptionedTextField(new JLabel("ИНН"), innField));
        c.add(new CaptionedTextField(new JLabel("Фамилия"), sNameField));
        c.add(new CaptionedTextField(new JLabel("Имя"), fNameField));
        c.add(new CaptionedTextField(new JLabel("Отчество"), mNameField));
        c.add(new CaptionedTextField(new JLabel("Дата рождения"), dateField));
        c.add(new CaptionedTextField(new JLabel("Серия и номер пасспорта"), passpField));
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton saveBtn = new JButton("Сохранить");
        saveBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new SwingWorker<Client, Void>() {

                    @Override
                    protected Client doInBackground() throws Exception {
                        Client newInf = parseData();
                        if (newInf.getClientId() == 0) {
                            return clientService.addClient(newInf);
                        } else {
                            clientService.updateClient(newInf);
                        }
                        return newInf;
                    }

                    @Override
                    protected void done() {
                        try {
                            Client result = get();
                            setSaved(true);
                            setClient(result);
                            showData();
                            JOptionPane.showMessageDialog(ClientDetails.this,
                                    "Сохранено", "Информация",
                                    JOptionPane.INFORMATION_MESSAGE);
                        } catch (InterruptedException | ExecutionException ex) {
                            Logger.getLogger(ClientDetails.class.getName()).log(Level.SEVERE, null, ex);
                            JOptionPane.showMessageDialog(ClientDetails.this,
                                    ex.getMessage(), "Ошибка",
                                    JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }.execute();

            }
        });

        JButton closeButton = new JButton("Закрыть");
        closeButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                ClientDetails.this.dispose();
            }
        });
        buttonPanel.add(saveBtn);
        buttonPanel.add(closeButton);
        c.add(buttonPanel);
        setSize(400, 300);
        setLocationRelativeTo(AppConfig.getMainFrame());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setModal(true);
    }

    private void showData() {
        idField.setText("" + client.getClientId());
        innField.setText(client.getInn());
        sNameField.setText(client.getsName());
        fNameField.setText(client.getfName());
        mNameField.setText(client.getmName());
        dateField.setText(dateFormat.format(client.getBirthDate()));
        passpField.setText(client.getPassp());
    }

    private Client parseData() {
        Client newClient = new Client();
        if (idField.getText().length() > 0) {
            newClient.setClientId(Integer.parseInt(idField.getText()));
        }
        newClient.setInn(innField.getText());
        newClient.setsName(sNameField.getText());
        newClient.setfName(fNameField.getText());
        newClient.setmName(mNameField.getText());
        try {
            newClient.setBirthDate(dateFormat.parse(dateField.getText()));
        } catch (ParseException ex) {
            throw new BusinessException("Неправильный формат даты (YYYY.MM.dd)");
        }
        newClient.setPassp(passpField.getText());
        return newClient;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public boolean isSaved() {
        return saved;
    }

    public void setSaved(boolean isSaved) {
        this.saved = isSaved;
    }

}
