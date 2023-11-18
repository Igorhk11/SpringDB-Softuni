package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;;
import softuni.exam.models.dto.TaskDto;
import softuni.exam.models.dto.TasksRootSeedDto;
import softuni.exam.models.entity.Car;
import softuni.exam.models.entity.Mechanic;
import softuni.exam.models.entity.Part;
import softuni.exam.models.entity.Task;
import softuni.exam.models.entity.enums.CarType;
import softuni.exam.repository.CarsRepository;
import softuni.exam.repository.MechanicsRepository;
import softuni.exam.repository.PartsRepository;
import softuni.exam.repository.TasksRepository;
import softuni.exam.service.TasksService;
import softuni.exam.util.ValidationUtil;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TasksServiceImpl implements TasksService {
    private static String TASKS_FILE_PATH = "src/main/resources/files/xml/tasks.xml";
    private TasksRepository taskRepository;
    private ModelMapper modelMapper;
    private XmlParser xmlParser;
    private ValidationUtil validationUtil;
    private MechanicsRepository mechanicsRepository;
    private CarsRepository carsRepository;
    private PartsRepository partsRepository;

    public TasksServiceImpl(TasksRepository taskRepository, ModelMapper modelMapper, XmlParser xmlParser, ValidationUtil validationUtil, MechanicsRepository mechanicsRepository, CarsRepository carsRepository, PartsRepository partsRepository) {
        this.taskRepository = taskRepository;
        this.modelMapper = modelMapper;
        this.xmlParser = xmlParser;
        this.validationUtil = validationUtil;
        this.mechanicsRepository = mechanicsRepository;
        this.carsRepository = carsRepository;
        this.partsRepository = partsRepository;
    }

    @Override
    public boolean areImported() {
        return this.taskRepository.count() > 0;
    }

    @Override
    public String readTasksFileContent() throws IOException {
        return Files.readString(Path.of(TASKS_FILE_PATH));
    }

    @Override
    public String importTasks() throws IOException, JAXBException {
        StringBuilder sb = new StringBuilder();

        TasksRootSeedDto tasksRootSeedDto = xmlParser.fromFile(TASKS_FILE_PATH, TasksRootSeedDto.class);

        tasksRootSeedDto.getTasks().stream().filter(tasksSeedDTO -> {
            boolean isValid = validationUtil.isValid(tasksSeedDTO);

            Optional<Mechanic> findByFirstName = mechanicsRepository.findByFirstName(tasksSeedDTO.getMechanic().getFirstName());

            if (findByFirstName.isEmpty()) {
                isValid = false;
            }

            sb.
                    append(isValid ? String.format("Successfully imported task %.2f",tasksSeedDTO.getPrice())
                            : "Invalid task").append(System.lineSeparator());


            return isValid;

        }).map(tasksSeedDTO -> {
            Task task = modelMapper.map(tasksSeedDTO, Task.class);

            Optional<Mechanic> mechanic = this.mechanicsRepository
                    .findByFirstName(tasksSeedDTO.getMechanic().getFirstName());

            Optional<Car> car = this.carsRepository
                    .findById(tasksSeedDTO.getCar().getId());

            Optional<Part> part = this.partsRepository.findById(tasksSeedDTO.getPart().getId());

            task.setCar(car.get());
            task.setMechanic(mechanic.get());
            task.setPart(part.get());

            return task;

        }).forEach(taskRepository::save);

        return sb.toString().trim();
    }

    @Override
    public String getCoupeCarTasksOrderByPrice() {
//        StringBuilder sb = new StringBuilder();
//
//        List<ExportHighestPriceTask> tasks = this.taskRepository.findHighestTaskPrice();
//
//        tasks.stream().forEach(task -> {
//            sb.append(String.format("Car %s %s with %dkm%n" +
//                    "-Mechanic: %s - task №%d:%n" +
//                    " --Engine: %.1f%n" +
//                    "---Price: %.2f$%n",task.getCarMake(), task.getCarModel(),task.getKilometers()
//                    ,task.getFullName(),task.getId(),
//                    task.getEngine()
//                    ,task.getPrice()));
//
//
//        });

//        return sb.toString();

        return this.taskRepository.findAllByCarCarTypeOrderByPriceDesc(CarType.coupe)
                .stream().map(task -> modelMapper.map(task, TaskDto.class))
                .map(TaskDto::toString)
                .collect(Collectors.joining()).trim();
    }
}
