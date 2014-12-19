package com.pb.kanivets.bki.swingclient.userinterface;

import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class CaptionedTextField extends JPanel{
    private final JLabel label;
    private final JTextField textField;

    public CaptionedTextField(JLabel label, JTextField textField) {
        this.label = label;
        this.textField = textField;
        this.textField.setColumns(17);
        setLayout(new FlowLayout(FlowLayout.RIGHT));
        add(this.label);
        add(this.textField);
    }
    
            
    
}
