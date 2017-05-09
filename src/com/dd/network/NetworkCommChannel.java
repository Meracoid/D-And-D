package com.dd.network;

import java.io.IOException;
import java.nio.channels.spi.AbstractSelectableChannel;
import java.nio.channels.spi.SelectorProvider;

public abstract class NetworkCommChannel{
    public abstract void close() throws IOException;
    public abstract byte[] read() throws IOException;
    public abstract void write(byte[] data) throws IOException;
}
