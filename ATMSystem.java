import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ATMSystem extends JFrame implements ActionListener {

    // Panels for card layout
    CardLayout cardLayout;
    JPanel mainPanel;

    // Login components
    JTextField cardNumberField;
    JPasswordField pinField;

    // Balance
    double balance = 10000.0; // initial demo balance
    JLabel balanceLabel;

    public ATMSystem() {
        setTitle("ATM Simulation System");
        setSize(500, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Modern look
        UIManager.put("Button.arc", 20);
        UIManager.put("TextField.arc", 15);
        UIManager.put("PasswordField.arc", 15);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Add different screens
        mainPanel.add(loginScreen(), "Login");
        mainPanel.add(menuScreen(), "Menu");
        mainPanel.add(balanceScreen(), "Balance");
        mainPanel.add(withdrawScreen(), "Withdraw");
        mainPanel.add(depositScreen(), "Deposit");

        add(mainPanel);
        cardLayout.show(mainPanel, "Login");
    }

    // ----------------- Login Screen -----------------
    private JPanel loginScreen() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(30, 30, 60));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel title = new JLabel("ATM LOGIN");
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setForeground(Color.WHITE);

        JLabel cardLabel = new JLabel("Card Number:");
        cardLabel.setForeground(Color.WHITE);
        JLabel pinLabel = new JLabel("PIN:");
        pinLabel.setForeground(Color.WHITE);

        cardNumberField = new JTextField(15);
        pinField = new JPasswordField(15);

        JButton loginBtn = new JButton("Login");
        loginBtn.addActionListener(e -> {
            String card = cardNumberField.getText();
            String pin = new String(pinField.getPassword());

            if (card.equals("123456") && pin.equals("1234")) {
                JOptionPane.showMessageDialog(this, "Login Successful!");
                cardLayout.show(mainPanel, "Menu");
            } else {
                JOptionPane.showMessageDialog(this, "Invalid Credentials!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        panel.add(title, gbc);

        gbc.gridwidth = 1; gbc.gridy = 1;
        panel.add(cardLabel, gbc);
        gbc.gridx = 1;
        panel.add(cardNumberField, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(pinLabel, gbc);
        gbc.gridx = 1;
        panel.add(pinField, gbc);

        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        panel.add(loginBtn, gbc);

        return panel;
    }

    // ----------------- Menu Screen -----------------
    private JPanel menuScreen() {
        JPanel panel = new JPanel(new GridLayout(5, 1, 10, 10));
        panel.setBackground(new Color(245, 245, 245));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 80, 30, 80));

        JLabel title = new JLabel("ATM Main Menu", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 22));

        JButton balanceBtn = new JButton("Check Balance");
        JButton withdrawBtn = new JButton("Withdraw Money");
        JButton depositBtn = new JButton("Deposit Money");
        JButton exitBtn = new JButton("Exit");

        balanceBtn.addActionListener(e -> {
            updateBalance();
            cardLayout.show(mainPanel, "Balance");
        });
        withdrawBtn.addActionListener(e -> cardLayout.show(mainPanel, "Withdraw"));
        depositBtn.addActionListener(e -> cardLayout.show(mainPanel, "Deposit"));
        exitBtn.addActionListener(e -> System.exit(0));

        panel.add(title);
        panel.add(balanceBtn);
        panel.add(withdrawBtn);
        panel.add(depositBtn);
        panel.add(exitBtn);

        return panel;
    }

    // ----------------- Balance Screen -----------------
    private JPanel balanceScreen() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);

        balanceLabel = new JLabel("Your Balance: ₹" + balance, JLabel.CENTER);
        balanceLabel.setFont(new Font("Arial", Font.BOLD, 22));

        JButton backBtn = new JButton("Back to Menu");
        backBtn.addActionListener(e -> cardLayout.show(mainPanel, "Menu"));

        panel.add(balanceLabel, BorderLayout.CENTER);
        panel.add(backBtn, BorderLayout.SOUTH);

        return panel;
    }

    // ----------------- Withdraw Screen -----------------
    private JPanel withdrawScreen() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(230, 240, 250));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10,10,10,10);

        JLabel label = new JLabel("Enter amount to withdraw:");
        JTextField amountField = new JTextField(10);

        JButton withdrawBtn = new JButton("Withdraw");
        withdrawBtn.addActionListener(e -> {
            try {
                double amt = Double.parseDouble(amountField.getText());
                if (amt <= balance && amt > 0) {
                    balance -= amt;
                    JOptionPane.showMessageDialog(this, "₹" + amt + " withdrawn successfully!");
                    updateBalance();
                    cardLayout.show(mainPanel, "Menu");
                } else {
                    JOptionPane.showMessageDialog(this, "Insufficient Balance or Invalid Amount!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Enter valid amount!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        JButton backBtn = new JButton("Back");
        backBtn.addActionListener(e -> cardLayout.show(mainPanel, "Menu"));

        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(label, gbc);
        gbc.gridx = 1;
        panel.add(amountField, gbc);
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 2;
        panel.add(withdrawBtn, gbc);
        gbc.gridy = 2;
        panel.add(backBtn, gbc);

        return panel;
    }

    // ----------------- Deposit Screen -----------------
    private JPanel depositScreen() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(240, 250, 240));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10,10,10,10);

        JLabel label = new JLabel("Enter amount to deposit:");
        JTextField amountField = new JTextField(10);

        JButton depositBtn = new JButton("Deposit");
        depositBtn.addActionListener(e -> {
            try {
                double amt = Double.parseDouble(amountField.getText());
                if (amt > 0) {
                    balance += amt;
                    JOptionPane.showMessageDialog(this, "₹" + amt + " deposited successfully!");
                    updateBalance();
                    cardLayout.show(mainPanel, "Menu");
                } else {
                    JOptionPane.showMessageDialog(this, "Enter valid amount!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Enter valid amount!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        JButton backBtn = new JButton("Back");
        backBtn.addActionListener(e -> cardLayout.show(mainPanel, "Menu"));

        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(label, gbc);
        gbc.gridx = 1;
        panel.add(amountField, gbc);
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 2;
        panel.add(depositBtn, gbc);
        gbc.gridy = 2;
        panel.add(backBtn, gbc);

        return panel;
    }

    private void updateBalance() {
        balanceLabel.setText("Your Balance: ₹" + balance);
    }

    @Override
    public void actionPerformed(ActionEvent e) {}

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new ATMSystem().setVisible(true);
        });
    }
}
