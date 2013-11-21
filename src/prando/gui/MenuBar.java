package prando.gui;

import java.awt.Dimension;
import javax.swing.Box;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTextField;
import prando.Prando;

public class MenuBar
{
    private Prando p;

    private JMenuItem open;
    private JTextField searchBar;

    public JMenuItem getOpen() { return open; }
    public JTextField getSearchBar() { return searchBar; };

    public MenuBar(Prando p)
    {
        this.p = p;
    }//constructor

    public JMenuBar build()
    {
        JMenuBar menuBar = new JMenuBar();
        menuBar.setPreferredSize(new Dimension(760, 33));

        open = new JMenuItem("Open New File/Directory");
        searchBar = new JTextField();

        open.setFont(p.getSettings().getFont());
        searchBar.setFont(p.getSettings().getFont());

        menuBar.add(open);
        menuBar.add(Box.createHorizontalStrut(289));
        menuBar.add(searchBar);

        return menuBar;
    }//build
}//MenuBar