package com.pb.kanivets.bki.core.wrappers;

import com.pb.kanivets.bki.core.entities.Client;
import java.util.List;

public class ListWrapper <T> {
    List<T> list;

    public ListWrapper() {
    }
        
    public ListWrapper(List<T> list) {
        this.list = list;
    }    

    public void setList(List<T> list) {
        this.list = list;
    }    
    
    public List<T> getList(){
        return list;
    }    

    @Override
    public String toString() {
        return "ListWrapper{" + "list=" + list + '}';
    }
    
    
    
}
