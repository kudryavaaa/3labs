package fotius.example.todo;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FileTodoListStorageIntegrationTest {

    private TodoListStorage todoListStorage;
    private Path testFilePath;

    @BeforeEach
    void beforeEach() throws IOException {
        testFilePath = Files.createTempFile("test-todo-list", ".txt");
        todoListStorage = new FileTodoListStorage(testFilePath);
    }

    @AfterEach
    void afterEach() throws IOException {
        Files.deleteIfExists(testFilePath);
    }

    @Test
    void saveAndLoadTodoList() throws StorageException {
        TodoList originalTodoList = new TodoList();
        originalTodoList.add("Task 1");
        originalTodoList.add("Task 2");
        originalTodoList.add("Task 3");

        todoListStorage.save(originalTodoList);

        TodoList loadedTodoList = todoListStorage.load();

        assertEquals(originalTodoList.items(), loadedTodoList.items());
    }

    @Test
    void loadNonExistentTodoList() throws StorageException, IOException {
        assertTrue(Files.deleteIfExists(testFilePath));

        TodoList loadedTodoList = todoListStorage.load();

        assertTrue(loadedTodoList.items().isEmpty());
    }

    @Test
    void handleIOExceptionDuringSave() {
        Path readOnlyPath = Paths.get("/read-only-path/todo.txt");
        TodoListStorage readOnlyStorage = new FileTodoListStorage(readOnlyPath);
        TodoList todoList = new TodoList();
        todoList.add("Test task");

        assertThrows(StorageException.class, () -> readOnlyStorage.save(todoList));
    }
}
