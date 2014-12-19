package com.pb.kanivets.bki.swingclient.userinterface.clients;

import com.pb.kanivets.bki.core.entities.Client;
import com.pb.kanivets.bki.core.services.IClientService;
import com.pb.kanivets.bki.swingclient.AppConfig;
import com.pb.kanivets.bki.swingclient.userinterface.credits.CreditsFrame;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.SwingWorker;
import javax.swing.event.AncestorListener;
import javax.swing.event.MouseInputAdapter;
import javax.swing.event.TableModelEvent;
import javax.swing.table.TableModel;

public class ClientsFrame extends JInternalFrame {

    private final JToolBar toolBar;
    private final ClientsTableModel model;
    private final JTable table;
    private final IClientService clientsStorage;
    private final String[] parameters = {"ИНН", "Пасспорт", "ФИО"};

    public ClientsFrame() throws HeadlessException {
        super("Клиенты", true, true, true, true);
        toolBar = new JToolBar();
        model = new ClientsTableModel();
        clientsStorage = AppConfig.getServiceFactory().getClientService();
        table = new JTable(model);
        init();
    }

    private void init() {
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(200, 200);
        JButton refreshButton = new JButton("Обновить");
        refreshButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                new SwingWorker<List<Client>, Void>() {
                    @Override
                    protected List<Client> doInBackground() throws Exception {
                        return clientsStorage.listClients();
                    }

                    @Override
                    protected void done() {
                        try {
                            model.setData(get());
                        } catch (InterruptedException | ExecutionException ex) {
                            Logger.getLogger(ClientsFrame.class.getName()).log(Level.SEVERE, null, ex);
                            JOptionPane.showMessageDialog(ClientsFrame.this,
                                    ex.getMessage(), "Ошибка",
                                    JOptionPane.ERROR_MESSAGE);
                        }
                    }

                }.execute();
            }
        });
        JButton editButton = new JButton("Изменить");

        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateClient();
            }
        }
        );

        table.addMouseListener(
                new MouseInputAdapter() {

                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if (e.getClickCount() == 2) {
                            updateClient();
                        }
                    }

                });

        JButton addButton = new JButton("Добавить клиента");

        addButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                ClientDetails cd = new ClientDetails();
                cd.setVisible(true);
                if (cd.isSaved()) {
                    model.addClient(cd.getClient());
                }
            }
        }
        );

        JButton deleteButton = new JButton("Удалить");

        deleteButton.addActionListener(
                new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        final int selectedRow = table.getSelectedRow();
                        if (selectedRow >= 0) {
                            new SwingWorker() {

                                @Override
                                protected Object doInBackground() throws Exception {
                                    clientsStorage.deleteClient(model.getClient(selectedRow).getClientId());
                                    return new Object();
                                }

                                @Override
                                protected void done() {
                                    try {
                                        get();
                                        model.removeClient(selectedRow);
                                        JOptionPane.showMessageDialog(ClientsFrame.this,
                                                "Пользователь удалён", "Информация",
                                                JOptionPane.INFORMATION_MESSAGE);
                                    } catch (InterruptedException | ExecutionException ex) {
                                        Logger.getLogger(ClientDetails.class.getName()).log(Level.SEVERE, null, ex);
                                        JOptionPane.showMessageDialog(ClientsFrame.this,
                                                ex.getMessage(), "Ошибка",
                                                JOptionPane.ERROR_MESSAGE);
                                    }
                                }

                            }.execute();
                        }
                    }
                }
        );

        JButton creditsButton = new JButton("Кредиты");
        creditsButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                int rowIndex = table.getSelectedRow();
                if (rowIndex >= 0) {
                    CreditsFrame cf = new CreditsFrame(model.getClient(rowIndex));
                    AppConfig.getMainFrame().addFrameOnDesktop(cf);
                }
            }
        });
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        final JTextField queryField = new JTextField();
        queryField.setColumns(15);
        JButton searchBtn = new JButton("Поиск");
        final JComboBox searchParameter = new JComboBox(parameters);
        searchBtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                final int parameterIdx = searchParameter.getSelectedIndex();
                final String query = queryField.getText().trim();
                if (query.length() > 0) {
                    new SwingWorker<List<Client>, Void>() {

                        @Override
                        protected List<Client> doInBackground() throws Exception {
                            List<Client> result = new ArrayList<>();
                            switch (parameterIdx) {
                                case 0: {
                                    return clientsStorage.searchByInn(query);
                                }
                                case 1: {
                                    return clientsStorage.searchByPassp(query);
                                }
                                case 2: {
                                    String[] params = query.split("\\s+");
                                    String sName = "";
                                    String fName = "";
                                    String mName = "";
                                    if (params.length > 0) {
                                        sName = params[0];
                                    }
                                    if (params.length > 1) {
                                        fName = params[1];
                                    }
                                    if (params.length > 2) {
                                        mName = params[2];
                                    }
                                    //System.out.println("sName=" + sName + " fName="+ fName + " mName=" +mName);
                                    return clientsStorage.searchByFIO(sName, fName, mName);
                                }
                            }
                            return result;
                        }

                        @Override
                        protected void done() {
                            try {
                                model.setData(get());
                            } catch (InterruptedException | ExecutionException ex) {
                                Logger.getLogger(ClientsFrame.class.getName()).log(Level.SEVERE, null, ex);
                                JOptionPane.showMessageDialog(ClientsFrame.this,
                                        ex.getMessage(), "Ошибка",
                                        JOptionPane.ERROR_MESSAGE);
                            }
                        }

                    }.execute();
                }
            }
        }
        );
        searchPanel.add(queryField);

        searchPanel.add(searchBtn);

        searchPanel.add(searchParameter);

        toolBar.add(refreshButton);

        toolBar.add(editButton);

        toolBar.add(addButton);

        toolBar.add(deleteButton);

        toolBar.add(creditsButton);

        toolBar.add(searchPanel);
        JScrollPane sp = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        setLayout(
                new BorderLayout());
        add(toolBar, BorderLayout.NORTH);

        add(sp, BorderLayout.CENTER);
    }

    private void updateClient() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            Client currClient = model.getClient(selectedRow);
            ClientDetails cd = new ClientDetails(currClient);
            cd.setVisible(true);
            if (cd.isSaved()) {
                model.setClient(selectedRow, cd.getClient());
            }
        }
    }

}
