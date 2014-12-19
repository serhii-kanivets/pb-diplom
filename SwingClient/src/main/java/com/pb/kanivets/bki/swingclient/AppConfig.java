package com.pb.kanivets.bki.swingclient;

import com.pb.kanivets.bki.core.services.IServiceFactory;
import com.pb.kanivets.bki.swingclient.userinterface.MainWindow;
import javax.swing.JFrame;

public class AppConfig {
    private static IServiceFactory serviceFactory;
    private static MainWindow mainFrame;
    public static void init(IServiceFactory sf, MainWindow frame){
        serviceFactory = sf;
        mainFrame = frame;
    }
    
    public static IServiceFactory getServiceFactory(){
        return serviceFactory;
    }
    
    public static MainWindow getMainFrame(){
        return mainFrame;
    }
}
