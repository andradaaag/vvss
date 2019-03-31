import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import service.ServiceTest;

public class TestRunner {
    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(ServiceTest.class);
        for (Failure failure : result.getFailures()) {
            System.out.println(failure.toString());
            System.exit(1);
        }

        if (!result.wasSuccessful()){
            System.exit(1);
        }
    }
}
