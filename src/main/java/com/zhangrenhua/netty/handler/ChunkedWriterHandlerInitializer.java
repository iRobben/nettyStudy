package com.zhangrenhua.netty.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslHandler;
import io.netty.handler.stream.ChunkedStream;
import io.netty.handler.stream.ChunkedWriteHandler;

import java.io.File;
import java.io.FileInputStream;

/**
 * @author zhangrenhua
 * @title  编写大型数据
 * @desc
 * @date 2019/4/29
 */


public class ChunkedWriterHandlerInitializer extends ChannelInitializer<Channel>{
    
    private final File file;
    
    private final SslContext sslCtx;
    
    public ChunkedWriterHandlerInitializer(File file, SslContext sslCtx) {
        this.file = file;
        this.sslCtx = sslCtx;
    }
    
    @Override
    protected void initChannel(Channel channel) throws Exception {
        channel.pipeline().addLast(
                new SslHandler(sslCtx.newEngine(channel.alloc())),
                new ChunkedWriteHandler(),
                new WriteStreamHandler()
        );
    
    }
    
    public final class WriteStreamHandler extends ChannelInboundHandlerAdapter{
        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            super.channelActive(ctx);
            ctx.writeAndFlush(new ChunkedStream(new FileInputStream(file)));
        }
    }
}
