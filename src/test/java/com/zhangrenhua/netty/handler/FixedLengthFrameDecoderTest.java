package com.zhangrenhua.netty.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author zhangrenhua
 * @title
 * @desc
 * @date 2019/5/2
 */


public class FixedLengthFrameDecoderTest {
    
    @Test
    public void decode1() throws Exception {
        ByteBuf buf = Unpooled.buffer();
        for(int i = 0 ; i < 9 ; i++){
            buf.writeByte(i);
        }
        ByteBuf input = buf.duplicate();
        EmbeddedChannel channel = new EmbeddedChannel(new FixedLengthFrameDecoder(3));
    
        Assert.assertFalse(channel.writeInbound(input.readBytes(2)));
        Assert.assertTrue(channel.writeInbound(input.readBytes(7)));
    
        Assert.assertTrue(channel.finish());
        ByteBuf read = (ByteBuf)channel.readInbound();
        Assert.assertEquals(buf.readSlice(3),read);
        read.release();
    
        read = (ByteBuf)channel.readInbound();
        Assert.assertEquals(buf.readSlice(3),read);
        read.release();
    
        read = (ByteBuf)channel.readInbound();
        Assert.assertEquals(buf.readSlice(3),read);
        read.release();
        
        Assert.assertNull(channel.readInbound());
        buf.release();
    }
    
}