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
    private JButton go;

    public JCheckBox getPatch() { return patch; }
    public JCheckBox getRename() { return rename; }
    public JCheckBox getOrganize() { return organize; }
    public JButton getGo() { return go; }

    public FunctionPanel(Prando p) { this.p = p; }

    public JPanel build()
    {
        JPanel jpanel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        patch = new JCheckBox("Patch");
        rename = new JCheckBox("Rename");
        organize = new JCheckBox("Organize");
        go = new JButton("Go");
        patch.setFocusable(false);
        rename.setFocusable(false);
        organize.setFocusable(false);
        go.setFocusable(false);
        patch.setFont(p.getSettings().getFont());
        rename.setFont(p.getSettings().getFont());
        organize.setFont(p.getSettings().getFont());
        go.setFont(p.getSettings().getFont());

        go.setEnabled(!p.getSettings().getSafeMode());

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
        jpanel.add(go, c);

        return jpanel;
    }//build
}//FunctionPanel