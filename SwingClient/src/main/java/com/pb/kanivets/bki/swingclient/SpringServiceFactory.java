package com.pb.kanivets.bki.swingclient;

import com.pb.kanivets.bki.core.services.IServiceFactory;
import com.pb.kanivets.bki.core.services.IBankService;
import com.pb.kanivets.bki.core.services.IClientService;
import com.pb.kanivets.bki.core.services.ICreditService;
import com.pb.kanivets.bki.core.services.ICurrencyService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class SpringServiceFactory implements IServiceFactory {

    ApplicationContext context;

    public SpringServiceFactory() {
        context = new AnnotationConfigApplicationContext("com.pb.kanivets.bki.spring");
    }

    @Override
    public IClientService getClientService(){
        return context.getBean(IClientService.class);
    }
    @Override
    public ICreditService getCreditService(){
        return context.getBean(ICreditService.class);
    }
    @Override
    public IBankService getBankService(){
        return context.getBean(IBankService.class);
    }
    @Override
    public ICurrencyService getCurrencyService(){
        return context.getBean(ICurrencyService.class);
    }
    
}
