package com.zhangrenhua.netty.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.LineBasedFrameDecoder;

/**
 * @author zhangrenhua
 * @title  解码分隔符
 * @desc
 * @date 2019/4/28
 */


public class LineBaseHandlerInitializer extends ChannelInitializer<Channel> {
    
    @Override
    protected void initChannel(Channel channel) throws Exception {
        channel.pipeline().addLast(
                new LineBasedFrameDecoder(65 * 1024),
                new FrameHandler());
    }
    
    public static final class FrameHandler extends SimpleChannelInboundHandler{
    
        @Override
        protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object o) throws Exception {
        
            //todo
        }
    }
}
