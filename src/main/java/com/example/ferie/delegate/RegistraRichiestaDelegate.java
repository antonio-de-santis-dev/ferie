package com.example.ferie.delegate;

import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class RegistraRichiestaDelegate implements JavaDelegate {

    public static final Logger log = LoggerFactory.getLogger(RegistraRichiestaDelegate.class);

    @Override
    public void execute(DelegateExecution execution) {


        String dipendente = (String)execution.getVariable("nomeDipendente");
        String dataInizio = (String)execution.getVariable("dataInizio");
        String dataFine = (String)execution.getVariable("dataFine");

        log.info("✅ REGISTRATA richiesta ferie");
        log.info("      Dipendente  : {}", dipendente);
        log.info("      Dal         : {}", dataInizio);
        log.info("      AL          : {}", dataFine);
        log.info("      ProcessId   : {}", execution.getProcessInstanceId() );

    }
}
