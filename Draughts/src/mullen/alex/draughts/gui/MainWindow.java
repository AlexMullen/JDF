package mullen.alex.draughts.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import mullen.alex.draughts.common.Game;
import mullen.alex.draughts.common.GameBuilderFactory;
import mullen.alex.draughts.english.EnglishDraughtsGameBuilder;
import mullen.alex.draughts.international.InternationalDraughtsGameBuilder;

/**
 * Represents the main program window.
 *
 * @author  Alex Mullen
 *
 */
@SuppressWarnings("serial")
public class MainWindow extends JFrame {
    /** The title of the window. */
    private static final String WINDOW_TITLE = "DistDraughts";
    /** The initial width the window should be in pixels. */
    private static final int INITIAL_WINDOW_WIDTH = 800;
    /** The initial height the window should be in pixels. */
    private static final int INITIAL_WINDOW_HEIGHT = 600;
    /** The minimum width the window is allowed to be in pixels. */
    private static final int MINIMUM_WINDOW_WIDTH = 350;
    /** The minimum height the window is allowed to be in pixels. */
    private static final int MINIMUM_WINDOW_HEIGHT = 350;
    /** Holds the current game panel frame if any. */
    private GamePanel currentGamePanel;
    /**
     * Creates a new instance.
     */
    public MainWindow() {
        final GameBuilderFactory gameBuilderFactory =
                new GameBuilderFactory();

        gameBuilderFactory.registerBuilder(new EnglishDraughtsGameBuilder());
        gameBuilderFactory.registerBuilder(
                new InternationalDraughtsGameBuilder());

        final NewGameDialog newGameDialog =
                new NewGameDialog(MainWindow.this, gameBuilderFactory);

        // Window options.
        setTitle(WINDOW_TITLE);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(
                new Dimension(INITIAL_WINDOW_WIDTH, INITIAL_WINDOW_HEIGHT));
        setMinimumSize(
                new Dimension(MINIMUM_WINDOW_WIDTH, MINIMUM_WINDOW_HEIGHT));
        // Set the window icon.
        setIconImage(new ImageIcon(
                getClass().getResource("resources/icon_64.png")).getImage());
        setLayout(new BorderLayout());
        // Add menu bar.
        final JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        // Add "File" menu.
        final JMenu fileMenu = new JMenu("File");
        menuBar.add(fileMenu);
        // Add "New Game" item.
        final JMenuItem newGameItem = new JMenuItem("New Game");
        newGameItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                /*
                 * Display a dialog for allowing the user to be able to
                 * configure and create a game.
                 */
                final Game createdGame =
                        newGameDialog.showDialog(MainWindow.this);
                if (createdGame != null) {
                    // Remove and shutdown the current one.
                    if (currentGamePanel != null) {
                        currentGamePanel.stop();
                        MainWindow.this.remove(currentGamePanel);
                    }
                    currentGamePanel = new GamePanel(createdGame);
                    add(currentGamePanel, BorderLayout.CENTER);
                    /*
                     * Re-validate the layout hierarchy so that our newly added
                     * panel is displayed.
                     */
                    MainWindow.this.revalidate();
                    currentGamePanel.start();
                }
            }
        });
        fileMenu.add(newGameItem);
        // Add "Exit" item.
        final JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                System.exit(0);
            }
        });
        fileMenu.addSeparator();
        fileMenu.add(exitItem);
        pack();
    }
}
