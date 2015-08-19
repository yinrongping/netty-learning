package com.royin.nio_02;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;

/**
 * User: Roy.Yin
 * Date: 2015/6/25
 * Time: 11:17
 */
public class ScatterDemo {

    private static Charset charset = Charset.forName("utf-8");

    public static void main(String[] args) throws IOException {
        final String fileName = "test.txt";
        //��д��10���ֽ����� �Ա���� scatterģʽ
        writeData(fileName, "qwertyuiop");

        /**----------Scatter------------*/
        //read(java.nio.ByteBuffer[])
        scatter(fileName);

        //read(java.nio.ByteBuffer[], int, int)
        scatter2(fileName);
    }


    /**
     * Scatter
     * <br>------------------------------<br>
     * @param fileName
     * @throws IOException
     * @see java.nio.channels.FileChannel.read(java.nio.ByteBuffer[])
     */
    private static void scatter(final String fileName) throws IOException {
        RandomAccessFile accessFile = new RandomAccessFile(fileName, "r");
        //��ȡ�ļ�ͨ��
        FileChannel channel = accessFile.getChannel();
        //��������������
        ByteBuffer headBuffer = ByteBuffer.allocate(10);
        ByteBuffer bodyBuffer = ByteBuffer.allocate(1024);

        ByteBuffer[] allBuffers = new ByteBuffer[]{headBuffer, bodyBuffer};
        // headBuffer ǰ10���ֽ�
        // bodyBuffer ʣ�µ�
        long n = channel.read(allBuffers);
        System.out.println("�����������ֽ�:" + n);

        headBuffer.flip();
        //head�������е�����:qw
        System.out.println("head�������е�����:" + charset.decode(headBuffer));

        bodyBuffer.flip();
        //body�������е�����:ertyuiop
        System.out.println("body�������е�����:" + charset.decode(bodyBuffer));
        accessFile.close();
        channel.close();
    }

    /**
     * Scatter2
     * <br>------------------------------<br>
     * @param fileName
     * @throws IOException
     * @see FileChannel.read(java.nio.ByteBuffer[], int, int)
     */
    private static void scatter2(final String fileName) throws IOException {
        RandomAccessFile accessFile = new RandomAccessFile(fileName, "r");
        //��ȡ�ļ�ͨ��
        FileChannel channel = accessFile.getChannel();
        //�������������
        ByteBuffer headBuffer = ByteBuffer.allocate(2);
        ByteBuffer bodyBuffer1 = ByteBuffer.allocate(3);
        ByteBuffer bodyBuffer2 = ByteBuffer.allocate(2);
        ByteBuffer bodyBuffer3 = ByteBuffer.allocate(2);
        ByteBuffer bodyBuffer4 = ByteBuffer.allocate(1);

        ByteBuffer[] allBuffers = new ByteBuffer[]{
                headBuffer,
                bodyBuffer1, bodyBuffer2,
                bodyBuffer3, bodyBuffer4,};
        //0���Ǹ���������ʼ��ʹ��    ʹ��3��������
        //��ʹ�� headBuffer,bodyBuffer1,bodyBuffer2
        long n = channel.read(allBuffers, 0, 3);

        System.out.println("�����������ֽ�:" + n);

        headBuffer.flip();
        //head�������е�����:qw
        System.out.println("head�������е�����:" + charset.decode(headBuffer));

        bodyBuffer1.flip();
        //body1�������е�����:ert
        System.out.println("body1�������е�����:" + charset.decode(bodyBuffer1));

        bodyBuffer2.flip();
        //body2�������е�����:yu
        System.out.println("body2�������е�����:" + charset.decode(bodyBuffer2));

        bodyBuffer3.flip();
        //body3��û������
        System.out.println("body3�������е�����:" + charset.decode(bodyBuffer3));

        bodyBuffer4.flip();
        //body4û������
        System.out.println("body4�������е�����:" + charset.decode(bodyBuffer4));

        accessFile.close();
        channel.close();
    }

    /**
     *
     * <br>------------------------------<br>
     * @param fileName
     * @throws IOException
     */
    private static void writeData(final String fileName, String data) throws IOException {
        RandomAccessFile accessFile = new RandomAccessFile(fileName, "rw");
        accessFile.writeBytes(data);
        accessFile.close();
    }

}
