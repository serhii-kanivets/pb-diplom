package com.pb.kanivets.bki.swingclient.userinterface;

import com.pb.kanivets.bki.swingclient.userinterface.bank.BanksFrame;
import com.pb.kanivets.bki.swingclient.userinterface.clients.ClientsFrame;
import com.pb.kanivets.bki.swingclient.userinterface.credits.CreditsFrame;
import com.pb.kanivets.bki.swingclient.userinterface.currency.CurrencyFrame;
import java.awt.BorderLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class MainWindow extends JFrame {

    private final JMenuBar menuBar;
    private final JDesktopPane desktop;

    public MainWindow() throws HeadlessException {
        super("Бюро кредитных историй");
        menuBar = new JMenuBar();
        desktop = new JDesktopPane();
        init();
    }

    private void init() {
        JMenu mainMenu = new JMenu("Главное");
        JMenu entityMenu = new JMenu("Справочники");
        JMenuItem clientsMenuItem = new JMenuItem("Клиенты");
        clientsMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JInternalFrame clientsFrame = new ClientsFrame();
                addFrameOnDesktop(clientsFrame);
            }
        });

        JMenuItem creditsMenuItem = new JMenuItem("Кредиты");

        creditsMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JInternalFrame cf = new CreditsFrame();
                addFrameOnDesktop(cf);
            }
        });

        JMenuItem banksMenuItem = new JMenuItem("Банки");
        banksMenuItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                addFrameOnDesktop(new BanksFrame());
            }
        });
        JMenuItem currenciesMenuItem = new JMenuItem("Валюты");
        currenciesMenuItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                addFrameOnDesktop(new CurrencyFrame());
            }
        });
        entityMenu.add(clientsMenuItem);
        entityMenu.add(creditsMenuItem);
        entityMenu.add(banksMenuItem);
        entityMenu.add(currenciesMenuItem);
        //menuBar.add(mainMenu);
        menuBar.add(entityMenu);
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(desktop, BorderLayout.CENTER);
        setJMenuBar(menuBar);
        setSize(800, 600);
        setLocation(200, 150);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    public void addFrameOnDesktop(JInternalFrame frame) {
        desktop.add(frame);
        try {
            frame.setMaximum(true);

        } catch (PropertyVetoException ex) {
            Logger.getLogger(MainWindow.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        frame.setVisible(true);
    }
}
