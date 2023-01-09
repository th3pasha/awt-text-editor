package example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Scanner;

public class TextEditor extends Frame implements ActionListener
{
    String[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
    String fontFamily;
    String currentFont;
    int fontSize;
    MenuBar menuBar;
    TextArea textArea;
    Menu fileMenu;
    Menu editMenu;
    Menu fontSizeMenu;

    Menu fontFamilyMenu;

    MenuItem fileNew;
    MenuItem fileOpen;
    MenuItem fileClose;
    MenuItem fileSaveAs;
    MenuItem fileExit;

    MenuItem editSelect;

    MenuItem fontAddSizeMenu;
    MenuItem fontRemSizeMenu;

    MenuItem fontMenuItem;

    public TextEditor()
    {
        fontSize = 20;
        currentFont = "Arial";
        menuBar = new MenuBar();
        textArea = new TextArea();
        setMenuBar(menuBar);
        add(textArea);
        textArea.setFont(new Font(currentFont, Font.PLAIN, fontSize));
        setTitle("untitled.txt - Editeur de texte");
        setSize(500, 500);
        setVisible(true);

        fileMenu = new Menu("File", true);
        editMenu = new Menu("Edit");
        fontSizeMenu = new Menu("Font Size");
        fontFamilyMenu = new Menu("Font Family");


        fileNew = new MenuItem("New");
        fileOpen = new MenuItem("Open");
        fileClose = new MenuItem("Close");
        fileSaveAs = new MenuItem("Save as");
        fileExit = new MenuItem("Exit");

        fileMenu.add(fileNew);
        fileMenu.add(fileOpen);
        fileMenu.add(fileClose);
        fileMenu.add(fileSaveAs);
        fileMenu.addSeparator();
        fileMenu.add(fileExit);

        editSelect  = new MenuItem("Select all");

        editMenu.add(editSelect);

        fontAddSizeMenu = new MenuItem("+");
        fontRemSizeMenu = new MenuItem("--");

        fontSizeMenu.add(fontAddSizeMenu);
        fontSizeMenu.add(fontRemSizeMenu);

        for (String fontFamily : fonts)
        {
            fontMenuItem = new MenuItem(fontFamily);
            fontFamilyMenu.add(fontMenuItem);
        }

        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(fontSizeMenu);
        menuBar.add(fontFamilyMenu);

        // textArea.setForeground(Color.red);

        /*
        for (int i = 0; i <= fonts.length - 50 ;i++){
            fontPane.add(fonts[i]);
        }
        fontPane.setBounds(5,550,10,10);

        */

        fileNew.addActionListener(this);
        fileOpen.addActionListener(this);
        fileClose.addActionListener(this);
        fileSaveAs.addActionListener(this);
        fileExit.addActionListener(this);

        editSelect.addActionListener(this);

        fontAddSizeMenu.addActionListener(this);
        fontRemSizeMenu.addActionListener(this);
        fontMenuItem.addActionListener(this);

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        Frame frame = new Frame();

        if (e.getActionCommand().equals(fontFamily))
        {
            textArea.setFont(new Font(fontFamily ,Font.PLAIN, fontSize));
        }

        if (e.getActionCommand().equals("+"))
        {
            int fontSizeTemp = (int) fontSize;
            fontSizeTemp += 5;
            textArea.setFont(new Font(currentFont ,Font.PLAIN, fontSizeTemp));
            fontSize = fontSizeTemp;

        }
        if (e.getActionCommand().equals("--"))
        {
            int fontSizeTemp = (int) fontSize;
            fontSizeTemp -= 5;
            textArea.setFont(new Font(currentFont ,Font.PLAIN, fontSizeTemp));
            fontSize = fontSizeTemp;

        }
        if (e.getActionCommand().equals("New"))
        {
            setTitle("untitled.txt - Editeur de texte");
            textArea.setText("");
        }

        if (e.getActionCommand().equals("Open"))
        {
            String data = null;
            FileDialog fileLoadDialog = new FileDialog(frame, "loadDialog", FileDialog.LOAD);
            fileLoadDialog.setVisible(true);
            try
            {
                File loadedFile = new File(fileLoadDialog.getDirectory() + "\\" + fileLoadDialog.getFile());
                Scanner fileScanner = new Scanner(loadedFile);
                while(fileScanner.hasNextLine()){
                    data = fileScanner.nextLine();
                }
                textArea.append(data);
                fileScanner.close();

            }
            catch (FileNotFoundException ex)
            {
                throw new RuntimeException(ex);
            }
            setTitle(fileLoadDialog.getFile() + " - Editeur de texte ");
        }

        if (e.getActionCommand().equals("Save as"))
        {
                FileDialog fileLoadDialog = new FileDialog(frame, "saveDialog", FileDialog.SAVE);
                fileLoadDialog.setVisible(true);
                File savedFile = new File(fileLoadDialog.getDirectory()+ "\\" + fileLoadDialog.getFile());
                try
                {
                    FileWriter fileWriter = new FileWriter(savedFile);
                    BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                    bufferedWriter.write(textArea.getText());
                    bufferedWriter.close();
                }
                catch(Exception ex)
                {
                    throw new RuntimeException(ex);
                }
                setTitle(fileLoadDialog.getFile()+ " - Editeur de texte ");
            }
            if (e.getActionCommand().equals("Close") || e.getActionCommand().equals("Exit"))
            {
               dispose();
            }

        }
    }


