package com.example.ferie.delegate;

import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class InviaNotificaDelegate implements JavaDelegate {

    public static final Logger log = LoggerFactory.getLogger(InviaNotificaDelegate.class);

    @Override
    public void execute(DelegateExecution execution) {

        String dipenddente =  (String) execution.getVariable("nomeDipendente");

        log.info("\"\uD83D\uDCE7 NOTIFICA INVIATA a: {} " + dipenddente);
        log.info("  Messaggio: 'La tua richiesta di ferie è stata ricevuta.'");
        log.info("  Processo completato: {}", execution.getProcessDefinitionId());

    }
}
