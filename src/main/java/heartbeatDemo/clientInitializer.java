package heartbeatDemo;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.string.StringEncoder;

import java.nio.charset.Charset;

public class clientInitializer extends ChannelInitializer<SocketChannel> {
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        socketChannel.pipeline().addLast(new StringEncoder(Charset.forName("GBK")));
        //socketChannel.pipeline().addLast(new StringEncoder(Charset.forName("UTF-8")));
        //添加心跳空闲检查Handler，5：表示5秒没有读操作，7：表示7秒没有写操作，10：表示10秒没有读写操作
        socketChannel.pipeline().addLast(new clientHandler()); // 客户端触发操作,处理客户端回调
    }
}
