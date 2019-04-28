package com.zhangrenhua.netty.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/**
 * @author zhangrenhua
 * @title
 * @desc
 * @date 2019/4/28
 */


public class LengthBasesInitializer extends ChannelInitializer<Channel> {
    @Override
    protected void initChannel(Channel channel) throws Exception {
        channel.pipeline().addLast(
                new LengthFieldBasedFrameDecoder(65 * 1024 ,0 ,8),
                new FrameHandler()
                );
    
    }
    
    public static final class FrameHandler extends SimpleChannelInboundHandler<ByteBuf>{
    
        @Override
        protected void channelRead0(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) throws Exception {
        
        }
    }
}
