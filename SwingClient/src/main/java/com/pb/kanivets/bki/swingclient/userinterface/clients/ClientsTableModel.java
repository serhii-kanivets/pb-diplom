package com.pb.kanivets.bki.swingclient.userinterface.clients;

import com.pb.kanivets.bki.core.entities.Client;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

class ClientsTableModel implements TableModel {

    private List<Client> clientsList;
    private final String[] columnHeaders;
    private final Class[] columnClasses;
    private final Set<TableModelListener> listeners;

    public ClientsTableModel() {
        clientsList = new ArrayList<>();
        columnHeaders = new String[]{"ИД", "ИНН", "Фамилия", "Имя", "Отчество", "Дата рождения", "Пасспорт"};
        columnClasses = new Class[]{Integer.class, String.class, String.class, String.class, String.class, Date.class, String.class};
        listeners = new HashSet<>();
    }

    public void setData(List<Client> list) {
        clientsList = list;
        fireTableChanged(new TableModelEvent(this));
    }

    public void addClient(Client c) {
        clientsList.add(c);
        fireTableChanged(new TableModelEvent(this));

    }

    public void setClient(int rowIndex, Client c) {
        clientsList.set(rowIndex, c);
        fireTableChanged(new TableModelEvent(this));

    }

    public void removeClient(int rowIndex) {
        clientsList.remove(rowIndex);
        fireTableChanged(new TableModelEvent(this));

    }

    public Client getClient(int rowIndex) {
        return clientsList.get(rowIndex);
    }

    @Override
    public int getRowCount() {
        return clientsList.size();
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
        Client currClient = clientsList.get(rowIndex);
        switch (columnIndex) {
            case 0: {
                return currClient.getClientId();
            }
            case 1: {
                return currClient.getInn();
            }
            case 2: {
                return currClient.getsName();
            }
            case 3: {
                return currClient.getfName();
            }
            case 4: {
                return currClient.getmName();
            }
            case 5: {
                return currClient.getBirthDate();
            }
            case 6: {
                return currClient.getPassp();
            }
        }
        return null;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        Client currClient = clientsList.get(rowIndex);
        switch (columnIndex) {
            case 0: {
                currClient.setClientId((Integer) aValue);
                break;
            }
            case 1: {
                currClient.setInn((String) aValue);
                break;
            }
            case 2: {
                currClient.setsName((String) aValue);
                break;
            }
            case 3: {
                currClient.setfName((String) aValue);
                break;
            }
            case 4: {
                currClient.setmName((String) aValue);
                break;
            }
            case 5: {
                currClient.setBirthDate((Date) aValue);
                break;
            }
            case 6: {
                currClient.setPassp((String) aValue);
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

    public void fireTableChanged(TableModelEvent e) {
        for (TableModelListener tableModelListener : listeners) {
            tableModelListener.tableChanged(e);
        }
    }  

}
