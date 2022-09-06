package gui.frames;

import collectionitems.MusicBand;
import connection.MusicBandConnection;
import gui.collectiontable.MultiLineTableCellRenderer;
import gui.collectiontable.MusicBandTableModel;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.List;

public class CollectionFrame extends JFrame {
    private JPanel mainPanel = new JPanel();
    private JPanel topPanel = new JPanel();
    private JPanel middlePanel = new JPanel();
    private JPanel bottomPanel = new JPanel();
    private JPanel bottomMainPanel = new JPanel();
    private JButton helpButton = new JButton();
    private JComboBox<String> languageComboBox = new JComboBox<>();
    private JLabel userLabel = new JLabel();
    private JTable collectionTable;
    private JButton executeScriptButton = new JButton();
    private JButton infoButton = new JButton();
    private JButton insertAtButton = new JButton();
    private JButton addIfMinButton = new JButton();
    private JButton countLesserButton = new JButton();
    private JButton addIfMaxButton = new JButton();
    private JButton clearButton = new JButton();
    private JButton addButton = new JButton();
    private JButton deleteButton = new JButton();
    private JButton editButton = new JButton();

    private final MusicBandConnection connection;
    private List<MusicBand> bands;
    private MusicBandTableModel tableModel;

    public CollectionFrame(String username, MusicBandConnection connection) throws IOException, ClassNotFoundException, InterruptedException {
        Color mainColor = new Color(88, 119, 235);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) screenSize.getWidth();
        int height = (int) screenSize.getHeight();
        this.setBounds(width/2 - 600, height/2 - 400, 1200, 800);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(false);

        //main holder
        add(mainPanel);
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(mainColor);

        //header
        topPanel.setPreferredSize(new Dimension(1200, 90));
        topPanel.setBackground(mainColor);
        mainPanel.add(topPanel, BorderLayout.NORTH);
        topPanel.setLayout(new GridBagLayout());
        GridBagConstraints headerConstraints = new GridBagConstraints();
        headerConstraints.weightx = 1180;
        headerConstraints.weighty = 96;
        headerConstraints.gridx = 0;
        headerConstraints.gridy = 0;
        headerConstraints.gridheight = 3;
        headerConstraints.gridwidth = 1;
        topPanel.add(new JLabel(), headerConstraints);
        headerConstraints.gridx = 1;
        headerConstraints.gridy = 0;
        headerConstraints.gridheight = 1;
        headerConstraints.gridwidth = 1;
        topPanel.add(new JLabel(), headerConstraints);
        helpButton.setText("Help");
        helpButton.setPreferredSize(new Dimension(273, 30));
        headerConstraints.gridx = 1;
        headerConstraints.gridy = 1;
        headerConstraints.gridheight = 1;
        headerConstraints.gridwidth = 1;
        topPanel.add(helpButton, headerConstraints);
        headerConstraints.gridx = 1;
        headerConstraints.gridy = 2;
        topPanel.add(new JLabel(), headerConstraints);
        headerConstraints.gridx = 2;
        headerConstraints.gridy = 0;
        topPanel.add(new JLabel(), headerConstraints);
        headerConstraints.gridx = 2;
        headerConstraints.gridy = 1;
        languageComboBox.addItem("English(Ireland)");
        languageComboBox.addItem("Русский");
        languageComboBox.addItem("Lietuvių");
        languageComboBox.addItem("Norsk");
        topPanel.add(languageComboBox, headerConstraints);
        languageComboBox.setPreferredSize(new Dimension(273, 30));
        headerConstraints.gridx = 2;
        headerConstraints.gridy = 2;
        topPanel.add(new JLabel(), headerConstraints);
        headerConstraints.gridx = 3;
        headerConstraints.gridy = 0;
        headerConstraints.gridheight = 3;
        headerConstraints.gridwidth = 1;
        userLabel.setText(username);
        ImageIcon userIcon = new ImageIcon(getClass().getResource("user.png"));
        userLabel.setIcon(userIcon);
        userLabel.setHorizontalTextPosition(JLabel.LEFT);
        userLabel.setFont(new Font("OPPO Sans", Font.ITALIC, 35));
        userLabel.setForeground(Color.WHITE);
        topPanel.add(userLabel, headerConstraints);
        topPanel.revalidate();

        middlePanel.setPreferredSize(new Dimension(1180, 450));
        JPanel leftMargin = new JPanel();
        leftMargin.setBackground(mainColor);
        leftMargin.setPreferredSize(new Dimension(10,450));
        JPanel rightMargin = new JPanel();
        rightMargin.setBackground(mainColor);
        leftMargin.setPreferredSize(new Dimension(10,450));
        mainPanel.add(leftMargin, BorderLayout.EAST);
        mainPanel.add(rightMargin, BorderLayout.WEST);
        mainPanel.add(middlePanel);

        bottomPanel.setPreferredSize(new Dimension(1200, 260));
        bottomPanel.setBackground(mainColor);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        bottomPanel.setLayout(new BorderLayout());
        JPanel leftMarginBottom = new JPanel();
        leftMarginBottom.setBackground(mainColor);
        leftMarginBottom.setPreferredSize(new Dimension(10, 260));
        bottomPanel.add(leftMarginBottom, BorderLayout.WEST);
        JPanel topMarginBottom = new JPanel();
        topMarginBottom.setBackground(mainColor);
        bottomPanel.add(topMarginBottom, BorderLayout.NORTH);
        bottomPanel.add(bottomMainPanel);
        //table 10 rows 18 cols
        //collection table 8 rows 12 cols
        bottomMainPanel.setLayout(new GridBagLayout());
        bottomMainPanel.setBackground(mainColor);
        GridBagConstraints bottomConstraints = new GridBagConstraints();
        JPanel bottomButtonsPanel = new JPanel();
        bottomButtonsPanel.setPreferredSize(new Dimension(370, 96));
        bottomButtonsPanel.setBackground(mainColor);
        bottomConstraints.gridx = 12;
        bottomConstraints.gridy = 0;
        bottomConstraints.gridheight = 4;
        bottomConstraints.gridwidth = 6;
        bottomMainPanel.add(bottomButtonsPanel, bottomConstraints);

        Dimension buttonsSize = new Dimension(110, 27);
        Font buttonsFont = new Font("Arial", Font.PLAIN, 9);
        executeScriptButton.setText("ExecuteScript");
        executeScriptButton.setFont(buttonsFont);
        executeScriptButton.setPreferredSize(buttonsSize);
        bottomButtonsPanel.add(executeScriptButton);
        infoButton.setText("Info");
        infoButton.setPreferredSize(buttonsSize);
        infoButton.setFont(buttonsFont);
        bottomButtonsPanel.add(infoButton);
        insertAtButton.setText("InsertAt");
        insertAtButton.setPreferredSize(buttonsSize);
        insertAtButton.setFont(buttonsFont);
        bottomButtonsPanel.add(insertAtButton);
        addIfMinButton.setText("AddIfMin");
        addIfMinButton.setPreferredSize(buttonsSize);
        addIfMinButton.setFont(buttonsFont);
        bottomButtonsPanel.add(addIfMinButton);
        clearButton.setText("Clear");
        clearButton.setPreferredSize(buttonsSize);
        clearButton.setFont(buttonsFont);
        bottomButtonsPanel.add(clearButton);
        countLesserButton.setText("CountLesserGenre");
        countLesserButton.setPreferredSize(buttonsSize);
        countLesserButton.setFont(buttonsFont);
        bottomButtonsPanel.add(countLesserButton);
        addIfMaxButton.setText("AddIfMax");
        addIfMaxButton.setPreferredSize(buttonsSize);
        addIfMaxButton.setFont(buttonsFont);
        bottomButtonsPanel.add(addIfMaxButton);

        bottomConstraints.gridx = 0;
        bottomConstraints.gridy = 9;
        bottomConstraints.gridheight = 1;
        bottomConstraints.gridwidth = 5;
        JPanel emptyBottomPanel = new JPanel();
        emptyBottomPanel.setBackground(mainColor);
        emptyBottomPanel.setPreferredSize(new Dimension(500, 50));
        bottomMainPanel.add(emptyBottomPanel, bottomConstraints);

        bottomConstraints.gridx = 6;
        bottomConstraints.gridy = 9;
        bottomConstraints.gridheight = 1;
        bottomConstraints.gridwidth = 5;
        JPanel underTableButtonsPanel = new JPanel();
        underTableButtonsPanel.setBackground(mainColor);
        bottomMainPanel.add(underTableButtonsPanel, bottomConstraints);

        addButton.setText("Add");
        deleteButton.setText("Delete");
        editButton.setText("Edit");
        underTableButtonsPanel.add(addButton);
        underTableButtonsPanel.add(deleteButton);
        underTableButtonsPanel.add(editButton);

        //functionality
        this.connection = connection;
        tableModel = new MusicBandTableModel(connection.sendCommand("load").musicBandList);
        collectionTable = new JTable(tableModel);
        MultiLineTableCellRenderer renderer = new MultiLineTableCellRenderer();
        collectionTable.setDefaultRenderer(String[].class, renderer);
        collectionTable.setRowHeight(75);

        bottomConstraints.gridx = 0;
        bottomConstraints.gridy = 0;
        bottomConstraints.gridheight = 8;
        bottomConstraints.gridwidth = 12;
        JScrollPane tableScrollablePane = new JScrollPane(collectionTable);
        tableScrollablePane.setPreferredSize(new Dimension(795, 180));
        bottomMainPanel.add(tableScrollablePane, bottomConstraints);

        helpButton.addActionListener( e -> JOptionPane.showMessageDialog(null, "HelpText"));
        infoButton.addActionListener( e -> {
            try {
                JOptionPane.showMessageDialog(null, connection.sendCommand("info").response);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "ConnectionLost");
            } catch (ClassNotFoundException ex) {
                ex.printStackTrace();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        });
    }
}
