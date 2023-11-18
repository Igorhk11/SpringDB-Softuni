package softuni.exam.models.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "tasks")
@XmlAccessorType(XmlAccessType.FIELD)
public class TasksRootSeedDto {

    @XmlElement(name = "task")
    List<TasksSeedDTO> tasks;

    public List<TasksSeedDTO> getTasks() {
        return tasks;
    }

    public void setTasks(List<TasksSeedDTO> tasks) {
        this.tasks = tasks;
    }
}
