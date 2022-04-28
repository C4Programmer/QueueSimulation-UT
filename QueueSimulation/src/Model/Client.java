/**
 * Public void generateRandomValues() -> it generates random values for each Client Variable.
 * Public int compareTo() -> it is an Override method of sorting a Client List
 */

package Model;

import java.util.Random;

public class Client implements Comparable<Client>{

    private final int ID;
    private int serviceTime;
    private int arrivalTime;

    public Client (int ID){
        this.ID = ID;
    }

    public void generateRandomValues(int minArrival, int maxArrival, int minService, int maxService){
        Random random = new Random();
        this.arrivalTime = random.nextInt((maxArrival-minArrival)+1)+minArrival;
        this.serviceTime = random.nextInt((maxService-minService)+1)+minService;
    }

    public void printClient(){
        System.out.println("Client ( "+this.ID+" , "+this.arrivalTime+" , "+this.serviceTime+" )");
    }

    public int getID() {
        return ID;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public int getServiceTime() {
        return serviceTime;
    }

    @Override
    public int compareTo(Client o) {
        return this.arrivalTime - o.arrivalTime;
    }
}
