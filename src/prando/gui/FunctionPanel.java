package prando.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import prando.Prando;

public class FunctionPanel
{
    private Prando p;

    private JCheckBox patch;
    private JCheckBox rename;
    private JCheckBox organize;
    private JButton start;

    public JCheckBox getPatch() { return patch; }
    public JCheckBox getRename() { return rename; }
    public JCheckBox getOrganize() { return organize; }
    public JButton getStart() { return start; }

    public FunctionPanel(Prando p) { this.p = p; }

    public JPanel build()
    {
        JPanel jpanel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        patch = new JCheckBox("Patch");
        rename = new JCheckBox("Rename");
        organize = new JCheckBox("Organize");
        start = new JButton("Start");
        patch.setFocusable(false);
        rename.setFocusable(false);
        organize.setFocusable(false);
        start.setFocusable(false);
        patch.setFont(p.getSettings().getFont());
        rename.setFont(p.getSettings().getFont());
        organize.setFont(p.getSettings().getFont());
        start.setFont(p.getSettings().getFont());

        patch.setEnabled(!p.getSettings().getEnableSafeMode());
        rename.setEnabled(!p.getSettings().getEnableSafeMode());
        organize.setEnabled(!p.getSettings().getEnableSafeMode());
        start.setEnabled(!p.getSettings().getEnableSafeMode());

        c.anchor = GridBagConstraints.WEST;
        c.insets.top = 2;
        c.insets.bottom = 3;

        jpanel.add(patch, c);

        c.gridy = 1;
        jpanel.add(rename, c);

        c.gridy = 2;
        jpanel.add(organize, c);

        c.insets.bottom = 0;
        c.fill = GridBagConstraints.BOTH;
        c.anchor = GridBagConstraints.CENTER;
        c.ipady = 89;
        c.gridy = 3;
        jpanel.add(start, c);

        return jpanel;
    }//build
}//FunctionPanel