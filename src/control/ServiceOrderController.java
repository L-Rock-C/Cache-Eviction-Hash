package control;

import database.Files;
import model.Cache;
import model.ServiceOrder;
import model.ServiceOrderServer;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class ServiceOrderController {

    Files files = new Files();

    FileController fileAccess = new FileController();
    ServiceOrderServer serviceOrders = fileAccess.readSOFile(files.getServiceOrderFile());
    Cache cache = fileAccess.readCacheFile(files.getCacheFile());
    Scanner input = new Scanner(System.in);

    public ServiceOrderController() throws IOException { }

    public void serviceOrdersList(){
        System.out.println("\n------- Service Orders List --------\n");

        System.out.println(serviceOrders.runServiceOrders());

        System.out.println();
    }

    public void serviceOrderView() throws IOException {
        System.out.println("\n------- Service Order Visualization --------\n");

        System.out.print("Select a service order by its code to visualize: ");
        int code = input.nextInt();

        ServiceOrder SOFromCache = cache.search(code);

        if(SOFromCache != null){
            System.out.println();

            SOFromCache.listShow();

            System.out.print("\n(Data from cache) \nPress Enter to continue.");
            input.nextLine();
            input.nextLine();
        } else {

            ServiceOrder chosen = serviceOrders.search(code);

            if (chosen != null) {
                System.out.println();
                chosen.listShow();

                cache.insert(chosen);
                fileAccess.WriteFile(files.getCacheFile(), cache.runCache());

                System.out.print("\nPress Enter to continue.");
                input.nextLine();
                input.nextLine();
            } else {
                System.out.println("\nService Order with id " + code + " doesn't exist.\n");
            }
        }
    }

    public void serviceOrderEdit() throws IOException {
        System.out.println("------- Service Order Edit --------\n");

        serviceOrdersList();

        System.out.print("\nSelect a service order by its id to edit: ");
        int code = input.nextInt();

        ServiceOrder chosen = serviceOrders.search(code);
        if(chosen != null){
            boolean done = false;
            while(!done){
                System.out.println("\n------- Editing Service Order --------\n");

                System.out.println("Code:" + chosen.getCode());
                System.out.println("[1] Name: " + chosen.getName());
                System.out.println("[2] Client: " + chosen.getClient());
                System.out.println("[3] Description: " + chosen.getDescription());
                System.out.println("\n[4] Finish edit.\n");

                System.out.print("Select a field to edit: ");
                code = input.nextInt();
                input.nextLine();

                switch(code){
                    case 1:
                        System.out.print("Insert new Name value: ");
                        String name = input.nextLine();
                        chosen.setName(name);
                        break;
                    case 2:
                        System.out.print("Insert new Client value: ");
                        String client = input.nextLine();
                        chosen.setClient(client);
                        break;
                    case 3:
                        System.out.print("Insert new Description value: ");
                        String description = input.nextLine();
                        chosen.setDescription(description);
                        break;
                    case 4:
                        fileAccess.WriteFile(files.getServiceOrderFile(), serviceOrders.runServiceOrders());
                        System.out.println();
                        done = true;
                        break;
                    default:
                        System.out.println("Invalid number.");
                        break;
                }
            }
        } else {
            System.out.println("\nService Order with id " + code + " doesn't exist.\n");
        }
    }

    public void serviceOrderForm() throws IOException {

        System.out.println("------- Service Order Form --------\n");

        System.out.print("Name: ");
        String name = input.nextLine();

        System.out.print("Client: ");
        String client = input.nextLine();

        System.out.print("Description: ");
        String description = input.nextLine();

        ServiceOrder serviceOrder = new ServiceOrder(name, client, description);
        serviceOrder.setHashCode();
        serviceOrders.insert(serviceOrder);

        fileAccess.WriteFile(files.getServiceOrderFile(), serviceOrders.runServiceOrders());
    }

    public void serviceOrderDelete() throws IOException {
        System.out.println("\n------- Service Order Delete --------\n");

        serviceOrdersList();

        System.out.print("\nSelect a service order by its id to delete: ");
        int code = input.nextInt();

        ServiceOrder chosen = serviceOrders.search(code);
        if(chosen != null){
            serviceOrders.remove(chosen.getCode());

            fileAccess.WriteFile(files.getServiceOrderFile(), serviceOrders.runServiceOrders());
            System.out.println("\nService order deleted.");
        }

        System.out.println();
    }

    public Cache getCache(){
        return cache;
    }
}
