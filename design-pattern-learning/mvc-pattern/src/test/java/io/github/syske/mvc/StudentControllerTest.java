package io.github.syske.mvc;

import junit.framework.TestCase;
import org.junit.Test;

/**
 * @author syske
 * @version 1.0
 * @date 2021-10-21 22:38
 */
public class StudentControllerTest extends TestCase {

    @Test
    public void testMVC() {

        //从数据库获取学生记录
        StudentModel model = retrieveStudentFromDatabase();

        //创建一个视图：把学生详细信息输出到控制台
        StudentView view = new StudentView();

        StudentController controller = new StudentController(model, view);

        controller.updateView();

        //更新模型数据
        controller.setStudentName("syske");

        controller.updateView();
    }


    private static StudentModel retrieveStudentFromDatabase() {
        StudentModel student = new StudentModel();
        student.setName("云中志");
        student.setRollNo("10");
        return student;
    }

}