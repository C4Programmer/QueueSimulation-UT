/**
 * Public void initialize() -> it starts the Controller thread and initialize all the variables that are part of this class and uses methods to initialize the Graphical User Interface, to generate random Clients, to sort Clients ArrayList to better introduce them in the Queue, methods that generates the queue threads.
 * Private void readInputsFromGUI() -> it initialize all the simulation bounds with the values that have been introduce with the help of the Graphical User Interface.
 * Private void generateQueueThreads() -> it generates the threads of queues.
 * Private int getMinimWaitQueue() -> it gets the index of the queue with the smallest waiting time.
 * Public void printToFile() -> it writes to file the simulation status.
 * Private void generateClients() -> it generates clients with random values and unique ID.
 * Private void initializeGUI() -> it starts the Graphical User Interface.
 * Private void run() -> it is the Override method of the thread that executes the application itself.
 * Private void writeAverage() -> writes the average service and wait time to the file.txt.
 */

package Control;

import Model.Client;
import Model.Queue;
import View.GraphicalUI;

import javax.swing.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class Controller implements Runnable{

    int realTime = 0;
    //private GlobalTime time;
    private List<Queue> queues;
    private List<Client> clients;
    //private List<Client> copyList;
    private List<Client> temporaryList;

    private int simulationDuration;
    private int numberOfClients;
    private int numberOfQueues;
    private int minArrivalTime;
    private int maxArrivalTime;
    private int minServiceTime;
    private int maxServiceTime;
    private int totalServiceTime = 0;
    private GraphicalUI viewDesign;

    File resultFile;
    BufferedWriter writerToFile;

    public void initialize() throws IOException {
        this.resultFile = new File("Simulation.txt");
        if(resultFile.createNewFile()){ System.out.println("File created"); }else{ System.out.println("File already created"); }
        this.writerToFile = new BufferedWriter(new FileWriter(this.resultFile));
        Thread controllerThread = new Thread(this);
        this.queues = new ArrayList<>();
        this.clients = new ArrayList<>();
        //this.time = new GlobalTime();
        this.viewDesign = new GraphicalUI();
        initializeGUI();
        while(!viewDesign.isSimulationRunning()){
            System.out.println("Simulation Not Running");
        }
        readInputsFromGUI();
        generateClients();
        for (var client:clients){
            totalServiceTime = totalServiceTime + client.getServiceTime();
        }
        temporaryList = new ArrayList<>();
        //copyList = new ArrayList<>(clients);
        Collections.sort(clients);
        printClients(clients);
        controllerThread.start();
    }

    @Override
    public void run() {
        generateQueueThreads();
        boolean lastClientEnteredQueue = false;
        boolean anyQueueFill = false;
        while((realTime <= simulationDuration) && (!lastClientEnteredQueue || anyQueueFill) ){
            anyQueueFill = false;
            if(clients.isEmpty()){ lastClientEnteredQueue = true; }
            for(var client:clients){
                if(realTime == client.getArrivalTime()){
                    queues.get(getMinimWaitQueue(queues)).addClient(client); }else{ break; } }
            for(var queue:queues){ if (!queue.getClients().isEmpty()) { anyQueueFill = true;break; } }
            for(var client:clients){ if(realTime == client.getArrivalTime()){ temporaryList.add(client); }else{ break;} }
            for (var client:temporaryList){ clients.remove(client); }
            temporaryList.clear();
            try { printToFile(realTime,queues,writerToFile);writerToFile.write("Waiting Clients: ");
                printClientsToFile(writerToFile,clients,0);} catch (IOException e) { e.printStackTrace(); }
            viewDesign.getSimulationGUI().writeToPane(realTime,queues);
            viewDesign.getSimulationGUI().writeWaitClients(clients);
            printingQueue(queues);
            try { Thread.sleep(1000);realTime++; } catch (InterruptedException e) { e.printStackTrace(); } }
        try { writeAverage(numberOfClients,writerToFile,clients,realTime-1);
            viewDesign.getSimulationGUI().writeAverage(numberOfClients,totalServiceTime,realTime-1);
            writerToFile.close(); } catch (IOException e) { e.printStackTrace(); }
        for (var queue: queues){ queue.setClosed(true); }
        /*
        generateQueueThreads();
        time.initialize();
        boolean lastClientEnteredQueue = false;
        boolean anyQueueFill = false;
        while((time.getClock() <= simulationDuration)  && (!lastClientEnteredQueue || anyQueueFill)){
            anyQueueFill = false;
            if(time.getClock() == clients.get(clients.size()-1).getArrivalTime()){ lastClientEnteredQueue = true; }
            for(var queue:queues){
                if (!queue.getClients().isEmpty()) {
                    anyQueueFill = true;break; } }
            for(var client:clients){
                if(time.getClock() == client.getArrivalTime()){
                    queues.get(getMinimWaitQueue(queues)).addClient(client); } }
            for(var client:copyList){
                if(time.getClock() == client.getArrivalTime()){
                    temporaryList.add(client); } }
            for (var client:temporaryList){
                copyList.remove(client); }
            temporaryList.clear();
            try { printToFile(time.getClock(),queues,writerToFile);writerToFile.write("Waiting Clients: ");
                printClientsToFile(writerToFile,copyList,0);} catch (IOException e) { e.printStackTrace(); }
            viewDesign.getSimulationGUI().writeToPane(time.getClock(),queues);
            viewDesign.getSimulationGUI().writeWaitClients(copyList);
            printingQueue(queues);
            try { Thread.sleep(1000); } catch (InterruptedException e) { e.printStackTrace(); } }
        try { writeAverage(numberOfClients,writerToFile,clients,time.getClock()-1);
            viewDesign.getSimulationGUI().writeAverage(numberOfClients,clients,time.getClock()-1);
            writerToFile.close(); } catch (IOException e) { e.printStackTrace(); }
        time.setStop(true);
        for (var queue: queues){ queue.setClosed(true); }
         */
    }

    private void readInputsFromGUI(){
        this.simulationDuration =  viewDesign.getTimeNeededForSimulation();
        this.numberOfClients = viewDesign.getNumberOfClients();
        this.numberOfQueues = viewDesign.getNumberOfQueues();
        this.minArrivalTime = viewDesign.getMinimArrival();
        this.maxArrivalTime = viewDesign.getMaximArrival();
        this.minServiceTime = viewDesign.getMinimService();
        this.maxServiceTime = viewDesign.getMaximService();
    }

    private void generateQueueThreads(){
        for(int i = 0;i<numberOfQueues;i++){
            Queue queue = new Queue(i);
            queue.initialize();
            queues.add(queue);
        }
    }

    private int getMinimWaitQueue(List<Queue> queues){
        int idMin = 1_000_000_000;
        int minWait = 1_000_000_000;
        for(int i=0;i<queues.size();i++){
            if(minWait > queues.get(i).getWaitTime()){
                minWait = queues.get(i).getWaitTime();
                idMin = i;
            }
        }
        return idMin;
    }

    private void writeAverage(int numberOfClients,BufferedWriter writer,List<Client> clients, int time) throws IOException {
        System.out.println("Time: "+time);
        System.out.println("NrClients: "+numberOfClients);
        System.out.println("TotalService: "+totalServiceTime);
        writer.write("Average waiting time: ");
        double averageWait = (double)time/(double)numberOfClients;
        writer.write(Double.toString(averageWait));
        writer.write("\n");
        writer.write("Average serving time: ");
        double averageService = (double)totalServiceTime/(double)numberOfClients;
        writer.write(Double.toString(averageService));
    }

    public void printToFile(int timeClock, List<Queue> queues,BufferedWriter writerToFile) throws IOException {
        writerToFile.write("Time: ");
        writerToFile.write(Integer.toString(timeClock));
        writerToFile.write("\n");
        for(var queue: queues){
            writerToFile.write("Queue ");
            writerToFile.write(Integer.toString(queue.getID()));
            writerToFile.write(" : ");
            printClientsToFile(writerToFile,queue.getClients(),1);
            writerToFile.write("\n");
        }
    }

    public void printClientsToFile(BufferedWriter writerToFile,List<Client> clients, int aux) throws IOException {
        for(var client:clients){
            writerToFile.write("Client ( ");
            writerToFile.write(Integer.toString(client.getID()));
            writerToFile.write(" , ");
            writerToFile.write(Integer.toString(client.getArrivalTime()));
            writerToFile.write(" , ");
            writerToFile.write(Integer.toString(client.getServiceTime()));
            writerToFile.write(" )");
            writerToFile.write(" , ");
        }
        if(aux == 0){
            writerToFile.write("\n");
        }
    }

    private void generateClients(){
        for(int i = 0;i < numberOfClients; i++){
            Client client = new Client(i);
            client.generateRandomValues(minArrivalTime,maxArrivalTime,minServiceTime,maxServiceTime);
            clients.add(client);
        }
    }
    private void printClients(List<Client> clients){
        for(var client:clients){
            client.printClient();
        }
    }

    private void printingQueue(List<Queue> queues){
        for(var queue: queues){
            System.out.println("Queue: "+queue.getID());
            printClients(queue.getClients());
        }
    }

    private void initializeGUI(){
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
            JFrame jFrame = new JFrame("Queues Simulation");
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setContentPane(viewDesign.SimulationGUIPanel);
        jFrame.pack();
        jFrame.setLocationRelativeTo(null);
        jFrame.setVisible(true);
        } catch (Exception ignored) {
        }
    }
}
