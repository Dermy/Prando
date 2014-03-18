package prando.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseListener;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import prando.Prando;
import prando.Settings.ColorScheme;
import prando.stfs.STFSPackage;

public class InfoPanel
{
    private Prando p;

    private JTextField[] infoTextFields;
    private JLabel[] thumbnailLabels;
    private JTextArea licensesTextArea;

    public JLabel[] getThumbnailLabels() { return thumbnailLabels; };

    public InfoPanel(Prando p)
    {
        this.p = p;
    }//constructor

    public JPanel build()
    {
        GridBagConstraints c = new GridBagConstraints();
        JPanel infoPanel = new JPanel(new GridBagLayout());

        infoPanel.setPreferredSize(new Dimension(440, 250));

        initLicensesTextArea();
        initInfoFields();

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridwidth = 4;

        c.gridy = 0;
        infoPanel.add(infoTextFields[0], c);

        c.gridy = 1;
        infoPanel.add(infoTextFields[1], c);

        c.gridy = 2;
        infoPanel.add(infoTextFields[2], c);

        c.gridy = 3;
        infoPanel.add(infoTextFields[3], c);

        c.gridwidth = 1;
        c.gridx = 0;
        c.gridy = 4;
        infoPanel.add(infoTextFields[4], c);
        c.gridx = 1;
        infoPanel.add(infoTextFields[5], c);

        c.gridx = 0;
        c.gridy = 5;
        infoPanel.add(infoTextFields[6], c);
        c.gridx = 1;
        infoPanel.add(infoTextFields[7], c);

        c.gridwidth = 2;
        c.gridx = 0;
        c.gridy = 6;
        infoPanel.add(infoTextFields[8], c);
        c.gridy = 7;
        infoPanel.add(infoTextFields[9], c);
        c.gridy = 8;
        infoPanel.add(infoTextFields[10], c);

        c.gridx = 2;
        c.gridy = 4;
        infoPanel.add(infoTextFields[11], c);

        c.gridwidth = 1;
        c.fill = GridBagConstraints.NONE;
        c.gridx = 2;
        c.gridy = 5;
        c.gridheight = 1;
        JLabel content = new JLabel(" Content Icon");
        content.setFont(p.getSettings().getFont());
        infoPanel.add(content, c);
        c.gridy = 6;
        c.gridheight = 3;
        infoPanel.add(thumbnailLabels[0], c);

        c.gridx = 3;
        c.gridy = 5;
        c.gridheight = 1;
        JLabel title = new JLabel("Title Icon");
        title.setFont(p.getSettings().getFont());
        infoPanel.add(title, c);
        c.gridy = 6;
        c.gridheight = 3;
        infoPanel.add(thumbnailLabels[1], c);

        JPanel jpanel = new JPanel(new GridBagLayout());
        c = new GridBagConstraints();

        c.insets.top = 5;
        jpanel.add(licensesTextArea, c);

        c.gridx = 1;
        c.insets.top = 0;
        jpanel.add(infoPanel, c);

        return jpanel;
    }//build

    private void initLicensesTextArea()
    {
        String ls = System.getProperty("line.separator");

        licensesTextArea = new JTextArea(
                " 01: License--------- Bits---- Flags---" + ls +
                " 02: License--------- Bits---- Flags---" + ls +
                " 03: License--------- Bits---- Flags---" + ls +
                " 04: License--------- Bits---- Flags---" + ls +
                " 05: License--------- Bits---- Flags---" + ls +
                " 06: License--------- Bits---- Flags---" + ls +
                " 07: License--------- Bits---- Flags---" + ls +
                " 08: License--------- Bits---- Flags---" + ls +
                " 09: License--------- Bits---- Flags---" + ls +
                " 10: License--------- Bits---- Flags---" + ls +
                " 11: License--------- Bits---- Flags---" + ls +
                " 12: License--------- Bits---- Flags---" + ls +
                " 13: License--------- Bits---- Flags---" + ls +
                " 14: License--------- Bits---- Flags---" + ls +
                " 15: License--------- Bits---- Flags---" + ls +
                " 16: License--------- Bits---- Flags---");

        licensesTextArea.setEditable(false);
        licensesTextArea.setFont(p.getSettings().getFont());
        licensesTextArea.setPreferredSize(new Dimension(325, 250));

        if(p.getSettings().getColorScheme() == ColorScheme.GRAYGREEN ||
                p.getSettings().getColorScheme() == ColorScheme.GRAYWHITE)
        {
            licensesTextArea.setBorder(BorderFactory.createLineBorder(
                    Color.DARK_GRAY));
        }//if

        else if(p.getSettings().getColorScheme() == ColorScheme.BLACKGREEN ||
                p.getSettings().getColorScheme() == ColorScheme.BLACKWHITE)
        {
            licensesTextArea.setBorder(BorderFactory.createLineBorder(
                    Color.BLACK));
        }//else if
    }//initLicensesTextArea

    private void initInfoFields()
    {
        infoTextFields = new JTextField[12];
        thumbnailLabels = new JLabel[2];

        for(int i = 0; i <= 11; i++)
        {
            if(i >= 0 && i <= 3)
            {
                infoTextFields[i] = new JTextField(52);
            }//if

            else if(i >= 4 && i <= 7)
            {
                infoTextFields[i] = new JTextField(12);
            }//else if

            else if(i >= 8 && i <= 11)
            {
                infoTextFields[i] = new JTextField(26);
            }//else if

            infoTextFields[i].setEditable(false);
            infoTextFields[i].setFont(p.getSettings().getFont());
            infoTextFields[i].setHorizontalAlignment(JTextField.CENTER);
        }//for

        thumbnailLabels[0] = new JLabel();
        thumbnailLabels[1] = new JLabel();

        if(p.getSettings().getColorScheme() == ColorScheme.GRAYGREEN ||
                p.getSettings().getColorScheme() == ColorScheme.BLACKGREEN)
        {
            thumbnailLabels[0].setBorder(BorderFactory.createLineBorder(
                    Color.GREEN));
            thumbnailLabels[1].setBorder(BorderFactory.createLineBorder(
                    Color.GREEN));
        }//if

        else if(p.getSettings().getColorScheme() == ColorScheme.GRAYWHITE ||
                p.getSettings().getColorScheme() == ColorScheme.BLACKWHITE ||
                p.getSettings().getColorScheme() == ColorScheme.NONE)
        {
            thumbnailLabels[0].setBorder(BorderFactory.createLineBorder(
                    Color.WHITE));
            thumbnailLabels[1].setBorder(BorderFactory.createLineBorder(
                    Color.WHITE));
        }//else if

        infoTextFields[0].setText("Display Name");
        infoTextFields[1].setText("Title Name");
        infoTextFields[2].setText("Location on Computer");
        infoTextFields[3].setText("Original Filename");
        infoTextFields[4].setText("Title ID");
        infoTextFields[5].setText("Content ID");
        infoTextFields[6].setText("Media ID");
        infoTextFields[7].setText("TU Number");
        infoTextFields[8].setText("Content Type");
        infoTextFields[9].setText("State");
        infoTextFields[10].setText("File Size");
        infoTextFields[11].setText("STFS Type");

        thumbnailLabels[0].setIcon(ViewableSTFSPackage.BLANKICON);
        thumbnailLabels[1].setIcon(ViewableSTFSPackage.BLANKICON);
    }//initInfoFields

    public void updateLicensesTextArea(ViewableSTFSPackage pkg)
    {
        licensesTextArea.setText(pkg.getLicenses());
    }//updateLicensesTextArea

    public void updateInfoTextFields(ViewableSTFSPackage pkg)
    {
        STFSPackage temp = pkg.getBasePackage();

        MouseListener contentPopupListener = thumbnailLabels[0].
                getMouseListeners()[0];
        MouseListener titlePopupListener = thumbnailLabels[1].
                getMouseListeners()[0];
        thumbnailLabels[0].removeMouseListener(contentPopupListener);
        thumbnailLabels[1].removeMouseListener(titlePopupListener);

        infoTextFields[0].setText(temp.getDisplayName());
        infoTextFields[1].setText(temp.getTitleName());

        infoTextFields[2].setText(pkg.getLocation());
        infoTextFields[2].setCaretPosition(0);

        infoTextFields[3].setText(temp.getOriginalFilename());
        infoTextFields[4].setText(temp.getTitleID());
        infoTextFields[5].setText(temp.getContentID());
        infoTextFields[6].setText(temp.getMediaID());
        infoTextFields[7].setText("Update #" + temp.getUpdateNumber());
        infoTextFields[8].setText(temp.getContentType().toString());
        infoTextFields[9].setText(temp.getState().toString());
        infoTextFields[10].setText(pkg.getFileSize());
        infoTextFields[11].setText(temp.getMagic().toString());

        thumbnailLabels[0].setIcon(pkg.getContentThumbnail());
        thumbnailLabels[1].setIcon(pkg.getTitleThumbnail());

        thumbnailLabels[0].addMouseListener(contentPopupListener);
        thumbnailLabels[1].addMouseListener(titlePopupListener);
    }//updateInfoPanel
}//InfoPanel