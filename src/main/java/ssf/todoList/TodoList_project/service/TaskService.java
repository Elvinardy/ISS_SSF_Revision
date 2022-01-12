package ssf.todoList.TodoList_project.service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import ssf.todoList.TodoList_project.repository.TaskRepo;

public class TaskService {
    
    @Autowired
    private TaskRepo taskRepo;

    public boolean hasKey(String key) {
        Optional<String> opt = taskRepo.get(key);
        return opt.isPresent();
    }

    public List<String> get(String key) {
        Optional<String> opt = taskRepo.get(key);
        List<String> list = new LinkedList<>();
        if(opt.isPresent()) {
            for (String c : opt.get().split("\\|")) {
                list.add(c);    
            }
        }  
            return list;
    }


    public void save(String key, List<String> values) {
        String l = values.stream()
                .collect(Collectors.joining("|"));
            this.save(key, l);

    }

    public void save(String key, String value) {
        taskRepo.save(key, value);
    }

    public boolean validateUser(String userId, String password) {
        return userId.equalsIgnoreCase("fred");
    }
}
