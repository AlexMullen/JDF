package uk.ac.tees.aia.mullen.draughts.gui;

import java.awt.Component;
import java.awt.Frame;
import java.awt.Dialog.ModalityType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.LayoutStyle;

import uk.ac.tees.aia.mullen.draughts.common.ArtificialPlayer;
import uk.ac.tees.aia.mullen.draughts.common.Game;
import uk.ac.tees.aia.mullen.draughts.common.GameBuilder;
import uk.ac.tees.aia.mullen.draughts.common.GameBuilderFactory;
import uk.ac.tees.aia.mullen.draughts.common.Player;
import uk.ac.tees.aia.mullen.draughts.common.evaluation.MaterialDifferenceBoardEvaluator;
import uk.ac.tees.aia.mullen.draughts.common.search.NegamaxDepthLimited;

/**
 * A dialog for allowing a user to configure a new game.
 *
 * @author  Alex Mullen
 *
 */
public class NewGameDialog {
    private javax.swing.JLabel variationLabel;
    private javax.swing.JLabel whoMovesFirstLabel;
    private javax.swing.JLabel lightPlayerConfigDifficultyLabel;
    private javax.swing.JLabel darkPlayerConfigDifficultyLabel;
    private javax.swing.JLabel forcedCapturesLabel;
    private javax.swing.JRadioButton darkPlayerAiOption;
    private javax.swing.JPanel darkPlayerConfigPanel;
    private javax.swing.JSlider darkPlayerDifficultySlider;
    private javax.swing.JRadioButton darkPlayerHumanOption;
    private javax.swing.ButtonGroup darkPlayerTypeButtonGroup;
    private javax.swing.ButtonGroup forcedCapturesButtonGroup;
    private javax.swing.JRadioButton forcedCapturesNoOption;
    private javax.swing.JRadioButton forcedCapturesYesOption;
    private javax.swing.JRadioButton lightPlayerAiOption;
    private javax.swing.ButtonGroup lightPlayerButtonGroup;
    private javax.swing.JPanel lightPlayerConfigPanel;
    private javax.swing.JSlider lightPlayerDifficultySlider;
    private javax.swing.JRadioButton lightPlayerHumanOption;
    private javax.swing.ButtonGroup moveFirstButtonGroup;
    private javax.swing.JRadioButton moveFirstDarkOption;
    private javax.swing.JRadioButton moveFirstLightOption;
    private javax.swing.JButton startButton;
    private javax.swing.JComboBox<GameBuilder> variationComboBox;

    private final JDialog dialog;
    private final GameBuilderFactory gameBuilderFactory;

    private Game gameToReturn;

    /**
     * Creates a new instance that belongs to the specified Frame and uses the
     * given game builder factory.
     *
     * @param owner    the parent Frame
     * @param factory  the game factory of game variations to use
     */
    public NewGameDialog(final Frame owner, final GameBuilderFactory factory) {
        gameBuilderFactory = factory;
        dialog = new JDialog(owner, "New game");
        dialog.setResizable(false);
        dialog.setModalityType(ModalityType.APPLICATION_MODAL);
        initComponents();
    }
    private void initComponents() {
        moveFirstButtonGroup = new ButtonGroup();
        forcedCapturesButtonGroup = new ButtonGroup();
        darkPlayerTypeButtonGroup = new ButtonGroup();
        lightPlayerButtonGroup = new ButtonGroup();
        lightPlayerHumanOption = new JRadioButton("Human", true);
        lightPlayerAiOption = new JRadioButton("AI", false);
        variationLabel = new JLabel("Variation:");
        variationComboBox = new JComboBox<>();
        whoMovesFirstLabel = new JLabel();
        moveFirstDarkOption = new JRadioButton("Dark", true);
        moveFirstLightOption = new JRadioButton("Light", false);
        lightPlayerConfigPanel = new JPanel();
        lightPlayerDifficultySlider = new JSlider();
        lightPlayerConfigDifficultyLabel = new JLabel("Difficulty");
        darkPlayerConfigPanel = new JPanel();
        darkPlayerHumanOption = new JRadioButton("Human", false);
        darkPlayerAiOption = new JRadioButton("AI", true);
        darkPlayerDifficultySlider = new JSlider();
        darkPlayerConfigDifficultyLabel = new JLabel("Difficulty");
        startButton = new JButton("Start");
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                // Handle the start button being pressed.
                final GameBuilder.Config gameConfig = new GameBuilder.Config();

                if (darkPlayerHumanOption.isSelected()) {
                    gameConfig.setDarkPlayer(new Player() {
                        @Override
                        public boolean isArtificial() {
                            return false;
                        }
                    });
                } else {
                    gameConfig.setDarkPlayer(
                            new ArtificialPlayer(
//                                  new MinimaxAlphaBetaDepthLimited(
                                    new NegamaxDepthLimited(
                                            new MaterialDifferenceBoardEvaluator(),
                                            darkPlayerDifficultySlider.getValue()),
                                    "AI-Dark") {
                                // Empty.
                    });
                }

                if (lightPlayerHumanOption.isSelected()) {
                    gameConfig.setLightPlayer(new Player() {
                        @Override
                        public boolean isArtificial() {
                            return false;
                        }
                    });
                } else {
                    gameConfig.setLightPlayer(
                            new ArtificialPlayer(
//                                    new MinimaxAlphaBetaDepthLimited(
                                    new NegamaxDepthLimited(
                                            new MaterialDifferenceBoardEvaluator(),
                                            lightPlayerDifficultySlider.getValue()),
                                    "AI-Light") {
                                // Empty.
                    });
                }

                gameToReturn = gameBuilderFactory.getBuilder(
                        ((GameBuilder)variationComboBox.getSelectedItem())
                        .getName()).build(gameConfig);
                dialog.dispose();
            }
        });
        forcedCapturesLabel = new JLabel("Forced captures:");
        forcedCapturesYesOption = new JRadioButton("Yes", true);
        forcedCapturesNoOption = new JRadioButton("No", false);
        variationComboBox.setModel(new DefaultComboBoxModel<GameBuilder>(
                gameBuilderFactory.getBuilders().toArray(
                        new GameBuilder[gameBuilderFactory.getBuilders().size()])));
        variationComboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(final ItemEvent e) {
                // Handle the game variation being changed.
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    final GameBuilder gameVariationSelected =
                            (GameBuilder) e.getItem();
                    handleGameVariationSelected(gameVariationSelected);
                }
            }
        });
        handleGameVariationSelected(
                (GameBuilder) variationComboBox.getSelectedItem());

        whoMovesFirstLabel.setText("Who moves first:");
        moveFirstButtonGroup.add(moveFirstDarkOption);
        moveFirstButtonGroup.add(moveFirstLightOption);
        lightPlayerConfigPanel.setBorder(BorderFactory.createTitledBorder("Light player"));



        lightPlayerButtonGroup.add(lightPlayerHumanOption);
        lightPlayerHumanOption.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                // Handle the human player option being selected for the light player.
                lightPlayerDifficultySlider.setEnabled(false);
            }
        });

        lightPlayerButtonGroup.add(lightPlayerAiOption);
        lightPlayerAiOption.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
             // Handle the AI player option being selected for the light player.
                lightPlayerDifficultySlider.setEnabled(true);
            }
        });

        lightPlayerDifficultySlider.setMajorTickSpacing(5);
        lightPlayerDifficultySlider.setMinimum(1);
        lightPlayerDifficultySlider.setMaximum(20);
        lightPlayerDifficultySlider.setMinorTickSpacing(1);
        lightPlayerDifficultySlider.setPaintLabels(true);
        lightPlayerDifficultySlider.setPaintTicks(true);
        lightPlayerDifficultySlider.setSnapToTicks(true);
        lightPlayerDifficultySlider.setValue(1);
        lightPlayerDifficultySlider.setEnabled(false);


        darkPlayerConfigPanel.setBorder(BorderFactory.createTitledBorder("Dark player"));

        darkPlayerTypeButtonGroup.add(darkPlayerHumanOption);
        darkPlayerHumanOption.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                // Handle the human player option being selected for the dark player.
                darkPlayerDifficultySlider.setEnabled(false);
            }
        });

        darkPlayerTypeButtonGroup.add(darkPlayerAiOption);
        darkPlayerAiOption.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                // Handle the AI player option being selected for the dark player.
                darkPlayerDifficultySlider.setEnabled(true);
            }
        });

        darkPlayerDifficultySlider.setMajorTickSpacing(5);
        darkPlayerDifficultySlider.setMinimum(1);
        darkPlayerDifficultySlider.setMaximum(20);
        darkPlayerDifficultySlider.setMinorTickSpacing(1);
        darkPlayerDifficultySlider.setPaintLabels(true);
        darkPlayerDifficultySlider.setPaintTicks(true);
        darkPlayerDifficultySlider.setSnapToTicks(true);
        darkPlayerDifficultySlider.setValue(1);
        darkPlayerDifficultySlider.setEnabled(true);


        forcedCapturesButtonGroup.add(forcedCapturesYesOption);
        forcedCapturesButtonGroup.add(forcedCapturesNoOption);

        GroupLayout lightPlayerConfigPanelLayout = new GroupLayout(lightPlayerConfigPanel);
        lightPlayerConfigPanel.setLayout(lightPlayerConfigPanelLayout);
        lightPlayerConfigPanelLayout.setHorizontalGroup(
            lightPlayerConfigPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(lightPlayerConfigPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(lightPlayerConfigPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(lightPlayerConfigPanelLayout.createSequentialGroup()
                        .addGroup(lightPlayerConfigPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addComponent(lightPlayerHumanOption)
                            .addComponent(lightPlayerAiOption)
                            .addComponent(lightPlayerConfigDifficultyLabel))
                        .addGap(0, 214, Short.MAX_VALUE))
                    .addComponent(lightPlayerDifficultySlider, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        lightPlayerConfigPanelLayout.setVerticalGroup(
            lightPlayerConfigPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(lightPlayerConfigPanelLayout.createSequentialGroup()
                .addComponent(lightPlayerHumanOption)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lightPlayerAiOption)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lightPlayerConfigDifficultyLabel)
                .addGap(1, 1, 1)
                .addComponent(lightPlayerDifficultySlider, GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE))
        );



        GroupLayout darkPlayerConfigPanelLayout = new GroupLayout(darkPlayerConfigPanel);
        darkPlayerConfigPanel.setLayout(darkPlayerConfigPanelLayout);
        darkPlayerConfigPanelLayout.setHorizontalGroup(
            darkPlayerConfigPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(darkPlayerConfigPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(darkPlayerConfigPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(darkPlayerConfigPanelLayout.createSequentialGroup()
                        .addGroup(darkPlayerConfigPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addComponent(darkPlayerHumanOption)
                            .addComponent(darkPlayerAiOption)
                            .addComponent(darkPlayerConfigDifficultyLabel))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(darkPlayerDifficultySlider, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        darkPlayerConfigPanelLayout.setVerticalGroup(
            darkPlayerConfigPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(darkPlayerConfigPanelLayout.createSequentialGroup()
                .addComponent(darkPlayerHumanOption)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(darkPlayerAiOption)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(darkPlayerConfigDifficultyLabel)
                .addGap(1, 1, 1)
                .addComponent(darkPlayerDifficultySlider, GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE))
        );

        final GroupLayout layout = new GroupLayout(dialog.getContentPane());
        dialog.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(lightPlayerConfigPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(darkPlayerConfigPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(140, 140, 140)
                        .addComponent(startButton)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addComponent(whoMovesFirstLabel)
                            .addComponent(variationLabel)
                            .addComponent(forcedCapturesLabel))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addComponent(variationComboBox, GroupLayout.PREFERRED_SIZE, 143, GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                    .addComponent(moveFirstDarkOption)
                                    .addComponent(forcedCapturesYesOption))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                    .addComponent(forcedCapturesNoOption)
                                    .addComponent(moveFirstLightOption, GroupLayout.PREFERRED_SIZE, 58, GroupLayout.PREFERRED_SIZE))))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(variationLabel)
                    .addComponent(variationComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(whoMovesFirstLabel)
                    .addComponent(moveFirstDarkOption)
                    .addComponent(moveFirstLightOption))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(forcedCapturesLabel)
                    .addComponent(forcedCapturesYesOption)
                    .addComponent(forcedCapturesNoOption))
                .addGap(13, 13, 13)
                .addComponent(darkPlayerConfigPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lightPlayerConfigPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(startButton)
                .addContainerGap(15, Short.MAX_VALUE))
        );

        dialog.pack();
    }
    private void handleGameVariationSelected(final GameBuilder builder) {
        if (builder.isFirstTurnConfigurable()) {
            moveFirstDarkOption.setEnabled(true);
            moveFirstLightOption.setEnabled(true);
        } else {
            // Not configurable so disable the options.
            moveFirstDarkOption.setEnabled(false);
            moveFirstLightOption.setEnabled(false);
        }
        if (builder.isForcedCapturesConfigurable()) {
            forcedCapturesNoOption.setEnabled(true);
            forcedCapturesYesOption.setEnabled(true);
        } else {
            // Not configurable so disable the options.
            forcedCapturesNoOption.setEnabled(false);
            forcedCapturesYesOption.setEnabled(false);
        }
    }
    /**
     * Displays this dialog in the centre of the specified component.
     *
     * @param component  the component to centre the dialog relative to
     * @return           a created {@link Game} instance or <code>null</code>
     *                   if the dialog was cancelled
     */
    public final Game showDialog(final Component component) {
        /*
         * Make sure we do not return the previously created game by this panel
         * in the event this is reused then cancelled.
         */
        gameToReturn = null;
        // Centre it relative to the component.
        dialog.setLocationRelativeTo(component);
        dialog.setVisible(true);
        /* .
         * .
         * .
         * The above call blocks until the dialog is closed.
         * .
         * .
         * .
         */
        // Return the created game object if the start button was pressed.
        return gameToReturn;
    }
}
