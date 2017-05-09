package com.dd.command_util;

import javafx.scene.control.TextInputControl;

public class CommandOutputLog{
    public static TextInputControl outputLog;

    public CommandOutputLog(TextInputControl outputLog){
        this.outputLog = outputLog;
    }

    public static void print(String outputStr){
        outputLog.appendText(outputStr);
    }

    public static void print(byte output[]){
        print(new String(output));
    }
}
