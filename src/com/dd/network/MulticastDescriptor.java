package com.dd.network;

import com.dd.dd_util.ByteUtility;

import java.nio.channels.SelectionKey;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MulticastDescriptor {
    private List<Byte> baseMsg = new ArrayList<Byte>();
    private Map<SelectionKey, byte[]> individualMessageMap = new HashMap<SelectionKey, byte[]>();

    public MulticastDescriptor(){}

    public void appendBaseMsg(byte[] newData){
        for(byte b : newData){
            baseMsg.add(b);
        }
    }

    public byte[] getBaseMsg(){
        return ByteUtility.byteArrayFromByteList(baseMsg);
    }

    public void setIndividualMessageMap(SelectionKey key, byte[] msg){
        individualMessageMap.put(key, msg);
    }

    public byte[] getIndividualMessage(SelectionKey key){
        byte[] outArr = individualMessageMap.get(key);
        return outArr;
    }
}
