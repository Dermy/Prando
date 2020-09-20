package prando.util;

import java.awt.GraphicsEnvironment;
import java.io.IOException;
import java.io.RandomAccessFile;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import prando.Prando;
import prando.stfs.ModifySTFS;

public class Utilities
{
    public static void readHex(String path, byte[] b, int fileStart,
            int readLength)
    {
        readHex(path, b, fileStart, readLength, 0);
    }//readHex

    public static void readHex(String path, byte[] b, int fileStart,
            int readLength, int startingIndex)
    {
        try(RandomAccessFile raf = new RandomAccessFile(path, "r"))
        {
            raf.seek(fileStart);
            raf.readFully(b, startingIndex, readLength);
        }//try

        catch(IOException ex){}//catch
    }//readHex

    public static void quit(Prando p, String details)
    {
        JLabel label = new JLabel(details);

        try
        {
            label.setFont(p.getSettings().getFont());
        }//try

        catch(NullPointerException ex)
        {
            JOptionPane.showMessageDialog(new JFrame(), label, "Prando v" +
                Prando.VERSION, JOptionPane.PLAIN_MESSAGE);
            System.exit(0);
        }//catch

        JOptionPane.showMessageDialog(p.getJFrame(), label, "Prando v" +
                Prando.VERSION, JOptionPane.PLAIN_MESSAGE);
        System.exit(0);
    }//quit

    public static void operationsComplete(Prando p)
    {
        if(ModifySTFS.getErrors().isEmpty())
        {
            Utilities.quit(p, "Operation(s) completed successfully!");
        }//if

        else
        {
            String[] buttons = {"OK", "Show Errors"};
            JLabel label = new JLabel("One or more errors occurred during "
                    + "operation(s).");
            label.setFont(p.getSettings().getFont());

            int option = JOptionPane.showOptionDialog(p.getJFrame(),
                    label, "Prando v" + Prando.VERSION,
                    JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE,
                    null, buttons, buttons[1]);

            if(option == 1)
            {
                quit(p, buildErrorOutput(p));
            }//if

            else if(option == 0)
            {
                System.exit(0);
            }//else if
        }//else
    }//operationsComplete

    private static String buildErrorOutput(Prando p)
    {
        GraphicsEnvironment genv = GraphicsEnvironment.
                getLocalGraphicsEnvironment();
        genv.registerFont(p.getSettings().getFont());

        String errors = "<html><body>";

        for(String s : ModifySTFS.getErrors())
        {
            errors += s + "<br>";
        }//for

        errors += "</body></html>";

        return errors;
    }//buildErrorOutput
}//Utilites