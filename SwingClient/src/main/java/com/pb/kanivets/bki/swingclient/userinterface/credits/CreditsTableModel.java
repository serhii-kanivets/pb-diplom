package com.pb.kanivets.bki.swingclient.userinterface.credits;

import com.pb.kanivets.bki.core.entities.Credit;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

public class CreditsTableModel implements TableModel {

    private List<Credit> credits = new ArrayList<>();
    private final Class[] columnClasses = {Integer.class, Integer.class, Integer.class,
        String.class, BigDecimal.class, Date.class,
        BigDecimal.class, Date.class, String.class};
    private final String[] columnHeaders = {"ИД", "ИД Клиента", "Код Банка",
        "Код валюты", "Начальная сумма", "Дата выдачи",
        "Текущее тело кредита", "Дата закрытия", "Просрочка"};

    private final Set<TableModelListener> listeners = new HashSet<>();

    public void setList(List<Credit> list) {
        this.credits = list;
        fireTableModelChanged(new TableModelEvent(this));

    }

    public Credit get(int rowIndex) {
        return credits.get(rowIndex);
    }

    public void add(Credit c) {
        credits.add(c);
        fireTableModelChanged(new TableModelEvent(this));
    }

    public void set(int rowIndex, Credit c) {
        credits.set(rowIndex, c);
        fireTableModelChanged(new TableModelEvent(this));

    }

    public void remove(int rowIndex) {
        credits.remove(rowIndex);
        fireTableModelChanged(new TableModelEvent(this));

    }

    public void fireTableModelChanged(TableModelEvent e) {
        for (TableModelListener tml : listeners) {
            tml.tableChanged(e);
        }
    }

    @Override
    public int getRowCount() {
        return credits.size();
    }

    @Override
    public int getColumnCount() {
        return columnHeaders.length;
    }

    @Override
    public String getColumnName(int columnIndex) {
        return columnHeaders[columnIndex];
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
        Credit credit = credits.get(rowIndex);
        switch (columnIndex) {
            case 0: {
                return credit.getId();
            }
            case 1: {
                return credit.getClientId();
            }
            case 2: {
                return credit.getMfo();
            }
            case 3: {
                return credit.getCurrId();
            }
            case 4: {
                return credit.getInitSum();
            }
            case 5: {
                return credit.getIssueDate();
            }
            case 6: {
                return credit.getBody();
            }
            case 7: {
                return credit.getCloseDate();
            }
            case 8: {
                return credit.getDelay();
            }
        }
        return null;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        Credit credit = credits.get(rowIndex);
        switch (columnIndex) {
            case 0: {
                credit.setId((Integer) aValue);
                break;
            }
            case 1: {
                credit.setClientId((Integer) aValue);
                break;
            }
            case 2: {
                credit.setMfo((Integer) aValue);
                break;
            }
            case 3: {
                credit.setCurrId((String) aValue);
                break;
            }
            case 4: {
                credit.setInitSum((BigDecimal) aValue);
                break;
            }
            case 5: {
                credit.setIssueDate((Date) aValue);
                break;
            }
            case 6: {
                credit.setBody((BigDecimal) aValue);
                break;
            }
            case 7: {
                credit.setCloseDate((Date) aValue);
                break;
            }
            case 8: {
                credit.setDelay((String) aValue);
                break;
            }
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

}
