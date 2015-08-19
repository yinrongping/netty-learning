package com.royin.nio_02;

import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;

/**
 * ÄÚ´æÓ³ÉäÎÄ¼þ
 * User: Roy.Yin
 * Date: 2015/6/25
 * Time: 18:13
 */
public class MappedFileDemo {

    public static void main(String[] args) throws Exception{

        RandomAccessFile randomAccessFile = new RandomAccessFile("test.txt", "rw");
        FileChannel fileChannel = randomAccessFile.getChannel();
        MappedByteBuffer byteBuffer = fileChannel.map(FileChannel.MapMode.READ_ONLY, 0, 10);
        System.out.println(Charset.forName("GBK").decode(byteBuffer));
    }
}
