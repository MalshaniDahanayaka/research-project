package todoupdate.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Todo {

    private int id;
    private String title;
    private String description;
    private boolean completed;

    public Todo(int i, String title, String description, boolean completed) {
        this.id = i;
        this.title = title;
        this.description = description;
        this.completed = completed;
    }
}
