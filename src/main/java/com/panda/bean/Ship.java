package com.panda.bean;

import com.google.common.collect.Lists;
import lombok.Data;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Random;
/**
 * @author GXB
 */
@Data
public class Ship {

    private String name;
    private String action;
    private int a;
    private int b;
    private int c;
    private int d;

    private List<Ship> targetShips;
    private String status;

    /**
     * 飞船日志
     */
    private ShipRecord shipRecord = new ShipRecord();

    public void init() {
        this.a = 1;
        this.b = 1;
        this.c = 1;
        this.d = 1;
        this.status = "1";
    }

    public int getAmmoNum(){
        return a + b + c + d;
    }

    /**随机一个动作
     * 可选的动作为1，不可选为0
     * 可选动作总数 = 动作之和
     * 随机数 = 第几个可选动作
     * 第一次出D的概率 = firstDRate/shipNum
     */
    public void randomAction(int firstDRate, int shipNum) {
        int total = a + b + c + d;
        if(total == 4 ){
            int x = new Random().nextInt(shipNum);
            if(x >= firstDRate){
                int n = new Random().nextInt(3) + 1;
                if (a == n) {
                    a--;
                    this.action = "A";
                } else if (a + b == n) {
                    b--;
                    this.action = "B";
                } else if (a + b + c == n) {
                    c--;
                    this.action = "C";
                }
            }else{
                d--;
                this.action = "D";
            }
        }else{

            int n = new Random().nextInt(total) + 1;
            if (a == n) {
                a--;
                this.action = "A";
            } else if (a + b == n) {
                b--;
                this.action = "B";
            } else if (a + b + c == n) {
                c--;
                this.action = "C";
            } else if (a + b + c + d == n) {
                d--;
                this.action = "D";
            } else {
                System.err.println("出问题了");
            }
        }
    }

    /**随机抽2个目标
     * 先建个新列，去掉自己，然后随机拿出一个放进target，新列remove再拿一个出来
     */
    public void randomTarget(List<Ship> ships) {
        List<Ship> targets = Lists.newArrayList();
        ships.forEach(s -> {
                    if (s != this) {
                        targets.add(s);
                    }
                }
        );
		if (targets.size() <= 2) {
            this.targetShips = targets;
		} else {
            this.targetShips = Lists.newArrayList();;
			int a1 = new Random().nextInt(targets.size());
            targetShips.add(targets.get(a1));
            targets.remove(a1);
			int a2 = new Random().nextInt(targets.size());
            targetShips.add(targets.get(a2));
            targets.remove(a2);
		}

    }
    /**计算结果*/
    public void clacResult(){
        System.out.println(this.name + this.action);
        //第一次记录日志时,记录第一次的选择
        if(StringUtils.isBlank(shipRecord.getRecord())){
            this.shipRecord.setFirstSelect(this.action);
        }

        writeRecord("[ 选择"+ this.action);
        if (CollectionUtils.isNotEmpty(this.targetShips) && !"D".equals(action) ) {
            Ship ship1 = this.targetShips.get(0);
            if(atkResult(this.action, ship1.getAction())){
                ship1.setStatus("0");
                System.out.println("目标 " + ship1.getName()+ship1.getAction() + "击破");

                writeRecord(" 击破"+ ship1.getName()+ship1.getAction());
            }else{
                System.out.println("目标 " + ship1.getName()+ship1.getAction() + "无效");
            }
            if (this.targetShips.size() > 1) {
                Ship ship2 = this.targetShips.get(1);
                if(atkResult(this.action, ship2.getAction())){
                    ship2.setStatus("0");
                    System.out.println("目标 " + ship2.getName()+ship2.getAction() + "击破");

                    writeRecord(" 击破"+ ship2.getName()+ship2.getAction());
                }else{
                    System.out.println("目标 " + ship2.getName()+ship2.getAction() + "无效");
                }
            }
        }
        writeRecord("]\n");
    }


    public void writeRecord(String record){
        this.shipRecord.setRecord(this.shipRecord.getRecord() +record);
    }

    public boolean atkResult(String act1,String act2){
        if("A".equals(act1) && "B".equals(act2)){
            return true;
        }else if("B".equals(act1) && "C".equals(act2)){
            return true;
        }else if("C".equals(act1) && "A".equals(act2)){
            return true;
        }else{
            return false;
        }
    }



    public static void main(String[] args) {
        for (int a = 0; a < 100; a++) {
            int i = new Random().nextInt(6);
            System.out.println(i);
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public int getA() {
        return a;
    }

    public void setA(int a) {
        this.a = a;
    }

    public int getB() {
        return b;
    }

    public void setB(int b) {
        this.b = b;
    }

    public int getC() {
        return c;
    }

    public void setC(int c) {
        this.c = c;
    }

    public int getD() {
        return d;
    }

    public void setD(int d) {
        this.d = d;
    }

}
