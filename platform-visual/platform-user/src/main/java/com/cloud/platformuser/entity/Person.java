package com.cloud.platformuser.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.ldap.odm.annotations.Attribute;
import org.springframework.ldap.odm.annotations.Entry;
import org.springframework.ldap.odm.annotations.Id;
import org.springframework.ldap.support.LdapNameBuilder;

import javax.naming.Name;

/**
 * @Classname Person
 * @Description <h1>LDAP 用户实体</h1>
 * @Author yulj
 * @Date: 2020/04/22 10:18 上午
 */
@Data
@Entry(objectClasses = {"posixAccount", "top", "inetOrgPerson", "shadowAccount"}, base = "ou=People")
public class Person {

    @Id
    @JsonIgnore
    private Name dn;

    @Attribute(name = "cn")
    private String cn;

    @Attribute(name = "gidNumber")
    private Integer gidNumber;

    @Attribute(name = "uidNumber")
    private Integer uidNumber;

    @Attribute(name = "sn")
    private String sn;

    @Attribute(name = "homeDirectory")
    private String homeDirectory;

    @Attribute(name = "userPassword")
    private String userPassword;

    public Person() {
    }

    public Person(String cn) {
        Name dn = LdapNameBuilder.newInstance()
                .add("ou", "People")
                .add("uid", cn)
                .build();
        this.dn = dn;
    }
}
