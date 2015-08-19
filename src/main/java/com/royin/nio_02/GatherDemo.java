package com.royin.nio_02;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;

/**
 * User: Roy.Yin
 * Date: 2015/6/25
 * Time: 11:14
 */
public class GatherDemo {

    private static Charset charset = Charset.forName("UTF-8");

    public static void main(String[] args) throws IOException {
        final String fileName = "test.txt";
        /**----------Gather------------*/
        //FileChannel#write(java.nio.ByteBuffer[])
        gather(fileName);

        //FileChannel#write(java.nio.ByteBuffer[], int, int)
        gather2(fileName);
    }

    /**
     * gather
     * <br>------------------------------<br>
     * @param fileName
     * @throws IOException
     * @see FileChannel#write(java.nio.ByteBuffer[])
     */
    private static void gather(String fileName) throws IOException {
        RandomAccessFile accessFile = new RandomAccessFile(fileName, "rw");
        //��ȡ�ļ�ͨ��
        FileChannel channel = accessFile.getChannel();
        //��������������
        ByteBuffer headBuffer = ByteBuffer.allocate(3);
        headBuffer.put("abc".getBytes());

        ByteBuffer bodyBuffer = ByteBuffer.allocate(1024);
        bodyBuffer.put("defg".getBytes());

        ByteBuffer[] allBuffers = new ByteBuffer[]{headBuffer, bodyBuffer};

        headBuffer.flip();
        bodyBuffer.flip();

        //����allBuffers˳��  д��abcdefg
        long n = channel.write(allBuffers);

        System.out.println("��д������ֽ�:" + n);

        accessFile.close();
        channel.close();
    }

    /**
     * gather2
     * <br>------------------------------<br>
     * @param fileName
     * @throws IOException
     * @see FileChannel#write(java.nio.ByteBuffer[], int, int)
     */
    private static void gather2(String fileName) throws IOException {
        RandomAccessFile accessFile = new RandomAccessFile(fileName, "rw");
        //��ȡ�ļ�ͨ��
        FileChannel channel = accessFile.getChannel();
        //��������������
        ByteBuffer headBuffer = ByteBuffer.allocate(3);
        ByteBuffer bodyBuffer1 = ByteBuffer.allocate(4);
        ByteBuffer bodyBuffer2 = ByteBuffer.allocate(20);
        ByteBuffer bodyBuffer3 = ByteBuffer.allocate(20);
        ByteBuffer bodyBuffer4 = ByteBuffer.allocate(20);

        headBuffer.put("abc".getBytes());
        bodyBuffer1.put("defg".getBytes());
        bodyBuffer2.put("bnbnbnb".getBytes());
        bodyBuffer3.put("zzz444".getBytes());

        ByteBuffer[] allBuffers = new ByteBuffer[]{
                headBuffer,
                bodyBuffer1, bodyBuffer2,
                bodyBuffer3, bodyBuffer4,};

        headBuffer.flip();
        bodyBuffer1.flip();
        bodyBuffer2.flip();
        bodyBuffer3.flip();
        bodyBuffer4.flip();

        //����allBuffers����˳��ʹ������������
        //0���Ŀ�ʼ
        //2ʹ�ü���
        //��ǰʹ��headBuffer  bodyBuffer1
        //����д��abcdefg
        long n = channel.write(allBuffers, 0, 5);

        //Ӧ�÷���7���ֽ�
        System.out.println("��д������ֽ�:" + n);

        accessFile.close();
        channel.close();
    }


}
