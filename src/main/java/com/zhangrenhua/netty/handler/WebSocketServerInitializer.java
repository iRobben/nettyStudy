package com.zhangrenhua.netty.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.ContinuationWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;

/**
 * @author zhangrenhua
 * @title  webSocket
 * @desc
 * @date 2019/4/28
 */


public class WebSocketServerInitializer extends ChannelInitializer<Channel>{
    @Override
    public void initChannel(Channel channel) throws Exception {
        channel.pipeline().addLast(
                new HttpServerCodec(),
                //用于提供在握手时聚合HttpRequest
                new HttpObjectAggregator(65536),
                //用于处理 如果请求到"/websocket"端点，当升级完成后，它将会处理Ping Pong Close
                new WebSocketServerProtocolHandler("/websocket"),
                new TextFrameHandler(),
                new BinaryFrameHandler(),
                new ContinuationFrameHandler());
    
    }
    
    public static final class TextFrameHandler extends SimpleChannelInboundHandler<TextWebSocketFrame>{
        @Override
        public void channelRead0(ChannelHandlerContext channelHandlerContext, TextWebSocketFrame textWebSocketFrame) throws Exception {
            //Handle text frame
        }
    }
    
    
    public static final class BinaryFrameHandler extends SimpleChannelInboundHandler<BinaryWebSocketFrame>{
    
        @Override
        public void channelRead0(ChannelHandlerContext channelHandlerContext, BinaryWebSocketFrame binaryWebSocketFrame) throws Exception {
            //Handle binary frame
        }
    }
    
    public static final class ContinuationFrameHandler extends SimpleChannelInboundHandler<ContinuationWebSocketFrame> {
    
        @Override
        public void channelRead0(ChannelHandlerContext channelHandlerContext, ContinuationWebSocketFrame continuationWebSocketFrame) throws Exception {
            //Handle continuation frame
        }
    }
}
