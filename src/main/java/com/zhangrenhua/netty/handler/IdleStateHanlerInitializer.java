package com.zhangrenhua.netty.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.CharsetUtil;

import java.util.concurrent.TimeUnit;

/**
 * @author zhangrenhua
 * @title  空闲连接以及超时 （心跳检测）
 * @desc
 * @date 2019/4/28
 */


public class IdleStateHanlerInitializer extends ChannelInitializer<Channel> {
    
    @Override
    public void initChannel(Channel channel) throws Exception {
        channel.pipeline().addLast(
                new IdleStateHandler(0,0,60, TimeUnit.SECONDS),
                new HeartbeatHandler());
    
    }
    
    public static final class HeartbeatHandler extends ChannelInboundHandlerAdapter{
        
        private static final ByteBuf HAERTBEAT_SEQUENCE = Unpooled.unreleasableBuffer(
                Unpooled.copiedBuffer("HEARTBEAT", CharsetUtil.ISO_8859_1));
    
        @Override
        public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
            if(evt instanceof IdleStateEvent){
                ctx.writeAndFlush(HAERTBEAT_SEQUENCE.duplicate())
                        .addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
            }else {
                super.userEventTriggered(ctx, evt);
            }
        }
    }
}
