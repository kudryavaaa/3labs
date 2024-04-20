package fotius.example.todo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class TodoListTest {

    // EXAMPLE TEST
    // READS AS: Test that newly created list has no items in it
    @Test
    void newlyCreatedListHasNoItemsInIt() {
        TodoList list = new TodoList();

        Assertions.assertTrue(list.items().isEmpty());
    }

    @Test
    void addElementToTodoList(){
        TodoList list = new TodoList();

        list.add("First task");
        list.add("Second task");

        Assertions.assertEquals("First task", list.items().get(0));
        Assertions.assertEquals("Second task", list.items().get(1));
    }

    @Test
    void getItemsOfTodoList(){
        TodoList todoList = new TodoList(List.of("First task", "Second task"));

        final List<String> items = todoList.items();

        Assertions.assertEquals(List.of("First task", "Second task"), items);
    }

    @Test
    void deleteElementFromTodoList(){
        TodoList todoList = new TodoList(List.of("First task", "Second task"));

        boolean isDeleted = todoList.delete("First task");
        Assertions.assertTrue(isDeleted);
    }

    @Test
    void deleteInvalidElementFromTodoList(){
        TodoList todoList = new TodoList(List.of("First task", "Second task"));

        boolean isDeleted = todoList.delete("Third task");
        Assertions.assertFalse(isDeleted);
    }

    @Test
    void deleteElementAtIndex(){
        TodoList todoList = new TodoList(List.of("First task", "Second task"));

        todoList.deleteAt(0);

        Assertions.assertEquals(List.of("Second task"), todoList.items());
    }
}
