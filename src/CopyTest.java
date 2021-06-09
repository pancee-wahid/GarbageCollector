import java.io.IOException;

public class CopyTest {
    public static void main(String[] args) throws IOException {
        if (args.length == 4) {
            //change this path to the src folder path in your computer
            String commonPath = "D:\\Projects\\IdeaProjects\\GarbageCollector\\src\\";

            String heapPath = commonPath + args[0];
            String rootsPath = commonPath + args[1];
            String pointersPath = commonPath + args[2];
            String newHeapPath_Copy = commonPath + args[3];
            Copy copy = new Copy();
            copy.copy(heapPath, rootsPath, pointersPath, newHeapPath_Copy);
        } else {
            System.out.println("Invalid Arguments!");
        }
    }
}
