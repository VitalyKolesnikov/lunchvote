package org.example.lunchvote;

import org.example.lunchvote.web.user.AdminRestController;
import org.springframework.context.support.GenericXmlApplicationContext;

public class Main {
    public static void main(String[] args) {
        try (GenericXmlApplicationContext appCtx = new GenericXmlApplicationContext()) {
            appCtx.getEnvironment().setActiveProfiles("postgres", "datajpa");
            appCtx.load("spring/spring-app.xml", "spring/spring-db.xml");
            appCtx.refresh();

            AdminRestController adminRestController = appCtx.getBean(AdminRestController.class);
            System.out.println(adminRestController.get(123));
        }
    }
}