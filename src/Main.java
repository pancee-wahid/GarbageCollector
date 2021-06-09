import java.io.IOException;

class Main {
    public static void main(String[] args) throws IOException {
        //change this path to the src folder path in your computer
        String commonPath = "D:\\Projects\\IdeaProjects\\GarbageCollector\\src\\";

        //sample test
        String heapPath = commonPath + "sample test\\heap.csv";
        String rootsPath = commonPath + "sample test\\roots.txt";
        String pointersPath = commonPath + "sample test\\pointers.csv";
        String newHeapPath_MarkAndCompact = commonPath + "sample test\\Mark&Compact_new-heap.csv";
        String newHeapPath_Copy = commonPath + "sample test\\Copy_new-Heap.csv";
        //Mark & Compact GC Test
        MarkCompact markCompact = new MarkCompact();
        markCompact.markAndCompact(heapPath, rootsPath, pointersPath, newHeapPath_MarkAndCompact);
        //Copy GC Test
        Copy copy = new Copy();
        copy.copy(heapPath, rootsPath, pointersPath, newHeapPath_Copy);

/*
.
.
.
.
 */
        //TestCase_1
        heapPath = commonPath + "testCase_1\\heap_testCase_1.csv";
        rootsPath = commonPath + "testCase_1\\roots_testCase_1.txt";
        pointersPath = commonPath + "testCase_1\\pointers_testCase_1.csv";
        newHeapPath_MarkAndCompact = commonPath + "testCase_1\\Mark&Compact_new-heap_1.csv";
        newHeapPath_Copy = commonPath + "testCase_1\\Copy_new-Heap_1.csv";
        //Mark & Compact GC Test
        MarkCompact markCompact_1 = new MarkCompact();
        markCompact_1.markAndCompact(heapPath, rootsPath, pointersPath, newHeapPath_MarkAndCompact);
        //Copy GC Test
        Copy copy_1 = new Copy();
        copy_1.copy(heapPath, rootsPath, pointersPath, newHeapPath_Copy);
/*
.
.
.
.
 */
        //TestCase_2
        heapPath = commonPath + "testCase_2\\heap_testCase_2.csv";
        rootsPath = commonPath + "testCase_2\\roots_testCase_2.txt";
        pointersPath = commonPath + "testCase_2\\pointers_testCase_2.csv";
        newHeapPath_MarkAndCompact = commonPath + "testCase_2\\Mark&Compact_new-heap_2.csv";
        newHeapPath_Copy = commonPath + "testCase_2\\Copy_new-Heap_2.csv";
        //Mark & Compact GC Test
        MarkCompact markCompact_2 = new MarkCompact();
        markCompact_2.markAndCompact(heapPath, rootsPath, pointersPath, newHeapPath_MarkAndCompact);
        //Copy GC Test
        Copy copy_2 = new Copy();
        copy_2.copy(heapPath, rootsPath, pointersPath, newHeapPath_Copy);
/*
.
.
.
.
 */
        //TestCase_3
        heapPath = commonPath + "testCase_3\\heap_testCase_3.csv";
        rootsPath = commonPath + "testCase_3\\roots_testCase_3.txt";
        pointersPath = commonPath + "testCase_3\\pointers_testCase_3.csv";
        newHeapPath_MarkAndCompact = commonPath + "testCase_3\\Mark&Compact_new-heap_3.csv";
        newHeapPath_Copy = commonPath + "testCase_3\\Copy_new-Heap_3.csv";
        //Mark & Compact GC Test
        MarkCompact markCompact_3 = new MarkCompact();
        markCompact_3.markAndCompact(heapPath, rootsPath, pointersPath, newHeapPath_MarkAndCompact);
        //Copy GC Test
        Copy copy_3 = new Copy();
        copy_3.copy(heapPath, rootsPath, pointersPath, newHeapPath_Copy);
/*
.
.
.
.
 */
        //TestCase_4
        heapPath = commonPath + "testCase_4\\heap_testCase_4.csv";
        rootsPath = commonPath + "testCase_4\\roots_testCase_4.txt";
        pointersPath = commonPath + "testCase_4\\pointers_testCase_4.csv";
        newHeapPath_MarkAndCompact = commonPath + "testCase_4\\Mark&Compact_new-heap_4.csv";
        newHeapPath_Copy = commonPath + "testCase_4\\Copy_new-Heap_4.csv";
        //Mark & Compact GC Test
        MarkCompact markCompact_4 = new MarkCompact();
        markCompact_4.markAndCompact(heapPath, rootsPath, pointersPath, newHeapPath_MarkAndCompact);
        //Copy GC Test
        Copy copy_4 = new Copy();
        copy_4.copy(heapPath, rootsPath, pointersPath, newHeapPath_Copy);
    }
}
