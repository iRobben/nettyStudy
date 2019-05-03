package com.zhangrenhua.netty.websocket;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.group.ChannelGroup;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.util.SelfSignedCertificate;

import java.net.InetSocketAddress;

/**
 * @author zhangrenhua
 * @title
 * @desc
 * @date 2019/5/3
 */


public class SecureChatServer extends ChatServer {
    
    private final SslContext context;
    
    public SecureChatServer(SslContext context) {
        this.context = context;
    }
    
    @Override
    protected ChannelInitializer<Channel> createInitializer(ChannelGroup group) {
        return new SecureChatServerInitializer(group,context);
    }
    
    public static void main(String[] args) throws Exception {
    
        SelfSignedCertificate certificate = new SelfSignedCertificate();
        SslContext sslContext = SslContext.newServerContext(certificate.certificate(), certificate.privateKey());
        final  SecureChatServer endpoint = new SecureChatServer(sslContext);
        ChannelFuture future = endpoint.start(new InetSocketAddress(9999));
        Runtime.getRuntime().addShutdownHook(new Thread(){
            @Override
            public void run() {
                endpoint.destroy();
            }
        });
        future.channel().closeFuture().syncUninterruptibly();
    }
}
