/**
 * Public void initialize() -> it initialize the queue Thread and the variables.
 * Public int getWaitTime() -> it returns the total wait time of the queue.
 * Public void run() -> it is the Override method of the Thread and it process the Clients that have to be serve, by waiting in seconds for each service time of each client.
 */

package Model;

import java.util.ArrayList;
import java.util.List;

public class Queue implements Runnable{

    private final int ID;
    private List<Client> clients;
    private boolean closed;

    public Queue(int ID){
        this.ID = ID;
    }

    public void initialize(){
        this.clients = new ArrayList<>();
        this.closed = false;
        Thread queueThread = new Thread(this);
        queueThread.start();
    }

    public int getWaitTime(){
        if(clients.isEmpty()){
            return 0;
        }else{
            int waitTime = 0;
            for(var client:clients){
                waitTime += client.getServiceTime();
            }
            return waitTime;
        }
    }

    public List<Client> getClients() {
        return clients;
    }

    public void addClient(Client client){
        clients.add(client);
    }

    public void setClosed(boolean closed){
        this.closed = closed;
    }

    @Override
    public void run() {
        while (!closed) {
            if(!clients.isEmpty()) {
                Client clientToServe = clients.get(0);
                try {
                    Thread.sleep((clientToServe.getServiceTime()-1)*1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                clients.remove(clientToServe);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }else{
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public int getID() {
        return ID;
    }
}
