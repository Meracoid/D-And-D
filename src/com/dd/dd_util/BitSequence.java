package com.dd.dd_util;

public class BitSequence {
    private int length;
    private byte[] sequence;
    private int bitOffset;

    public BitSequence(int length, byte[] sequence){
        this.length = length;
        if(Math.ceil((double)length / 8.0) != sequence.length){
            throw new InvalidBitLengthException("");
        }
        this.sequence = sequence;
        this.bitOffset = 0;
    }

    public BitPattern getNextBits(int numBits){
        if(numBits + bitOffset >= length) {
            throw new InvalidBitLengthException("");
        }
        int byteIndex = (int)Math.ceil((double)length / 8.0);
        int numBitsGot = 0;
        int numBytes = (int)Math.ceil((double)numBits / 8.0);
        byte[] retPattern = new byte[numBytes];

        for(int i = 0; i < numBytes - 1; i++, numBitsGot += 8){
            retPattern[i] = sequence[bitOffset + i];
        }
        retPattern[numBytes - 1] = (byte)(sequence[bitOffset + numBytes - 1] >> (8 - numBits - numBitsGot));
        retPattern[numBytes - 1] <<= (8 - numBits - numBitsGot);
        bitOffset += numBits;
        return new BitPattern(numBits, retPattern);
    }

    public byte[] getAsBytes() {
        return sequence;
    }

    public boolean hasNext(){
        return (bitOffset < length) ? true : false;
    }

    public void resetOffset(){
        bitOffset = 0;
    }
}
