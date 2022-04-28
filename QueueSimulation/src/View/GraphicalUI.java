/**
 * It contains the constructor with the necessary buttons that introduce the simulation bounds, and getters and setters
 */

package View;

import javax.swing.*;
import java.awt.*;


public class GraphicalUI {

    public JPanel SimulationGUIPanel;
    private JTextField timeOfSimulationText;
    private JTextField numberOfQueuesText;
    private JTextField numberOfClientsText;
    private JTextField minArrivalText;
    private JTextField maxArrivalText;
    private JTextField minServiceText;
    private JTextField maxServiceText;
    private JButton introduceTimeOfSimulationButton;
    private JButton introduceNumberOfQueuesButton;
    private JButton introduceNumberOfClientsButton;
    private JButton introduceMinimumArrivalTimeButton;
    private JButton introduceMaximumArrivalTimeButton;
    private JButton introduceMinimumServiceTimeButton;
    private JButton introduceMaximumServiceTimeButton;
    private JButton startSimulationButton;
    private JTextArea seeTime;
    private JTextArea seeNumberQueues;
    private JTextArea seeNumberClients;
    private JTextArea seeMinA;
    private JTextArea seeMaxA;
    private JTextArea seeMinS;
    private JTextArea seeMaxS;

    private boolean simulationRunning = false;

    private int timeNeededForSimulation;

    private int numberOfQueues;

    private int numberOfClients;

    private int minimArrival = 0;
    private int maximArrival = 0;
    private int minimService = 0;
    private int maximService = 0;

    private boolean checkTime = false;
    private boolean checkQueue = false;
    private boolean checkClients = false;
    private boolean checkMinA = false;
    private boolean checkMaxA = false;
    private boolean checkMinS = false;
    private boolean checkMaxS = false;

    private final SimulationGUI simulationGUI = new SimulationGUI();
    JFrame jFrame;

    public GraphicalUI() {

        startSimulationButton.addActionListener(e -> {
            if(checkMaxS && checkMinS && checkMaxA && checkMinA && checkClients && checkQueue && checkTime){
                if(minimService <= maximService && minimArrival <= maximArrival ){
                    simulationRunning = true;
                    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
                    int height = screenSize.height * 2 / 3;
                    int width = screenSize.width * 2 / 3;
                    jFrame = new JFrame("Queues Simulation");
                    jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    jFrame.setContentPane(simulationGUI.statusPanel);
                    jFrame.setPreferredSize(new Dimension(width, height));
                    jFrame.pack();
                    jFrame.setLocationRelativeTo(null);
                    jFrame.setVisible(true);
                }else{
                    JOptionPane.showMessageDialog(null,"The minimum must be smaller or equal than maximum");
                }
            }else{
                JOptionPane.showMessageDialog(null,"The Simulation cannot start due to some errors regarding the inputs");
            }
        });

        introduceTimeOfSimulationButton.addActionListener(e -> {
            try {
                timeNeededForSimulation = Integer.parseInt(timeOfSimulationText.getText());
                if (timeNeededForSimulation<0){
                    JOptionPane.showMessageDialog(null,"Negative Value introduced");
                    checkTime = false;
                }else{
                    checkTime = true;
                }
            }catch (NumberFormatException nr){
                JOptionPane.showMessageDialog(null,"The entered value is not an integer");
                checkTime = false;
            }
            seeTime.setText(timeOfSimulationText.getText());
        });

        introduceNumberOfQueuesButton.addActionListener(e -> {
            try {
                numberOfQueues = Integer.parseInt(numberOfQueuesText.getText());
                if (numberOfQueues < 0){
                    JOptionPane.showMessageDialog(null,"Negative Value introduced");
                    checkQueue = false;
                }else{
                    checkQueue = true;
                }
            }catch (NumberFormatException nr){
                JOptionPane.showMessageDialog(null,"The entered value is not an integer");
                checkQueue = false;
            }
            seeNumberQueues.setText(numberOfQueuesText.getText());
        });

        introduceNumberOfClientsButton.addActionListener(e -> {

            try {
                numberOfClients = Integer.parseInt(numberOfClientsText.getText());
                if (numberOfClients < 0){
                    JOptionPane.showMessageDialog(null,"Negative Value introduced");
                    checkClients = false;
                }else{
                    checkClients = true;
                }
            }catch (NumberFormatException nr){
                JOptionPane.showMessageDialog(null,"The entered value is not an integer");
                checkClients = false;
            }
            seeNumberClients.setText(numberOfClientsText.getText());
        });

        introduceMinimumArrivalTimeButton.addActionListener(e -> {
            try {
                minimArrival = Integer.parseInt(minArrivalText.getText());
                if (minimArrival < 0){
                    JOptionPane.showMessageDialog(null,"Negative Value introduced");
                    checkMinA = false;
                }else{
                    checkMinA = true;
                }
            }catch (NumberFormatException nr){
                checkMinA = false;
                JOptionPane.showMessageDialog(null,"The entered value is not an integer");
            }
            seeMinA.setText(minArrivalText.getText());
        });

        introduceMaximumArrivalTimeButton.addActionListener(e -> {
            try {
                maximArrival = Integer.parseInt(maxArrivalText.getText());
                if (maximArrival < 0){
                    JOptionPane.showMessageDialog(null,"Negative Value introduced");
                    checkMaxA = false;
                }else{
                    checkMaxA = true;
                }
            }catch (NumberFormatException nr){
                checkMaxA = false;
                JOptionPane.showMessageDialog(null,"The entered value is not an integer");
            }
            seeMaxA.setText(maxArrivalText.getText());
        });

        introduceMinimumServiceTimeButton.addActionListener(e -> {
            try {
                minimService = Integer.parseInt(minServiceText.getText());
                if (minimService < 0){
                    JOptionPane.showMessageDialog(null,"Negative Value introduced");
                    checkMinS = false;
                }else{
                    checkMinS = true;
                }
            }catch (NumberFormatException nr){
                checkMinS = false;
                JOptionPane.showMessageDialog(null,"The entered value is not an integer");
            }
            seeMinS.setText(minServiceText.getText());
        });

        introduceMaximumServiceTimeButton.addActionListener(e -> {
            try {
                maximService = Integer.parseInt(maxServiceText.getText());
                if (maximService < 0){
                    JOptionPane.showMessageDialog(null,"Negative Value introduced");
                    checkMaxS = false;
                }else{
                    checkMaxS = true;
                }
            }catch (NumberFormatException nr){
                checkMaxS = false;
                JOptionPane.showMessageDialog(null,"The entered value is not an integer");
            }
            seeMaxS.setText(maxServiceText.getText());
        });
    }

    public SimulationGUI getSimulationGUI() {
        return simulationGUI;
    }

    public int getTimeNeededForSimulation() {
        return timeNeededForSimulation;
    }

    public int getNumberOfQueues() {
        return numberOfQueues;
    }

    public int getNumberOfClients() {
        return numberOfClients;
    }

    public int getMinimArrival() {
        return minimArrival;
    }

    public int getMaximArrival() {
        return maximArrival;
    }

    public int getMinimService() {
        return minimService;
    }

    public int getMaximService() {
        return maximService;
    }

    public boolean isSimulationRunning() {
        return simulationRunning;
    }
}
