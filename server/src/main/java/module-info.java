module server {
    requires spring.boot.autoconfigure;
    requires spring.boot;
    requires spring.data.jpa;
    requires spring.tx;
    requires spring.context;
    requires lombok;
    requires spring.web;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.datatype.jsr310;
    requires spring.beans;
    requires spring.core;
    requires spring.jdbc;
    requires java.sql;
    requires jakarta.persistence;
    requires org.hibernate.orm.core;

    exports ite.jp.ak.lab03.server;
    exports ite.jp.ak.lab03.server.config;
    exports ite.jp.ak.lab03.server.web.dto;
    exports ite.jp.ak.lab03.server.web.controllers;
    exports ite.jp.ak.lab03.server.web.services;
    exports ite.jp.ak.lab03.server.model.enums;
    exports ite.jp.ak.lab03.server.model.entities;
    exports ite.jp.ak.lab03.server.model.repositories;

    opens ite.jp.ak.lab03.server to spring.core;
    opens ite.jp.ak.lab03.server.config to spring.core;
    opens ite.jp.ak.lab03.server.web.controllers to spring.beans, spring.web, spring.core;
    opens ite.jp.ak.lab03.server.web.services to spring.beans, spring.core;
    opens ite.jp.ak.lab03.server.model.entities to spring.core, org.hibernate.orm.core;

    uses org.springframework.boot.autoconfigure.SpringBootApplication;
}