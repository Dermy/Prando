package prando.gui;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JMenuBar;
import javax.swing.JTextField;
import prando.Prando;

public class MenuBar
{
    private Prando p;

    private JButton open;
    private JTextField searchBar;

    public JButton getOpen() { return open; }
    public JTextField getSearchBar() { return searchBar; };

    public MenuBar(Prando p)
    {
        this.p = p;
    }//constructor

    public JMenuBar build()
    {
        JMenuBar menuBar = new JMenuBar();

        open = new JButton("Open New File/Directory");
        searchBar = new JTextField();

        open.setFont(p.getSettings().getFont());
        searchBar.setFont(p.getSettings().getFont());

        menuBar.add(open);
        menuBar.add(Box.createHorizontalStrut(480));
        menuBar.add(searchBar);

        return menuBar;
    }//build
}//MenuBar