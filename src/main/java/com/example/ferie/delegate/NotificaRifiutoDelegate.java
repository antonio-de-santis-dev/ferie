package com.example.ferie.delegate;

import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class NotificaRifiutoDelegate implements JavaDelegate {

    private final Logger log = LoggerFactory.getLogger(NotificaRifiutoDelegate.class);

    @Override
    public void execute(DelegateExecution execution) {
        String dipendente = (String) execution.getVariable("dipendenti");
        String motivazione = (String) execution.getVariable("motivazione");

        log.info("❌ FERIE RIFIUTATE per: {}", dipendente);
        log.info("   Motivazione: {}", motivazione != null ? motivazione : "non specificata");
        log.info("   📧 Email di rifiuto inviata!");

        execution.setVariable("stato", "RIFIUTATO");
    }
}
