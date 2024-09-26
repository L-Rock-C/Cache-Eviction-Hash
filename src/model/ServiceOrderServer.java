package model;

import java.util.LinkedList;

public class ServiceOrderServer {

    private LinkedList<ServiceOrder>[] table;
    private int size;
    private int quantity;
    private static final float CHARGE_FACTOR = 0.75f;

    public ServiceOrderServer(int initialSize) {
        this.size = initialSize;
        this.quantity = 0;
        this.table = new LinkedList[initialSize];

        for (int i = 0; i < initialSize; i++) {
            table[i] = new LinkedList<>();
        }
    }

    public String runServiceOrders(){
        String returnString = "";
        for (int i = 0; i < size; i++) {
            if (!table[i].isEmpty()) {
                for (ServiceOrder order : table[i]) {
                    returnString += order.toString();
                }
            }
        }

        return returnString;
    }

    public String listServiceOrders(){
        String returnString = "";
        for (int i = 0; i < size; i++) {
            if (!table[i].isEmpty()) {
                for (ServiceOrder order : table[i]) {
                    returnString += i + " " + order.toString();
                }
            }
        }

        return returnString;
    }

    private int getIndex(int hashCode) {
        return Math.abs(hashCode) % (size); // Makes sure that the index is positive and in range
    }

    public void insert(ServiceOrder order) {
        int index = getIndex(order.hashCode());
        LinkedList<ServiceOrder> list = table[index];

        for (ServiceOrder SO : list) {
            if (SO.equals(order)) {
                System.out.println("Service order already exists: " + order.getCode());
                return;
            }
        }

        list.add(order);
        quantity++;

        if (needsResize()) {
            resize();
        }
    }

    public ServiceOrder search(int code) {
        for (LinkedList<ServiceOrder> list : table) {
            for (ServiceOrder order : list) {
                if (order.getCode() == code) {
                    return order;
                }
            }
        }
        return null;
    }


    public void remove(int code) {
        for (LinkedList<ServiceOrder> list : table) {
            ServiceOrder removeOrder = null;
            for (ServiceOrder order : list) {
                if (order.getCode() == code) {
                    removeOrder = order;
                    break;
                }
            }

            if (removeOrder != null) {
                list.remove(removeOrder);
                quantity--;
                break;
            }
        }
    }

    private boolean needsResize() {
        return quantity > CHARGE_FACTOR * size;
    }

    private void resize() {
        int newSize = size * 2;
        LinkedList<ServiceOrder>[] newTable = new LinkedList[newSize];

        for (int i = 0; i < newSize; i++) {
            newTable[i] = new LinkedList<>();
        }

        for (int i = 0; i < size; i++) {
            for (ServiceOrder order : table[i]) {
                int newIndex = Math.abs(order.hashCode()) % newSize;
                newTable[newIndex].add(order);
            }
        }

        this.table = newTable;
        this.size = newSize;
    }

    public LinkedList<ServiceOrder>[] getTable() {
        return table;
    }

    public void setTable(LinkedList<ServiceOrder>[] table) {
        this.table = table;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }


}
