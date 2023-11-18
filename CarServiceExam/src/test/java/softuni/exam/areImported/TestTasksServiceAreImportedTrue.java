package softuni.exam.areImported;
//TestTasksServiceAreImportedTrue

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import softuni.exam.repository.TasksRepository;
import softuni.exam.service.impl.TasksServiceImpl;

@ExtendWith(MockitoExtension.class)
public class TestTasksServiceAreImportedTrue {

    @InjectMocks
    private TasksServiceImpl tasksService;
    @Mock
    private TasksRepository mockTasksRepository;

    @Test
    void areImportedShouldReturnTrue() {
        Mockito.when(mockTasksRepository.count()).thenReturn(1L);
        Assertions.assertTrue(tasksService.areImported());
    }
}