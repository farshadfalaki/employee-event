package com.takeaway.eventservice.controller;

import com.takeaway.eventservice.model.Event;
import com.takeaway.eventservice.service.EventService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping(path = "/event")
@AllArgsConstructor
public class EventController {
    private final EventService eventService;

    @GetMapping("/list/{employeeId}")
    List<Event> getEmployeeEvents(@PathVariable String employeeId){
        return eventService.getEmployeeEvents(employeeId);
    }

    @PostMapping
    ResponseEntity<Event> create(@RequestBody Event event){
        return new ResponseEntity<>( eventService.create(event) , HttpStatus.NO_CONTENT);

    }

    @DeleteMapping("/all")
    ResponseEntity deleteAll(){
        eventService.deleteAll();
        return new ResponseEntity<>( HttpStatus.NO_CONTENT);
    }
}
