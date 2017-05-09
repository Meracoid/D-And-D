package com.dd.command_util;

import javafx.scene.control.TextInputControl;

public class LocalCommandOutputLog extends CommandOutputLog{
    private TextInputControl outputLog;

    public LocalCommandOutputLog(TextInputControl outputLog){
        this.outputLog = outputLog;
    }

    @Override
    public void print(String outputStr){
        outputLog.appendText(outputStr);
    }

    @Override
    public void print(byte output[]){
        print(new String(output));
    }
}
