package server;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.string.StringEncoder;
import java.nio.charset.Charset;
public class serverInitializer extends ChannelInitializer<SocketChannel> {
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        System.out.println("客户端触发操作");
        socketChannel.pipeline().addLast(new StringEncoder(Charset.forName("GBK")));
        //socketChannel.pipeline().addLast(new StringEncoder(Charset.forName("UTF-8")));
        socketChannel.pipeline().addLast(new serverHandler()); // 客户端触发操作,处理客户端回调
    }
}
