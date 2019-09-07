package heartbeatDemo;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import io.netty.util.concurrent.ScheduledFuture;

import java.util.concurrent.TimeUnit;

public class clientHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = (ByteBuf) msg;
        byte[] buffer = new byte[buf.readableBytes()];
        buf.readBytes(buffer, 0, buffer.length);
        String rev =new String( buffer);
        System.out.println("客户端收到数据:" + rev);
        super.channelRead(ctx, msg);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ping(ctx.channel());
        super.channelActive(ctx);
    }

    private void ping(final Channel channel) {
        ScheduledFuture<?> future = channel.eventLoop().schedule(new Runnable() {
            public void run() {
                if (channel.isActive()) {
                    channel.writeAndFlush("Xin Tiao");
                } else {
                    channel.closeFuture();
                    throw new RuntimeException();
                }
            }
        }, 5, TimeUnit.SECONDS);

        future.addListener(new GenericFutureListener() {
            public void operationComplete(Future future) throws Exception {
                if (future.isSuccess()) {
                    ping(channel);
                }
            }
        });
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        // 当Channel已经断开的情况下, 仍然发送数据, 会抛异常, 该方法会被调用.
        cause.printStackTrace();
        ctx.close();
        super.exceptionCaught(ctx, cause);
    }
}
