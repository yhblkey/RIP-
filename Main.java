import java.util.*;






public class Main {
    public static void display(ArrayList<Rout> rlist){

        for (int i=0;i<rlist.size();i++){
            System.out.println("第"+(i+1)+"个路由的路由表");
            System.out.format("%-10s %-10s %-10s \n","NetId","Distance","NextRout");
//            System.out.format("%-10d %-10d %-10d",rlist.get(i).rout_table.)
            for (int j=0;j<rlist.get(i).rout_table.size();j++){
                System.out.format("%-10d %-10d %-10d \n",rlist.get(i).rout_table.get(j).get("NetId"),rlist.get(i).rout_table.get(j).get("Distance"),rlist.get(i).rout_table.get(j).get("NextRout"));
            }
            System.out.println();
        }
    }



    public static int exists_netid(ArrayList<HashMap<String,Integer>> rout_table,int netid){
        for (int i=0;i<rout_table.size();i++){
            if (rout_table.get(i).get("NetId") == netid){
                return i;
            }else
                continue;
        }
        return -1;
    }
    public static boolean isOver(ArrayList<Rout> rout_list){
        for (int i=0;i<rout_list.size();i++){
            if (rout_list.get(i).rout_table.size() == 6){
                continue;
            }else {
                return false;
            }
        }
        return true;
    }
    public static void rip(ArrayList<Rout> rlist){
        //遍历每一个路由
        for (int i=0;i<rlist.size();i++){

            //获取自身路由的路由表路径加一，并且换下一跳路由
            ArrayList<HashMap<String,Integer>> temp_table = new ArrayList<HashMap<String,Integer>>();
            for (int lens =0;lens < rlist.get(i).rout_table.size();lens++){
                HashMap<String,Integer> temp = new HashMap<String,Integer>();
                temp.put("NetId",rlist.get(i).rout_table.get(lens).get("NetId"));
                temp.put("Distance",rlist.get(i).rout_table.get(lens).get("Distance")+1);
                temp.put("NextRout",rlist.get(i).rout_id);
                temp_table.add(temp);
            }


            //向相邻路由发送路由表
            for (int j=0;j<rlist.get(i).next_rout.size();j++){
                //相邻路由表接受数据
                //对于备份表内的每一条数据进行操作
                for (int len=0;len<temp_table.size();len++){
                    int flag = -1;
                    if ((flag = exists_netid(rlist.get(rlist.get(i).next_rout.get(j)-1).rout_table,temp_table.get(len).get("NetId")))!= -1){
                        if (rlist.get(rlist.get(i).next_rout.get(j)-1).rout_table.get(flag).get("NextRout") == temp_table.get(len).get("NextRout")){
                            rlist.get(rlist.get(i).next_rout.get(j)-1).rout_table.set(flag,temp_table.get(len));
                        }else {
                            if (rlist.get(rlist.get(i).next_rout.get(j)-1).rout_table.get(flag).get("Distance") > temp_table.get(len).get("Distance")){
                                rlist.get(rlist.get(i).next_rout.get(j)-1).rout_table.set(flag,temp_table.get(len));
                            }
                        }
                    }else {
                        rlist.get(rlist.get(i).next_rout.get(j)-1).rout_table.add(temp_table.get(len));
                    }
                }
            }
        }

    }
    public static void main(String[] args) {
        ArrayList<Rout> rout_list = new ArrayList<Rout>();
        ArrayList<ArrayList<Integer>> net_id = new ArrayList<ArrayList<Integer>>();
        ArrayList<ArrayList<Integer>> nextrout = new ArrayList<ArrayList<Integer>>();
        net_id.add(new ArrayList<Integer>(Arrays.asList(1,2,3)));
        net_id.add(new ArrayList<Integer>(Arrays.asList(1,5)));
        net_id.add(new ArrayList<Integer>(Arrays.asList(3,4)));
        net_id.add(new ArrayList<Integer>(Arrays.asList(2,5)));
        net_id.add(new ArrayList<Integer>(Arrays.asList(4,6)));
        net_id.add(new ArrayList<Integer>(Arrays.asList(5,6)));
        nextrout.add(new ArrayList<Integer>(Arrays.asList(2,3,4)));
        nextrout.add(new ArrayList<Integer>(Arrays.asList(1,4,6)));
        nextrout.add(new ArrayList<Integer>(Arrays.asList(1,5)));
        nextrout.add(new ArrayList<Integer>(Arrays.asList(1,2,6)));
        nextrout.add(new ArrayList<Integer>(Arrays.asList(3,6)));
        nextrout.add(new ArrayList<Integer>(Arrays.asList(2,4,5)));
        for(int i = 0;i<6;i++){
            rout_list.add(new Rout(net_id.get(i), nextrout.get(i), i+1));
        }
        display(rout_list);
//        rip(rout_list);        //进行一次rip
//        display(rout_list);
        int count =0;
        while (!isOver(rout_list)){
            rip(rout_list);
            count++;

        }
        display(rout_list);
        System.out.println(count);
    }
}
