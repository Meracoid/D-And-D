package com.dd.network;

import javafx.scene.control.TextInputControl;

import java.util.concurrent.Semaphore;

public class ClientInputController {
    private Semaphore controlSem = new Semaphore(1);
    private TextInputControl clientInput;

    public ClientInputController(TextInputControl clientInput){
        this.clientInput = clientInput;
    }

    public void clientTakeControl(){
        try {
            controlSem.acquire();
            clientInput.setEditable(true);
        }
        catch (InterruptedException e) {

        }
    }

    public void clientReleaseControl(){
        clientInput.setEditable(false);
        controlSem.release();
    }

    public void listenerTakeControl(){
        try{
            controlSem.acquire();
        }
        catch (InterruptedException e){

        }
    }

    public void listenerReleaseControl(){
        controlSem.release();
    }
}
