package stock.view;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class debtUI extends JFrame {
    private int walletAmount = 1000;  // Initial wallet amount
    private int transferAmount = 0;   // Initial transfer amount
    private int debt = 10000;//take from controller interphase in MVC

    private JLabel debtLabel;
    private JLabel walletLabel;
    private JLabel transferLabel;

    public debtUI() {
        setTitle("Transfer Window");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create labels
        debtLabel = new JLabel("Debt: $" + debt);
        walletLabel = new JLabel("Wallet: $" + walletAmount);
        transferLabel = new JLabel("Transfer Amount: $" + transferAmount);

        // Create buttons
        JButton incrementButton = new JButton("+100");
        JButton decrementButton = new JButton("-100");
        JButton acceptButton = new JButton("Accept");

        // Add action listeners to buttons
        incrementButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                incrementTransferAmount(100);
            }
        });

        decrementButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                decrementTransferAmount(100);
            }
        });

        acceptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                acceptButtonActionPerformed();
            }
        });

        // Set layout manager
        setLayout(new GridLayout(4, 1));

        // Add components to the frame
        add(debtLabel);
        add(walletLabel);
        add(transferLabel);
        add(incrementButton);
        add(decrementButton);
        add(acceptButton);

        // Display the frame
        setVisible(true);
    }

    private void incrementTransferAmount(int increment) {
        if (transferAmount + increment <= walletAmount) {
            transferAmount += increment;
            updateLabels();
        }
    }

    private void decrementTransferAmount(int decrement) {
        if (transferAmount - decrement >= 0) {
            transferAmount -= decrement;
            updateLabels();
        }
    }

    private void acceptButtonActionPerformed() {
        // Perform action when the "Accept" button is clicked
        debt = debt - transferAmount;
        updateLabels();
        System.out.println("Transfer accepted. Update your debt and wallet logic here.");
    }

    private void updateLabels() {
        debtLabel.setText("Debt: $" + debt);
        walletLabel.setText("Wallet: $" + (walletAmount - transferAmount));
        transferLabel.setText("Transfer Amount: $" + transferAmount);
    }

    /* public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new debtUI();
        });
    } */
}

