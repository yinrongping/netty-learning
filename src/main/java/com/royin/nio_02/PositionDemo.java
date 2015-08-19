package com.royin.nio_02;

import java.io.RandomAccessFile;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;

/**
 * FileChannel λ�ã�position���Ǵӵײ���ļ���������õģ��� position ͬʱ����Ϊͨ������
 * ��ȡ��Դ���ļ���������Ҳ����ζ��һ������Ը� position �ĸ��¿��Ա���һ�����󿴵�
 * User: Roy.Yin
 * Date: 2015/6/25
 * Time: 14:28
 */
public class PositionDemo {

    public static void main(String[] args) throws Exception{

        RandomAccessFile randomAccessFile = new RandomAccessFile ("test.txt", "rw");
// Set the file position
        randomAccessFile.seek(5);
// Create a channel from the file
        FileChannel fileChannel = randomAccessFile.getChannel( );
// This will print "1000"
        System.out.println ("file pos: " + fileChannel.position());
// Change the position using the RandomAccessFile object
        randomAccessFile.seek(8);
        // This will print "500"
        System.out.println ("file pos: " + fileChannel.position( ));
// Change the position using the FileChannel object
        fileChannel.position (500);
// This will print "200"
        System.out.println ("file pos: " + randomAccessFile.getFilePointer( ));
        ByteBuffer bb = ByteBuffer.allocate(10);
        bb.put((byte)'d');
        bb.put((byte)'e');
        bb.put((byte)'d');
        bb.put((byte)'d');bb.put((byte)'d');bb.put((byte)'d');bb.put((byte)'d');bb.put((byte)'d');

        bb.flip();
        fileChannel.write(bb);


        System.out.println(fileChannel.size());
        //fileChannel.truncate(100);
    }
}
