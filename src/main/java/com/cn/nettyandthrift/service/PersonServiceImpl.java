package com.cn.nettyandthrift.service;

import com.cn.nettyandthrift.thrift.generated.DataException;
import com.cn.nettyandthrift.thrift.generated.Person;
import com.cn.nettyandthrift.thrift.generated.PersonService;
import org.apache.thrift.TException;

public class PersonServiceImpl implements PersonService.Iface {
    @Override
    public Person getPersonByPersonName(String username) throws DataException, TException {
        System.out.println("got client person" + username);
        Person person = new Person();
        person.setAge(10);
        person.setMarried(false);
        person.setName("james");
        return person;
    }

    @Override
    public void PersonSave(Person person) throws DataException, TException {

        System.out.println("got client person" + person);
        System.out.println(person.getAge());
        System.out.println(person.getName());
        System.out.println(person.isMarried());

    }
}
