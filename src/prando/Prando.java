package prando;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import prando.gui.CustomJTable;
import prando.gui.FunctionPanel;
import prando.gui.InfoPanel;
import prando.gui.InputOutput;
import prando.gui.MenuBar;
import prando.gui.Table;
import prando.gui.ViewableSTFSPackage;
import prando.listeners.OpenActionListener;
import prando.listeners.PopupMenuListener;
import prando.listeners.SaveThumbnailActionListener;
import prando.listeners.SearchBarDocumentListener;
import prando.listeners.StartButtonActionListener;
import prando.listeners.TableListSelectionListener;
import prando.stfs.STFSPackage;
import prando.util.Utilities;

public class Prando
{
    public static final String VERSION = "2.1.0";

    private String arg;

    private Settings settings;
    private InputOutput io;

    private ArrayList<ViewableSTFSPackage> packageList;
    private Path selectedDirectory;

    private MenuBar menuBar;
    private Table table;
    private FunctionPanel functionPanel;
    private InfoPanel infoPanel;

    private JFrame jframe;
    private JMenuBar jMenuBar;
    private CustomJTable customJTable;
    private JPanel topPanel;
    private JPanel bottomPanel;

    public InputOutput getInputOutput() { return io; }
    public Settings getSettings() { return settings; }
    public ArrayList<ViewableSTFSPackage> getPackageList() {
        return packageList; }
    public Path getSelectedDirectory() { return selectedDirectory; }
    public JFrame getJFrame() { return jframe; }
    public MenuBar getMenuBar() { return menuBar; }
    public Table getTable() { return table; }
    public FunctionPanel getFunctionPanel() { return functionPanel; }
    public InfoPanel getInfoPanel() { return infoPanel; }
    public CustomJTable getCustomJTable() { return customJTable; }

    public static void main(final String[] args)
    {
        SwingUtilities.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                new Prando(args).createAndShowGUI();
            }//run
        });//invokeLater
    }//main

    public Prando(String[] args)
    {
        try
        {
            arg = args[0];
        }//try

        catch(ArrayIndexOutOfBoundsException ex){}

        settings = new Settings();
        io = new InputOutput();
        packageList = new ArrayList<>();

        settings.readAllSettings();
        changeColorScheme();
        changeLookAndFeel();
    }//constructor

    public void createAndShowGUI()
    {
        jframe = new JFrame();
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.setResizable(false);
        jframe.setLayout(new GridBagLayout());

        chooseDirectory();

        jframe.setTitle("Prando v" + Prando.VERSION + " :: " +
                packageList.size() + " File(s) Found :: Developed by Dermy");

        buildComponents();
        addListeners();
        addComponents();

        jframe.setLocationRelativeTo(null);

        if(settings.getSelectFirstItemAfterLoad())
        {
            customJTable.requestFocus();
        }//if

        else
        {
            jframe.getComponent(0).requestFocus();
        }//else

        jframe.setVisible(true);
    }//createAndShowGUI

    private void changeColorScheme()
    {
        UIManager.put("nimbusBase", Color.DARK_GRAY);
        UIManager.put("nimbusBlueGrey", Color.DARK_GRAY);
        UIManager.put("nimbusSelectionBackground", Color.DARK_GRAY);
        UIManager.put("nimbusFocus", Color.GREEN);
        UIManager.put("text", Color.WHITE);
        UIManager.put("nimbusSelectedText", Color.GREEN);
        UIManager.put("control", Color.DARK_GRAY);
        UIManager.put("List[Selected].textForeground", Color.GREEN);
        UIManager.put("nimbusLightBackground", Color.DARK_GRAY);
        UIManager.put("Table.background", Color.DARK_GRAY);
        UIManager.put("Table.alternateRowColor", Color.DARK_GRAY);
    }//changeColorScheme

    private void changeLookAndFeel()
    {
        try
        {
            UIManager.setLookAndFeel(
                    "javax.swing.plaf.nimbus.NimbusLookAndFeel");
        }//try

        catch(ClassNotFoundException | InstantiationException
                | IllegalAccessException |
                UnsupportedLookAndFeelException ex)
        {
            Utilities.quit(this, "Nimbus Look and Feel not found. Exiting.");
        }//catch

        UIManager.getLookAndFeelDefaults().put("Table[Enabled+Selected]."
                + "textForeground", Color.GREEN);
        UIManager.getLookAndFeelDefaults().put("ToolTip[Enabled]."
                + "backgroundPainter", Color.DARK_GRAY);
    }//changeLookAndFeel

    private void chooseDirectory()
    {
        if(arg == null)
        {
            selectedDirectory = io.selectFileOrDirectory(this);
        }//if

        else
        {
            selectedDirectory = Paths.get(arg);
        }//else

        ArrayList<STFSPackage> tempPackageList = io.makePackageList(this);

        if(tempPackageList.isEmpty())
        {
            Utilities.quit(this, "No LIVE or PIRS files were found."
                    + " Exiting.");
        }//if

         packageList = makeViewablePackageList(tempPackageList);
    }//chooseDirectory

    private ArrayList<ViewableSTFSPackage> makeViewablePackageList(
            ArrayList<STFSPackage> STFSPackageList)
    {
        ArrayList<ViewableSTFSPackage> VSTFSPackageList = new ArrayList<>();

        for(STFSPackage pkg : STFSPackageList)
        {
            VSTFSPackageList.add(new ViewableSTFSPackage(pkg,
                    (double) settings.getKilobyteSize().getByteCount(),
                    selectedDirectory));
        }//for

        VSTFSPackageList.trimToSize();
        return VSTFSPackageList;
    }//makeViewablePackageList

    private void buildComponents()
    {
        menuBar = new MenuBar(this);
        jMenuBar = menuBar.build();

        table = new Table(this);
        customJTable = table.build();

        functionPanel = new FunctionPanel(this);

        buildTopPanel();

        infoPanel = new InfoPanel(this);
        bottomPanel = infoPanel.build();
    }//buildComponents

    private void buildTopPanel()
    {
        GridBagConstraints c = new GridBagConstraints();
        topPanel = new JPanel(new GridBagLayout());

        JScrollPane scrollPane = new JScrollPane(customJTable);
        scrollPane.setHorizontalScrollBarPolicy(
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        topPanel.add(scrollPane, c);
    }//buildTopPanel

    private void addListeners()
    {
        menuBar.getOpen().addActionListener(new OpenActionListener(this));
        menuBar.getSearchBar().getDocument().addDocumentListener(new
                SearchBarDocumentListener(this));

        customJTable.getSelectionModel().addListSelectionListener(
                new TableListSelectionListener(this));

        functionPanel.getStart().addActionListener(
                new StartButtonActionListener(this));

        makeAndAddThumbnailListeners(infoPanel.getThumbnailLabels());

        if(settings.getSelectFirstItemAfterLoad())
        {
            customJTable.changeSelection(0, 0, false, false);
        }//if
    }//addListeners

    private void makeAndAddThumbnailListeners(JLabel[] thumbnails)
    {
        JPopupMenu popup;
        JMenuItem menuItem;

        popup = new JPopupMenu();
        menuItem = new JMenuItem("Save Thumbnail");
        menuItem.addActionListener(new SaveThumbnailActionListener(this,
                "ContentThumbnail.png"));
        popup.add(menuItem);
        thumbnails[0].addMouseListener(new PopupMenuListener(this, popup));

        popup = new JPopupMenu();
        menuItem = new JMenuItem("Save Thumbnail");
        menuItem.addActionListener(new SaveThumbnailActionListener(this,
                "TitleThumbnail.png"));
        popup.add(menuItem);
        thumbnails[1].addMouseListener(new PopupMenuListener(this, popup));
    }//makeAndAddThumbnailListeners

    private void addComponents()
    {
        GridBagConstraints c = new GridBagConstraints();

        c.fill = GridBagConstraints.HORIZONTAL;
        jframe.add(jMenuBar, c);

        c.gridy = 1;
        c.fill = GridBagConstraints.NONE;
        jframe.add(topPanel, c);

        c.gridy = 2;
        jframe.add(bottomPanel, c);

        jframe.pack();
    }//addComponents
}//Prando