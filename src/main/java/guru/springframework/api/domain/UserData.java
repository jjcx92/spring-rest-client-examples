package guru.springframework.api.domain;

import java.util.List;

/**
 * Juerghens castro on 06-30-20 and  09:06 AM to 2020
 */
public class UserData {

    List<User> data;

    public  List<User> getData(){
        return data;
    }

    public void setData(List<User>  data){
        this.data=data;
    }
}
