package com.zhangrenhua.netty.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslHandler;

import javax.net.ssl.SSLEngine;

/**
 * @author zhangrenhua
 * @title  https
 * @desc
 * @date 2019/4/28
 */


public class HttpsCodecInitializer extends ChannelInitializer<Channel>{
    
    private final SslContext sslContext;
    
    private final boolean client;
    
    public HttpsCodecInitializer(SslContext sslContext, boolean client) {
        this.sslContext = sslContext;
        this.client = client;
    }
    
    @Override
    protected void initChannel(Channel channel) throws Exception {
        ChannelPipeline pipeline = channel.pipeline();
        SSLEngine engine = sslContext.newEngine(channel.alloc());
        pipeline.addLast("ssl",new SslHandler(engine));
        if(client){
            pipeline.addLast("codec",new HttpClientCodec());
        } else {
            pipeline.addLast("codec",new HttpServerCodec());
        }
    }
}
