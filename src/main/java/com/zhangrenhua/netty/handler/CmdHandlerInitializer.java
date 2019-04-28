package com.zhangrenhua.netty.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.LineBasedFrameDecoder;

/**
 * @author zhangrenhua
 * @title  分割符
 * @desc
 * @date 2019/4/28
 */


public class CmdHandlerInitializer extends ChannelInitializer<Channel> {
    
    @Override
    protected void initChannel(Channel channel) throws Exception {
        channel.pipeline().addLast(new CmdDecoder(65 * 1024),
                new CmdHandler());
    
    }
    
    public static final class Cmd{
        private final ByteBuf name;
        private final ByteBuf args;
    
        public Cmd(ByteBuf name, ByteBuf args) {
            this.name = name;
            this.args = args;
        }
    
        public ByteBuf getName() {
            return name;
        }
    
        public ByteBuf getArgs() {
            return args;
        }
    }
    
    public static final class CmdDecoder extends LineBasedFrameDecoder{
    
        public CmdDecoder(int maxLength) {
            super(maxLength);
        }
    
        @Override
        protected Object decode(ChannelHandlerContext ctx, ByteBuf buffer) throws Exception {
            ByteBuf frame = (ByteBuf)super.decode(ctx,buffer);
            if(frame == null){
                return  null;
            }
            int index = frame.indexOf(frame.readerIndex(),frame.writerIndex(),(byte)' ');
            return new Cmd(frame.slice(frame.readerIndex(),index),frame.slice(index + 1,frame.writerIndex()));
        }
    }
    
    public static final class CmdHandler extends SimpleChannelInboundHandler<Cmd>{
    
        @Override
        protected void channelRead0(ChannelHandlerContext channelHandlerContext, Cmd cmd) throws Exception {
            //todo
        }
    }
}
