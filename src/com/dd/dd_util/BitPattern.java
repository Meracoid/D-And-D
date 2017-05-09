package com.dd.dd_util;

import java.lang.Math;

public class BitPattern {
    private int bitLength;
    private byte[] bitPattern;

    public BitPattern(int bitLength, byte[] bitPattern){
        int byteLength = (int)Math.ceil((double)bitLength / 8.0);
        if(byteLength != bitPattern.length){
            throw new InvalidBitPatternException("");
        }
        this.bitLength = bitLength;
        this.bitPattern = bitPattern;
    }

    public boolean matchesBitPattern(byte[] bitPatternTest){
        boolean retCode = true;
        if(bitPattern.length != bitPatternTest.length){
            retCode = false;
        }
        else{
            int i = 0;
            for(; i < bitPattern.length - 1; i++){
                if(bitPattern[i] != bitPatternTest[i]){
                    retCode = false;
                    break;
                }
                if(retCode == true){
                    byte lastByteMask = (byte)0xff;
                    for(int j = 0; j < 8 - (bitLength % 8); j++){
                        lastByteMask |= 1<<j;
                    }
                    if(bitPattern[i] != (bitPatternTest[i] & lastByteMask)){
                        retCode = false;
                    }
                }
            }
        }
        return retCode;
    }

    public boolean matchesBitPattern(BitPattern bitPatternTest){
        if(bitLength != bitPatternTest.getBitLength()){
            return false;
        }
        for(int i = 1; i < bitPattern.length; i++){
            if(bitPattern[i] != bitPatternTest.getBitPattern()[i]){
                return false;
            }
        }
        return true;
    }

    public byte getAsByte(){
        if(bitLength > 8){
            throw new InvalidBitLengthException("");
        }
        return bitPattern[0];
    }

    public short getAsShort(){
        return ByteUtility.getShortFromBytes(bitPattern, bitLength);
    }

    public int getAsInt(){
        return ByteUtility.getIntFromBytes(bitPattern, bitLength);
    }

    public long getAsLong() { return ByteUtility.getLongFromBytes(bitPattern, bitLength); }

    public char getAsChar(){
        return ByteUtility.getCharFromBytes(bitPattern, bitLength);
    }

    public String getAsString(){
        return ByteUtility.getStringFromBytes(bitPattern, bitLength);
    }

    public byte[] getBitPattern(){
        return bitPattern;
    }

    public int getBitLength(){
        return bitLength;
    }

    public BitPattern modBitPattern(int bitLengthMod, byte[] bitPatternMod){
        return new BitPattern(bitLength + bitLengthMod, bitPatternMod);
    }

    public class InvalidBitPatternException extends RuntimeException {
        public InvalidBitPatternException(String message) {
            super(message);
        }
    }
}
