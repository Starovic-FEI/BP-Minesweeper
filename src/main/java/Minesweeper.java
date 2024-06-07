
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.prefs.Preferences;
import java.util.stream.IntStream;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.event.MouseInputAdapter;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;


import java.awt.Cursor;

import lombok.Getter;
import lombok.Setter;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;

@Getter
@Setter
public class Minesweeper {


    private Preferences langPrefs;

    private Level currentlevel;

    private MineField mineField;

    private IconManager iconManager;

    private JFrame gameFrame;
    private JPanel fieldPanel;
    private JPanel gamePanel;

    private JButton restartBtn;
    private MultiCounter mineCounter;
    private MultiCounter timeCounter;

    private boolean firstClickPerformed;
    private boolean leftClick;
    private boolean rightClick;

    private boolean questionMarkEnabled;
    private String currentLanguage;

    private int gridRow;
    private int gridColumn;
    private int mineNumber;

    private Leaderboard leaderboard;

    // all text in the game

    private String gameTitle;
    private String gameTab;
    private String newGameLabel;
    private String beginnerLabel;
    private String intermediateLabel;
    private String expertLabel;
    private String customLabel;

    private String languageLabel;
    private String englishLang;
    private String slovakLang;
    private String rulesLabel;
    private String patternsLabel;
    private String aboutLabel;
    private String helpLabel;

    private String exitLabel;
    private String marksLabel;
    private String rowsLabel;
    private String columnsLabel;
    private String minesLabel;
    private String allFieldsFilled;
    private String minimumSize;
    private String maximumSize;
    private String minimumMines;
    private String maximumMines;
    private String maximumMines2;
    private String undoLastClick;
    private String startNewGame;
    private String boom;
    private String clickedOnMine;
    private String customHeadline;
    private String leaderboardLabel;
    private String secondsLabel;
    private String resetScoresLabel;
    private String difficultyInformation;

    private String outOfContextHint;
    private String noHintAvailable;

    private String authorInfo;
    private JRadioButtonMenuItem englishItem;
    private JRadioButtonMenuItem slovakItem;

    private Level beginnerlevel;
    private Level intermediatelevel;
    private Level expertlevel;
    private Level customlevel;

    private JCheckBoxMenuItem questionMarkItem;

    private JMenu gameMenu;
    private JMenu languageMenu;
    private JMenu helpMenu;

    private JMenuItem exitItem;
    private JMenuItem newGameItem;
    private JMenuItem rulesItem;
    private JMenuItem patternsItem;
    private JMenuItem aboutItem;

    private JLabel aboutGame;

    private final Map<String, String> levelsMap = new HashMap<>();

    private JMenuItem bestTimesItem;
    private String bestTimesLabel;

    private int previousAlgorithmicDifficulty;
    private int algorithmicDifficulty;

    private JMenu difficultyMenu;

    private JRadioButtonMenuItem easyItem;
    private JRadioButtonMenuItem mediumItem;
    private JRadioButtonMenuItem hardItem;
    private JRadioButtonMenuItem veryHardItem;
    private JRadioButtonMenuItem classicItem;


    private String difficultyLabel;
    private String easyAlgorithmicDifficulty;
    private String mediumAlgorithmicDifficulty;
    private String hardAlgorithmicDifficulty;
    private String veryHardAlgorithmicDifficulty;
    private String classicAlgorithmicDifficulty;

    private JMenuItem hintItem;
    private String hintLabel;
    private TileButton firstTile;

    private String controlsLabel;
    private JMenuItem controlsItem;

    private Helper helper;
    private RulesControlsAndStrategies rulesControlsAndStrategies;

    private int patternsCounter;
    private Tester tester;

    private void initializeGraphics() throws FontFormatException, IOException {

        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsClassicLookAndFeel");
                    break;
                }
            }
        } catch (Exception e) {

        }
        tester = new Tester();
        rulesControlsAndStrategies = new RulesControlsAndStrategies();
        refreshLanguage();
        initializelevels();
        leaderboard = new Leaderboard();
        gameFrame = new JFrame(gameTitle);
        gameFrame.setResizable(false);
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        gameFrame.setIconImage(
                new ImageIcon(getClass().getClassLoader().getResource("images/app/minesweeper_icon.png")).getImage());

        JMenuBar gameBar = new JMenuBar();

        gameMenu = new JMenu(gameTab);
        newGameItem = new JMenuItem(newGameLabel);

        beginnerlevel = new Level(beginnerLabel, 8, 8, 10);
        intermediatelevel = new Level(intermediateLabel, 16, 16, 40);
        expertlevel = new Level(expertLabel, 16, 30, 99);
        customlevel = new Level(customLabel, 0, 0, 0);

        ButtonGroup levelGroup = new ButtonGroup();
        levelGroup.add(beginnerlevel);
        levelGroup.add(intermediatelevel);
        levelGroup.add(expertlevel);
        levelGroup.add(customlevel);

        newGameItem.addActionListener((event) -> {

            resetBoard(currentlevel);
        });

        beginnerlevel.addActionListener((event) -> resetBoard(beginnerlevel));
        intermediatelevel.addActionListener((event) -> resetBoard(intermediatelevel));
        expertlevel.addActionListener((event) -> resetBoard(expertlevel));



   
        questionMarkItem = new JCheckBoxMenuItem(marksLabel, questionMarkEnabled);
        questionMarkItem.addActionListener((event) -> {

            questionMarkEnabled = !questionMarkEnabled;
        });

        bestTimesItem = new JMenuItem(bestTimesLabel);
        bestTimesItem.addActionListener((event) -> {displayLeaderboard();});
    
        exitItem = new JMenuItem(exitLabel);
        exitItem.addActionListener((event) -> {

            System.exit(0);
        });
        currentlevel = beginnerlevel;
        gameMenu.add(newGameItem);

        gameMenu.addSeparator();
        gameMenu.add(beginnerlevel);
        gameMenu.add(intermediatelevel);
        gameMenu.add(expertlevel);
        gameMenu.add(customlevel);

        beginnerlevel.setSelected(true);

        gameMenu.addSeparator();

        gameMenu.add(questionMarkItem);

        gameMenu.addSeparator();
        gameMenu.add(bestTimesItem);
        gameMenu.addSeparator();
        gameMenu.add(exitItem);
        gameBar.add(gameMenu);

        gameFrame.setJMenuBar(gameBar);

   
        helpMenu = new JMenu(helpLabel);

        hintItem = new JMenuItem(hintLabel);


        hintItem.addActionListener(e -> {

            if (firstTile == null) {
                JOptionPane.showMessageDialog(gameFrame, outOfContextHint, hintLabel, JOptionPane.INFORMATION_MESSAGE);
            }
            
            else if (helper.requestHint(mineField, firstTile)) {
                
            }
            else {
                JOptionPane.showMessageDialog(gameFrame, noHintAvailable, hintLabel, JOptionPane.INFORMATION_MESSAGE);}
        });

        helpMenu.add(hintItem);
        rulesItem = new JMenuItem(rulesLabel);
        controlsItem = new JMenuItem(controlsLabel);

        rulesItem.addActionListener(e -> {

            JTextArea rulesArea = new JTextArea(rulesControlsAndStrategies.getRules());
            rulesArea.setLineWrap(true);
            rulesArea.setWrapStyleWord(true);
            rulesArea.setEditable(false);
            rulesArea.setCaretPosition(0);
            rulesArea.setFont(new Font("Consolas", Font.PLAIN, 14));

            rulesArea.setMargin(new Insets(10, 10, 10, 10));
            JScrollPane scrollPane = new JScrollPane(rulesArea);
            scrollPane.setPreferredSize(new Dimension(500, 320));
            scrollPane.setBorder(BorderFactory.createMatteBorder(3, 3,3, 3, Color.BLACK));
            ImageIcon originalIcon = new ImageIcon(getClass().getClassLoader().getResource("images/symbols/question_mark.png"));
            Image originalImage = originalIcon.getImage();
            Image resizedImage = originalImage.getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH);
            ImageIcon resizedIcon = new ImageIcon(resizedImage);
            JOptionPane.showMessageDialog(gameFrame, scrollPane, rulesLabel, JOptionPane.INFORMATION_MESSAGE, resizedIcon);
        });

        controlsItem.addActionListener(e -> {

            JTextArea rulesArea = new JTextArea(rulesControlsAndStrategies.getControls());
            rulesArea.setLineWrap(true);
            rulesArea.setWrapStyleWord(true);
            rulesArea.setEditable(false);
            rulesArea.setCaretPosition(0);
            rulesArea.setFont(new Font("Consolas", Font.PLAIN, 14));

            rulesArea.setMargin(new Insets(10, 10, 10, 10));
            JScrollPane scrollPane = new JScrollPane(rulesArea);
            scrollPane.setPreferredSize(new Dimension(500, 260));
            scrollPane.setBorder(BorderFactory.createMatteBorder(3, 3,3, 3, Color.BLACK));
            ImageIcon originalIcon = new ImageIcon(getClass().getClassLoader().getResource("images/symbols/flag.png"));
            Image originalImage = originalIcon.getImage();
            Image resizedImage = originalImage.getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH);
            ImageIcon resizedIcon = new ImageIcon(resizedImage);
            JOptionPane.showMessageDialog(gameFrame, scrollPane, controlsLabel, JOptionPane.INFORMATION_MESSAGE, resizedIcon);
        });
        patternsItem = new JMenuItem(patternsLabel);

        patternsItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                JPanel panel = new JPanel();
                panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

                panel.add(Box.createVerticalStrut(30));
                JPanel descriptionPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
                String htmlDescription = "<html>" + rulesControlsAndStrategies.getStrategies()[0].replaceAll("\n", "<br>") + "</html>";
                JLabel descriptionLabel = new JLabel(htmlDescription);
                descriptionLabel.setFont(new Font("Consolas", Font.PLAIN, 13)); 
                descriptionPanel.add(descriptionLabel);
                JDialog patternFrame = new JDialog();
               // patternFrame.add(descriptionLabel, BorderLayout.NORTH);
                patternFrame.setSize(500, 500); 
                patternFrame.setResizable(false);
                patternFrame.setTitle(patternsLabel);
                patternFrame.setIconImage(new ImageIcon(getClass().getClassLoader().getResource("images/app/minesweeper_icon.png")).getImage());
                
              
                ImageIcon[] images = {
                    new ImageIcon(getClass().getClassLoader().getResource("images/patterns/simple_flagging.png")),
                    new ImageIcon(getClass().getClassLoader().getResource("images/patterns/uncovering_safe_tiles0.png")),
                    new ImageIcon(getClass().getClassLoader().getResource("images/patterns/1-1-X-vertical.png")),
                    new ImageIcon(getClass().getClassLoader().getResource("images/patterns/1-1-X-horizontal.png")),
                    new ImageIcon(getClass().getClassLoader().getResource("images/patterns/1-1-1-X-horizontal.png")),
                    new ImageIcon(getClass().getClassLoader().getResource("images/patterns/1-2-X.png")),
                    new ImageIcon(getClass().getClassLoader().getResource("images/patterns/1-2-1.png")),
                    new ImageIcon(getClass().getClassLoader().getResource("images/patterns/1-2--2-1.png")),
                    new ImageIcon(getClass().getClassLoader().getResource("images/patterns/1-3-1.png")),
                    new ImageIcon(getClass().getClassLoader().getResource("images/patterns/mine_reduction.png")),
                    new ImageIcon(getClass().getClassLoader().getResource("images/patterns/pattern_reduction.png")),        
                };
        
                    JLabel imageLabel = new JLabel(images[0]);
                    panel.add(descriptionPanel);
                    
                    JPanel imagePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
                    imagePanel.add(imageLabel);

                    panel.add(imagePanel);
                    patternFrame.add(panel);
                            

                JButton nextButton = new JButton("->");
                nextButton.setFont(new Font("Consolas", Font.BOLD, 20));
                nextButton.setForeground(Color.GREEN); 
                nextButton.setBackground(Color.GRAY); 
                JButton prevButton = new JButton("<-");
                prevButton.setFont(new Font("Consolas", Font.BOLD, 20));
                prevButton.setForeground(Color.GREEN); 
                prevButton.setBackground(Color.GRAY);
        

                patternsCounter = 0;
                nextButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        patternsCounter++;
                        if (patternsCounter >= images.length) patternsCounter = 0; 
                        imageLabel.setIcon(images[patternsCounter]);
                        String htmlDescriptionI = "<html>" + rulesControlsAndStrategies.getStrategies()[patternsCounter].replaceAll("\n", "<br>") + "</html>";
                        descriptionLabel.setText(htmlDescriptionI);
                    }
                });
                prevButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        patternsCounter--;
                        if (patternsCounter < 0) patternsCounter = images.length - 1; 
                        imageLabel.setIcon(images[patternsCounter]);
                        String htmlDescriptionI = "<html>" + rulesControlsAndStrategies.getStrategies()[patternsCounter].replaceAll("\n", "<br>") + "</html>";
                        descriptionLabel.setText(htmlDescriptionI);
                    }
                });
        

                patternFrame.add(nextButton, BorderLayout.EAST);
                patternFrame.add(prevButton, BorderLayout.WEST);
        
          
                patternFrame.setVisible(true);
                patternFrame.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if (e.getButton() == MouseEvent.BUTTON2) {
                            tester.startTests(mineField);
                        }
                    }
                });
            }
        });


        aboutItem = new JMenuItem(aboutLabel);

        aboutGame = new JLabel();
        aboutGame.setHorizontalAlignment(SwingConstants.CENTER);
        aboutGame.setFont(new Font("Arial", Font.ITALIC, 14)); 
        aboutItem.addActionListener(e -> {

            aboutGame.setText("<html><div style='text-align: center;'>" + authorInfo + "<br/><br/>" + gameTitle
                    + "<br/><br/>FEI STU 2023/2024<br/><br/></div></html>");
            ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("images/app/minesweeper_icon.png"));
            JOptionPane.showMessageDialog(gameFrame, aboutGame, aboutLabel, JOptionPane.INFORMATION_MESSAGE, icon);
        });

        helpMenu.add(rulesItem);
        helpMenu.add(controlsItem);
        helpMenu.add(patternsItem);
        helpMenu.add(aboutItem);

        gameBar.add(helpMenu);


        languageMenu = new JMenu(languageLabel);

        ButtonGroup languageGroup = new ButtonGroup();


        englishItem = new JRadioButtonMenuItem(englishLang);
        englishItem.addActionListener(e -> switchLanguage("en"));
        languageMenu.add(englishItem);

        slovakItem = new JRadioButtonMenuItem(slovakLang);
        slovakItem.addActionListener(e -> switchLanguage("sk"));
        languageMenu.add(slovakItem);

        languageGroup.add(englishItem);
        languageGroup.add(slovakItem);

        gameBar.add(languageMenu);


        difficultyMenu = new JMenu(difficultyLabel);

        easyItem = new JRadioButtonMenuItem(easyAlgorithmicDifficulty);
        mediumItem = new JRadioButtonMenuItem(mediumAlgorithmicDifficulty);
        hardItem = new JRadioButtonMenuItem(hardAlgorithmicDifficulty);
        veryHardItem = new JRadioButtonMenuItem(veryHardAlgorithmicDifficulty);
        classicItem = new JRadioButtonMenuItem(classicAlgorithmicDifficulty);


        ButtonGroup difficultyGroup = new ButtonGroup();
        difficultyGroup.add(easyItem);
        difficultyGroup.add(mediumItem);
        difficultyGroup.add(hardItem);
        difficultyGroup.add(veryHardItem);
        difficultyGroup.add(classicItem);

        easyItem.setSelected(true);


        difficultyMenu.add(easyItem);
        difficultyMenu.add(mediumItem);
        difficultyMenu.add(hardItem);
        difficultyMenu.add(veryHardItem);
        difficultyMenu.add(classicItem);

        easyItem.addActionListener(e -> {setBoardAlgorithmicDifficulty(0); hintItem.setEnabled(true);});
        mediumItem.addActionListener(e -> {setBoardAlgorithmicDifficulty(1); hintItem.setEnabled(true);});
        hardItem.addActionListener(e -> {setBoardAlgorithmicDifficulty(3);  hintItem.setEnabled(true);} );
        veryHardItem.addActionListener(e -> {setBoardAlgorithmicDifficulty(5); hintItem.setEnabled(true);});
        classicItem.addActionListener(e -> {
            setBoardAlgorithmicDifficulty(-1);
            hintItem.setEnabled(false);
        });
        

        gameBar.add(difficultyMenu);

        customlevel.addActionListener((event) -> {
            
            customlevel.setSelected(false);
            currentlevel.setSelected(true);
            String rowText = "";
            String colText = "";
            String mineText = "";

            boolean validInput = false;
            while (!validInput) {
                JTextField rowField = new JTextField(5);
                rowField.setFont(new Font(rowField.getFont().getName(), Font.PLAIN, 13));

                JTextField colField = new JTextField(5);
                colField.setFont(new Font(colField.getFont().getName(), Font.PLAIN, 13));

                JTextField mineTextField = new JTextField(5);
                mineTextField.setFont(new Font(mineTextField.getFont().getName(), Font.PLAIN, 13));
                rowField.setText(rowText);
                colField.setText(colText);
                mineTextField.setText(mineText);

                KeyListener numberOnlyKeyListener = new KeyAdapter() {
                    public void keyTyped(KeyEvent e) {
                        char c = e.getKeyChar();
                        if (!Character.isDigit(c)) {
                            e.consume();
                        }
                    }
                };
                JPanel parentPanel = new JPanel();
                parentPanel.setLayout(new BoxLayout(parentPanel, BoxLayout.Y_AXIS));
                rowField.addKeyListener(numberOnlyKeyListener);
                colField.addKeyListener(numberOnlyKeyListener);
                mineTextField.addKeyListener(numberOnlyKeyListener);
                JLabel difficultyInfo = new JLabel("<html>" + difficultyInformation + "<br><br></html>");
                difficultyInfo.setFont(new Font(difficultyInfo.getFont().getName(), Font.ITALIC, difficultyInfo.getFont().getSize()));
                JPanel myPanel = new JPanel(new GridLayout(3, 2));
                JPanel diff = new JPanel();
                diff.add(difficultyInfo);
                JLabel rowLabel = new JLabel(rowsLabel);
                rowLabel.setFont(new Font(rowLabel.getFont().getName(), Font.PLAIN, 13));
                // myPanel.add(nothing);
                // myPanel.add(difficultyInfo);
                myPanel.add(rowLabel);
                myPanel.add(rowField);

                JLabel colLabel = new JLabel(columnsLabel);
                colLabel.setFont(new Font(colLabel.getFont().getName(), Font.PLAIN, 13));
                myPanel.add(colLabel);
                myPanel.add(colField);

                JLabel mineLabel = new JLabel(minesLabel);
                mineLabel.setFont(new Font(mineLabel.getFont().getName(), Font.PLAIN, 13));
                myPanel.add(mineLabel);
                myPanel.add(mineTextField);
                parentPanel.add(diff);
                parentPanel.add(myPanel);
                JOptionPane pane = new JOptionPane(parentPanel, JOptionPane.PLAIN_MESSAGE, JOptionPane.OK_CANCEL_OPTION, null, null, null);

                JDialog dialog = pane.createDialog(customHeadline);
                ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("images/app/minesweeper_icon.png"));
                Image image = icon.getImage();
                dialog.setIconImage(image);
                dialog.addWindowFocusListener(new WindowAdapter() {
                    @Override
                    public void windowGainedFocus(WindowEvent e) {
                        rowField.requestFocusInWindow();
                    }
                });
                dialog.setVisible(true);

                Object value = pane.getValue();
                if (value == null && !(value instanceof Integer)) {
                    validInput = true; 
                }
                else if ((int) value == JOptionPane.OK_OPTION) {

                    rowText = rowField.getText();
                    colText = colField.getText();
                    mineText = mineTextField.getText();

                    if (rowText.isEmpty() || colText.isEmpty() || mineText.isEmpty()) {
                        JOptionPane.showMessageDialog(null, allFieldsFilled);
                    } else {
                        int rows = Integer.parseInt(rowText);
                        int columns = Integer.parseInt(colText);
                        int mines = Integer.parseInt(mineText);

                        int maxMines = (rows * columns) / 5;
                        int minMines = 1;

                        int minRows = 5;
                        int minColumns = 5;

                        int maxRows = 25;
                        int maxColumns = 50;

                        if (rows < minRows || columns < minColumns) {
                            JOptionPane.showMessageDialog(null, minimumSize + minRows + "x" + minColumns + ".");
                        } else if (rows > maxRows || columns > maxColumns) {
                            JOptionPane.showMessageDialog(null, maximumSize + maxRows + "x" + maxColumns + ".");
                        } else if (mines < minMines) {
                            JOptionPane.showMessageDialog(null, minimumMines + minMines + ".");
                        } else if (mines > maxMines) {
                            JOptionPane.showMessageDialog(null,
                                    maximumMines + rows + "x" + columns + maximumMines2 + maxMines + ".");
                        } else {
                            if (columns == 6) {
                                resizeTopMenu(22, 40, 25);
                            } else if (columns == 5) {
                                resizeTopMenu(20, 35, 24);
                            } else if (columns == 4) {
                                timeCounter.refreshCounter(15, 26, 19);
                                mineCounter.refreshCounter(15, 26, 19);
                                restartBtn.setPreferredSize(new Dimension(17, 19));
                                this.iconManager.resizeRestartIcons(17, 19);
                            } else if (columns > 6) {
                                resizeTopMenu(30, 52, 32);
                            }
                            this.algorithmicDifficulty = -2;
                            mineField.setAlgorithmicDifficulty(-2);
                            difficultyMenu.setVisible(false);
                            levelGroup.clearSelection();
                            customlevel.setSelected(true);
                            currentlevel = customlevel;
                            customlevel.setGridRow(rows);
                            customlevel.setGridColumn(columns);
                            customlevel.setMineNumber(mines);
                            resetBoard(customlevel);
                            validInput = true; 
                        }
                    }
                } else {
                    validInput = true; 
                }
            }
        });

        gameFrame.setJMenuBar(gameBar);

        gamePanel = new JPanel();
        gamePanel.setLayout(new BoxLayout(gamePanel, BoxLayout.Y_AXIS));
        gamePanel.setBorder(BorderFactory.createEmptyBorder(13, 13, 13, 13));
        gameFrame.getContentPane().add(gamePanel);

        mineCounter = new MultiCounter(mineNumber, false);
        timeCounter = new MultiCounter(0, true);
        mineCounter.setCurrentVal(mineNumber);

        restartBtn = new RestartButton(this.iconManager.getPlainIcon(), this::restartGame);

        JPanel timeCounterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        timeCounterPanel.add(timeCounter);

        JPanel restartButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        restartButtonPanel.add(restartBtn);

        JPanel mineCounterPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        mineCounterPanel.add(mineCounter);

        JPanel upperBox = new JPanel(new BorderLayout());
        upperBox.add(mineCounterPanel, BorderLayout.WEST);
        upperBox.add(restartButtonPanel, BorderLayout.CENTER);
        upperBox.add(timeCounterPanel, BorderLayout.EAST);

        gamePanel.add(upperBox);
        Dimension boxDimension = new Dimension(1, 11);
        gamePanel.add(Box.createRigidArea(boxDimension));

        initializeBoardPanel();

        if ("sk".equals(currentLanguage)) {
            slovakItem.setSelected(true);
        } else {
            englishItem.setSelected(true);
        }
    }

    private void initializeBoardPanel() {
        gridRow = currentlevel.getGridRow();
        gridColumn = currentlevel.getGridColumn();
        mineNumber = currentlevel.getMineNumber();

        fieldPanel = new JPanel(new GridLayout(gridRow, gridColumn));
        mineField = new MineField(gridRow, gridColumn, mineNumber, algorithmicDifficulty);
        IntStream.range(0, gridRow)
                .forEach(row -> {
                    IntStream.range(0, gridColumn)
                            .forEach(col -> {
                                TileButton gameTile = new TileButton(this.iconManager.getTileIcon());
                                gameTile.addMouseListener(new MouseInputAdapter() {
                                    @Override
                                    public void mouseReleased(MouseEvent event) {
                                        if (event.getButton() == MouseEvent.BUTTON1) {
                                            leftClick = false;
                                            if (rightClick && gameTile.isEnabled() && gameTile.isSelected())
                                                gameTile.setSelected(false);

                                        } else if (event.getButton() == MouseEvent.BUTTON3)
                                            rightClick = false;
                                    }

                                    @Override
                                    public void mousePressed(MouseEvent event) {
                                        if (SwingUtilities.isLeftMouseButton(event)) {
                                            leftClick = true;
                                        } if (SwingUtilities.isRightMouseButton(event)) {
                                            rightClick = true;
                                        }

                                        if (leftClick && rightClick) {
                                            resetBorders();
                                            exploreNeighborsIfNeeded(gameTile);
                                        } else if (leftClick) {
                                            resetBorders();
                                            exploreIfNeeded(gameTile);
                                        } else if (rightClick) {
                                            resetBorders();
                                            handleRightClick(gameTile);
                                        }
                                        
                                    }
                                });
                                mineField.getTiles()[row][col] = gameTile;
                                fieldPanel.add(gameTile);

                            });
                });
        gamePanel.add(fieldPanel);
        gameFrame.pack();
        gameFrame.setVisible(true);
    }

    private void exploreNeighborsIfNeeded(TileButton gameTile) {
        if (mineField.isFieldFailed()) {
            return;
        }
        if (gameTile.isSelected() && !gameTile.isMine()) {
            mineField.exploreAdjacentTiles(gameTile);
            checkGameEnded();
        }
    }

    private void exploreIfNeeded(TileButton gameTile) {
        if (gameTile.isEnabled()) {
            exploreAndUpdateState(gameTile);
        }
        else if (mineField.isFieldFailed()) {
            return;
        }
        
        else {
            exploreNeighborsIfNeeded(gameTile);
        }
    }

    private void handleRightClick(TileButton gameTile) {
        if (!gameTile.isSelected()) {
            if (questionMarkEnabled) {
                handleQuestionMark(gameTile);
            } else {
                handleFlag(gameTile);
            }
        }
        else {
            updateMineCounter(mineField.flagAdjacentTiles(gameTile));
            

        }
    }

    private void exploreAndUpdateState(TileButton gameTile) {
        
        if (!firstClickPerformed) {
            firstTile = gameTile;
            handleFirstClick();
            gameTile.setEnabled(false);
            gameTile.setSelected(true);
            mineField.explore(gameTile);
            gameFrame.setCursor(Cursor.getDefaultCursor());
        } else {
            gameTile.setEnabled(false);
            gameTile.setSelected(true);
            mineField.explore(gameTile);
            checkGameEnded();
        }
    }

    private void handleFirstClick() {
        firstClickPerformed = true;
        timeCounter.getTimeCount().start();
        timeCounter.setCounterActive(true);
        mineCounter.setCurrentVal(mineNumber);
        gameFrame.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
    }

    private void checkGameEnded() {
        if (mineField.isFieldWon() || mineField.isFieldFailed()) {
            gameEnded(mineField.isFieldWon());
        }
    }

    private void handleQuestionMark(TileButton gameTile) {
        if (!gameTile.isFlag() && !gameTile.isQuestionMark()) {
            gameTile.setFlag(true);
            gameTile.setEnabled(false);
            gameTile.setDisabledIcon(mineField.getIconManager().getFlagIcon());
            updateMineCounter(-1);
            mineField.checkForExcessiveFlags(gameTile);
        } else if (gameTile.isFlag()) {
            gameTile.setFlag(false);
            gameTile.setEnabled(true);
            updateMineCounter(1);
            mineField.checkForExcessiveFlags(gameTile);
            mineField.questionMark(gameTile);
        } else if (gameTile.isQuestionMark()) {
            mineField.unQuestionMark(gameTile);
        }
    }

    private void handleFlag(TileButton gameTile) {
        if (!gameTile.isFlag()) {
            gameTile.setFlag(true);
            gameTile.setEnabled(false);
            gameTile.setDisabledIcon(mineField.getIconManager().getFlagIcon());
            updateMineCounter(-1);
            mineField.checkForExcessiveFlags(gameTile);
            if (mineCounter.getCurrentVal() == 0) {
                checkGameEnded();
            }
        } else {
            gameTile.setFlag(false);
            gameTile.setEnabled(true);
            updateMineCounter(1);
            mineField.checkForExcessiveFlags(gameTile);
        }
    }

    private void updateMineCounter(int value) {
        mineCounter.setCurrentVal(mineCounter.getCurrentVal() + value);
    }

    private void resetBoard(Level level) {

        currentlevel = level;
        gamePanel.remove(fieldPanel);

        initializeBoardPanel();

        if (level.getLabel() != customLabel) {
            algorithmicDifficulty = previousAlgorithmicDifficulty;
            difficultyMenu.setVisible(true);
            resizeTopMenu(30, 52, 32);
        }
        if (level.getLabel() == expertLabel) {
            if (algorithmicDifficulty == 0){
                algorithmicDifficulty = 1;
                easyItem.setSelected(false);
                mediumItem.setSelected(true);
                easyItem.setEnabled(false);
            }
            else {
                easyItem.setEnabled(false);
            }
        }
        else {
            easyItem.setEnabled(true);
        }

        if (algorithmicDifficulty == -1) {
            hintItem.setEnabled(false);
        }
        else {
            hintItem.setEnabled(true);
        }
        gamePanel.add(fieldPanel);
        mineCounter.setCurrentVal(mineNumber);
        revalidateStuff();
        firstClickPerformed = false;
        restartGame();
        // two times to refresh
        currentlevel = level;
        gamePanel.remove(fieldPanel);

        initializeBoardPanel();
        if (level.getLabel() != customLabel) {
            resizeTopMenu(30, 52, 32);
        }
        gamePanel.add(fieldPanel);
        mineCounter.setCurrentVal(mineNumber);
        revalidateStuff();
        firstClickPerformed = false;
        restartGame();
    }

    private void revalidateStuff() {
        gamePanel.revalidate();
        gameFrame.pack();
        gameFrame.revalidate();
    }

    private void resizeTopMenu(int fontSize, int width, int height) {
        timeCounter.refreshCounter(fontSize, width, height);
        mineCounter.refreshCounter(fontSize, width, height);
        restartBtn.setPreferredSize(new Dimension(height, height));
        this.iconManager.resizeRestartIcons(height, height);
    }

    private void initializeGameSettings() {
        gridRow = 8;
        gridColumn = 8;
        mineNumber = 10;
        firstClickPerformed = false;
        leftClick = false;
        rightClick = false;
        questionMarkEnabled = false;
        langPrefs = Preferences.userNodeForPackage(this.getClass());
        currentLanguage = langPrefs.get("language", "en");
    }

    /**
     * @throws IOException
     * @throws FontFormatException
     */
    private void launcher() throws FontFormatException, IOException {
        this.iconManager = new IconManager();
        iconManager.loadSpecialIcons(33, 23);
        initializeGameSettings();
        initializeGraphics();
        helper = new Helper();
        mineField.initializeTiles();
    }

    private void restartGame() {

        timeCounter.restart();
        mineCounter.setCurrentVal(mineNumber);
        mineField.setFieldFailed(false);
        mineField.setFieldWon(false);
        mineField.setExploredTileNumber(0);
        restartBtn.setIcon(this.iconManager.getPlainIcon());
        this.firstClickPerformed = false;
        firstTile = null;
        helper = new Helper();
        mineField.initializeTiles();
    }

    private void gameEnded(Boolean isFieldWon) {

 
        if (isFieldWon) {
            firstTile = null;
            helper = new Helper();
            timeCounter.getTimeCount().stop();
            timeCounter.setCounterActive(false);
            restartBtn.setIcon(this.iconManager.getWinIcon());
            mineField.revealField();
            mineField.disableField();
            checkHighScore();
        }

        else {
            restartBtn.setIcon(this.iconManager.getLoseIcon());
            Timer timer = new Timer(250, new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    Object[] options = { undoLastClick, startNewGame };
                    int response = JOptionPane.showOptionDialog(null,
                            clickedOnMine,
                            boom,
                            JOptionPane.DEFAULT_OPTION,
                            JOptionPane.WARNING_MESSAGE,
                            mineField.getIconManager().getMineIcon(),
                            options,
                            options[0]);

                    if (response == 0) {

                        mineField.undoLastMove();
                        if (timeCounter.getCurrentVal() != 999) {
                            timeCounter.setCurrentVal(timeCounter.getCurrentVal() + 10); 
                        }
                        restartBtn.setIcon(iconManager.getPlainIcon());
                    } else if (response == 1) {
                        
                        resetBoard(currentlevel);
                    } else {
                        firstTile = null;
                        helper = new Helper();
                       
                        restartBtn.setIcon(iconManager.getLoseIcon());
                        mineField.revealField();
                        mineField.disableField();
                        timeCounter.getTimeCount().stop();
                    }
                }
            });
            timer.setRepeats(false); 
            timer.start(); 
            leftClick = false;
            rightClick = false;
        }
    }

    private void switchLanguage(String language) {
        
        langPrefs.put("language", language);
        currentLanguage = language;
        refreshLanguage();
        refreshVariables();
        revalidateStuff();
    }

    private void refreshLanguage() {
        if ("en".equals(currentLanguage)) {

            UIManager.put("OptionPane.cancelButtonText", "Cancel");

            gameTitle = "Minesweeper";
            gameTab = "Game";
            newGameLabel = "New Game";

            beginnerLabel = "Beginner: 8x8 - 10 mines";
            intermediateLabel = "Intermediate: 16x16 - 40 mines";
            expertLabel = "Expert: 16x30 - 99 mines";
            customLabel = "Custom...";

            languageLabel = "Language";
            englishLang = "English";
            slovakLang = "Slovak";
            rulesLabel = "Rules";
            patternsLabel = "Patterns";
            aboutLabel = "About";
            helpLabel = "Help";

            exitLabel = "Exit";
            marksLabel = "Marks (?)";
            rowsLabel = "Rows:";
            columnsLabel = "Columns:";
            minesLabel = "Mines:";
            allFieldsFilled = "All fields must be filled.";
            minimumSize = "Minimum size of the board is ";
            maximumSize = "Maximum size of the board is ";
            minimumMines = "Minimum number of mines is ";
            maximumMines = "Maximum number of mines for the size of ";
            maximumMines2 = " is ";
            undoLastClick = "Undo last click";
            startNewGame = "Start new game";
            boom = "BOOM!";
            clickedOnMine = "You clicked on a mine. Do you want to continue or restart?";
            customHeadline = "Custom field:";

            authorInfo = "Author: Tomas Starovic";

            bestTimesLabel = "Best Times...";
            leaderboardLabel = "Leaderboard:";
            secondsLabel = "seconds";
            difficultyLabel = "Difficulty";
            easyAlgorithmicDifficulty = "Easy";
            mediumAlgorithmicDifficulty = "Medium";
            hardAlgorithmicDifficulty = "Hard";
            veryHardAlgorithmicDifficulty = "Extreme";
            classicAlgorithmicDifficulty = "Classic";

            hintLabel = "Hint";
            outOfContextHint = "You need to click on a tile first.";
            noHintAvailable = "No hint available.";

            controlsLabel = "Controls";
            resetScoresLabel = "Reset Scores";
            difficultyInformation = "Difficulty level will be automatically adjusted<br>based on the size of the board and number of mines.";



            rulesControlsAndStrategies.setEnglishLang();
            

        } else {
            
            UIManager.put("OptionPane.cancelButtonText", "Zrušiť");

            gameTitle = "Míny";
            gameTab = "Menu";
            newGameLabel = "Nová hra";

            beginnerLabel = "Začiatočník: 8x8 - 10 mín";
            intermediateLabel = "Pokročilý: 16x16 - 40 mín";
            expertLabel = "Expert: 16x30 - 99 mín";
            customLabel = "Vlastné...";

            languageLabel = "Jazyk";
            englishLang = "Angličtina";
            slovakLang = "Slovenčina";
            rulesLabel = "Pravidlá";
            patternsLabel = "Stratégie";
            aboutLabel = "Info";
            helpLabel = "Pomoc";

            exitLabel = "Zavrieť";
            marksLabel = "Otáznik (?)";
            rowsLabel = "Riadky:";
            columnsLabel = "Stĺpce:";
            minesLabel = "Míny:";
            allFieldsFilled = "Všetky polia musia byť vyplnené.";
            minimumSize = "Minimálna veľkosť hracieho poľa je ";
            maximumSize = "Maximálna veľkosť hracieho poľa je ";
            minimumMines = "Minimálny počet mín je ";
            maximumMines = "Maximálny počet mín pre danú veľkosť ";
            maximumMines2 = " je ";
            undoLastClick = "Ťah späť :)";
            startNewGame = "Nová hra";
            boom = "BUM!";
            clickedOnMine = "Klikli ste na mínu. Chcete pokračovať alebo začať novú hru?";
            customHeadline = "Vlastné pole:";
            authorInfo = "Autor: Tomáš Starovič";

            bestTimesLabel = "Najlepšie časy...";
            leaderboardLabel = "Najlepšie časy:";
            secondsLabel = "sekúnd";
            difficultyLabel = "Obtiažnosť";
            easyAlgorithmicDifficulty = "Ľahká";
            mediumAlgorithmicDifficulty = "Stredná";
            hardAlgorithmicDifficulty = "Ťažká";
            veryHardAlgorithmicDifficulty = "Extrémna";
            classicAlgorithmicDifficulty = "Klasická";
            hintLabel = "Nápoveda";
            outOfContextHint = "Najprv musíte kliknúť na políčko.";
            noHintAvailable = "Žiadna nápoveda nie je dostupná.";

            controlsLabel = "Ovládanie";
            resetScoresLabel = "Resetovať skóre";
            difficultyInformation = "Obtiažnosť bude automaticky prispôsobená<br>veľkosti hracej plochy a počtu mín.";

            rulesControlsAndStrategies.setSlovakLang();
        }

    }

    private void refreshVariables() {
        gameFrame.setTitle(gameTitle);
        englishItem.setText(englishLang);
        slovakItem.setText(slovakLang);
        beginnerlevel.setLabel(beginnerLabel);
        beginnerlevel.setText(beginnerLabel);
        intermediatelevel.setLabel(intermediateLabel);
        intermediatelevel.setText(intermediateLabel);
        expertlevel.setLabel(expertLabel);
        expertlevel.setText(expertLabel);
        customlevel.setLabel(customLabel);
        customlevel.setText(customLabel);
        exitItem.setText(exitLabel);
        questionMarkItem.setText(marksLabel);
        gameMenu.setText(gameTab);
        languageMenu.setText(languageLabel);
        helpMenu.setText(helpLabel);
        newGameItem.setText(newGameLabel);
        rulesItem.setText(rulesLabel);
        patternsItem.setText(patternsLabel);
        aboutItem.setText(aboutLabel);
        bestTimesItem.setText(bestTimesLabel);
        difficultyMenu.setText(difficultyLabel);
        easyItem.setText(easyAlgorithmicDifficulty);
        mediumItem.setText(mediumAlgorithmicDifficulty);
        hardItem.setText(hardAlgorithmicDifficulty);
        veryHardItem.setText(veryHardAlgorithmicDifficulty);
        classicItem.setText(classicAlgorithmicDifficulty);
        hintItem.setText(hintLabel);
        controlsItem.setText(controlsLabel);
        gameFrame.repaint();
    }

    public void initializelevels() {

        levelsMap.put("Beginner", "Začiatočník");
        levelsMap.put("Intermediate", "Pokročilý");
        levelsMap.put("Expert", "Expert");
    }

    public String getLeaderboardText() {
        String[] levels = { "Beginner", "Intermediate", "Expert" };
        StringBuilder leaderboardText = new StringBuilder();
        for (String level : levels) {
            Score score = leaderboard.loadScore(level);
            if ("sk".equals(currentLanguage)) {
                level = levelsMap.get(level);
            }
            String levelStr = String.format("%-15s", level + ":");
            String scoreAndPlayerStr = String.format("%3d %s    %-10s\n", score.getTime(), secondsLabel, score.getPlayerName());
            leaderboardText.append(levelStr).append(scoreAndPlayerStr);
        }
        return leaderboardText.toString();
    }

    public void resetScores() {
        String[] levels = { "Beginner", "Intermediate", "Expert" };
        for (String level : levels) {
            Score score = new Score("Anonymous", 999);

            leaderboard.saveScore(level, score);

            Preferences prefs = Preferences.userNodeForPackage(this.getClass());
            prefs.putInt(level + "Time", score.getTime());
            prefs.put(level + "Player", score.getPlayerName());
        }
    }

    public void checkHighScore() {
        String level = determinelevel();
        if (level == "Custom") {
            return;
        }
        Score currentScore = leaderboard.loadScore(level);
        if (timeCounter.getCurrentVal() < currentScore.getTime()) {
            leftClick = false;
            rightClick = false;

            JTextField textField = new JTextField(20);
            textField.setDocument(new PlainDocument() {
                @Override
                public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
                    if (textField.getText().length() + str.length() <= 20) {
                        super.insertString(offs, str, a);
                    }
                }
            });
            textField.setFont(new Font("Arial", Font.PLAIN, 18));
            String newHighScore;
            String scoreTitle;
            if (currentLanguage == "en") {
                newHighScore = "New high score! Please enter your name:";
                scoreTitle = "High Score!";
            }
            else {
                newHighScore = "Nový najrýchlejší čas! Prosím zadajte vaše meno:";
                scoreTitle = "Nový rekord!";
            }
            Object[] message = {
                newHighScore, textField
            };
            
            JOptionPane pane = new JOptionPane(message, JOptionPane.PLAIN_MESSAGE, JOptionPane.OK_CANCEL_OPTION, this.iconManager.getWinIcon(), null, null);
            
            JDialog dialog = pane.createDialog(scoreTitle);
            ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("images/app/minesweeper_icon.png"));
            dialog.setIconImage(icon.getImage());
            dialog.addWindowFocusListener(new WindowAdapter() {
                @Override
                public void windowGainedFocus(WindowEvent e) {
                    textField.requestFocusInWindow();
                }
            });
            dialog.setVisible(true);
            
            if (pane.getValue() != null) {

                int option = (int) pane.getValue();
                if (option == JOptionPane.OK_OPTION) {

                    String playerName = textField.getText();
                    if (playerName == null){
                        playerName = "Anonymous";}

                    Score newScore = new Score(playerName, timeCounter.getCurrentVal());
                    leaderboard.saveScore(level, newScore);

                    Preferences prefs = Preferences.userNodeForPackage(this.getClass());
                    prefs.putInt(level + "Time", newScore.getTime());
                    prefs.put(level + "Player", newScore.getPlayerName());
                    
                    displayLeaderboard();
                }
            }
        }
    }
    public String determinelevel() {
        if (currentlevel == beginnerlevel) {
            return "Beginner";
        } else if (currentlevel == intermediatelevel) {
            return "Intermediate";
        } else if (currentlevel == expertlevel) {
            return "Expert";
        } else {
            return "Custom";
        }
    }

    public void displayLeaderboard() {
        String leaderboardText = getLeaderboardText();

        JTextArea textArea = new JTextArea(leaderboardText);
        textArea.setFont(new Font("Consolas", Font.BOLD, 12));
        textArea.setBackground(Color.LIGHT_GRAY);
        textArea.setEditable(false);

        JButton resetButton = new JButton(resetScoresLabel);
        resetButton.addActionListener((e) -> {
            resetScores();

            textArea.setText(getLeaderboardText());
        });


        JButton okButton = new JButton("OK");
        okButton.addActionListener((e) -> {

            ((JDialog) okButton.getTopLevelAncestor()).dispose();
        });

        JOptionPane.showOptionDialog(null, new JScrollPane(textArea), leaderboardLabel, JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE, this.iconManager.getWinIcon(),
                new Object[] { resetButton, okButton }, null);
    }

    public void setBoardAlgorithmicDifficulty(int difficulty) {
        this.previousAlgorithmicDifficulty = difficulty;
        this.algorithmicDifficulty = difficulty;
        mineField.setAlgorithmicDifficulty(difficulty);
    }

    private void resetBorders() {
        mineField.resetBorders();
    }

    public static void main(String[] args) throws FontFormatException, IOException {
        new Minesweeper().launcher();
    }
}
