package com.dd.dd_util;

import java.util.List;

public class ByteUtility {
    public static byte[] byteArrayFromByteList(List<Byte> list){
        byte[] outArr = null;
        if(list.size() > 0) {
            Byte[] tempArr = new Byte[list.size()];
            list.toArray(tempArr);
            outArr = new byte[list.size()];
            for (int i = 0; i < tempArr.length; i++) {
                outArr[i] = tempArr[i];
            }
        }
        return outArr;
    }

    public static byte getBits(byte data, int numBits){
        if(numBits > 8 || numBits <= 0){
            throw new InvalidBitLengthException("");
        }
        data <<= (8 - numBits);
        return (byte)(data >> (8 - numBits));
    }

    public static byte getBits(byte data, int startBit, int numBits){
        int maxBit = startBit + numBits;
        if(maxBit > 8 || maxBit <= 0 || startBit <= 0){
            throw new InvalidBitLengthException("");
        }
        data <<= (8 - maxBit);
        data >>= (8 - maxBit + startBit);
        return (byte)data;
    }

    public static short getShortFromBytes(byte[]data, int bitLength){
        if(bitLength > 16){
            throw new InvalidBitLengthException("");
        }
        short retShort = 0;
        int numBytes = (int)Math.ceil((double)bitLength / 8.0);
        for(int i = 0, bitOffset = 0; bitOffset < bitLength; i++, bitOffset+=8){
            retShort += ((short)data[i]) << bitOffset;
        }
        return retShort;
    }

    public static int getIntFromBytes(byte[] data, int bitLength){
        if(bitLength > 32){
            throw new InvalidBitLengthException("");
        }
        int retInt = 0;
        int numBytes = (int)Math.ceil((double)bitLength / 8.0);
        for(int i = 0, bitOffset = 0; bitOffset < bitLength; i++, bitOffset+=8){
            retInt += ((int)data[i]) << bitOffset;
        }
        return retInt;
    }

    public static long getLongFromBytes(byte[] data, int bitLength){
        if(bitLength > 128){
            throw new InvalidBitLengthException("");
        }
        long retLong = 0;
        int numBytes = (int)Math.ceil((double)bitLength / 8.0);
        for(int i = 0, bitOffset = 0; bitOffset < bitLength; i++, bitOffset+=8){
            retLong += ((long)data[i]) << bitOffset;
        }
        return retLong;
    }

    public static char getCharFromBytes(byte[] data, int bitLength){
        if(bitLength > 16){
            throw new InvalidBitLengthException("");
        }
        char retChar = 0;
        int numBytes = (int)Math.ceil((double)bitLength / 8.0);
        boolean shortChar = (numBytes % 2 != 0) ? true : false;
        retChar += ((short)data[0]) << 8;
        if(!shortChar){
            retChar += data[1];
        }
        return retChar;
    }

    public static String getStringFromBytes(byte[] data, int bitLength){
        int numBytes = (int)Math.ceil((double)bitLength / 8.0);
        char[] charBuffer = new char[numBytes << 1];
        for(int i = 0, j = 0, bitOffset = 0; j < numBytes;){
            charBuffer[i] = (char)(((short)data[j++]) << 8);
            bitOffset += 8;
            if(bitOffset < bitLength){
                charBuffer[i++] += (char)data[j++];
            }
            else{
                break;
            }
        }
        return new String(charBuffer);
    }
}
