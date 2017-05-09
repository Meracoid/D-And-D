package com.dd.network;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Pipe;

public class PipeCommChannel extends NetworkCommChannel{
    private Pipe.SourceChannel inStream;
    private Pipe.SinkChannel outStream;

    public PipeCommChannel(Pipe.SourceChannel inStream, Pipe.SinkChannel outStream){
        this.inStream = inStream;
        this.outStream = outStream;
    }

    @Override
    public void close() throws IOException{
        inStream.close();
        outStream.close();
    }

    @Override
    public byte[] read() throws IOException{
        ByteBuffer data = ByteBuffer.allocate(1460);
        int dataRead = inStream.read(data);
        byte[] outData = data.array();
        if (dataRead != 1460){
            byte[] tempData = new byte[dataRead];
            for(int i = 0; i < dataRead; i++){
                tempData[i] = outData[i];
            }
            outData = tempData;
        }
        return outData;
    }

    @Override
    public void write(byte[] data) throws IOException{
        ByteBuffer sendData = ByteBuffer.allocate(data.length);
        sendData.put(data);
        outStream.write(sendData);
    }

    public Pipe.SourceChannel getInputChannel(){
        return inStream;
    }

    public Pipe.SinkChannel getOutputChannel(){
        return outStream;
    }
}
