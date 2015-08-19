package com.royin.nio_01;

import java.nio.*;

/**
 * User: Roy.Yin
 * Date: 2015/6/24
 * Time: 15:52
 */
public class BufferDemo {

    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(10);
        buffer.put((byte)'a');
        buffer.put((byte)'b');
        buffer.put((byte)'c');
        buffer.put((byte)'d');

        ByteBuffer buffer1 = buffer.duplicate();
        buffer1.put((byte)'w');

        buffer.position(2).limit(3);

        ByteBuffer bf = buffer.slice();
        System.out.println(bf.get());

//        byte[] arr = new byte[]{'a','b','c'};
//
//        buffer.put(arr,0,buffer.remaining());
//
//
//        ByteBuffer buf = ByteBuffer.wrap(arr);
//
//        CharBuffer charBuffer = CharBuffer.allocate(100);
//        charBuffer.put("12121212");


//        buffer.put((byte)'a');
//        buffer.put((byte)'b');
//        buffer.put((byte)'c');
//        buffer.put((byte)'d');
//
//
//        buffer.flip();
//        System.out.println(buffer.get());
//        System.out.println(buffer.get());
//        buffer.compact();
//        buffer.put((byte)'l');
//        buffer.clear();


//        System.out.println(buffer.get());
//
//        buffer.put((byte)'d');
//        buffer.put((byte)'g');
//        buffer.put((byte)'g');
//
//        buffer.mark().position(0).reset();
//
//        buffer.put((byte)'G');
    }
}
