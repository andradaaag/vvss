import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import service.ServiceTest;

public class TestRunner {
    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(ServiceTest.class);
        for (Failure failure : result.getFailures()) {
            System.out.println(failure.toString());
        }
        if (result.wasSuccessful()){
            System.out.println("everything ran as expected;");
        }
    }
}
