package model;

import java.util.LinkedList;
import java.util.Random;

public class Cache {

    private LinkedList<ServiceOrder>[] table;
    private int size;
    private int quantity;
    private final int CACHE_SIZE = 20;

    public Cache() {
        this.size = CACHE_SIZE;
        this.quantity = 0;
        this.table = new LinkedList[CACHE_SIZE];

        for (int i = 0; i < CACHE_SIZE; i++) {
            table[i] = new LinkedList<>();
        }
    }

    public String runCache(){
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

    private int getIndex(int hashCode) {
        return Math.abs(hashCode) % (size); // Makes sure that the index is positive and in range
    }

    public void insert(ServiceOrder order) {
        if(!isFull()){
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
        } else {
            Random random = new Random();
            int randomIndex = random.nextInt(table.length);

            remove(randomIndex);
            insert(order);
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

    public LinkedList<ServiceOrder>[] getTable() {
        return table;
    }

    public boolean isFull(){
        return quantity > 19;
    }

    public boolean isEmpty(){
        return quantity == 0;
    }

    public void printCache(){
        System.out.println("\n------- View Cache --------\n");
        String listCacheReturn = runCache();
        System.out.println(listCacheReturn);
    }
}
