package example;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.lang.reflect.*;
import java.util.*;

public class TextEditor extends Frame implements ActionListener
{
    String[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
    String fontFamily;
    String currentFont;
    int fontSize;

    Field[] fields;

    MenuBar menuBar;
    TextArea textArea;
    Menu fileMenu;
    Menu editMenu;
    Menu fontSizeMenu;

    Menu fontFamilyMenu;
    Menu fontStyleMenu;

    Menu fontColorMenu;

    MenuItem fileNew;
    MenuItem fileOpen;
    MenuItem fileClose;
    MenuItem fileSaveAs;
    MenuItem fileExit;

    MenuItem editSelect;

    MenuItem fontAddSizeMenu;
    MenuItem fontRemSizeMenu;

    MenuItem fontMenuItem;

    MenuItem fontStylePlain;
    MenuItem fontStyleBold;
    MenuItem fontStyleItalic;

    MenuItem colorMenuItem;

    public TextEditor()
    {
        fontSize = 20;
        //currentFont = "Algerian";
        menuBar = new MenuBar();
        textArea = new TextArea();
        setMenuBar(menuBar);
        add(textArea);
        //textArea.setFont(new Font(currentFont, Font.PLAIN, fontSize));
        setTitle("untitled.txt - Editeur de texte");
        setSize(500, 500);
        setVisible(true);

        fileMenu = new Menu("File", true);
        editMenu = new Menu("Edit");
        fontSizeMenu = new Menu("Font size");
        fontFamilyMenu = new Menu("Family");

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
            fontMenuItem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    textArea.setFont(new Font(fontFamily, Font.PLAIN, fontSize));
                }
            });
            fontFamilyMenu.add(fontMenuItem);
        }

        fontStyleMenu = new Menu("Style");
        fontStylePlain = new MenuItem("Plain");
        fontStyleBold = new MenuItem("Bold");
        fontStyleItalic = new MenuItem("Italic");

        fontStyleMenu.add(fontStylePlain);
        fontStyleMenu.add(fontStyleBold);
        fontStyleMenu.add(fontStyleItalic);

        fontColorMenu = new Menu("Color");

        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(fontSizeMenu);
        menuBar.add(fontFamilyMenu);
        menuBar.add(fontStyleMenu);
        menuBar.add(fontColorMenu);

        fields = Color.class.getFields();

        for (Field field : fields) {
            if (java.lang.reflect.Modifier.isStatic(field.getModifiers()) && java.lang.reflect.Modifier.isFinal(field.getModifiers()) && field.getType() == Color.class)
            {
                try
                {
                    final Color color = (Color) field.get(null);
                    colorMenuItem = new MenuItem(field.getName());
                    fontColorMenu.add(colorMenuItem);
                    colorMenuItem.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        textArea.setForeground(color);
                    }
                });
                }
                catch (IllegalAccessException e)
                {
                    e.printStackTrace();
                }
            }
        }

        fileNew.addActionListener(this);
        fileOpen.addActionListener(this);
        fileClose.addActionListener(this);
        fileSaveAs.addActionListener(this);
        fileExit.addActionListener(this);

        editSelect.addActionListener(this);

        fontAddSizeMenu.addActionListener(this);
        fontRemSizeMenu.addActionListener(this);

        fontMenuItem.addActionListener(this);

        fontStylePlain.addActionListener(this);
        fontStyleBold.addActionListener(this);
        fontStyleItalic.addActionListener(this);

        colorMenuItem.addActionListener(this);

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
        /*
        if (e.getActionCommand().equals(fontFamily))
        {
            textArea.setFont(new Font(fontFamily, Font.PLAIN, fontSize));
        }
        */
        if (e.getActionCommand().equals("Plain")){
            textArea.setFont(new Font(fontFamily, Font.PLAIN, fontSize));
        }
        if (e.getActionCommand().equals("Bold")){
            textArea.setFont(new Font(fontFamily, Font.BOLD, fontSize));
        }
        if (e.getActionCommand().equals("Italic")){
            textArea.setFont(new Font(fontFamily, Font.ITALIC, fontSize));
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


