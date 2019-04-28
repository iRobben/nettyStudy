package com.zhangrenhua.netty.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpContentCompressor;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * @author zhangrenhua
 * @title  http 消息聚合 、压缩
 * @desc
 * @date 2019/4/28
 */


public class HttpAggregatorInitializer extends ChannelInitializer<Channel>{
    
    private final boolean client;
    
    
    public HttpAggregatorInitializer(boolean client) {
        this.client = client;
    }
    
    
    @Override
    protected void initChannel(Channel channel) throws Exception {
        ChannelPipeline pipeline = channel.pipeline();
        if(client){
            pipeline.addLast("codec",new HttpClientCodec());
            pipeline.addLast("decompressor",new HttpContentCompressor());
        }else{
            pipeline.addLast("codec",new HttpServerCodec());
            pipeline.addLast("compressor",new HttpContentCompressor());
        }
        //最大消息512kb
        pipeline.addLast("aggegator",new HttpObjectAggregator(512 * 1024));
        
    }
}
