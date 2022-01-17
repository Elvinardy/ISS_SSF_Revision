package ssf.todoList.TodoList_project.controller;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import ssf.todoList.TodoList_project.Constants;
import ssf.todoList.TodoList_project.service.TaskService;


@Controller
@RequestMapping(path="/task", produces = MediaType.TEXT_HTML_VALUE)
public class TodoController {
    private final static Logger logger = Logger.getLogger(TodoController.class.getName());

    @Autowired 
    TaskService tasksvc;        // injecting taskservice bean
/* 
    @RequestMapping(value="/login", method = RequestMethod.GET)    // To add new login page to check user todo list is already present
    public String showLoginPage(Model model) {

        return "userlogin";
    } */
    // when requestmapping is used above, no need to include '/' at the Post mapping
    @PostMapping("save")
    // appropriate to use multivaluemap if theres alot of inputs from the form
    public String postTaskSave(@RequestBody MultiValueMap<String, String> form) {  
        String contents = form.getFirst("contents");

        logger.log(Level.INFO, "to be saved: %s".formatted(contents));

        tasksvc.save(Constants.TODO_KEY, contents);

        return "index";
    }

    @PostMapping
    public String postTask (@RequestBody MultiValueMap<String, String> todoList, Model model) {

        String todo = todoList.getFirst("taskName");
        String contents = todoList.getFirst("contents");

        logger.log(Level.INFO, "contents: '%s'".formatted(contents));
        logger.log(Level.INFO, "task: '%s'".formatted(todo));

        // Split the contents into List, delimited by "|"
        List<String> taskList = new LinkedList<>();
        if((contents != null) && (contents.trim().length() > 0)) {
            // append new task to contents
            contents = "%s|%s".formatted(contents, todo);
            taskList = Arrays.asList(contents.split("\\|"));

        } else {
            contents = todo;
            taskList.add(contents);
        }

        logger.log(Level.INFO, "taskName: %s".formatted(todo));
        logger.log(Level.INFO, "tasks: %s".formatted(taskList));
        model.addAttribute("contents", contents);
        model.addAttribute("tasks", taskList);
        
        return "index";
    }

}
