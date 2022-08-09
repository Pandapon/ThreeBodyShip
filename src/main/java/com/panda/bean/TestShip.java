package com.panda.bean;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TestShip {
	
	
	public static void main(String[] args) {
		//船数
		int shipNum = 7;
		//第一次出D的概率 = firstDRate/shipNum
		int firstDRate= 3;
		//试验次数
		int testNum = 10000;
		//打一局试试
		OnceGame(shipNum, firstDRate);
		//测试很多次
		//testManyTimes(shipNum, firstDRate, testNum);

	}

	private static void testManyTimes(int shipNum, int firstDRate, int testNum) {

		List<ShipRecord> results= Lists.newArrayList();
		List<ShipRecord> all= Lists.newArrayList();
		for(int i = 0; i < testNum; i++){
			Map<String, Object> map = OnceGame(shipNum, firstDRate);
			results.add((ShipRecord) map.get("alive"));
			List<ShipRecord> al = (List<ShipRecord>) map.get("all");
			all.addAll(al);
		}
		double a= 0;
		double b= 0;
		double c= 0;
		double d= 0;
		for(int j= 0; j < results.size() ;j++){
			System.out.println(j+1);
			ShipRecord shipRecord = results.get(j);
			System.out.println(results.get(j).getRecord());
			if("A".equals(shipRecord.getFirstSelect())){
				a++;
			}else if("B".equals(shipRecord.getFirstSelect())){
				b++;
			}else if("C".equals(shipRecord.getFirstSelect())){
				c++;
			}else if("D".equals(shipRecord.getFirstSelect())){
				d++;
			}
		}
		double aa= 0;
		double bb= 0;
		double cc= 0;
		double dd= 0;
		for(int k= 0; k < all.size() ;k++){

			ShipRecord shipRecord = all.get(k);
			if("A".equals(shipRecord.getFirstSelect())){
				aa++;
			}else if("B".equals(shipRecord.getFirstSelect())){
				bb++;
			}else if("C".equals(shipRecord.getFirstSelect())){
				cc++;
			}else if("D".equals(shipRecord.getFirstSelect())){
				dd++;
			}
		}


		System.out.println("首轮A的胜者有：" + (int)a + "/" +(int)aa+" 个" + " 胜率"+ Math.round(a/aa*10000D)/100.0 + "%");
		System.out.println("首轮B的胜者有：" + (int)b + "/" +(int)bb+" 个" + " 胜率"+ Math.round(b/bb*10000D)/100.0 + "%");
		System.out.println("首轮C的胜者有：" + (int)c + "/" +(int)cc+" 个" + " 胜率"+ Math.round(c/cc*10000D)/100.0 + "%");
		System.out.println("首轮D的胜者有：" + (int)d + "/" +(int)dd+" 个" + " 胜率"+ Math.round(d/dd*10000D)/100.0 + "%");
	}

	/** 进行一局游戏
	 * @param shipNum
	 * @param firstDRate
	 * @return
	 */
	public static Map<String,Object> OnceGame(int shipNum, int firstDRate){
		//船初始化
		List<Ship> ships=new ArrayList<Ship>();
		for(int i=1; i <= shipNum; i++) {
			Ship sp= new Ship();
			sp.setName(i + "号船");
			sp.init();
			ships.add(sp);
		}

		//进入回合,回收幸存者的航行日志
		ShipRecord aliveRecord = round(ships, firstDRate);

		//回收所有船的航行日志
		List<ShipRecord> allRecord = Lists.newArrayList();
		ships.forEach(e -> {
			allRecord.add(e.getShipRecord());
		});

		Map<String,Object> map = Maps.newHashMap();
		map.put("alive",aliveRecord);
		map.put("all",allRecord);
		return map;
	}

	/**
	 * 回合
	 * @param ships
	 * @param dRate
	 * @return
	 */
	public static ShipRecord round(List<Ship> ships, int dRate){
		System.out.println();

		ships.forEach(ship -> {
			//选择随机行动
			ship.randomAction(dRate, ships.size());
			//选择2个随机目标
			ship.randomTarget(ships);
		});
		//结算胜负
		ships.forEach(ship -> {
			ship.clacResult();
		});

		List<Ship> alive = Lists.newArrayList();
		System.out.println("\n此轮结果:");

		//结算完胜负后,进行状态统计
		ships.forEach(ship -> {
			String s = ship.getStatus();
			//生存状态=1 为存活
			if("1".equals(s)){
				if(ship.getAmmoNum() == 0){
					//当弹药打完了,恢复弹药(即4轮结束了)
					ship.init();
				}
				//记录存活的船
				alive.add(ship);
				System.out.println(ship.getName()+"存活");
			}else{
				System.out.println(ship.getName()+"坠毁");
			}
		});
		System.out.println("---------------------------------");

		if(alive.size() > 1){
			//幸存船数大于1时,幸存者们继续循环
			return round(alive,dRate);
		}else if(alive.size() == 1){
			Ship survival = alive.get(0);
			ShipRecord shipRecord = survival.getShipRecord();
			String finalMsg = "最后的幸存者"+survival.getName()+"\n" + shipRecord.getRecord();
			System.out.println(finalMsg);
			return shipRecord;
		}else{
			ShipRecord shipRecord = new ShipRecord();
			shipRecord.setRecord("无人生还");
			return shipRecord;
		}
	}
}
