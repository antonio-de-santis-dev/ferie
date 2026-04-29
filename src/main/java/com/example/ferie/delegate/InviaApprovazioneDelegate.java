package com.example.ferie.delegate;

import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class InviaApprovazioneDelegate implements JavaDelegate {

    private final Logger log = LoggerFactory.getLogger(InviaApprovazioneDelegate.class);

    @Override
    public void execute(DelegateExecution execution) {
        String dipendente =(String) execution.getVariable("nomeDipendenti");
        String dataInizio =(String) execution.getVariable("dataInizio");
        String dataFine=(String) execution.getVariable("dataFine");

        log.info("✅ FERIE APPROVATE per: {}", dipendente);
        log.info("   Periodo: {} - {}", dataInizio, dataFine);
        log.info("   📧 Email di approvazione inviata!");

        execution.setVariable("stato", "APROVATO");
    }
}
