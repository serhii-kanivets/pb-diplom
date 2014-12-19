package com.pb.kanivets.bki.swingclient.userinterface;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.table.TableModel;

public abstract class AbstractEntityFrame extends JInternalFrame {

    private final JTable table;
    private final JToolBar toolBar;

    public AbstractEntityFrame(String title, TableModel tm) {
        super(title, true, true, true, true);
        table = new JTable(tm);
        toolBar = new JToolBar();
        init();
    }

    private void init() {
        Container c = getContentPane();
        c.setLayout(new BorderLayout());
        JButton refreshBtn = new JButton("Обновить");
        refreshBtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                doRefresh();
            }
        });
        JButton modifyBtn = new JButton("Изменить");
        modifyBtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                doModify();
            }
        });
        JButton addBtn = new JButton("Добавить");
        addBtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                doAdd();
            }
        });

        table.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    doModify();
                }
            }

        });

        toolBar.add(refreshBtn);
        toolBar.add(modifyBtn);
        toolBar.add(addBtn);
        c.add(toolBar, BorderLayout.NORTH);
        c.add(new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED), BorderLayout.CENTER);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    public JTable getTable() {
        return table;
    }

    public JToolBar getToolBar() {
        return toolBar;
    }

    public abstract void doRefresh();

    public abstract void doModify();

    public abstract void doAdd();

}
