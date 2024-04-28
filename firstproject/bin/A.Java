import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

public class VendingMachineGUI extends JFrame {

    private HashMap<String, HashMap<String, Object>> items;
    private double balance;
    private int points;
    private HashMap<String, Integer> inventory;
    private int plasticBottleCounter;
    private JLabel balanceLabel;
    private JLabel pointsLabel;
    private JLabel inventoryLabel;
    private JLabel messageLabel;

    public VendingMachineGUI(HashMap<String, HashMap<String, Object>> items) {
        this.items = items;
        this.balance = 0.00;
        this.points = 0;
        this.inventory = new HashMap<>();
        this.plasticBottleCounter = 0;

        setTitle("BERTO-Bin Vending Machine");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLayout(new BorderLayout());

        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new GridLayout(3, 1));

        JButton depositButton = new JButton("Deposit Bottle");
        depositButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                depositBottle();
            }
        });
        controlPanel.add(depositButton);

        JButton purchaseButton = new JButton("Purchase Item");
        purchaseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                purchaseItem();
            }
        });
        controlPanel.add(purchaseButton);

        JButton checkBalanceButton = new JButton("Check Balance");
        checkBalanceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkBalance();
            }
        });
        controlPanel.add(checkBalanceButton);

        add(controlPanel, BorderLayout.WEST);

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new GridLayout(4, 1));
        balanceLabel = new JLabel("Balance: $" + balance);
        pointsLabel = new JLabel("Points: " + points);
        inventoryLabel = new JLabel("Inventory: " + inventory);
        messageLabel = new JLabel("Welcome to the BERTO-Bin!");
        infoPanel.add(balanceLabel);
        infoPanel.add(pointsLabel);
        infoPanel.add(inventoryLabel);
        infoPanel.add(messageLabel);
        add(infoPanel, BorderLayout.CENTER);
    }

    private void depositBottle() {
        String bottleSize = JOptionPane.showInputDialog(this, "Enter the size of the bottle (small/large):").toLowerCase();
        if (bottleSize.equals("small") || bottleSize.equals("large")) {
            plasticBottleCounter++;
            messageLabel.setText(bottleSize.toUpperCase() + " bottle deposited.");
        } else {
            JOptionPane.showMessageDialog(this, "Invalid bottle size. Please enter 'small' or 'large'.");
        }
    }

    private void purchaseItem() {
        // Implement purchase functionality
        // You can display a dialog to select an item and quantity
        JOptionPane.showMessageDialog(this, "Purchase functionality not implemented yet.");
    }

    private void checkBalance() {
        balanceLabel.setText("Balance: $" + balance);
        pointsLabel.setText("Points: " + points);
        inventoryLabel.setText("Inventory: " + inventory);
    }

    public static void main(String[] args) {
        HashMap<String, HashMap<String, Object>> items = new HashMap<>();
        HashMap<String, Object> item1 = new HashMap<>();
        item1.put("name", "ballpen");
        item1.put("points", 30);
        item1.put("quantity", 10);
        HashMap<String, Object> item2 = new HashMap<>();
        item2.put("name", "bondpaper");
        item2.put("points", 50);
        item2.put("quantity", 10);
        HashMap<String, Object> item3 = new HashMap<>();
        item3.put("name", "tape");
        item3.put("points", 40);
        item3.put("quantity", 10);
        items.put("1", item1);
        items.put("2", item2);
        items.put("3", item3);

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new VendingMachineGUI(items).setVisible(true);
            }
        });
    }
}
