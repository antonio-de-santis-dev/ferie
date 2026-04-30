package com.example.ferie.controller;

import com.example.ferie.servis.dto.RichiestaFerieDTO;

import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/ferie")
public class FerieController {


    @Autowired
    private   RuntimeService runtimeService;
    @Autowired
    private   TaskService taskService;

    private RichiestaFerieDTO Rdto;


    @PutMapping("/richiesta") //invio richiesta ferie
    public ResponseEntity<Map<String, Object>> inviRichiesta(
            @RequestBody RichiestaFerieDTO dto
            ){
        //variabiili del percorso
        Map<String, Object> variabili = new HashMap<>();
        variabili.put("nomeDipendente", dto.getNomeDipendente());
        variabili.put("dataInizio", dto.getDataInizio());
        variabili.put("dataFine", dto.getDataFine());


        //Avio processo Flowable , richiesta-ferie = id nel PMN
        ProcessInstance instance = runtimeService.startProcessInstanceByKey("richiesta-ferie", variabili);

        Map<String, Object> risposta = new HashMap<>();
        risposta.put("processInstanceId",   instance.getId());
        risposta.put("stato",               "AVVVIATO");
        risposta.put("dipendente",          dto.getNomeDipendente());

        return ResponseEntity.ok(risposta);
    }


    @GetMapping("/taskList") //Visualizazione Richieste  ricevute
    public ResponseEntity<?> getTaskList(){

        var tasks = taskService.createTaskQuery()
                .taskAssignee("manager")
                .includeProcessVariables()
                .list();

        var risposta = tasks.stream().map(t ->{
            Map<String, Object> m = new HashMap<>();
            m.put("taskId",             t.getId());
            m.put("nome",               t.getName());
            m.put("percorsoInstanceId", t.getProcessInstanceId());
            m.put("dataCreazione",      t.getCreateTime());
            Map<String, Object> variabili = t.getProcessVariables();
            m.put("nomeDipendente", variabili.get("nomeDipendente"));
            m.put("Data", variabili.get("dataInizio") + " | " + variabili.get("dataFine"));
            return m;
        }).toList();

        return ResponseEntity.ok(risposta);
    }




    @PostMapping("tasks/{taskId}/completa")
    public ResponseEntity<?> completaTask(
            @PathVariable String taskId,
            @RequestBody Map<String, Object> body
    ){

        Map<String, Object> variabili = new HashMap<>();
        variabili.put("decisione", body.get("decisione"));
        variabili.put("motivazione", body.get("motivazione"));

        taskService.complete(taskId, variabili);


        Map<String, Object> risposta = new HashMap<>();
        risposta.put("taskId",   taskId);
        risposta.put("decisione", variabili.get("decisione"));
        risposta.put("stato", "TASK COMPLLETATO - proceso ripreso");

        return ResponseEntity.ok(risposta);

    }

}
