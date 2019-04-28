package com.zhangrenhua.netty.echo;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;

/**
 * @author zhangrenhua
 * @title
 * @desc
 * @date 2019/4/22
 */


public class EchoServer {
    
    private final int port;
    
    public EchoServer(int port){
        this.port = port;
    }
    
    
    public void start() throws Exception {
        NioEventLoopGroup group = new NioEventLoopGroup();
        try {
            ServerBootstrap sb = new ServerBootstrap();
            sb.group(group)
              .channel(NioServerSocketChannel.class)
              .localAddress(new InetSocketAddress(port))
              .childHandler(new ChannelInitializer<SocketChannel>() {
                  @Override
                  protected void initChannel(SocketChannel socketChannel) throws Exception {
                      socketChannel.pipeline().addLast(new EchoServerHandler());
                  }
              });
    
            ChannelFuture f = sb.bind().sync();
            System.out.println(EchoServer.class.getName() + "started and listen on " + f.channel().localAddress() );
            f.channel().closeFuture().sync();
        }finally {
            group.shutdownGracefully().sync();
        }
    }
    
    public static void main(String[] args) throws Exception {
     
        System.err.println("Usage :" + EchoServer.class.getSimpleName() + "8999");
        new EchoServer(8999).start();
        
    }
}
