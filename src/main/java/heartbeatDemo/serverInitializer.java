package heartbeatDemo;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;

import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

public class serverInitializer extends ChannelInitializer<SocketChannel> {
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();
        pipeline.addLast(new StringEncoder(Charset.forName("GBK")));
        //心跳超时事件，1：读超时2：写超时3：读写超时
        socketChannel.pipeline().addLast(new IdleStateHandler(15,0,0, TimeUnit.SECONDS));
        pipeline.addLast(new serverHandler());
    }
}
