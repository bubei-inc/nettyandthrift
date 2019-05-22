package com.cn.nettyandthrift.client;

import com.cn.nettyandthrift.thrift.generated.Person;
import com.cn.nettyandthrift.thrift.generated.PersonService;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

public class ThriftClient {
    public static void main(String[] args) {
        TTransport tTransport = new TFramedTransport(new TSocket("localhost", 8200), 600);
        TProtocol tProtocol = new TCompactProtocol(tTransport);
        PersonService.Client client = new PersonService.Client(tProtocol);
        try{
            tTransport.open();
            Person person = client.getPersonByPersonName("james");
            System.out.println(person);

            Person per = new Person();
            per.setName("sophia");
            per.setAge(22);
            per.setMarried(true);
            client.PersonSave(per);

        }catch (Exception e) {
            throw new RuntimeException(e.getMessage(),e);

        }finally {
            tTransport.close();
        }
    }
}