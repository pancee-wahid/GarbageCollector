import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MarkCompact {
    List<List<String>> heap = new ArrayList<>();        //heap -- each List has 4 elements : id - start - end - usedMark(added later)
    List<String> roots = new ArrayList<>();             //roots -- contains ids of roots
    List<List<String>> pointers = new ArrayList<>();    //pointer -- each List has 2 elements : parent id - child id
    String delimiter = ",";

    //remove garbage in first line in excel files
    public List<String> removeEx(List<String> list){
        String str = list.get(0);
        //get last 6 chars in first column to get rid of special chars
        str = str.substring(str.length() - 6);
        list.set(0, str);
        return  list;
    }

    public void readHeap(String path){
        try(BufferedReader br = Files.newBufferedReader(Paths.get(path))){
            // read the file line by line
            String line;
            int i = 0;
            while((line = br.readLine()) != null){
                // convert line into columns
                String[] columns = line.split(delimiter);
                List<String> temp = Arrays.asList(columns); // id - start - end
                temp = new ArrayList<>(temp);
                if(i == 0){
                    temp = removeEx(temp);
                    i++;
                }
                // id - start - end - usedMark
                temp.add("false");  //mark as not used
                heap.add(temp);
            }

        }catch(IOException ex){
            ex.printStackTrace();
        }
    }

    public void readRoots(String path){
        try(BufferedReader br = Files.newBufferedReader(Paths.get(path))){
            //read the file line by line
            String line;
            while((line = br.readLine()) != null)
                roots.add(line);
        }catch (IOException ex){
            ex.printStackTrace();
        }
    }

    public void readPointers(String path){
        try(BufferedReader br = Files.newBufferedReader(Paths.get(path))){
            // read the file line by line
            String line;
            int i = 0;
            while((line = br.readLine()) != null){
                // convert line into columns
                String[] columns = line.split(delimiter);
                List<String> temp = Arrays.asList(columns); // parent - child
                if(i == 0){
                    temp = removeEx(temp);
                    i++;
                }
                pointers.add(temp);
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    //iterate over roots and mark them in heap as used
    public void markRoots(){
        for (String root : roots) { setAsUsed(root); }
    }

    public void setAsUsed(String id){
        for (List<String> object : heap) {
            if (object.get(0).compareTo(id) == 0) {
                object.set(3, "true"); //mark as used
                break;
            }
        }
    }

    //get children of object with given ids
    public List<String> getChildren(String id){
        List<String> children = new ArrayList<>();
        for (List<String> pointer : pointers) {
            //get children associated with the given id from pointers except if there exists self-loop
            if (pointer.get(0).compareTo(id) == 0 && pointer.get(0).compareTo(pointer.get(1)) != 0) {
                children.add(pointer.get(1));
            }
        }
        return children;
    }

    //mark all children and grandchildren of the parent with the given id
    public void mark(String id){
        List<String> children = getChildren(id);
        for (String child : children) {
            setAsUsed(child);
            mark(child);
        }
    }

    //remove objects marked as not used from heap
    public void sweep(){
        for(int i = 0; i < heap.size(); i++){
            if(heap.get(i).get(3).compareTo("false") == 0){
                heap.remove(i);
                i--;
            }
        }
    }

    //shift objects remaining in heap to be in successive memory locations - to solve external fragmentation
    public void compact(){
        int start = 0;
        for (List<String> object : heap) {
            int size = toInt(object.get(2)) - toInt(object.get(1)) + 1;
            object.set(1, toStr(start));
            object.set(2, toStr(start + size - 1));
            object.remove(3);  //remove used status
            start += size;
        }
    }

    public int toInt(String s){
        return Integer.parseInt(s);
    }

    public String toStr(int num){
        return String.valueOf(num);
    }

    //write the heap after mark, sweep & compact to the output file
    public void writeHeap(String fileName) throws IOException {
        FileWriter csvWriter = new FileWriter(fileName);
        for (int i = 0; i < heap.size(); i++) {
            List<String> row = heap.get(i);
            csvWriter.append(String.join(",", row));
            if(i != heap.size() - 1)
                csvWriter.append("\n");
        }
        csvWriter.flush();
        csvWriter.close();
    }

    //execute the algorithm
    public void markAndCompact(String heapPath, String rootsPath, String pointersPath, String newHeapPath_MarkAndCompact) throws IOException {
        readRoots(rootsPath);
        if(roots.size() == 0){
            heap = new ArrayList<>();
        }else{
            readHeap(heapPath);
            readPointers(pointersPath);
            //mark roots then their children
            markRoots();
            for (String root : roots) {
                mark(root);
            }
            //sweep and compact
            sweep();
            compact();
        }
        writeHeap(newHeapPath_MarkAndCompact);
    }
}