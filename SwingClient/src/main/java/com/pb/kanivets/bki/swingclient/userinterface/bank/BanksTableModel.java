package com.pb.kanivets.bki.swingclient.userinterface.bank;

import com.pb.kanivets.bki.core.entities.Bank;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

public class BanksTableModel implements TableModel {

    private List<Bank> banks = new ArrayList<>();
    private final String headers[] = {"МФО", "Название"};
    private final Class[] columnClasses = {Integer.class, String.class};
    private final Set<TableModelListener> listeners = new HashSet<>();

    public void setList(List<Bank> banks) {
        this.banks = banks;
        fireTableModelChanged(new TableModelEvent(this));
    }

    public Bank get(int rowIndex) {
        return banks.get(rowIndex);
    }

    public void add(Bank bank) {
        banks.add(bank);
        fireTableModelChanged(new TableModelEvent(this));
    }

    public void modify(int rowIndex, Bank newInfo) {
        banks.set(rowIndex, newInfo);
        fireTableModelChanged(new TableModelEvent(this));
    }

    public void delete(int rowIndex) {
        banks.remove(rowIndex);
        fireTableModelChanged(new TableModelEvent(this));
    }

    @Override
    public int getRowCount() {
        return banks.size();
    }

    @Override
    public int getColumnCount() {
        return headers.length;
    }

    @Override
    public String getColumnName(int columnIndex) {
        return headers[columnIndex];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return columnClasses[columnIndex];
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Bank bank = banks.get(rowIndex);
        if (columnIndex == 0) {
            return bank.getMfo();
        } else {
            return bank.getName();
        }
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        Bank bank = banks.get(rowIndex);
        if (columnIndex == 0) {
            bank.setMfo((Integer) aValue);
        } else {
            bank.setName((String) aValue);
        }
    }

    @Override
    public void addTableModelListener(TableModelListener l) {
        listeners.add(l);
    }

    @Override
    public void removeTableModelListener(TableModelListener l) {
        listeners.remove(l);
    }

    public void fireTableModelChanged(TableModelEvent e) {
        for (TableModelListener listener : listeners) {
            listener.tableChanged(e);
        }
    }

}
