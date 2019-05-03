package com.zhangrenhua.netty.websocket;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.group.ChannelGroup;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * @author zhangrenhua
 * @title 初始化ChannelPipeline
 * @desc
 * @date 2019/5/3
 */


public class ChatServerInitializer extends ChannelInitializer<Channel> {
    
    private final ChannelGroup group;
    
    public ChatServerInitializer(ChannelGroup group) {
        this.group = group;
    }
    
    @Override
    protected void initChannel(Channel channel) throws Exception {
        channel.pipeline().addLast(
                new HttpServerCodec(),
                new HttpObjectAggregator(64 * 1024),
                new ChunkedWriteHandler(),
                new HttpRequestHandler("/ws"),
                new WebSocketServerProtocolHandler("/ws"),
                new TextWebSocketFrameHandler(group)
        );
    
    }
}
