package com.zhangrenhua.netty.sj.httpServer;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;

/**
 * @author zhangrenhua
 * @title
 * @desc
 * @date 2019/10/8
 */


public class HttpServer {
    
    private void openServer(){
    
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.channel(NioServerSocketChannel.class);
    
        NioEventLoopGroup boot = new NioEventLoopGroup(1);
        NioEventLoopGroup workers = new NioEventLoopGroup(8);
        bootstrap.group(boot,workers);
        
        bootstrap.childHandler(new ChannelInitializer() {
            @Override
            protected void initChannel(Channel channel) throws Exception {
                channel.pipeline().addLast("http-decode",new HttpRequestDecoder());
                channel.pipeline().addLast("http-encode",new HttpResponseEncoder());
                channel.pipeline().addLast("http-server-handler",new MyHttpServerHandler());
        
            }
        });
        try {
            ChannelFuture future  = bootstrap.bind(8080).sync();
            System.out.printf("服务启动成功，端口8080..");
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            boot.shutdownGracefully();
            workers.shutdownGracefully();
        }
    }
    
    private static class MyHttpServerHandler extends SimpleChannelInboundHandler{
    
        @Override
        protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object o) throws Exception {
    
            DefaultFullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK);
            response.headers().add(HttpHeaderNames.CONTENT_TYPE, "text/html;charset=utf-8");
    
            String html = "<!DOCTYPE html>\n" +
                    "<html lang=\"en\">\n" +
                    "<head>\n" +
                    "    <meta charset=\"UTF-8\">\n" +
                    "    <title>hello word</title>\n" +
                    "</head>\n" +
                    "<body>\n" +
                    "hello zrh\n" +
                    "</body>\n" +
                    "</html>";
            response.content().writeBytes(html.getBytes("UTF-8"));
            channelHandlerContext.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
        }
    }
    
    public static void main(String[] args) {
        HttpServer httpServer = new HttpServer();
        httpServer.openServer();
    
    }
}
