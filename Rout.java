import java.util.ArrayList;
import java.util.HashMap;

public class Rout {
    ArrayList<HashMap<String,Integer>> rout_table;
    int rout_id;
    ArrayList<Integer> next_rout;
    public Rout(ArrayList<Integer> net_id,ArrayList<Integer> nextrout,int num){
        rout_table = new ArrayList<HashMap<String,Integer>>();
        next_rout = new ArrayList<Integer>();
        rout_id = num;
        init_Rout(net_id,nextrout);
    }
    public void init_Rout(ArrayList<Integer> net_id,ArrayList<Integer> nextrout){
        for(int i=0;i<net_id.size();i++){
            HashMap<String,Integer> temp = new HashMap<String,Integer>();
            temp.put("NetId", net_id.get(i));
            temp.put("Distance", 1);
            temp.put("NextRout", 0);
            rout_table.add(temp);
        }
        for(int i=0;i<nextrout.size();i++){
            next_rout.add(nextrout.get(i));
        }
    }
}