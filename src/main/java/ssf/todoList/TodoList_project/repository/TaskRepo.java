package ssf.todoList.TodoList_project.repository;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import ssf.todoList.TodoList_project.Constants;

@Repository
public class TaskRepo {
    
    @Autowired
    @Qualifier(Constants.TODO_REDIS)
    private RedisTemplate<String, String> template;

    public void save(String key, String value) {
        template.opsForValue().set(key, value, 5, TimeUnit.MINUTES);

    }
    public Optional<String> get(String key) {
        return Optional.ofNullable(template.opsForValue().get(key));  // Getting the key

    }
}
