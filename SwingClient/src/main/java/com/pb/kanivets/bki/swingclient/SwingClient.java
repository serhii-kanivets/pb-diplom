package com.pb.kanivets.bki.swingclient;

import com.pb.kanivets.bki.swingclient.userinterface.MainWindow;
import java.awt.EventQueue;

public class SwingClient {

    public static void main(String[] args) {
        AppConfig.init(new SpringServiceFactory(), new MainWindow());
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                AppConfig.getMainFrame().setVisible(true);
            }
        }
        );
    }

}
