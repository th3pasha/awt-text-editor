package org.example;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Scanner;

public class TextEditor extends Frame implements ActionListener
{
    String[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
    //Choice fontPane;
    MenuBar menuBar;
    TextArea textArea;

    Dialog saveDialog;

    Menu fileMenu;
    Menu editMenu;
    Menu formatMenu;

    MenuItem fileNew;
    MenuItem fileOpen;
    MenuItem fileClose;
    MenuItem fileSaveAs;
    MenuItem fileExit;

    MenuItem editSelect;
    MenuItem policeFormat;
    MenuItem fontFormat;

    public TextEditor()
    {
        menuBar = new MenuBar();
        editMenu    = new Menu("Edit");
        textArea = new TextArea();
       //fontPane = new Choice();
        editSelect  = new MenuItem("Select all");

        saveDialog = new Dialog(this);

        setMenuBar(menuBar);
        add(textArea);
        //add(fontPane);

        setVisible(true);
        textArea.setFont(new Font("Arial",Font.PLAIN,20));
        setTitle("untitled.txt - Editeur de texte");
        setSize(600, 600);

        fileMenu = new Menu("File", true);
        fileNew = new MenuItem("New");
        fileOpen = new MenuItem("Open");
        fileClose = new MenuItem("Close");
        fileSaveAs = new MenuItem("Save as");
        fileExit = new MenuItem("Exit");

        formatMenu   = new Menu("Format");
        policeFormat = new MenuItem("Police");
        fontFormat   = new MenuItem("Font");

        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(formatMenu);
        editMenu.add(editSelect);

        fileMenu.add(fileNew);
        fileMenu.add(fileOpen);
        fileMenu.add(fileClose);
        fileMenu.add(fileSaveAs);
        fileMenu.addSeparator();
        fileMenu.add(fileExit);

        formatMenu.add(fontFormat);
        formatMenu.add(policeFormat);

         /*
        for (int i = 0; i <= fonts.length - 50 ;i++){
            fontPane.add(fonts[i]);
        }
         */
        //fontPane.setBounds(50);

        fileNew.addActionListener(this);
        fileOpen.addActionListener(this);
        fileClose.addActionListener(this);
        fileSaveAs.addActionListener(this);
        fileExit.addActionListener(this);
        editSelect.addActionListener(this);

        /*
        fontPane.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                textArea.setFont(new Font((String)fontPane.getSelectedItem(),Font.PLAIN,textArea.getFont().getSize()));
            }
        });*/

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

        if (e.getActionCommand().equals("Close"))
        {
            saveDialog.setVisible(true);
            saveDialog.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    saveDialog.dispose();
                }
            });
        }
        if (e.getActionCommand().equals("Exit"))
        {
            dispose();
        }
    }
}
