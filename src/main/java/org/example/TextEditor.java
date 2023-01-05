package org.example;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Scanner;

public class TextEditor extends Frame implements ActionListener
{
    private String text;

    // les MenuBar
    MenuBar menuBar = new MenuBar();
    // Zone de text
    TextArea textArea = new TextArea();

    Frame frame = new Frame();
    // les Menu File, Edit, Save
    Menu fileMenu = new Menu("File", true);
    Menu editMenu = new Menu("Edit");

    // les MenuItem Open, Close
    MenuItem fileOpen = new MenuItem("Open");
    MenuItem fileClose = new MenuItem("Close");
    MenuItem fileSaveAs = new MenuItem("Save as");


    @Override
    public void actionPerformed(ActionEvent e) {
        String s = e.getActionCommand();

        if (s.equals("Open"))
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
        }

        if (s.equals("Save as"))
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
            catch(Exception exception)
            {

            }
        }
    }

    public TextEditor()
    {

        setTitle("Text Editor");
        setBounds(100, 100, 600, 700);
        setVisible(true);
        setMenuBar(menuBar);
        add(textArea);

        // ----------------------------------------------- Editeur de text setting
        this.text = textArea.getSelectedText();

        // ----------------------------------------------- les zone dans MenuBar
        menuBar.add(fileMenu);
        menuBar.add(editMenu);

        // -------------------------------------------------le Menu file
        fileMenu.add(fileOpen);
        fileMenu.add(fileClose);
        fileMenu.add(fileSaveAs);

        // ----------------------------------------------
        fileOpen.addActionListener(this);
        fileClose.addActionListener(this);
        fileSaveAs.addActionListener(this);

        // --------------------------- WINDOWS LISTENERS ----------------------------

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }
}
