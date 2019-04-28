package com.zhangrenhua.netty.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslHandler;

import javax.net.ssl.SSLEngine;

/**
 * @author zhangrenhua
 * @title ssl/tls 加密netty程序
 * @desc
 * @date 2019/4/27
 */


public class SslChannelInitializer extends ChannelInitializer<Channel>{
   
   private final SslContext context;
   
   private final boolean client;
   
   private final boolean startTls;
   
   public SslChannelInitializer(SslContext context,boolean client, boolean startTls){
       this.context = context;
       this.client = client;
       this.startTls = startTls;
   }
    
   
    @Override
    protected void initChannel(Channel channel) throws Exception {
        SSLEngine engine = context.newEngine(channel.alloc());
        engine.setUseClientMode(client);
        //添加SslHandler到pipeline作为第一个处理器
        channel.pipeline().addFirst("ssl",new SslHandler(engine,startTls));
    }
}
