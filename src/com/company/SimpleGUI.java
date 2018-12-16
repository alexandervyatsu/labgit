package com.company;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class SimpleGUI extends JFrame {

    private String content, tokens;
    private JMenuItem txtFileItem, tokenFileItem, exitItem, saveItem;
    private JButton replace_button;
    private JTextArea text1, text2, text3;
    private JFrame thisFrame = this;
    JLabel labl1, labl2;

    private void createMenu() {
        JMenuBar menuBar;
        JMenu fileMenu, newMenu;
        Font font = new Font("Verdana", Font.PLAIN, 11);
        menuBar = new JMenuBar();
        fileMenu = new JMenu("Файл");
        fileMenu.setFont(font);
        newMenu = new JMenu("Открыть");
        newMenu.setFont(font);
        fileMenu.add(newMenu);
        txtFileItem = new JMenuItem("Файл для замены");
        txtFileItem.setFont(font);
        newMenu.add(txtFileItem);
        tokenFileItem = new JMenuItem("Файл с лексемами");
        tokenFileItem.setFont(font);
        newMenu.add(tokenFileItem);
        fileMenu.addSeparator();
        saveItem = new JMenuItem("Сохранить");
        saveItem.setFont(font);
        fileMenu.add(saveItem);
        fileMenu.addSeparator();
        exitItem = new JMenuItem("Выход");
        exitItem.setFont(font);
        fileMenu.add(exitItem);
        menuBar.add(fileMenu);
        replace_button = new JButton("Заменить");
        menuBar.add(replace_button);
        setJMenuBar(menuBar);
    }

    private void Events() {
        Replacer r = new Replacer();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("TEXT FILES", "txt", "text");
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(filter);
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        exitItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        txtFileItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int result = fileChooser.showOpenDialog(thisFrame);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    System.out.println("Selected file: " + selectedFile.getAbsolutePath());
                    try {
                        content = new String(Files.readAllBytes(Paths.get(selectedFile.getAbsolutePath())));
                    } catch (IOException err) {
                        System.out.println("Нельзя прочитать файл для замены или он отсутствует");
                        err.printStackTrace();
                    }
                    labl1.setText("label1");
                    text1.setText(content);
                }
            }
        });
        replace_button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("click");
                r.setContent(text1.getText());
                r.setTokens(text2.getText());
                r.replaceContent();
                text3.setText(r.getContent());
            }
        });
        tokenFileItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int result = fileChooser.showOpenDialog(thisFrame);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    System.out.println("Selected file: " + selectedFile.getAbsolutePath());
                    try {
                        tokens = new String(Files.readAllBytes(Paths.get(selectedFile.getAbsolutePath())));
                    } catch (IOException err) {
                        System.out.println("Нельзя прочитать файл для замены или он отсутствует");
                        err.printStackTrace();
                    }
                    labl2.setText(selectedFile.getAbsolutePath());
                    text2.setText(tokens);
                }
            }
        });
        saveItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                fileChooser.setDialogTitle("Specify a file to save");
                int userSelection = fileChooser.showSaveDialog(thisFrame);
                if (userSelection == JFileChooser.APPROVE_OPTION) {
                    File fileToSave = fileChooser.getSelectedFile();
                    r.writeContentToFile(fileToSave.getAbsolutePath());
                    System.out.println("Save as file: " + fileToSave.getAbsolutePath());
                }
            }
        });
    }

    private void createOther() {
        JPanel grid, bottom_panel;
        GridLayout layout;
        grid = new JPanel();
        bottom_panel = new JPanel();
        labl1 = new JLabel("text1");
        labl2 = new JLabel("text2");
        bottom_panel.add(labl1);
        bottom_panel.add(labl2);
        getContentPane().add(BorderLayout.SOUTH, bottom_panel);
        layout = new GridLayout(1, 3, 10, 10);
        grid.setLayout(layout);
        text1 = new JTextArea();
        text1.setWrapStyleWord(true);
        text1.setLineWrap(true);
        text2 = new JTextArea();
        text2.setWrapStyleWord(true);
        text2.setLineWrap(true);
        text3 = new JTextArea();
        text3.setWrapStyleWord(true);
        text3.setLineWrap(true);
        grid.add(text1);
        grid.add(text2);
        grid.add(text3);
        getContentPane().add(BorderLayout.CENTER, grid);
    }

    //--------------------------------------------------------
    public SimpleGUI() {
        super("Lab3");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
        createMenu();
        createOther();
        Events();
        // выводим окно на экран
        setSize(800, 600);
        setVisible(true);
    }

}