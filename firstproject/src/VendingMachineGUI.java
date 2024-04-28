import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import javax.swing.*;

public class VendingMachineGUI extends JFrame {

    private double points; // Changed to double for more precise calculations
    private int plasticBottleCounter;
    private JLabel messageLabel;
    private JLabel pointsLabel;
    private JButton depositButton;
    private JButton purchaseRewardButton;
    private JButton doneButton;
    private JButton checkButton;

    private JPanel startPanel; // Panel to hold the start button

    public VendingMachineGUI(HashMap<String, HashMap<String, Object>> items) {
        this.points = 0;
        this.plasticBottleCounter = 0;

        setTitle("BERTO-Bin Vending Machine");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 200);
        setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        // Load logo
        ImageIcon logoIcon = new ImageIcon("bertologo1.png");
        JLabel logoLabel = new JLabel(logoIcon);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(0, 1)); // Changed to single column layout

        startPanel = new JPanel(); // Initialize the start panel
        startPanel.setLayout(new FlowLayout()); // Set layout for the start panel

        JButton startButton = new JButton("Start");
        startButton.setPreferredSize(new Dimension(370, 100));
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startVendingMachine();
            }
        });
        startPanel.add(startButton); //

        depositButton = new JButton("Deposit Bottle");
        depositButton.setPreferredSize(new Dimension(150, 50));
        depositButton.setEnabled(false); // Disable until start is clicked
        depositButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                depositBottle();
            }
        });
        buttonPanel.add(depositButton);

        purchaseRewardButton = new JButton("Purchase Reward");
        purchaseRewardButton.setPreferredSize(new Dimension(150, 50));
        purchaseRewardButton.setEnabled(false); // Disable until start is clicked
        purchaseRewardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                purchaseReward();
            }
        });
        buttonPanel.add(purchaseRewardButton);

        // Initialize the check balance button
        checkButton = new JButton("Check Balance");
        checkButton.setPreferredSize(new Dimension(150, 50));
        checkButton.setEnabled(false); // Disable until start is clicked
        checkButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkBalance();
            }
        });
        buttonPanel.add(checkButton);


        doneButton = new JButton("Done"); // Initialize the done button
        doneButton.setPreferredSize(new Dimension(400, 50));
        doneButton.setEnabled(false); // Disable until start is clicked
        doneButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                returnToStart();
                checkButton.setEnabled(false);
                JOptionPane.showMessageDialog(VendingMachineGUI.this, "Thank you for using BERTO-Bin!");
            }
        });
        buttonPanel.add(doneButton); // Add the done button to the button panel

        mainPanel.add(logoLabel, BorderLayout.WEST); // Add logo to the left
        mainPanel.add(startPanel, BorderLayout.WEST); // Add the start panel to the main panel
        mainPanel.add(buttonPanel, BorderLayout.EAST);
        mainPanel.setBackground(Color.LIGHT_GRAY);

        messageLabel = new JLabel("Welcome to the BERTO-Bin!");
        mainPanel.add(messageLabel, BorderLayout.NORTH);

        JPanel labelPanel = new JPanel();
        labelPanel.setLayout(new GridLayout(1, 1));
        pointsLabel = new JLabel("Points: " + points);
        labelPanel.add(pointsLabel);

        mainPanel.add(labelPanel, BorderLayout.SOUTH);

        add(mainPanel, BorderLayout.CENTER);

        setVisible(true); // Set visible after all components are added
    }

    private void startVendingMachine() {
        // Enable buttons after starting the vending machine
        messageLabel.setText("Vending Machine Started");
        pointsLabel.setText("Points: " + points);
        depositButton.setEnabled(true);
        purchaseRewardButton.setEnabled(true);
        doneButton.setEnabled(true); // Enable the done button
        checkButton.setEnabled(true); // Enable the check button
        startPanel.setVisible(false); // Hide the start panel
    }

    private void depositBottle() {
        while (true) {
            String[] options = {"Small", "Large"};
            int choice = JOptionPane.showOptionDialog(this,
                    "Please select the size of the plastic bottle:",
                    "Bottle Size",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options,
                    options[0]);

            if (choice == JOptionPane.YES_OPTION) {
                // Small bottle selected
                plasticBottleCounter++;
                double bottlePoints = 0.65; // Small bottle is 0.65 points
                points += bottlePoints;
                messageLabel.setText("Small bottle deposited. Points earned: " + bottlePoints + ". Total bottles: " + plasticBottleCounter);
            } else if (choice == JOptionPane.NO_OPTION) {
                // Large bottle selected
                plasticBottleCounter++;
                double bottlePoints = 1.0; // Large bottle is 1 point
                points += bottlePoints;
                messageLabel.setText("Large bottle deposited. Points earned: " + bottlePoints + ". Total bottles: " + plasticBottleCounter);
            }

            // Update points label
            updateLabels();

            int option = JOptionPane.showConfirmDialog(this, "Do you want to insert another bottle?", "Confirmation", JOptionPane.YES_NO_OPTION);
            if (option != JOptionPane.YES_OPTION) {
                int purchaseOption = JOptionPane.showOptionDialog(this,
                        "Do you want to purchase a reward or check your balance?",
                        "Purchase or Check Balance",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        new String[]{"Purchase Reward", "Check Balance"},
                        "Purchase Reward");

                if (purchaseOption == JOptionPane.YES_OPTION) {
                    purchaseReward(); // Purchase reward after successful scanning
                    break; // Exit the loop
                } else {
                    // Check balance
                    checkBalance();
                }
                break; // Exit the loop
            }
        }
    }

    /**
     * 
     */
    private void purchaseReward() {
        // Prompt the user to scan a QR code
        int qrConfirmation = JOptionPane.showConfirmDialog(this, "Please scan the QR code to proceed with the purchase.", "Scan QR Code", JOptionPane.OK_CANCEL_OPTION);
        
        // Check if the user confirmed (OK) to scan the QR code
        if (qrConfirmation == JOptionPane.OK_OPTION) {
            // Display current points
            JOptionPane.showMessageDialog(this, "Current Points: " + points);
            
            String[] rewardOptions = {"Ruler kit 20 points", "Note kit 25 points", "Correction Tape 15 points"};
            String selectedReward = (String) JOptionPane.showInputDialog(this, "Select a reward to purchase:", "Purchase Reward", JOptionPane.QUESTION_MESSAGE, null, rewardOptions, rewardOptions[0]);
            if (selectedReward != null) {
                int selectedRewardPoints = 0;
                switch (selectedReward) {
                    case "Ruler kit 20 points":
                        selectedRewardPoints = 20;
                        break;
                    case "Note kit 25 points":
                        selectedRewardPoints = 25;
                        break;
                    case "Correction Tape 15 points":
                        selectedRewardPoints = 15;
                        break;
                }
                if (points >= selectedRewardPoints) {
                    // Deduct points and update inventory
                    points -= selectedRewardPoints;
                    HashMap<String, Object> selectedRewardItem = new HashMap<>();
                    selectedRewardItem.put("name", selectedReward);
                    selectedRewardItem.put("points", selectedRewardPoints);
                    selectedRewardItem.put("quantity", 1); // Assuming 1 quantity per reward
                    // Update inventory or perform other necessary actions
                    messageLabel.setText("Reward purchased: " + selectedReward);
                    // Display updated points
                    JOptionPane.showMessageDialog(this, "Points after purchase: " + points);
                    JOptionPane.showMessageDialog(this, "Congratulations! You have successfully redeemed the reward: " + selectedReward);
         
                } else {
                    // Insufficient points
                    handleInsufficientPoints(selectedReward, selectedRewardPoints, 1); // Assuming 1 quantity per reward
                }
            }
        } else {
            // User canceled scanning QR code
            return;
        }
    }
    
    private void handleInsufficientPoints(String selectedReward, int selectedRewardPoints, int numItems) {
        JOptionPane.showMessageDialog(this, "Insufficient points to purchase this reward.");
        
        boolean continuePurchase = true;
        while (continuePurchase) {
            int option = JOptionPane.showOptionDialog(this,
                    "Do you want to deposit a bottle or choose another reward?",
                    "Insufficient Points",
                    JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    new String[]{"Deposit Bottle", "Choose Other Reward", "Back to Menu"},
                    "Deposit Bottle");
            
            if (option == JOptionPane.YES_OPTION) {
                // Deposit bottle
                depositBottle();
                continuePurchase = false;
            } else if (option == JOptionPane.NO_OPTION) {
                // Choose another reward
                purchaseReward();
                continuePurchase = false;
            } else {
                // Go back to the main menu
                startVendingMachine();
                continuePurchase = false;
            }
        }
    }
    

    private void checkBalance() {
        JOptionPane.showMessageDialog(this, "Current Points: " + points);

        int option = JOptionPane.showOptionDialog(this,
                "Do you want to purchase a reward, deposit a bottle, or go back to the main menu?",
                "Purchase Reward, Deposit Bottle, or Back to Menu",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                new String[]{"Purchase Reward", "Deposit Bottle", "Back to Menu"},
                "Purchase Reward");

        if (option == JOptionPane.YES_OPTION) {
            purchaseReward();
        } else if (option == JOptionPane.NO_OPTION) {
            depositBottle();
        } else {
            // Go back to the main menu
            startVendingMachine();
        }
    }

    private void returnToStart() {
        // Reset vending machine to initial state
        points = 0;
        plasticBottleCounter = 0;
        messageLabel.setText("Welcome to the BERTO-Bin!");
        pointsLabel.setText("Points: " + points);
        depositButton.setEnabled(false);
        purchaseRewardButton.setEnabled(false);
        doneButton.setEnabled(false);
        startPanel.setVisible(true); // Show the start panel
    }

    private void updateLabels() {
        pointsLabel.setText("Points: " + points);
    }

    public static void main(String[] args) {
        HashMap<String, HashMap<String, Object>> items = new HashMap<>();
        HashMap<String, Object> item1 = new HashMap<>();
        item1.put("name", "Ruler kit");
        item1.put("points", 20);
        items.put("Ballpen", item1);

        HashMap<String, Object> item2 = new HashMap<>();
        item2.put("name", "Note kit");
        item2.put("points", 25);
        items.put("Bondpaper", item2);

        HashMap<String, Object> item3 = new HashMap<>();
        item3.put("name", "Correction tape");
        item3.put("points", 15);
        items.put("Tape", item3);

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new VendingMachineGUI(items).setVisible(true);
            }
        });
    }
}
