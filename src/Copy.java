import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Copy {
    List<List<String>> heap = new ArrayList<>();        //heap -- each List has 4 elements : id - start - end - copiedMark(added later)
    List<String> roots = new ArrayList<>();             //roots -- contains ids of roots
    List<List<String>> pointers = new ArrayList<>();    //pointer -- each List has 2 elements : parent id - child id
    List<List<String>> newHeap = new ArrayList<>();     //new heap -- heap sorted by level
    List<String> copyList = new ArrayList<>();          //ids of new arranged heep
    String delimiter = ",";
    int s = 0;  //starting pointer
    int e = 0;  //ending pointer

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
                // id - start - end - copied
                temp.add("false");  //mark as not copied
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
                List<String> temp = Arrays.asList(columns); //parent - child
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

    public void copyRoots(){
        for (String root : roots) {
            copyList.add(root); //add roots to copyList
            setAsCopied(root); //set roots in heap as copied
        }
        e = copyList.size() - 1; //make the end pointer points to the new end
    }

    //mark object with given id as copied in heap
    public void setAsCopied(String id){
        for (List<String> object : heap) {
            if (object.get(0).compareTo(id) == 0) {
                object.set(3, "true"); //mark as copied
                break;
            }
        }
    }

    //check if object with given id in heap is copied
    public boolean isCopied(String id){
        for (List<String> object : heap) {
            if (object.get(0).compareTo(id) == 0) {
                return object.get(3).compareTo("true") == 0;
            }
        }
        return false;
    }

    //check if object with given id is parent -- check if it's in first column in pointers
    public boolean isParent(String id){
        for (List<String> pointer : pointers) {
            if (pointer.get(0).compareTo(id) == 0) {
                return true;
            }
        }
        return false;
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

    //iterate on copyList to add children of roots
    public void copying(){
        for (int i = 0; i < copyList.size(); i++) {
            String id = copyList.get(i);
            //check if start not equal end
            //or start equal end but the last object is parent
            if((s != e) || isParent(id)){
                List<String> children = getChildren(id);
                //iterate on children to add them in their supposed place
                for (String child : children) {
                    if (!isCopied(child)) {
                        copyList.add(child);
                        setAsCopied(child);
                        e++; //increase the end pointer if we add new child
                    }
                }
                s++; //increase the start pointer when we finish a parent
            }
        }
    }

    //create the new heap using the sorted (by level) id list -- copyList
    public void createNewHeap(){
        int start = 0;
        for (String id : copyList) {
            int size = getSize(id);
            List<String> temp = new ArrayList<>();
            temp.add(id);
            temp.add(toStr(start));
            temp.add(toStr(start + size - 1));
            newHeap.add(temp);
            start += size;
        }
    }

    //get size of object from original heap list
    public int getSize(String id){
        int size = 0;
        for (List<String> object : heap) {
            if (object.get(0).compareTo(id) == 0) {
                size = (toInt(object.get(2)) - toInt(object.get(1)) + 1);
            }
        }
        return size;
    }

    public int toInt(String s){
        return Integer.parseInt(s);
    }

    public String toStr(int num){
        return String.valueOf(num);
    }

    //write the heap after copy to the output file
    public void writeHeap(String fileName) throws IOException {
        FileWriter csvWriter = new FileWriter(fileName);
        for (int i = 0; i < newHeap.size(); i++) {
            List<String> row = newHeap.get(i);
            csvWriter.append(String.join(",", row));
            if(i != newHeap.size() - 1)
                csvWriter.append("\n");
        }
        csvWriter.flush();
        csvWriter.close();
    }

    //execute the algorithm
    public void copy(String heapPath, String rootsPath, String pointersPath, String newHeapPath_copy) throws IOException {
        readRoots(rootsPath);
        if(roots.size() != 0){
            readHeap(heapPath);
            readPointers(pointersPath);
            copyRoots();
            copying();
            createNewHeap();
        }
        writeHeap(newHeapPath_copy);
    }
}