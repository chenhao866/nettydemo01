package server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

public class serverHandler extends ChannelInboundHandlerAdapter {

    //声明一个通道组，用来保存所有的连接通道
    private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    // 当客户端主动链接服务端的链接后执行
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().localAddress().toString() + "已连接");
        //对所有连接当前服务器的客户端通道发送信息
        channelGroup.writeAndFlush(ctx.channel().localAddress()+" Yi Shang Xian");
        //将当前连接通道添加到通道组 ps:如果客户端通道断开，Netty会自动移除
        channelGroup.add(ctx.channel());
        super.channelActive(ctx);
    }
    // 当客户端主动断开服务端的链接后执行
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().localAddress().toString() + "已断开");
        //关闭流
        ctx.close();
        super.channelInactive(ctx);
    }
    //读取到客户端发送过来信息时执行
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // 第一种：接收字符串时的处理
        ByteBuf buf = (ByteBuf) msg;
        byte[] buffer = new byte[buf.readableBytes()];
        buf.readBytes(buffer, 0, buffer.length);
        String rev = new String(buffer);
        System.out.println("服务器收到客户端数据:" + rev);
       // ctx.channel().writeAndFlush("ok");//返回客户端数据
        super.channelRead(ctx, msg);
    }
    //读取完毕客户端发送过来的数据之后执行
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        System.out.println("服务端接收数据完毕..");
        // 第一种方法：写一个空的buf，并刷新写出区域。完成后关闭sock channel连接。
        // ctx.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
        // ctx.flush();
        // ctx.flush(); //
        // 第二种方法：在client端关闭channel连接，这样的话，会触发两次channelReadComplete方法。
        // ctx.flush().close().sync(); // 第三种：改成这种写法也可以，但是这中写法，没有第一种方法的好。
        super.channelReadComplete(ctx);
    }
    //服务端发生异常时执行
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("异常信息");
        cause.printStackTrace();
        ctx.close();
        super.exceptionCaught(ctx, cause);
    }


}
