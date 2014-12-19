package com.pb.kanivets.bki.swingclient.userinterface.currency;

import com.pb.kanivets.bki.core.entities.Currency;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

public class CurrencyTableModel implements TableModel {

    private List<Currency> currencies = new ArrayList<>();
    private final String[] headers = {"Код", "Описание"};
    private final Class[] columnClasses = {String.class, String.class};
    private final Set<TableModelListener> listeners = new HashSet<>();

    public void setList(List<Currency> currencies) {
        this.currencies = currencies;
        fireTableChanged(new TableModelEvent(this));
    }

    public Currency get(int rowIndex) {
        return currencies.get(rowIndex);
    }

    public void add(Currency curr) {
        currencies.add(curr);
        fireTableChanged(new TableModelEvent(this));
    }

    public void modify(int rowIndex, Currency newInfo) {
        currencies.set(rowIndex, newInfo);
        fireTableChanged(new TableModelEvent(this));
    }

    @Override
    public int getRowCount() {
        return currencies.size();
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
        Currency curr = currencies.get(rowIndex);
        if (columnIndex == 0) {
            return curr.getCurrId();
        } else {
            return curr.getName();
        }
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        Currency curr = currencies.get(rowIndex);
        if (columnIndex == 0) {
            curr.setCurrId((String) aValue);
        } else {
            curr.setName((String) aValue);
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

    public void fireTableChanged(TableModelEvent e) {
        for (TableModelListener listener : listeners) {
            listener.tableChanged(e);
        }
    }

}
