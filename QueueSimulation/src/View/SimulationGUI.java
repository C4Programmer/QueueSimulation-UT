/**
 * Public void writeToPane() -> it updates the simulation status each second.
 * Public void writeWaitClients() -> it prints to the simulation status the remaining clients that have not entered a queue.
 * Public void writeAverage() -> it write the average service and wait time to the status pane.
 */

package View;

import Model.Client;
import Model.Queue;

import javax.swing.*;
import java.io.BufferedWriter;
import java.util.List;

public class SimulationGUI {
    public JPanel statusPanel;
    private JScrollPane statusPane;

    JTextArea textField = new JTextArea();

    public void writeToPane(int time,List<Queue> queues){
        textField.setText(textField.getText()+"Time: "+ time+"\n");
        for (var queue:queues){
            textField.setText(textField.getText()+"Queue "+queue.getID()+" : ");
            for(var client:queue.getClients()){
                textField.setText(textField.getText()+"Client ( "+client.getID()+" , "+client.getArrivalTime()+" , "+client.getServiceTime()+" )");
                textField.setText(textField.getText()+" , ");
            }
            textField.setText(textField.getText()+"\n");
        }
        statusPane.getViewport().add(textField);
    }
    public void writeAverage(int numberOfClients,int totalServiceTime, int time){
        textField.setText(textField.getText()+"Average Waiting time: "+ (double)time/(double)numberOfClients+"\n");
        textField.setText(textField.getText()+"Average Service time: "+ (double)totalServiceTime/(double)numberOfClients+"\n");
    }
    public void writeWaitClients(List<Client> clients){
        textField.setText(textField.getText()+"Waiting clients: ");
        for(var client:clients){
            textField.setText(textField.getText()+"Client ( "+client.getID()+" , "+client.getArrivalTime()+" , "+client.getServiceTime()+" )");
            textField.setText(textField.getText()+" , ");
        }
        textField.setText(textField.getText()+"\n");
    }
}
