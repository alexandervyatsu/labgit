package com.company;

import java.io.IOException;
import java.io.PrintWriter;


public class Replacer {

    private String content;

    private String[] token_array;


    Replacer() {
        this.content = null;
        this.token_array = null;
    }

    void setContent(String content) {
        this.content = content;
    }

    String getContent() {
        return this.content;
    }

    String getTokens() {
        String tmp = new String();
        for (String token : this.token_array) {
            tmp += token + " ";
        }
        return tmp;
    }


    void setTokens(String tokens) {
        this.token_array = tokens.split("\\s|\\W");
    }


    void replaceContent() {
        if (this.content != null && this.token_array != null)
            try {
                for (String token : this.token_array)
                    this.content = this.content.replaceAll("\\b" + token + "\\b", "");
            } catch (Exception e) {
                e.printStackTrace();
            }
    }


    void writeContentToFile(String file_name) {
        try {
            PrintWriter writer = new PrintWriter(file_name);
            writer.print(content);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
