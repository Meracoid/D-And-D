package com.dd.network;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.channels.SocketChannel;

public class SocketCommChannel extends NetworkCommChannel{
    private Socket socket;
    private SocketChannel channel;
    private InputStream inStream;
    private OutputStream outStream;

    public SocketCommChannel(Socket socket){
        try{
            this.socket = socket;
            channel = socket.getChannel();
            inStream = socket.getInputStream();
            outStream = socket.getOutputStream();
        }
        catch(IOException e){
            //hanlde failure
        }
    }

    public SocketCommChannel(InetAddress addr, int port){
        try{
            socket = new Socket(addr, port);
            channel = socket.getChannel();
            inStream = socket.getInputStream();
            outStream = socket.getOutputStream();
        }
        catch(IOException e){
            //hanlde failure
        }
    }

    @Override
    public void close() throws IOException{
        try {
            socket.close();
        }
        catch(IOException e){
            //handle failure
        }
    }

    @Override
    public byte[] read() throws IOException {
        byte[] outData = null;
        int availData = inStream.available();
        if (availData > 0){
            outData = new byte[availData];
            inStream.read(outData);
        }
        else{
            outData = new byte[1460];
            int dataRead = inStream.read(outData);
            if (dataRead < 1460){
                byte[] tempData = new byte[dataRead];
                for(int i = 0; i < dataRead; i++){
                    tempData[i] = outData[i];
                }
                outData = tempData;
            }
        }
        return outData;
    }

    @Override
    public void write(byte[] data) throws IOException{
        outStream.write(data);
    }

    public InputStream getInputStream() throws IOException{
        return socket.getInputStream();
    }

    public OutputStream getOutputStream() throws IOException{
        return socket.getOutputStream();
    }

    public Socket getSocket(){
        return socket;
    }
}
