package uk.ac.tees.aia.mullen.draughts.gui;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

/**
 * The main class for the DistDraughts GUI.
 *
 * @author  Alex Mullen
 *
 */
public final class Loader {
    /**
     * Private constructor to prevent this being instantiated.
     */
    private Loader() {
        // Constructor is intentionally empty.
    }
    /**
     * Main program entry point.
     *
     * @param args  supplied program arguments
     */
    public static void main(final String... args) {
        // Make sure to initialise in the EDT thread.
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // Load Nimbus theme if it is available.
                try {
                    for (LookAndFeelInfo info
                            : UIManager.getInstalledLookAndFeels()) {
                        if ("Nimbus".equals(info.getName())) {
                            UIManager.setLookAndFeel(info.getClassName());
                            break;
                        }
                    }
                } catch (Exception e) {
                    // Nimbus theme is not available.
                }
                new MainWindow().setVisible(true);
            }
        });
    }
}
