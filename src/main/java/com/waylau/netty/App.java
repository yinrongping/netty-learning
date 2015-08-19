package com.waylau.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.CompositeByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelPipeline;

import java.nio.charset.Charset;
import java.util.Iterator;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {

/*        CompositeByteBuf compBuf = Unpooled.compositeBuffer();
        ByteBuf heapBuf = Unpooled.buffer(8);
        ByteBuf directBuf = Unpooled.directBuffer(16);
        //添加ByteBuf到CompositeByteBuf
        compBuf.addComponents(heapBuf,directBuf);
        //删除第一个ByteBuf
        compBuf.removeComponent(0);
        Iterator<ByteBuf> iter = compBuf.iterator();
        while(iter.hasNext()){
            System.out.println(iter.next().toString());
        }
        //使用数组访问数据
        if(!compBuf.hasArray()){
            int len = compBuf.readableBytes();
            byte[] arr = new byte[len];
            compBuf.getBytes(0, arr);
        }*/



      /*  //create a ByteBuf of capacity is 16
        ByteBuf buf = Unpooled.buffer(16);
        //write data to buf
        for(int i=0;i<16;i++){
            buf.writeByte(i+1);
        }

        System.out.println(ByteBufUtil.hexDump(buf));

        //read data from buf
        for(int i=0;i<buf.capacity();i++){
            System.out.println(buf.getByte(i));
        }

        //ByteBuf buf = Unpooled.buffer(16);
        while(buf.isReadable()){
            System.out.println(buf.readByte());
        }
        for(int i=0;i<3;i++){
            buf.writeByte(i+1+100);
        }

        buf.discardReadBytes();

        for(int i=0;i<3;i++){
            buf.writeByte(i+1+30);
        }

        while(buf.isReadable()){
            System.out.println(buf.readByte());
        }*/


/*        // get a Charset of UTF-8
        Charset utf8 = Charset.forName("UTF-8");
        // get a ByteBuf
        ByteBuf buf1 = Unpooled.copiedBuffer("Netty in Action rocks!", utf8);
        // slice
        ByteBuf sliced = buf.slice(0, 14);
        // copy
        ByteBuf copy = buf1.copy(0, 14);
        // print "?Netty in Action rocks!?"
        System.out.println(buf.toString(utf8));
        // print "?Netty in Act"
        System.out.println(sliced.toString(utf8));
        // print "?Netty in Act"
        System.out.println(copy.toString(utf8));*/

        int val = 16;
        System.out.println(val & -val);


    }
}
