package model;

public class ServiceOrder {
    int nodeHeight;
    ServiceOrder left, right;

    private int code;
    private String name;
    private String client;
    private String description;
    private final long requestTime;

    public ServiceOrder(int code, String name, String client, String description, long requestTime) {
        this.code = code;
        this.name = name;
        this.client = client;
        this.description = description;
        this.requestTime = requestTime;
    }

    public ServiceOrder(String name, String client, String description) {
        this.name = name;
        this.client = client;
        this.description = description;
        this.requestTime = System.currentTimeMillis();
    }

    public void setHashCode(){
        this.code = serviceOrderHash();
    }

    public void listShow() {

        String requestMin = String.valueOf((requestTime / (1000 * 60)) % 60); // Get minutes
        String requestHour = String.valueOf((requestTime / (1000 * 60 * 60)) % 24); // Get minutes

        System.out.print("  Code: " + getCode() + "  |  ");
        System.out.print("Name: " + getName() + "  |  ");
        System.out.print("Client: " + getClient() + "  |  ");
        System.out.print("Description: " + getDescription() + "  |  ");
        System.out.println("Request Time: " + requestHour + ":" + requestMin);
    }

    @Override
    public String toString() {
        String toString;
        toString = getCode() + ";" + getName() + ";" + getClient() + ";" + getDescription() + ";" + getRequestTime() + "\n";
        return toString;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getRequestTime() {
        return requestTime;
    }

    @Override
    public int hashCode(){
        return serviceOrderHash();
    }

    // Hash function using requestTime and name of the serviceOrder
    private int serviceOrderHash() {

        long requestSec = (requestTime / 1000) % 60; // Get seconds
        long requestMin = (requestTime / (1000 * 60)) % 60; // Get minutes

        // Transforms the name to a numeric value based on a sum of ASCII values of the characters
        int nameValue = 0;
        for (char c : name.toCharArray()) {
            nameValue += c;
        }

        int hash = (int) (requestSec * 31 + requestMin * 17 + nameValue);
        return hash;
    }
}
