package com.cn.nettyandthrift.server;

import com.cn.nettyandthrift.service.PersonServiceImpl;
import com.cn.nettyandthrift.thrift.generated.PersonService;
import org.apache.thrift.TProcessorFactory;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.server.THsHaServer;
import org.apache.thrift.server.TServer;
import org.apache.thrift.transport.TFastFramedTransport;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.transport.TTransportException;


public class ThriftServer  {
    public static void main(String[] args) throws TTransportException {

//        Thrift常见的支持的服务模型  TSimpleServer 简单的单线程服务 一般用于测试， TNonblockingServerSocket 多线程服务，使用非阻塞I/O（需要使用TFrameTransport进行传输）
//        TThreadPoolSever 多线程模型使用标准的阻塞式I/O模型 THsHaServer引入了线程池去处理，把读写任务放到了线程池中去处理half-sync() half-async(),half-async()用于处理I/O事件上的（accept,read, write io）
//        half-sync() 用于handler对rpc 同步处理

//        1.创建连接方式
        TNonblockingServerSocket socket = new TNonblockingServerSocket(8200);
        THsHaServer.Args arg = new THsHaServer.Args(socket).minWorkerThreads(2).maxWorkerThreads(4);
        PersonService.Processor<PersonServiceImpl> personServiceProcessor = new PersonService.Processor<>(new PersonServiceImpl());
//        双方需要协议号调用一样的协议层 传输层
//        常见的传输格式有TBinaryProtocol  二进制传输格式， TCompactProtocol 压缩的传输格式 ， TJSONProtocol JSON格式， TSimpleJSONProtocol生成json只写协议
//        生成的文件 很容易被脚本语言解析不常用，TDebugProtocol使用易懂的可读文件，方便测试
        arg.protocolFactory(new TCompactProtocol.Factory());

//        传输方式 TSocket 阻塞式   TFramedTransport 以frame格式进行传输 非阻塞服务中使用  TFileTransport 以文件形式进行传输，
//        TMemoryTransport将内存英用于I/O,java实现实际上使用了简单的ByteArrayOutputStream  TZlibTransport 使用与zlib进行压缩，与其他传输方式联合使用，当前无Java实现
        arg.transportFactory(new TFastFramedTransport.Factory());
        arg.processorFactory(new TProcessorFactory(personServiceProcessor));
        TServer thriftServer  = new THsHaServer(arg);
        System.out.println("thrift server started ");
        thriftServer.serve();
    }
}
