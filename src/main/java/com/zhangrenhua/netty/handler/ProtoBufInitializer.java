package com.zhangrenhua.netty.handler;

import com.google.protobuf.MessageLite;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;

/**
 * @author zhangrenhua
 * @title 序列化
 * @desc
 * @date 2019/4/29
 */


public class ProtoBufInitializer extends ChannelInitializer<Channel> {
    
    private final MessageLite lite;
    
    public ProtoBufInitializer(MessageLite lite) {
        this.lite = lite;
    }
    
    @Override
    protected void initChannel(Channel channel) throws Exception {
        channel.pipeline().addLast(
                //用来分割帧
                new ProtobufVarint32FrameDecoder(),
                //处理消息的编码
                new ProtobufEncoder(),
                //处理消息的解码
                new ProtobufDecoder(lite),
                //用于处理解码了的消息
                new ObjectHandler()
        );
    }
    
    public static final class ObjectHandler extends SimpleChannelInboundHandler<Object>{
    
        @Override
        protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object o) throws Exception {
            //todo
        }
    }
}
